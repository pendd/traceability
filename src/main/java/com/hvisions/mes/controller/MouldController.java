package com.hvisions.mes.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.Equipment;
import com.hvisions.mes.domain.Mould;
import com.hvisions.mes.service.impl.EquipmentServiceImpl;
import com.hvisions.mes.service.impl.MouldServiceImpl;
import springfox.documentation.annotations.ApiIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/json/mould")
@ApiIgnore
public class MouldController extends BaseController {
    @Autowired
    private EquipmentServiceImpl equipmentServiceImpl;
    @Autowired
    private MouldServiceImpl mouldService;

    @RequestMapping(value = "/MouldListPage", method = RequestMethod.GET)
    public Map<String, Object> MouldListPage (
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "15") Integer rows) {
        Page<Mould> data = mouldService.showMould(new Page<Mould>(page, rows));
        Map<String, Object> result = new HashMap<>();
        result.put("total", data.getTotal());
        result.put("rows", data.getRecords());

        return result;
    }



    @RequestMapping(value =  "/addMould", method = RequestMethod.POST)
    public String addMould(HttpServletRequest request, HttpServletResponse respon) {
        Mould mould = new Mould();
        String res = "";
        String msg = null;
        try {
            int num = mouldService.findMouldName(request.getParameter("typeName"));
            if (num != 0) {
                res = "2";
            } else {
                mould.setCreateTime(new Date());
                mould.setEquipmentId(Integer.valueOf(request.getParameter("equipmentId")));
                mould.setMoldName(request.getParameter("moldName"));
                mould.setAmount(Integer.valueOf(request.getParameter("amount")));
                mould.setMoldType(request.getParameter("moldType"));
                mould.setUserId(getCurrentUserID());
                mould.setUpdateTime(new Date());
                mould.setUpdateUserId(getCurrentUserID());
                mouldService.AddMould(mould);
                res = "true";
            }
        } catch (Exception ex) {
            msg = ex.getMessage();
            res = "false";
        }
        return res;
    }



    @RequestMapping(value = "/removeMould", method = RequestMethod.POST)
    public String removeMould(@RequestParam("delIDs") List<String> delIDs ) {
        String res = "true";

        try {

            for(int i=0;i<delIDs.size();i++)
            {
                mouldService.DelMould(Integer.valueOf(delIDs.get(i)));
            }


        } catch (Exception ex) {
            res = "false";
        }

        return res;
    }

    @RequestMapping(value =  "/editMould", method = RequestMethod.POST)
    public String editMould(String typeName,HttpServletRequest request, HttpServletResponse respon) {
        Mould mould = new Mould();
        String res = "";
        String msg = null;
        try {
            mould.setEquipmentId(Integer.valueOf(request.getParameter("equipmentId")));
            mould.setMoldName(request.getParameter("moldName"));
            mould.setAmount(Integer.valueOf(request.getParameter("amount")));
            mould.setMoldType(request.getParameter("moldType"));
            mould.setMoldId(Integer.valueOf(request.getParameter("moldId")));
            mould.setUpdateTime(new Date());
            mould.setUserId(getCurrentUserID());
            mould.setUpdateUserId(getCurrentUserID());
            mouldService.ModMould(mould);
            res = "true";
            
        } catch (Exception ex) {
            msg = ex.getMessage();
            res = "false";
        }
        return res;
    }


    @RequestMapping(value = "/combox",method = RequestMethod.GET)
    public List<Equipment> getCombox(){
        return equipmentServiceImpl.queryEquipmentCombobox();
    }


}
