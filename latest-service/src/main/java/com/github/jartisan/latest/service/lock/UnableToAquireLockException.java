package com.github.jartisan.latest.service.lock;

/**
 * @Title: UnableToAquireLockException.java
 * @Package com.github.jartisan.latest.service.lock
 * @author wjl
 * @date 2018年5月8日 上午11:38:09
 * @version V1.0
 */
public class UnableToAquireLockException  extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public UnableToAquireLockException() {
	}

	public UnableToAquireLockException(String message) {
		super(message);
	}

	public UnableToAquireLockException(String message, Throwable cause) {
		super(message, cause);
	}

}
