package com.github.jartisan.latest.service.lock;

import javax.annotation.PostConstruct;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

/**
 * @Title: RedissonConnector.java
 * @Package com.github.jartisan.latest.service.lock
 * @author wjl
 * @date 2018年5月8日 上午11:39:15
 * @version V1.0
 */
@Component
public class RedissonConnector {

	RedissonClient redisson;

	@PostConstruct
	public void init() {
		redisson = Redisson.create();
	}

	public RedissonClient getClient() {
		return redisson;
	}
}
