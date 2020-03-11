$(function () {

    // 产品下拉列表
    $("#searchGoods").combobox({
        url : '/json/Goods/getGoodsByQ',
        editable : true,
        method : 'get',
        mode: 'remote',
        valueField : 'goodsId',
        textField : 'goodsName',
        onSelect: function (ret) {

            let goodsId = ret.goodsId;

            // 工单
            $("#searchProduceOrder").combobox('setValue',null);
            $("#searchProduceOrder").combobox({
                url : '/json/produceOrder/getProduceOrderByGoodsId?goodsId=' + goodsId,
                editable : true,
                method : 'get',
                mode : 'remote',
                valueField : 'produceId',
                textField : 'workNumber'
            });

            // 工序
            $("#searchOrderDetail").combobox('setValue',null);
            let url = '/json/OrderDetail/getOrderDetailByGoodsId?goodsId=' + goodsId;
            $("#searchOrderDetail").combobox({
                url : url,
                editable : true,
                method : 'get',
                valueField : 'orderId',
                textField : 'orderName',
                filter: function (q,row) {
                    let opts = $(this).combobox('options');
                    return row[opts.textField].indexOf(q) > -1;
                },
                onSelect: function (result) {
                    // 设备
                    $("#searchEquipment").combobox('setValue',null);
                    $("#searchEquipment").combobox({
                        url : '/json/equipment/getEquipmentByOrderId?orderId=' + result.orderId,
                        editable : true,
                        method : 'get',
                        valueField : 'equipmentId',
                        textField : 'equipmentName',
                        filter: function (q,row) {
                            let opts = $(this).combobox('options');
                            return row[opts.textField].indexOf(q) > -1;
                        }
                    });

                }
            });
        }
    });

});

let column = [];

// 搜索按钮事件
function searchData() {
    let workNumber = $('#searchProduceOrder').combobox('getText');
    let equipmentId = $("#searchEquipment").combobox('getValue');

    let path = '';

    switch (equipmentId) {
        case '2': path = "/getEquipmentSpi"; column = equipment_spi_column; break;
        case '3': path = "/getEquipmentTpjHeadS"; column = equipment_tpj_head_s_column; break;
        case '7': path = "/getEquipmentAoi"; column = aoi_column; break;
        case '8': path = "/getEquipmentIct"; column = equipment_ict_column; break;
        case '9': path = "/getEquipmentFct"; column = equipment_fct_column; break;
    }

    /*let opts = $("#tb1").datagrid("options");
    opts.url = "/json/qualityTrace" + path;
    opts.columns = column;
    opts.queryParams = {workNumber:workNumber};

    console.log(path);
    console.log(column);
    console.log(opts);
    $("#tb1").datagrid("load");*/

    if (workNumber == null || path == '') {
        return;
    }

    $('#gridDiv').css('display','block');

    $("#tb1").datagrid({
        url : '/json/qualityTrace' + path,
        method : 'get',
        remoteSort:false,
        fitColumns : true,
        queryParams: {workNumber:workNumber},
        columns: column
    });


}


// equipment_aoi        7
let aoi_column = [[
    {
        field:'testModel',
        width:200,
        align:'center',
        title:'测试型号'
    },
    {
        field:'completionTime',
        width:200,
        align:'center',
        title:'测试完成时间'
    },
    {
        field:'startRetrialTime',
        width:200,
        align:'center',
        title:'开始复判时间'
    },
    {
        field:'endRetrialTime',
        width:200,
        align:'center',
        title:'结束复判时间'
    },
    {
        field:'pointsTotal',
        width:200,
        align:'center',
        title:'测试总点数'
    },
    {
        field:'wrongPointsTotal',
        width:200,
        align:'center',
        title:'不良品总点数'
    },
    {
        field:'workstation',
        width:200,
        align:'center',
        title:'测试工作站'
    },
    {
        field:'operator',
        width:200,
        align:'center',
        title:'操作员'
    },
    {
        field:'pcb',
        width:200,
        align:'center',
        title:'PCB面别'
    },
]];

// equipment_aoi_wrong_detail
let aoi_wrong_detail_column = [[
    {
        field:'wrongPoint',
        width:200,
        align:'center',
        title:'不良点位&拼版号'
    },
    {
        field:'typeName',
        width:200,
        align:'center',
        title:'不良类型'
    },
    {
        field:'xCoordinate',
        width:200,
        align:'center',
        title:'X 坐标'
    },
    {
        field:'yCoordinate',
        width:200,
        align:'center',
        title:'Y 坐标'
    },
    {
        field:'angle',
        width:200,
        align:'center',
        title:'角度'
    },
]];

// equipment_fct         9
let equipment_fct_column = [[
    {
        field:'uutBarcode',
        width:200,
        align:'center',
        title:'UUT'
    },
    {
        field:'testDate',
        width:200,
        align:'center',
        hidden: true,
        title:'测试日期'
    },
    {
        field:'testTime',
        width:200,
        align:'center',
        title:'测试时间',
        formatter: function (value, rowData, rowIndex){
            return rowData.testDate + ' ' + rowData.testTime;
        }

    },
    {
        field:'state',
        width:200,
        align:'center',
        title:'是否通过',
        formatter:function (value, rowData, rowIndex) {
            let str = rowData.state;
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
    }
]];

// equipment_ict          8
let equipment_ict_column = [[
    {
        field:'ictName',
        width:200,
        align:'center',
        title:'程式名称'
    },
    {
        field:'userId',
        width:200,
        align:'center',
        title:'用户ID'
    },
    {
        field:'boardName',
        width:200,
        align:'center',
        title:'板子名称'
    },
    {
        field:'ver',
        width:200,
        align:'center',
        title:'版本'
    },
    {
        field:'testDate',
        width:200,
        align:'center',
        title:'测试日期'
    },
    {
        field:'sN',
        width:200,
        align:'center',
        title:'S/N'
    },
    {
        field:'totalPanes',
        width:200,
        align:'center',
        title:'测试板数'
    },
    {
        field:'totalResult',
        width:200,
        align:'center',
        title:'测试结果'
    },
]];

// equipment_spi          2
let equipment_spi_column = [[
    {
        field:'modelName',
        width:200,
        align:'center',
        title:'程式名称'
    },
    {
        field:'lineNumber',
        width:200,
        align:'center',
        title:'线别'
    },
    {
        field:'boardStatus',
        width:200,
        align:'center',
        title:'板子的测试结果'
    },
    {
        field:'boardBarcode',
        width:200,
        align:'center',
        title:'整板条码'
    }
]];

// equipment_tpj_head_s    3
let equipment_tpj_head_s_column = [[
    {
        field:'gantryId',
        width:200,
        align:'center',
        title:'悬臂ID'
    },
    {
        field:'headId',
        width:200,
        align:'center',
        title:'悬臂头编号'
    },
    {
        field:'pickUp',
        width:200,
        align:'center',
        title:'吸料数量'
    },
    {
        field:'place',
        width:200,
        align:'center',
        title:'贴装数量'
    },
    {
        field:'pickError',
        width:200,
        align:'center',
        title:'吸料错误数量'
    },
    {
        field:'visionError',
        width:200,
        align:'center',
        title:'检测出错数量'
    },
    {
        field:'dump',
        width:200,
        align:'center',
        title:'抛料数'
    }
]];


















