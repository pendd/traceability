package com.hvisions.mes.controller;

import com.hvisions.mes.dto.*;
import com.hvisions.mes.service.IBoardTechnologyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dpeng
 * @description 工艺参数看板控制器
 * @date 2019-09-24 17:11
 */
@RestController
@RequestMapping("/json/boardTechnology")
public class BoardTechnologyController {

    @Autowired
    private IBoardTechnologyService service;

    /**
     *  查询SPI 程式名称 测试总数 pass数量
     * @param workNumber
     * @return
     */
    @GetMapping("/getSpiSum")
    public SpiResult getSpiSum(String workNumber) {
        return service.querySpiSum(workNumber);
    }

    /**
     *  查询贴片机 吸料数量 贴装数量 吸料错误数量 检测出错数量 抛料数
     * @param workNumber
     * @return
     */
    @GetMapping("/getTpjSum")
    public TpjResult getTpjSum(String workNumber) {
        return service.queryTpjSum(workNumber);
    }

    /**
     *  查询AOI检测 测试数量 测试总点数 不良总点数
     * @param workNumber
     * @return
     */
    @GetMapping("/getAoiSum")
    public AoiResult getAoiSum(String workNumber) {
        return service.queryAoiSum(workNumber);
    }

    /**
     *  查询ICT测试 测试数量 测试板数
     * @param workNumber
     * @return
     */
    @GetMapping("/getIctSum")
    public IctResult getIctSum(String workNumber) {
        return service.queryIctSum(workNumber);
    }

    /**
     *  查询FCT 测试数量 合格数
     * @param workNumber
     * @return
     */
    @GetMapping("/getFctSum")
    public FctResult getFctSum(String workNumber) {
        return service.queryFctSum(workNumber);
    }
}
