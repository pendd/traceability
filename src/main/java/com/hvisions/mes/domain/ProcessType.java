package com.hvisions.mes.domain;

import java.util.Date;

public class ProcessType {

	private Integer fullId;

	private String fullName;

	private Date createTime;

	private Integer available;

	private Integer userId;

	private Date updateTime;

	private Integer updateUserId;

	public Integer getFullId() {
		return fullId;
	}

	public void setFullId(Integer fullId) {
		this.fullId = fullId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getAvailable() {
		return available;
	}

	public void setAvailable(Integer available) {
		this.available = available;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(Integer updateUserId) {
		this.updateUserId = updateUserId;
	}
}
