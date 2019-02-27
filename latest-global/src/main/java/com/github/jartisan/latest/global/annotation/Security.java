package com.github.jartisan.latest.global.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*** 
 * 授权用于Controller
 * 该方法在映射时会检查用户是否登录
 * Status code (401) indicating that the request requires HTTP
 * Security.
 * @author jartisan
 * @date 2017/09/20.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Security {

}
