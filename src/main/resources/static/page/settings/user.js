
$(function(){
   //grid加载
   $("#tbl").datagrid({
        url: "/json/Users/userlistpage",
        idField: "userId",
        fitColumns:true,
        columns : [ [ {
            field : 'ck',
            width : 50,
            checkbox : true,
            align : 'center'
        }, {
            field : 'userId',
            width : 50,
            hidden : true
        },{
            field : 'account',
            title : lang.getText("settings_account"),
            width : 100,
            align : 'center'
        },{
            field : 'empName',
            title : lang.getText("settings_userName"),
            width : 100,
            align : 'center'
        },
        {
            field : 'needPw',
            title : '是否需要密码',
            width : 100,
            align : 'center',
            formatter:function (value, rowData, rowIndex) {
                let str = rowData.needPw;
                switch (str) {
                    case 0:
                        value = "否";
                        break;
                    case 1:
                        value = "是";
                        break;
                }
                return value;
            }
        },
        {
            field : 'password',
            title : '密码',
            width : 100,
            align : 'center'
        },
        {
            field : 'roleName',
            title : lang.getText("settings_roleName"),
            width : 100,
            align : 'center'
        },
        {
            field : 'csex',
            title : lang.getText("settings_sex"),
            width : 100,
            align : 'center'
        },
        {
            field : 'tel',
            title : lang.getText("settings_phone"),
            width : 100,
            align : 'center'
        },
        {
            field : 'roleId',
            width : 100,
            align : 'center',
            hidden: true
        },
        {
            field : 'email',
            width : 100,
            align : 'center',
            title : lang.getText("settings_email")
        },
        {
            field : 'memo',
            width : 100,
            align : 'center',
            title : lang.getText("settings_memo")
        },
        {
            field : 'mesOreErpEmp',
            width : 100,
            align : 'center',
            hidden : true
        }
        ] ],
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
});

// 页面加载完再渲染lang
window.onload = function () {
    var path=window.location.pathname;
    lang.getOperate(path);
};

// 是否需要密码  不需要 隐藏密码框
/*$("input[name='needPw']").change(function () {
    let needPw = 1;
    $("input[name='needPw']").each(function () {
        if ($(this).prop("checked")) {
            needPw = $(this).val();
        }
    });

    if (needPw == 1) {
       $(".pw").show();
    } else {
        $("#txtPassword").textbox('setValue',null);
        $(".pw").hide();
    }
});*/

//增加按钮点击事件
function addBtnClick() {
    //设置显示模式为增加
    $("#showModel").val("add");
    $("#dlg").removeAttr("disabled"); //解除Dialog禁用
    $("#dlg-buttons").show(); //显示按钮

    // 设置用户名和账号取消只读
    $("#txtUserAccount").textbox('textbox').attr("readonly",false);
    $("#txtUserName").textbox('textbox').attr("readonly",false);

    //清空表单
    $("#fm").form('clear');
    $("input[name='sex'][value='0']").prop("checked", true);
    $("input[name='needPw']:first").prop("checked",true);
    $(".pw").show();
    $("#cboRoleName").combobox({
        url: '/json/Users/queryRole',
        editable: false,
        valueField:'roleId',
        textField:'roleName'
    });

    //显示Dialog
    $("#dlg").dialog('open').dialog('setTitle', lang.getText("btn_add"));
};

//Dialog确定按钮点击事件
function dlgOKBtnClick() {
    //获取显示模式
    var showModel = $("#showModel").val();

    if (showModel == "add") {
        addRecord();
    } else if (showModel == "edit") {
        editRecord();
    }
};


//增加记录提交
function addRecord() {
    $("#fm").form('submit', {
        url: "/json/Users/addUser", //后台请求路径
        onSubmit: function (param) {
            return $(this).form('validate');//验证表单
        },
        success: function (result) {
            console.log(1)
             if(result=="2"){
                $('#dlg').dialog('close');

                $.messager.alert(lang.getText("promptNotice_prompt"), lang.getText("base_operateSuccessfully"), "info");
                $('#tbl').datagrid('reload');
            } else if (result == "1") {
                $.messager.alert(lang.getText("promptNotice_prompt"),"用户名已存在", "info");
                return;
            } else if (result == "0") {
                $.messager.alert(lang.getText("promptNotice_prompt"), lang.getText("base_operateFailed"), "info");
            }
        }
    });
};



//编辑按钮点击事件
function editBtnClick() {

    // 清空表单
    $("#fm").form("clear");

    //获取选中行数据
    let checkedRows = $("#tbl").datagrid("getChecked");

    //选中行检查
    if (checkedRows.length != 1) {
        $.messager.alert(lang.getText("promptNotice_prompt"),lang.getText("promptNotice_edit"), "info");
        return;
    }

    //设置显示模式为编辑
    $("#showModel").val("edit");

    // 设置用户名和账号为只读
    $("#txtUserAccount").textbox('textbox').attr("readonly",true);
    $("#txtUserName").textbox('textbox').attr("readonly",true);

    $("#dlg").removeAttr("disabled"); //解除Dialog禁用
    $("#dlg-buttons").show(); //显示按钮


    //获取选中的数据
    let rowData = $("#tbl").datagrid('getChecked')[0];

    if (rowData.needPw == 1) {
        // 需要密码 显示
        $(".pw").show();
    } else {
        $(".pw").hide();
    }


    $("#cboRoleName").combobox({
        url: '/json/Users/queryRole',
        editable: false,
        valueField:'roleId',
        textField:'roleName'
    });

    $("#txtPassword").textbox("setValue",rowData.password);
    $("#txtUserAccount").textbox("setValue", rowData.account);
    $("#txtUserName").textbox("setValue", rowData.empName);
    $("#txtTel").textbox("setValue", rowData.tel);
    $("#cboRoleName").combobox("setValue", rowData.roleId);
    $("#cboLineName").combobox("setValue",rowData.lineId);
    $("#cboOrderName").combobox("setValue",rowData.orderId);
    $("#txtMailAccount").textbox("setValue", rowData.email);
    if(rowData.sex == "0"){

        $("input[name='sex'][value='0']").prop("checked", true);
    }else{
        $("input[name='sex'][value='1']").prop("checked", true);
    }

    if(rowData.needPw == "0"){

        $("input[name='needPw'][value='0']").prop("checked", true);
    }else{
        $("input[name='needPw'][value='1']").prop("checked", true);
    }
    $("#txtMemo").textbox("setValue", rowData.memo);

    //显示Dialog
    $('#dlg').dialog('open').dialog('setTitle', lang.getText("btn_edit"));
}


//编辑记录提交
function editRecord() {
    let empId = $("#tbl").datagrid('getChecked')[0].userId;
    $("#empId").val(empId);
    $('#fm').form('submit', {
        url: "/json/Users/edituser",//后台请求路径
        onSubmit: function (param) {
            param.empId = empId;

            return $(this).form('validate');
        },
        success: function (result) {
            //result = "true";
            if (result == "true") {
                //成功后提交到设备
             $('#dlg').dialog('close');

                $.messager.alert(lang.getText("promptNotice_prompt"), lang.getText("base_operateSuccessfully"), "info");
                $('#tbl').datagrid('reload');
            } else if (result == "3") {
                $.messager.alert(lang.getText("promptNotice_prompt"),"账号已存在", "info");
                return;
            } else if (result == "false") {
                $.messager.alert(lang.getText("promptNotice_prompt"),lang.getText("base_operateFailed"), "info");
                return;
            }
        }
    });
}


//密码初始化
function resetPassword(){
    //获取选中行数据
    var checkedRows = $("#tbl").datagrid("getChecked");
    //选中行检查
    if (checkedRows.length == 0) {
        $.messager.alert(lang.getText("promptNotice_prompt"), lang.getText("promptNotice_resetPsw"), "info");
        return;
    }
    //获取数据的主键名
    var idField = $("#tbl").datagrid('options').idField;

    //获取选中的ID（多选时，逗号分隔）
    /*var strIDlist = "";
    for (var i = 0; i < checkedRows.length; i++) {
        strIDlist += "" + checkedRows[i][idField] + "";

        if (i < checkedRows.length - 1) {
            strIDlist += ",";
        }
    }*/

    let strIDlist = [];
    for (let i = 0; i < checkedRows.length; i++) {
        strIDlist.push(checkedRows[i][idField]);
    }
    $.messager.confirm(lang.getText("promptNotice_prompt"),lang.getText("promptNotice_delConfirm"), function (r) {
        if (r) {
            $('#fm').form('submit', {
                url: "/json/Users/updatepassword",//后台请求路径
                onSubmit: function (param) {
                    param.newDelIDs = strIDlist;
                },
                success: function (result) {
                    //result = "true";
                    if (result == "true") {
                        $('#dlg').dialog('close');

                        $("#tbl").datagrid('clearSelections');
                        $("#tbl").datagrid('clearChecked');

                        $.messager.alert(lang.getText("promptNotice_prompt"), lang.getText("base_operateSuccessfully"), "info");

                        $("#tbl").datagrid('reload');
                    }

                    else {
                        $.messager.alert(lang.getText("promptNotice_prompt"),  lang.getText("base_operateFailed"), "info");
                        return;
                    }
                }
            });
        }
    });
};




function searchdata() {
    // var  ProdLine_ID = $("#searchProdLineID").combobox("getValue");
     var UserAccount = $("#searchUseraccount").val();
     var UserName = $("#searchUserName").val();
         $("#tbl").datagrid('load', {

             UserAccount: UserAccount,
             UserName: UserName
         });
     }

 //格式化时间
 function ChangeDateFormat(cellval) {
         try {
           //  var date = new Date(parseInt(cellval.replace("/Date(", "").replace(")/", ""), 10));
             var date = new Date(cellval);
             var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
             var currentDate = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
             return date.getFullYear() + "-" + month + "-" + currentDate;
         //    return date.getFullYear() + "-" + month + "-" + currentDate+" " + date.getHours() + ":" + date.getMinutes();
         } catch (e) {
             return "";
         }
     }



 function formatterdate(val, row) {
     if (val != null) {
         var str = ChangeDateFormat(val);
         return str;
     }
 }

//Dialog取消按钮点击事件
 function dlgCancelBtnClick() {
     $('#dlg').dialog('close');
 }




//删除按钮点击事件
 function delBtnClick() {
     //获取选中行数据
     var checkedRows = $("#tbl").datagrid("getChecked");
     // debugger;

     //选中行检查
     if (checkedRows.length == 0) {
         $.messager.alert(lang.getText("promptNotice_prompt"), lang.getText("promptNotice_del"), "info");
         return;
     }

     // 判断删除用户是erp还是mes的  erp的不能删除  mes的可以删除
     for (let i = 0;i < checkedRows.length; i++) {
         if (checkedRows[i].mesOrErpEmp !== 0) {
             $.messager.alert(lang.getText("promptNotice_prompt"), 'ERP基础信息不能删除', "info");
             return;
         }
     }

     //获取数据的主键名
     var idField = $("#tbl").datagrid('options').idField;
     //获取选中的ID（多选时，逗号分隔）
     var delIDs = "";
     var strRoleId = "";
     for (var i = 0; i < checkedRows.length; i++) {
        delIDs += "" + checkedRows[i][idField] + "";
         if (i < checkedRows.length - 1) {
            delIDs += ",";
             strRoleId += ",";
         }
     }
     // 下面onSubmit提交的时候已经带了一个过去  这里就不用在form表单里再设置隐藏域了
     // $('#delIDs').val(delIDs);
     $.messager.confirm(lang.getText("promptNotice_prompt"),lang.getText("promptNotice_delConfirm"), function (r) {
         if (r) {

             $('#fm').form('submit', {
                 url: "/json/Users/removeuser",//后台请求路径
                 data: $("#fm").serialize(),
                 onSubmit: function (param) {
                     param.deleteIDs = delIDs;
                 },
                 success: function (result) {
                     // result = "true";
                     if (result == "true") {
                        //成功后提交到设备
                        /*$.ajax({
                            url: "/json/Users/removeuser",
                            type: "post",
                            data: $("#fm").serialize()
                            });*/

                         $('#dlg').dialog('close');

                         $("#tbl").datagrid('clearSelections');
                         $("#tbl").datagrid('clearChecked');

                         $.messager.alert(lang.getText("promptNotice_prompt"), lang.getText("base_operateSuccessfully"), "info");

                         $("#tbl").datagrid('reload');
                     }
                    /* else if (result == "1") {
                         $.messager.alert($.i18n.prop('string_hint'), $.i18n.prop('string_no_record_remove'), "info");
                         return;
                     }*/
                     else {
                        $.messager.alert(lang.getText("promptNotice_prompt"), lang.getText("base_operateFailed"), "info");
                         return;
                     }
                 }
             });
         }
     });
 }
