package com.hvisions.mes.service.INewPdaService;


import com.hvisions.mes.domain.*;
import com.hvisions.mes.domain.PdaDomain.PdaResponseData;

import java.util.List;

/**
 * @author dpeng
 * @description PDA通知消息接口
 * @date 2019-07-17 11:08
 */
public interface INewPdaNoticeService {

    /**
     *  定时通知任务数据插入
     */
    void noticeTask();

    /**
     *  把通知表信息提供接口给PDA调用
     * @return
     */
    PdaResponseData noticeToPda(Integer userId);

    /**
     *  更新消息表为已读
     * @param notice
     * @return
     */
    PdaResponseData modifyNotice(Notice notice);

    /**
     *  新增退料表记录
     * @param withdrawal
     */
    void appendWithdrawal(List<Withdrawal> withdrawal);

    /**
     *  新增配料表记录
     * @param callOff
     */
    void appendCallOff(List<CallOff> callOff, ProduceOrder produceOrder);

    /**
     *  退料超期通知
     */
    void noticeWithdrawalOverTime();
}
