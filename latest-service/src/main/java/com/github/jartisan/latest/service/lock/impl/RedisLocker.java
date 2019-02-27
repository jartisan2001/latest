package com.github.jartisan.latest.service.lock.impl;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.jartisan.latest.service.lock.AquiredLockWorker;
import com.github.jartisan.latest.service.lock.DistributedLocker;
import com.github.jartisan.latest.service.lock.RedissonConnector;
import com.github.jartisan.latest.service.lock.UnableToAquireLockException;

/**
 * @Title: RedisLocker.java
 * @Package com.github.jartisan.latest.service.lock.impl
 * @author wjl
 * @date 2018年5月8日 上午11:40:13
 * @version V1.0
 */
@Component
public class RedisLocker implements DistributedLocker {
	private final static String LOCKER_PREFIX = "lock:";

	@Autowired
	RedissonConnector redissonConnector;

	@Override
	public <T> T lock(String resourceName, AquiredLockWorker<T> worker)
			throws InterruptedException, UnableToAquireLockException, Exception {

		return lock(resourceName, worker, 100);
	}

	@Override
	public <T> T lock(String resourceName, AquiredLockWorker<T> worker, int lockTime)
			throws UnableToAquireLockException, Exception {
		RedissonClient redisson = redissonConnector.getClient();
		RLock lock = redisson.getLock(LOCKER_PREFIX + resourceName);
		// Wait for 100 seconds seconds and automatically unlock it after
		// lockTime seconds
		boolean success = lock.tryLock(100, lockTime, TimeUnit.SECONDS);
		if (success) {
			try {
				return worker.invokeAfterLockAquire();
			} finally {
				lock.unlock();
			}
		}
		throw new UnableToAquireLockException();
	}
}
