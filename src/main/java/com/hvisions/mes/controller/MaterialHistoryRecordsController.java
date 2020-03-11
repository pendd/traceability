package com.hvisions.mes.controller;

import java.util.HashMap;
import java.util.Map;

import springfox.documentation.annotations.ApiIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.controller.vo.MaterialInHistory;
import com.hvisions.mes.controller.vo.MaterialOutHistory;
import com.hvisions.mes.service.IMaterialHistoryRecordsService;

@RestController
@RequestMapping("/json/MaterialHistoryRecords")
@ApiIgnore
public class MaterialHistoryRecordsController {
    @Autowired
    private IMaterialHistoryRecordsService iMaterialHistoryRecordsService;
    @RequestMapping(value = "/MaterialInHistoryRecordsListPage",method = RequestMethod.POST)
    public Map<String,Object> getAllMaterialInHistoryRecords(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "15") Integer rows,
            @RequestParam(value = "materialCode", required = false) String materialCode){
        Page<MaterialInHistory> data = iMaterialHistoryRecordsService.queryAllMerialInHistoryRecords(new Page<MaterialInHistory>(page,rows),materialCode);
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("total", data.getTotal());
        result.put("rows", data.getRecords());
        return result;
    }

    @RequestMapping(value = "/MaterialOutHistoryRecordsListPage",method = RequestMethod.POST)
    public Map<String,Object> getAllMaterialOutHistoryRecords(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "15") Integer rows,
            @RequestParam(value = "materialCode", required = false) String materialCode){
        Page<MaterialOutHistory> data = iMaterialHistoryRecordsService.queryAllMerialOutHistoryRecords(new Page<MaterialOutHistory>(page,rows),materialCode);
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("total", data.getTotal());
        result.put("rows", data.getRecords());
        return result;
    }
}
