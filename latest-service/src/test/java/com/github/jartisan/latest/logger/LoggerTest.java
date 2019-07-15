package com.github.jartisan.latest.logger;

import java.lang.reflect.Field;
import java.util.Objects;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import weixin.popular.bean.message.EventMessage;

/***
 * 
 * 
 * @author wjl
 * @date 2019年7月26日
 */
public class LoggerTest {

	private static Logger logger = LoggerFactory.getLogger(LoggerTest.class);
	
	/****
	 *  getClass().getName() + '@' + Integer.toHexString(hashCode())
	 *  微信消息体封装
	 *  [
	 *	  toUserName=gh_3083ef78d39c
	 *	  fromUserName=o4sdr1Mh_gNNKuSOqwZt1rien_2I
	 *	  createTime=1562144031
	 *	  msgType=text
	 *	  msgId=22364525629945296
	 *	  content=hello world
	 *	]
	 */
	private static EventMessage msg = new EventMessage();
	
	@BeforeClass
    public static void setUp() throws Exception {
		msg.setToUserName("gh_3083ef78d39c");
		msg.setFromUserName("o4sdr1Mh_gNNKuSOqwZt1rien_2I");
		msg.setCreateTime(1562144031);
		msg.setMsg("text");
		msg.setMsgId("22364525629945296");
		msg.setContent("hello world");
		logger.info("EventMessage Initialization Ready... ");
    }

	@Test
	public void testToString(){
		logger.info("EventMessage body is:{}",msg);
	}
	
	
	@Test
	public void testReflection() throws IllegalArgumentException, IllegalAccessException{
		Field[] fields = msg.getClass().getDeclaredFields();
		for(int i = 0 ; i < fields.length ; i++) {
		    //设置是否允许访问，不是修改原来的访问权限修饰词。
			fields[i].setAccessible(true);
			if(Objects.nonNull(fields[i].get(msg))) {
				logger.info("EventMessage  field:[{}]_value:[{}]",fields[i].getName(),fields[i].get(msg));	
			}
		}
	}
	
	
	
	@Test
	public void testToStringBuilder(){
		logger.info("EventMessage body is:{}",ToStringBuilder.reflectionToString(msg,ToStringStyle.MULTI_LINE_STYLE));
	}
	
	/***
	 * 推荐
	 */
	@Test
	public void testMoreObjectsUtil(){
		logger.info(ReflectionToStringBuilder.toString(msg, ToStringStyle.MULTI_LINE_STYLE, true, true, true, null));
	}
	
}
