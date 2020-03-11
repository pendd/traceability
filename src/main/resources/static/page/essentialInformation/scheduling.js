$(function() {
    // Combobox初始化
    $("#teamId").combobox({
        url : '/json/Schedule/combox',
        editable : true,
        method : 'get',
        mode : 'remote',
        valueField : 'teamId',
        textField : 'teamName'
    });
    // grid初始化
    $("#tbl").datagrid({
        url : "/json/Schedule/ScheduleListPage",
        idField : "scheduleId",
        method : 'get',
        columns:[[
            {
                field:'ck',
                width:50,
                checkbox:true,
                align:'center'
            },
            {
                field:'teamName',
                width:200,
                align:'center',
                title:'班组名称'
            },
            {
                field:'startTime',
                width:200,
                align:'center',
                title:'开始时间'/*,
                formatter:function (value,rowData) {
                    var time = new Date(rowData.startTime);
                    value = time.toLocaleString();
                    return value;
                }*/
            },
            {
                field:'endTime',
                width:200,
                align:'center',
                title:'结束时间'/*,
                formatter:function (value,rowData) {
                    var time = new Date(rowData.endTime);
                    value = time.toLocaleString();
                    return value;
                }*/
            }

        ]]
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
    $("#dlg").dialog('open').dialog('setTitle', '排班');
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
    /*var star = new Date(rowData.startTime);
    var endt = new Date(rowData.endTime);*/

    // 表单数据填充
    $('#fm').form('load', {

        teamName : rowData.teamName,
        /*startTime : getMyDate(star.getTime()),
        endTime:getMyDate(endt.getTime()),*/
        startTime: rowData.startTime,
        endTime: rowData.endTime,
        teamId : rowData.teamId,
        scheduleId:rowData.scheduleId
    });

    // 显示Dialog
    $('#dlg').dialog('open').dialog('setTitle', '编辑');
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
                url : "/json/Schedule/removeSchedule",// 后台请求路径
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

    if (showModel == "add") {
        addRecord();
    } else if (showModel == "edit") {
        editRecord();
    }
}

// Dialog取消按钮点击事件
function dlgCancelBtnClick() {
    $('#dlg').dialog('close');
}

// 增加记录提交
function addRecord() {
    $("#fm").form('submit', {
        url : "/json/Schedule/addSchedule", // 后台请求路径
        onSubmit : function(param) {
            return $(this).form('validate');// 验证表单
        },
        success : function(result) {
            if (result === "true") {
                $('#dlg').dialog('close');
                $.messager.alert(lang.getText("promptNotice_prompt"), lang.getText("base_operateSuccessfully"), "info");
                $('#tbl').datagrid('reload');
            }else if (result === "1") {
                $.messager.alert(lang.getText("promptNotice_prompt"),lang.getText("base_operate_4"), "info");
            } else {
                $.messager.alert(lang.getText("promptNotice_prompt"),lang.getText("base_operateFailed"), "info");
            }
        }
    });
};

// 编辑记录提交
function editRecord() {
    $('#fm').form('submit', {
        url : "/json/Schedule/editSchedule",// 后台请求路径
        onSubmit : function(param) {
            // 获取选中行数据
            var checkedRow = $("#tbl").datagrid('getChecked')[0];

            // 获取选中行的主键值
            var recordID = checkedRow[$("#tbl").datagrid('options').idField];

            // 设置请求参数


            return $(this).form('validate');
        },
        success : function(result) {
            if (result === "true") {
                $('#dlg').dialog('close');
                $.messager.alert(lang.getText("promptNotice_prompt"), lang.getText("base_operateSuccessfully"), "info");
                $('#tbl').datagrid('reload');
            }else if (result === "1") {
                $.messager.alert(lang.getText("promptNotice_prompt"),lang.getText("base_operate_4"), "info");
            } else {
                $.messager.alert(lang.getText("promptNotice_prompt"),lang.getText("base_operateFailed"), "info");
            }
        }
    });
};
//将时间戳转为easyui日期框可以识别的日期格式，作为修改方法填充使用6/1/2012 12:30:56  月日年 时分秒
/*
function getMyDate(str){
    var oDate = new Date(str),
        oYear = oDate.getFullYear(),
        oMonth = oDate.getMonth()+1,
        oDay = oDate.getDate(),
        oHour = oDate.getHours(),
        oMin = oDate.getMinutes(),
        oSen = oDate.getSeconds(),
        oTime = getzf(oMonth) +'/'+ getzf(oDay) +'/'+ oYear +' '+ getzf(oHour) +':'+ getzf(oMin) +':'+getzf(oSen);//最后拼接时间
    return oTime;
};
//补0操作
function getzf(num){
    if(parseInt(num) < 10){
        num = '0'+num;
    }
    return num;
}*/
