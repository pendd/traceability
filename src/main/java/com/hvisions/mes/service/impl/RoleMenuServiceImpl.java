package com.hvisions.mes.service.impl;

import com.hvisions.mes.domain.RoleMenu;
import com.hvisions.mes.mapper.RoleMenuMapper;
import com.hvisions.mes.service.IRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoleMenuServiceImpl implements IRoleMenuService{

    @Autowired
    private RoleMenuMapper roleMenuMapper;
    @Override
    public List<RoleMenu> queryRoleMenuByRole(Integer roleId,Integer menuType){

        List<RoleMenu> ilRoleMenu= roleMenuMapper.selectRoleMenuByRole(roleId,menuType);
        return ilRoleMenu;
    }

    @Override
    public List<RoleMenu> queryRoleMenuOByRole(Integer roleId,Integer menuType){

        List<RoleMenu> ilRoleMenu= roleMenuMapper.selectRoleMenuOByRole(roleId,menuType);
        return ilRoleMenu;
    }

    @Override
    public void addRoleMenu(Integer roleId,Integer menuType,List<String> list){
        if(list.size()!=0){
        roleMenuMapper.deleteOpMenuByRoleId(roleId);
        for(int i=0;i<list.size();i++)
        {
             RoleMenu oRoleMenu=new RoleMenu();
             oRoleMenu.setRoleId(roleId);
             oRoleMenu.setMenuId(list.get(i));
             oRoleMenu.setMenuType(menuType);
            roleMenuMapper.insertRoleMenu(oRoleMenu);

        }

        }

    }
    @Override
    public void addRoleOMenu(Integer roleId,Integer menuType,List<String> list){

        if(list.size()!=0)
        {
            roleMenuMapper.deleteOpMenuByRoleId(roleId);
            for(int i=0;i<list.size();i++)
           {
             RoleMenu oRoleMenu=new RoleMenu();
             oRoleMenu.setRoleId(roleId);
             oRoleMenu.setMenuId(list.get(i));
             oRoleMenu.setMenuType(menuType);
            roleMenuMapper.insertRoleOMenu(oRoleMenu);

          }

        }

    }



    @Override
   public  void removeRoleMenu(List<Integer> delIDs)
    {

        roleMenuMapper.deleteRoleMenu(delIDs);
    }

    @Override
    public void removeRoleMenuByRoleId(Integer roleId) {
        // TODO Auto-generated method stub
        roleMenuMapper.deleteRoleMenuByRoleId(roleId);
    }

    @Override
    public void removeOpMenuByRoleId(Integer roleId) {
        // TODO Auto-generated method stub
        roleMenuMapper.deleteRoleMenuByRoleId(roleId);
    }
}
