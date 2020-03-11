package com.hvisions.mes.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.EmpLineOrderTeam;
import com.hvisions.mes.domain.Goods;
import com.hvisions.mes.service.IEmpLineOrderTeamService;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 员工产线工序班组配置控制器
 *
 * @author dpeng
 * @create 2019-06-27 10:18
 */
@RestController
@RequestMapping("/json/empLineOrderTeam")
@ApiIgnore
public class EmpLineOrderTeamController {

    @Autowired
    private IEmpLineOrderTeamService empLineOrderTeamService;

    /**
     *  查询员工产线工序班组配置信息
     * @param empLineOrderTeam 配置对象    可能出现值 empName lineName orderName teamName
     * @return
     */
    @GetMapping("/getEmpLineOrderTeam")
    public Map<String,Object> getEmpLineOrderTeam(@RequestParam(defaultValue = "1")Integer page,
                                                     @RequestParam(defaultValue = "15")Integer rows,
                                                     EmpLineOrderTeam empLineOrderTeam) {
        Page<EmpLineOrderTeam> data = empLineOrderTeamService.queryAllEmpLineOrderTeam(new Page<>(page, rows), empLineOrderTeam);
        Map<String,Object> result = new HashMap<>(16);
        result.put("total",data.getTotal());
        result.put("rows",data.getRecords());
        return result;
    }

    /**
     *  增加记录
     * @param empLineOrderTeam 对象
     */
    @RequestMapping("/increaseEmpLineOrderTeam")
    public String increaseEmpLineOrderTeam(EmpLineOrderTeam empLineOrderTeam) {
        String flag;
        try {
            empLineOrderTeamService.addEmpLineOrderTeam(empLineOrderTeam);
            flag = "true";
        } catch (Exception e) {
            e.printStackTrace();
            flag = "false";
        }
        return flag;
    }

    /**
     *  修改记录
     * @param empLineOrderTeam 对象
     */
    @RequestMapping("/alterEmpLineOrderTeam")
    public String alterEmpLineOrderTeam(EmpLineOrderTeam empLineOrderTeam) {
        String flag;
        try {
            empLineOrderTeamService.modifyEmpLineOrderTeam(empLineOrderTeam);
            flag = "true";
        } catch (Exception e) {
            e.printStackTrace();
            flag = "false";
        }
        return flag;
    }

    /**
     *  批量删除
     * @param ids  主键id
     */
    @RequestMapping("/removeEmpLineOrderTeam")
    public String removeEmpLineOrderTeam(Integer[] ids) {
        String flag;
        try {
            empLineOrderTeamService.removeEmpLineOrderTeam(ids);
            flag = "true";
        } catch (Exception e) {
            e.printStackTrace();
            flag = "false";
        }
        return flag;
    }

    @GetMapping("/getGoodsIdAndName")
    public List<Goods> getGoodsIdAndName(@RequestParam(required = false) String goodsName) {
        return empLineOrderTeamService.queryGoodsIdAndName(goodsName);
    }

    @GetMapping("/getGoodsIdAndNameByQ")
    public List<Goods> getGoodsIdAndNameByQ(@RequestParam(required = false,value = "q") String goodsName) {
        return empLineOrderTeamService.queryGoodsIdAndName(goodsName);
    }
}
