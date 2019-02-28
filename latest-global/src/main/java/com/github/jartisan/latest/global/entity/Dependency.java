package com.github.jartisan.latest.global.entity;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/***
 * 
 * Dependency
 * 
 * @author jalen
 *
 */


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Dependency {
	/**
	 * groupId
	 */
	@XmlAttribute
	@NotBlank
	private String groupId;
	/**
	 * artifactId
	 */
	@XmlAttribute
	@NotBlank
	private String artifactId;
	/**
	 * version
	 */
	@XmlAttribute
	@NotBlank
	private String version;
	
	/**
	 * 名称
	 * name
	 */
	@XmlAttribute
	@NotBlank
	private String name;
	
	/**
	 * githubApi
	 */
	@XmlAttribute
	@NotBlank
	private String githubApi;
	
	/**
	 * 语言
	 * language
	 */
	@XmlAttribute
	@NotBlank
	private String language;
	
	/**
	 * 检查更新方式(M-maven中央仓库方式，G-github方式)
	 */
	@XmlAttribute
	@NotBlank
	private String checkType;
	
	/***
	 * 更新时间戳
	 */
	private Date updatedTimestamp;
	
	

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getArtifactId() {
		return artifactId;
	}

	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGithubApi() {
		return githubApi;
	}

	public void setGithubApi(String githubApi) {
		this.githubApi = githubApi;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}
	
	public Date getUpdatedTimestamp() {
		if (this.updatedTimestamp != null) {
			return new Date(this.updatedTimestamp.getTime());
		} else {
			return null;
		}
	}

	public void setUpdatedTimestamp(Date updatedTimestamp) {
		if (updatedTimestamp != null) {
			this.updatedTimestamp = (Date) updatedTimestamp.clone();
		} else {
			this.updatedTimestamp = null;
		}
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((artifactId == null) ? 0 : artifactId.hashCode());
		result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}			
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Dependency other = (Dependency) obj;
		if (artifactId == null) {
			if (other.artifactId != null) {
				return false;
			}
		} else if (!artifactId.equals(other.artifactId)) {
			return false;
		}
		if (groupId == null) {
			if (other.groupId != null) {
				return false;
			}
		} else if (!groupId.equals(other.groupId)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (version == null) {
			if (other.version != null) {
				return false;
			}
		} else if (!version.equals(other.version)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.NO_CLASS_NAME_STYLE);
	}


}
