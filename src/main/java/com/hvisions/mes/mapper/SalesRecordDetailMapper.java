package com.hvisions.mes.mapper;

import com.hvisions.mes.domain.SalesRecordDetail;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SalesRecordDetailMapper {
    List<SalesRecordDetail> selectSalesRecordDetail();
    void insertSalesRecordDetailList(SalesRecordDetail salesRecordDetail);
}