package com.github.jartisan.latest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
/***
 * 
 * @author jalen
 * @date: 2016年2月26日 上午11:39:24
 */
@SpringBootApplication
@ServletComponentScan
@MapperScan(basePackages = "com.github.jartisan.latest.dao")
public class LatestApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(LatestApplication.class, args);
	}
}
