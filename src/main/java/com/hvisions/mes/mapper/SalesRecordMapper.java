package com.hvisions.mes.mapper;

import com.hvisions.mes.domain.SalesRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SalesRecordMapper {
    void insertSalesRecordList(SalesRecord salesRecord);
}