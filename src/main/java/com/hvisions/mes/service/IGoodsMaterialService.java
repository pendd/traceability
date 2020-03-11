package com.hvisions.mes.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.GoodsMaterial;

import java.util.List;

public interface IGoodsMaterialService {

//    List<GoodsMaterial> queryAllGoodsMaterial();
    Page<GoodsMaterial> showGoodsMaterial(Page<GoodsMaterial> page,String materialName,String goodsName);



    void AddGoodsMaterial(GoodsMaterial goodsMaterial);


    void ModGoodsMaterial(GoodsMaterial goodsMaterial);


    void DelGoodsMaterial(Integer ids);
}
