package com.hvisions.mes.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hvisions.mes.domain.Menu;
import org.springframework.stereotype.Repository;

/**
 * 菜单数据库接口
 *
 * @author wenkb
 * @since 2018-01-10
 */

@Mapper
@Repository
public interface MenuMapper {

    /**
     * 查询所有可显示的菜单
     *
     * @return 所有可显示的菜单列表
     */
    List<Menu> selectVisibleMenu(@Param("empId") Integer empId);

    List<Menu>selectOperateMenu(@Param("empId") Integer empId);

    /**
     * 查询一级菜单
     *
     * @return 一级菜单列表
     */
    List<Menu> selectTopMenu(@Param("empId") Integer empId);

    /**
     * 查询二级菜单
     *
     * @param topMenuID 一级菜单ID
     * @return 二级菜单列表
     */
    List<Menu> selectSubMenu(@Param("topMenuID") String topMenuID,@Param("empId") Integer empId);


    /**
     * 查询二级菜单
     *
     * @param topMenuID 一级菜单ID
     * @return 二级菜单列表
     */
    List<Menu> selectSubMenus(@Param("topMenuID") String topMenuID);

    List<Menu> selectMenuList(Pagination page);

    void updatemenu(Menu oMenu);
}
