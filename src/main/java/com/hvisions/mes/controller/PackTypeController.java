package com.hvisions.mes.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.PackType;
import com.hvisions.mes.service.IPackTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dpeng
 * @description 包装配置控制器
 * @date 2019-09-14 17:35
 */
@RestController
@RequestMapping("/json/packType")
public class PackTypeController {

    @Autowired
    private IPackTypeService packTypeService;

    /**
     *  分页查询包装配置对象
     * @param page
     * @param rows
     * @return
     */
    @GetMapping("/getPackTypeByPage")
    public Map<String,Object> getPackTypeByPage(@RequestParam(defaultValue = "1") Integer page,
                                                @RequestParam(defaultValue = "15") Integer rows) {
        Page<PackType> result = packTypeService.queryPackTypeByPage(new Page<>(page, rows));
        Map<String,Object> data = new HashMap<>(16);
        data.put("total",result.getTotal());
        data.put("rows",result.getRecords());
        return data;
    }

    /**
     *  新增包装类型
     * @param packType
     * @return
     */
    @PostMapping("/addPackType")
    public String addPackType(PackType packType) {
        return packTypeService.appendPackType(packType);
    }

    /**
     *  修改包装类型
     * @param packType
     * @return
     */
    @PostMapping("/modifyPackType")
    public String modifyPackType(PackType packType) {
        return packTypeService.changePackType(packType);
    }

    /**
     *  批量删除
     * @param ids
     * @return
     */
    @PostMapping("/removePackType")
    public String removePackType(@RequestParam(value = "ids")List<Integer> ids) {
        try {
            packTypeService.cutPackTypeByIds(ids);
            return "true";
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
    }
}
