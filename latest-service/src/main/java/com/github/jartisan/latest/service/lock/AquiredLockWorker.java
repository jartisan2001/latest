package com.github.jartisan.latest.service.lock;


/**
 * 获取锁后需要处理的逻辑
 * @author wjl
 * @param <T>
 */
public interface AquiredLockWorker<T> {
	
	/***
	 * invokeAfterLockAquire
	 * @return
	 * @throws Exception
	 */
	T invokeAfterLockAquire() throws Exception;
	
}
