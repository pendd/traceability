package com.hvisions.mes.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.PdaInOut;
import com.hvisions.mes.mapper.PdaInOutMapper;
import com.hvisions.mes.service.IPdaInOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author dpeng
 * @description
 * @date 2019-09-28 19:46
 */
@Service
public class PdaInOutServiceImpl implements IPdaInOutService {

    @Autowired
    private PdaInOutMapper mapper;

    @Override
    public Page<PdaInOut> queryPdaInOutByPage(Page<PdaInOut> page,String empName, Date inOutDate) {
        page.setRecords(mapper.selectPdaInOutByPage(page,empName,inOutDate));
        return page;
    }

    /**
     *  先获取员工ID  在添加员工退出状态时  更新员工状态为不在线
     * @param pdaInOut
     */
    @Override
    @Transactional(rollbackFor = Exception.class,transactionManager = "primaryTransaction")
    public void changePdaInOutByOutTime(PdaInOut pdaInOut) {

        PdaInOut inOut = mapper.selectPdaInOutById(pdaInOut.getId());
        mapper.updatePdaInOutByOutTime(pdaInOut);
        mapper.updateEmpOnlineState(inOut.getEmpId());
    }
}
