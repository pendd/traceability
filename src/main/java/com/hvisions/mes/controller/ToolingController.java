package com.hvisions.mes.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.Tooling;
import com.hvisions.mes.service.IToolingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dpeng
 * @description 工装控制器
 * @date 2019-08-27 16:28
 */
@RestController
@RequestMapping("/json/tooling")
public class ToolingController extends BaseController{

    @Autowired
    private IToolingService toolingService;

    /**
     *  分页查询工装表信息
     * @param page     当前页
     * @param rows     每页显示数量
     * @param tooling  工装对象
     * @return         工装对象集合
     */
    @GetMapping("/getAllByPage")
    public Map<String,Object> getAllByPage(@RequestParam(defaultValue = "1")Integer page,
                                              @RequestParam(defaultValue = "15")Integer rows,
                                              Tooling tooling) {
        Page<Tooling> data = toolingService.selectAllByPage(new Page<>(page, rows), tooling);
        Map<String,Object> result = new HashMap<>(16);
        result.put("total",data.getTotal());
        result.put("rows",data.getRecords());
        return result;
    }

    /**
     *  添加工装信息
     * @param tooling  工装对象
     * @return         true 成功  false 失败
     */
    @PostMapping("/addTooling")
    public String addTooling(Tooling tooling) {
        String flag = "true";
        tooling.setUserId(getCurrentUserID());
        tooling.setUpdateUserId(getCurrentUserID());
        try {
            toolingService.insertTooling(tooling);
        }catch (Exception e) {
            flag = "false";
            e.printStackTrace();
        }
        return flag;
    }

    /**
     *  修改工装信息
     * @param tooling  工装对象
     * @return         true 成功  false 失败
     */
    @PostMapping("/modifyTooling")
    public String modifyTooling(Tooling tooling) {
        String flag = "true";
        tooling.setUpdateUserId(getCurrentUserID());
        try {
            toolingService.updateTooling(tooling);
        }catch (Exception e) {
            flag = "false";
            e.printStackTrace();
        }
        return flag;
    }

    /**
     *  批量删除
     * @param ids 主键集合
     * @return    true 成功  false 失败
     */
    @PostMapping("/removeTooling")
    public String removeTooling(@RequestParam("ids") List<String> ids) {
        String flag = "true";

        List<Integer> idList = new ArrayList<>();
        ids.forEach(e -> idList.add(Integer.valueOf(e)));

        try {
            toolingService.deleteToolingById(idList);
        }catch (Exception e) {
            flag = "false";
            e.printStackTrace();
        }
        return flag;
    }
}
