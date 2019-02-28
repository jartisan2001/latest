package com.github.jartisan.latest.dao.sonatype.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * @author: jalen
 * @date: 2016年2月26日 上午11:39:24
 */
public class Java implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private Integer favoriteId;

    /**
     * 
     */
    private String groupId;

    /**
     * 
     */
    private String artifactId;

    /**
     * 
     */
    private Date createdDate = new Date();

    /**
     * tutorial_java
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     * @return id 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return favorite_id 
     */
    public Integer getFavoriteId() {
        return favoriteId;
    }

    /**
     * 
     * @param favoriteId 
     */
    public void setFavoriteId(Integer favoriteId) {
        this.favoriteId = favoriteId;
    }

    /**
     * 
     * @return group_id 
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * 
     * @param groupId 
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId == null ? null : groupId.trim();
    }

    /**
     * 
     * @return artifact_id 
     */
    public String getArtifactId() {
        return artifactId;
    }

    /**
     * 
     * @param artifactId 
     */
    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId == null ? null : artifactId.trim();
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", favoriteId=").append(favoriteId);
        sb.append(", groupId=").append(groupId);
        sb.append(", artifactId=").append(artifactId);
        sb.append(", createdDate=").append(createdDate);
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
        Java other = (Java) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getFavoriteId() == null ? other.getFavoriteId() == null : this.getFavoriteId().equals(other.getFavoriteId()))
            && (this.getGroupId() == null ? other.getGroupId() == null : this.getGroupId().equals(other.getGroupId()))
            && (this.getArtifactId() == null ? other.getArtifactId() == null : this.getArtifactId().equals(other.getArtifactId()))
            && (this.getCreatedDate() == null ? other.getCreatedDate() == null : this.getCreatedDate().equals(other.getCreatedDate()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getFavoriteId() == null) ? 0 : getFavoriteId().hashCode());
        result = prime * result + ((getGroupId() == null) ? 0 : getGroupId().hashCode());
        result = prime * result + ((getArtifactId() == null) ? 0 : getArtifactId().hashCode());
        result = prime * result + ((getCreatedDate() == null) ? 0 : getCreatedDate().hashCode());
        return result;
    }
}