package com.hvisions.mes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.controller.vo.SalesHistory;
import com.hvisions.mes.mapper.SalesHistoryMapper;
import com.hvisions.mes.service.ISalesHistoryService;

import java.util.Collections;
import java.util.List;


@Service
public class SalesHistoryServiceImpl implements ISalesHistoryService {
    @Autowired
    private SalesHistoryMapper salesHistoryMapper;
    @Override
    public Page<SalesHistory> querySalesHistory(Page<SalesHistory> page, Integer companyId) {
        // TODO Auto-generated method stub
        page.setRecords(salesHistoryMapper.selectSalesHistory(page, companyId));
        return page;
    }

    /**
     *  查询最近销售量、最近交易时间、销售总量、公司所在地级市
     * @return
     */
    @Override
    public List<SalesHistory> querySalesMessage() {
        // 销售总量
        List<SalesHistory> allList = salesHistoryMapper.selectSalesAllCount();
        // 最近销售量
        List<SalesHistory> recentCountList = salesHistoryMapper.selectSalesRecentCount();
        // 最近交易时间
        List<SalesHistory> recentTimeList = salesHistoryMapper.selectSalesRecentTime();

        for (SalesHistory time : recentTimeList) {
            // 交易时间 和 销售总量 合并  通过companyId
            for (SalesHistory all : allList) {
                if (time.getCompanyId().equals(all.getCompanyId())){
                    time.setSaleCount(all.getSaleCount());
                    break;
                }
            }

            // 交易时间 和 最近销售量 合并
            for (SalesHistory recentCount : recentCountList) {
                if (time.getCompanyId().equals(recentCount.getCompanyId())){
                    time.setRecentCount(recentCount.getRecentCount());
                    break;
                }
            }
        }

        return recentTimeList;
    }

}
