package com.hvisions.mes.xa.sqlServerMapper;

import com.hvisions.mes.domain.MomOrderDetail;
import com.hvisions.mes.domain.Person;
import com.hvisions.mes.domain.Rdrecord10;
import com.hvisions.mes.domain.Rdrecords10;

import java.util.List;

/**
 * @author dpeng
 * @description 成品入库
 * @date 2019-09-02 23:21
 */
public interface GoodsSqlServerMapper {

    /**
     *  通过单据编号查询子表信息
     * @param cMoCode
     * @return
     */
    List<MomOrderDetail> selectMomOrderDeailListByCode(String cMoCode);

    /**
     *  新增产成品入库单主表
     * @param rdrecord10
     */
    void insertRdrecord10(Rdrecord10 rdrecord10);

    /**
     *  新增产成品入库单子表
     * @param rdrecords10
     */
    void insertRdrecords10(Rdrecords10 rdrecords10);

}
