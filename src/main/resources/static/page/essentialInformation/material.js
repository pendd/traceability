$(function() {

    // grid初始化
    $("#tbl").datagrid({
        url : "/json/material/materialListPage",
        idField : "materialId",
        method : 'get',
        fitColumns: true,
        columns:[[
            {
                field:'ck',
                width:50,
                checkbox:true,
                align:'center'
            },
            {
                field:'materialName',
                width:200,
                align:'center',
                title:'物料名称'
            },
            {
                field:'materialSignCode',
                width:200,
                align:'center',
                title:'物料编码'
            },
            {
                field:'alarmStockUp',
                width:200,
                align:'center',
                title:'报警库存上限'
            },
            {
                field:'alarmStockDown',
                width:200,
                align:'center',
                title:'报警库存下限'
            },
            {
                field:'unit',
                width:200,
                align:'center',
                title:'单位'
            }

        ]]
    });


});


// 搜索按钮事件
function searchData() {
    let materialName = $("#searchMaterialName").val();
    let materialSignCode = $("#searchMaterialSignCode").val();
    $("#tbl").datagrid('load', {
        materialName: materialName,
        materialSignCode: materialSignCode
    });
}

// 增加按钮点击事件
function addBtnClick() {
    // 设置显示模式为增加
    $("#showModel").val("add");

    $("#dlg").removeAttr("disabled"); // 解除Dialog禁用
    $("#dlg-buttons").show(); // 显示按钮

    // 清空表单
    $("#fm").form('clear');

    // 显示Dialog
    $("#dlg").dialog('open').dialog('setTitle', '添加原料');
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
    var rowData = checkedRows[0];

    // 表单数据填充
    $('#fm').form('load', {
        materialId : rowData.materialId,
        materialName : rowData.materialName,
        materialSignCode : rowData.materialSignCode,
        alarmStockUp : rowData.alarmStockUp,
        alarmStockDown : rowData.alarmStockDown,
        unit : rowData.unit
    });

    // 显示Dialog
    $('#dlg').dialog('open').dialog('setTitle', '编辑原料');
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
                url : "/json/material/removeMaterial",// 后台请求路径
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
        url : "/json/material/addMaterial", // 后台请求路径
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
    let id = $("#tbl").datagrid('getChecked')[0].materialId;
    $('#fm').form('submit', {
        url : "/json/material/editMaterial",// 后台请求路径
        onSubmit : function(param) {
            param.materialId = id
            return $(this).form('validate');
        },
        success : function(result) {
            onResult(result, "#dlg", "#tbl", "edit");
        }
    });
};