package com.hvisions.mes.service;

import com.hvisions.mes.domain.MoldApply;

import java.util.List;

public interface IMoldApplyService {

    List<MoldApply> findAllMoldApply();

    void addMoldApply(MoldApply moldApply);

    MoldApply findMoldApplyById(Integer applyId);

    Integer findMoldAmount(Integer moldId);

    List<MoldApply> findAllMoldApplyIsOut();

    void modMoldApply(Integer applyId);
}
