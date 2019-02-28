package com.github.jartisan.latest.configuration;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;
/**
 * @ClassName: DataSourceConfiguration
 * @Description:数据源配置
 * @author: jalen
 * @date: 2016年2月26日 上午11:39:24
 */
@Configuration
@EnableTransactionManagement
public class DataSourceConfiguration {
	@Value("${druid.type}")
    private Class<? extends DataSource> dataSourceType;


    @Bean(name = "masterDataSource")
    @Primary
    @ConfigurationProperties(prefix = "druid.master")
    public DataSource masterDataSource(){
        return DataSourceBuilder.create().type(dataSourceType).build();
    }

    @Bean(name = "slave1DataSource")
    @ConfigurationProperties(prefix = "druid.slave1")
    public DataSource slave1DataSource(){
        return DataSourceBuilder.create().type(dataSourceType).build();
    }
    
    @Bean(name = "slave2DataSource")
    @ConfigurationProperties(prefix = "druid.slave2")
    public DataSource slave2DataSource(){
        return DataSourceBuilder.create().type(dataSourceType).build();
    }
    
    @Bean(name = "slaveDataSource")
    public List<DataSource> slaveDataSource() {
        List<DataSource> dataSources = new ArrayList<>(2);
        dataSources.add(slave1DataSource());
        dataSources.add(slave2DataSource());
        return dataSources;
    }
}
