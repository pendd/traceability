package com.hvisions.mes.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.Menu;

/**
 * 菜单Service接口
 *
 * @author wenkb
 * @since 2018-01-10
 */
public interface IMenuService {
    /**
     * 获取所有可显示的菜单
     *
     * @return 所有可显示的菜单列表
     */
    List<Menu> getVisibleMenu(Integer empId);


    List<Menu> getOperateMenu(Integer empId);

    /**
     * 获取一级菜单
     *
     * @return 一级菜单列表
     */
    List<Menu> getTopMenu(Integer empId);

    /**
     * 获取二级菜单
     *
     * @param topMenuID 一级菜单ID
     * @return 二级菜单列表
     */
    List<Menu> getSubMenu(String topMenuID,Integer empId);

    /**
     * 获取菜单列表
     *
     * @param
     * @return
     */
    Page<Menu> queryMenuList(Page<Menu> page);

    /**
     * 更新菜单
     *
     * @param
     * @return
     */
    void editMenu(Menu oMenu);

    /**
     * 获取菜单列表
     *
     * @param
     * @return
     */
    List<Menu> getSubMenus(String topMenuID);
}
