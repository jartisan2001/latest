package com.github.jartisan.latest.global.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在 Controller 的方法参数中使用此注解
 * 该方法在映射时会注入当前登录的 User 对象
 * @author jartisan
 * @date 2017/09/20.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrentUser {

}
