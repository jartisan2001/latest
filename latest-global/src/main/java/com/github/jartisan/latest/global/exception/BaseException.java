package com.github.jartisan.latest.global.exception;

/***
 * @description 异常封类  
 * @author wjl
 * @ClassName: BaseException 
 * @Version 版本 
 * @ModifiedBy jartisan
 * @Copyright com.github.jartisan 
 * @date 2017年07月25日 下午7:28:23
 */
public class BaseException extends RuntimeException {
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

	public BaseException(int errCode, String errMsg) {
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

}
