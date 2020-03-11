package com.hvisions.mes.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.Equipment;
import com.hvisions.mes.domain.EquipmentRepair;
import com.hvisions.mes.service.IEquipmentRepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dpeng
 * @description 设备维保控制器
 * @date 2019-08-27 19:59
 */
@RestController
@RequestMapping("/json/equipmentRepair")
public class EquipmentRepairController {

    @Autowired
    private IEquipmentRepairService repairService;

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
                                           EquipmentRepair repair) {
        Page<EquipmentRepair> data = repairService.queryAllByPage(new Page<>(page, rows), repair);
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
    @PostMapping("/addEquipmentRepair")
    public String addEquipmentRepair(EquipmentRepair repair){
        String flag = "true";
        try {
            repairService.appendEquipmentRepair(repair);
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
    @PostMapping("/modifyEquipmentRepair")
    public String modifyEquipmentRepair(EquipmentRepair repair) {
        String flag = "true";
        try {
            repairService.changeEquipmentRepair(repair);
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
    @PostMapping("/removeEquipmentRepair")
    public String removeEquipmentRepair(@RequestParam("ids")List<Integer> ids) {
        String flag = "true";
        try {
            repairService.cutEquipmentRepairById(ids);
        }catch (Exception e) {
            flag = "false";
            e.printStackTrace();
        }
        return flag;
    }

    /**
     *  查询设备ID和设备名称做下拉列表用
     * @return
     */
    @GetMapping("/getEquipmentName")
    public List<Equipment> getEquipmentName() {
        return repairService.queryEquipmentName();
    }
}
