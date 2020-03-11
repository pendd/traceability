package com.hvisions.mes.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.Email;
import com.hvisions.mes.domain.ReportAssembling;
import com.hvisions.mes.domain.ReportElectronic;
import com.hvisions.mes.service.IEmpCheckService;
import com.hvisions.mes.util.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author dpeng
 * @description 人员考核路由
 * @date 2019-07-31 14:39
 */
@RestController
@RequestMapping("/json/empCheck")
@ApiIgnore
public class EmpCheckController extends BaseController{

    @Autowired
    private IEmpCheckService checkService;

    /**
     *  分页查询所有电子车间的人员考核 按某个月
     * @param page
     * @param rows
     * @param dataId
     * @return
     */
    @RequestMapping("/getElectronic")
    public Map<String,Object> getElectronic(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "15") Integer rows,
            String dataId) {

        Calendar date;
        int month;
        Map<String,Object> result = new HashMap<>(16);
        if (StringUtil.isNull(dataId)) {
            date = Calendar.getInstance();
            month = date.get(Calendar.MONTH);
        } else {
            try {
                Date parse = new SimpleDateFormat("yyyy-MM").parse(dataId);
                date = Calendar.getInstance();
                date.setTime(parse);
                month = date.get(Calendar.MONTH) + 1;
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }

        Page<ReportElectronic> data = checkService.queryElectronicByMonth(new Page<>(page, rows), date.get(Calendar.YEAR), month, getCurrentUserID());
        result.put("total",data.getTotal());
        result.put("rows",data.getRecords());
        return result;
    }

    @PostMapping("/addElectronic")
    public String addElectronic(@RequestParam(value = "list") List<ReportElectronic> list) {
        System.out.println(list);
        return null;
    }

    @PostMapping("/modifyElectronic")
    public String modifyElectronic(ReportElectronic reportElectronic) {
        String flag = "false";
        try {
            checkService.modifyElectronic(reportElectronic);
            flag = "true";
        }catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     *  分页查询所有电子车间的人员考核 按某个月
     * @param page
     * @param rows
     * @param dataId
     * @return
     */
    @RequestMapping("/getAssembling")
    public Map<String,Object> getAssembling(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "15") Integer rows,
            String dataId) {

        Calendar date;
        int month;
        Map<String,Object> result = new HashMap<>(16);
        if (StringUtil.isNull(dataId)) {
            date = Calendar.getInstance();
            month = date.get(Calendar.MONTH);
        } else {
            try {
                Date parse = new SimpleDateFormat("yyyy-MM").parse(dataId);
                date = Calendar.getInstance();
                date.setTime(parse);
                month = date.get(Calendar.MONTH) + 1;
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }

        Page<ReportAssembling> data = checkService.queryAssemblingByMonth(new Page<>(page, rows), date.get(Calendar.YEAR), month, getCurrentUserID());
        result.put("total",data.getTotal());
        result.put("rows",data.getRecords());
        return result;
    }

    @PostMapping("/modifyAssembling")
    public String modifyAssembling(ReportAssembling reportAssembling) {
        String flag = "false";
        try {
            checkService.modifyAssembling(reportAssembling);
            flag = "true";
        }catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}
