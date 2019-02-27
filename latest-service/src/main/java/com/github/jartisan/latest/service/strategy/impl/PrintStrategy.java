package com.github.jartisan.latest.service.strategy.impl;

import org.springframework.stereotype.Component;

import com.github.jartisan.latest.service.strategy.Strategy;


/***
 * 打印
 * @author wjl
 *
 */
@Component("print")
public class PrintStrategy implements Strategy{

	@Override
	public String action() {
		
		return "printing";
	}

}
