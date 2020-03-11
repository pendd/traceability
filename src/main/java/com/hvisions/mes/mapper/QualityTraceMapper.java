package com.hvisions.mes.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hvisions.mes.domain.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author dpeng
 * @description 质量追溯
 * @date 2019-09-25 14:31
 */
public interface QualityTraceMapper {

    List<EquipmentAoi> selectEquipmentAoi(@Param("page")Pagination page, @Param("workNumber")String workNumber);

    List<EquipmentAoiWrongDetail> selectEquipmentAoiWrongDetail(@Param("page")Pagination page, @Param("workNumber")String workNumber);

    List<EquipmentFct> selectEquipmentFct(@Param("page")Pagination page, @Param("workNumber")String workNumber);

    List<EquipmentIct> selectEquipmentIct(@Param("page")Pagination page, @Param("workNumber")String workNumber);

    List<EquipmentSpi> selectEquipmentSpi(@Param("page")Pagination page, @Param("workNumber")String workNumber);

    List<EquipmentTpjHeadS> selectEquipmentTpjHeadS(@Param("page")Pagination page, @Param("workNumber")String workNumber);
}
