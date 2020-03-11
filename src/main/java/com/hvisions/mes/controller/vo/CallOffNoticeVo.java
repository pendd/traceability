package com.hvisions.mes.controller.vo;

import com.hvisions.mes.domain.CallOff;
import com.hvisions.mes.domain.ProduceOrder;
import lombok.Data;

import java.util.List;

/**
 * @author dpeng
 * @description 备料通知vo类
 * @date 2019-09-03 22:07
 */
@Data
public class CallOffNoticeVo {

    private List<CallOff> callOff;

    private ProduceOrder produceOrder;
}
