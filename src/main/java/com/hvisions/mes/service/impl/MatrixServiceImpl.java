package com.hvisions.mes.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.Matrix;
import com.hvisions.mes.mapper.MatrixMapper;
import com.hvisions.mes.service.IMatrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dpeng
 * @description
 * @date 2019-08-28 10:09
 */
@Service
public class MatrixServiceImpl implements IMatrixService {

    @Autowired
    private MatrixMapper matrixMapper;

    @Override
    public Page<Matrix> queryAllByPage(Page<Matrix> page, Matrix matrix) {
        page.setRecords(matrixMapper.selectAllByPage(page,matrix));
        return page;
    }

    @Override
    public void appendMatrix(Matrix matrix) {
        matrixMapper.insertMatrix(matrix);
    }

    @Override
    public void changeMatrix(Matrix matrix) {
        matrixMapper.updateMatrix(matrix);
    }

    @Override
    public void cutMatrixById(List<Integer> ids) {
        matrixMapper.deleteMatrixById(ids);
    }
}
