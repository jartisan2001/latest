package com.github.jartisan.latest.service.lock;


/**   
* @Title: DistributedLocker.java 
* @Package com.github.jartisan.latest.service.lock 
* @author wjl
* @date 2018年5月8日 上午11:37:24 
* @version V1.0   
*/
public interface DistributedLocker {
	
	 /**
     * 获取锁
     * @param resourceName  锁的名称
     * @param worker 获取锁后的处理类
     * @param <T>
     * @return 处理完具体的业务逻辑要返回的数据
     * @throws UnableToAquireLockException
     * @throws Exception
     */
    <T> T lock(String resourceName, AquiredLockWorker<T> worker) throws UnableToAquireLockException, Exception;
    /***
     * lock
     * @param resourceName
     * @param worker
     * @param lockTime
     * @return
     * @throws UnableToAquireLockException
     * @throws Exception
     */
    <T> T lock(String resourceName, AquiredLockWorker<T> worker, int lockTime) throws UnableToAquireLockException, Exception;


}
