package com.github.jartisan.latest.configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @Title: ThreadPoolConfig.java
 * @Package com.github.jartisan.springbootdemo.configuration
 * @Description: 自定义线程池
 * @author wjl
 * @date 2017年10月20日 上午12:13:57
 * @version V1.0
 */
@Configuration
@EnableAsync
public class ThreadPoolConfig {
	@Bean
	public Executor defaultThreadPool() {
		ThreadPoolTaskScheduler executor = new ThreadPoolTaskScheduler();
		//线程池维护线程的最大数量
		executor.setPoolSize(20);
		/**
		 * 优先级
		 * 最低优先级 :1
		 * 普通优先级 :5
		 * 最高优先级 :10 
		 */
		executor.setThreadPriority(5);
        //rejection-policy：当pool已经达到max size的时候，如何处理新任务  
        //CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行  
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());  
		executor.setThreadNamePrefix("defaultThreadPool-");
		executor.initialize();
		return executor;
	}
	
	@Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(50);
        threadPoolTaskExecutor.setMaxPoolSize(200);
        threadPoolTaskExecutor.setQueueCapacity(1000);
        threadPoolTaskExecutor.setThreadNamePrefix("Data-Job");
        return threadPoolTaskExecutor;
    }

}
