package com.hvisions.mes.service.impl;

import com.hvisions.mes.dto.*;
import com.hvisions.mes.mapper.BoardTechnologyMapper;
import com.hvisions.mes.service.IBoardTechnologyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author dpeng
 * @description
 * @date 2019-09-24 16:55
 */
@Service
public class BoardTechnologyServiceImpl implements IBoardTechnologyService {

    @Autowired
    private BoardTechnologyMapper mapper;

    /**
     *  查询SPI 程式名称 测试总数 pass数量
     * @param workNumber
     * @return
     */
    @Override
    public SpiResult querySpiSum(String workNumber) {
        SpiResult result = new SpiResult();
        List<String> names = mapper.selectSpi(workNumber);
        result.setModelName(names.isEmpty() ? "" : names.get(0));
        result.setPassAmount(mapper.selectPassAmount(workNumber));
        result.setSumAmount(mapper.selectAllAmount(workNumber));
        return result;
    }

    /**
     *  查询贴片机 吸料数量 贴装数量 吸料错误数量 检测出错数量 抛料数
     * @param workNumber
     * @return
     */
    @Override
    public TpjResult queryTpjSum(String workNumber) {
        return mapper.selectTpjSum(workNumber);
    }

    /**
     *  查询AOI检测 测试数量 测试总点数 不良总点数
     * @param workNumber
     * @return
     */
    @Override
    public AoiResult queryAoiSum(String workNumber) {
        return mapper.selectAoiSum(workNumber);
    }

    /**
     *  查询ICT测试 测试数量 测试板数
     * @param workNumber
     * @return
     */
    @Override
    public IctResult queryIctSum(String workNumber) {
        IctResult result = mapper.selectIctSum(workNumber);
        result.setQualifiedAmount(mapper.selectIctQualifiedSum(workNumber));
        return result;
    }

    /**
     *  查询FCT 测试数量 合格数
     * @param workNumber
     * @return
     */
    @Override
    public FctResult queryFctSum(String workNumber) {
        FctResult result = new FctResult();
        result.setRowNum(mapper.selectFctSum(workNumber));
        result.setQualifiedSum(mapper.selectFctQualifiedSum(workNumber));
        return result;
    }

}
