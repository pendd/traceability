package com.hvisions.mes.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.Schedule;
import com.hvisions.mes.domain.Team;
import com.hvisions.mes.service.impl.ScheduleServiceImpl;
import com.hvisions.mes.service.impl.TeamServiceImpl;
import springfox.documentation.annotations.ApiIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/json/Schedule")
@ApiIgnore
public class ScheduleController extends BaseController {
    @Autowired
    private ScheduleServiceImpl scheduleService;
    @Autowired
    private TeamServiceImpl teamService;

    @RequestMapping(value = "/ScheduleListPage", method = RequestMethod.GET)
    public Map<String, Object> ScheduleListPage (
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "15") Integer rows) {
        Page<Schedule> data = scheduleService.showSchedule(new Page<Schedule>(page, rows));
        Map<String, Object> result = new HashMap<>();
        result.put("total", data.getTotal());
        result.put("rows", data.getRecords());
        return result;
    }



    @RequestMapping(value =  "/addSchedule", method = RequestMethod.POST)
    public String addMould(Schedule schedule) {
        schedule.setCreateTime(new Date());
        schedule.setUpdateTime(new Date());
        schedule.setUserId(getCurrentUserID());
        schedule.setUpdateUserId(getCurrentUserID());
        return scheduleService.addSchedule(schedule);
    }

    @RequestMapping(value = "/removeSchedule", method = RequestMethod.POST)
    public String removeMould(@RequestParam("delIDs") List<String> delIDs ) {
        String res = "true";
        try {

            for(int i=0;i<delIDs.size();i++)
            {
                scheduleService.DelSchedule(Integer.valueOf(delIDs.get(i)));
            }
        } catch (Exception ex) {
            res = "false";
        }
        return res;
    }

    @RequestMapping(value =  "/editSchedule", method = RequestMethod.POST)
    public String editMould(Schedule schedule) {

        schedule.setUpdateTime(new Date());
        schedule.setUserId(getCurrentUserID());
        schedule.setUpdateUserId(getCurrentUserID());
        return scheduleService.modSchedule(schedule);

    }

    @RequestMapping(value = "/combox",method = RequestMethod.GET)
    public List<Team> getCombox(@RequestParam(value = "q",required = false)String teamName){
        return teamService.queryTeamCombobox(teamName);
    }

/*//时间戳字符串转 java util date 类型
    public Date getDate(String timestamp){
        Long time = Long.parseLong(timestamp);
        Date date = new Date(time);
        return date;
    }
    //日期字符串转java util date 类型
    public  String getMyDate(String strDate) {
        //注意：SimpleDateFormat构造函数的样式与strDate的样式必须相符
//        SimpleDateFormat sDateFormat=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss"); //加上时间
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //加上时间
        //必须捕获异常
        try {
            Date date=sDateFormat.parse(strDate);
            long ls = date.getTime();
            String st = String.valueOf(ls);
            return st;
        } catch(ParseException px) {
            px.printStackTrace();
            return "false";
        }
    }*/
}
