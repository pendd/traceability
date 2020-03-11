package com.hvisions.mes.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.Matrix;
import com.hvisions.mes.domain.MatrixRepair;
import com.hvisions.mes.service.IMatrixRepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dpeng
 * @description 模具维保控制器
 * @date 2019-08-28 10:10
 */
@RestController
@RequestMapping("/json/matrixRepair")
public class MatrixRepairController {

    @Autowired
    private IMatrixRepairService repairService;

    @GetMapping("/getAllByPage")
    public Map<String,Object> getAllByPage(@RequestParam(defaultValue = "1")Integer page,
                                           @RequestParam(defaultValue = "15")Integer rows,
                                           MatrixRepair repair) {

        Map<String,Object> result = new HashMap<>(16);

        Page<MatrixRepair> data = repairService.selectAllByPage(new Page<>(page, rows), repair);
        result.put("total",data.getTotal());
        result.put("rows",data.getRecords());

        return result;
    }

    @PostMapping("/addMatrixRepair")
    public String addMatrixRepair(MatrixRepair repair) {
        String flag = "true";
        try {
            repairService.insertMatrixRepair(repair);
        }catch (Exception e) {
            flag = "false";
            e.printStackTrace();
        }
        return flag;
    }

    @PostMapping("/modifyMatrixRepair")
    public String modifyMatrixRepair(MatrixRepair repair) {
        String flag = "true";
        try {
            repairService.updateMatrixRepair(repair);
        }catch (Exception e) {
            flag = "false";
            e.printStackTrace();
        }
        return flag;
    }

    @PostMapping("/removeMatrixRepair")
    public String removeMatrixRepair(@RequestParam("ids")List<Integer> ids) {
        String flag = "true";
        try {
            repairService.deleteMatrixRepair(ids);
        }catch (Exception e) {
            flag = "false";
            e.printStackTrace();
        }
        return flag;
    }

    @GetMapping("/getNameAndId")
    public List<Matrix> getNameAndId() {
        return repairService.selectNameAndId();
    }
}
