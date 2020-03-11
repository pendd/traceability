package com.hvisions.mes.mapper;

import com.hvisions.mes.domain.ProcessLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ProcessLogMapper {

    /**
     * 根据过程中的编码(原料编码/成品编码)获取记录
     */
    ProcessLog selectProcess(String processCode);
    /**
     *  添加流程中转数据
     */
    void insertProcess(ProcessLog processLog);
    /**
     *  更新流程中转数据
     */
    void updateProcess(ProcessLog processLog);

}