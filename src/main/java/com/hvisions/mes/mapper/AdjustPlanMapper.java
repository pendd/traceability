package com.hvisions.mes.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hvisions.mes.domain.AdjustPlan;

public interface AdjustPlanMapper {
    // 1
    List<AdjustPlan> selectAdjustPlanList(Pagination page, @Param("planPart") String planPart,
            @Param("planContent") String planContent);

    // 5
    AdjustPlan selectAdjustPlanById(@Param("planId") Integer planId);

    // 2
    void insertAdjustPlan(AdjustPlan oAdjustPlan);

    // 3
    void updateAdjustPlan(AdjustPlan oAdjustPlan);

    // 4
    void deleteAdjustPlan(@Param("planId") Integer planId);

}
