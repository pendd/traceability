﻿$(function() {
    // grid初始化
    $("#tbl").datagrid({
        url : "/json/salesCompany/SalesCompanyListPage",
        idField : "comId",
        method : 'get',
        columns:[[
            {
                field:'ck',
                width:50,
                checkbox:true,
                align:'center'
            },
            {
                field:'comName',
                width:200,
                align:'center',
                title:'销售公司名称'
            },
            {
                field:'address',
                width:250,
                align:'center',
                title:'地址'
            },
            {
                field:'principal',
                width:100,
                align:'center',
                title:'负责人'
            },
            {
                field:'telephone',
                width:150,
                align:'center',
                title:'联系电话'
            },
            {
                field:'telephoneBackup',
                width:150,
                align:'center',
                title:'备用电话'
            },
            {
                field:'city',
                width:200,
                height:300,
                align:'center',
                title:'所属市'
            },
            {
                field:'remark',
                width:200,
                height:300,
                align:'center',
                title:'备注'
            }
        ]],

        onDblClickRow : function(rowIndex, rowData) {
            $("#dlg").attr("disabled", "disabled"); // Dialog禁用
            $("#dlg-buttons").hide(); // 隐藏按钮

            // 将grid中选中的数据，显示在Dialog中
            $('#fm').form('load', {
                comName :rowData.comName,
                comId :rowData.comId,
                address :rowData.address,
                principal : rowData.principal,
                telephone : rowData.telephone,
                telephoneBackup : rowData.telephoneBackup,
                remark : rowData.remark,
                city: rowData.city
            });

            // 显示Dialog
            $('#dlg').dialog('open').dialog('setTitle', '销售公司详细');
        }
    });
});

// 增加按钮点击事件
function addBtnClick() {
    // 设置显示模式为增加
    $("#showModel").val("add");

    $("#dlg").removeAttr("disabled"); // 解除Dialog禁用
    $("#dlg-buttons").show(); // 显示按钮

    // 清空表单
    $("#fm").form('clear');

    // 显示Dialog
    $("#dlg").dialog('open').dialog('setTitle', '添加供应商');
};

// 编辑按钮点击事件
function editBtnClick() {
    // 获取选中行数据
    var checkedRows = $("#tbl").datagrid("getChecked");

    // 选中行检查
    if (checkedRows.length != 1) {
        $.messager.alert("提示", PromptNotice.edit, "info");
        return;
    }

    // 设置显示模式为编辑
    $("#showModel").val("edit");

    $("#dlg").removeAttr("disabled"); // 解除Dialog禁用
    $("#dlg-buttons").show(); // 显示按钮

    // 获取选中的数据
    var rowData = $("#tbl").datagrid('getChecked')[0];

    // 表单数据填充
    $('#fm').form('load', {
        comName :rowData.comName,
        comId :rowData.comId,
        address :rowData.address,
        principal : rowData.principal,
        telephone : rowData.telephone,
        telephoneBackup : rowData.telephoneBackup,
        remark : rowData.remark,
        city : rowData.city
    });

    // 显示Dialog
    $('#dlg').dialog('open').dialog('setTitle', '编辑销售公司');
};

// 删除按钮点击事件
function delBtnClick() {
    // 获取选中行数据
    var checkedRows = $("#tbl").datagrid("getChecked");

    // 选中行检查
    if (checkedRows.length == 0) {
        $.messager.alert("提示", PromptNotice.del, "info");
        return;
    }

    // 获取数据的主键名
    var idField = $("#tbl").datagrid('options').idField;

    // 获取选中的ID（多选时，逗号分隔）
    var strIDlist = "";

    //获取选中的项的名称 （多选时，逗号分隔）
    var strName ="";
    for (var i = 0; i < checkedRows.length; i++) {
        strIDlist += "" + checkedRows[i][idField] + "";
        strName += "" + checkedRows[i].comName + "";
        if (i < checkedRows.length - 1) {
            strIDlist += ",";
            strName+= ",";
        }
    }

    $.messager.confirm('提示', PromptNotice.delConfirm, function(r) {
        if (r) {
            $('#fm').form('submit', {
                url : "/json/salesCompany/removeSalesCompany",// 后台请求路径
                onSubmit : function(param) {
                    param.delIDs = strIDlist;
                    param.delNames=strName;
                },
                success : function(result) {
                    onResult(result, "#dlg", "#tbl", "remove");
                }
            });
        }
    });
};

// Dialog确定按钮点击事件
function dlgOKBtnClick() {
    // 获取显示模式
    var showModel = $("#showModel").val();

    if (showModel == "add") {
        addRecord();
    } else if (showModel == "edit") {
        editRecord();
    }
};

// Dialog取消按钮点击事件
function dlgCancelBtnClick() {
    $('#dlg').dialog('close');
};

// 增加记录提交
function addRecord() {
    $("#fm").form('submit', {
        url : "/json/salesCompany/addSalesCompany", // 后台请求路径
        onSubmit : function(param) {
        	param.createTime = Date.parse(new Date())
            return $(this).form('validate');// 验证表单
        },
        success : function(result) {
            onResult(result, "#dlg", "#tbl", "add");
        }

    });
};

// 编辑记录提交
function editRecord() {
    $('#fm').form('submit', {
        url : "/json/salesCompany/editSalesCompany",// 后台请求路径
        onSubmit : function(param) {
            // 获取选中行数据
            var checkedRow = $("#tbl").datagrid('getChecked')[0];

            // 获取选中行的主键值
            var recordID = checkedRow[$("#tbl").datagrid('options').idField];

            // 设置请求参数
            param.createTime = Date.parse(new Date());

            return $(this).form('validate');
        },
        success : function(result) {
            onResult(result, "#dlg", "#tbl", "edit");
        }
    });
};