package com.hvisions.mes.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.AdjustPlan;

public interface IAdjustPlanService {

    Page<AdjustPlan> queryAdjustPlanList(Page<AdjustPlan> page, String planPart,
            String planContent);

    void addAdjustPlan(AdjustPlan oAdjustPlan);

    void editAdjustPlan(AdjustPlan oAdjustPlan);

    void romoveAdjustPlan(Integer planId);

    AdjustPlan selectAdjustPlanById(Integer planId);

}
