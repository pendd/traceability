package com.hvisions.mes.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 工序总称
 *
 * @author dpeng
 * @create 2019-03-30 9:42
 */

@Mapper
@Repository
public interface OrderFullNameMapper {

    // 通过工序总称名获取工序总称编号
    Integer selectOrderFullNameIdByName(String fullName);
}
