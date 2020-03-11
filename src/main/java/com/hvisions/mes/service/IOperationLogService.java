package com.hvisions.mes.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.OperationLog;

public interface IOperationLogService {
    public Page<OperationLog> queryOperationLog(Page<OperationLog> page, String menuName, String empName,String language);
    /**
     * 人员操作日志
     * 操作类型：0:添加、1:修改、2:删除
     */
    public void addOperationLog(OperationLog oOperationLog);
}
