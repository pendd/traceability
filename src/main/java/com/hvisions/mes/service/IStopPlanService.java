package com.hvisions.mes.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.StopPlanAuto;
import com.hvisions.mes.domain.StopPlanManual;

public interface IStopPlanService {

    Page<StopPlanAuto> queryStopPlanAuto(Page<StopPlanAuto> page, String activeBeginDate,
            String activeEndDate);

    Page<StopPlanManual> queryStopPlanManual(Page<StopPlanManual> page, String stopBeginTime,
            String stopEndTime);

    void addStopPlanAuto(StopPlanAuto oStopPlanAuto);

    void addStopPlanManual(StopPlanManual oStopPlanManual);

    void editStopPlanAuto(StopPlanAuto oStopPlanAuto);

    void editStopPlanManual(StopPlanManual oStopPlanManual);

    void removeStopPlanAuto(Integer serialId);

    void removeStopPlanManual(Integer serialId);
}
