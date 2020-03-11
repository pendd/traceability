
$(function () {

    $("#personnelOperationLog").datagrid({
        url: "/json/OperationLog/operationLogpage?language="+lang.language,
        loadFilter:function(data){
            console.log(data)
            return data;
        },
        idfield: "id",
            columns : [[{
                 field : 'menuNameZh',
                 title : '操作菜单',
                 width : 130,
                 algin : 'center'
            },{
                field : 'menuNameEh',
                title : '操作菜单',
                width : 130,
                algin : 'center'
           },{
                field : 'empName',
                title : '操作人',
                width : 130,
                algin : 'center'
            },{
                field : 'operType',
                title : '操作类型',
                width : 100,
                algin : 'center',
                formatter : function(value,row,index){
                    if(lang.language='zh')
                    {
                        if(value=='0'){
                             return '添加'
                             }
                          if(value=='1'){
                             return '修改'
                             }
                          if(value=='2'){
                              return '删除'
                          }
                          if(value=='3'){
                              return '查看'
                          }
                          if(value=='4'){
                              return '修改密码'
                          }
                    }
                    else
                        {
                        if(value=='0'){
                             return 'Add'
                             }
                          if(value=='1'){
                             return 'Edit'
                             }
                          if(value=='2'){
                              return 'Remove'
                          }
                          if(value=='3'){
                              return 'Check'
                          }
                          if(value=='4'){
                              return 'Password'
                          }

                        }

                  }
            },{
                field : 'operContent',
                title : '操作内容',
                width : 350,
                algin : 'center'
            },{
                field : 'operContentEn',
                title : '操作内容',
                width : 350,
                algin : 'center'
            }
            ,{
                field : 'operTime',
                title : '操作时间',
                width : 150,
                algin : 'center',
                formatter:function(value,row,index){
                    var unixTimestamp = new Date(value);
                    return formatDate(unixTimestamp);
                    }
             }]],
             onLoadSuccess:function(data){
                var language=lang.language;
                    if(language=='zh')
                    {
                        $("#personnelOperationLog").datagrid("hideColumn", "menuNameEh");
                        $("#personnelOperationLog").datagrid("hideColumn", "operContentEn");
                        $('#personnelOperationLog').datagrid('showColumn', 'menuNameZh');
                        $('#personnelOperationLog').datagrid('showColumn', 'operContent');
                    }
                    else
                    {
                        $("#personnelOperationLog").datagrid("hideColumn", "menuNameZh");
                        $("#personnelOperationLog").datagrid("hideColumn", "operContent");
                        $('#personnelOperationLog').datagrid('showColumn', 'menuNameEh');
                        $('#personnelOperationLog').datagrid('showColumn', 'operContentEn')

                    }

             }


    });

});

function formatTen(num) {
    return num > 9 ? (num + "") : ("0" + num);
    }
function formatDate(date) {
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var day = date.getDate();
    var hour = date.getHours();
    var minute = date.getMinutes();
    var second = date.getSeconds();
    return year + "-" + formatTen(month) + "-" + formatTen(day);
    }
//模糊查询
function like(){
    $("#personnelOperationLog").datagrid('load',{
        menuName:$("#txtFaultName").val(),
        empName:$("#txtEquipmentNo").val()
    })
}