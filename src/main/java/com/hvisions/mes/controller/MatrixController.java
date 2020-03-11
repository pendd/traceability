package com.hvisions.mes.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.Matrix;
import com.hvisions.mes.service.IMatrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dpeng
 * @description 模具控制器
 * @date 2019-08-28 10:10
 */
@RestController
@RequestMapping("/json/matrix")
public class MatrixController {

    @Autowired
    private IMatrixService matrixService;

    @GetMapping("/getAllByPage")
    public Map<String,Object> getAllByPage(@RequestParam(defaultValue = "1")Integer page,
                                           @RequestParam(defaultValue = "15")Integer rows,
                                           Matrix matrix) {
        Map<String,Object> result = new HashMap<>(16);

        Page<Matrix> data = matrixService.queryAllByPage(new Page<>(page, rows), matrix);
        result.put("total",data.getTotal());
        result.put("rows",data.getRecords());

        return result;
    }

    @PostMapping("/addMatrix")
    public String addMatrix(Matrix matrix) {
        String flag = "true";
        try {
            matrixService.appendMatrix(matrix);
        }catch (Exception e) {
            flag = "false";
            e.printStackTrace();
        }
        return flag;
    }

    @PostMapping("/modifyMatrix")
    public String modifyMatrix(Matrix matrix) {
        String flag = "true";
        try {
            matrixService.changeMatrix(matrix);
        }catch (Exception e) {
            flag = "false";
            e.printStackTrace();
        }
        return flag;
    }

    @PostMapping("/removeMatrix")
    public String removeMatrix(@RequestParam("ids")List<Integer> ids) {
        String flag = "true";
        try {
            matrixService.cutMatrixById(ids);
        }catch (Exception e) {
            flag = "false";
            e.printStackTrace();
        }
        return flag;
    }
}
