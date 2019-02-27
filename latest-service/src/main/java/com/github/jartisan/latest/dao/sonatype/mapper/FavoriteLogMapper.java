package com.github.jartisan.latest.dao.sonatype.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.github.jartisan.latest.dao.sonatype.entity.FavoriteLog;
import com.github.jartisan.latest.dao.sonatype.query.LatestQuery;

/**
 * @author: wjl
 * @date: 2016年2月26日 上午11:39:24
 */
@Mapper
public interface FavoriteLogMapper {
	/**
	 * 根据主键删除记录
	 * @param id
	 * @return
	 */
    int deleteByPrimaryKey(Integer id);
    /***
     * 插入记录
     * @param record
     * @return
     */


    int insert(FavoriteLog record);
    /***
     * 插入记录
     * @param record
     * @return
     */

    int insertSelective(FavoriteLog record);
    /***
     * 根据主键查询记录
     * @param id
     * @return
     */
    FavoriteLog selectByPrimaryKey(Integer id);
    /***
     * 更新记录
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(FavoriteLog record);
    /***
     * 更新记录
     * @param record
     * @return
     */
    int updateByPrimaryKey(FavoriteLog record);
    
    /***
     * 获取上一次更新记录
     * @param favoriteId
     * @return
     */
    FavoriteLog selectByLastVersion(Integer favoriteId);
    
    
    /***
     * 获取所有版本
     * @param favoriteId
     * @return
     */
    List<String> selectByVersions(Integer favoriteId);
    
    /***
     * 当天更新库
     * @param checkType
     * @return
     */
    List<LatestQuery> listLatestByToday(String checkType);
    
    /***
     * 最近3天更新库
     * @param checkType
     * @return
     */
    List<LatestQuery> listLatestBy3day(String checkType);
    
}