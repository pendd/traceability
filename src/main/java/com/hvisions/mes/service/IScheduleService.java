package com.hvisions.mes.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.Schedule;

import java.util.List;

public interface IScheduleService {

    Page<Schedule> showSchedule(Page<Schedule> page);

    String addSchedule(Schedule schedule);


    String modSchedule(Schedule schedule);


    void DelSchedule(Integer ids);
}
