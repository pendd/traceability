package com.hvisions.mes.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.*;

/**
 * @author dpeng
 * @description 质量追溯
 * @date 2019-09-27 17:47
 */
public interface IQualityTraceService {
    
    Page<EquipmentAoi> queryEquipmentAoi(Page<EquipmentAoi> page,String workNumber);

    Page<EquipmentAoiWrongDetail> queryEquipmentAoiWrongDetail(Page<EquipmentAoiWrongDetail> page,String workNumber);

    Page<EquipmentFct> queryEquipmentFct(Page<EquipmentFct> page,String workNumber);

    Page<EquipmentIct> queryEquipmentIct(Page<EquipmentIct> page,String workNumber);

    Page<EquipmentSpi> queryEquipmentSpi(Page<EquipmentSpi> page,String workNumber);

    Page<EquipmentTpjHeadS> queryEquipmentTpjHeadS(Page<EquipmentTpjHeadS> page,String workNumber);

}
