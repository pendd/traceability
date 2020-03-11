package com.hvisions.mes.controller;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import springfox.documentation.annotations.ApiIgnore;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.AdjustPlan;
import com.hvisions.mes.service.IAdjustPlanService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/maintenance")
@ApiIgnore
public class MaintenanceController {

    @Autowired
    private IAdjustPlanService adjustPlanService;

    @GetMapping("/plans")
    public Map<String, Object> getAll(@RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "15") Integer rows,
            @RequestParam(value = "planPart", required = false) String planPart,
            @RequestParam(value = "planContent", required = false) String planContent,
            @RequestParam(value = "language", defaultValue = "zh") String language)
            throws ParseException {
        Page<AdjustPlan> pageData = adjustPlanService
                .queryAdjustPlanList(new Page<AdjustPlan>(page, rows), planPart, planContent);
        Map<String, Object> result = new HashMap<>();
        result.put("total", pageData.getTotal());
        result.put("rows", pageData.getRecords());
        return result;
    }

    @GetMapping("/{id}")
    public AdjustPlan getById(@PathVariable("id") Integer id) {
        return adjustPlanService.selectAdjustPlanById(id);
    }

    @PostMapping
    public String savePlan( //
            @RequestParam(name = "planId", required = false) Integer planId,
            @RequestParam(name = "cycleDuration", required = false) Integer cycleDuration,
            @RequestParam(name = "cycleDurationUnit", required = false) Integer cycleDurationUnit,
            @RequestParam(name = "isDelay", required = false) Integer isDelay,
            @RequestParam(name = "planContent", required = false) String planContent,
            @RequestParam(name = "planPart", required = false) String planPart) {
        log.debug("request to save plan, isDelay: [{}], cycleDurationUnit: [{}]", isDelay,
                cycleDurationUnit);
        AdjustPlan adjustPlan = new AdjustPlan();
        adjustPlan.setCycleDuration(cycleDuration);
        adjustPlan.setCycleDurationUnit(cycleDurationUnit);
        adjustPlan.setIsDelay(isDelay);
        adjustPlan.setPlanContent(planContent);
        adjustPlan.setPlanPart(planPart);
        if (planId == null) {
            adjustPlanService.addAdjustPlan(adjustPlan);
            return "true";
        } else {
            adjustPlan.setPlanId(planId);
            adjustPlanService.editAdjustPlan(adjustPlan);
            return "true";
        }
    }

    @DeleteMapping
    public void deletePlan(@RequestParam(value = "ids", required = false) List<Integer> ids) {
        if (CollectionUtils.isNotEmpty(ids)) {
            for (Integer id : ids) {
                adjustPlanService.romoveAdjustPlan(id);
            }
        }
    }

}
