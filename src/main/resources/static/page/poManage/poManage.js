$(function() {
    // 变量定义
    // ========================================================================
    /** 通用 订单状态 数据 */
    var orderStateData = [ {
        "value" : 0,
        "text" : "未开始"
    }, {
        "value" : 1,
        "text" : "进行中"
    }, {
        "value" : 2,
        "text" : "完成"
    } ];
    $("#dlg-modifyDataBox").hide();
    $("#dlg-modifyData").hide();
    var queryUrl = "poManage/getProcessOrder";

    // 方法定义
    // ========================================================================

    /**
     * 把时间戳转换成 yyyy-MM-dd hh:mm:ss 格式的字符串
     */
    function timestampToString(value) {
        if (!value) {
            return "";
        }

        return new Date(value).format("yyyy-MM-dd hh:mm:ss");
    }
    /**
     * 通过日期字符串获得时间戳
     *
     * @param dateString
     *            格式没有要求,会自动猜测
     */
    function stringToTimestamp(dateString) {
        return $.format.parseDate(dateString).date.getTime();
    }

    function openDialog(title) {
        $('#dlg').dialog('open').dialog('setTitle', title);
    }
    function closeDialog() {
        $('#dlg').dialog('close');
    }
    function refreshBaseData() {
        $("#state").combobox({
            data : orderStateData
        });

        $("#prodLineId").combobox({
            url : getUrl("equipment/prodline/prodlinelist"),
            valueField : "prodLineId",
            textField : "prodLineName",
            method : "get",
            validType : "selectValueRequired['prodLineId']"
        });


        $("#prodCode").combobox({
            url :"/json/ProdManage/queryProCombox",
            valueField : "prodCode",
            textField : "prodName",
            method : "post"

        });



    }
    function refreshData(rowData) {
        refreshBaseData();

        ajax({
            url : "poManage/getProcessOrderByOrderNo",
            method : "post",
            param : {
                orderNo : rowData.orderNo
            },
            onSuccess : function(json, textStatus, jqXHR) {
                $('#fm').form('load', {
                    orderNo : json.orderNo,
                    prodLineId : json.prodLineId,
                    state : json.state,
                    lotNo : json.lotNo,
                    prodCode : json.prodCode,
                    planStartTime : timestampToString(json.planStartTime),
                    planEndTime : timestampToString(json.planEndTime),
                    planQuantity : json.planQuantity,
                    unit : "箱"
                });
            }
        });

    }

    // function getFormData() {
    // return {
    // orderNo : $("#orderNo").textbox("getValue"),
    // prodLineId : $("#prodLineId").combobox("getValue"),
    // lotNo : $("#lotNo").textbox("getValue"),
    // prodCode : $("#prodCode").textbox("getValue"),
    // planStartTime : stringToTimestamp($("#planStartTime").datetimebox(
    // "getValue")),
    // planEndTime : stringToTimestamp($("#planEndTime").datetimebox(
    // "getValue")),
    // planQuantity : $("#planQuantity").numberbox("getValue"),
    // unit : $("#unit").textbox("getValue"),
    // targetYield : $("#targetYield").numberbox("getValue")
    // };
    // }

    function getFormJson() {
        var toMergeObj = {
            planStartTime : stringToTimestamp($("#planStartTime").datetimebox(
                    "getValue")),
            planEndTime : stringToTimestamp($("#planEndTime").datetimebox(
                    "getValue"))
        }
        return $("#fm").form("toJson", toMergeObj);
    }
    // 组件初始化
    // ========================================================================
    $("#buttonAdd").bind("click", function() {

        $("#orderNo").textbox("readonly", false);
        // $("#dlg").removeAttr("disabled"); // 解除禁用
        $("#dlg-buttons").show();
        $(".continue_add").show();
        $('#fm').form("clear");
        $('#fm').form('load', {
            unit : "瓶"
        });

        refreshBaseData();
        openDialog("添加订单");

        var onClick = function() {
            if (!$("#fm").form("validate")) {
                return;
            }

            var json = getFormJson();
            ajax({
                url : "poManage/addProcessOrder",
                method : "POST",
                contentType : "application/json",
                param : json,
                onSuccess : function(json, textStatus, jqXHR) {
                    $.messager.alert("提示", "添加成功", "info");
                    $("#POTable").datagrid('clearSelections');
                    $("#POTable").datagrid('reload');
                    if ($("#continueAdd").attr("checked")) {
                        $('#fm').form("clear");
                    } else {
                        closeDialog();
                    }

                }
            });
        };

        $("#dialogButtonOk").linkbutton({
            onClick : onClick
        });

    });

    $("#buttonEdit").bind("click", function() {
        var selectRow = $("#POTable").datagrid("getChecked");// 获取选中的复选框行数据
        if (selectRow.length != 1) {
            $.messager.alert("提示", PromptNotice.edit, "info");
            return;
        }

        var rowData = $("#POTable").datagrid('getSelected');
        refreshData(rowData);

        $("#orderNo").textbox("readonly");
        $("#dlg-buttons").show();
        $(".continue_add").hide();

        openDialog("编辑订单");

        var onClick = function() {
            if (!$("#fm").form("validate")) {
                return;
            }

            var json = getFormJson();
            ajax({
                url : "poManage/updateProcessOrder",
                method : "POST",
                contentType : "application/json",
                param : json,
                onSuccess : function(json, textStatus, jqXHR) {
                    $.messager.alert("提示", "更新成功", "info");
                    $("#POTable").datagrid('clearSelections');
                    $("#POTable").datagrid('reload');
                    closeDialog();
                }
            });
        }

        $("#dialogButtonOk").linkbutton({
            onClick : onClick
        });
    });

    // 开始
    $("#buttonStart").bind(
            "click",
            function() {
                var selectRow = $("#POTable").datagrid("getChecked");// 获取选中的复选框行数据
                if (selectRow.length == 1) {
                    if (selectRow[0].state == 0) {
                        ajax({
                            url : "poManage/buttonStart?orderNo="
                                    + selectRow[0].orderNo,
                            contentType : "application/json",
                            onSuccess : function(json, textStatus, jqXHR) {
                                if(json == "0"){
                                    $.messager.alert("提示", "更新成功", "info");
                                    $('#POTable').datagrid('reload');
                                }else{
                                    $.messager.alert("提示", "更新失败，该产线已存在开始订单", "info");
                                }

                            }
                        });

                    } else {
                        $.messager.alert("提示", "请选择未开始状态的数据", "info");
                    }
                } else {
                    $.messager.alert("提示", "只可以选择一条数据修改", "info");
                }
            });
    // 结束
    $("#buttonEnd").bind("click", function() {
        var selectRow = $("#POTable").datagrid("getChecked");
        if (selectRow.length == 1) {
            if (selectRow[0].state == 1) {
                //实际开始时间、实际结束时间、实际产量都显示
                $("#pop_actual_start_time").show();
                $("#pop_actual_end_time").show();
                $("#pop_actual_quantity").show();

                $('#modifyData').form('clear');// 清空内容
                reminderValue();
                var dateNew = Date.parse(new Date());

                $('#modifyData').form('load', {
                    actualStartTime : timestampToString(selectRow[0].actualStartTime),
                    actualEndTime : timestampToString(dateNew)
                });

//                $('#actualEndTime').datebox('setValue', timestampToString(dateNew));
                $("#actual_end_time span .textbox-text").attr("disabled",true);
                $("#actual_end_time span span a").hide();
                $("#actual_end_time .textbox ").css("background-color","#EBEBE4");

//                $('#actualStartTime').datebox('setValue', timestampToString(selectRow[0].actualStartTime));
                $("#actual_start_time span span a").hide();
                $("#actual_start_time span .textbox-text").attr("disabled",true);
                $("#actual_start_time .textbox ").css("background-color","#EBEBE4");
            } else {
                $.messager.alert("提示", "请选择进行中状态的数据", "info");
            }
        } else {
            $.messager.alert("提示", "只可以选择一条数据修改", "info");
        }
        $("#determine").linkbutton({
            onClick : sure
        });
        function sure() {
            //$("#actualStartTime").attr("disabled", true);
            close();
            var selectRow = $("#POTable").datagrid("getChecked");
            var row= $("#actualQuantity").val();
            if (!$("#modifyData").form("validate")) {
                return reminderValue();
            }
            ajax({
                url : "poManage/buttonEnd?orderNo=" + selectRow[0].orderNo + "&actualQuantity="+row,
                contentType : "application/json",
                onSuccess : function(json, textStatus, jqXHR) {
                    $.messager.alert("提示", "更新成功", "info");
                    $('#POTable').datagrid('reload');

                }
            });

        }

    });
    $("#cancel").bind("click", close);
    function close(){
        $('#dlg-modifyDataBox').dialog('close');
    }
    // 显示修改窗口
    function reminderValue() {
        $('#dlg-modifyDataBox').dialog({
            title : '修改数据',
            width : 358,
            height : 250,
            closed : false,
        });
        $("#dig-buttons").show();
    }

    // 生产数据修改
    $("#buttonDataEdit").bind("click", function() {
        var selectRow = $("#POTable").datagrid("getChecked");
            if (selectRow.length == 1) {
                $("#pop_actual_start_time").show();
                $("#pop_actual_end_time").show();
                $("#pop_actual_quantity").show();

                if (selectRow[0].state == 1) { //进行中订单
                    //进行中订单，只能修改实际开始时间
                    $("#pop_actual_end_time").hide();
                    $("#pop_actual_quantity").hide();
                    $("#actual_start_time span .textbox-text").attr("disabled", false);
                    $("#actual_start_time span .textbox-text").attr("data-options", "required:true");
                    $("#actual_start_time span span a").show();

                    $('#modifyData').form('clear');// 清空内容
                    $('#modifyData').form('load', {
                        actualStartTime : timestampToString(selectRow[0].actualStartTime)
                    });

                    reminderValue();
                } else if (selectRow[0].state == 2) { //已完成订单
                     $("#actual_end_time span .textbox-text").attr("disabled",false);
                     $("#actual_start_time span .textbox-text").attr("disabled",false);
                     $("#actual_quantity span .textbox-text").attr("data-options","required:true");
                     $("#actual_quantity span .textbox-value").attr("data-options","required:true");

                     $('#modifyData').form('clear');// 清空内容
                     $('#modifyData').form('load', {
                         actualQuantity : selectRow[0].actualQuantity,
                         actualStartTime : timestampToString(selectRow[0].actualStartTime),
                         actualEndTime : timestampToString(selectRow[0].actualEndTime),
                     });
                     $("#actual_start_time span span a").show();
                     $("#actual_end_time span span a").show();

                    reminderValue();
                }else{
                    $.messager.alert("提示", "只有进行中、完成状态的数据可以修改", "info");
                }
            }else{
                $.messager.alert("提示", "只可以选择一条数据修改", "info");
            }
            $("#determine").linkbutton({
                onClick : sure
            });
            function sure() {
                //$("#actualStartTime").attr("disabled", true);
                close();
                var selectRow = $("#POTable").datagrid("getChecked");
                var row= $("#actualQuantity").val();
                var actualStartTime = $('#actualStartTime').datebox('getValue');
                var actualEndTime = $('#actualEndTime').datebox('getValue');

                //输入检查
                if (selectRow[0].state == 1) { //进行中订单，只检查开始时间是否输入
                    if (!actualStartTime) {
                        return reminderValue();
                    }
                } else if (!$("#modifyData").form("validate")) {
                    return reminderValue();
                }

                //更新
                if (selectRow[0].state == 1) { //进行中订单，只更新开始时间
                    ajax({
                        url : "poManage/buttonRunningDataEdit?orderNo=" + selectRow[0].orderNo + "&actualStartTime=" + actualStartTime,
                        contentType : "application/json",
                        onSuccess : function(json, textStatus, jqXHR) {
                            $.messager.alert("提示", "更新成功", "info");
                            $('#POTable').datagrid('reload');
                        }
                    });
                } else {
                    ajax({
                        url : "poManage/buttonDataEdit?orderNo=" + selectRow[0].orderNo + "&actualQuantity="+row + "&actualStartTime="+actualStartTime+"&actualEndTime="+actualEndTime,
                        contentType : "application/json",
                        onSuccess : function(json, textStatus, jqXHR) {
                            $.messager.alert("提示", "更新成功", "info");
                            $('#POTable').datagrid('reload');
                        }
                    });
                }


            }
    });

    $("#buttonRemove").bind("click", function() {
        var selectRow = $("#POTable").datagrid("getChecked");// 获取选中行
        if (selectRow.length <= 0) {
            $.messager.alert("提示", PromptNotice.del, "info");
            return;
        }

        var idList = "";// ID列表（逗号分隔）
        var idField = $("#POTable").datagrid('options').idField;
        for (var i = 0; i < selectRow.length; i++) {
            idList += "" + selectRow[i][idField] + "";
            if (i != selectRow.length - 1) {
                idList += ",";
            }
        }

        $.messager.confirm('提示', PromptNotice.delConfirm, function(isOk) {
            if (!isOk) {
                return;
            }

            ajax({
                url : "poManage/deleteProcessOrders",
                method : "post",
                param : {
                    idList : idList
                },
                onSuccess : function(json, textStatus, jqXHR) {
                    closeDialog();
                    $.messager.alert("提示", "删除成功", "info");
                    $("#POTable").datagrid('clearSelections');
                    $("#POTable").datagrid('reload');
                }
            });

        });// $.messager.confirm

    });

    $("#dialogButtonCancel").bind("click", closeDialog);
    $("#queryState").combobox({
        data : [ {
            "value" : -1,
            "text" : "全选"
        } ].concat(orderStateData)
    });
    $("#queryState").combobox("select", -1);
//    $("#queryProdCode").combobox({
//        url :"/json/ProdManage/queryProCombox?value=all",
//        valueField : "prodCode",
//        textField : "prodName",
//        method : "post"
//
//    });
    // 点击查询按钮的事件处理.
    $("#queryButton").linkbutton({
        onClick : function() {
            $("#POTable").datagrid("load", {
                orderNo : $("#queryOrderNo").textbox("getValue"),
                lotNo : $("#queryLotNo").textbox("getValue"),
//                prodCode : $("#queryProdCode").textbox("getValue"),
                state : $("#queryState").textbox("getValue")
            });
        }
    });

    $("#POTable").datagrid({
        url : getUrl(queryUrl),
        idField : "orderNo",
        // selectOncheck : false,
        // checkOnSelect : false,
        // singleSelect : true,
        columns : [ [ {
            field : 'ck',
            width : 50,
            checkbox : true,
            align : 'center'
        }, {
            field : 'orderNo',
            title : '订单编号',
            align : 'center'
        },{
            field : 'lotNo',
            title : '批次号',
            align : 'center'
        }, {
            field : 'state',
            title : '订单状态',
            align : 'center',
            formatter : function(value, rowData, rowIndex) {
                return orderStateData[value].text;
            }
        },  {
            field : 'planStartTime',
            title : '计划开始时间',
            align : 'center',
            formatter : timestampToString
        }, {
            field : 'planEndTime',
            title : '计划结束时间',
            align : 'center',
            formatter : timestampToString
        }, {
            field : 'planQuantity',
            title : '计划产量',
            align : 'center'
        },  {
            field : 'actualStartTime',
            title : '实际开始时间',
            align : 'center',
            formatter : timestampToString
        }, {
            field : 'actualEndTime',
            title : '实际结束时间',
            align : 'center',
            formatter : timestampToString
        }, {
            field : 'actualQuantity',
            title : '实际产量',
            align : 'center'
        }, {
            field : 'empName',
            title : '创建人',
            align : 'center'
        }, {
            field : 'createTime',
            title : '创建时间',
            align : 'center',
            formatter : timestampToString
        } ] ],
        onDblClickRow : function(rowIndex, rowData) {
            $("#dlg").attr("disabled", "disabled"); // 禁用
            $("#dlg-buttons").hide();
            $(".continue_add").hide();

            openDialog("订单明细");
            refreshData(rowData);
        }
    }); // datagrid
    // =============================================================================================

});