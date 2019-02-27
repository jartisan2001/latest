package com.github.jartisan.latest.service.strategy;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/***
 * ContextSpringFactory
 * @author ejb3
 *
 */
@Component
public class ContextSpringFactory {
	
	@Autowired
    private Map<String, Strategy> stgMap;
 
 
    public String doAction(String strType) {
        Strategy strategy = this.stgMap.get(strType);
        return strategy.action();
    }
}
