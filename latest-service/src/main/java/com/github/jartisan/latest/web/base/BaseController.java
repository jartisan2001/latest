package com.github.jartisan.latest.web.base;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.github.jartisan.latest.global.enums.GlobalCode;
import com.github.jartisan.latest.global.exception.BaseException;
import com.github.jartisan.latest.global.response.RestResult;
/***
 * @author jalen
 * @date: 2016年2月26日 上午11:39:24
 */
@ControllerAdvice
public class BaseController {
	private static final Logger log = LoggerFactory.getLogger(BaseController.class);
	

	@ExceptionHandler(BaseException.class)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody RestResult<String> baseExceptionHandler(BaseException ex) {
		log.error("发生系统错误:{}:{}", ex.getErrCode(),ex.getErrMsg(),ex);
		return RestResult.failure(ex.getErrCode(), ex.getErrMsg());
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody RestResult<String> handleAllException(Exception ex) {
		log.error("发生系统错误:{}", ex.getMessage(),ex);
		return RestResult.failure(GlobalCode.ERROR.getStatus(), GlobalCode.ERROR.getMessage(),ex.getMessage());
	}
	
	@ExceptionHandler(BindException.class)  
	@ResponseStatus(HttpStatus.OK)  
    public @ResponseBody RestResult<String> handleValidationException(BindException e) {  
   	   log.warn("参数验证失败", e.getMessage());  
   	   Set<String> errorCodes = new HashSet<>();
		for (ObjectError error :  e.getAllErrors()) {
			errorCodes.add(error.getDefaultMessage());
		}
		return RestResult.failure(GlobalCode.ERROR_190002.getStatus(), errorCodes.toString());
    } 
	
	 @ExceptionHandler(MethodArgumentNotValidException.class)  
	 @ResponseStatus(HttpStatus.OK)  
     public @ResponseBody RestResult<String> handleValidationException(MethodArgumentNotValidException e) {  
		log.warn("参数验证失败", e.getMessage());  
    	Set<String> errorCodes = new HashSet<>();
    	for (ObjectError error :  e.getBindingResult().getAllErrors()) {
			errorCodes.add(error.getDefaultMessage());
		}
    	return RestResult.failure(GlobalCode.ERROR_190002.getStatus(), errorCodes.toString());  
     }  
}
