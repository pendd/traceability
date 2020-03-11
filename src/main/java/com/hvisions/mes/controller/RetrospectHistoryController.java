package com.hvisions.mes.controller;

import com.alibaba.fastjson.JSONObject;
import com.hvisions.mes.service.IRetrospectHistoryService;
import springfox.documentation.annotations.ApiIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dpeng
 * @description 追溯控制器
 * @date 2019-07-25 9:15
 */
@RestController
@RequestMapping("/json/retrospect")
@ApiIgnore
public class RetrospectHistoryController {

    @Autowired
    private IRetrospectHistoryService historyService;

    @RequestMapping("/getGoodsRetrospect")
    public JSONObject getGoodsRetrospect(String goodsCode) {
        return historyService.queryGoodsRetrospect(goodsCode);
    }
}
