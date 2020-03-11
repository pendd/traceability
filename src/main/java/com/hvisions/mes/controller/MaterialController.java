package com.hvisions.mes.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.controller.vo.MaterialVo;
import com.hvisions.mes.domain.Material;
import com.hvisions.mes.domain.Supplier;
import com.hvisions.mes.service.impl.MaterialServiceImpl;
import com.hvisions.mes.service.impl.SupplierServiceImpl;
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
@RequestMapping("/json/material")
@ApiIgnore
public class MaterialController extends BaseController {
    @Autowired
    private MaterialServiceImpl materialService;
    @Autowired
    private SupplierServiceImpl supplierService;
    @RequestMapping(value = "/materialListPage", method = RequestMethod.GET)
    public Map<String, Object> materialListPage (
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "15") Integer rows,
            @RequestParam(value = "materialName",required = false)String materialName,
            @RequestParam(value = "materialSignCode",required = false)String materialSignCode) {
        Page<Material> data = materialService.showMaterial(new Page<>(page, rows),materialName,materialSignCode);
        Map<String, Object> result = new HashMap<>(16);
        result.put("total", data.getTotal());
        result.put("rows", data.getRecords());

        return result;
    }


    //增加原料
    @RequestMapping(value =  "/addMaterial", method = RequestMethod.POST)
    public String addMaterial(HttpServletRequest request, HttpServletResponse respon) {
        Material material = new Material();
        String res = "";
        String msg = null;
        try {
            int num = materialService.findMaterialName(request.getParameter("materialName"));

            if (num != 0) {
                res = "2";
            } else {
                material.setCreateTime(new Date());
                material.setSupplierId(Integer.valueOf(request.getParameter("supplierId")));
                material.setMaterialName(request.getParameter("materialName"));
                material.setUserId(getCurrentUserID());
                material.setUpdateTime(new Date());
                material.setUpdateUserId(getCurrentUserID());
                if (request.getParameter("parentId")!=null & request.getParameter("parentId")!=""){
                    material.setParentId(Integer.valueOf(request.getParameter("parentId")));
                }
                material.setAlarmStock(Integer.valueOf(request.getParameter("alarmStock")));
                material.setIsCheck(Integer.valueOf(request.getParameter("isCheck")));
                materialService.AddMaterial(material);
                res = "true";
            }
        } catch (Exception ex) {
            msg = ex.getMessage();
            res = "false";
        }
        return res;
    }



    @RequestMapping(value = "/removeMaterial", method = RequestMethod.POST)
    public String removeMaterial(@RequestParam("delIDs") List<String> delIDs ) {
        String res = "true";

        try {

            for(int i=0;i<delIDs.size();i++)
            {
                materialService.DelMaterial(Integer.valueOf(delIDs.get(i)));
            }


        } catch (Exception ex) {
            res = "false";
        }

        return res;
    }


    @RequestMapping(value =  "/editMaterial", method = RequestMethod.POST)
    public String editMaterial(Material material) {
        material.setUpdateTime(new Date());
        material.setUpdateUserId(getCurrentUserID());
        return materialService.modMaterial(material);
    }



    @RequestMapping(value = "/combox",method = RequestMethod.GET)
    public List<Material> getCombox(HttpServletRequest request,HttpServletResponse response){

        return materialService.queryMaterialCombobox();
    }
    @RequestMapping(value = "/sucombox",method = RequestMethod.GET)
    public List<Supplier> getSuombox(HttpServletRequest request, HttpServletResponse response){

        return supplierService.querySupplierCombobox();
    }

    /**
     *  获取原料名  原料数量  最小的五个
     * @return
     */
    @RequestMapping(value = "/getMaterialCount")
    public List<MaterialVo> getMaterialCount(){
        return materialService.queryMaterialCount();
    }
}
