package com.hvisions.mes.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hvisions.mes.domain.*;
import com.hvisions.mes.mapper.EmpLineOrderTeamMapper;
import com.hvisions.mes.service.*;
import com.hvisions.mes.util.StringUtil;
import springfox.documentation.annotations.ApiIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.Param;

import com.hvisions.mes.dto.Result;
import com.hvisions.mes.security.EncryptUtil;
import com.hvisions.mes.service.exception.ErrorCode;
import com.hvisions.mes.service.exception.ValidationException;
import com.hvisions.mes.util.CookieUtil;

@RestController
@RequestMapping("/json/Users")
@ApiIgnore
public class UserController extends BaseController{
    @Value("${web.localhost}")
    private String localhost;
    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private IUserService userService;
    @Autowired
    private IMenuService menuService;
    @Autowired
    private ILineService lineService;
    @Autowired
    private ApplicationContext ctx;
    @Autowired
    private IUserRoleService userRoleService;

    /**
     *  通过员工ID获取员工产线ID
     * @return
     */
    @GetMapping("/getLineIdByUserId")
    public String getLineIdByUserId() {
        return String.valueOf(lineService.queryLineByPrincipal(getCurrentUserID()).getLineId());
    }

    /**
     *  加载数据列表(分页)
     * @param userAccount
     * @param userName
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "/userlistpage", method = RequestMethod.POST)
    public Map<String, Object> userListPage (
            @RequestParam(value = "UserAccount", required = false) String userAccount,
            @RequestParam(value = "UserName", required = false) String userName,
             @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "15") Integer rows) {
        Page<Emp> data = userService.QueryUser(new Page<>(page, rows), userAccount, userName);
        Map<String, Object> result = new HashMap<>(16);
        result.put("total", data.getTotal());
        result.put("rows", data.getRecords());

        return result;
    }

    /**
     *  添加用户信息  2019/05/28 modified by dpeng
     */
    @PostMapping("/addUser")
    public String addUser(Emp emp){
        // 返回0表示操作失败  1表示用户名已存在  2表示成功
        String ret;

        // 必须传入这些数据库非空字段
        if (StringUtil.isNull(emp.getEmpName())
                || StringUtil.isNull(emp.getAccount())
                || StringUtil.isNull(emp.getPassword())
                || emp.getSex() == null
                || emp.getNeedPw() == null) {
            return "0";
        }

        try {
            Emp newEmp = userService.QueryByUserAccount(emp.getAccount());

            if (newEmp != null) {
                ret = "1";
            }else {
                // 用户名不存在 表示可以创建这个用户
                emp.setPassword(EncryptUtil.encrypt(emp.getPassword()));
                emp.setMesOrErpEmp(0);
                userService.AddUser(emp);
                EmpRole empRole = new EmpRole();
                empRole.setEmpId(emp.getEmpId());
                empRole.setRoleId(emp.getRoleId());
                userService.AddUserRole(empRole);
                ret = "2";
            }
        } catch (Exception e) {
            ret = "0";
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 登录
     */
    @RequestMapping(value ="/login", method = RequestMethod.POST)

    public Result login(String account, String password, String language, HttpServletRequest request, HttpServletResponse response) {
        String encrypt = EncryptUtil.encrypt(password);
         Emp user = userService.login(account, encrypt);
         if (user == null) {
             throw new ValidationException("login.error.auth", ErrorCode.UNAUTHORIZED, ctx);
         }
        CookieUtil.addCookies("language", language, response);
        CookieUtil.addCookies("emp_id", user.getEmpId().toString(), response);
        CookieUtil.addCookies("type", "", response);
        logger.debug("###### User ID:{} login ######", user.getEmpId());
        return Result.newSuccessResult();
    }


    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public Result searchDetail(HttpServletRequest request, HttpServletResponse response) {
        //List<SystemLanguage> data = systemLanguageService.querySystemLanguage();

        String data = CookieUtil.getCookieValue(request, "language");
        return Result.newSuccessResult(data);
    }


    /**
     * 登出
     */
    @RequestMapping(value = Param.URL_LOGOUT, method = RequestMethod.POST)
    public Result logout(HttpServletRequest request, HttpServletResponse response) {
        String value="";
        if(!CookieUtil.getCookieValue(request, "type").equals(""))
        {
            value=CookieUtil.getCookieValue(request, "type").toString();
        }
        else
        {
            CookieUtil.removeCookies("emp_id", request, response);
            request.removeAttribute(Param.TOKEN_COOKIE_NAME);
            request.removeAttribute(Param.USER_ID);
        }
        //Delete cookie


        //String value=EncryptUtil.encrypt("type");
        //Clear Request

        return Result.newSuccessResult(value);
    }



//    /**
//     * 获取所有可显示的菜单
//     */
//    @GetMapping("visible_menu")
//    public Result visibleMenu(HttpServletRequest request, HttpServletResponse response) {
//        //查询菜单
//        String value=CookieUtil.getCookieValue(request, "emp_id");
//        Integer empId=Integer.valueOf(EncryptUtil.decrypt(value));
//        List<Menu> menus = menuService.getVisibleMenu(empId);
//
//        return Result.newSuccessResult(menus);
//    }
//
//
//
    /**
     * 获取所有可显示的菜单
     */
    @GetMapping("operation_menu")
    public Result operationMenu(HttpServletRequest request, HttpServletResponse response) {
        //查询菜单
      //查询菜单
        //String value=CookieUtil.getCookieValue(request, "emp_id");
        Integer empId=getCurrentUserID();
        List<Menu> menus = menuService.getOperateMenu(empId);

        return Result.newSuccessResult(menus);
    }
    /**
     * 获取当前用户有权访问的一级菜单
     */
    @RequestMapping(value = "/topMenu", method = RequestMethod.POST)
    public Result topMenu(HttpServletRequest request, HttpServletResponse response) {
        //获取当前用户ID
       // String value=CookieUtil.getCookieValue(request, "emp_id");
        Integer empId=getCurrentUserID();
        //查询一级菜单
        List<Menu> menus = userService.getTopMenu(empId);
        String language = CookieUtil.getCookieValue(request, "language");
        Map<String ,Object> result = new HashMap<>();
        result.put("menus", menus);
        result.put("language", language);

        return Result.newSuccessResult(result);
    }



    /**
     * 获取当前用户信息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/currentUser", method = RequestMethod.POST)
    public Result currentUser(HttpServletRequest request, HttpServletResponse response) {
        //Return value
        Emp user = new Emp();

        //Gets the current user id
       // String value=CookieUtil.getCookieValue(request, "emp_id");
        Integer empId=getCurrentUserID();
        //search user
        if (empId != null) {
            user = userService.getUsersByID(empId);
            String password=EncryptUtil.decrypt(user.getPassword());
            user.setPassword(password);


        }

        logger.debug("###### current user:{} ######", user);

        return Result.newSuccessResult(user);
    }

    /**
     * 获取当前用户有权访问的二级菜单
     */
    @RequestMapping(value =  "/subMenu", method = RequestMethod.POST)
    public Result subMenu(String topMenuID, HttpServletRequest request, HttpServletResponse response) {
        //获取当前用户ID
        Map<String ,Object> result = new HashMap<>();
        //String value=CookieUtil.getCookieValue(request, "emp_id");
        Integer empId=getCurrentUserID();
        //查询一级菜单
        List<Menu> menus = userService.getSubMenu(empId, topMenuID);
        String language = CookieUtil.getCookieValue(request, "language");
        result.put("menus", menus);
        result.put("language", language);
        return Result.newSuccessResult(result);
    }


    /**
     *  删除用户信息
     * @param delIDs
     * @return
     */
    @RequestMapping(value = "/removeuser", method = RequestMethod.POST)
    public String removeUser(@RequestParam("deleteIDs") List<String> delIDs ) {
        String res = "true";

        try {
            List<Integer> empIdList = new ArrayList<Integer>();
            for(int i=0;i<delIDs.size();i++) {
                empIdList.add(Integer.valueOf(delIDs.get(i)));
            }
            userService.RemoveUser(empIdList);
            //addPersonnelOperationLog(OperationLog.OPERATION_TYPE_REMOVE,"删除用户","Remove users");

        } catch (Exception ex) {
           res = "false";
        }

        return res;
    }

    /**
     *  密码初始化
     * @param delIDs
     * @return
     */
    @RequestMapping(value = "/updatepassword", method = RequestMethod.POST)
    public String editPassword(@RequestParam("newDelIDs") List<Integer> delIDs) {
        String res = "true";
        try {
            userService.EditPassword(delIDs);
        } catch (Exception ex) {
            res = "false";
        }
        return res;
    }



    @RequestMapping(value = "/password", method = RequestMethod.POST)
    public Result Password(String password,String empId) {
        try {
            String encrypt = EncryptUtil.encrypt(password);
            userService.EditPasswordByID(encrypt, empId);
            addPersonnelOperationLog(OperationLog.OPERATION_TYPE_PASSWORD,"修改密码","Revise password");

        } catch (Exception ex) {
            throw new ValidationException("login.error.auth", ErrorCode.UNAUTHORIZED, ctx);
            //msg = ex.getMessage();
           // res = "false";
        }
        return Result.newSuccessResult();
    }

    /**
     * 编辑用户信息    2019/05/28 modified by dpeng
     * @param emp
     * @return
     */
    @RequestMapping(value = "/edituser", method = RequestMethod.POST)
    public String editUser(Emp emp) {
//        Emp userByID = userService.getUserByID(emp.getEmpId());
        Emp oUser = userService.QueryByUserAccount(emp.getAccount());
        EmpRole userRole = new EmpRole();
        String res;
        try {
            if (oUser != null && !emp.getEmpId().equals(oUser.getEmpId())) {
                res = "3";
            } else {
               //编辑用户角色
                userRole.setEmpId(emp.getEmpId());
                userRole.setRoleId(emp.getRoleId());
                userService.EditUserRole(userRole);
                userService.EditUser(emp);
                res = "true";
//                addPersonnelOperationLog(OperationLog.OPERATION_TYPE_MODIFY,"编辑用户:"+request.getParameter("empName"),"Edit user:"+request.getParameter("empName"));
            }
        } catch (Exception ex) {
            ex.getMessage();
            res = "false";
        }
        return res;
    }


    /**
     * 角色下拉列表
     */
    @RequestMapping(value = "/queryRole", method = RequestMethod.POST)
    public List<Role> queryRole(){
        return userService.queryRole();
    }

    @PostMapping("queryEmp")
    public List<Emp> queryUser(){

        List<Emp> ilEmp=userService.QueryUser(null, null);
        return ilEmp;
    }


    /**
     * 获取当前用户有权访问的所有菜单       新版本页面
     */
    @RequestMapping(value = "/menus", method = RequestMethod.POST)
    public Result getMenus(HttpServletRequest request, HttpServletResponse response) {
        //获取当前用户ID
        Map<String ,Object> result = new HashMap<>();
        Integer empId=getCurrentUserID();
        //查询所有菜单
        List<Menu> menus = userRoleService.queryMenus(empId);
        String language = CookieUtil.getCookieValue(request, "language");
        result.put("menus", menus);
        result.put("language", language);
        return Result.newSuccessResult(result);
    }

    /**
     *  获取所有菜单  用于菜单树
     * @param menuType
     * @return
     */
    @RequestMapping(value = "/queryAllMenu", method = RequestMethod.POST )
    public List<Tree> queryAllMenu(Integer menuType,Integer roleId) {
        return userRoleService.queryAllMenus(menuType,roleId);
    }


    /**
     *  查询所有的用户  下列列表用
     * @return
     */
    @RequestMapping("/getAllEmp")
    public List<Emp> getAllEmp(@RequestParam(value = "q",required = false)String userName) {
        return userService.queryAllEmp(userName == null || Objects.equals(userName,"") ? "" : userName.trim());
    }

    /**
     *  查询角色下的所有菜单权限
     * @param roleId
     * @param menuType
     * @return
     */
    @RequestMapping("/getAllRoleMenuByRoleId")
    public List<RoleMenu> getAllRoleMenuByRoleId(Integer roleId,Integer menuType) {
        return userRoleService.queryAllRoleMenuByRoleId(roleId,menuType);
    }

    /**
     *  更新角色权限表
     * @param list
     * @param roleId
     * @param menuType
     * @return
     */
    @RequestMapping("/alterRoleMenu")
    public String alterRoleMenu(String[] list,Integer roleId,Integer menuType) {
        String flag;
        try {
            userRoleService.modifyRoleUser(list,roleId,menuType);
            flag = "1";
        }catch (Exception e) {
            e.printStackTrace();
            flag = "0";
        }
        return flag;
    }
}
