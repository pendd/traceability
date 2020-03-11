$(function () {

    $("#principle").combobox({
        url : '/json/Users/getAllEmp',
        editable : true,
        valueField : 'userId',
        textField : 'empName',
        filter: function (q,row) {
            let opts = $(this).combobox('options');
            return row[opts.textField].indexOf(q) > -1;
        }
    });

    $("#taskUser").combobox({
        url : '/json/Users/getAllEmp',
        editable : true,
        valueField : 'userId',
        textField : 'empName',
        filter: function (q,row) {
            let opts = $(this).combobox('options');
            return row[opts.textField].indexOf(q) > -1;
        }
    });

    // grid初始化
    $("#tbl").datagrid({
        url : "/json/matrix/getAllByPage",
        idField : "matrixId",
        method : 'get',
        remoteSort:false,
        fitColumns : true,
        columns:[[
            {
                field:'ck',
                width:50,
                checkbox:true,
                align:'center'
            },
            {
                field:'matrixId',
                width:200,
                align:'center',
                title:'主键',
                hidden:true
            },
            {
                field:'matrixName',
                width:200,
                align:'center',
                title:'模具名'
            },
            {
                field:'matrixSpec',
                width:200,
                align:'center',
                title:'模具规格'
            },
            {
                field:'matrixUse',
                width:200,
                align:'center',
                title:'模具用途'
            },
            {
                field:'taskUserName',
                width:200,
                align:'center',
                title:'任务人'
            },
            {
                field:'principleName',
                width:200,
                align:'center',
                title:'责任人'
            }
        ]],
        onDblClickRow : function(rowIndex, rowData) {
            $("#dlg").attr("disabled", "disabled"); // Dialog禁用
            $("#dlg-buttons").hide(); // 隐藏按钮

            // 将grid中选中的数据，显示在Dialog中
            $('#fm').form('load', {
                matrixName: rowData.matrixName,
                matrixSpec: rowData.matrixSpec,
                matrixUse:  rowData.matrixUse,
                taskUser: rowData.taskUser,
                principle: rowData.principle
            });

            // 显示Dialog
            $('#dlg').dialog('open').dialog('setTitle', '模具详细');
        }
    });

});

// 搜索按钮事件
function searchData() {
    let searchMatrixName = $("#searchMatrixName").val();
    $("#tbl").datagrid('load', {
        matrixName: searchMatrixName
    });
}

//增加按钮点击事件
function addBtnClick() {
    //设置显示模式为增加
    $("#showModel").val("add");
    $("#dlg").removeAttr("disabled"); //解除Dialog禁用
    $("#dlg-buttons").show(); //显示按钮
    //清空表单
    $("#fm").form('clear');
    //显示Dialog
    $("#dlg").dialog('open').dialog('setTitle', lang.getText("btn_add"));
}

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

    $("#dlg").removeAttr("disabled"); //解除Dialog禁用
    $("#dlg-buttons").show(); //显示按钮

    //获取选中的数据
    let rowData = $("#tbl").datagrid('getChecked')[0];

    $('#fm').form('load', {
        matrixName: rowData.matrixName,
        matrixSpec: rowData.matrixSpec,
        matrixUse:  rowData.matrixUse,
        taskUser: rowData.taskUser,
        principle: rowData.principle
    });

    //显示Dialog
    $('#dlg').dialog('open').dialog('setTitle', lang.getText("btn_edit"));
}

// 删除按钮点击事件
function delBtnClick() {
    // 获取选中行数据
    let checkedRows = $("#tbl").datagrid("getChecked");

    // 选中行检查
    if (checkedRows.length == 0) {
        $.messager.alert(lang.getText("promptNotice_prompt"), lang.getText("promptNotice_del"), "info");
        return;
    }

    // 获取数据的主键名
    let idField = $("#tbl").datagrid('options').idField;

    // 获取选中的ID（多选时，逗号分隔）
    let strIDList = [];

    for (let i = 0; i < checkedRows.length; i++) {
        strIDList.push(checkedRows[i][idField]);
    }

    $.messager.confirm(lang.getText("promptNotice_prompt"), lang.getText("promptNotice_delConfirm"), function(r) {
        if (r) {
            $('#fm').form('submit', {
                url : "/json/matrix/removeMatrix",// 后台请求路径
                onSubmit : function(param) {
                    param.ids = strIDList;
                },
                success : function(result) {
                    if (result === "true") {
                        $('#dlg').dialog('close');

                        $("#tbl").datagrid('clearSelections');
                        $("#tbl").datagrid('clearChecked');

                        $.messager.alert(lang.getText("promptNotice_prompt"), lang.getText("base_operateSuccessfully"), "info");

                        $("#tbl").datagrid('reload');
                    } else if (result === "false") {
                        $.messager.alert(lang.getText("promptNotice_prompt"), lang.getText("base_operateFailed"), "info");
                    }
                }
            });
        }
    });
}

//Dialog确定按钮点击事件
function dlgOKBtnClick() {
    //获取显示模式
    let showModel = $("#showModel").val();

    if (showModel == "add") {
        addRecord();
    } else if (showModel == "edit") {
        editRecord();
    }
}

//增加记录提交
function addRecord() {
    $("#fm").form('submit', {
        url: "/json/matrix/addMatrix", //后台请求路径
        onSubmit: function (param) {
            return $(this).form('validate');//验证表单
        },
        success: function (result) {
            console.log(result);
            if (result == "false") {
                $.messager.alert(lang.getText("promptNotice_prompt"), lang.getText("base_operateFailed"), "info");
            } else if (result == "true") {
                $('#dlg').dialog('close');

                $.messager.alert(lang.getText("promptNotice_prompt"), lang.getText("base_operateSuccessfully"), "info");
                $('#tbl').datagrid('reload');
            }
        }
    });
}

//编辑记录提交
function editRecord() {
    let id = $("#tbl").datagrid('getChecked')[0].matrixId;

    $('#fm').form('submit', {
        url: "/json/matrix/modifyMatrix",//后台请求路径
        onSubmit: function (param) {
            param.matrixId = id;
            return $(this).form('validate');
        },
        success: function (result) {
            if (result == "true") {
                $('#dlg').dialog('close');
                $.messager.alert(lang.getText("promptNotice_prompt"), lang.getText("base_operateSuccessfully"), "info");
                $('#tbl').datagrid('reload');
            } else if (result == "false") {
                $.messager.alert(lang.getText("promptNotice_prompt"),lang.getText("base_operateFailed"), "info");
            }
        }
    });
}
