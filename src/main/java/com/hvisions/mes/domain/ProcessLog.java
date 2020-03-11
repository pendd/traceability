package com.hvisions.mes.domain;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Date;

@Data
public class ProcessLog {

    private Long logId;

    private Date createTime;

    private Long userId;

    private Date updateTime;

    private Long updateUserId;
    /**扫码物品编码*/
    private String processCode;
    /**重点json数据 */
    private Object processData;

    public JSONObject getProcessDataJSON(){
        if(StringUtils.isEmpty(processData)) return null;
        return JSON.parseObject(this.processData.toString());
    }

}