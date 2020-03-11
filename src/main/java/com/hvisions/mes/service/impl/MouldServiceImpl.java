package com.hvisions.mes.service.impl;

import java.util.List;

import com.hvisions.mes.domain.Mould;
import com.hvisions.mes.mapper.MouldMapper;
import com.hvisions.mes.service.IMouldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
@Service
public class MouldServiceImpl implements IMouldService {
    @Autowired
    private MouldMapper mouldMapper;
    @Override
    public List<Mould> queryAllMould() {
        // TODO Auto-generated method stub
        return mouldMapper.selectMould();
    }
    @Override
    public Page<Mould> showMould(Page<Mould> page) {
        page.setRecords(mouldMapper.selectMould(page));
        return page;
    }

    @Override
    public List<Mould> queryMouldCombobox() {
        return mouldMapper.selectMouldCombobox();
    }

    @Override
    public Integer findMouldName(String typeName) {
        return mouldMapper.selectMouldName(typeName);
    }

    @Override
    public void AddMould(Mould mould) {
        mouldMapper.insertMould(mould);
    }

    @Override
    public void ModMould(Mould mould) {
        mouldMapper.updateMould(mould);
    }

    @Override
    public void DelMould(Integer ids) {
        mouldMapper.deleteMould(ids);
    }

    @Override
    public Integer findEquipmentById(Integer moldId) {
        return mouldMapper.selectEquipmentById(moldId);
    }
}
