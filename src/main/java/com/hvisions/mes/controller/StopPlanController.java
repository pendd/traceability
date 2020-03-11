package com.hvisions.mes.controller;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import springfox.documentation.annotations.ApiIgnore;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.StopPlanAuto;
import com.hvisions.mes.domain.StopPlanManual;
import com.hvisions.mes.service.IStopPlanService;

@RestController
@RequestMapping("/stopplan")
@ApiIgnore
public class StopPlanController {

    @Autowired
    private IStopPlanService stopPlanService;

    @PostMapping("/Autolist")
    public Map<String, Object> stopPlanAutolistpage(
            @RequestParam(value = "activeBeginDate", required = false) String stractiveBeginDate,
            @RequestParam(value = "activeEndDate", required = false) String stractiveEndDate,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer rows) throws ParseException {

        if (!"".equals(stractiveBeginDate) && stractiveBeginDate != null) {
            stractiveBeginDate = stractiveBeginDate + " 12:00:00";
        }

        if (!"".equals(stractiveEndDate) && stractiveBeginDate != null) {
            stractiveEndDate = stractiveEndDate + " 12:00:00";
        }

        Page<StopPlanAuto> data = stopPlanService.queryStopPlanAuto(
                new Page<StopPlanAuto>(page, rows), stractiveBeginDate, stractiveEndDate);
        Map<String, Object> result = new HashMap<>();
        result.put("total", data.getTotal());
        result.put("rows", data.getRecords());
        return result;
    }

    @PostMapping("/Manuallist")
    public Map<String, Object> stopPlanManuallistpage(
            @RequestParam(value = "stopBeginTime", required = false) String strstopBeginTime,
            @RequestParam(value = "stopEndTime", required = false) String strstopEndTime,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer rows) throws ParseException {

        if (!"".equals(strstopBeginTime) && strstopBeginTime != null) {
            strstopBeginTime = strstopBeginTime + " 00:00:01";
        }

        if (!"".equals(strstopEndTime) && strstopEndTime != null) {
            strstopEndTime = strstopEndTime + " 23:59:59";
        }

        Page<StopPlanManual> data = stopPlanService.queryStopPlanManual(
                new Page<StopPlanManual>(page, rows), strstopBeginTime, strstopEndTime);
        Map<String, Object> result = new HashMap<>();
        result.put("total", data.getTotal());
        result.put("rows", data.getRecords());
        return result;
    }

    @PostMapping("/addStopPlan")
    public String addStopPlan( //
            String stopReason, //
            String activeBeginDate, //
            String activeEndDate, //
            String activeWeeks, //
            String stopBeginTime, //
            String stopEndTime, //
            String type //
    ) throws ParseException {
        try {
            DateFormat dateFormats = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // 自动停机
            if (type.equals("1")) {
                activeBeginDate = activeBeginDate + " 12:00:00";
                Date time = dateFormats.parse(activeBeginDate);
                String str = dateFormats.format(time);// 时间存储为字符串
                Timestamp beginDate = Timestamp.valueOf(str);
                activeEndDate = activeEndDate + " 12:00:00";
                Date times = dateFormats.parse(activeEndDate);
                String strs = dateFormats.format(times);// 时间存储为字符串
                Timestamp endDate = Timestamp.valueOf(strs);
                String[] stopBeginTimes = stopBeginTime.split(",");
                String strStopBeginTime = stopBeginTimes[0];
                Time StopBeginTime = Time.valueOf(strStopBeginTime);
                String[] stopEndTimes = stopEndTime.split(",");
                String strStopEndTime = stopEndTimes[0];
                Time StopEndTime = Time.valueOf(strStopEndTime);
                if (StopEndTime.compareTo(StopBeginTime) < 0) {
                    return "timeOut";
                }
                StopPlanAuto oStopPlanAuto = new StopPlanAuto();
                oStopPlanAuto.setStopReason(stopReason);
                oStopPlanAuto.setActiveBeginDate(beginDate);
                oStopPlanAuto.setActiveEndDate(endDate);
                oStopPlanAuto.setActiveWeeks(activeWeeks.replace(',', '|'));
                oStopPlanAuto.setStopBeginTime(StopBeginTime);
                oStopPlanAuto.setStopEndTime(StopEndTime);
                stopPlanService.addStopPlanAuto(oStopPlanAuto);
                // 手动停机
            } else if (type.equals("2")) {
                Date time = dateFormats.parse(stopBeginTime);
                String str = dateFormats.format(time);// 时间存储为字符串
                Timestamp beginDate = Timestamp.valueOf(str);
                Date times = dateFormats.parse(stopEndTime);
                String strs = dateFormats.format(times);// 时间存储为字符串
                Timestamp endDate = Timestamp.valueOf(strs);
                StopPlanManual oStopPlanManual = new StopPlanManual();
                oStopPlanManual.setStopReason(stopReason);
                oStopPlanManual.setStopBeginTime(beginDate);
                oStopPlanManual.setStopEndTime(endDate);
                stopPlanService.addStopPlanManual(oStopPlanManual);
            }
            return "true";
        } catch (Exception e) {
            return "false";
        }

    }

    @PostMapping("/editStopPlan")
    public String editStopPlan(String stopReason, String activeBeginDate, String activeEndDate,
            String activeWeeks, String stopBeginTime, String stopEndTime, String type,
            String serialId) throws ParseException {
        DateFormat dateFormats = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 自动停机
        if (type.equals("1")) {
            activeBeginDate = activeBeginDate + " 12:00:00";
            Date time = dateFormats.parse(activeBeginDate);
            String str = dateFormats.format(time);// 时间存储为字符串
            Timestamp beginDate = Timestamp.valueOf(str);
            activeEndDate = activeEndDate + " 12:00:00";
            Date times = dateFormats.parse(activeEndDate);
            String strs = dateFormats.format(times);// 时间存储为字符串
            Timestamp endDate = Timestamp.valueOf(strs);

            String[] stopBeginTimes = stopBeginTime.split(",");
            String strStopBeginTime = stopBeginTimes[0];
            Time StopBeginTime = Time.valueOf(strStopBeginTime);
            String[] stopEndTimes = stopEndTime.split(",");
            String strStopEndTime = stopEndTimes[0];
            Time StopEndTime = Time.valueOf(strStopEndTime);
            StopPlanAuto oStopPlanAuto = new StopPlanAuto();
            oStopPlanAuto.setStopReason(stopReason);
            oStopPlanAuto.setActiveBeginDate(beginDate);
            oStopPlanAuto.setActiveEndDate(endDate);
            oStopPlanAuto.setActiveWeeks(activeWeeks.replace(',', '|'));
            oStopPlanAuto.setStopBeginTime(StopBeginTime);
            oStopPlanAuto.setStopEndTime(StopEndTime);
            oStopPlanAuto.setSerialId(Integer.valueOf(serialId));
            stopPlanService.editStopPlanAuto(oStopPlanAuto);
            // 手动停机
        } else if (type.equals("2")) {
            Date time = dateFormats.parse(stopBeginTime);
            String str = dateFormats.format(time);// 时间存储为字符串
            Timestamp beginDate = Timestamp.valueOf(str);
            Date times = dateFormats.parse(stopEndTime);
            String strs = dateFormats.format(times);// 时间存储为字符串
            Timestamp endDate = Timestamp.valueOf(strs);
            StopPlanManual oStopPlanManual = new StopPlanManual();
            oStopPlanManual.setStopReason(stopReason);
            oStopPlanManual.setStopBeginTime(beginDate);
            oStopPlanManual.setStopEndTime(endDate);
            oStopPlanManual.setSerialId(Integer.valueOf(serialId));
            stopPlanService.editStopPlanManual(oStopPlanManual);
        }
        return "true";
    }

    @PostMapping("/removeStopPlan")
    public String removeLubricationPlan(
            @RequestParam(value = "idsAuto", required = false) List<Integer> delIDs,
            @RequestParam(value = "idsManual", required = false) List<Integer> delIDss) {
        try {
            if (CollectionUtils.isNotEmpty(delIDs)) {
                for (int i = 0; i < delIDs.size(); i++) {
                    Integer serialId = delIDs.get(i);
                    stopPlanService.removeStopPlanAuto(serialId);
                }
            }
            if (CollectionUtils.isNotEmpty(delIDss)) {
                for (int i = 0; i < delIDss.size(); i++) {
                    Integer serialId = delIDss.get(i);
                    stopPlanService.removeStopPlanManual(serialId);
                }
            }
            return "true";
        } catch (Exception e) {
            return "false";
        }

    }

}
