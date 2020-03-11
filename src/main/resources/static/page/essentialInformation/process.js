$(function() {

    changeState();

    // Combobox初始化
    $("#parentId").combobox({
        url : '/json/GoodsMaterial/goodcombox',
        editable : true,
        method : 'get',
        mode : 'remote',
        valueField : 'goodsId',
        textField : 'goodsName',
        filter: function (q,row) {
            let opts = $(this).combobox('options');
            return row[opts.textField].indexOf(q) > -1;
        }
    });
    // 下拉框实现多选
    $("#equipmentId").combobox({
        url : '/json/OrderDetail/comsubox',
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
        url : "/json/OrderDetail/OrderDetaillistpage",
        idField : "orderId",
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
                field:'orderName',
                width:200,
                align:'center',
                title:'工序名称'
            },
            {
                field:'parentName',
                width:200,
                align:'center',
                title:'产品名称'
            },
            {
                field:'equipmentName',
                title:'设备',
                align:'center',
                width:200
            },
            {
                field:'orderType',
                title:'工序类型',
                align:'center',
                width:200,
                formatter: function(value,rowData){
                    var str = rowData.orderType;
                    switch(str)
                    {
                        case 0:
                            value = "普通工序";
                            break;
                        case 1:
                            value = "质检工序";
                            break;
                        case 2:
                            value = "返修工序";
                            break;
                        case 3:
                            value = "包装工序";
                            break;
                        case 4:
                            value = "装配工序";
                            break;
                    }
                    return value;
                }
            },
            {
                field:'orderNum',
                title:'顺序',
                align:'center',
                width:200,
                sortable : true,
                sorter: function (a,b) {
                    return (a > b ? 1:-1);
                }
            },
            {
                field:'processContent',
                width:300,
                align:'center',
                title:'工序内容'
            },
            {
                field:'remark',
                title:'备注',
                align:'center',
                width:300
            }
        ]]/*,
        onDblClickRow : function(rowIndex, rowData) {
            $("#dlg").attr("disabled", "disabled"); // Dialog禁用
            $("#dlg-buttons").hide(); // 隐藏按钮

            // 将grid中选中的数据，显示在Dialog中
            $('#fm').form('load', {
            	equipmentId : rowData.equipmentId,
                orderNum : rowData.orderNum,
                processContent:rowData.processContent,
                parentId:rowData.parentId,
                orderType : rowData.orderType,
                remark : rowData.remark,
                orderName:rowData.orderName
            });

            // 显示Dialog
            $('#dlg').dialog('open').dialog('setTitle', '工序详细');
        }*/
    });
});

// 搜索按钮事件
function searchData() {
    let searchGoodsName = $("#searchGoodsName").val();
    let searchOrderName = $("#searchOrderName").val();
    $("#tbl").datagrid('load', {
        goodsName: searchGoodsName,
        orderName : searchOrderName
    });
}

// 增加按钮点击事件
function addBtnClick() {
    // 设置显示模式为增加
    $("#showModel").val("add");

    $("#dlg").removeAttr("disabled"); // 解除Dialog禁用
    $("#dlg-buttons").show(); // 显示按钮

    // 显示Dialog
    $("#dlg").dialog('open').dialog('setTitle', '添加工序');

    // 清空表单
    $("#fm").form('clear');

    // 默认工序类型第一个选中
    $("input[name='orderType']:first").prop("checked","checked");
    // 显示工序顺序
    $(".orderNum").show();
}

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

    if (rowData.orderType !== 2) {
        // 显示工序顺序
        $(".orderNum").show();
    }else {
        $(".orderNum").hide();
    }

    // 表单数据填充
    $('#fm').form('load', {
        orderId : rowData.orderId,
        equipmentId : rowData.equipmentId,
        orderNum : rowData.orderNum,
        processContent:rowData.processContent,
        parentId:rowData.parentId,
        orderType : rowData.orderType,
        remark : rowData.remark,
        orderName: rowData.orderName
    });

    // 显示Dialog
    $('#dlg').dialog('open').dialog('setTitle', '编辑设备');
}

// 删除按钮点击事件
function delBtnClick() {
    // 获取选中行数据
    var checkedRows = $("#tbl").datagrid("getChecked");

    // 选中行检查
    if (checkedRows.length === 0) {
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
        strName+= checkedRows[i].linename+"";

        if (i < checkedRows.length - 1) {
            strIDlist += ",";
            strName+=",";
        }
    }

    $.messager.confirm('提示', PromptNotice.delConfirm, function(r) {
        if (r) {
            $('#fm').form('submit', {
                url : "/json/OrderDetail/removeOrderDetail",// 后台请求路径
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
}

// Dialog确定按钮点击事件
function dlgOKBtnClick() {
    // 获取显示模式
    var showModel = $("#showModel").val();

    if (showModel === "add") {
        addRecord();
    } else if (showModel === "edit") {
        editRecord();
    }
}

// Dialog取消按钮点击事件
function dlgCancelBtnClick() {
    $('#dlg').dialog('close');
}

// 如果工序类型是维修工序  隐藏工序顺序输入框  否则 显示
function changeState() {
    $("input[name='orderType']").change(function () {
        let orderType = 0;

        $("input[name='orderType']").each(function () {
            if ($(this).prop("checked")) {
                orderType = $(this).val();
            }
        });

        if (orderType == 2) {
            // 设置顺序为null
            $("#orderNum").textbox('setValue',null);
            // 隐藏工序顺序
            $(".orderNum").hide();
        }else {
            // 显示工序顺序
            $(".orderNum").show();
        }
    });
}

// 增加记录提交
function addRecord() {
    $("#fm").form('submit', {
        url : "/json/OrderDetail/addOrderDetail", // 后台请求路径
        onSubmit : function(param) {
            return $(this).form('validate');// 验证表单
        },
        success : function(result) {
            if (result === "true") {
                $('#dlg').dialog('close');
                $.messager.alert(lang.getText("promptNotice_prompt"), lang.getText("base_operateSuccessfully"), "info");
                $('#tbl').datagrid('reload');
            }else if (result === "1") {
                $.messager.alert(lang.getText("promptNotice_prompt"),lang.getText("base_operate_5"), "info");
            }else if (result === "2") {
                $.messager.alert(lang.getText("promptNotice_prompt"),lang.getText("base_operate_6"), "info");
            }else {
                $.messager.alert(lang.getText("promptNotice_prompt"),lang.getText("base_operateFailed"), "info");
            }
        }
    });
}

// 编辑记录提交
function editRecord() {
    // 获取选中行数据
    let orderId = $("#tbl").datagrid('getChecked')[0].idField;

    $('#fm').form('submit', {
        url : "/json/OrderDetail/editOrderDetail",// 后台请求路径
        onSubmit : function(param) {
            param.orderId = orderId;
            return $(this).form('validate');
        },
        success : function(result) {
            if (result === "true") {
                $('#dlg').dialog('close');
                $.messager.alert(lang.getText("promptNotice_prompt"), lang.getText("base_operateSuccessfully"), "info");
                $('#tbl').datagrid('reload');
            }else if (result === "1") {
                $.messager.alert(lang.getText("promptNotice_prompt"),lang.getText("base_operate_5"), "info");
            }else if (result === "2") {
                $.messager.alert(lang.getText("promptNotice_prompt"),lang.getText("base_operate_6"), "info");
            } else {
                $.messager.alert(lang.getText("promptNotice_prompt"),lang.getText("base_operateFailed"), "info");
            }
        }
    });
}