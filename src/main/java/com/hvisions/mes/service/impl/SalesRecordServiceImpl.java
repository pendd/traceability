package com.hvisions.mes.service.impl;

import com.hvisions.mes.domain.SalesRecord;
import com.hvisions.mes.mapper.SalesRecordMapper;
import com.hvisions.mes.service.ISalesRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalesRecordServiceImpl implements ISalesRecordService {
    @Autowired
    SalesRecordMapper salesRecordMapper;


    @Override
    public void addSalesRecord(SalesRecord salesRecord) {

        salesRecordMapper.insertSalesRecordList(salesRecord);

    }
}
