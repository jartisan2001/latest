package com.github.jartisan.latest.global.consts;


/**   
* @Title: TokenKey.java 
* @Package com.xdf.bling.cloud.global.consts 
* @Description: jwt token key
* @author jalen
* @date 2018年4月19日 上午11:03:58 
* @version V1.0   
*/
public class TokenKey {
	/***
	 * token的发行者
	 */
	public static final String ISSUER ="com.github.jartisan";
	/***
	 * 接收jwt的一方
	 */
	public static final String AUDIENCE ="client";
	/***
	 * 有效期设置 7 天
	 */
	public static final int EXPIRATION_TIME =7;
	
	/****
	 * SECRET
	 */
	public static final String SECRET ="jartisan-jalenei";
	
	/****
	 * USER_CODE
	 */
	public static final String USER_CODE ="usercode";
	
}
