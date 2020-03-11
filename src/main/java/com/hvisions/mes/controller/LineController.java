package com.hvisions.mes.controller;


import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hvisions.mes.service.impl.UserServiceImpl;
import springfox.documentation.annotations.ApiIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.Line;
import com.hvisions.mes.service.impl.LineServiceImpl;

/**
*
* 项目名称：quality_traceability_system
* 类名称：LineController
* 类描述：  产线控制
* 创建人：宫岩
* 创建时间：2019年2月26日 下午3:53:37
*
*/

@RestController
@RequestMapping("/json/Line")
@ApiIgnore
public class LineController extends BaseController {

	@Autowired
	private LineServiceImpl lineServiceImpl;
	@Autowired
	private UserServiceImpl userService;
	//分页查询产线
	@RequestMapping(value = "/linelistpage", method = RequestMethod.GET)
    public Map<String, Object> lineListPage (
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "15") Integer rows) {
        Page<Line> data = lineServiceImpl.showLine(new Page<Line>(page, rows));
        Map<String, Object> result = new HashMap<>();
        result.put("total", data.getTotal());
        result.put("rows", data.getRecords());
        return result;
    }


	//增加产线
	@RequestMapping(value =  "/addline", method = RequestMethod.POST)
    public String addLine(Line line) {

	   line.setCreateTime(new Date());
	   line.setUpdateTime(new Date());
	   line.setUpdateUserId(getCurrentUserID());
	   line.setUserId(getCurrentUserID());

	   return lineServiceImpl.AddLine(line);
    }


	//删除产线
	 @RequestMapping(value = "/removeline", method = RequestMethod.POST)
	    public String removeline(@RequestParam("delIDs") List<String> delIDs ) {
	        String res = "true";

	        try {

	            for(int i=0;i<delIDs.size();i++)
	            {
	            	lineServiceImpl.DelLine(Integer.valueOf(delIDs.get(i)));
	            }
	        } catch (Exception ex) {
	           res = "false";
	        }

	        return res;
	    }

	 //修改产线
	 @RequestMapping(value =  "/editline", method = RequestMethod.POST)
	 public String editLine(Line line) {
		line.setUpdateUserId(getCurrentUserID());
		return lineServiceImpl.ModLine(line);
	}



}

















