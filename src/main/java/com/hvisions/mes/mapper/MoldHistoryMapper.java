package com.hvisions.mes.mapper;

import com.hvisions.mes.domain.MoldHistory;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Repository
@Mapper
public interface MoldHistoryMapper {
    void insertMoldHistory(MoldHistory moldHistory);
}