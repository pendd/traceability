$(function () {

    for(type of ["Auto","Manual"]){
        var columns = [{
            field:'ck',
            width:50,
            checkbox:true,
            align:'center'
        },
        {
            field:'serialId',
            width:200,
            align:'center',
            title:'主键',
            hidden:true
        },
        {
            field:'stopReason',
            width:200,
            align:'center',
            title:'停机原因'
        },{
            field:'stopBeginTime',
            width:200,
            align:'center',
            title:'停机开始时间'
        },
        {
            field:'stopEndTime',
            title:'停机结束时间',
            align:'center',
            width:200
        }];
        if(type === 'Auto'){
            columns = columns.concat([{
                field:'activeBeginDate',
                width:200,
                align:'center',
                title:'有效区间-开始日期'
            },
            {
                field:'activeEndDate',
                width:200,
                align:'center',
                title:'有效区间-结束日期'
            },
            {
                field:'activeWeeks',
                width:200,
                align:'center',
                title:'有效星期'
            }]);
        }
        // grid初始化
        $("#tbl"+type).datagrid({
            url : "/stopplan/"+type+"list",
            idField : "serialId",
            method : 'post',
            remoteSort:false,
            fitColumns : true,
            columns: [columns],
            onDblClickRow : function(rowIndex, rowData) {
                let planType = !!rowData.activeBeginDate?'Auto':'Manual';
                $("#dlg"+planType).attr("disabled", "disabled"); // Dialog禁用
                $("#dlg-buttons"+planType).hide(); // 隐藏按钮
             // activeWeeks解析
                $("input[name='checkWeeks']").prop("checked",false);// 取消选中某值对应的项
                const { activeWeeks } = rowData;
                if(activeWeeks){
                    const activeWeeksArr = activeWeeks.split("|");
                    activeWeeksArr.forEach(week=>{
                        $("#"+week).prop("checked",true);
                    });
                }
                const viewData = planType ==='Auto'?{
                    stopReason : rowData.stopReason,
                    activeBeginDate : rowData.activeBeginDate,
                    activeEndDate : rowData.activeEndDate,
// activeWeeks : rowData.activeWeeks,
                    stopBeginTime : rowData.stopBeginTime,
                    stopEndTime : rowData.stopEndTime
                }:{
                    stopReason : rowData.stopReason,
                    stopBeginTime : rowData.stopBeginTime,
                    stopEndTime : rowData.stopEndTime
                };

                // 将grid中选中的数据，显示在Dialog中
                $('#fm'+planType).form('load', viewData);

                // 显示Dialog
                $('#dlg'+planType).dialog('open').dialog('setTitle', '详情');

            }
        });
    }

});

// 搜索按钮事件
function queryStopPlan(type) {
    let begin = '';
    let end = '';
    let filterBody = {};
    if(type==='Auto'){
        begin = $("#queryBeginAuto").datebox('getValue');
        end= $("#queryEndAuto").datebox('getValue');
        filterBody = {
            activeBeginDate: begin.substr(0,10),
            activeEndDate: end.substr(0,10)
        }
    }else{
        begin = $("#queryBeginDateManual").datetimebox('getValue');
        end= $("#queryEndDateManual").datetimebox('getValue');
        filterBody = {
            stopBeginTime: begin,
            stopEndTime: end
        }
    }
    $("#tbl"+type).datagrid('load', filterBody);
}

// 增加按钮点击事件
function addStopPlan(type) {
    // 设置显示模式为增加
    $("#showModel").val("add");
    $("#dlg"+type).removeAttr("disabled"); // 解除Dialog禁用
    $("#dlg-buttons"+type).show(); // 显示按钮
    // 清空表单
    $("#fm"+type).form('clear');
    $("input[name='checkWeeks']").prop("checked",false);// 取消选中某值对应的项
    // 显示Dialog
    $("#dlg"+type).dialog('open').dialog('setTitle', lang.getText("btn_add"));
}

// 编辑按钮点击事件
function editStopPlan(type) {
    // 清空表单
    $("#fm"+type).form("clear");
    $("input[name='checkWeeks']").prop("checked",false);// 取消选中某值对应的项

    // 获取选中行数据
    let checkedRows = $("#tbl"+type).datagrid("getChecked");
    // 选中行检查
    if (checkedRows.length != 1) {
        $.messager.alert(lang.getText("promptNotice_prompt"),lang.getText("promptNotice_edit"), "info");
        return;
    }

    // 设置显示模式为编辑
    $("#showModel").val("edit");

    $("#dlg"+type).removeAttr("disabled"); // 解除Dialog禁用
    $("#dlg-buttons"+type).show(); // 显示按钮

    // 获取选中的数据
    let rowData = $("#tbl"+type).datagrid('getChecked')[0];

    // activeWeeks解析
    const { activeWeeks } = rowData;
    if(activeWeeks){
        const activeWeeksArr = activeWeeks.split("|");
        activeWeeksArr.forEach(week=>{
            $("#"+week).prop("checked",true);
        });
    }

    $('#fm'+type).form('load', type==='Auto'?{
        serialId: rowData.serialId,
        stopReason : rowData.stopReason,
        activeBeginDate : rowData.activeBeginDate,
        activeEndDate : rowData.activeEndDate,
        stopBeginTime : rowData.stopBeginTime,
        stopEndTime : rowData.stopEndTime
    }:{
        serialId: rowData.serialId,
        stopReason : rowData.stopReason,
        stopBeginTime : rowData.stopBeginTime,
        stopEndTime : rowData.stopEndTime
    });

    // 显示Dialog
    $('#dlg'+type).dialog('open').dialog('setTitle', lang.getText("btn_edit"));
}

// 删除按钮点击事件
function deleteStopPlan(type) {
    // 获取选中行数据
    let checkedRows = $("#tbl"+type).datagrid("getChecked");

    // 选中行检查
    if (checkedRows.length == 0) {
        $.messager.alert(lang.getText("promptNotice_prompt"), lang.getText("promptNotice_del"), "info");
        return;
    }

    // 获取数据的主键名
    let idField = $("#tbl"+type).datagrid('options').idField;

    // 获取选中的ID（多选时，逗号分隔）
    let strIDList = [];

    for (let i = 0; i < checkedRows.length; i++) {
        strIDList.push(checkedRows[i][idField]);
    }

    $.messager.confirm(lang.getText("promptNotice_prompt"), lang.getText("promptNotice_delConfirm"), function(r) {
        if (r) {
            $('#fm'+type).form('submit', {
                url : "/stopplan/removeStopPlan",// 后台请求路径
                onSubmit : function(param) {
                    param[`ids${type}`] = strIDList;
                },
                success : function(result) {
                    if (result == "true") {
                        $('#dlg'+type).dialog('close');

                        $("#tbl"+type).datagrid('clearSelections');
                        $("#tbl"+type).datagrid('clearChecked');

                        $.messager.alert(lang.getText("promptNotice_prompt"), lang.getText("base_operateSuccessfully"), "info");

                        $("#tbl"+type).datagrid('reload');
                    } else if (result == "false") {
                        $.messager.alert(lang.getText("promptNotice_prompt"), lang.getText("base_operateFailed"), "info");
                    }
                }
            });
        }
    });
};


// Dialog确定按钮点击事件
function onDialogConfirm(type) {
    // 获取显示模式
    let showModel = $("#showModel").val();

    if (showModel == "add") {
        addRecord(type);
    } else if (showModel == "edit") {
        editRecord(type);
    }
}

// 增加记录提交
function addRecord(type) {
    $("#activeWeeks").val(buildActiveWeeks(type));
    const planType = type==='Auto'?'1':'2';

    $("#fm"+type).form('submit', {
        url: "/stopplan/addStopPlan", // 后台请求路径
        onSubmit: function (param) {
            param.type = planType;
            return $(this).form('validate');// 验证表单
        },
        success: function (result) {
            if (result == "false") {
                $.messager.alert(lang.getText("promptNotice_prompt"), lang.getText("base_operateFailed"), "info");
            } else if (result == "true") {
                $('#dlg'+type).dialog('close');

                $.messager.alert(lang.getText("promptNotice_prompt"), lang.getText("base_operateSuccessfully"), "info");
                $('#tbl'+type).datagrid('reload');
            }
        }
    });
}

// 编辑记录提交
function editRecord(type) {
    $("#activeWeeks").val(buildActiveWeeks(type));
    let id = $("#tbl"+type).datagrid('getChecked')[0].id;
    const planType = type==='Auto'?'1':'2';

    $('#fm'+type).form('submit', {
        url: "/stopplan/editStopPlan",// 后台请求路径
        onSubmit: function (param) {
            param.id = id;
            param.type = planType;
            return $(this).form('validate');
        },
        success: function (result) {
            if (result == "true") {
                $('#dlg'+type).dialog('close');
                $.messager.alert(lang.getText("promptNotice_prompt"), lang.getText("base_operateSuccessfully"), "info");
                $('#tbl'+type).datagrid('reload');
            } else if (result == "false") {
                $.messager.alert(lang.getText("promptNotice_prompt"),lang.getText("base_operateFailed"), "info");
            }
        }
    });
}

// Dialog取消按钮点击事件
function dlgCancelBtnClick(type) {
    $('#dlg'+type).dialog('close');
};

function buildActiveWeeks(type){
    var result = '';
    if(type === 'Auto'){
        $("input[name='checkWeeks']:checked").each(function(){
            result+=$(this).val();
            result+="|";
        });
    }
    return result.substr(0,result.length-1);
}

function formatterdate(date) {
    var y = date.getFullYear();
    var m = date.getMonth()+1;
    var d = date.getDate();
    return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
}

function myformattertime(date) {
    var y = date.getFullYear();
    var m = date.getMonth()+1;
    var d = date.getDate();
    var hh=date.getHours();
    var mm=date.getMinutes();
    var ss=date.getSeconds();
    return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d)+' '+(hh<10?('0'+hh):hh)+':'+(mm<10?('0'+mm):mm)+':'+(ss<10?('0'+ss):ss);
}

function myparserdate(s) {
    if (!s) return new Date();
    var ss = (s.split('-'));
    var y = parseInt(ss[0],10);
    var m = parseInt(ss[1],10);
    var value =ss[2];
    var d=parseInt(value.substring(0,2),10);
    return new Date(y,m-1,d,0,0,0);
}

function myparsertime(s) {
    if (!s) return new Date();
    var ss = (s.split('-'));
    var y = parseInt(ss[0],10);
    var m = parseInt(ss[1],10);
    var value =ss[2];
    var d=parseInt(value.substring(0,2),10);
    var hh= parseInt(value.substring(3,5),10);
    var mm= parseInt(value.substring(6,8),10);
    var sss=parseInt(value.substring(9),10);
    if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
    return new Date(y,m-1,d,hh,mm,sss);
    }
    else
    {
    return new Date();
    }
}

var buttons = $.extend([], $.fn.datebox.defaults.buttons);
buttons.splice(1, 0, {
    text: 'Clear',
    handler: function(target){
        $(target).combo("setValue", "").combo("setText", ""); // 设置空值
        $(target).combo("hidePanel"); // 点击清空按钮之后关闭日期选择面板

    }
});

function onSelect(date){
    var queryBeginAuto=$("#queryBeginAuto").datebox('getValue');
    var queryEndAuto=$("#queryEndAuto").datebox('getValue');
    var formBeginDateAuto=$("#formBeginDateAuto").datetimebox('getValue');
    var formEndDateAuto=$("#formEndDateAuto").datetimebox('getValue');

    // 时间
//    var formBeginTimeAuto=$("#formBeginTimeAuto").timespinner('getValue');
//    var formEndTimeAuto=$("#formEndTimeAuto").timespinner('getValue');

    var queryBeginDateManual=$("#queryBeginDateManual").datebox('getValue');
    var queryEndDateManual=$("#queryEndDateManual").datebox('getValue');
    var formBeginTimeManual=$("#formBeginTimeManual").datetimebox('getValue');
    var formEndTimeManual=$("#formEndTimeManual").datetimebox('getValue');

    if(queryBeginAuto!=""&&queryEndAuto!="") {
        if(queryEndAuto<queryBeginAuto) {
            $.messager.alert(lang.getText("promptNotice_prompt"), lang.getText("base_compareTime"), "info");
            $("#queryEndAuto").datebox('setValue','');
            return;
        }
     }
    if(formBeginDateAuto!=""&&formEndDateAuto!="") {
       if(formEndDateAuto<formBeginDateAuto) {
           $.messager.alert(lang.getText("promptNotice_prompt"), lang.getText("base_compareTime"), "info");
           $("#formEndDateAuto").datebox('setValue','');
           return;
       }
    }
    // 时间比较器 TODO
//    if(formBeginTimeAuto!=""&&formEndTimeAuto!=""){
//        const beginArr = formBeginTimeAuto.split(":");
//        const endArr = formEndTimeAuto.split(":");
//        if(beginArr[0]>endArr[0]||beginArr[0]===endArr[0]&&beginArr[1]>endArr[1]
//            ||beginArr[0]===endArr[0]&&beginArr[1]===endArr[1]&&beginArr[2]>endArr[2])
//            $.messager.alert(lang.getText("promptNotice_prompt"), lang.getText("base_compareTime"), "info");
//            $("#formEndTimeAuto").timespinner('setValue','');
//            return;
//    }

    if(queryBeginDateManual!=""&&queryEndDateManual!="") {
       if(queryEndDateManual<queryBeginDateManual) {
           $.messager.alert(lang.getText("promptNotice_prompt"),lang.getText("base_compareTime"), "info");
           $("#queryEndDateManual").datebox('setValue','');
           return;
       }
    }

    if(formBeginTimeManual!=""&&formEndTimeManual!="") {
        if(formEndTimeManual<formBeginTimeManual) {
            $.messager.alert(lang.getText("promptNotice_prompt"),lang.getText("base_compareTime"), "info");
            $("#formEndTimeManual").datebox('setValue','');
            return;
        }
     }
}