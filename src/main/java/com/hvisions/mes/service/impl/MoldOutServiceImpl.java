package com.hvisions.mes.service.impl;

import com.hvisions.mes.domain.MoldOut;
import com.hvisions.mes.mapper.MoldOutMapper;
import com.hvisions.mes.service.IMoldOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoldOutServiceImpl implements IMoldOutService {
    @Autowired
    MoldOutMapper moldOutMapper;

    @Override
    public List<MoldOut> findAllMoldOut() {
        return moldOutMapper.selectMoldOut();
    }

    @Override
    public void addMoldOut(MoldOut moldOut) {
        moldOutMapper.insertMoldOut(moldOut);
    }
}
