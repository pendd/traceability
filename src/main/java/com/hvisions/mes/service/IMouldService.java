package com.hvisions.mes.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.Mould;

public interface IMouldService {
    List<Mould> queryAllMould();
    Page<Mould> showMould(Page<Mould> page);

    List<Mould> queryMouldCombobox();

    Integer findMouldName(String typeName);

    void AddMould(Mould mould);


    void ModMould(Mould mould);


    void DelMould(Integer ids);

    Integer findEquipmentById(Integer moldId);
}
