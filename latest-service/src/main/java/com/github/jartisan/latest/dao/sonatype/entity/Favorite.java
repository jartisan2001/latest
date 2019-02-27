package com.github.jartisan.latest.dao.sonatype.entity;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
/**
 * @author: wjl
 * @date: 2016年2月26日 上午11:39:24
 */

import org.apache.commons.lang3.StringUtils;
public class Favorite implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 所属语言
     */
    private String language;

    /**
     * 是否删除 0-不删除，1-删除
     */
    private Integer deleted;

    /**
     * 最新版本
     */
    private String lastVersion;

    /**
     * 检查更新方式(M-maven中央仓库方式，G-github方式)
     */
    private String checkType;

    /**
     * github_api地址
     */
    private String githubApi;

    /**
     * star_count
     */
    private Integer starCount;

    /**
     * watcher_count
     */
    private Integer watcherCount;

    /**
     * fork_count
     */
    private Integer forkCount;

    /**
     * gmt_create
     */
    private Date createdDate = new Date();

    /**
     * gmt_modified
     */
    private Date modifieDate;

    /**
     * tutorial_favorite
     */
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     * @return id 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 主键
     * @param id 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 名称
     * @return name 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 名称
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 所属语言
     * @return language 所属语言
     */
    public String getLanguage() {
        return language;
    }

    /**
     * 所属语言
     * @param language 所属语言
     */
    public void setLanguage(String language) {
        this.language = language == null ? null : language.trim();
    }

    /**
     * 是否删除 0-不删除，1-删除
     * @return deleted 是否删除 0-不删除，1-删除
     */
    public Integer getDeleted() {
        return deleted;
    }

    /**
     * 是否删除 0-不删除，1-删除
     * @param deleted 是否删除 0-不删除，1-删除
     */
    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    /**
     * 最新版本
     * @return last_version 最新版本
     */
    public String getLastVersion() {
        return lastVersion;
    }

    /**
     * 最新版本
     * @param lastVersion 最新版本
     */
    public void setLastVersion(String lastVersion) {
        this.lastVersion = lastVersion == null ? null : lastVersion.trim();
    }

    /**
     * 检查更新方式(M-maven中央仓库方式，G-github方式)
     * @return check_type 检查更新方式(M-maven中央仓库方式，G-github方式)
     */
    public String getCheckType() {
        return checkType;
    }

    /**
     * 检查更新方式(M-maven中央仓库方式，G-github方式)
     * @param checkType 检查更新方式(M-maven中央仓库方式，G-github方式)
     */
    public void setCheckType(String checkType) {
        this.checkType = checkType == null ? null : checkType.trim();
    }

    /**
     * github_api地址
     * @return github_api github_api地址
     */
    public String getGithubApi() {
        return githubApi;
    }

    /**
     * github_api地址
     * @param githubApi github_api地址
     */
    public void setGithubApi(String githubApi) {
        this.githubApi = githubApi == null ? null : githubApi.trim();
    }
    
    
    /**
     * 项目全名
     * @return 项目全名
     * @throws MalformedURLException 
     */
    public String getFullName() throws MalformedURLException {
    	URL url = new URL(this.githubApi);
        return StringUtils.remove(url.getPath(), "/repos/");
    }

    
    	

    /**
     * star_count
     * @return star_count star_count
     */
    public Integer getStarCount() {
        return starCount;
    }

    /**
     * star_count
     * @param starCount star_count
     */
    public void setStarCount(Integer starCount) {
        this.starCount = starCount;
    }

    /**
     * watcher_count
     * @return watcher_count watcher_count
     */
    public Integer getWatcherCount() {
        return watcherCount;
    }

    /**
     * watcher_count
     * @param watcherCount watcher_count
     */
    public void setWatcherCount(Integer watcherCount) {
        this.watcherCount = watcherCount;
    }

    /**
     * fork_count
     * @return fork_count fork_count
     */
    public Integer getForkCount() {
        return forkCount;
    }

    /**
     * fork_count
     * @param forkCount fork_count
     */
    public void setForkCount(Integer forkCount) {
        this.forkCount = forkCount;
    }

    public Date getCreatedDate() {
		if (this.createdDate != null) {
			return new Date(this.createdDate.getTime());
		} else {
			return null;
		}
	}

	public void setCreatedDate(Date createdDate) {
		if (createdDate != null) {
			this.createdDate = (Date) createdDate.clone();
		} else {
			this.createdDate = null;
		}
	}

    /**
     * gmt_modified
     * @return gmt_modified gmt_modified
     */
    public Date getModifieDate() {
    	if (this.modifieDate != null) {
			return new Date(this.modifieDate.getTime());
		} else {
			return null;
		}
    }

    /**
     * gmt_modified
     * @param modifieDate gmt_modified
     */
    public void setModifieDate(Date modifieDate) {
        if (modifieDate != null) {
			this.modifieDate = (Date) modifieDate.clone();
		} else {
			this.modifieDate = null;
		}
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", language=").append(language);
        sb.append(", deleted=").append(deleted);
        sb.append(", lastVersion=").append(lastVersion);
        sb.append(", checkType=").append(checkType);
        sb.append(", githubApi=").append(githubApi);
        sb.append(", starCount=").append(starCount);
        sb.append(", watcherCount=").append(watcherCount);
        sb.append(", forkCount=").append(forkCount);
        sb.append(", createdDate=").append(createdDate);
        sb.append(", modifieDate=").append(modifieDate);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Favorite other = (Favorite) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getLanguage() == null ? other.getLanguage() == null : this.getLanguage().equals(other.getLanguage()))
            && (this.getDeleted() == null ? other.getDeleted() == null : this.getDeleted().equals(other.getDeleted()))
            && (this.getLastVersion() == null ? other.getLastVersion() == null : this.getLastVersion().equals(other.getLastVersion()))
            && (this.getCheckType() == null ? other.getCheckType() == null : this.getCheckType().equals(other.getCheckType()))
            && (this.getGithubApi() == null ? other.getGithubApi() == null : this.getGithubApi().equals(other.getGithubApi()))
            && (this.getStarCount() == null ? other.getStarCount() == null : this.getStarCount().equals(other.getStarCount()))
            && (this.getWatcherCount() == null ? other.getWatcherCount() == null : this.getWatcherCount().equals(other.getWatcherCount()))
            && (this.getForkCount() == null ? other.getForkCount() == null : this.getForkCount().equals(other.getForkCount()))
            && (this.getCreatedDate() == null ? other.getCreatedDate() == null : this.getCreatedDate().equals(other.getCreatedDate()))
            && (this.getModifieDate() == null ? other.getModifieDate() == null : this.getModifieDate().equals(other.getModifieDate()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getLanguage() == null) ? 0 : getLanguage().hashCode());
        result = prime * result + ((getDeleted() == null) ? 0 : getDeleted().hashCode());
        result = prime * result + ((getLastVersion() == null) ? 0 : getLastVersion().hashCode());
        result = prime * result + ((getCheckType() == null) ? 0 : getCheckType().hashCode());
        result = prime * result + ((getGithubApi() == null) ? 0 : getGithubApi().hashCode());
        result = prime * result + ((getStarCount() == null) ? 0 : getStarCount().hashCode());
        result = prime * result + ((getWatcherCount() == null) ? 0 : getWatcherCount().hashCode());
        result = prime * result + ((getForkCount() == null) ? 0 : getForkCount().hashCode());
        result = prime * result + ((getCreatedDate() == null) ? 0 : getCreatedDate().hashCode());
        result = prime * result + ((getModifieDate() == null) ? 0 : getModifieDate().hashCode());
        return result;
    }
}