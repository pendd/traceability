package com.hvisions.mes.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import springfox.documentation.annotations.ApiIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hvisions.mes.domain.SystemLanguage;
import com.hvisions.mes.dto.Result;

import com.hvisions.mes.service.ISystemLanguageService;
import com.hvisions.mes.util.CookieUtil;

@ApiIgnore
@RestController
@RequestMapping("/json/systemLanguage")
public class SystemLanguageController {
    @Autowired
    private ISystemLanguageService systemLanguageService;

//    @Autowired
//    private IServerHostService serverHostService;

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public Result searchDetail(HttpServletRequest request, HttpServletResponse response) {
        //List<SystemLanguage> data = systemLanguageService.querySystemLanguage();
        String data = CookieUtil.getCookieValue(request, "emp_id");
        return Result.newSuccessResult(data);
    }

//    @RequestMapping(value = "/edit", method = RequestMethod.POST)
//    public Result edit(String langugeSystem) {
//
//            SystemLanguage oSystemLanguage=new SystemLanguage();
//            oSystemLanguage.setSystemLanguage(langugeSystem);
//            oSystemLanguage.setCurrentLanguage(1);
//            systemLanguageService.editSystemLanguage(oSystemLanguage);
//            oSystemLanguage.setCurrentLanguage(0);
//            systemLanguageService.editSystemLanguage(oSystemLanguage);
//            String serverHost=serverHostService.selectSeiverHost();
//            String downPath=serverHost.split(":")[1].split("//")[1]+","+serverHost.split(":")[2];
//            return Result.newSuccessResult(downPath);
//
//
//
//    }


}
