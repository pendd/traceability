package com.hvisions.mes.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.*;
import com.hvisions.mes.service.IQualityTraceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dpeng
 * @description 质量追溯控制器
 * @date 2019-09-27 18:16
 */
@RestController
@RequestMapping("/json/qualityTrace")
public class QualityTraceController {

    @Autowired
    private IQualityTraceService traceService;

    @GetMapping("/getEquipmentAoi")
    public Map<String,Object> getEquipmentAoi(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                              @RequestParam(value = "rows",defaultValue = "15") Integer rows,
                                              String workNumber) {
        Page<EquipmentAoi> result = traceService.queryEquipmentAoi(new Page<>(page, rows), workNumber);
        Map<String,Object> data = new HashMap<>(16);
        data.put("rows",result.getRecords());
        data.put("total",result.getTotal());
        return data;
    }

    @GetMapping("/getEquipmentAoiWrongDetail")
    public Map<String,Object> getEquipmentAoiWrongDetail(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                                         @RequestParam(value = "rows",defaultValue = "15") Integer rows,
                                                         String workNumber) {
        Page<EquipmentAoiWrongDetail> result = traceService.queryEquipmentAoiWrongDetail(new Page<>(page, rows), workNumber);
        Map<String,Object> data = new HashMap<>(16);
        data.put("rows",result.getRecords());
        data.put("total",result.getTotal());
        return data;
    }

    @GetMapping("/getEquipmentFct")
    public Map<String,Object> getEquipmentFct(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                              @RequestParam(value = "rows",defaultValue = "15") Integer rows,
                                              String workNumber) {
        Page<EquipmentFct> result = traceService.queryEquipmentFct(new Page<>(page, rows), workNumber);
        Map<String,Object> data = new HashMap<>(16);
        data.put("rows",result.getRecords());
        data.put("total",result.getTotal());
        return data;
    }

    @GetMapping("/getEquipmentIct")
    public Map<String,Object> getEquipmentIct(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                              @RequestParam(value = "rows",defaultValue = "15") Integer rows,
                                              String workNumber) {
        Page<EquipmentIct> result = traceService.queryEquipmentIct(new Page<>(page, rows), workNumber);
        Map<String,Object> data = new HashMap<>(16);
        data.put("rows",result.getRecords());
        data.put("total",result.getTotal());
        return data;
    }

    @GetMapping("/getEquipmentSpi")
    public Map<String,Object> getEquipmentSpi(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                              @RequestParam(value = "rows",defaultValue = "15") Integer rows,
                                              String workNumber) {
        Page<EquipmentSpi> result = traceService.queryEquipmentSpi(new Page<>(page, rows), workNumber);
        Map<String,Object> data = new HashMap<>(16);
        data.put("rows",result.getRecords());
        data.put("total",result.getTotal());
        return data;
    }

    @GetMapping("/getEquipmentTpjHeadS")
    public Map<String,Object> getEquipmentTpjHeadS(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                              @RequestParam(value = "rows",defaultValue = "15") Integer rows,
                                              String workNumber) {
        Page<EquipmentTpjHeadS> result = traceService.queryEquipmentTpjHeadS(new Page<>(page, rows), workNumber);
        Map<String,Object> data = new HashMap<>(16);
        data.put("rows",result.getRecords());
        data.put("total",result.getTotal());
        return data;
    }
}
