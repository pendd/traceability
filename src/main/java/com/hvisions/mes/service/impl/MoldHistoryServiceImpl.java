package com.hvisions.mes.service.impl;

import com.hvisions.mes.domain.MoldHistory;
import com.hvisions.mes.mapper.MoldHistoryMapper;
import com.hvisions.mes.service.IMoldHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MoldHistoryServiceImpl implements IMoldHistoryService {

    @Autowired
    MoldHistoryMapper moldHistoryMapper;

    @Override
    public void addMoldHistory(MoldHistory moldHistory) {
        moldHistoryMapper.insertMoldHistory(moldHistory);
    }
}
