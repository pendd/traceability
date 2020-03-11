package com.hvisions.mes.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.*;
import com.hvisions.mes.mapper.QualityTraceMapper;
import com.hvisions.mes.service.IQualityTraceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dpeng
 * @description
 * @date 2019-09-27 17:48
 */
@Service
public class QualityTraceServiceImpl implements IQualityTraceService {

    @Autowired
    private QualityTraceMapper traceMapper;

    @Override
    public Page<EquipmentAoi> queryEquipmentAoi(Page<EquipmentAoi> page, String workNumber) {
        page.setRecords(traceMapper.selectEquipmentAoi(page,workNumber));
        return page;
    }

    @Override
    public Page<EquipmentAoiWrongDetail> queryEquipmentAoiWrongDetail(Page<EquipmentAoiWrongDetail> page, String workNumber) {
        page.setRecords(traceMapper.selectEquipmentAoiWrongDetail(page,workNumber));
        return page;
    }

    @Override
    public Page<EquipmentFct> queryEquipmentFct(Page<EquipmentFct> page, String workNumber) {
        page.setRecords(traceMapper.selectEquipmentFct(page,workNumber));
        return page;
    }

    @Override
    public Page<EquipmentIct> queryEquipmentIct(Page<EquipmentIct> page, String workNumber) {
        page.setRecords(traceMapper.selectEquipmentIct(page,workNumber));
        return page;
    }

    @Override
    public Page<EquipmentSpi> queryEquipmentSpi(Page<EquipmentSpi> page, String workNumber) {
        page.setRecords(traceMapper.selectEquipmentSpi(page,workNumber));
        return page;
    }

    @Override
    public Page<EquipmentTpjHeadS> queryEquipmentTpjHeadS(Page<EquipmentTpjHeadS> page, String workNumber) {
        page.setRecords(traceMapper.selectEquipmentTpjHeadS(page,workNumber));
        return page;
    }
}
