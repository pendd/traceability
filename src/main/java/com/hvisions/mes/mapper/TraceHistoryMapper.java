package com.hvisions.mes.mapper;

import com.hvisions.mes.domain.TraceHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Author swang
 * Date 2019/3/23 14:07
 * Version 1.0
 * Description
 **/
@Mapper
@Repository
public interface TraceHistoryMapper {

    /**
     * 根据追溯码获取上级原料的所有数据
     */
    List<TraceHistory> selectMaterialByNextTraceCode(@Param("nextTraceCode") String nextTraceCode,@Param("traceType") Integer traceType,@Param("date") Date date);
    /**
     * 根据追溯码获取下级产品的所有数据
     */
    List<TraceHistory> selectProductByTraceCode(@Param("traceCode") String traceCode);
    /**
     * 根据追溯编码获取所有"投料"之后相关信息
     */
    List<TraceHistory> selectByTraceCode(@Param("traceCode") String traceCode);
    /**
     * 添加追溯操作记录
     */
    void insertTraceHistory(TraceHistory traceHistory);

}
