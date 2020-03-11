$(function(){
    refreshLine();
    refreshEqpt();
    $("#lineName").combobox({
        //相当于html >> select >> onChange事件
        onChange:function(newValue,oldValue){
            var lineId = $("#lineName").combobox('getValue');
            refreshEqpt(lineId);
            // alert($("#lineName").combobox('getValue'));
        }
    });

    //加载产线下拉框
    function refreshLine(){
        $.ajax({
            type: "POST",
            url: '/json/MoldUseHistoryRecords/LineList',
            dataType: "json",
            success: function(json) {
                $('#lineName').combobox({
                    data: json,
                    valueField: 'lineId',
                    textField: 'lineName'
                });
            }
        });
    }
    //加载设备下拉框
    function refreshEqpt(lineId){
        //var data = {'lineIdData':lineId};
        $.ajax({
            type: "POST",
            url: '/json/MoldUseHistoryRecords/EquipmentList',
            dataType: "json",
            data:{'lineIdData':lineId},
            success: function(json) {
                $('#equipmentId').combobox({
                    data: json,
                    valueField: 'equipmentId',
                    textField: 'equipmentName'
                });
            }
        });
    }

//    $("#lineName").combobox({
//        url :"/json/MoldUseHistoryRecords/LineList",
//        valueField : "lineId",
//        textField : "lineName",
//        method : "post"
//    });

    //渲染入库历史记录表格
    $('#MoldUseHistoryTable').datagrid({
        url:'/json/MoldUseHistoryRecords/MoldUseHistoryRecordsListPage',
        idField:'moldName',
        columns:[[
            {
                field:'moldName',
                title:'模具名称',
                width:200,
                align:'center'
            },
            {
                field:'lineName',
                title:'所在产线',
                width:200,
                align:'center'
            },
            {
                field:'equipmentName',
                title:'所属设备',
                width:200,
                align:'center'
            },
            {
                field:'produceNumber',
                title:'生产批次',
                width:200,
                align:'center'
            },
            {
                field:'workNumber',
                title:'工单号',
                width:180,
                align:'center'
            },
            {
                field:'empName',
                title:'操作人',
                width:200,
                align:'center'
            },
            {
                field:'createTime',
                title:'操作时间',
                width:200,
                formatter:timestampToString,
                align:'center'
            },
            {
                field:'remark',
                title:'备注',
                width:200,
                align:'center'
            }
        ]]
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
//查询按钮点击事件
$("#queryButton").linkbutton({
    onClick : function() {
        $("#MoldUseHistoryTable").datagrid("load", {
            lineId : $("#lineName").combobox('getValue'),
            equipmentId : $("#equipmentId").combobox('getValue')
        });
    }
});