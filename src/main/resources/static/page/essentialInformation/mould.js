$(function() {
    // Combobox初始化
    $("#equipmentId").combobox({
        url : '/json/mould/combox',
        editable : true,
        method : 'get',
        valueField : 'equipmentId',
        textField : 'equipmentName',
        filter: function (q,row) {
            let opts = $(this).combobox('options');
            return row[opts.textField].indexOf(q) > -1;
        }
    });
    // grid初始化
    $("#tbl").datagrid({
        url : "/json/mould/MouldListPage",
        idField : "moldId",
        method : 'get',
        columns:[[
            {
                field:'ck',
                width:50,
                checkbox:true,
                align:'center'
            },
            {
                field:'moldName',
                width:200,
                align:'center',
                title:'模具名称'
            },
            {
                field:'equipmentName',
                width:200,
                align:'center',
                title:'设备'
            },
            {
                field:'amount',
                width:200,
                align:'center',
                title:'数量'
            },
            {
                field:'moldType',
                width:200,
                align:'center',
                title:'模具类型'
            }

        ]],
        onDblClickRow : function(rowIndex, rowData) {
            $("#dlg").attr("disabled", "disabled"); // Dialog禁用
            $("#dlg-buttons").hide(); // 隐藏按钮

            // 将grid中选中的数据，显示在Dialog中
            $('#fm').form('load', {
                moldName : rowData.moldName,
                equipmentName : rowData.equipmentName,
                moldType:rowData.moldType,
                amount : rowData.amount,
                equipmentId : rowData.equipmentId
            });

            // 显示Dialog
            $('#dlg').dialog('open').dialog('setTitle', '模具详细');
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
    $("#dlg").dialog('open').dialog('setTitle', '添加模具');
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
        moldName : rowData.moldName,
        moldId : rowData.moldId,
        equipmentName : rowData.equipmentName,
        moldType:rowData.moldType,
        amount : rowData.amount,
        equipmentId : rowData.equipmentId
    });

    // 显示Dialog
    $('#dlg').dialog('open').dialog('setTitle', '编辑模具');
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
        strName+= checkedRows[i].mouldName+"";

        if (i < checkedRows.length - 1) {
            strIDlist += ",";
            strName+=",";
        }
    }

    $.messager.confirm('提示', PromptNotice.delConfirm, function(r) {
        if (r) {
            $('#fm').form('submit', {
                url : "/json/mould/removeMould",// 后台请求路径
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
        url : "/json/mould/addMould", // 后台请求路径
        onSubmit : function(param) {
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
        url : "/json/mould/editMould",// 后台请求路径
        onSubmit : function(param) {
            // 获取选中行数据
            var checkedRow = $("#tbl").datagrid('getChecked')[0];

            // 获取选中行的主键值
            var recordID = checkedRow[$("#tbl").datagrid('options').idField];

            // 设置请求参数


            return $(this).form('validate');
        },
        success : function(result) {
            onResult(result, "#dlg", "#tbl", "edit");
        }
    });
};