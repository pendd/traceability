package com.hvisions.mes.domain;

import java.util.Date;

/**
*
* 项目名称：quality_traceability_system
* 类名称：Line
* 类描述：   产线实体类
* 创建人：宫岩
* 创建时间：2019年2月25日 下午1:39:03
*
*/
public class Line {
	//产线ID
	private Integer lineId;
	//产线名称
	private String lineName;
	//创建时间
	private Date createTime;
	//是否可用
	private Integer available;
	//设备IP
	private String lineIp;
	//负责人
	private Integer principal;
	//负责人账户
	private String principalName;
	//创建人
	private Integer userId;
	//更新时间
	private Date updateTime;
	//更新人
	private Integer updateUserId;

	private String account;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPrincipalName() {
		return principalName;
	}

	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
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

	public Integer getPrincipal() {
		return principal;
	}

	public void setPrincipal(Integer principal) {
		this.principal = principal;
	}

	public String getLineIp() {
		return lineIp;
	}

	public void setLineIp(String lineIp) {
		this.lineIp = lineIp;
	}

	public Integer getLineId() {
		return lineId;
	}
	public void setLineId(Integer lineId) {
		this.lineId = lineId;
	}
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
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


}
