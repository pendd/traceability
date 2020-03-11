package com.hvisions.mes.service;

import java.util.List;

import com.hvisions.mes.domain.RoleMenu;

public interface IRoleMenuService {
    /**
     * 查询角色菜单
     * @param oRoleMenu
     * @return
     */
     List<RoleMenu> queryRoleMenuByRole(Integer roleId,Integer menuType);

     List<RoleMenu> queryRoleMenuOByRole(Integer roleId,Integer menuType);
     /**
      * 增加角色菜单
      * @param oRoleMenu
      * @return
      */
    void addRoleMenu(Integer roleId,Integer menuType,List<String> list);

    void addRoleOMenu(Integer roleId,Integer menuType,List<String> list);

     /**
     * 删除角色菜单
     * */
    void removeRoleMenu(List<Integer> delIDs);

    void removeRoleMenuByRoleId(Integer roleId);

    void removeOpMenuByRoleId(Integer roleId);
}
