package com.hvisions.mes.mapper;

import com.hvisions.mes.dto.AoiResult;
import com.hvisions.mes.dto.IctResult;
import com.hvisions.mes.dto.TpjResult;

import java.util.List;

/**
 * @author dpeng
 * @description 工艺看板
 * @date 2019-09-24 13:38
 */
public interface BoardTechnologyMapper {

    /**
     *  查询SPI 程式名称
     * @param workNumber
     * @return
     */
    List<String> selectSpi(String workNumber);

    /**
     *  查询SPI 测试总数
     * @param workNumber
     * @return
     */
    Integer selectAllAmount(String workNumber);

    /**
     *  查询SPI pass数量
     * @param workNumber
     * @return
     */
    Integer selectPassAmount(String workNumber);

    /**
     *  查询贴片机 吸料数量 贴装数量 吸料错误数量 检测出错数量 抛料数
     * @param workNumber
     * @return
     */
    TpjResult selectTpjSum(String workNumber);

    /**
     *  查询AOI检测 测试数量 测试总点数 不良总点数
     * @param workNumber
     * @return
     */
    AoiResult selectAoiSum(String workNumber);

    /**
     *  查询ICT测试 测试数量 测试板数
     * @param workNumber
     * @return
     */
    IctResult selectIctSum(String workNumber);

    /**
     *  查询ICT 测试合格数
     * @param workNumber
     * @return
     */
    Integer selectIctQualifiedSum(String workNumber);

    /**
     *  查询FCT 测试数量
     * @param workNumber
     * @return
     */
    Integer selectFctSum(String workNumber);

    /**
     *  查询FCT 合格数
     * @param workNumber
     * @return
     */
    Integer selectFctQualifiedSum(String workNumber);

}
