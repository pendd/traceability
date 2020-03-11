package com.hvisions.mes.mapper;

import com.hvisions.mes.domain.MoldApply;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoldApplyMapper {

    List<MoldApply> selectMoldAapply();

    void insertMoldApply(MoldApply moldApply);

    MoldApply selectMoldAapplyById(Integer applyId);

    Integer selectMoldAmountById(Integer moldId);

    List<MoldApply> selectMoldAapplyIsOut();

    void updateMoldApplyOut(Integer applyId);
}