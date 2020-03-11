package com.hvisions.mes.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.controller.vo.SalesHistory;

import java.util.List;

public interface ISalesHistoryService {
    //查询销售历史记录
    Page<SalesHistory> querySalesHistory(Page<SalesHistory> page,Integer companyId);

    // 查询最近销售量、最近交易时间、销售总量、公司所在地级市
    List<SalesHistory> querySalesMessage();

}
