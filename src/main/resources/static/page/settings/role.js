$(function () {
     //加载数据
    loaddata();
});

// 页面加载完再渲染lang对应的中英文
window.onload = function () {
    let path=window.location.pathname;
    lang.getOperate(path);
};

function loaddata(){

    $("#BsiRoleTable").datagrid({
        url: "/json/Role/queryRole",
        idField: "roleId",
        fitColumns: true,
        columns : [ [ {
            field : 'ck',
            width : 50,
            checkbox : true,
            align : 'center'
        },
        {
            field : 'roleName',
            title :lang.getText("settings_roleName"),
            width : 100,
            align : 'center'
        },
        {
            field : 'memo',
            title : lang.getText("settings_memo"),
            width : 150,
            align : 'center'
        },
        {
            field : 'webAssign',
            width : 100,
            align : 'center',
            formatter:formatOrole
        },
        {
            field : 'pdaAssign',
            width : 100,
            align : 'center',
            formatter:formatRole
        }] ],
        //加载完毕后获取所有的checkbox遍历
        onLoadSuccess: function(data){
            //循环判断操作为新增的不能选择
            for (let i = 0; i < data.rows.length; i++) {
                // 隐藏管理员所在行的复选框
                if (data.rows[i].roleId == 1) {
                    $("input[type='checkbox']")[i + 1].hidden = true;
                }
            }
        }
    });

}

// grid查询
function searchdata() {
    $("#BsiRoleTable").datagrid('load', {
        roleName:$("#txtName").val()
    });
}


//关闭面板
function closepannel() {
    $('#dlg').dialog('close');
}
function closepannel_Right() {
    $('#dlg_Right').dialog('close');
}

//增加操作还是编辑操作
let addoredit;

//添加角色
function addRole() {
    //获取设备
    addoredit = "add";
    $('#fm').form('clear');//清空内容
    $("#leave").click();
    $(".continue_add").show();
    $("#btn_jurisdiction_ok").val(lang.getText("btn_ok"));
    $('#dlg').dialog('open').dialog('setTitle', lang.getText("btn_add"));
}

//提交增加角色
function saveAddRole() {
    $('#fm').form('submit', {
        url: "/json/Role/addRole",
        onSubmit: function (param) {
            param.operatetype = "add";
            return $(this).form('validate');
        },
        success: function (result) {
            if (result == "true") {
                //成功后提交到设备
                closepannel();
                $.messager.alert(lang.getText("promptNotice_prompt"), lang.getText("base_operateSuccessfully"), "info");
                $('#BsiRoleTable').datagrid('reload');
            }
            else if (result == "false") {
                $.messager.alert(lang.getText("promptNotice_prompt"),lang.getText("base_operateFailed"), "info");
                return;
            }
            else if (result == "repetition") {
                $.messager.alert(lang.getText("promptNotice_prompt"),lang.getText("base_operateFailed"), "info");
                return;
            }
        }
    });
}

//提交数据
function saveRole() {
    if (addoredit == "add") {

        //提交增加角色
        saveAddRole();
    }
    if (addoredit == "edit") {

        //提交编辑角色
        saveEditRole();
    }
}

//编辑角色
function editRole() {

    //获取选中行
    let selectRow = $("#BsiRoleTable").datagrid("getChecked");
    if (selectRow.length != 1) {
        $.messager.alert(lang.getText("promptNotice_prompt"),lang.getText("promptNotice_edit"), "info");
    }
    else {
        var checkRow = $("#BsiRoleTable").datagrid("getChecked")[0];
        var strName=checkRow.strId;
        $('#fm').form('load', {
            roleName:selectRow[0].roleName,
            memo:selectRow[0].memo,
            roleId:selectRow[0].roleId
        });
        addoredit = "edit";
        $(".continue_add").hide();
        $('#dlg').dialog('open').dialog('setTitle',lang.getText("btn_edit"));
    }
}

//提交编辑角色
function saveEditRole() {
    var roleId = $("#BsiRoleTable").datagrid('getSelected').roleId;
    $("#roleId").val(roleId);
    $('#fm').form('submit', {
        url: "/json/Role/modifyRole",
        onSubmit: function (param) {
            param.operatetype = "edit";
            return $(this).form('validate');
        },
        success: function (result) {
           if (result == "true") {
            //成功后提交到设备
                 closepannel();
                $.messager.alert(lang.getText("promptNotice_prompt"), lang.getText("base_operateSuccessfully"), "info");
                $('#BsiRoleTable').datagrid('reload');
            }
            else if (result == "repetition") {
                $.messager.alert(lang.getText("promptNotice_prompt"),lang.getText("base_operateFailed"), "info");
                return;

            }
            else if (result == "false") {
                $.messager.alert(lang.getText("promptNotice_prompt"),lang.getText("base_operateFailed"), "info");
                return;
            }
        }
    });
}

//删除角色
function removeRole() {

    //获取选中行
    var selectRow = $("#BsiRoleTable").datagrid("getChecked");
    if (selectRow.length > 0) {

        //角色ID列表（逗号分隔）
        var roleidlist = "";
        var nameList = "";
        for (var i = 0; i < selectRow.length; i++) {
            roleidlist += "" + selectRow[i].roleId + "";
            nameList += "" + selectRow[i].roleName + "";
            if (i != selectRow.length - 1) {
                roleidlist += ",";
                nameList += ",";
            }
        }
      $("#roleidlist").val(roleidlist);
      $("#nameList").val(nameList);
        $.messager.confirm(lang.getText("promptNotice_prompt"),lang.getText("promptNotice_delConfirm"), function (r) {
            if (r) {
                $('#fm').form('submit', {
                    url: "/json/Role/removeRole",
                    onSubmit: function (param) {
                        param.operatetype = "remove";
                        param.roleidlist = roleidlist;
                        param.nameList = nameList;
                    },
                    success: function (result) {
                        if (result == "true") {
                            //成功后提交到设备
                            closepannel();
                            $("#BsiRoleTable").datagrid('clearSelections');
                            $("#BsiRoleTable").datagrid('clearChecked');
                            $.messager.alert(lang.getText("promptNotice_prompt"), lang.getText("base_operateSuccessfully"), "info");
                            $("#BsiRoleTable").datagrid('reload');
                        } else if (result == "1") {
                            $.messager.alert(lang.getText("promptNotice_prompt"), lang.getText("base_operateFailed"), "info");
                            return;
                        }
                        else {
                            $.messager.alert(lang.getText("promptNotice_prompt"), lang.getText("base_operateFailed"), "info");
                            return;
                        }
                    }
                });
            }
        })
    }
    else {
        $.messager.alert(lang.getText("promptNotice_prompt"), lang.getText("promptNotice_del"), "info");
        return;
    }
}


//格式化数值类型 web
function formatOrole(val, row) {
    if (row.roleName == "系统管理员") {
        return "";
    } else {
        return "<a href='javascript:void(0)' style='text-decoration:none' onclick='roleORight(\"" + row.roleId + "\",\"" + row.roleName + "\",0)'>分配WEB菜单</a>";
    }
}

// pda
function formatRole(val, row) {
    if (row.roleName == "系统管理员") {
        return "";
    } else {
        return "<a href='javascript:void(0)' style='text-decoration:none' onclick='roleORight(\"" + row.roleId + "\",\"" + row.roleName + "\",1)'>分配PDA菜单</a>";
    }
}

//加载菜单树
function loadtree(menuType){
    $('#tbom').tree('loadData',[]);
    $("#menuName").text('');
    $('#tbom').tree({
        url: "/json/Users/queryAllMenu?menuType="+menuType+"&roleId="+$("#hid").val(),
        method: 'POST',
        checkbox: true,
        animate:true,
        onLoadSuccess:function(node,data){
            if (menuType == 0) {
                // web
                $("#menuName").text('WEB');
            } else if (menuType == 1) {
                // pda
                $("#menuName").text('PDA');
            }
        }
    });
}

// 分配菜单按钮点击事件
function roleORight(roleId, name,menuType){
    $("#fm_Right").show();
    $("#fm_RightDept").hide();
    $("#hid").val(roleId);
    $("#hType").val(menuType);
    $('#dlg_Right').dialog({
        closed: false,
        cache: false,
        modal: true
      });
    $('#dlg_Right').dialog('open').dialog('setTitle',  name);
    loadtree(menuType);
}

//保存角色菜单权限
function saveRight() {
    // 角色ID
    let id=  $("#hid").val();
    let menuType = $("#hType").val();
    let list = [];
    let tBom = $('#tbom').tree('getChecked',['checked']);
    for (let i = 0; i < tBom.length; i++) {
        list.push(tBom[i].id);
    }
    $('#fm_Right').form('submit', {
        url: "/json/Users/alterRoleMenu",
        method : 'post',
        onSubmit: function (param) {
            param.list = list;
            param.roleId = id;
            param.menuType = menuType;
        },
        success: function (ret) {
            closepannel_Right();
            if(ret == "1"){
                //成功后提交到设备
                $.messager.alert(lang.getText("promptNotice_prompt"),lang.getText("base_operateSuccessfully"), "info");
            }else if(ret == "0"){
                $.messager.alert(lang.getText("promptNotice_prompt"),lang.getText("base_operateFailed"), "info");
            }
        }

    } );

}

