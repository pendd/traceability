package com.hvisions.mes.service.impl;

import static org.mockito.Mockito.ignoreStubs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.aspectj.weaver.tools.cache.AsynchronousFileCacheBacking.RemoveCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;

import com.hvisions.mes.domain.Role;

import com.hvisions.mes.domain.Tree;
import com.hvisions.mes.mapper.RoleMapper;
import com.hvisions.mes.mapper.RoleMenuMapper;
import com.hvisions.mes.service.IRoleService;
import com.hvisions.mes.util.CookieUtil;
@Service
public class RoleServiceImpl  implements IRoleService{

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;
    @Autowired
    HttpServletRequest request;


    @Override
    public Page<Role> queryRole(Page<Role> page, String roleName) {
        String value=CookieUtil.getCookieValue(request, "language");
        page.setRecords(roleMapper.selectRole(page, roleName,value));
        return page;
    }

    @Override
    public Role queryRoleByName(String roleName ,Integer roleId){
        return roleMapper.selectRoleByName(roleName,roleId);
    }
    @Override
    public void addRole(Role oRole){
         roleMapper.insertRole(oRole);

    }
    @Override
    public  boolean editRole(Role oRole)
    {
        boolean result=false;
        Integer roleId=oRole.getRoleId();
        Role role = roleMapper.selectRoleByRoleId(roleId);
        if(role != null)
        {
            roleMapper.updateRole(oRole);
            //roleMapper.delRoleEqpt(roleId);
            result=true;
        }
        else{
            result=false;
        }
        return result;

    }
    @Override
    public void removeRole(List<Integer> delIDs)
    {
        //roleMenuMapper.deleteRoleMenu(delIDs);

        roleMapper.deleteRole(delIDs);

    }
    @Override
    public void removeRoleEqpt(Integer roleId)
    {

        roleMapper.delRoleEqpt(roleId);
    }
    @Override
     public List<Tree> selectMenu(Integer menuType){
         return  roleMapper.selectMenu(menuType);
     }

    @Override
    public void addRoleEqpt(Integer roleId, Integer eqId) {
        roleMapper.insertRoleEqpt(roleId, eqId);

    }

    @Override
    public void delRoleEqpt(Integer roleId) {


    }

    @Override
    public List<Role> getPath(Integer roleId) {
        List<Role> list = roleMapper.getPath(roleId);
        //ist.add(roleMapper.getPath(roleId));
        return list;
    }

    @Override
    public boolean queryCountBYRole(Integer roleId){
        boolean result=false;
        Role role = roleMapper.selectRoleByRoleId(roleId);
        if(role == null)
        {
        result=true;
        }
        else{
            result=false;
        }
        return result;
    }

    @Override
    public Role getIpPort(Integer empId) {
        // TODO Auto-generated method stub
        return roleMapper.getIpPort(empId);
    }

    @Override
    public List<Integer> geteqIdByRoleId(Integer roleId) {
        // TODO Auto-generated method stub
        return roleMapper.selecteqIdByRoleId(roleId);
    }

    @Override
    public List<String> queryRoleByEqId(List<Integer> delIDs, Integer eqId) {
        // TODO Auto-generated method stub
        return roleMapper.selectRoleByEqId(delIDs, eqId);
    }

    @Override
    public List<Integer> queryEqId(List<Integer> roleId) {
        // TODO Auto-generated method stub
        return roleMapper.selectEqId(roleId);
    }


    @Override
    public boolean determineEqptByUser(Integer empId,Integer roleId)
    {
        boolean result=false;
        if(roleMapper.selectEqptByUser(empId, roleId)!=0)
        {
            result=true;
        }
        else
        {

            result=false;
        }
        return result;
    }



}
