$(function() {
    // grid初始化
    $("#tbl").datagrid({
        url : "/json/Goods/Goodslistpage",
        idField : "goodsId",
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
                field:'goodsName',
                width:200,
                align:'center',
                title:'产品名称'
            },
            {
                field:'goodsCode',
                width:200,
                align:'center',
                title:'产品编码'
            },
            {
                field:'specs',
                width:200,
                align:'center',
                title:'产品规格'
            },
            {
                field:'goodsType',
                title:'产品类型',
                align:'center',
                width:200,

            },
            {
                field:'standardHours',
                title:'标准时长(小时)',
                align:'center',
                width:200,

            }/*,
            {
                field:'shelfLife',
                width:200,
                align:'center',
                title:'保质期(单位:月)'}*/
        ]]/*,
        onDblClickRow : function(rowIndex, rowData) {
            $("#dlg").attr("disabled", "disabled"); // Dialog禁用
            $("#dlg-buttons").hide(); // 隐藏按钮

            // 将grid中选中的数据，显示在Dialog中
            $('#fm').form('load', {
                goodsName : rowData.goodsName,
                goodsType:rowData.goodsType,
                shelfLife:rowData.shelfLife

            });

            // 显示Dialog
            $('#dlg').dialog('open').dialog('setTitle', '产品详细');
        }*/
    });
});

// 搜索按钮事件
function searchData() {
    let searchGoodsName = $("#searchGoodsName").val();
    let searchGoodsCode = $("#searchGoodsCode").val();
    $("#tbl").datagrid('load', {
        goodsName: searchGoodsName,
        goodsCode: searchGoodsCode
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
    $("#dlg").dialog('open').dialog('setTitle', '添加产品');
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
        goodsName : rowData.goodsName,
        goodsType:rowData.goodsType,
        // shelfLife:rowData.shelfLife,
        goodsId:rowData.goodsId,
        goodsCode: rowData.goodsCode,
        specs: rowData.spec,
        standardHours: rowData.standardHours
    });

    // 显示Dialog
    $('#dlg').dialog('open').dialog('setTitle', '编辑产品');
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
                url : "/json/Goods/removeGoods",// 后台请求路径
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
        url : "/json/Goods/addGoods", // 后台请求路径
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
        url : "/json/Goods/editGoods",// 后台请求路径
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