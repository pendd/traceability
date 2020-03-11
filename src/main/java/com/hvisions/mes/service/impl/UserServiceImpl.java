package com.hvisions.mes.service.impl;

import java.util.List;

import com.hvisions.mes.security.EncryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;

import com.hvisions.mes.domain.Menu;
import com.hvisions.mes.domain.Role;
import com.hvisions.mes.domain.Emp;
import com.hvisions.mes.domain.EmpRole;

import com.hvisions.mes.mapper.UserMapper;
import com.hvisions.mes.service.IUserService;

@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public Emp login(String account, String password) {
        //密码加密
        //password = MD5Util.encrypt(password);

        //查询用户
        List<Emp> users = userMapper.selectUserByAccountAndPwd(account, password);

        if (users.size() > 0) {
            return users.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Emp getUserByID(Integer empId) {
        if (empId == null) {
            return null;
        }

        //查询用户
        Emp user = userMapper.selectUsersByID(empId);

        return user;
    }

    @Override
    public Emp getUsersByID(Integer empId) {
        if (empId == null) {
            return null;
        }

        //查询用户
        Emp oemp = userMapper.selectUsersByID(empId);

        return oemp;
    }

    @Override
    public List<Menu> getTopMenu(Integer empId) {
        List<Menu> topMenus = userMapper.selectTopMenu(empId);

        return topMenus;
    }

    @Override
    public List<Menu> getSubMenu(Integer empId, String upMenuId) {
        List<Menu> subMenus = userMapper.selectSubMenu(empId, upMenuId);
        for(int i=0;i<subMenus.size();i++)
        {
            if(!subMenus.get(i).getUpUpMenuId().equals(""))
            {
                Menu oMenu=userMapper.selectMenuBYId(subMenus.get(i).getUpUpMenuId());
                subMenus.get(i).setUpUpMenuMameEn(oMenu.getUpUpMenuMameEn());
                subMenus.get(i).setUpUpMenuMameZh(oMenu.getUpUpMenuMameZh());
            }
            else
            {
                subMenus.get(i).setUpUpMenuMameEn("");
                subMenus.get(i).setUpUpMenuMameZh("");

            }

        }

        return subMenus;
    }
    @Override
    public Page<Emp> QueryUser(Page<Emp> page,String account,String empName) {
//        page.setRecords(userMapper.selectUser(page, account, empName));
        List<Emp> emps = userMapper.selectUser(page, account, empName);
        for (Emp emp : emps) {
            if (emp.getPassword() != null && !"".equals(emp.getPassword())) {
                emp.setPassword(EncryptUtil.decrypt(emp.getPassword()));
            }
        }
        page.setRecords(emps);
        return page;
    }


    @Override
    public List<Emp> QueryUser(String account,String empName) {
       return userMapper.selectUser(account, empName);

    }

    @Override
    public Emp QueryByUserAccount(String account){
        Emp oUser = userMapper.SelectByUserAccount(account);
        return oUser;
    }
    @Override
    public void AddUser(Emp oEmp) {
        //增加用户信息
        userMapper.InsertUser(oEmp);
    }
    @Override
    public void AddUserRole(EmpRole  oEmpRole) {
        //增加用户角色信息
        userMapper.InsertUserRole(oEmpRole);
    }

    @Override
    public void RemoveUser(List<Integer> empIdList) {
        //删除用户信息
        userMapper.DeleteUser(empIdList);
        for(int i=0;i<empIdList.size();i++)
        {
            userMapper.DeleteUserRoleByUser(empIdList.get(i));
        }
    }

    @Override
    public void RemoveUserRole(List<String> Stringlist) {
        //删除用户信息
        userMapper.DeleteUserRole(Stringlist);
    }

    /**
     *  根据用户编号查询用户信息
     * @param empId
     * @return
     */
    @Override
    public Emp getEmpById(Integer empId) {
        return userMapper.selectUsersByID(empId);
    }

    @Override
    public void EditPassword(List<Integer> Stringlist) {
        //密码初始化
        userMapper.UpdatePassword(Stringlist);
    }

    @Override
    public void EditPasswordByID(String password ,String empId) {
        //密码初始化


        userMapper.UpdatePasswordById(password, Integer.valueOf(empId));
    }

    @Override
    public void EditUser(Emp emp) {
        if (emp.getPassword() != null && ! "".equals(emp.getPassword())) {
            emp.setPassword(EncryptUtil.encrypt(emp.getPassword()));
        }
        //编辑用户信息
        userMapper.UpdateUser(emp);
    }
    @Override
    public void EditUserRole(EmpRole oEmpRole) {
        //编辑用户角色信息

        userMapper.DeleteUserRoleByUser(oEmpRole.getEmpId());
        userMapper.InsertUserRole(oEmpRole);
    }


    @Override
    public List<Role> queryRole(){
        List<Role> ilRoles = userMapper.selectRole();
        return ilRoles;
    }

    /**
     *  查询所有的用户  下列列表用
     * @return
     */
    @Override
    public List<Emp> queryAllEmp(String userName) {

        return userMapper.selectAllEmp(userName);
    }



}
