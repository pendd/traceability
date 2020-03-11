package com.hvisions.mes.controller.newpdacontroller;

import com.hvisions.mes.controller.vo.CallOffNoticeVo;
import com.hvisions.mes.domain.CallOff;
import com.hvisions.mes.domain.Emp;
import com.hvisions.mes.domain.Notice;
import com.hvisions.mes.domain.PdaDomain.PdaResponseData;
import com.hvisions.mes.domain.Withdrawal;
import com.hvisions.mes.service.INewPdaService.INewPdaNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dpeng
 * @description PDA 消息通知路由
 * @date 2019-07-17 11:10
 */
@RestController
@RequestMapping("/json/pda/notice")
public class NewPdaNoticeController {

    @Autowired
    private INewPdaNoticeService noticeService;

    /**
     *  PDA 调用 推送消息
     * @return
     */
    @RequestMapping("/getNotice")
    public PdaResponseData getNotice(@RequestBody Emp emp) {
        return noticeService.noticeToPda(emp.getEmpId());
    }

    /**
     *  更新消息为已读
     * @param notice
     * @return
     */
    @RequestMapping("/changeNotice")
    public PdaResponseData changeNotice(@RequestBody Notice notice) {
        return noticeService.modifyNotice(notice);
    }

    /**
     *  新增退料表记录
     * @param list
     * @return
     */
    @RequestMapping("/addWithdrawal")
    public PdaResponseData addWithdrawal(@RequestBody List<Withdrawal> list) {
        PdaResponseData pdaResponseData = new PdaResponseData();
        noticeService.appendWithdrawal(list);
        return pdaResponseData;
    }

    /**
     *  新增配料表记录
     * @param vo
     * @return
     */
    @RequestMapping("/addCallOff")
    public PdaResponseData addCallOff(@RequestBody CallOffNoticeVo vo) {
        PdaResponseData pdaResponseData = new PdaResponseData();
        noticeService.appendCallOff(vo.getCallOff(),vo.getProduceOrder());
        return pdaResponseData;
    }

}
