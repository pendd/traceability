package com.hvisions.mes.controller;

import com.alibaba.fastjson.JSONObject;
import com.hvisions.mes.service.ITraceHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Author swang
 * Date 2019/3/27 9:59
 * Version 1.0
 * Description
 **/
@RestController
@RequestMapping("/json/traceHistory")
@ApiIgnore
public class TraceHistoryController extends BaseController{

    @Autowired
    private ITraceHistoryService traceHistoryService;

    @PostMapping(value = "/productTrace")
    public JSONObject productTrace (@RequestParam("productCode") String productCode) {
        if(!StringUtils.isEmpty(productCode)){
            return traceHistoryService.selectProductTraceByCode(productCode);
        }else return null;
    }

    @PostMapping(value = "/materialTrace")
    public JSONObject materialTrace (@RequestParam("materialCode") String materialCode) {
        if(!StringUtils.isEmpty(materialCode)){
            return traceHistoryService.selectMaterialTraceByCode(materialCode);
        }else return null;
    }

}
