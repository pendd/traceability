package com.hvisions.mes.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @author dpeng
 * @description 追溯历史接口
 * @date 2019-07-25 9:14
 */
public interface IRetrospectHistoryService {

    /**
     *  成品码追溯流程
     * @param goodsCode  成品码
     * @return           json对象
     */
    JSONObject queryGoodsRetrospect(String goodsCode);
}
