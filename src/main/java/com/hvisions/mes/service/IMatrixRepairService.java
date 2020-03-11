package com.hvisions.mes.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.Matrix;
import com.hvisions.mes.domain.MatrixRepair;

import java.util.List;

/**
 * @author dpeng
 * @description 模具维保
 * @date 2019-08-28 10:09
 */
public interface IMatrixRepairService {

    Page<MatrixRepair> selectAllByPage(Page<MatrixRepair> page, MatrixRepair repair);

    void insertMatrixRepair(MatrixRepair repair);

    void updateMatrixRepair(MatrixRepair repair);

    void deleteMatrixRepair(List<Integer> ids);

    List<Matrix> selectNameAndId();
}
