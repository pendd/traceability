package com.hvisions.mes.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.Tooling;
import com.hvisions.mes.domain.ToolingRepair;
import com.hvisions.mes.service.IToolingRepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dpeng
 * @description 工装维保控制器
 * @date 2019-08-27 19:59
 */
@RestController
@RequestMapping("/json/toolingRepair")
public class ToolingRepairController {

    @Autowired
    private IToolingRepairService repairService;

    /**
     *  分页查询
     * @param page   当前页
     * @param rows   一页显示数量
     * @param repair 维保对象
     * @return       维保集合
     */
    @GetMapping("/getAllByPage")
    public Map<String,Object> getAllByPage(@RequestParam(defaultValue = "1")Integer page,
                                           @RequestParam(defaultValue = "15")Integer rows,
                                           ToolingRepair repair) {
        Page<ToolingRepair> data = repairService.queryAllByPage(new Page<>(page, rows), repair);
        Map<String,Object> result = new HashMap<>(16);

        result.put("total",data.getTotal());
        result.put("rows",data.getRecords());
        return result;
    }

    /**
     *  添加维保
     * @param repair 维保对象
     * @return       状态
     */
    @PostMapping("/addToolingRepair")
    public String addToolingRepair(ToolingRepair repair){
        String flag = "true";
        try {
            repairService.appendToolingRepair(repair);
        }catch (Exception e) {
            e.printStackTrace();
            flag = "false";
        }
        return flag;
    }

    /**
     *  更新维保
     * @param repair 维保对象
     * @return       状态
     */
    @PostMapping("/modifyToolingRepair")
    public String modifyToolingRepair(ToolingRepair repair) {
        String flag = "true";
        try {
            repairService.changeToolingRepair(repair);
        }catch (Exception e) {
            flag = "false";
            e.printStackTrace();
        }
        return flag;
    }

    /**
     *  删除维保
     * @param ids 维保ID
     * @return    ID
     */
    @PostMapping("/removeToolingRepair")
    public String removeToolingRepair(@RequestParam("ids")List<Integer> ids) {
        String flag = "true";
        try {
            repairService.cutToolingRepairById(ids);
        }catch (Exception e) {
            flag = "false";
            e.printStackTrace();
        }
        return flag;
    }

    /**
     *  查询工装ID和工装名称做下拉列表用
     * @return
     */
    @GetMapping("/getToolingName")
    public List<Tooling> getToolingName() {
        return repairService.queryToolingName();
    }
}
