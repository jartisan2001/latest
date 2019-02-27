package com.github.jartisan.latest.global.exception;

import java.io.IOException;

/***
 * http HttpStatus 503 异常
 * @code 503 Service Unavailable} (HTTP/1.0 - RFC 1945) 
 * @author jartisan
 */
public class ServiceUnavailableException extends IOException {
	private static final long serialVersionUID = 1L;


	private int errCode;
	private String errMsg;

	public int getErrCode() {
		return errCode;
	}

	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public ServiceUnavailableException(int errCode, String errMsg) {
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

}
