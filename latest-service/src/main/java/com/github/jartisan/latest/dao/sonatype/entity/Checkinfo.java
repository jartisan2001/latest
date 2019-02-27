package com.github.jartisan.latest.dao.sonatype.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

/***
 * 
 * @author ejb3
 *
 */
public class Checkinfo implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 关注ID
     */
    private Integer favoriteId;

    /**
     * 更新检查类型
     */
    private String checkType;

    /**
     * 创建时间
     */
    private Date createdDate = new Date();

    /**
     * tutorial_checkinfo
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
     * 关注ID
     * @return favorite_id 关注ID
     */
    public Integer getFavoriteId() {
        return favoriteId;
    }

    /**
     * 关注ID
     * @param favoriteId 关注ID
     */
    public void setFavoriteId(Integer favoriteId) {
        this.favoriteId = favoriteId;
    }

    /**
     * 更新检查类型
     * @return check_type 更新检查类型
     */
    public String getCheckType() {
        return checkType;
    }

    /**
     * 更新检查类型
     * @param checkType 更新检查类型
     */
    public void setCheckType(String checkType) {
        this.checkType = checkType == null ? null : checkType.trim();
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
		return ToStringBuilder.reflectionToString(this);
	}

}