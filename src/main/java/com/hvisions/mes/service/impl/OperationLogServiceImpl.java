package com.hvisions.mes.service.impl;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.Param;
import com.hvisions.mes.domain.OperationLog;
import com.hvisions.mes.mapper.OperationLogMapper;
import com.hvisions.mes.service.IOperationLogService;
@Service
public class OperationLogServiceImpl implements IOperationLogService{
    @Autowired
    private OperationLogMapper operationLogMapper;
    @Autowired
    HttpServletRequest request;

    @Override
    public Page<OperationLog> queryOperationLog(Page<OperationLog> page, String menuName, String empName,String language) {
        page.setRecords(operationLogMapper.selectOperationLog(page,menuName, empName,language));
        return page;
    }

    @Override
    public void addOperationLog(OperationLog oOperationLog) {

        operationLogMapper.insert(oOperationLog);
    }
}
