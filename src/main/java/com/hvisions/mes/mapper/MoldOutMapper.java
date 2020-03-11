package com.hvisions.mes.mapper;

import com.hvisions.mes.domain.MoldOut;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface MoldOutMapper {
    List<MoldOut> selectMoldOut();
    void insertMoldOut(MoldOut moldOut);
}