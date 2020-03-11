$(function () {

    // grid初始化
    $("#tbl").datagrid({
        url : "/json/equipmentReply/getEquipmentReplyByPage",
        idField : "replyId",
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
                field:'replyId',
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
                title:'通知时间'
            },
            {
                field:'replyTime',
                width:200,
                align:'center',
                title:'反馈时间'
            },
            {
                field:'repairDetail',
                width:200,
                align:'center',
                title:'维保明细'
            },
            {
                field:'repairState',
                width:200,
                align:'center',
                title:'维保状态',
                formatter:function (value,row) {
                    let state = row.repairState;
                    switch (state) {
                        case 0:
                            value = "未维保";
                            break;
                        case 1:
                            value = "已维保";
                            break;
                    }
                    return value;
                }
            },
            {
                field:'taskUserName',
                width:200,
                align:'center',
                title:'任务人'
            }
        ]]
    });

});


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
        equipmentName: rowData.equipmentName,
        repairDetail: rowData.repairDetail,
        taskUserName: rowData.taskUserName,
        createTime: rowData.createTime
    });

    //显示Dialog
    $('#dlg').dialog('open').dialog('setTitle', lang.getText("btn_edit"));
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

//编辑记录提交
function editRecord() {
    let id = $("#tbl").datagrid('getChecked')[0].replyId;

    $('#fm').form('submit', {
        url: "/json/equipmentReply/modifyEquipmentReply",//后台请求路径
        onSubmit: function (param) {
            param.replyId = id;
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
