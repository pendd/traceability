package com.hvisions.mes.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.Matrix;

import java.util.List;

/**
 * @author dpeng
 * @description 模具
 * @date 2019-08-28 10:09
 */
public interface IMatrixService {

    Page<Matrix> queryAllByPage(Page<Matrix> page, Matrix matrix);

    void appendMatrix(Matrix matrix);

    void changeMatrix(Matrix matrix);

    void cutMatrixById(List<Integer> ids);
}
