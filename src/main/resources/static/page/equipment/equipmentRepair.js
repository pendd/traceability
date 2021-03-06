$(function () {

    // 设备下拉列表
    $("#equipmentId").combobox({
        url : '/json/equipmentRepair/getEquipmentName',
        method: 'get',
        editable : true,
        valueField : 'equipmentId',
        textField : 'equipmentName',
        filter: function (q,row) {
            let opts = $(this).combobox('options');
            return row[opts.textField].indexOf(q) > -1;
        }
    });

    // grid初始化
    $("#tbl").datagrid({
        url : "/json/equipmentRepair/getAllByPage",
        idField : "repairId",
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
                field:'repairId',
                width:200,
                align:'center',
                title:'主键',
                hidden:true
            },
            {
                field:'equipmentName',
                width:200,
                align:'center',
                title:'设备名'
            },
            {
                field:'createTime',
                width:200,
                align:'center',
                title:'开始时间'
            },
            {
                field:'repairContent',
                width:200,
                align:'center',
                title:'维保内容'
            },
            {
                field:'repairInterval',
                width:200,
                align:'center',
                title:'时间间隔',
                formatter: function(value,row){
                    if(row.unit){
                        switch(row.unit){
                            /*case 1:
                            default:
                                return `${value}(小时)`;*/
                            case 2:
                                return `${value}(天)`;
                            case 3:
                                return `${value}(月)`;
                            case 4:
                                return `${value}(年)`;
                        }
                    }
                    return value;
                }
            }
        ]]

    });

});

// 搜索按钮事件
function searchData() {
    let searchEquipmentName = $("#searchEquipmentName").val();
    let searchRepairContent = $("#searchRepairContent").val();
    $("#tbl").datagrid('load', {
        equipmentName: searchEquipmentName,
        repairContent: searchRepairContent
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
    $("input[name='unit'][value='2']").prop("checked", true);
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
        equipmentId: rowData.equipmentId,
        repairId: rowData.repairId,
        repairContent: rowData.repairContent,
        repairInterval: rowData.repairInterval,
        unit: rowData.unit,
        createTime: rowData.createTime
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
                url : "/json/equipmentRepair/removeEquipmentRepair",// 后台请求路径
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

//Dialog取消按钮点击事件
function dlgCancelBtnClick() {
    $('#dlg').dialog('close');
}

//增加记录提交
function addRecord() {
    $("#fm").form('submit', {
        url: "/json/equipmentRepair/addEquipmentRepair", //后台请求路径
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
    let id = $("#tbl").datagrid('getChecked')[0].repairId;

    $('#fm').form('submit', {
        url: "/json/equipmentRepair/modifyEquipmentRepair",//后台请求路径
        onSubmit: function (param) {
            param.repairId = id;
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
