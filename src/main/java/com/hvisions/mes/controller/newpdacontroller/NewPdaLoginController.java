package com.hvisions.mes.controller.newpdacontroller;

import com.hvisions.mes.domain.Emp;
import com.hvisions.mes.domain.PdaDomain.PdaResponseData;
import com.hvisions.mes.service.INewPdaService.INewPdaLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录控制器
 *
 * @author dpeng
 * @create 2019-05-20 13:33
 */
//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/json/pda")
@Api(description = "登录控制器 登录退出状态有修改 请注意 有返回值")
public class NewPdaLoginController {

    private PdaResponseData responseData;
    private Map<String,Object> data;

    @Autowired
    private INewPdaLoginService loginService;

    @ModelAttribute
    public void init() {
        responseData = new PdaResponseData();
        data = new HashMap<>(16);
    }

    /**
     *  PDA登录
     * @param emp
     * @return
     */
    @PostMapping("/login")
    public PdaResponseData getEmp(@RequestBody Emp emp) {
        if (emp.getAccount() == null) {
            responseData.setStatus(PdaResponseData.STATUS_FAIL);
            responseData.setErrorMessage("参数传入不正确");
            return responseData;
        }
        emp = loginService.selectEmpByAccount(emp);
        if (emp != null) {
            data.put("emp",emp);
            responseData.setData(data);
        } else {
            responseData.setStatus(1);
            responseData.setErrorMessage("无此用户");
        }
        return responseData;
    }

    @PostMapping("/alterEmpOnlineState")
    @ApiOperation(value = "修改员工状态",notes = "登录退出都要调这个接口 flag 为0表示ok 为1 表示上一次操作未退出")
    public PdaResponseData alterEmpOnlineState(@RequestBody Emp emp) {
        int flag = loginService.modifyEmpOnlineState(emp.getEmpId(), emp.getIsOnline());
        data.put("flag",flag);
        responseData.setData(data);
        return responseData;
    }

    /**
     *  加密
     * @param password
     * @return
     */
    @GetMapping("/encryptPassword")
    public String encryptPassword(String password) {
        return loginService.encryptPassword(password);
    }

}
