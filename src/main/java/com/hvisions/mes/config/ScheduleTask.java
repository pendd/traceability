package com.hvisions.mes.config;

import com.hvisions.mes.service.INewPdaService.INewPdaNoticeService;
import com.hvisions.mes.service.IRepairEmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * @author dpeng
 */
@Component
@Slf4j
@Async
public class ScheduleTask {

    @Autowired
    private INewPdaNoticeService newPdaNoticeService;

    @Autowired
    private IRepairEmailService emailService;

    @Scheduled(cron = "0 * * * * ?")
    public void scheduleTest() {
        log.info("scheduleTest开始定时执行-1min");
        newPdaNoticeService.noticeTask();
    }

    @Scheduled(cron = "0 0 9 * * ?")
    public void scheduleTestDay() {
        log.info("scheduleTest开始定时执行-1day");
        emailService.queryToolingAndRepair();
        emailService.queryEquipmentAndRepair();
    }

    @Scheduled(cron = "0 0 * * * ?")
    public void scheduleTestHour() {
        log.info("scheduleTest开始定时执行-1h");
        newPdaNoticeService.noticeWithdrawalOverTime();
    }
}
