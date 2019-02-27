package com.github.jartisan.latest.configuration;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**   
* @Title: RestTemplateConfiguration.java 
* @Package com.github.jartisan.springbootdemo.configuration 
* @author wjl
* @date 2017年10月17日 下午7:20:25 
* @version V1.0   
*/
@Configuration
public class RestTemplateConfiguration {
	
	@Autowired  
    private RestTemplateBuilder builder;  
  
	@Bean
	public RestTemplate restTemplate(){
		RestTemplate restTemplate = builder
			 //连接超时为3秒
			.setConnectTimeout(Duration.ofSeconds(3))
			//请求超时为3秒
			.setReadTimeout(Duration.ofSeconds(3))
			.build();
		return restTemplate;
	}

}
