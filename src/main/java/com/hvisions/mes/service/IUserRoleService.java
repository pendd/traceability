package com.hvisions.mes.service;

import com.hvisions.mes.domain.Menu;
import com.hvisions.mes.domain.RoleMenu;
import com.hvisions.mes.domain.Tree;

import java.util.List;

public interface IUserRoleService {

    // 查询用户旗下一级菜单和二级菜单
    List<Menu> queryMenus(Integer userId);

    // 查询所有一级菜单和对应的二级菜单   菜单树
    List<Tree> queryAllMenus(Integer menuType,Integer roleId);

    /**
     *  查询角色下的所有菜单权限
     * @param roleId
     * @param menuType
     * @return
     */
    List<RoleMenu> queryAllRoleMenuByRoleId(Integer roleId,Integer menuType);

    /**
     *  更新角色权限表
     * @param menuId
     * @param roleId
     */
    void modifyRoleUser(String[] menuId,Integer roleId,Integer menuType);
}
