package com.hvisions.mes.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.hvisions.mes.service.impl.GoodsServiceImpl;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.Goods;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/json/Goods")
@ApiIgnore
public class GoodsController extends BaseController {
    @Autowired
    private GoodsServiceImpl iGoodsService;

    @RequestMapping(value = "/Goodslistpage", method = RequestMethod.GET)
    public Map<String, Object> lineListPage (
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "15") Integer rows,
            @RequestParam(required = false)String goodsCode,
            @RequestParam(required = false)String goodsName) {
        Page<Goods> data = iGoodsService.showGoods(new Page<>(page, rows),goodsCode,goodsName);
        Map<String, Object> result = new HashMap<>();
        result.put("total", data.getTotal());
        result.put("rows", data.getRecords());

        return result;
    }

    @RequestMapping(value =  "/addGoods", method = RequestMethod.POST)
    public String addGoods(HttpServletRequest request, HttpServletResponse respon) {
        Goods goods = new Goods();
        String res = "";
        String msg = null;
        try {
            int num = iGoodsService.findGoodsName(request.getParameter("goodsName"));
            if (num != 0) {
                res = "2";
            } else {
                goods.setCreateTime(new Date());
                goods.setGoodsName(request.getParameter("goodsName"));
                goods.setGoodsType(request.getParameter("goodsType"));
                goods.setUpdateTime(new Date());
                goods.setUpdateUserId(getCurrentUserID());
                goods.setUserId(getCurrentUserID());
                goods.setShelfLife(Integer.valueOf(request.getParameter("shelfLife")));

                iGoodsService.AddGoods(goods);
                res = "true";
            }
        } catch (Exception ex) {
            msg = ex.getMessage();
            res = "false";
        }
        return res;
    }


    //删除产品
    @RequestMapping(value = "/removeGoods", method = RequestMethod.POST)
    public String removeGoods(@RequestParam("delIDs") List<String> delIDs ) {
        String res = "true";

        try {

            for(int i=0;i<delIDs.size();i++)
            {
                iGoodsService.DelGoods(Integer.valueOf(delIDs.get(i)));
            }
        } catch (Exception ex) {
            res = "false";
        }

        return res;
    }

    //修改产品
    @RequestMapping(value =  "/editGoods", method = RequestMethod.POST)
    public String editLine(HttpServletRequest request, HttpServletResponse respon) {

        Goods goods = new Goods();
        String res;
        try {
            goods.setGoodsType(request.getParameter("goodsType"));
            goods.setGoodsId(Integer.valueOf(request.getParameter("goodsId")));
            goods.setGoodsName(request.getParameter("goodsName"));
            goods.setUpdateUserId(getCurrentUserID());
            goods.setUpdateTime(new Date());
            goods.setStandardHours(new BigDecimal(Double.valueOf(request.getParameter("standardHours"))));
//            goods.setShelfLife(Integer.valueOf(request.getParameter("shelfLife")));
            iGoodsService.ModGoods(goods);
            res = "true";

        } catch (Exception ex) {
            ex.printStackTrace();
            res = "false";
        }
        return res;
    }

    @GetMapping("/getGoodsByGoodsName")
    public List<Goods> getGoodsByGoodsName(String goodsName) {
        return iGoodsService.queryGoodsByGoodsName(goodsName);
    }

    @GetMapping("/getGoodsByQ")
    public List<Goods> getGoodsByQ(@RequestParam(value = "q",required = false) String goodsName) {
        return iGoodsService.queryGoodsByGoodsName(goodsName);
    }

}
