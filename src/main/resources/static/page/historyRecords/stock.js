$(function(){
    //渲染入库历史记录表格
    $('#MaterialInHistoryTable').datagrid({
        url:'/json/materialStoreroomHistory/getAllMaterialStoreroomHistory',
        idField:'materialCode',
        queryParams:{"inOutType":0},
        fitColumns:true,
        columns:[[
            {
                field:'materialCode',
                title:'原料编号',
                width:200,
                align:'center'
            },
            {
                field:'supplierNumber',
                title:'原批次号',
                width:200,
                align:'center'
            },
            {
                field:'materialName',
                title:'原料名称',
                width:200,
                align:'center'
            },
            {
                field:'unit',
                title:'单位',
                width:150,
                align:'center'
            },
            {
                field:'amount',
                title:'数量',
                width:150,
                align:'center'
            },
            {
                field:'isHistory',
                title:'原始入库',
                width:160,
                align:'center',
                formatter:function (value, rowData, rowIndex) {
                    var str = rowData.isHistory;
                    switch (str) {
                        case 0:
                            value = "否";
                            break;
                        case 1:
                            value = "是";
                            break;
                    }
                    return value;
                }
            },
            {
                field:'qualified',
                title:'是否合格',
                width:180,
                align:'center',
                formatter:function (value, rowData, rowIndex) {
                    var str = rowData.qualified;
                    switch (str) {
                        case 0:
                            value = "否";
                            break;
                        case 1:
                            value = "是";
                            break;
                    }
                    return value;
                }
            },
            /*{
                field:'supplierName',
                title:'供应商',
                width:200,
                align:'center'
            },*/
            {
                field:'storeroomName',
                title:'库房',
                width:150,
                align:'center'
            },
            {
                field:'empName',
                title:'操作员',
                width:200,
                align:'center'
            },
            {
                field:'createTime',
                title:'入库时间',
                width:200,
                formatter:timestampToString,
                align:'center'
            }
        ]]
    });
    $("#queryInButton").linkbutton({
        onClick : function() {
            $("#MaterialInHistoryTable").datagrid("load", {
                startTime : $("#inStartTime").datebox("getValue"),
                endTime : $("#inEndTime").textbox("getValue"),
                inOutType : 0
            });
        }
    });
    //渲染出库历史记录表格
    $('#MaterialOutHistoryTable').datagrid({
        url:'/json/materialStoreroomHistory/getAllMaterialStoreroomHistory',
        fitColumns:true,
        queryParams:{"inOutType":1},
        columns:[[
            {
                field:'materialCode',
                title:'原料编号',
                width:200,
                align:'center'
            },
            {
                field:'produceNumber',
                title:'生产工单号',
                width:200,
                align:'center'
            },
            {
                field:'materialName',
                title:'原料名称',
                width:200,
                align:'center'
            },
            {
                field:'unit',
                title:'单位',
                width:150,
                align:'center'
            },
            {
                field:'amount',
                title:'数量',
                width:150,
                align:'center'
            },
            {
                field:'qualified',
                title:'是否合格',
                width:180,
                align:'center',
                formatter:function (value, rowData, rowIndex) {
                    var str = rowData.qualified;
                    switch (str) {
                        case 0:
                            value = "否";
                            break;
                        case 1:
                            value = "是";
                            break;
                    }
                    return value;
                }
            },
            {
                field:'storeroomName',
                title:'库房',
                width:150,
                align:'center'
            },
            {
                field:'empName',
                title:'操作员',
                width:200,
                align:'center'
            },
            {
                field:'createTime',
                title:'出库时间',
                width:200,
                formatter:timestampToString,
                align:'center'
            }
        ]]
    });
    $("#queryOutButton").linkbutton({
        onClick : function() {
            $("#MaterialOutHistoryTable").datagrid("load", {
                workNumber : $("#searchWorkNumber").textbox("getValue"),
                startTime : $("#outStartTime").datebox("getValue"),
                endTime : $("#outEndTime").textbox("getValue"),
                inOutType : 1
            });
        }
    });

});

/**
 * 把时间戳转换成 yyyy-MM-dd hh:mm:ss 格式的字符串
 */
function timestampToString(value) {
    var date =  new Date(value);
    var y = 1900+date.getYear();
    var m = "0"+(date.getMonth()+1);
    var d = "0"+date.getDate();
    return y+"-"+m.substring(m.length-2,m.length)+"-"+d.substring(d.length-2,d.length);
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

function formatterdate(date) {
    var y = date.getFullYear();
    var m = date.getMonth()+1;
    var d = date.getDate();
    return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
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

var buttons = $.extend([], $.fn.datebox.defaults.buttons);
buttons.splice(1, 0, {
    text: 'Clear',
    handler: function(target){
        $(target).combo("setValue", "").combo("setText", ""); // 设置空值
        $(target).combo("hidePanel"); // 点击清空按钮之后关闭日期选择面板

    }
});

function onSelect(date){
    let inStartTime = $("#inStartTime").datebox('getValue');
    let inEndTime = $("#inEndTime").datebox('getValue');

    let outStartTime = $('#outStartTime').datebox('getValue');
    let outEndTime = $('#outEndTime').datebox('getValue');

    if(inStartTime !== '' && inEndTime !== '') {
        if(inStartTime > inEndTime) {
            $.messager.alert(lang.getText("promptNotice_prompt"), lang.getText("base_compareTime"), "info");
            $("#inEndTime").datebox('setValue','');
            return;
        }
    }

    if(outStartTime !== '' && outEndTime !== '') {
        if(outStartTime > outEndTime) {
            $.messager.alert(lang.getText("promptNotice_prompt"), lang.getText("base_compareTime"), "info");
            $("#outEndTime").datebox('setValue','');
            return;
        }
    }

}
