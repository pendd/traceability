package com.hvisions.mes.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.controller.vo.MaterialStoreroomHistoryVo;
import com.hvisions.mes.service.IMaterialStoreroomHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/json/materialStoreroomHistory")
@ApiIgnore
public class MaterialStoreroomHistoryController {
    @Autowired
    private IMaterialStoreroomHistoryService mshService;

    /**
     *  原料出入库历史
     * @param page            当前页
     * @param rows            每页显示数量
     * @param inOutType       0入库  1出库
     * @return
     */
    @RequestMapping(value = "/getAllMaterialStoreroomHistory")
    public Map<String,Object> getAllMaterialStoreroomHistory(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "15") Integer rows,
            @RequestParam(value = "startTime",required = false) String startTime,
            @RequestParam(value = "endTime",required = false) String endTime,
            @RequestParam(value = "workNumber",required = false) String workNumber,
            @RequestParam(value = "inOutType",required = false) Integer inOutType){
        Page<MaterialStoreroomHistoryVo> data = mshService.queryAllMaterialStoreroomHistory(new Page<>(page, rows), workNumber,startTime,endTime, inOutType);
        Map<String,Object> result = new HashMap<>(16);
        result.put("total",data.getTotal());
        result.put("rows",data.getRecords());
        return result;
    }
}
