package com.github.jartisan.latest.service.sonatype;

import java.util.List;

import com.github.jartisan.latest.dao.sonatype.entity.Favorite;
import com.github.jartisan.latest.global.entity.Dependency;
import com.github.jartisan.latest.global.exception.BaseException;
/***
 * 
 * @author jalen
 *
 */
public interface SonatypeService {
	/***
	 * 查询数据
	 * @param checkType
	 * @return
	 * @throws BaseException
	 */
    List<Favorite> listAll(String checkType) throws BaseException;
    
   /***
    * 检查发现
    * @param checkType
    * @return
    * @throws BaseException
    */
    void discover(String checkType) throws BaseException;
    
    /***
     * 保存Favorite
     * @param dependency
     * @return
     * @throws BaseException
     */
	Favorite saveFavorite(Dependency dependency) throws BaseException;
}
