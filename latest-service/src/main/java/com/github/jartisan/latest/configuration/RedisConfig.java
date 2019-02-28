package com.github.jartisan.latest.configuration;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * @ClassName: RedisConfig
 * @Description:RedisConfig
 * @author: jalen
 * @date: 2016年2月26日 上午11:39:24
 */
@Configuration
@EnableCaching
public class RedisConfig  extends CachingConfigurerSupport{
	 	@Bean(name="redisTemplate")
	    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
	        RedisTemplate<String, String> template = new RedisTemplate<>();
	        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
	        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
	        ObjectMapper om = new ObjectMapper();
	        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
	        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
	        jackson2JsonRedisSerializer.setObjectMapper(om);
	        template.setConnectionFactory(factory);
	        //key序列化方式
	        template.setKeySerializer(redisSerializer);
	        //value序列化
	        template.setValueSerializer(jackson2JsonRedisSerializer);
	        //value hashmap序列化
	        template.setHashValueSerializer(jackson2JsonRedisSerializer);

	        return template;
	    }
	  
	    
	    @Bean
	    public CacheManager cacheManager(RedisConnectionFactory factory) {
	    	// 生成一个默认配置，通过config对象即可对缓存进行自定义配置
	        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();  
	        
	        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
	        ObjectMapper om = new ObjectMapper();
	        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
	        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
	        jackson2JsonRedisSerializer.setObjectMapper(om);
	        // 设置缓存的默认过期时间，也是使用Duration设置
	        config = config.entryTtl(Duration.ofMinutes(1))     
	        		// 设置 key为string序列化
	                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
	                // 设置value为json序列化
	                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer))
	                // 不缓存空值
	                .disableCachingNullValues(); 

	        // 设置一个初始化的缓存空间set集合
	        Set<String> cacheNames =  new HashSet<>();
	        cacheNames.add("general");
	        cacheNames.add("my-redis-cache2");

	        // 对每个缓存空间应用不同的配置
	        Map<String, RedisCacheConfiguration> configMap = new HashMap<>(2);
	        configMap.put("general", config.entryTtl(Duration.ofSeconds(120)));
	        configMap.put("my-redis-cache2", config.entryTtl(Duration.ofSeconds(120)));
	        // 使用自定义的缓存配置初始化一个cacheManager
	        RedisCacheManager cacheManager = RedisCacheManager.builder(factory)
	        		 // 注意这两句的调用顺序，一定要先调用该方法设置初始化的缓存名，再初始化相关的配置
	                .initialCacheNames(cacheNames)  
	                .withInitialCacheConfigurations(configMap)
	                .build();
	        return cacheManager;
	    }
	    
	    
	/**
	 * 在使用@Cacheable时，如果不指定key，则使用找个默认的key生成器生成的key
	 */
	@Override
	@Bean
	public SimpleKeyGenerator keyGenerator() {
		return new SimpleKeyGenerator() {
			@Override
			public Object generate(Object target, Method method, Object... params) {
				StringBuilder sb = new StringBuilder();
				sb.append(target.getClass().getName());
				sb.append(".").append(method.getName());
				StringBuilder paramsSb = new StringBuilder();
				for (Object param : params) {
					// 如果不指定，默认生成包含到键值中
					if (param != null) {
						paramsSb.append(param.toString());
					}
				}
				if (paramsSb.length() > 0) {
					sb.append("_").append(paramsSb);
				}
				return sb.toString();
			}
		};
	}
	    
	/*
	 * @Bean(name="redisAtomicLong") public RedisAtomicLong
	 * redisAtomicLong(RedisTemplate<String, String> redisTemplate) {
	 * RedisAtomicLong redisAtomicLong =null; if(redisTemplate !=null) {
	 * redisAtomicLong = new
	 * RedisAtomicLong("atomickey",redisTemplate.getConnectionFactory()); } return
	 * redisAtomicLong; }
	 */
	    
}
