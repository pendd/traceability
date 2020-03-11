package com.hvisions.mes.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hvisions.mes.domain.OperationLog;

public interface OperationLogMapper {
    public List<OperationLog> selectOperationLog(Pagination page, @Param("menuName") String menuName, @Param("empName") String empName,@Param("language")String language);

    public void insert(OperationLog operationLog);
}
