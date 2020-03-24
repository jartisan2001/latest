package com.github.jartisan.latest.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.jartisan.latest.configuration.SlaveDataSource;
import com.github.jartisan.latest.global.response.RestResult;
import com.google.common.collect.ImmutableSet;

/**
 * @ClassName: UserController
 * @Description: 用户管理
 * @author wjl
 * @date 2017年3月27日
 */
@RestController
@RequestMapping("/user")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	public static final ImmutableSet<String> USER_NAMES = ImmutableSet.of(
	        "A",
	        "B",
	        "C");
	@RequestMapping(value = "/v1/users",method = RequestMethod.GET)
	@SlaveDataSource
	public RestResult<ImmutableSet<String>>  usersList() throws InterruptedException {
		logger.info("users:{}",USER_NAMES);
		return RestResult.ok(USER_NAMES);
	}

}
