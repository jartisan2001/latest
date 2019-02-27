package com.github.jartisan.latest.configuration;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
/**
 * @ClassName: SlaveDataSourceInterceptor
 * @Description:从库、只读库 切面
 * @author: wjl
 * @date: 2016年2月26日 上午11:39:24
 */
@Aspect
@Component
public class SlaveDataSourceInterceptor implements Ordered {

    public static final Logger logger = LoggerFactory.getLogger(SlaveDataSourceInterceptor.class);

    @Around("@annotation(slaveDataSource)")
    public Object proceed(ProceedingJoinPoint proceedingJoinPoint,SlaveDataSource slaveDataSource) throws Throwable {
        try {
            logger.debug("set database connection to read only");
            DbContextHolder.setDbType(DbContextHolder.DbType.SLAVE);
            Object result = proceedingJoinPoint.proceed();
            return result;
        }finally {
            DbContextHolder.clearDbType();
            logger.debug("restore database connection");
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
