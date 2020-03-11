package com.hvisions.mes.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.Goods;
import com.hvisions.mes.domain.GoodsMaterial;
import com.hvisions.mes.domain.Material;
import com.hvisions.mes.service.impl.GoodsMaterialServiceImpl;
import com.hvisions.mes.service.impl.GoodsServiceImpl;
import com.hvisions.mes.service.impl.MaterialServiceImpl;
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
@RequestMapping("/json/GoodsMaterial")
@ApiIgnore
public class GoodsMaterialController extends BaseController {
    @Autowired
    private GoodsMaterialServiceImpl goodsMaterialService;
    @Autowired
    private GoodsServiceImpl goodsService;
    @Autowired
    private MaterialServiceImpl materialService;


    @RequestMapping(value = "/GoodsMaterialListPage", method = RequestMethod.GET)
    public Map<String, Object> goodsMaterialListPage (
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "15") Integer rows,
            @RequestParam(required = false)String materialName,
            @RequestParam(required = false)String goodsName) {
        Page<GoodsMaterial> data = goodsMaterialService.showGoodsMaterial(new Page<>(page, rows),materialName,goodsName);
        Map<String, Object> result = new HashMap<>();
        result.put("total", data.getTotal());
        result.put("rows", data.getRecords());

        return result;
    }



    @RequestMapping(value =  "/addGoodsMaterial", method = RequestMethod.POST)
    public String addGoodsMaterial(HttpServletRequest request, HttpServletResponse respon) {
        GoodsMaterial goodsMaterial = new GoodsMaterial();
        String res = "";
        String msg = null;
        try {
            goodsMaterial.setCreateTime(new Date());
            goodsMaterial.setGoodsId(Integer.valueOf(request.getParameter("goodsId")));
            goodsMaterial.setMaterialId(Integer.valueOf(request.getParameter("materialId")));
            goodsMaterial.setQuantity(Integer.valueOf(request.getParameter("quantity")));
            goodsMaterial.setUpdateTime(new Date());
            goodsMaterial.setUpdateUserId(getCurrentUserID());
            goodsMaterial.setUserId(getCurrentUserID());
            goodsMaterialService.AddGoodsMaterial(goodsMaterial);
            res = "true";
        } catch (Exception ex) {
                msg = ex.getMessage();
                res = "false";
        }
        return res;
    }



    @RequestMapping(value = "/removeGoodsMaterial", method = RequestMethod.POST)
    public String removeGoodsMaterial(@RequestParam("delIDs") List<String> delIDs ) {
        String res = "true";

        try {

            for(int i=0;i<delIDs.size();i++)
            {
                goodsMaterialService.DelGoodsMaterial(Integer.valueOf(delIDs.get(i)));
            }


        } catch (Exception ex) {
            res = "false";
        }

        return res;
    }


    @RequestMapping(value =  "/editGoodsMaterial", method = RequestMethod.POST)
    public String editMaterial(HttpServletRequest request, HttpServletResponse respon) {
        GoodsMaterial goodsMaterial = new GoodsMaterial();
        String res = "";
        String msg = null;
        try {
            goodsMaterial.setGmId(Integer.valueOf(request.getParameter("gmId")));
            goodsMaterial.setGoodsId(Integer.valueOf(request.getParameter("goodsId")));
            goodsMaterial.setMaterialId(Integer.valueOf(request.getParameter("materialId")));
            goodsMaterial.setQuantity(Integer.valueOf(request.getParameter("quantity")));
            goodsMaterial.setUpdateTime(new Date());
            goodsMaterial.setUpdateUserId(getCurrentUserID());
            goodsMaterialService.ModGoodsMaterial(goodsMaterial);
            res = "true";

        } catch (Exception ex) {
            msg = ex.getMessage();
            res = "false";
        }
        return res;
    }



    @RequestMapping(value = "/materialcombox",method = RequestMethod.GET)
    public List<Material> getCombox(HttpServletRequest request,HttpServletResponse response){

        return materialService.queryMaterialCombobox();
    }

    @RequestMapping(value = "/goodcombox",method = RequestMethod.GET)
    public List<Goods> getSuombox(@RequestParam(value = "q",required = false)String goodsName){

        return goodsService.queryGoodsCombobox(goodsName);
    }
}
