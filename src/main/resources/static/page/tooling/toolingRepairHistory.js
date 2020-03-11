$(function () {

    // grid初始化
    $("#tbl").datagrid({
        url : "/json/toolingReply/getAllToolingReply",
        idField : "replyId",
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
                field:'replyId',
                width:200,
                align:'center',
                title:'主键',
                hidden:true
            },
            {
                field:'toolingName',
                width:200,
                align:'center',
                title:'工装名'
            },
            {
                field:'createTime',
                width:200,
                align:'center',
                title:'通知时间'
            },
            {
                field:'replyTime',
                width:200,
                align:'center',
                title:'反馈时间'
            },
            {
                field:'repairDetail',
                width:200,
                align:'center',
                title:'维保明细'
            },
            {
                field:'repairState',
                width:200,
                align:'center',
                title:'维保状态',
                formatter:function (value,row) {
                    let state = row.repairState;
                    switch (state) {
                        case 0:
                            value = "未维保";
                            break;
                        case 1:
                            value = "已维保";
                            break;
                    }
                    return value;
                }
            },
            {
                field:'taskUserName',
                width:200,
                align:'center',
                title:'任务人'
            },
            {
                field:'principleName',
                width:200,
                align:'center',
                title:'责任人'
            }
        ]]
    });

});

// 搜索按钮事件
function searchData() {
    let searchToolingName = $("#searchToolingName").val();
    let searchTaskUserName = $("#searchTaskUserName").val();
    let searchPrincipleName = $("#searchPrincipleName").val();
    $("#tbl").datagrid('load', {
        toolingName: searchToolingName,
        taskUserName: searchTaskUserName,
        principleName: searchPrincipleName
    });
}
