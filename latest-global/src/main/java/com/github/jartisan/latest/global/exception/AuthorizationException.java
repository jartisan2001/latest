package com.github.jartisan.latest.global.exception;


/***
 * 权限异常
 * @author jartisan
 *
 */
public class AuthorizationException extends RuntimeException {
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

	public AuthorizationException(int errCode, String errMsg) {
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

}
