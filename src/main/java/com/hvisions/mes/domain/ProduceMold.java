package com.hvisions.mes.domain;

import java.util.Date;


public class ProduceMold {
    private Integer proAbrId;

    private String produceNumber;

    private Long historyId;

    private Date createTime;

    public Integer getProAbrId() {
        return proAbrId;
    }

    public void setProAbrId(Integer proAbrId) {
        this.proAbrId = proAbrId;
    }

    public String getProduceNumber() {
        return produceNumber;
    }

    public void setProduceNumber(String produceNumber) {
        this.produceNumber = produceNumber;
    }

    public Long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}