package com.hvisions.mes.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hvisions.mes.controller.vo.SalesHistory;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SalesHistoryMapper {
    //查询销售历史记录
    List<SalesHistory> selectSalesHistory(Pagination page,@Param("companyId")Integer companyId);

    // 查询销售总量
    List<SalesHistory> selectSalesAllCount();

    // 查询最近交易时间
    List<SalesHistory> selectSalesRecentTime();

    // 查询最近销售量
    List<SalesHistory> selectSalesRecentCount();
}
