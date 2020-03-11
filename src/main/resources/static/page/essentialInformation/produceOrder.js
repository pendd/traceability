$(function(){

    $('#orderTable').datagrid({
        url : '/json/produceOrder/getAllProduceOrder',
        fitColumns : true,
        idField : 'produceId',
        method: 'get',
        remoteSort : false,
        columns:[[
            {
                field:'workNumber',
                title:'生产工单号',
                width:200,
                align:'center',
                sortable : true,
                sorter: function (a,b) {
                    return a > b ? 1 : -1;
                }
            },
            {
                field:'goodsName',
                title:'产品名称',
                width:150,
                align:'center',
                sortable : true,
                sorter: function (a,b) {
                    return a > b ? 1 : -1;
                }
            },
            {
                field:'lineName',
                title:'产线名称',
                width:150,
                align:'center',
                sortable : true,
                sorter: function (a,b) {
                    return a > b ? 1 : -1;
                }
            },
            {
                field:'planStartTime',
                title:'计划开始时间',
                width:200,
                align:'center',
                sortable : true,
                sorter: function (a,b) {
                    return a > b ? 1 : -1;
                }
            },
            {
                field:'planEndTime',
                title:'计划结束时间',
                width:200,
                align:'center',
                sortable : true,
                sorter: function (a,b) {
                    return a > b ? 1 : -1;
                }
            },
            {
                field:'realStartTime',
                title:'实际开始时间',
                width:200,
                align:'center',
                sortable : true,
                sorter: function (a,b) {
                    return a > b ? 1 : -1;
                }
            },
            {
                field:'realEndTime',
                title:'实际结束时间',
                width:200,
                align:'center',
                sortable : true,
                sorter: function (a,b) {
                    return a > b ? 1 : -1;
                }
            },
            {
                field:'planAmount',
                title:'计划数量',
                width:200,
                align:'center'
            },
            {
                field:'status',
                title:'状态',
                width:100,
                align:'center',
                formatter:function (value,rowData,rowIndex) {
                    switch (rowData.status) {
                        case 0 :
                            value = '配料';
                            break;
                        case 1 :
                            value = '可运行';
                            break;
                        case 2 :
                            value = '正在运行';
                            break;
                        case 3 :
                            value = '暂停';
                            break;
                        case 4 :
                        case 5 :
                            value = '已完成';
                    }
                    return value;
                }
            }
        ]]
    });


});

function searchData() {
    let goodsName = $("#searchGoodsName").textbox('getValue');
    let realStartTime = $('#startTime').datebox('getValue');
    let realEndTime = $('#endTime').datebox('getValue');
    console.log(realStartTime);
    console.log(realEndTime);
    $("#orderTable").datagrid('load',{
        goodsName : goodsName,
        realStartTime: realStartTime,
        realEndTime: realEndTime
    });
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
    let startTime = $("#startTime").datebox('getValue');
    let endTime = $("#endTime").datebox('getValue');

    if(startTime !== '' && endTime !== '') {
        if(startTime > endTime) {
            $.messager.alert(lang.getText("promptNotice_prompt"), lang.getText("base_compareTime"), "info");
            $("#endTime").datebox('setValue','');
            return;
        }
    }
}

