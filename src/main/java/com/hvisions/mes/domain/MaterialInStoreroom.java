package com.hvisions.mes.domain;

import lombok.Data;

import java.util.Date;

/**
*
* 实体类
* @author mtyu
* @since 2019-03-05
*/
@Data
public class MaterialInStoreroom{
    //入库ID
    private Integer inId;
    //原料新编号
    private String materialCode;
    //原料批次ID
    private Integer batchId;
    //单位
    private String unit;
    //数量
    private Integer amount;
    //时间
    private Date createTime;
    //是否合格 0否1是
    private Integer qualified;
    //原始入库 0否1是
    private Integer isHistory;
    //是否已入库 0否1是
    private Integer isInStoreroom;
    //备注
    private String remark;
    //原料ID
    private Integer materialId;
    //原料名称
    private String materialName;
    //班组ID
    private Integer teamId;
    //班组
    private String teamName;
    //库房名称
    private String storeroomName;

}
