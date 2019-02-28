package com.github.jartisan.latest.global.utils;

/**   
* @Title: UserPrivacyUtil.java 
* @Package com.github.jartisan.latest.global.utils 
* @Description: 用户隐私信息遮挡工具类
* @author jalen
* @date 2018年9月11日 下午2:05:20 
* @version V1.0   
*/
public class UserPrivacyUtil {
	
	/***
	 * 手机号中间四位****隐藏 
	 * @param phone
	 * @return
	 */
	public static String phoneHide(String phone){
		String regex = "(\\d{3})\\d{4}(\\d{4})";
		String replacement = "$1****$2";
		return phone.replaceAll(regex,replacement);
	}
	
	
	public static void main(String[] args) {
		System.out.println(UserPrivacyUtil.phoneHide("18601036307"));
	}
}
