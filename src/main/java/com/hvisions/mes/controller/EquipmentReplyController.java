package com.hvisions.mes.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.EquipmentReply;
import com.hvisions.mes.dto.ReadJson;
import com.hvisions.mes.service.IEquipmentReply;
import com.hvisions.mes.util.ReadJsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dpeng
 * @description 设备反馈控制器
 * @date 2019-08-30 14:11
 */
@RestController
@RequestMapping("/json/equipmentReply")
public class EquipmentReplyController extends BaseController{

    @Autowired
    private IEquipmentReply equipmentReply;

    @Value("file:///" + "${web.uploadPath}" + "json/setting.json")
    private Resource resource;

    @GetMapping("/getEquipmentReplyByPage")
    public Map<String,Object> getEquipmentReplyByPage(@RequestParam(defaultValue = "1")Integer page,
                                                    @RequestParam(defaultValue = "15")Integer rows) {
        Page<EquipmentReply> data = equipmentReply.queryEquipmentReplyByPage(new Page<>(page, rows),getCurrentUserID());
        Map<String,Object> result = new HashMap<>(16);
        result.put("total",data.getTotal());
        result.put("rows",data.getRecords());
        return result;
    }

    @PostMapping("/modifyEquipmentReply")
    public String modifyEquipmentReply(EquipmentReply reply) {
        String flag = "true";
        try {
            equipmentReply.updateEquipmentReply(reply);
        }catch (Exception e) {
            e.printStackTrace();
            flag = "false";
        }
        return flag;
    }

    @GetMapping("/getAllEquipmentReply")
    public Map<String,Object> getAllEquipmentReply(@RequestParam(defaultValue = "1")Integer page,
                                                 @RequestParam(defaultValue = "15")Integer rows,
                                                 @RequestParam(required = false)String equipmentName,
                                                 @RequestParam(required = false)String taskUserName) {
        Page<EquipmentReply> data = equipmentReply.queryAllEquipmentReply(new Page<>(page, rows), equipmentName, taskUserName);
        Map<String,Object> result = new HashMap<>(16);
        result.put("total",data.getTotal());
        result.put("rows",data.getRecords());
        return result;
    }

    @GetMapping("/test")
    public ReadJson test() {

        ReadJson readJson = ReadJsonUtil.parseJson(resource);

        int count = ReadJsonUtil.daysBetween(resource);
        System.out.println(count);

        return readJson;
    }

}
