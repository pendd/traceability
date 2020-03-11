package com.hvisions.mes.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hvisions.mes.domain.PdaInOut;import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author dpeng
 * @description PDA登录登出
 * @date 2019-09-28 19:10
 */
public interface PdaInOutMapper {

    List<PdaInOut> selectPdaInOutByPage(@Param("page")Pagination page, @Param("empName")String empName, @Param("inOutDate")Date inOutDate);

    void updatePdaInOutByOutTime(PdaInOut pdaInOut);

    PdaInOut selectPdaInOutById(Integer id);

    void updateEmpOnlineState(Integer empId);
}
