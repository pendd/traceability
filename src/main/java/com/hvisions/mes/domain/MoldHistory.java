package com.hvisions.mes.domain;



import java.util.Date;


public class MoldHistory {
    private Integer historyId;

    private Long moldId;

    private Long lineId;

    private Long equpmentId;

    private Long userId;

    private Date createTime;

    private String remark;

    public Integer getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Integer historyId) {
        this.historyId = historyId;
    }

    public Long getMoldId() {
        return moldId;
    }

    public void setMoldId(Long moldId) {
        this.moldId = moldId;
    }

    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    public Long getEqupmentId() {
        return equpmentId;
    }

    public void setEqupmentId(Long equpmentId) {
        this.equpmentId = equpmentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}