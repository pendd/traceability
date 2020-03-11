package com.hvisions.mes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.AdjustPlan;
import com.hvisions.mes.mapper.AdjustPlanMapper;
import com.hvisions.mes.service.IAdjustPlanService;

@Service
public class AdjustPlanServiceImp implements IAdjustPlanService {
    @Autowired
    private AdjustPlanMapper adjustPlanMapper;

    @Override
    public Page<AdjustPlan> queryAdjustPlanList(Page<AdjustPlan> page, String planPart,
            String planContent) {
        page.setRecords(adjustPlanMapper.selectAdjustPlanList(page, planPart, planContent));
        return page;
    }

    @Override
    public void addAdjustPlan(AdjustPlan oAdjustPlan) {
        adjustPlanMapper.insertAdjustPlan(oAdjustPlan);
    }

    @Override
    public void editAdjustPlan(AdjustPlan oAdjustPlan) {
        adjustPlanMapper.updateAdjustPlan(oAdjustPlan);
    }

    @Override
    public void romoveAdjustPlan(Integer planId) {
        adjustPlanMapper.deleteAdjustPlan(planId);
    }

    @Override
    public AdjustPlan selectAdjustPlanById(Integer planId) {
        return adjustPlanMapper.selectAdjustPlanById(planId);
    }

}
