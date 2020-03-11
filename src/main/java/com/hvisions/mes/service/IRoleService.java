
package com.hvisions.mes.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;

import com.hvisions.mes.domain.Role;
import com.hvisions.mes.domain.Tree;



public interface IRoleService {

  //List<Tree> selectMenu(Integer type,Integer eqId,String upid);

  //List<Tree> topMenu();


  //查询角色
    Page<Role> queryRole(Page<Role> page, String roleName);

    //查询角色By Name
    Role queryRoleByName(String roleName,Integer roleId);

    void delRoleEqpt(Integer roleId);

    void addRoleEqpt(Integer roleId,Integer eqId);

    List<Role> getPath(Integer roleId);

    void addRole(Role oRole);

    boolean editRole(Role oRole);

    boolean queryCountBYRole(Integer roleId);

    void removeRoleEqpt(Integer roleId);

    void removeRole(List<Integer> delIDs);

     List<Tree> selectMenu(Integer menuType);
     Role getIpPort(Integer empId);



    List<Integer> geteqIdByRoleId(Integer roleId);

    List<String> queryRoleByEqId(List<Integer> delIDs,Integer eqId);

    List<Integer> queryEqId(List<Integer> roleId);



    boolean determineEqptByUser(Integer empId,Integer roleId);

}
