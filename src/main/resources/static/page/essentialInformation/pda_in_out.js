$(function () {
    // grid初始化
    $("#tbl").datagrid({
        url : "/json/pdaInOut/getPdaInOutByPage",
        idField : "id",
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
                field:'id',
                width:200,
                align:'center',
                title:'主键',
                hidden:true
            },
            {
                field:'empName',
                width:200,
                align:'center',
                title:'用户名'
            },
            {
                field:'inTime',
                width:200,
                align:'center',
                title:'登录时间'
            },
            {
                field:'outTime',
                width:200,
                align:'center',
                title:'登出时间'
            }
        ]]
    });
});

// Dialog取消按钮点击事件
function dlgCancelBtnClick() {
    $('#dlg').dialog('close');
}

// 搜索按钮事件
function searchData() {
    let empName = $("#searchEmpName").val();
    let inOutDate = $("#searchInOutDate").datebox('getValue');
    $("#tbl").datagrid('load', {
        empName : empName,
        inOutDate : inOutDate
    });
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

    $("#dlg").removeAttr("disabled"); //解除Dialog禁用
    $("#dlg-buttons").show(); //显示按钮

    //获取选中的数据
    let rowData = checkedRows[0];
    
    $('#fm').form('load', {
        empName:rowData.empName,
        inTime:rowData.inTime,
        outTime:rowData.outTime
    });
    
    //显示Dialog
    $('#dlg').dialog('open').dialog('setTitle', lang.getText("btn_edit"));
}

//编辑记录提交
function editRecord() {

    compareDate();

    let id = $("#tbl").datagrid('getChecked')[0].id;

    $('#fm').form('submit', {
        url: "/json/pdaInOut/modifyPdaInOut",//后台请求路径
        onSubmit: function (param) {
            param.id = id;
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

function formatterdate(date) {
    let y = date.getFullYear();
    let m = date.getMonth()+1;
    let d = date.getDate();
    return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
}

function myparserdate(s) {
    if (!s) return new Date();
    let ss = (s.split('-'));
    let y = parseInt(ss[0],10);
    let m = parseInt(ss[1],10);
    let value =ss[2];
    let d=parseInt(value.substring(0,2),10);
    return new Date(y,m-1,d,0,0,0);
}

var buttons = $.extend([], $.fn.datebox.defaults.buttons);
buttons.splice(1, 0, {
    text: 'Clear',
    handler: function(target){
        $(target).combo("setValue", "").combo("setText", ""); // 设置空值
        $(target).combo("hidePanel"); // 点击清空按钮之后关闭日期选择面板

    }
});

function compareDate(){
    let inStartTime = $("#inTime").val();
    let inEndTime = $("#outTime").datetimebox('getValue');

    if(inStartTime !== '' && inEndTime !== '') {
        if(inStartTime > inEndTime) {
            $.messager.alert(lang.getText("promptNotice_prompt"), lang.getText("base_compareTime1"), "info");
            $("#outTime").datebox('setValue','');
            return;
        }
    }

}
