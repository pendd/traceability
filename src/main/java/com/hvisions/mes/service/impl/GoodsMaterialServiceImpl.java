package com.hvisions.mes.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.GoodsMaterial;
import com.hvisions.mes.mapper.GoodsMaterialMapper;
import com.hvisions.mes.service.IGoodsMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsMaterialServiceImpl implements IGoodsMaterialService {
    @Autowired
    private GoodsMaterialMapper goodsMaterialMapper;

   /* @Override
    public List<GoodsMaterial> queryAllGoodsMaterial() {
        return goodsMaterialMapper.selectGoodsMaterial();
    }*/

    @Override
    public Page<GoodsMaterial> showGoodsMaterial(Page<GoodsMaterial> page,String materialName,String goodsName) {
        page.setRecords(goodsMaterialMapper.selectGoodsMaterial(page,materialName,goodsName));
        return page;
    }

    @Override
    public void AddGoodsMaterial(GoodsMaterial goodsMaterial) {

        goodsMaterialMapper.insertGoodsMaterial(goodsMaterial);
    }

    @Override
    public void ModGoodsMaterial(GoodsMaterial goodsMaterial) {

        goodsMaterialMapper.updateGoodsMaterial(goodsMaterial);
    }

    @Override
    public void DelGoodsMaterial(Integer ids) {

        goodsMaterialMapper.deleteGoodsMaterial(ids);
    }
}
