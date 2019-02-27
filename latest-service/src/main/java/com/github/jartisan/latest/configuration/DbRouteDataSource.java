package com.github.jartisan.latest.configuration;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


/**   
 * @ClassName:  DbRouteDataSource   
 * @Description:自定义的动态路由数据源 继承自 spring jdbc的AbstractRoutingDataSource   
 * @author: wjl 
 * @date:   2016年2月26日 上午11:35:56        
 */
public class DbRouteDataSource extends AbstractRoutingDataSource {
	
	private static final Logger log = LoggerFactory.getLogger(DbRouteDataSource.class);
	
    /**
     * 从库数据源个数-默认1
     */
    private int dataSourceNumber=2;
    /**
     * 用来从库进行轮询算法的记录值
     */
    private AtomicLong count = new AtomicLong(0);

    /**
     * DbRouteDataSource
     * @param dataSourceNumber
     */
    public DbRouteDataSource(Integer dataSourceNumber) {
        this.dataSourceNumber = dataSourceNumber;
    }
	
	/***
	 * 获取与数据源相关的key
	 * 此key是Map<String,DataSource> resolvedDataSources 中与数据源绑定的key值
	 * 在通过determineTargetDataSource获取目标数据源时使用
	 */
	@Override
    protected Object determineCurrentLookupKey() {
        String typekey = DbContextHolder.getDbType().name();
        // 为主时直接返回
        if (DbContextHolder.DbType.MASTER.name().equals(typekey)) {
            return DbContextHolder.getDbType();
        }
        // 为从库时进行轮询
        count.getAndIncrement();
        log.info("count:{}",count.get());
        long lookupKey = count.get()%dataSourceNumber;
        log.info("lookupKey:{}",lookupKey);
        return lookupKey;
    }
}
