package com.github.jartisan.latest.dao.sonatype.entity;

import java.io.Serializable;
import java.util.Date;
/***
 * FavoriteDaily
 * @author jalen
 *
 */

public class FavoriteDaily implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * favorite_id
     */
    private Integer favoriteId;

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
     * 创建时间
     */
    private Date createdDate = new Date();

    /**
     * tutorial_favorite_daily
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
     * favorite_id
     * @return favorite_id favorite_id
     */
    public Integer getFavoriteId() {
        return favoriteId;
    }

    /**
     * favorite_id
     * @param favoriteId favorite_id
     */
    public void setFavoriteId(Integer favoriteId) {
        this.favoriteId = favoriteId;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", favoriteId=").append(favoriteId);
        sb.append(", starCount=").append(starCount);
        sb.append(", watcherCount=").append(watcherCount);
        sb.append(", forkCount=").append(forkCount);
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
        FavoriteDaily other = (FavoriteDaily) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getFavoriteId() == null ? other.getFavoriteId() == null : this.getFavoriteId().equals(other.getFavoriteId()))
            && (this.getStarCount() == null ? other.getStarCount() == null : this.getStarCount().equals(other.getStarCount()))
            && (this.getWatcherCount() == null ? other.getWatcherCount() == null : this.getWatcherCount().equals(other.getWatcherCount()))
            && (this.getForkCount() == null ? other.getForkCount() == null : this.getForkCount().equals(other.getForkCount()))
            && (this.getCreatedDate() == null ? other.getCreatedDate() == null : this.getCreatedDate().equals(other.getCreatedDate()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getFavoriteId() == null) ? 0 : getFavoriteId().hashCode());
        result = prime * result + ((getStarCount() == null) ? 0 : getStarCount().hashCode());
        result = prime * result + ((getWatcherCount() == null) ? 0 : getWatcherCount().hashCode());
        result = prime * result + ((getForkCount() == null) ? 0 : getForkCount().hashCode());
        result = prime * result + ((getCreatedDate() == null) ? 0 : getCreatedDate().hashCode());
        return result;
    }
}