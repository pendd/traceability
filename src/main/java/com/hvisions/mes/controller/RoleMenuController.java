package com.hvisions.mes.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import springfox.documentation.annotations.ApiIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.hvisions.mes.domain.RoleMenu;

import com.hvisions.mes.service.IRoleMenuService;
import com.hvisions.mes.util.StringUtil;

@RestController
@RequestMapping("/json/RoleMenu")
@ApiIgnore
public class RoleMenuController  extends BaseController{

    @Autowired
    private IRoleMenuService RoleMenuService;

    @RequestMapping(value = "/addMenu", method = RequestMethod.POST)
    public String addRoleMenu(String roleId,Integer menuType,@RequestParam("list") List<String> list)
    {   try
        {
           //Integer userId=getCurrentUserID();
           RoleMenuService.addRoleMenu(Integer.valueOf(roleId),menuType, list);
           return "true";

         }
         catch (Exception ex)
         {
             return "false";
         }


    }

    @RequestMapping(value = "/addOMenu", method = RequestMethod.POST)
    public String addRoleOMenu(String roleId,Integer menuType,@RequestParam("list") List<String> list)
    {
        try
        {
           //Integer userId=getCurrentUserID();
           RoleMenuService.addRoleOMenu(Integer.valueOf(roleId),menuType, list);
           return "true";

         }
         catch (Exception ex)
         {
             return "false";
         }


    }
   @RequestMapping(value = "/SelectedRoleMenu", method = RequestMethod.POST)
    public List<RoleMenu> SelectedRoleMenu(@RequestParam("roleId")String roleId,Integer menuType)
   {

        Integer value=StringUtil.isNotNull(roleId)?Integer.valueOf(roleId):null;
        List<RoleMenu> ilRoleMenu=RoleMenuService.queryRoleMenuByRole(value,menuType);
        return ilRoleMenu;
   }


   @RequestMapping(value = "/SelectedRoleOMenu", method = RequestMethod.POST)
   public List<RoleMenu> SelectedRoleOMenu(@RequestParam("roleId")String roleId,Integer menuType)
  {

       Integer value=StringUtil.isNotNull(roleId)?Integer.valueOf(roleId):null;
       List<RoleMenu> ilRoleMenu=RoleMenuService.queryRoleMenuOByRole(value,menuType);
       return ilRoleMenu;
  }









   //处理不规则list****************************
    public List<String> getTureList(List<String> roleRightList) {
        String str="";
        for (int i = 0; i < roleRightList.size(); i++) {
            str += roleRightList.get(i);
        }
        String str1 = str.replace(" ", "");
        String[] strArray=str1.split("_");
        List<String> l1 = new ArrayList<>();
    for (String string : strArray) {
        l1.add(string);
    }
    String s1 = "";
    for (int i = 1; i < l1.size(); i++) {
        s1 += l1.get(i)+",";
    }
    String str2 = s1.replace("A", ",A");
    String[] strArray1 = str2.split(",");
    List<String> l2 = new ArrayList<>();
    for (String string : strArray1) {
        l2.add(string);
    }
    System.out.println(l2);
        return l2;
    }
}
