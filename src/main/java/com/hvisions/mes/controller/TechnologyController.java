package com.hvisions.mes.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.Technology;
import com.hvisions.mes.service.ITechnologyService;
import springfox.documentation.annotations.ApiIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 工艺控制器
 *
 * @author dpeng
 * @create 2019-07-03 22:05
 */
@RestController
@RequestMapping("/json/technology")
@ApiIgnore
public class TechnologyController {

    @Autowired
    private ITechnologyService technologyService;

    /**
     *  分页查询所有工艺
     * @param page
     * @param rows
     * @param technology
     * @return
     */
    @RequestMapping("/getAllTechnology")
    public Map<String,Object> getAllTechnology(@RequestParam(defaultValue = "1")Integer page,
                                               @RequestParam(defaultValue = "15")Integer rows,
                                               Technology technology) {
        Page<Technology> data = technologyService.queryAllTechnology(new Page<>(page, rows), technology);
        Map<String,Object> result = new HashMap<>(16);
        result.put("total",data.getTotal());
        result.put("rows",data.getRecords());
        return result;
    }

    /**
     *  新增工艺
     * @param technology
     * @return
     */
    @RequestMapping("/addTechnology")
    public String addTechnology(Technology technology) {
        String flag;
        try {
            technologyService.incrementTechnology(technology);
            flag = "true";
        } catch (Exception e) {
            e.printStackTrace();
            flag = "false";
        }
        return flag;
    }

    /**
     *  修改工艺
     * @param technology
     * @return
     */
    @RequestMapping("/alterTechnology")
    public String alterTechnology(Technology technology) {
        String flag;
        try {
            technologyService.modifyTechnology(technology);
            flag = "true";
        } catch (Exception e) {
            e.printStackTrace();
            flag = "false";
        }
        return flag;
    }

    /**
     *  删除工艺
     * @param ids  主键数组
     * @return
     */
    @RequestMapping("/removeTechnology")
    public String removeTechnology(Integer[] ids) {
        String flag;
        try {
            technologyService.removeTechnology(ids);
            flag = "true";
        } catch (Exception e) {
            e.printStackTrace();
            flag = "false";
        }
        return flag;
    }

}
