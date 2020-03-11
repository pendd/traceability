package com.hvisions.mes.controller;

import com.hvisions.mes.controller.vo.GoodsSortVo;
import com.hvisions.mes.controller.vo.GoodsVo;
import com.hvisions.mes.service.IGoodsInStoreroomService;
import springfox.documentation.annotations.ApiIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author dpeng
 * @create 2019-03-27 22:29
 */
@RestController
@RequestMapping("/json/goodsInStoreroomController")
@ApiIgnore
public class GoodsInStoreroomController {

    @Autowired
    private IGoodsInStoreroomService service;

    /**
     *  获取成品合格率top5
     * @return
     */
    @RequestMapping("/getGoodsTop5")
    public List<GoodsVo> getGoodsTop5(){
        return service.getAllGoods();
    }

    /**
     *  成品库存top5
     * @return
     */
    @RequestMapping("/getCountTop5")
    public List<GoodsSortVo> getCountTop5() {
        return service.getCountGoods();
    }
}
