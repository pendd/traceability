package com.hvisions.mes.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.controller.vo.LineStoreroomHistoryVo;
import com.hvisions.mes.service.ILineStoreroomHistoryService;
import springfox.documentation.annotations.ApiIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dpeng
 * @create 2019-03-13 16:57
 */
@RestController
@RequestMapping("/json/LineStoreroomHistory")
@ApiIgnore
public class LineStoreroomHistoryController {

    @Autowired
    private ILineStoreroomHistoryService lshService;

    /**
     *
     * @param page              当前页
     * @param rows              每页显示数量
     * @param lineStoreroomId   线边库编号
     * @return
     */
    @RequestMapping(value = "/lineStoreroomOutHistoryListPage",method = RequestMethod.POST)
    public Map<String,Object> LineStoreroomOutHistoryListPage(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "15") Integer rows,
            @RequestParam(value = "lineStoreroomId",required = false) Long lineStoreroomId){
        Page<LineStoreroomHistoryVo> data = lshService.queryLineStoreroomOutHistory(new Page<LineStoreroomHistoryVo>(page, rows), lineStoreroomId);
        Map<String,Object> result = new HashMap<>();
        result.put("total",data.getTotal());
        result.put("rows",data.getRecords());

        return result;
    }

    /**
     *  线边库入库列表
     * @param page
     * @param rows
     * @param lineStoreroomId
     * @return
     */
    @RequestMapping(value = "/lineStoreroomInHistoryListPage",method = RequestMethod.POST)
    public Map<String,Object> LineStoreroomInHistoryListPage(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "15") Integer rows,
            @RequestParam(value = "lineStoreroomId",required = false) Long lineStoreroomId){
        Page<LineStoreroomHistoryVo> data = lshService.queryLineStoreroomInHistory(new Page<LineStoreroomHistoryVo>(page, rows), lineStoreroomId);
        Map<String,Object> result = new HashMap<>();
        result.put("total",data.getTotal());
        result.put("rows",data.getRecords());

        return result;
    }
}
