package com.hvisions.mes.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.Matrix;
import com.hvisions.mes.domain.MatrixRepair;
import com.hvisions.mes.mapper.MatrixRepairMapper;
import com.hvisions.mes.service.IMatrixRepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dpeng
 * @description
 * @date 2019-08-28 10:10
 */
@Service
public class MatrixRepairServiceImpl implements IMatrixRepairService {

    @Autowired
    private MatrixRepairMapper repairMapper;

    @Override
    public Page<MatrixRepair> selectAllByPage(Page<MatrixRepair> page, MatrixRepair repair) {
        page.setRecords(repairMapper.selectAllByPage(page,repair));
        return page;
    }

    @Override
    public void insertMatrixRepair(MatrixRepair repair) {
        repairMapper.insertMatrixRepair(repair);
    }

    @Override
    public void updateMatrixRepair(MatrixRepair repair) {
        repairMapper.updateMatrixRepair(repair);
    }

    @Override
    public void deleteMatrixRepair(List<Integer> ids) {
        repairMapper.deleteMatrixRepair(ids);
    }

    @Override
    public List<Matrix> selectNameAndId() {
        return repairMapper.selectNameAndId();
    }
}
