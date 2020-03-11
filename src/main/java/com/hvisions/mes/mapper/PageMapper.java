package com.hvisions.mes.mapper;

import org.apache.ibatis.annotations.Param;

public interface PageMapper {
    public String selectMenuId(@Param("url") String url);
}
