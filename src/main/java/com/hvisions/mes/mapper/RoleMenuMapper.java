package com.hvisions.mes.mapper;

import com.hvisions.mes.domain.RoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface RoleMenuMapper {


    /**
     * 查询角色浏览菜单
     * @return
     */
    List<RoleMenu> selectRoleMenuByRole(@Param("roleId")Integer roleId,@Param("menuType")Integer menuType);

    /**
     * 增加角色浏览菜单
     * @return
     */
    void insertRoleMenu(RoleMenu oRoleMenu);

    /**
     * 删除角色浏览菜单
     * */
    void deleteRoleMenu(@Param("delIDs")List<Integer> delIDs);



    /**
     * 查询角色操作菜单
     * @return
     */
    List<RoleMenu> selectRoleMenuOByRole(@Param("roleId")Integer roleId,@Param("menuType")Integer menuType);

    /**
     * 增加角色操作菜单
     * @return
     */
    void insertRoleOMenu(RoleMenu oRoleMenu);

    /**
     * 删除角色操作菜单
     * */
    void deleteRoleOMenu(@Param("delIDs")List<Integer> delIDs);

    void deleteRoleMenuByRoleId(@Param("roleId")Integer roleId);

    void deleteOpMenuByRoleId(@Param("roleId")Integer roleId);
}
