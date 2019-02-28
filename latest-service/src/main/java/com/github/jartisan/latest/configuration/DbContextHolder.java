package com.github.jartisan.latest.configuration;
/**
 * @ClassName: RouteHolder
 * @Description:保存当前线程数据源的key
 * @author: jalen
 * @date: 2016年2月26日 上午11:39:24
 */
public class DbContextHolder {
	public enum DbType{
		/***
		 * 主数据源
		 */
        MASTER,
        /***
         * 从数据源
         */
        SLAVE
    }

    private static final ThreadLocal<DbType> CONTEXT_HOLDER = new ThreadLocal<>();

    public static void setDbType(DbType dbType){
        if(dbType==null){
        	throw new NullPointerException();
        }else{
        	CONTEXT_HOLDER.set(dbType);	
        }
    }

    public static DbType getDbType(){
        return CONTEXT_HOLDER.get()==null?DbType.MASTER:CONTEXT_HOLDER.get();
    }

    public static void clearDbType(){
        CONTEXT_HOLDER.remove();
    }

}
