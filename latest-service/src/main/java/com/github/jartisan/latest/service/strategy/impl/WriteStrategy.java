package com.github.jartisan.latest.service.strategy.impl;

import org.springframework.stereotype.Component;

import com.github.jartisan.latest.service.strategy.Strategy;


/***
 * 
 *@author wjl
 *
 */
@Component("write")
public class WriteStrategy implements Strategy{

	@Override
	public String action() {
		return "Writing";
	}

}
