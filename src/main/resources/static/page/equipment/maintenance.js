$(function () {
     //加载数据
    loaddata();
});

// 页面加载完再渲染lang对应的中英文
window.onload = function () {
    let path=window.location.pathname;
    lang.getOperate(path);
};

function loaddata(){

    $("#MaintenanceTable").datagrid({
        url: "/maintenance/plans",
        method: "get",
        idField: "planId",
        fitColumns: true,
        columns : [ [ {
            field : 'ck',
            width : 50,
            checkbox : true,
            align : 'center'
        },
        {
            field : 'planId',
            title :lang.getText("adjustPlan_planId"),
            width : 100,
            align : 'center',
            title:'主键',
            hidden:true
        },
        {
            field : 'planPart',
            title :lang.getText("adjustPlan_planPart"),
            width : 100,
            align : 'center'
        },
        {
            field : 'planContent',
            title : lang.getText("adjustPlan_planContent"),
            width : 150,
            align : 'center'
        },
        {
            field : 'cycleDuration',
            title : lang.getText("adjustPlan_cycleDuration"),
            width : 100,
            align : 'center',
            formatter: function(value,row,index){
                if(row.cycleDurationUnit){
                    switch(row.cycleDurationUnit){
                    case 3:
                    default:
                        return `${value}(小时)`;
                    case 5:
                        return `${value}(天)`;
                    case 7:
                        return `${value}(月)`;
                    case 9:
                        return `${value}(年)`;
                    }
                }
                return value;
            }
        },
        {
            field : 'isDelay',
            title : lang.getText("adjustPlan_isDelay"),
            width : 100,
            align : 'center',
            formatter: function (value,row) {
                switch (row.isDelay) {
                    case 0:
                        return "未延期";
                    case 1:
                        return "延期";
                }
            }
        }]]
    });
}

// grid查询
function searchdata() {
    $("#MaintenanceTable").datagrid('load', {
        planPart:$("#planPart").val(),
        planContent:$("#planContent").val()
    });
}


//关闭面板
function closepannel() {
    $('#dlg').dialog('close');
}

//增加操作还是编辑操作
let addoredit;

//添加维保
function addAdjustPlan() {
    addoredit = "add";
    $('#fm').form('clear');//清空内容
    $("input[name='unit']").prop("checked",false);// 取消选中某值对应的项
    $("#小时").prop("checked",true); //默认时间单位是小时
    $('#dlg').dialog('open').dialog('setTitle', lang.getText("btn_add"));
}

//提交增加维保计划
function saveAddAdjustPlan() {

    $('#fm').form('submit', {
        url: "/maintenance?type=add",
        onSubmit: function (param) {
            setIsDelay();
            setDurationUnit();
            return $(this).form('validate');
        },
        success: function (result) {
            if (result == "true") {
                //成功后提交到设备
                closepannel();
                $.messager.alert(lang.getText("promptNotice_prompt"), lang.getText("base_operateSuccessfully"), "info");
                $('#MaintenanceTable').datagrid('reload');
            } else {
                $.messager.alert(lang.getText("promptNotice_prompt"),lang.getText("base_operateFailed"), "info");
                return;
            }
        }
    });
}

//提交数据
function saveAdjustPlan() {
    if (addoredit == "add") {
        //提交增加维保计划
        saveAddAdjustPlan();
    }
    if (addoredit == "edit") {
        //提交编辑维保计划
        saveEditAdjustPlan();
    }
}

//编辑维保计划
function editAdjustPlan() {

    //获取选中行
    let selectRow = $("#MaintenanceTable").datagrid("getChecked");
    if (selectRow.length != 1) {
        $.messager.alert(lang.getText("promptNotice_prompt"),lang.getText("promptNotice_edit"), "info");
    } else {
        $('#fm').form('load', {
            planPart:selectRow[0].planPart,
            planContent:selectRow[0].planContent,
            cycleDuration:selectRow[0].cycleDuration,
            cycleDurationUnit:selectRow[0].cycleDurationUnit,
            isDelay:selectRow[0].isDelay
        });
        addoredit = "edit";
        if (selectRow[0].isDelay === 1) {
            $("#isDelayCheckbox").prop("checked",true);
        } else {
            $("#isDelayCheckbox").removeAttr("checked");
        }
        $("input[name='unit']").prop("checked",false);// 取消选中某值对应的项
        if(selectRow[0].cycleDurationUnit){
            let unit="";
            switch(selectRow[0].cycleDurationUnit){
                case 3: unit="小时"; break;
                case 5: unit="天"; break;
                case 7: unit="月"; break;
                case 9: unit="年"; break;
                default: break;
            }
            if(unit){
                $("#"+unit).prop("checked",true); //默认时间单位是小时
            }
        }
        $('#dlg').dialog('open').dialog('setTitle',lang.getText("btn_edit"));
    }
}

//提交编辑维保计划
function saveEditAdjustPlan() {
    var planId = $("#MaintenanceTable").datagrid('getSelected').planId;
    $("#planId").val(planId);


    $('#fm').form('submit', {
        url: "/maintenance?type=edit",
        onSubmit: function (param) {
            setIsDelay();
            setDurationUnit();
            return $(this).form('validate');
        },
        success: function (result) {
           if (result == "true") {
            //成功后提交到设备
                closepannel();
                $.messager.alert(lang.getText("promptNotice_prompt"), lang.getText("base_operateSuccessfully"), "info");
                $('#MaintenanceTable').datagrid('reload');
            } else {
                $.messager.alert(lang.getText("promptNotice_prompt"),lang.getText("base_operateFailed"), "info");
                return;
            }
        }
    });
}

//删除维保计划
function removeAdjustPlan() {

    //获取选中行
    var selectRow = $("#MaintenanceTable").datagrid("getChecked");
    if (selectRow.length > 0) {
        //维保计划ID列表（逗号分隔）
        var ids = "";
        for (var i = 0; i < selectRow.length; i++) {
            ids += "" + selectRow[i].planId;
            if (i != selectRow.length - 1) {
                ids += ",";
            }
        }
        $.messager.confirm(lang.getText("promptNotice_prompt"),lang.getText("promptNotice_delConfirm"), function (r) {
            if (r) {

                $.ajax({
                    url: `/maintenance?ids=${ids}`,
                    method: "delete",
                    success: function(res){
                        //成功后提交到设备
                        closepannel();
                        $("#MaintenanceTable").datagrid('clearSelections');
                        $("#MaintenanceTable").datagrid('clearChecked');
                        $.messager.alert(lang.getText("promptNotice_prompt"), lang.getText("base_operateSuccessfully"), "info");
                        $("#MaintenanceTable").datagrid('reload');
                    },
                    error: function(e){
                        console.log(e);
                        $.messager.alert(lang.getText("promptNotice_prompt"), lang.getText("base_operateFailed"), "info");
                        return;
                    }
                });

            }
        })
    } else {
        $.messager.alert(lang.getText("promptNotice_prompt"), lang.getText("promptNotice_del"), "info");
        return;
    }
}

function setIsDelay(){
    if($("#isDelayCheckbox").is(":checked")){
        $("#isDelay").val(1);
    } else {
        $("#isDelay").val(0);
    }
}

function setDurationUnit() {
    debugger;
    const unit = $("input[name='unit']:checked")[0].value;
    $("#cycleDurationUnit").val(unit);
}

