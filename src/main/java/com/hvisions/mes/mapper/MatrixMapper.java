package com.hvisions.mes.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hvisions.mes.domain.Matrix;

import java.util.List;

/**
 * @author dpeng
 * @description 模具
 * @date 2019-08-28 10:07
 */
public interface MatrixMapper {

    List<Matrix> selectAllByPage(Pagination page,Matrix matrix);

    void insertMatrix(Matrix matrix);

    void updateMatrix(Matrix matrix);

    void deleteMatrixById(List<Integer> ids);
}
