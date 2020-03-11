package com.hvisions.mes.service.impl;

import com.hvisions.mes.domain.EquipmentRepair;
import com.hvisions.mes.domain.EquipmentReply;
import com.hvisions.mes.domain.ToolingRepair;
import com.hvisions.mes.domain.ToolingReply;
import com.hvisions.mes.mapper.EquipmentReplyMapper;
import com.hvisions.mes.mapper.RepairEmailMapper;
import com.hvisions.mes.mapper.ToolingReplyMapper;
import com.hvisions.mes.service.IRepairEmailService;
import org.joda.time.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author dpeng
 * @description
 * @date 2019-08-29 13:32
 */
@Service
public class RepairEmailServiceImpl implements IRepairEmailService {
    
    @Autowired
    private RepairEmailMapper emailMapper;

    @Value("${spring.mail.username}")
    private String fromUser;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private ToolingReplyMapper replyMapper;

    @Autowired
    private EquipmentReplyMapper equipmentReplyMapper;

    /**
     *  设备维保
     */
    @Override
    public void queryEquipmentAndRepair() {
        List<EquipmentRepair> list = emailMapper.selectEquipmentAndRepair();

        // 去掉责任人邮箱不存在得记录
        list.removeIf(e -> e.getTaskUserEmail() == null || Objects.equals(e.getTaskUserEmail(),""));

        for (EquipmentRepair equipmentRepair : list) {
            // 判断是单位是 天 月 年
            Integer unit = equipmentRepair.getUnit();
            // 时间周期
            Integer interval = equipmentRepair.getRepairInterval();
            boolean flag = isTodayRepair(equipmentRepair.getCreateTime(), unit, interval);

            if (flag) {
                // 今天就是维保日期 发邮件提醒
                SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
                //发件人
                simpleMailMessage.setFrom(fromUser);
                //收件人
                simpleMailMessage.setTo(new String[]{equipmentRepair.getTaskUserEmail()});
                //邮件主题
                simpleMailMessage.setSubject(equipmentRepair.getEquipmentName() + "维保");
                //邮件内容
                simpleMailMessage.setText(new SimpleDateFormat("yyyy-MM-dd").format(DateTime.now().plusDays(1).toDate()) + " " + equipmentRepair.getEquipmentName() + "需要进行维保");
                javaMailSender.send(simpleMailMessage);

                // 往反馈表新增记录
                EquipmentReply reply = new EquipmentReply();
                reply.setEquipmentId(equipmentRepair.getEquipmentId());
                reply.setEquipmentName(equipmentRepair.getEquipmentName());
                reply.setTaskUser(equipmentRepair.getTaskUser());
                equipmentReplyMapper.insertEquipmentReply(reply);
            }
        }
    }

    /**
     *  工装维保
     */
    @Override
    public void queryToolingAndRepair() {
        List<ToolingRepair> list = emailMapper.selectToolingAndRepair();

        list.removeIf(e -> (e.getTaskUserEmail() == null || Objects.equals(e.getTaskUserEmail(),""))
                && (e.getPrincipleEmail() == null || Objects.equals(e.getPrincipleEmail(),"")));

        for (ToolingRepair toolingRepair : list) {
            // 判断是单位是 天 月 年
            Integer unit = toolingRepair.getUnit();
            // 时间周期
            Integer interval = toolingRepair.getRepairInterval();
            boolean flag = isTodayRepair(toolingRepair.getCreateTime(), unit, interval);

            if (flag) {
                // 今天就是维保日期 发邮件提醒
                SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
                //发件人
                simpleMailMessage.setFrom(fromUser);

                //收件人
                if (toolingRepair.getTaskUserEmail() == null || Objects.equals(toolingRepair.getTaskUserEmail(),"")) {
                    simpleMailMessage.setTo(new String[]{toolingRepair.getPrincipleEmail()});
                }else if (toolingRepair.getPrincipleEmail() == null || Objects.equals(toolingRepair.getPrincipleEmail(),"")) {
                    simpleMailMessage.setTo(new String[]{toolingRepair.getTaskUserEmail()});
                }else {
                    simpleMailMessage.setTo(new String[]{toolingRepair.getTaskUserEmail(),toolingRepair.getPrincipleEmail()});
                }

                //邮件主题
                simpleMailMessage.setSubject(toolingRepair.getToolingName() + "维保");
                //邮件内容
                simpleMailMessage.setText(new SimpleDateFormat("yyyy-MM-dd").format(DateTime.now().plusDays(1).toDate()) + " " + toolingRepair.getToolingName() + "需要进行维保");
                javaMailSender.send(simpleMailMessage);

                // 往反馈表新增记录
                ToolingReply reply = new ToolingReply();
                reply.setToolingId(toolingRepair.getToolingId());
                reply.setToolingName(toolingRepair.getToolingName());
                reply.setTaskUser(toolingRepair.getTaskUser());
                reply.setPrinciple(toolingRepair.getPrinciple());
                replyMapper.insertToolingReply(reply);
            }

        }
    }

    /**
     *  判断明天是否是维保日期   提前通知
     * @param createTime  维保一开始设定时间
     * @param unit        维保间隔单位
     * @param interval    维保间隔时长
     * @return            true 今天是维保日期   false  今天不是维保日期
     */
    private boolean isTodayRepair(Date createTime, Integer unit, Integer interval) {
        boolean flag = false;
        if (unit == 2) {
            // 天
            int days = Days.daysBetween(new DateTime(createTime), DateTime.now().plusDays(1)).getDays();
            if (days >= 0 && days % interval == 0) {
                // 能整除 今天是维保时间
                flag = true;
            }
        }else if (unit == 3) {
            // 月
            int months = Months.monthsBetween(new DateTime(createTime), DateTime.now()).getMonths();
            if (months >= 0 && months % interval == 0) {
                // 月份满足 判断天数是否满足
                if (Objects.equals(new DateTime(createTime).getDayOfMonth(),DateTime.now().plusDays(1).getDayOfMonth())) {
                    flag = true;
                }
            }
        }else if (unit == 4) {
            // 年
            int years = Years.yearsBetween(new DateTime(createTime), DateTime.now()).getYears();
            if (years >= 0 && years % interval == 0) {
                if (Objects.equals(new DateTime(createTime).getMonthOfYear(),DateTime.now().getMonthOfYear()) && Objects.equals(new DateTime(createTime).getDayOfMonth(),DateTime.now().plusDays(1).getDayOfMonth())){
                    flag = true;
                }
            }
        }
        return flag;
    }

}
