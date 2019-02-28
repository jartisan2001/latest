package com.github.jartisan.latest.configuration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: 作用于业务层service实现类方法上，标注slave库 ,默认为主库
 * @author: jalen 
 * @date:   2016年2月26日 上午11:35:56 
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SlaveDataSource {
}
