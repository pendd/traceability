package com.hvisions.mes.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.PdaInOut;

import java.util.Date;

/**
 * @author dpeng
 * @description PDA登录登出
 * @date 2019-09-28 19:45
 */
public interface IPdaInOutService {

    Page<PdaInOut> queryPdaInOutByPage(Page<PdaInOut> page,String empName, Date inOutDate);

    void changePdaInOutByOutTime(PdaInOut pdaInOut);
}
