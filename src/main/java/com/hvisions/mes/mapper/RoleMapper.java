package com.hvisions.mes.mapper;

import java.util.List;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import com.hvisions.mes.domain.Role;
import com.hvisions.mes.domain.Tree;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RoleMapper {

    List<Role> selectRole(Pagination page, @Param("roleName")String roleName,@Param("language")String language);

    Role selectRoleByName(@Param("roleName")String roleName,@Param("roleId")Integer roleId);
    Role getIpPort(@Param("empId")Integer empId);

    void insertRole(Role oRole);

    void updateRole(Role oRole);

    void deleteRole(@Param("delIDs")List<Integer> delIDs);

    List<Tree> selectMenu(@Param("menuType")Integer menuType);

   void insertRoleEqpt(@Param("roleId")Integer roleId,@Param("eqId")Integer eqId);

    List<Role> getPath(@Param("roleId")Integer roleId);

    void delRoleEqpt(@Param("roleId")Integer roleId);

    Role selectRoleByRoleId(@Param("roleId")Integer roleId);

    List<Integer> selecteqIdByRoleId(@Param("roleId")Integer roleId);

    List<String> selectRoleByEqId(@Param("delIDs")List<Integer> delIDs,@Param("eqId")Integer eqId);

    List<Integer> selectEqId(@Param("roleId")List<Integer> roleId);



    Integer selectEqptByUser(@Param("empId")Integer empId,@Param("roleId")Integer roleId);

}
