package com.github.jartisan.latest.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/***
 * 
 * 初始化
 * @author wjl
 *
 */
@Component
public class LatestInitializeRunner implements CommandLineRunner {
	
	private static final Logger log = LoggerFactory.getLogger(LatestInitializeRunner.class);
	
	@Override
    public void run(String... args) throws Exception {
		log.info("初始化数据，加载数据到缓存redis");
    }
}
