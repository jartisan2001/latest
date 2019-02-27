package com.github.jartisan.latest.dao.sonatype.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.github.jartisan.latest.dao.sonatype.entity.Checkinfo;

/***
 * 
 * @author ejb3
 *
 */
@Mapper
public interface CheckinfoMapper {
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
    int insert(Checkinfo record);
    /***
     * 插入记录
     * @param record
     * @return
     */
    int insertSelective(Checkinfo record);
    /***
     * 根据主键查询记录
     * @param id
     * @return
     */
    Checkinfo selectByPrimaryKey(Integer id);
    /***
     * 更新记录
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Checkinfo record);
    /***
     * 更新记录
     * @param record
     * @return
     */
    int updateByPrimaryKey(Checkinfo record);
    
    /***
     * favoriteId
     * @param favoriteId
     * @return
     */
    Checkinfo selectByFavoriteId(Integer favoriteId);
}