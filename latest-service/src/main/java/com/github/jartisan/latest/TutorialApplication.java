package com.github.jartisan.latest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/***
 * 
 * @author wjl
 * @date: 2016年2月26日 上午11:39:24
 */
@SpringBootApplication
@EnableBatchProcessing
@MapperScan(basePackages = "com.github.jartisan.latest.dao")
public class TutorialApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(TutorialApplication.class, args);
	}
}
