package com.hvisions.mes.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.Param;
import com.hvisions.mes.domain.OperationLog;
import com.hvisions.mes.domain.Role;
import com.hvisions.mes.domain.Tree;
import com.hvisions.mes.domain.Emp;

import com.hvisions.mes.service.IRoleMenuService;
import com.hvisions.mes.service.IRoleService;

import com.hvisions.mes.service.IUserRoleService;
import springfox.documentation.annotations.ApiIgnore;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author bliu
 * @since 2017-08-3
 */
@RestController
@RequestMapping("/json/Role")
@ApiIgnore
public class RoleController extends BaseController {

    @Autowired
    private IRoleService roleService;
    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private IRoleMenuService roleMenuService;
    /**
     * 查询角色列表
     * @param roleName
     * @param page
     * @param rows
     * @return
     */
     @RequestMapping(value = "/queryRole", method = RequestMethod.POST)
    public Map<String, Object> queryRole(String roleName,
                                         @RequestParam(defaultValue = "1") Integer page,
                                         @RequestParam(defaultValue = "15") Integer rows) {
        Emp oUser=getCurrentUser();
        Page<Role> data = roleService.queryRole(new Page<Role>(page, rows), roleName);
        Map<String ,Object> result = new HashMap<>(16);
        result.put("total", data.getTotal());
        result.put("rows",data.getRecords());
        return result;

    }

    /**
     * 新增角色
     * @param roleName
     * @param memo
     * @return
     */
    @RequestMapping(value = "/addRole", method = RequestMethod.POST)
    public String addRole(@RequestParam("roleName")String roleName,@RequestParam("memo")String memo){

        Role role=new Role();
        role.setRoleName(roleName);
        role.setMemo(memo);
        Emp oUser=getCurrentUser();
        Role oRole=roleService.queryRoleByName(roleName,null);
        if(oRole!=null)
        {
            return "repetition";
        }

        try
        {
            roleService.addRole(role);

            //addPersonnelOperationLog(OperationLog.OPERATION_TYPE_ADD,"增加角色"+roleName, "Add role："+roleName);
            return "true";
       }
        catch (Exception ex)
        {
            return "false";
        }


    }

   @RequestMapping(value = "/removeRole", method = RequestMethod.POST)
    public String removeRole(@RequestParam("roleidlist")List<String> delIDs, @RequestParam("nameList")List<String> nameList){

       String[] roleId = delIDs.get(1).split(",");
       List<Integer> roleIdList = new ArrayList<Integer>();
       for(int i=0;i<roleId.length;i++){
           roleIdList.add(Integer.valueOf(roleId[i]));
       }
       roleService.removeRole(roleIdList);
       roleMenuService.removeRoleMenu(roleIdList);
       //addPersonnelOperationLog(OperationLog.OPERATION_TYPE_ADD,"删除角色", "Remove roles");
       return "true";
    }

    @RequestMapping(value = "/modifyRole", method = RequestMethod.POST)
    public String modifyRole(@RequestParam("roleId")Integer roleId,@RequestParam("roleName")String roleName,@RequestParam("memo")String memo){




        Role role=new Role();
        System.out.println(roleId);
        role.setRoleId(roleId);
        role.setRoleName(roleName);
        role.setMemo(memo);
        Emp oUser=getCurrentUser();
        Role oRole=roleService.queryRoleByName(roleName,null);
        Role ooRole=roleService.queryRoleByName(null,roleId);

        try
        {
            if(oRole==null || ooRole.getRoleName().equals(roleName))
            {
                roleService.editRole(role);
                 // addPersonnelOperationLog(OperationLog.OPERATION_TYPE_ADD,"编辑角色"+roleName, "Edit role："+roleName);
                 return "true";



            }
            else
            {

                return "repetition";
            }

        }
        catch (Exception ex)
        {
            return "false";
       }
    }

    @RequestMapping(value = "/queryMenu", method = RequestMethod.POST)
    private List<Tree> queryTree(@RequestParam("menuType")Integer menuType){

        List<Tree> large=roleService.selectMenu(menuType);
        List<Tree> ilRootRight = new ArrayList<>();
        for (int i = 0; i < large.size(); i++) {
            if("".equals(large.get(i).upid)){
                Tree oNewTree = new Tree();
                oNewTree.id = large.get(i).id;
                //oNewTree.eqId=Integer.valueOf(eqId);
                oNewTree.text = large.get(i).text;
                oNewTree.children = LoadSubNode(large,large.get(i).id);
                ilRootRight.add(oNewTree);
            }
        }
        return ilRootRight;
    }

    //二级菜单
    private List<Tree> LoadSubNode(List<Tree> ilRight, String RightID){
        List<Tree> ilSubRight = new ArrayList<>();
        for(int i=0;i<ilRight.size();i++){
            if(ilRight.get(i).upid.equals(RightID)){
                Tree oNewTree = new Tree();
                oNewTree.id = ilRight.get(i).id;
                oNewTree.text = ilRight.get(i).text;
//                oNewTree.children = LoadSubNode(ilRight, ilRight.get(i).id.toString());
                //oNewTree.eqId=Integer.valueOf(eqId);
                oNewTree.upid = RightID;
                ilSubRight.add(oNewTree);
            }
        }
        return ilSubRight;
    }


}
