package com.github.jartisan.latest.global.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

/***
 * 
 * @author  wjl
 * @date 2017/10/16
 */
public class AbstractPersistable <PK extends Serializable> implements Persistable<PK> {

	private static final long serialVersionUID = 1L;
	
	private PK id;
	
	
	/***
	 *实体创建时间
	 *@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8") 
	 */
	private Date createDate;
	
	/***
	 *实体修改时间
	 *@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8") 
	 */
	private Date modifieDate;
	@Override
	public PK getId() {
		return id;
	}

	protected void setId(final PK id) {
		this.id = id;
	}

	
	public Date getCreateDate() {
		if (this.createDate != null) {
			return new Date(this.createDate.getTime());
		} else {
			return null;
		}
	}

	public void setCreateDate(Date createDate) {
		if (createDate != null) {
			this.createDate = (Date) createDate.clone();
		} else {
			this.createDate = null;
		}
	}
	

	public Date getModifieDate() {
		if (this.modifieDate != null) {
			return new Date(this.modifieDate.getTime());
		} else {
			return null;
		}
	}

	public void setModifieDate(Date modifieDate) {
		if (modifieDate != null) {
			this.modifieDate = (Date) modifieDate.clone();
		} else {
			this.modifieDate = null;
		}
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
