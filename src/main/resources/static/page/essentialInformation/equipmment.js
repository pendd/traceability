$(function() {
    // Combobox初始化
    $("#lineId").combobox({
        url : '/json/equipment/combox',
        editable : true,
        method : 'get',
        valueField : 'lineId',
        textField : 'lineName',
        filter: function (q,row) {
            let opts = $(this).combobox('options');
            return row[opts.textField].indexOf(q) > -1;
        }
    });
    $("#supplierId").combobox({
        url : '/json/equipment/comsubox',
        editable : true,
        method : 'get',
        valueField : 'supplierId',
        textField : 'supplierName',
        filter: function (q,row) {
            let opts = $(this).combobox('options');
            return row[opts.textField].indexOf(q) > -1;
        }
    });
    $("#principal").combobox({
       url : '/json/Users/getAllEmp',
        editable : true,
        mode : 'remote',
        valueField : 'userId',
        textField : 'empName'
    });
    // grid初始化
    $("#tbl").datagrid({
        url : "/json/equipment/equipmentlistpage",
        idField : "equipmentId",
        method : 'get',
        fitColumns : true,
        columns:[[
            {
                field:'ck',
                width:50,
                checkbox:true,
                align:'center'
            },
            {
                field:'equipmentName',
                width:100,
                align:'center',
                title:'设备名称'
            },
            {
                field:'lineName',
                width:50,
                align:'center',
                title:'归属产线'
            },
            {
                field:'principalName',
                title:'负责人',
                align:'center',
                width:50
            },
            {
                field:'supplierName',
                title:'供应商',
                align:'center',
                width:100
            },
            {
                field:'eqptSn',
                title:'设备编号',
                align:'center',
                width:50
            },
            {
                field:'eqptType',
                title:'设备类型',
                align:'center',
                width:50
            },
            {
                field:'eqptModel',
                title:'设备型号',
                align:'center',
                width:50
            },
            {
                field:'deptName',
                title:'使用部门',
                align:'center',
                width:50
            },
            {
                field:'manufacturer',
                title:'生产商',
                align:'center',
                width:100
            },
            {
                field:'releaseDate',
                title:'出厂日期',
                align:'center',
                width:50
            },
            {
                field:'serviceLimit',
                title:'保修期限',
                align:'center',
                width:50
            },
            {
                field:'eqptWeight',
                title:'重量',
                align:'center',
                width:40
            },
            {
                field:'quantityUnit',
                title:'计量单位',
                align:'center',
                width:30
            },
            {
                field:'theoreticalYield',
                title:'理论产量(小时)',
                align:'center',
                width:30
            }
        ]],
        onDblClickRow : function(rowIndex, rowData) {
            $("#dlg").attr("disabled", "disabled"); // Dialog禁用
            $("#dlg-buttons").hide(); // 隐藏按钮

            // 将grid中选中的数据，显示在Dialog中
            $('#fm').form('load', {
                equipmentName : rowData.equipmentName,
                lineName : rowData.lineName,
                lineId:rowData.lineId,
                supplierId:rowData.supplierId,
                principal : rowData.principalName,
                supplierName : rowData.supplierName,
                eqptSn : rowData.eqptSn,
                eqptType : rowData.eqptType,
                eqptModel : rowData.eqptModel,
                deptName : rowData.deptName,
                manufacturer : rowData.manufacturer,
                releaseDate : rowData.releaseDate,
                serviceLimit : rowData.serviceLimit,
                eqptWeight : rowData.eqptWeight,
                quantityUnit : rowData.quantityUnit,
                theoreticalYield: rowData.theoreticalYield
            });

            // 显示Dialog
            $('#dlg').dialog('open').dialog('setTitle', '设备详细');
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
    $("#dlg").dialog('open').dialog('setTitle', '添加设备');
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
        equipmentName : rowData.equipmentName,
        lineName : rowData.lineName,
        lineId:rowData.lineId,
        supplierId:rowData.supplierId,
        principal:rowData.principal,
        principalName:rowData.principalName,
        supplierName : rowData.supplierName,
        equipmentId : rowData.equipmentId,
        eqptSn : rowData.eqptSn,
        eqptType : rowData.eqptType,
        eqptModel : rowData.eqptModel,
        deptName : rowData.deptName,
        manufacturer : rowData.manufacturer,
        releaseDate : rowData.releaseDate,
        serviceLimit : rowData.serviceLimit,
        eqptWeight : rowData.eqptWeight,
        quantityUnit : rowData.quantityUnit,
        theoreticalYield: rowData.theoreticalYield
    });

    // 显示Dialog
    $('#dlg').dialog('open').dialog('setTitle', '编辑设备');
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
        strName+= checkedRows[i].equipmentName+"";

        if (i < checkedRows.length - 1) {
            strIDlist += ",";
            strName+=",";
        }
    }

    $.messager.confirm('提示', PromptNotice.delConfirm, function(r) {
        if (r) {
            $('#fm').form('submit', {
                url : "/json/equipment/removeEquipment",// 后台请求路径
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
        url : "/json/equipment/addEquipment", // 后台请求路径
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
        url : "/json/equipment/editEquipment",// 后台请求路径
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