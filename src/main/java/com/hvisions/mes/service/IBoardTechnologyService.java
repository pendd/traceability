package com.hvisions.mes.service;

import com.hvisions.mes.dto.*;


/**
 * @author dpeng
 * @description 工艺参数看板
 * @date 2019-09-24 16:54
 */
public interface IBoardTechnologyService {

    /**
     *  查询SPI 程式名称 测试总数 pass数量
     * @param workNumber
     * @return
     */
    SpiResult querySpiSum(String workNumber);

    /**
     *  查询贴片机 吸料数量 贴装数量 吸料错误数量 检测出错数量 抛料数
     * @param workNumber
     * @return
     */
    TpjResult queryTpjSum(String workNumber);

    /**
     *  查询AOI检测 测试数量 测试总点数 不良总点数
     * @param workNumber
     * @return
     */
    AoiResult queryAoiSum(String workNumber);

    /**
     *  查询ICT测试 测试数量 测试板数 测试合格数
     * @param workNumber
     * @return
     */
    IctResult queryIctSum(String workNumber);

    /**
     *  查询FCT 测试数量 合格数
     * @param workNumber
     * @return
     */
    FctResult queryFctSum(String workNumber);

}
