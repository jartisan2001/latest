package com.github.jartisan.latest.service.strategy.impl;

import org.springframework.stereotype.Component;

import com.github.jartisan.latest.service.strategy.Handler;

/***
 * 
 * @author wjl
 *
 */
@Component
public class WriteHandler implements Handler {

	@Override
	public String getType() {
		return "WriteHandler";
	}

	@Override
	public void execute() {
		System.out.println("WriteHandler execute");

	}

}
