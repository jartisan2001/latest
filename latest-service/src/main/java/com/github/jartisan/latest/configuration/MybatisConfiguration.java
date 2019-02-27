package com.github.jartisan.latest.configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
/**
 * @ClassName: MybatisConfiguration
 * @Description:MybatisConfiguration
 * @author: wjl
 * @date: 2016年2月26日 上午11:39:24
 */
@Configuration
@AutoConfigureAfter({DataSourceConfiguration.class})
public class MybatisConfiguration extends MybatisAutoConfiguration {

    public MybatisConfiguration(MybatisProperties properties, ObjectProvider<Interceptor[]> interceptorsProvider,
			ResourceLoader resourceLoader, ObjectProvider<DatabaseIdProvider> databaseIdProvider,
			ObjectProvider<List<ConfigurationCustomizer>> configurationCustomizersProvider) {
		super(properties, interceptorsProvider, resourceLoader, databaseIdProvider, configurationCustomizersProvider);
	}


	private static Log logger = LogFactory.getLog(MybatisConfiguration.class);

    @Resource(name = "masterDataSource")
    private DataSource masterDataSource;
    @Resource(name = "slaveDataSource")
    private List<DataSource> slaveDataSource;

    @Bean
    @Override
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        logger.info("-------------------- 重载父类 sqlSessionFactory init ---------------------");
        return super.sqlSessionFactory(roundRobinDataSouceProxy());
    }


    @Bean
    public AbstractRoutingDataSource roundRobinDataSouceProxy(){
    	DbRouteDataSource proxy = new DbRouteDataSource(slaveDataSource.size());
        Map<Object,Object> targetDataResources = new HashMap<Object, Object>(slaveDataSource.size()+1);
        targetDataResources.put(DbContextHolder.DbType.MASTER,masterDataSource);
        for(int i=0;i<slaveDataSource.size();i++) {
            targetDataResources.put(i,slaveDataSource.get(i));
        }
        proxy.setDefaultTargetDataSource(masterDataSource);
        proxy.setTargetDataSources(targetDataResources);
        proxy.afterPropertiesSet();
        return proxy;
    }

    
    /**
                *  配置事务管理器
     */
    @Bean
    public DataSourceTransactionManager transactionManager(AbstractRoutingDataSource dataSource) throws Exception {
        logger.info("-------------------- 重载父类 transactionManager init ---------------------");
        return new DataSourceTransactionManager(dataSource);
    }
}
