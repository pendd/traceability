package com.hvisions.mes.mapper;

import com.hvisions.mes.domain.EquipmentRepair;
import com.hvisions.mes.domain.MatrixRepair;
import com.hvisions.mes.domain.ToolingRepair;

import java.util.List;

/**
 * @author dpeng
 * @description 维保邮件通知
 * @date 2019-08-29 11:51
 */
public interface RepairEmailMapper {

    List<ToolingRepair> selectToolingAndRepair();

    List<EquipmentRepair> selectEquipmentAndRepair();
}
