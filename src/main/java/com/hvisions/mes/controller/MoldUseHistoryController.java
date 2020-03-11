package com.hvisions.mes.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import springfox.documentation.annotations.ApiIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.controller.vo.MoldUseHistory;
import com.hvisions.mes.domain.Equipment;
import com.hvisions.mes.domain.Line;
import com.hvisions.mes.service.IEquipmentService;
import com.hvisions.mes.service.ILineService;
import com.hvisions.mes.service.IMoldUseHistoryService;

@RestController
@RequestMapping("/json/MoldUseHistoryRecords")
@ApiIgnore
public class MoldUseHistoryController {
    @Autowired
    private IMoldUseHistoryService iMoldUseHistoryService;
    @Autowired
    private ILineService iLineService;
    @Autowired
    private IEquipmentService iEquipmentService;
    @RequestMapping(value = "/MoldUseHistoryRecordsListPage",method = RequestMethod.POST)
    public Map<String,Object> getAllMoldUseHistoryRecords(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "15") Integer rows,
            @RequestParam(value = "lineId", required = false) Integer lineId,
            @RequestParam(value = "equipmentId", required = false) Integer equipmentId){
        Page<MoldUseHistory> data = iMoldUseHistoryService.queryAllMoldUSeHistoryRecords(new Page<MoldUseHistory>(page,rows),lineId,equipmentId);
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("total", data.getTotal());
        result.put("rows", data.getRecords());
        return result;
    }
    @RequestMapping(value = "/LineList",method = RequestMethod.POST)
    @ResponseBody
    public String getAllLine(){
        List<Line> lineList = iLineService.showLine();
        String data = JSON.toJSONString(lineList);
        return data;
    }
    @RequestMapping(value = "/EquipmentList",method = RequestMethod.POST)
    @ResponseBody
    public String getAllEquipment( @RequestParam(value = "lineIdData", required = false) String lineIdData){
        Integer lineId = Integer.parseInt(lineIdData);
        List<Equipment> eqptList = iEquipmentService.queryEqptByLine(lineId);
        String data = JSON.toJSONString(eqptList);
        return data;
    }
}
