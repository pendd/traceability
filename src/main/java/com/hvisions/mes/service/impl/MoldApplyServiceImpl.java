package com.hvisions.mes.service.impl;

import com.hvisions.mes.domain.Emp;
import com.hvisions.mes.domain.MoldApply;
import com.hvisions.mes.mapper.MoldApplyMapper;
import com.hvisions.mes.mapper.UserMapper;
import com.hvisions.mes.service.IMoldApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoldApplyServiceImpl implements IMoldApplyService {
    @Autowired
    MoldApplyMapper moldApplyMapper;
    @Autowired
    UserMapper userMapper;

    @Override
    public List<MoldApply> findAllMoldApply() {
        return moldApplyMapper.selectMoldAapply();
    }

    @Override
    public MoldApply findMoldApplyById(Integer applyId) {
        return moldApplyMapper.selectMoldAapplyById(applyId);
    }

    @Override
    public Integer findMoldAmount(Integer moldId) {
        return moldApplyMapper.selectMoldAmountById(moldId);
    }

    @Override
    public void addMoldApply(MoldApply moldApply) {
        //添加班组信息
        Emp oEmp = userMapper.selectUsersByID(moldApply.getUserId());
        moldApply.setTeamId(oEmp.getTeamId());

        moldApplyMapper.insertMoldApply(moldApply);
    }

    @Override
    public List<MoldApply> findAllMoldApplyIsOut() {
        return moldApplyMapper.selectMoldAapplyIsOut();
    }

    @Override
    public void modMoldApply(Integer applyId) {
        moldApplyMapper.updateMoldApplyOut(applyId);
    }
}
