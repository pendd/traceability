$(function() {
    $("#principal").combobox({
        url : '/json/Users/getAllEmp',
        editable : true,
        method: 'get',
        mode : 'remote',
        valueField : 'userId',
        textField : 'empName'
    });
    // grid初始化
    $("#tbl").datagrid({
        url : "/json/Line/linelistpage",
        idField : "lineId",
        method : 'get',
        columns:[[
            {
                field:'ck',
                width:50,
                checkbox:true,
                align:'center'
            },
            {
                field:'lineName',
                width:200,
                align:'center',
                title:'产线名称'
            },
            {
                field:'principalName',
                title:'负责人',
                align:'center',
                width:200,

            }/*,
            {
                field:'lineIp',
                width:200,
                align:'center',
                title:'产线IP'}*/
        ]],
        onDblClickRow : function(rowIndex, rowData) {
            $("#dlg").attr("disabled", "disabled"); // Dialog禁用
            $("#dlg-buttons").hide(); // 隐藏按钮

            // 将grid中选中的数据，显示在Dialog中
            $('#fm').form('load', {
                lineName : rowData.lineName,
                // lineIp:rowData.lineIp,
                principal:rowData.principal

            });

            // 显示Dialog
            $('#dlg').dialog('open').dialog('setTitle', '产线详细');
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
    $("#dlg").dialog('open').dialog('setTitle', '添加产线');
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
        lineName : rowData.lineName,
        lineIp:rowData.lineIp,
        lineId:rowData.lineId,
        principal:rowData.principal
    });

    // 显示Dialog
    $('#dlg').dialog('open').dialog('setTitle', '编辑产线');
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
        strName += "" + checkedRows[i].lineName + "";
        if (i < checkedRows.length - 1) {
            strIDlist += ",";
            strName+= ",";
        }
    }

    $.messager.confirm('提示', PromptNotice.delConfirm, function(r) {
        if (r) {
            $('#fm').form('submit', {
                url : "/json/Line/removeline",// 后台请求路径
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
        url : "/json/Line/addline", // 后台请求路径
        onSubmit : function(param) {
            return $(this).form('validate');// 验证表单
        },
        success : function(result) {
            if (result === "true") {
                $('#dlg').dialog('close');
                $.messager.alert(lang.getText("promptNotice_prompt"), lang.getText("base_operateSuccessfully"), "info");
                $('#tbl').datagrid('reload');
            } else if (result === "false") {
                $.messager.alert(lang.getText("promptNotice_prompt"),lang.getText("base_operate_2"), "info");
            }else if (result === "1") {
                $.messager.alert(lang.getText("promptNotice_prompt"),lang.getText("base_operate_3"), "info");
            } else {
                $.messager.alert(lang.getText("promptNotice_prompt"),lang.getText("base_operateFailed"), "info");
            }
        }

    });
};

// 编辑记录提交
function editRecord() {
    $('#fm').form('submit', {
        url : "/json/Line/editline",// 后台请求路径
        onSubmit : function(param) {
            return $(this).form('validate');
        },
        success : function(result) {
            if (result === "true") {
                $('#dlg').dialog('close');
                $.messager.alert(lang.getText("promptNotice_prompt"), lang.getText("base_operateSuccessfully"), "info");
                $('#tbl').datagrid('reload');
            } else if (result === "false") {
                $.messager.alert(lang.getText("promptNotice_prompt"),lang.getText("base_operate_2"), "info");
            }else if (result === "1") {
                $.messager.alert(lang.getText("promptNotice_prompt"),lang.getText("base_operate_3"), "info");
            } else {
                $.messager.alert(lang.getText("promptNotice_prompt"),lang.getText("base_operateFailed"), "info");
            }
        }
    });
}