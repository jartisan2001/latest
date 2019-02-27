package com.github.jartisan.latest.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
/**
 * @ClassName: GitHubConfig
 * @Description:GitHubConfig
 * @author: wjl
 * @date: 2016年2月26日 上午11:39:24
 */
@Configuration
public class GitHubConfig {
	
	@Value("${github.userName}")
	private String userName;
	
	@Value("${github.passWord}")
	private String passWord;
	
	@Value("${github.localRepoPath}")
	private String localRepoPath;
	
	@Value("${github.fileName}")
	private String fileName;
	
	
	@Value("${github.token}")
	private String token;
	
	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getLocalRepoPath() {
		return localRepoPath;
	}

	public void setLocalRepoPath(String localRepoPath) {
		this.localRepoPath = localRepoPath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
	

}
