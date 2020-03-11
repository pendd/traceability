
$(function () {

    $('#eqptName').combobox(
            {
                url : "/json/equipment/equipmentCombobox",
                method : 'post',
                valueField : 'eqId',
                textField : 'eqptName',

     });
    $('#eqptName').combobox('select',0);

    $("#personnelOperationLog").datagrid({
        url: "/json/EqptState/eqptStateList?language="+lang.language,
        loadFilter:function(data){
            console.log(data)
            return data;
        },
        idfield: "id",
            columns : [[{
                 field : 'eqptName',
                 title : '设备',
                 width : 130,
                 algin : 'center'
            },{
                field : 'eqptModel',
                title : '设备型号',
                width : 130,
                algin : 'center'
           },{
                field : 'cEqptState',
                title : '设备状态',
                width : 130,
                algin : 'center'
            },{
                field : 'cEqptStateEn',
                title : 'Eqpt state',
                width : 100,
                algin : 'center'

            }
            ,{
                field : 'beginTime',
                title : '开始时间',
                width : 150,
                algin : 'center',
                formatter:function(value,row,index){
                    var unixTimestamp = new Date(value);
                    return formatDate(unixTimestamp);
                    }
             },
             {
                 field : 'endTime',
                 title : '结束时间',
                 width : 150,
                 algin : 'center',
                 formatter:function(value,row,index){
                     var unixTimestamp = new Date(value);
                     return formatDate(unixTimestamp);
                     }
              }
             ]],
             onLoadSuccess:function(data){
                var language=lang.language;
                    if(language=='zh')
                    {
                       $("#personnelOperationLog").datagrid("hideColumn", "cEqptStateEn");
                       $('#personnelOperationLog').datagrid('showColumn', 'cEqptState');

                    }
                    else
                    {
                        $("#personnelOperationLog").datagrid("hideColumn", "cEqptState");
                        $('#personnelOperationLog').datagrid('showColumn', 'cEqptStateEn');

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

        eqptId:$("#eqptName").combobox('getValue'),
        beginTime:$("#beginTimebox").datetimebox('getValue'),
        endTime:$("#endTimebox").datetimebox('getValue')
    })
}