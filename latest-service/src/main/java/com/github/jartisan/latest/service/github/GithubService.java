package com.github.jartisan.latest.service.github;

import com.github.jartisan.latest.global.exception.BaseException;
/***
 * 
 * @author jalen
 *
 */
public interface GithubService {
	/***
	 * 每日统计
	 * @param checkType
	 * @throws BaseException
	 */
	void everyDaily(String checkType) throws BaseException;
	
	/***
	 * syncReadme 
	 * @throws Exception
	 */
	void syncReadme() throws Exception;
}
