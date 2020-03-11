package com.hvisions.mes.service.impl;


import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.Schedule;
import com.hvisions.mes.mapper.ScheduleMapper;
import com.hvisions.mes.service.IScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ScheduleServiceImpl implements IScheduleService {
    @Autowired
    private ScheduleMapper scheduleMapper;

    @Override
    public Page<Schedule> showSchedule(Page<Schedule> page) {
        page.setRecords(scheduleMapper.selectSchedule(page));
        return page;
    }


    @Override
    public String addSchedule(Schedule schedule) {
        String res;
        Schedule newSchedule = scheduleMapper.selectScheduleByTeamId(schedule.getTeamId());

        if (newSchedule == null) {
            // 不存在相同班组的排班
            try {
                scheduleMapper.insertSchedule(schedule);
                res = "true";
            } catch (Exception e) {
                res = "false";
                e.printStackTrace();
            }
        }else {
            res = "1";
        }
        return res;
    }

    @Override
    public String modSchedule(Schedule schedule) {
        String res;
        Schedule newSchedule = scheduleMapper.selectScheduleByTeamId(schedule.getTeamId());

        if (newSchedule == null || Objects.equals(schedule.getTeamId(),newSchedule.getTeamId())) {
            // 不存在相同班组的排班
            try {
                scheduleMapper.updateSchedule(schedule);
                res = "true";
            } catch (Exception e) {
                res = "false";
                e.printStackTrace();
            }
        }else {
            res = "1";
        }
        return res;
    }

    @Override
    public void DelSchedule(Integer ids) {

        scheduleMapper.deleteSchedule(ids);
    }
}
