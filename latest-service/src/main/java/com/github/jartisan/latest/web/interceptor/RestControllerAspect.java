package com.github.jartisan.latest.web.interceptor;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**   
* @Title: RestControllerAspect.java 
* @Package com.github.jartisan.latest.web.interceptor 
* @Description: RestController
* @author jalen
* @date 2018年1月2日 下午6:15:41 
* @version V1.0   
* @Aspect
* @Component 
*/

public class RestControllerAspect {
	 protected final Logger log = LoggerFactory.getLogger(RestControllerAspect.class);
	    @Before("execution(public * com.github.jartisan.latest.web.controller.*Controller.*(..))")
	    public void logBeforeRestCall(JoinPoint pjp) throws Throwable {
	        log.info(":::::AOP Before REST call:::::" + pjp);
	    }
}
