package com.github.jartisan.latest.service.strategy;

/***
 * 定义接口。type用来路由具体的Handler实现
 *@author wjl
 */
public interface Handler {
	/***
	 * getType
	 * @return
	 */
	String getType();

	/***
	 * execute
	 */
	void execute();
}
