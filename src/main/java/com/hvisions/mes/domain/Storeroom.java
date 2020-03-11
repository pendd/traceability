package com.hvisions.mes.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Storeroom {
	//库房ID
	private Integer storeroomId;
	//库房名称
	private String storeroomName;
	//创建时间
	private Date createTime;
	//是否可用
	private Integer available;
	//库房类型
	private Integer type;
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
	/** 库房编码 */
	private String storeroomCode;

	private String account;
}
