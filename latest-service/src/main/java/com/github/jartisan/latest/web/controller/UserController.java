package com.github.jartisan.latest.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.jartisan.latest.configuration.SlaveDataSource;
import com.github.jartisan.latest.global.response.RestResult;

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
	
	@RequestMapping(value = "/v1/users",method = RequestMethod.GET)
	@SlaveDataSource
	public RestResult<List<String>>  usersList() throws InterruptedException {
		List<String> users = new ArrayList<String>(3);
		users.add("freewolf");
		users.add("tom");
		users.add("jerry");
		//Thread.sleep(30000);
		logger.info("users:{}",users);
		return RestResult.ok(users);
	}

}
