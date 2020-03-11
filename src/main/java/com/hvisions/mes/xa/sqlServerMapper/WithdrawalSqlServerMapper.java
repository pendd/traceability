package com.hvisions.mes.xa.sqlServerMapper;

import com.hvisions.mes.domain.Rdrecord11;
import com.hvisions.mes.domain.Rdrecords11;

/**
 * @author dpeng
 * @description 退料 回写接口
 * @date 2019-09-09 16:34
 */
public interface WithdrawalSqlServerMapper {

    void insertRdrecord11(Rdrecord11 rdrecord11);

    void insertRdrecords11(Rdrecords11 rdrecords11);
}
