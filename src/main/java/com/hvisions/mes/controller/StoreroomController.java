package com.hvisions.mes.controller;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hvisions.mes.domain.Storeroom;
import com.hvisions.mes.service.impl.StoreroomServiceImpl;
import com.hvisions.mes.service.impl.UserServiceImpl;
import springfox.documentation.annotations.ApiIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;


@ApiIgnore
@RestController
@RequestMapping("/json/Storeroom")
public class StoreroomController extends BaseController {

	@Autowired
	private StoreroomServiceImpl storeroomServiceImpl;
	@Autowired
	private UserServiceImpl userService;
	//分页查询库房
	@RequestMapping(value = "/Storeroomlistpage", method = RequestMethod.GET)
    public Map<String, Object> lineListPage (
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "15") Integer rows) {
        Page<Storeroom> data = storeroomServiceImpl.showStoreroom(new Page<Storeroom>(page, rows));
        Map<String, Object> result = new HashMap<>();
        result.put("total", data.getTotal());
        result.put("rows", data.getRecords());

        return result;
    }


	//增加库房
	@RequestMapping(value =  "/addStoreroom", method = RequestMethod.POST)
    public String addStoreroom(HttpServletRequest request, HttpServletResponse respon) {
		Storeroom storeroom = new Storeroom();
        String res = "";
        String msg = null;
        try {
            int num = storeroomServiceImpl.findStoreroomName(request.getParameter("storeroomName"));
            if (num != 0) {
                res = "2";
            } else {
				storeroom.setCreateTime(new Date());
				storeroom.setStoreroomName(request.getParameter("storeroomName"));
				storeroom.setType(Integer.valueOf(request.getParameter("type")));
				storeroom.setUpdateTime(new Date());
				storeroom.setUpdateUserId(getCurrentUserID());
				storeroom.setUserId(getCurrentUserID());
//				storeroom.setPrincipal(userService.QueryByUserAccount(request.getParameter("principal")).getEmpId());
				storeroom.setPrincipal(Integer.valueOf(request.getParameter("principal")));
				storeroom.setStoreroomCode(request.getParameter("storeroomCode"));
				storeroomServiceImpl.AddStoreroom(storeroom);
            	res = "true";
            }
        } catch (Exception ex) {
        	msg = ex.getMessage();
            res = "false";
        }
        return res;
    }


	//删除库房
	 @RequestMapping(value = "/removeStoreroom", method = RequestMethod.POST)
	    public String removeStoreroom(@RequestParam("delIDs") List<String> delIDs ) {
	        String res = "true";

	        try {

	            for(int i=0;i<delIDs.size();i++)
	            {
					storeroomServiceImpl.DelStoreroom(Integer.valueOf(delIDs.get(i)));
	            }
	        } catch (Exception ex) {
	           res = "false";
	        }

	        return res;
	    }

	 //修改库房
	 @RequestMapping(value =  "/editStoreroom", method = RequestMethod.POST)
	    public String editStoreroom(HttpServletRequest request, HttpServletResponse respon) {

		 Storeroom storeroom = new Storeroom();
	        String res = "";
	        String msg = null;
	        try {
				 storeroom.setType(Integer.valueOf(request.getParameter("type")));
				 storeroom.setStoreroomId(Integer.valueOf(request.getParameter("storeroomId")));
				 storeroom.setStoreroomName(request.getParameter("storeroomName"));
				 storeroom.setUpdateUserId(getCurrentUserID());
				 storeroom.setUpdateTime(new Date());
//				 storeroom.setPrincipal(userService.QueryByUserAccount(request.getParameter("principal")).getEmpId());
				storeroom.setPrincipal(Integer.valueOf(request.getParameter("principal")));
				storeroom.setStoreroomCode(request.getParameter("storeroomCode"));
				 storeroomServiceImpl.ModStoreroom(storeroom);
	            	res = "true";
	        } catch (Exception ex) {
	        	msg = ex.getMessage();
	            res = "false";
	        }
	        return res;
	    }

	/**
	 *  通过库房类型查询库房下拉
	 * @param type   库房类型     0原料库1成品库2线边库3半成品库
	 * @return
	 */
	@RequestMapping(value = "/getStoreroomByType")
	    public List<Storeroom> getStoreroomByType(Integer type){
			return storeroomServiceImpl.queryStoreroomByType(type);
		}

}

















