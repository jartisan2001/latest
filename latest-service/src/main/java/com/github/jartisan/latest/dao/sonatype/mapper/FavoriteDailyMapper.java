package com.github.jartisan.latest.dao.sonatype.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.github.jartisan.latest.dao.sonatype.entity.FavoriteDaily;
/**
 * @author: wjl
 * @date: 2016年2月26日 上午11:39:24
 */
@Mapper
public interface FavoriteDailyMapper {
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
    int insert(FavoriteDaily record);
    /***
     * 插入记录
     * @param record
     * @return
     */
    int insertSelective(FavoriteDaily record);
    /***
     * 根据主键查询记录
     * @param id
     * @return
     */
    FavoriteDaily selectByPrimaryKey(Integer id);
    /***
     * 更新记录
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(FavoriteDaily record);
    /***
     * 更新记录
     * @param record
     * @return
     */
    int updateByPrimaryKey(FavoriteDaily record);
}