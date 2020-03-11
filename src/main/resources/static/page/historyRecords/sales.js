$(function(){
    refreshLine();
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
            $("#SalesHistoryTable").datagrid("load", {
                companyId : $("#companyId").combobox('getValue')
            });
        }
    });
    //加载经销商下拉框
    function refreshLine(){
        $.ajax({
            type: "POST",
            url: '/json/salesCompany/getSalesCompanyCombobox',
            dataType: "json",
            success: function(json) {
                $('#companyId').combobox({
                    data: json,
                    valueField: 'comId',
                    textField: 'comName'
                });
            }
        });
    }

    //渲染入库历史记录表格
    $('#SalesHistoryTable').datagrid({
        url:'/json/SalesHistoryRecords/salesHistoryRecordsListPage',
        idField:'code',
        columns:[[
            {
                field:'code',
                title:'产品编码',
                width:200,
                align:'center'
            },
            {
                field:'goodsName',
                title:'产品名称',
                width:200,
                align:'center'
            },
            {
                field:'unitName',
                title:'单位',
                width:200,
                align:'center'
            },
            {
                field:'comName',
                title:'公司名称',
                width:200,
                align:'center'
            },
            {
                field:'salesName',
                title:'销售名称',
                width:200,
                align:'center'
            },
            {
                field:'address',
                title:'地址',
                width:180,
                align:'center'
            },
            {
                field:'principal',
                title:'负责人',
                width:200,
                align:'center'
            },
            {
                field:'telephone',
                title:'电话',
                width:200,
                align:'center'
            },
            {
                field:'createTime',
                title:'操作时间',
                width:200,
                formatter:timestampToString,
                align:'center'
            }
        ]]
    });
});
