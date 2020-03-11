package com.hvisions.mes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.Menu;

import com.hvisions.mes.mapper.MenuMapper;
import com.hvisions.mes.service.IMenuService;

/**
 * 菜单Service
 *
 * @author wenkb
 * @since 2018-01-10
 */
@Service
public class MenuService implements IMenuService {
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> getVisibleMenu(Integer empId) {
        List<Menu> menus = menuMapper.selectVisibleMenu(empId);

        return menus;
    }

    @Override
    public List<Menu> getOperateMenu(Integer empId) {
        List<Menu> menus = menuMapper.selectOperateMenu(empId);

        return menus;
    }

    @Override
    public List<Menu> getTopMenu(Integer empId) {
        List<Menu> menus = menuMapper.selectTopMenu(empId);

        return menus;
    }

    @Override
    public List<Menu> getSubMenu(String topMenuID,Integer empId) {
        List<Menu> menus = menuMapper.selectSubMenu(topMenuID,empId);

        return menus;
    }

    @Override
    public List<Menu> getSubMenus(String topMenuID) {
        List<Menu> menus = menuMapper.selectSubMenus(topMenuID);

        return menus;
    }

    @Override
    public Page<Menu> queryMenuList(Page<Menu> page) {
        page.setRecords(menuMapper.selectMenuList(page));
        return page;
    }

    @Override
    public void editMenu(Menu oMenu) {
        menuMapper.updatemenu(oMenu);
    }
}
