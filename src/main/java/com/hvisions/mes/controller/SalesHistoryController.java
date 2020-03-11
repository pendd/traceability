package com.hvisions.mes.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.controller.vo.SalesHistory;
import com.hvisions.mes.service.ISalesHistoryService;
import springfox.documentation.annotations.ApiIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/json/SalesHistoryRecords")
@ApiIgnore
public class SalesHistoryController {
    @Autowired
    private ISalesHistoryService iSalesHistoryService;
    @RequestMapping(value = "/salesHistoryRecordsListPage",method = RequestMethod.POST)
    public Map<String,Object> getAllSalesHistoryRecords(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "15") Integer rows,
            @RequestParam(value = "companyId", required = false) Integer companyId){
        Page<SalesHistory> data = iSalesHistoryService.querySalesHistory(new Page<SalesHistory>(page,rows), companyId);
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("total", data.getTotal());
        result.put("rows", data.getRecords());
        return result;
    }

    /**
     *  获取地图中所需的 最近销售量  销售总量  最近交易时间
     * @return
     */
    @RequestMapping(value = "/getSalesMessage")
    public List<SalesHistory> getSalesMessage(){
        return iSalesHistoryService.querySalesMessage();
    }
}
