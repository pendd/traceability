package com.hvisions.mes.mapper;

import java.util.List;

import com.hvisions.mes.domain.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

    /**
     * 根据账号、密码查询用户
     * @param account 账号
     * @param password 密码
     * @return 用户列表
     */
    List<Emp> selectUserByAccountAndPwd(@Param("account") String account, @Param("password") String password);

    /**
     * 根据用户ID，查询用户
     * @param userID
     * @return
     */
    Emp selectUserByID(Integer empId);

    /**
     * 根据用户ID，删除角色
     * @param userID
     * @return
     */
    void DeleteUserRoleByUser(@Param("empId") Integer empId);
    /**
     * 根据用户ID，查询用户
     * @param userID
     * @return
     */
    Emp selectUsersByID(Integer empId);

    /**
     * 根据userID查询顶部菜单
     * @param userID
     * @return
     */
    List<Menu> selectTopMenu(Integer empId);

    /**
     * 查询用户有权访问的二级菜单
     * @param userID 用户ID
     * @param topMenuID 一级菜单ID
     * @return 二级菜单列表
     */
    List<Menu> selectSubMenu(@Param("empId") Integer empId, @Param("upMenuId") String upMenuId);

    Menu selectMenuBYId(@Param("menuId") String menuId);
    /**
     * 查询用户
     */
    List<Emp> selectUser(Pagination page,@Param("account")String account ,@Param("empName") String empName);


    /**
     * 查询用户
     */
    List<Emp> selectUser(@Param("account")String account ,@Param("empName") String empName);

    /**
     * 根据账号查询
     */
    Emp SelectByUserAccount(@Param("account") String account );
    /**
     * 增加用户信息
     * @param oplant
     * @return
     */
    void InsertUser(Emp oEmp);
    /**
     * 增加用户角色信息
     * @param oplant
     * @return
     */
    void InsertUserRole(EmpRole oEmpRole);
    /**
     * 删除用户信息
     * @param oplant
     * @return 二级菜单列表
     */
    void DeleteUser(List<Integer> empIdList);
    /**
     * 删除用户角色信息
     * @param oplant
     * @return 二级菜单列表
     */
    void DeleteUserRole(List<String> Stringlist);
    /**
     * 初始化密码
     * @param oplant
     * @return 二级菜单列表
     */
    void UpdatePassword(List<Integer> Stringlist);


     void UpdatePasswordById(@Param("password")String password ,@Param("empId") Integer empId);
    /**
     * 编辑用户
     * @param oplant
     * @return 二级菜单列表
     */
    void UpdateUser(Emp oEmp);
    /**
     * 编辑用户角色
     * @param oplant
     * @return 二级菜单列表
     */
    void UpdateUserRole(EmpRole oEmpRole);

    /**
     * 角色下拉列表
     */
    List<Role> selectRole();

    /**
     * 根据用户Id查询角色所属设备信息
     */
    List selectEqptByEmpId(@Param("empId")Integer empId);

    /**
     * 查询所有的用户    用作下拉列表
     *
     * @return
     */
    List<Emp> selectAllEmp(@Param("userName")String userName);

}
