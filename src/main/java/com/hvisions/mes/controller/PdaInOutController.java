package com.hvisions.mes.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.PdaInOut;
import com.hvisions.mes.service.IPdaInOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dpeng
 * @description PDA登录登出控制器
 * @date 2019-09-28 19:50
 */
@RestController
@RequestMapping("/json/pdaInOut")
public class PdaInOutController {

    @Autowired
    private IPdaInOutService outService;

    @GetMapping("/getPdaInOutByPage")
    public Map<String,Object> getPdaInOutByPage(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                                @RequestParam(value = "rows",defaultValue = "15")Integer rows,
                                                PdaInOut pdaInOut) {

        Map<String,Object> data = new HashMap<>(16);
        Page<PdaInOut> result = outService.queryPdaInOutByPage(new Page<>(page, rows), pdaInOut.getEmpName(), pdaInOut.getInOutDate());
        data.put("total",result.getTotal());
        data.put("rows",result.getRecords());
        return data;
    }

    @PostMapping("/modifyPdaInOut")
    public String modifyPdaInOut(PdaInOut pdaInOut) {
        String res;
        try {
            outService.changePdaInOutByOutTime(pdaInOut);
            res = "true";
        } catch (Exception e) {
            e.printStackTrace();
            res = "false";
        }
        return res;
    }
}
