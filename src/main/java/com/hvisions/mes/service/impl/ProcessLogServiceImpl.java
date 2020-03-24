package com.hvisions.mes.service.impl;

import com.hvisions.mes.domain.ProcessLog;
import com.hvisions.mes.mapper.ProcessLogMapper;
import com.hvisions.mes.service.IProcessLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author swang
 * Date 2019/3/22 17:59
 * Version 1.0
 * Description
 **/
@Service
@Slf4j
public class ProcessLogServiceImpl implements IProcessLogService {

    @Autowired
    private ProcessLogMapper processLogMapper;

    @Override
    public ProcessLog selectProcess(String processCode) {
        log.info("过程记录  select");
        return processLogMapper.selectProcess(processCode);
    }

    @Override
    public void insertProcess(ProcessLog processLog) {
        log.info("过程记录  insert");
        processLogMapper.insertProcess(processLog);
    }

    @Override
    public void updateProcess(ProcessLog processLog) {
        log.info("过程记录  update");
        processLogMapper.updateProcess(processLog);
    }
}
