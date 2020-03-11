package com.hvisions.mes.controller;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hvisions.mes.domain.ProcessType;
import com.hvisions.mes.service.impl.ProcessTypeServiceImpl;
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



@RestController
@RequestMapping("/json/ProcessType")
@ApiIgnore
public class ProcessTypeController extends BaseController {

	@Autowired
	private ProcessTypeServiceImpl processTypeService;


	@RequestMapping(value = "/ProcessTypelistpage", method = RequestMethod.GET)
    public Map<String, Object> ProcessTypeListPage (
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "15") Integer rows) {
        Page<ProcessType> data = processTypeService.showProcessType(new Page<ProcessType>(page, rows));
        Map<String, Object> result = new HashMap<>();
        result.put("total", data.getTotal());
        result.put("rows", data.getRecords());

        return result;
    }



	@RequestMapping(value =  "/addProcessType", method = RequestMethod.POST)
    public String addProcessType(HttpServletRequest request, HttpServletResponse respon) {
		ProcessType processType = new ProcessType();
        String res = "";
        String msg = null;
        try {
            int num = processTypeService.findProcessTypeName(request.getParameter("fullName"));
            if (num != 0) {
                res = "2";
            } else {
				processType.setCreateTime(new Date());
				processType.setFullName(request.getParameter("fullName"));
				processType.setUpdateTime(new Date());
				processType.setUpdateUserId(getCurrentUserID());
				processType.setUserId(getCurrentUserID());
            	processTypeService.AddProcessType(processType);
            	res = "true";
            }
        } catch (Exception ex) {
        	msg = ex.getMessage();
            res = "false";
        }
        return res;
    }



	 @RequestMapping(value = "/removeProcessType", method = RequestMethod.POST)
	    public String removeProcessType(@RequestParam("delIDs") List<String> delIDs ) {
	        String res = "true";

	        try {

	            for(int i=0;i<delIDs.size();i++)
	            {
	            	processTypeService.DelProcessType(Integer.valueOf(delIDs.get(i)));
	            }
	        } catch (Exception ex) {
	           res = "false";
	        }

	        return res;
	    }


	 @RequestMapping(value =  "/editProcessType", method = RequestMethod.POST)
	    public String editProcessType(HttpServletRequest request, HttpServletResponse respon) {

		 	ProcessType processType = new ProcessType();
	        String res = "";
	        String msg = null;
	        try {
				processType.setFullId(Integer.valueOf(request.getParameter("fullId")));
				processType.setFullName(request.getParameter("fullName"));
				processType.setUpdateUserId(getCurrentUserID());
				processType.setUpdateTime(new Date());
				processTypeService.ModProcessType(processType);
	            	res = "true";

	        } catch (Exception ex) {
	        	msg = ex.getMessage();
	            res = "false";
	        }
	        return res;
	    }



}

















