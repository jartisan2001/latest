package com.github.jartisan.latest.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName: CorsConfig
 * @Description:CORS跨域请示
 * @author: jalen
 * @date: 2016年2月26日 上午11:39:24
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
	@Override
	public void addCorsMappings(CorsRegistry corsRegistry) {
		// 允许跨域访问资源定义： /api/ 所有资源
		// corsRegistry.addMapping("/api/**")
		corsRegistry.addMapping("/**")
				// 只允许本地的9000端口访问
				// .allowedOrigins("http://localhost:9000", "http://127.0.0.1:9000")
				.allowedOrigins("*")
				// 允许发送Cookie
				.allowCredentials(true)
				// 如果要限制 HEADER 或 METHOD 请自行更改
				.allowedHeaders("*")
				// 允许所有方法
				.allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD","OPTIONS");
	}
}
