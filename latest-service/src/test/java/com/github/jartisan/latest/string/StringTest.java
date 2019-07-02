package com.github.jartisan.latest.string;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import weixin.popular.bean.message.EventMessage;

/***
 * 
 * @author wjl
 *
 */
public class StringTest {

	private static Logger logger = LoggerFactory.getLogger(StringTest.class);
	
	@Test
	public void testMoreObjectsUtil(){
		EventMessage msg = new EventMessage();
		msg.setDescription("111");
		msg.setEvent("event");
		logger.info(ReflectionToStringBuilder.toString(msg, ToStringStyle.MULTI_LINE_STYLE, true, true, true, null));
	}
	
}
