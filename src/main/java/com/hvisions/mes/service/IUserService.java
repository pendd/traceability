package com.hvisions.mes.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;

import com.hvisions.mes.domain.Menu;
import com.hvisions.mes.domain.Role;
import com.hvisions.mes.domain.Emp;
import com.hvisions.mes.domain.EmpRole;


public interface IUserService {


    /**
     * 用户登陆
     * @param account 账号
     * @param password 密码
     * @return 用户信息
     */
    Emp login(String account, String password);

    /**
     * 根据用户ID，获取用户信息
     * @param userID
     * @return
     */
    Emp getUserByID(Integer empId);


    /**
     * 根据用户ID，获取用户信息
     * @param userID
     * @return
     */
    Emp getUsersByID(Integer empId);

    /**
     * 根据用户ID，查询顶部菜单
     * @param userID
     * @return
     */
    List<Menu> getTopMenu(Integer empId);

    /**
     * 获取用户有权访问的二级菜单
     * @param userID 用户ID
     * @param topMenuID 一级菜单ID
     * @return 二级菜单列表
     */
    List<Menu> getSubMenu(Integer empId, String upMenuId);
    /**
    * 查询用户
    * @param page
    * @param userAccount
    * @param userName
    * @return
    */
    Page<Emp> QueryUser(Page<Emp> page,String account,String userName);
    List<Emp> QueryUser(String account,String empName);
       /**
        * 根据账号查询
        */
    Emp QueryByUserAccount(String account);
    /**
     * 增加用户
     * @param oUser
     */
    void AddUser(Emp oEmp);
     /**
      * 删除用户
      * @param strUserIdList
      */
    void RemoveUser(List<Integer> Stringlist);
      /**
       * 密码初始化
       * @param oUser
       */
    void EditPassword(List<Integer> Stringlist);


    void EditPasswordByID(String password ,String empId);
       /**
        * 编辑用户
        * @param oUser
        */
    void EditUser(Emp oEmp);

        /**
         * 角色下拉列表
         */
    List<Role> queryRole();
     /**
      * 增加用户角色
      */
    void AddUserRole(EmpRole  oEmpRole);
       /**
        * 编辑用户角色
        */
    void EditUserRole(EmpRole oEmpRole);
       /**
        * 删除用户角色
        * @param Stringlist
        */
    void RemoveUserRole(List<String> Stringlist);


    // 根据用户编号查询用户信息
    Emp getEmpById(Integer empId);

    /**
     *  查询所有的用户  下列列表用
     * @return
     */
    List<Emp> queryAllEmp(String userName);

}
