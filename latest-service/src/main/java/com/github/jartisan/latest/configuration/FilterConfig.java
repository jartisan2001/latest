package com.github.jartisan.latest.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/***
 * FilterConfig
 * @author jalen
 *
 */
@Configuration
public class FilterConfig {
	
	@Bean
	public FilterRegistrationBean<TokenFilter> filterRegistration() {
		// 新建过滤器注册类
		FilterRegistrationBean<TokenFilter> registration = new FilterRegistrationBean<TokenFilter>();
		// 添加自定义 过滤器
		registration.setFilter(globalFilter());
		// 设置过滤器的URL模式
		registration.addUrlPatterns("/*");
		// 设置过滤器顺序
		registration.setOrder(1);
		return registration;
	}

	@Bean
	public TokenFilter globalFilter() {
		return new TokenFilter();
	}
}
