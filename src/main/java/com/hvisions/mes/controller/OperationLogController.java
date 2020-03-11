package com.hvisions.mes.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import springfox.documentation.annotations.ApiIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.Emp;
import com.hvisions.mes.domain.OperationLog;
import com.hvisions.mes.service.IOperationLogService;
import com.hvisions.mes.util.CookieUtil;
@RestController
@RequestMapping("/json/OperationLog")
@ApiIgnore
public class OperationLogController extends BaseController {
    @Autowired
    private IOperationLogService operationLogService;

    //加载数据列表(分页)
    @RequestMapping(value = "/operationLogpage", method = RequestMethod.POST)
    public Map<String, Object> operationLogpage ( @RequestParam(value = "language", required = false) String language,
            @RequestParam(value = "menuName", required = false) String menuName,
            @RequestParam(value = "empName", required = false) String empName,
             @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "15") Integer rows) {
         Page<OperationLog> data = operationLogService.queryOperationLog(new Page<OperationLog>(page, rows), menuName, empName,language);
        Map<String, Object> result = new HashMap<>();
        result.put("total", data.getTotal());
        result.put("rows", data.getRecords());

        return result;
    }
}
