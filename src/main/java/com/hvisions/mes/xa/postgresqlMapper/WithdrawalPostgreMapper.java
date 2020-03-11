package com.hvisions.mes.xa.postgresqlMapper;

import com.hvisions.mes.domain.Rdrecord11;
import com.hvisions.mes.domain.Rdrecords11;

/**
 * @author dpeng
 * @description 退料 回写接口
 * @date 2019-09-09 16:34
 */
public interface WithdrawalPostgreMapper {

    void insertRdrecord11(Rdrecord11 rdrecord11);

    void insertRdrecords11(Rdrecords11 rdrecords11);

    String selectCAccId();

    /**
     *  通过物料二维码获取物料批次
     * @param materialSignCode
     * @return
     */
    String selectBatchByMaterialSignCode(String materialSignCode);

    /**
     *  通过工单号查询订单号
     * @param workNumber
     * @return
     */
    String selectCodeByWorkNumber(String workNumber);

    /**
     *  通过批次ID获取批次号
     * @param batchId
     * @return
     */
    String selectBatchByBatchId(Integer batchId);
}
