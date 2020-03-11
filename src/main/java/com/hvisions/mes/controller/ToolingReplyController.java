package com.hvisions.mes.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.ToolingReply;
import com.hvisions.mes.service.IToolingReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dpeng
 * @description 工装反馈控制器
 * @date 2019-08-30 14:11
 */
@RestController
@RequestMapping("/json/toolingReply")
public class ToolingReplyController extends BaseController{

    @Autowired
    private IToolingReply toolingReply;

    @GetMapping("/getToolingReplyByPage")
    public Map<String,Object> getToolingReplyByPage(@RequestParam(defaultValue = "1")Integer page,
                                                    @RequestParam(defaultValue = "15")Integer rows) {
        Page<ToolingReply> data = toolingReply.queryToolingReplyByPage(new Page<>(page, rows),getCurrentUserID());
        Map<String,Object> result = new HashMap<>(16);
        result.put("total",data.getTotal());
        result.put("rows",data.getRecords());
        return result;
    }

    @PostMapping("/modifyToolingReply")
    public String modifyToolingReply(ToolingReply reply) {
        String flag = "true";
        try {
            toolingReply.updateToolingReply(reply);
        }catch (Exception e) {
            e.printStackTrace();
            flag = "false";
        }
        return flag;
    }

    @GetMapping("/getAllToolingReply")
    public Map<String,Object> getAllToolingReply(@RequestParam(defaultValue = "1")Integer page,
                                                 @RequestParam(defaultValue = "15")Integer rows,
                                                 @RequestParam(required = false)String toolingName,
                                                 @RequestParam(required = false)String taskUserName,
                                                 @RequestParam(required = false)String principleName) {
        Page<ToolingReply> data = toolingReply.queryAllToolingReply(new Page<>(page, rows), toolingName, taskUserName, principleName);
        Map<String,Object> result = new HashMap<>(16);
        result.put("total",data.getTotal());
        result.put("rows",data.getRecords());
        return result;
    }
}
