package com.github.jartisan.latest.global.utils;

import org.apache.commons.codec.digest.DigestUtils;
/**
 * @Title: MD5Util.java
 * @Package com.github.jartisan.latest.global.utils
 * @Description: MD5+SALT
 * @author wjl
 * @date 2018年8月28日 上午9:24:38
 * @version V1.0
 */
public class Md5Util {

    private static final String SALT = "jartisan.github.com";

    public static String encode(String password) {
        password = password + SALT;
        return DigestUtils.md5Hex(password);
    }

    public static void main(String[] args) {
        String md5Hex = DigestUtils.md5Hex("abel"+SALT);
        System.out.println(md5Hex);
        System.out.println(DigestUtils.md5Hex(SALT));


    }
}