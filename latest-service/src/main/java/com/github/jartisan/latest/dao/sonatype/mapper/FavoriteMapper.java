package com.github.jartisan.latest.dao.sonatype.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.github.jartisan.latest.dao.sonatype.entity.Favorite;

/**
 * @author: wjl
 * @date: 2016年2月26日 上午11:39:24
 */
@Mapper
public interface FavoriteMapper {
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

    int insert(Favorite record);
    /***
     * 插入记录
     * @param record
     * @return
     */

    int insertSelective(Favorite record);
    /***
     * 根据主键查询记录
     * @param id
     * @return
     */

    Favorite selectByPrimaryKey(Integer id);
    /***
     * 更新记录
     * @param record
     * @return
     */

    int updateByPrimaryKeySelective(Favorite record);
    /***
     * 更新记录
     * @param record
     * @return
     */

    int updateByPrimaryKey(Favorite record);
   /***
    * 按checkType查询所有排除已删除
    * @param checkType
    * @return
    */
	List<Favorite> listAll(String checkType);
}