package com.hvisions.mes.service;

import com.hvisions.mes.domain.ProcessLog;

/**
 * Author swang
 * Date 2019/3/22 17:58
 * Version 1.0
 * Description
 **/
public interface IProcessLogService {

    /**
     * 根据过程中的编码(原料编码/成品编码)获取记录
     */
    ProcessLog selectProcess(String processCode);
    /**
     *  添加流程中转数据
     *  需要添加时间和人员的信息
     */
    void insertProcess(ProcessLog processLog);
    /**
     *  更新流程中转数据
     *  更新修改人员的信息
     */
    void updateProcess(ProcessLog processLog);
}
