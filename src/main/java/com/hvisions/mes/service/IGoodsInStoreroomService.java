package com.hvisions.mes.service;

import com.hvisions.mes.controller.vo.GoodsSortVo;
import com.hvisions.mes.controller.vo.GoodsVo;

import java.util.List;

/**
 * 成品入库
 *
 * @author dpeng
 * @create 2019-03-27 21:57
 */
public interface IGoodsInStoreroomService {

    // 成品合格率
    List<GoodsVo> getAllGoods();

    // 成品库存
    List<GoodsSortVo> getCountGoods();
}
