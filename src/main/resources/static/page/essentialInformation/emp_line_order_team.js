$(function () {

   // 用户下拉列表
    $("#empId").combobox({
        url : '/json/Users/getAllEmp',
        editable : true,
        mode : 'remote',
        valueField : 'userId',
        textField : 'empName'
    });

   // 产线下拉列表
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

    // 产品下拉列表
    $("#goodsId").combobox({
        url : '/json/empLineOrderTeam/getGoodsIdAndNameByQ',
        editable : true,
        method : 'get',
        mode : 'remote',
        valueField : 'goodsId',
        textField : 'goodsName',
        onSelect: function (ret) {
            console.log('测试ing。。。。。。。。。。。。。。。')
            $("#orderId").combobox('setValue',null);
            let url = '/json/OrderDetail/getOrderIdOrderName?goodsId='+ret.goodsId;
            // $("#orderId").combobox('reload',url);
            $("#orderId").combobox({
                url: url,
                editable: true,
                method: 'get',
                mode: 'remote',
                valueField: 'orderId',
                textField: 'orderName'
            });
        }
    });

   // 工序下拉列表
    /*$("#orderId").combobox({
        url : '/json/OrderDetail/getOrderIdOrderName',
        editable : true,
        method : 'get',
        valueField : 'orderId',
        textField : 'orderName',
        filter: function (q,row) {
            let opts = $(this).combobox('options');
            return row[opts.textField].indexOf(q) > -1;
        }
    });*/

   // 班组下拉列表
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
        url : "/json/empLineOrderTeam/getEmpLineOrderTeam",
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
                field:'lineName',
                width:200,
                align:'center',
                title:'产线名称'
            },
            {
                field:'goodsName',
                width:200,
                align:'center',
                title:'产品名'
            },
            {
                field:'orderName',
                width:200,
                align:'center',
                title:'工序名称'
            },
            {
                field:'teamName',
                title:'班组名称',
                align:'center',
                width:200
            }
        ]]/*,
        onDblClickRow : function(rowIndex, rowData) {
            $("#dlg").attr("disabled", "disabled"); // Dialog禁用
            $("#dlg-buttons").hide(); // 隐藏按钮

            // 将grid中选中的数据，显示在Dialog中
            $('#fm').form('load', {
                empId : rowData.empId,
                lineId : rowData.lineId,
                orderId : rowData.orderId,
                teamId : rowData.teamId,
                goodsId: rowData.goodsId
            });

            // 显示Dialog
            $('#dlg').dialog('open').dialog('setTitle', '配置详细');
        }*/
    });

});

// Dialog取消按钮点击事件
function dlgCancelBtnClick() {
    $('#dlg').dialog('close');
}

// 搜索按钮事件
function searchData() {
    let searchEmpName = $("#searchEmpName").val();
    let searchLineName = $("#searchLineName").val();
    let searchOrderName = $("#searchOrderName").val();
    let searchTeamName = $("#searchTeamName").val();
    $("#tbl").datagrid('load', {
        empName : searchEmpName,
        lineName : searchLineName,
        orderName : searchOrderName,
        teamName : searchTeamName
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
        empId : rowData.empId,
        lineId: rowData.lineId,
        // orderId: rowData.orderId,
        teamId: rowData.teamId,
        goodsId: rowData.goodsId
    });

    let goodsId = rowData.goodsId === undefined ? '' : rowData.goodsId;

    // 工序下拉列表
    $("#orderId").combobox({
        url : '/json/OrderDetail/getOrderIdOrderName?goodsId='+goodsId,
        editable : true,
        method : 'get',
        valueField : 'orderId',
        textField : 'orderName'
    });

    $("#orderId").combobox('setValue',rowData.orderId);

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
                url : "/json/empLineOrderTeam/removeEmpLineOrderTeam",// 后台请求路径
                onSubmit : function(param) {
                    param.ids = strIDList;
                },
                success : function(result) {
                    if (result == "true") {
                        $('#dlg').dialog('close');

                        $("#tbl").datagrid('clearSelections');
                        $("#tbl").datagrid('clearChecked');

                        $.messager.alert(lang.getText("promptNotice_prompt"), lang.getText("base_operateSuccessfully"), "info");

                        $("#tbl").datagrid('reload');
                    } else if (result == "false") {
                        $.messager.alert(lang.getText("promptNotice_prompt"), lang.getText("base_operateFailed"), "info");
                    }
                }
            });
        }
    });
};

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

//增加记录提交
function addRecord() {
    $("#fm").form('submit', {
        url: "/json/empLineOrderTeam/increaseEmpLineOrderTeam", //后台请求路径
        onSubmit: function (param) {
            return $(this).form('validate');//验证表单
        },
        success: function (result) {
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
    let id = $("#tbl").datagrid('getChecked')[0].id;

    $('#fm').form('submit', {
        url: "/json/empLineOrderTeam/alterEmpLineOrderTeam",//后台请求路径
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
