package com.hvisions.mes.domain.PdaDomain;

import lombok.Data;

import java.util.Date;

@Data
public class PdaMaterialStoreroomHistory implements Comparable<PdaMaterialStoreroomHistory>{
    private String lineName;
    private String  produceNumber;
    private Integer detailId;
    private String  materialName;
    private Integer isArrive;
    private String supplierName;
    private String storeroomName;
    private String unit;
    private Integer amount;
    private String materialCode;
    private Integer userId;
    private Integer historyId;
    private Integer batchId;
    private Date createTime;

    /**
     *  自定义排序  按createTime 降序
     * @param o
     * @return
     */
    @Override
    public int compareTo(PdaMaterialStoreroomHistory o) {
        long value = o.getCreateTime().getTime() - this.createTime.getTime();
        if (value >= 0){
            return 1;
        }
        return -1;
    }
}

