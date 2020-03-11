package com.hvisions.mes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.StopPlanAuto;
import com.hvisions.mes.domain.StopPlanManual;
import com.hvisions.mes.mapper.StopPlanMapper;
import com.hvisions.mes.service.IStopPlanService;

/**
 * Service实体类
 *
 * @author mtyu
 * @since 2018-01-30
 */

@Service
public class StopPlanServiceImpl implements IStopPlanService {

    @Autowired
    private StopPlanMapper stopPlanMapper;

    @Override
    public Page<StopPlanAuto> queryStopPlanAuto(Page<StopPlanAuto> page, String activeBeginDate,
            String activeEndDate) {
        page.setRecords(stopPlanMapper.selectStopPlanAuto(page, activeBeginDate, activeEndDate));
        return page;
    }

    @Override
    public Page<StopPlanManual> queryStopPlanManual(Page<StopPlanManual> page,
            String activeBeginDate, String activeEndDate) {
        page.setRecords(stopPlanMapper.selectStopPlanManual(page, activeBeginDate, activeEndDate));
        return page;
    }

    @Override
    public void addStopPlanAuto(StopPlanAuto oStopPlanAuto) {
        stopPlanMapper.insertStopPlanAuto(oStopPlanAuto);
    }

    @Override
    public void addStopPlanManual(StopPlanManual oStopPlanManual) {

        stopPlanMapper.insertStopPlanManual(oStopPlanManual);
    }

    @Override
    public void editStopPlanAuto(StopPlanAuto oStopPlanAuto) {
        stopPlanMapper.updateStopPlanAuto(oStopPlanAuto);
    }

    @Override
    public void editStopPlanManual(StopPlanManual oStopPlanManual) {
        stopPlanMapper.updateStopPlanManual(oStopPlanManual);
    }

    @Override
    public void removeStopPlanAuto(Integer serialId) {

        stopPlanMapper.deleteStopPlanAuto(serialId);
    }

    @Override
    public void removeStopPlanManual(Integer serialId) {
        stopPlanMapper.deleteStopPlanManual(serialId);

    }
}
