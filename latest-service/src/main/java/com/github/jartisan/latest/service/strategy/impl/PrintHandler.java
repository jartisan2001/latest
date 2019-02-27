package com.github.jartisan.latest.service.strategy.impl;

import org.springframework.stereotype.Component;

import com.github.jartisan.latest.service.strategy.Handler;
/***
 * @author wjl
 *
 */
@Component
public class PrintHandler implements Handler {

	@Override
	public String getType() {
		
		return "PrintHandler";
	}

	@Override
	public void execute() {
		System.out.println("PrintHandler execute");
	}

}
