package com.hvisions.mes.mapper;

import com.hvisions.mes.domain.Menu;
import com.hvisions.mes.domain.RoleMenu;
import com.hvisions.mes.domain.Tree;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户权限mapper
 *
 * @author dpeng
 * @modify 2019-04-12 15:20
 */
@Mapper
@Repository
public interface UserRoleMapper {
    /**
     *  查询一级菜单  用户
     * @param userId
     * @return
     */
    List<Menu> selectFirstMenu(Integer userId);

    /**
     *  从菜单表中查询所有以及菜单并且是web端的
     * @return
     */
    List<Menu> selectFirstMenuFromMenu();

    /**
     * 从用户角色菜单表中查询所有一级菜单  (在角色菜单表中出现的一级菜单 都是些没有二级菜单的独立项)
     * @param userId
     * @return
     */
    List<Menu> selectFirstMenuFromRoleMenu(Integer userId);

    /**
     *  查询二级菜单  用户
     * @param userId
     * @param menuId
     * @return
     */
    List<Menu> selectSecondMenu(@Param("userId") Integer userId, @Param("menuId") String menuId);

    /**
     *  查询所有一级菜单
     * @param menuType
     * @return
     */
    List<Tree> selectFMenu(Integer menuType);

    /**
     *  查询所有二级菜单
     * @param menuType
     * @param upMenuId
     * @return
     */
    List<Tree> selectSMenu(@Param("menuType") Integer menuType,@Param("upMenuId") String upMenuId);

    /**
     * 查询角色下的所有菜单权限
     *
     * @param roleId
     * @param menuType
     * @return
     */
    List<RoleMenu> selectAllRoleMenuByRoleId(@Param("roleId")Integer roleId, @Param("menuType")Integer menuType);

    /**
     *  根据角色ID 和 菜单类型删除
     * @param roleId
     * @param menuType
     */
    void deleteRoleMenuByRoleIdMenuType(@Param("roleId")Integer roleId, @Param("menuType")Integer menuType);

    /**
     *  新增
     * @param roleId
     * @param menuType
     * @param menuId
     */
    void insertRoleMenu(@Param("roleId")Integer roleId, @Param("menuType")Integer menuType, @Param("menuId")String menuId);

}
