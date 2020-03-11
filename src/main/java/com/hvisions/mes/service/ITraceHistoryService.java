package com.hvisions.mes.service;

import com.alibaba.fastjson.JSONObject;

/**
 * Author swang
 * Date 2019/3/23 16:43
 * Version 1.0
 * Description
 **/
public interface ITraceHistoryService {

    /**
     * 正向追溯
     * 根据成品编码获取所有相关环节的数据
     */
    JSONObject selectProductTraceByCode(String productCode);
    /**
     * 反向追溯
     * 根据原料编码获取所有相关环节的数据
     */
    JSONObject selectMaterialTraceByCode(String materialCode);

}
