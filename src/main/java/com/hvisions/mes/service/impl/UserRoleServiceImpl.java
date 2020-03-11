package com.hvisions.mes.service.impl;

import com.hvisions.mes.domain.Menu;
import com.hvisions.mes.domain.RoleMenu;
import com.hvisions.mes.domain.Tree;
import com.hvisions.mes.mapper.UserRoleMapper;
import com.hvisions.mes.service.IUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * @author dpeng
 * @create 2019-04-12 16:17
 */
@Service
public class UserRoleServiceImpl implements IUserRoleService {

    @Autowired
    private UserRoleMapper mapper;

    /**
     *  查询用户旗下所有能查看的菜单
     * @param userId   用户编号
     * @return
     */
    /*@Override
    public List<Menu> queryMenus(Integer userId) {

        // 一级 + 二级菜单
        List<Menu> list = new ArrayList<>();

        // 一级菜单
        List<Menu> firstMenus = mapper.selectFirstMenu(userId);

        for (Menu firstMenu : firstMenus) {
            List<Menu> secondMenus = mapper.selectSecondMenu(userId, firstMenu.getMenuId());

            Menu menu;
            menu = firstMenu;
            menu.setSecondMenus(secondMenus);

            list.add(menu);

        }
        return list;
    }*/

    /**
     *  查询用户旗下所有能查看的菜单
     * @param userId   用户编号
     * @return
     */
    @Override
    public List<Menu> queryMenus(Integer userId) {

        // 一级 + 二级菜单
        List<Menu> list = new ArrayList<>();

        // 一级菜单
        List<Menu> firstMenus = mapper.selectFirstMenuFromMenu();

        for (Menu firstMenu : firstMenus) {
            List<Menu> secondMenus = mapper.selectSecondMenu(userId, firstMenu.getMenuId());

            Menu menu;
            menu = firstMenu;
            menu.setSecondMenus(secondMenus);

            list.add(menu);
        }

        // 移除掉没有子菜单的一级菜单
        list.removeIf(e -> e.getSecondMenus().isEmpty());

        // 新增角色菜单表中的一级菜单
        List<Menu> menus = mapper.selectFirstMenuFromRoleMenu(userId);
        list.addAll(menus);

        // 排序
        list.sort(Comparator.comparing(Menu::getSort));
        return list;
    }

    /**
     *  查询所有一级菜单和对应的二级菜单    菜单树
     * @param menuType   菜单类型   pda端  web端
     * @return
     */
    @Override
    public List<Tree> queryAllMenus(Integer menuType,Integer roleId) {

        // 一级 + 二级菜单
        List<Tree> list = new ArrayList<>();

        // 一级菜单
        List<Tree> firstMenus = mapper.selectFMenu(menuType);

        for (Tree firstMenu : firstMenus) {
            List<Tree> secondMenus = mapper.selectSMenu(menuType, firstMenu.getId());

            // 获取角色下的所有操作菜单
            List<RoleMenu> roleMenus = mapper.selectAllRoleMenuByRoleId(roleId, menuType);

            // 遍历
            for (RoleMenu roleMenu : roleMenus) {
                for (Tree secondMenu : secondMenus) {
                    if (roleMenu.getMenuId().equals(secondMenu.getId())) {
                        secondMenu.setChecked(true);
                    }
                }
                if (firstMenu.getId().equals(roleMenu.getMenuId())) {
                    firstMenu.setChecked(true);
                }
            }

            Tree tree;
            tree = firstMenu;
            tree.setChildren(secondMenus);

            list.add(tree);
        }
        return list;
    }

    /**
     *  查询角色下的所有菜单权限
     * @param roleId
     * @param menuType
     * @return
     */
    @Override
    public List<RoleMenu> queryAllRoleMenuByRoleId(Integer roleId,Integer menuType) {
        return mapper.selectAllRoleMenuByRoleId(roleId,menuType);
    }

    /**
     *  更新角色权限表
     * @param menuId
     * @param roleId
     */
    @Override
    public void modifyRoleUser(String[] menuId, Integer roleId,Integer menuType) {
        mapper.deleteRoleMenuByRoleIdMenuType(roleId,menuType);
        for (String s : menuId) {
            mapper.insertRoleMenu(roleId,menuType,s);
        }
    }
}
