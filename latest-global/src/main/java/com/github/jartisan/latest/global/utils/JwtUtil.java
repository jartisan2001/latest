package com.github.jartisan.latest.global.utils;

import java.nio.charset.Charset;
import java.security.Key;
import java.util.Date;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jartisan.latest.global.consts.TokenKey;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @Title: JwtUtil.java
 * @Package com.github.jartisan.latest.global.utils
 * @Description: jwt utils
 * @author wjl
 * @date 2018年4月13日 上午11:54:27
 * @version V1.0
 */
public class JwtUtil {
	private static Logger logger = LoggerFactory.getLogger(JwtUtil.class);
	
	
	/***
	 * key 使用HS256算法和Secret:tutorial-secret生成signKey
	 * @param secret 
	 * @return
	 */
	private static Key getKeyInstance(String secret) {
		// We will sign our JavaWebToken with our ApiKey secret
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
		byte[] apiKeySecretBytes = Base64.encodeBase64((TokenKey.SECRET+secret).getBytes(Charset.defaultCharset()));
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
		return signingKey;
	}

	/***
	 * 使用HS512签名算法和生成的signingKey最终的Token
	 * @param claims claims中是有效payload
	 * @return
	 */
	public static String createJavaWebToken(Map<String, Object> claims,String secret) {
		
		// 生成JWT
        String jwt = Jwts.builder()
                // 保存权限（角色）"ROLE_ADMIN,AUTH_WRITE"
        		///该方法是在JWT中加入值为vaule的key字段
                .setClaims(claims)
                //token的发行者
                .setIssuer(TokenKey.ISSUER)
                //接收jwt的一方
                .setAudience(TokenKey.AUDIENCE)
                //jwt的签发时间
                .setIssuedAt(new Date())
                //定义在什么时间之前，该jwt都是不可用的.
                //.setNotBefore(DateUtils.addSeconds(new Date(), 2))
                // jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。
                //.setId(jti)
                // 用户名写入标题 jwt所面向的用户
               //.setSubject(auth.getName())
                // 有效期设置
                .setExpiration(DateUtils.addDays(new Date(),TokenKey.EXPIRATION_TIME))
                // 签名设置
                 .signWith(SignatureAlgorithm.HS512, getKeyInstance(secret))
                 .compact();
		return jwt; 
	}

	/***
	 * 解析Token
	 * @param jwt
	 * @return
	 */
	public static Map<String, Object> parserJavaWebToken(String jwt,String secret) {
			Map<String, Object> jwtClaims = Jwts.parser().setSigningKey(getKeyInstance(secret)).parseClaimsJws(jwt).getBody();
			logger.info("JWT parserJavaWebToken :{} ",jwtClaims);
			return jwtClaims;
	}

}
