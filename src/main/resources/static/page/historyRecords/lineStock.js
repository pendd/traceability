$(function(){
    refreshLineStoreroom();
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

    //加载线边库下拉框
    function refreshLineStoreroom(){
        $.ajax({
            type: "POST",
            url: '/json/Storeroom/getStoreroomByType',
            data:{type:"2"},   // 2 表示线边库
            dataType: "json",
            async:false,
            success: function(json) {
                $('#queryInlineMaterialId').combobox({
                    data: json,
                    valueField: 'storeroomId',
                    textField: 'storeroomName'
                });
                $('#queryOutlineMaterialId').combobox({
                    data: json,
                    valueField: 'storeroomId',
                    textField: 'storeroomName'
                });
            }
        });
    }

    //渲染入库历史记录表格
    $('#lineMaterialInHistoryTable').datagrid({
        url:'/json/LineStoreroomHistory/lineStoreroomInHistoryListPage',
        sortName:'createTime',
        sortOrder:'desc',
        columns:[[
            {
                field:'materialCode',
                title:'原料编号',
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
                field:'teamName',
                title:'班组',
                width:150,
                align:'center'
            },
            {
                field:'StoreroomName',
                title:'线边库',
                width:150,
                align:'center'
            },
            {
                field:'createTime',
                title:'创建时间',
                width:200,
                formatter:timestampToString,
                align:'center'
            },
            {
                field:'empName',
                title:'操作员',
                width:200,
                align:'center'
            }
        ]]
    });
    $("#queryInlineMaterialButton").linkbutton({
        onClick : function() {
            $("#lineMaterialInHistoryTable").datagrid("load", {
                lineStoreroomId : $("#queryInlineMaterialId").textbox("getValue")
            });
        }
    });
    //渲染出库历史记录表格
    $('#lineMaterialOutHistoryTable').datagrid({
        url:'/json/LineStoreroomHistory/lineStoreroomOutHistoryListPage',
        columns:[[
            {
                field:'materialCode',
                title:'原料编号',
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
                field:'teamName',
                title:'班组',
                width:150,
                align:'center'
            },
            {
                field:'lineStoreroomName',
                title:'线边库',
                width:150,
                align:'center'
            },
            {
                field:'storeroomName',
                title:'来源库',
                width:150,
                align:'center'
            },
            {
                field:'createTime',
                title:'创建时间',
                width:200,
                formatter:timestampToString,
                align:'center'
            },
            {
                field:'empName',
                title:'操作员',
                width:200,
                align:'center'
            },
            {
                field:'isArrive',
                title:'是否到达',
                width:150,
                align:'center',
                formatter:function (value, rowData, rowIndex) {
                    var str = rowData.isArrive;
                    switch (str) {
                        case 0:
                            value = "未到达";
                            break;
                        case 1:
                            value = "已到达";
                            break;
                    }
                    return value;
                }
            }
        ]]
    });
    $("#queryOutlineMaterialButton").linkbutton({
        onClick : function() {
            $("#lineMaterialOutHistoryTable").datagrid("load", {
                lineStoreroomId : $("#queryOutlineMaterialId").textbox("getValue")
            });
        }
    });

})
