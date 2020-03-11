package com.hvisions.mes.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hvisions.mes.domain.Matrix;
import com.hvisions.mes.domain.MatrixRepair;

import java.util.List;

/**
 * @author dpeng
 * @description 模具维保
 * @date 2019-08-28 10:08
 */
public interface MatrixRepairMapper {

    List<MatrixRepair> selectAllByPage(Pagination page, MatrixRepair repair);

    void insertMatrixRepair(MatrixRepair repair);

    void updateMatrixRepair(MatrixRepair repair);

    void deleteMatrixRepair(List<Integer> ids);

    List<Matrix> selectNameAndId();
}
