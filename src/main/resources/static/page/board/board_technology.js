let material;
let equip;
let product;
let equipException;
let inWorkNum;
let interval = null; // 定时器


    $(function () {
        material = echarts.init(document.getElementById("material"));
        equip = echarts.init(document.getElementById("equip"));
        product = echarts.init(document.getElementById("product"));
        equipException = echarts.init(document.getElementById("equipException"));
        inWorkNum = echarts.init(document.getElementById("inWorkNum"));
        loadEcharts();

         start();

        //浏览器缩放时重设图形大小
        setTimeout(function (){
            window.onresize = function () {

                if (typeof (material) != 'undefined') {
                    material.resize();
                }

                if (typeof (equip) != 'undefined') {
                    equip.resize();
                }

                if (typeof (product) != 'undefined') {
                    product.resize();
                }

                if (typeof (equipException) != 'undefined') {
                    equipException.resize();
                }

                if (typeof (inWorkNum) != 'undefined') {
                    inWorkNum.resize();
                }
            }
        },500);

    });

    $("#fullscreen").click(function(){
    	  $(".main-header").toggle();
    	  $(".main-sidebar").toggle();
    	  $(".content-header").toggle();
    	  $(".main-footer").toggle();
    	  $(".content-wrapper").toggleClass("sidebar-hide");

        material.resize();
        equip.resize();
        product.resize();
        equipException.resize();
        inWorkNum.resize();
    	});


function start(){//启动计时器函数
    if(interval!=null){//判断计时器是否为空
        clearInterval(interval);
        interval=null;
    }
    interval = setInterval(loadEcharts,60000);//启动计时器，调用overs函数，
}


function loadEcharts() {

    // 当前工单信息
    $.ajax({
        url : '/board/getRunProduceOrder',
        async : false,
        success : function (ret) {

            if (ret == null || ret == undefined || ret == '') {
                return;
            }

            $("#orderNum").text(ret.workNumber);

            $("#orderHour").text(ret.planTime);

            $("#real_start_time").text(ret.realStartTime);

            $("#overHour").text(ret.overTime);

            currentWorkNumberDetail(ret.workNumber);
        }
    });

}

function currentWorkNumberDetail(produceNumber) {

    // 1. 原材料
    $.ajax({
        url : '/board/getCurrentGoodsMaterial',
        data : {'produceNumber' : produceNumber},
        success : function (ret) {
            // 物料名
            let materialName = [];
            // 物料数量
            let materialNum = [];
            // 物料种类数
            let sumNum = ret.length;

            for (let i = 0; i < ret.length; i++) {
                if (ret[i].materialName != null) {
                    materialName.push(ret[i].materialName);
                }

                if (ret[i].quantity != null) {
                    materialNum.push(ret[i].quantity);
                }
            }

            let material_option = {
                backgroundColor: '#0f375f',
                title:[{
                    text:'原材料(TOP10)',
                    textStyle: {
                        color:'#ffffff',
                        fontSize:"18",
                        fontWeight:"400",
                    },
                    // padding:[2,10]
                    left:'5%',
                    top:'5'
                },
                    {
                        text:sumNum,
                        textStyle: {
                            color:'#2fb7e7'
                        },
                        right:'5%',
                        top:'5'
                    }],
                grid: [
                    {top: '40', bottom: '10',left:'180',right:'50' },
                ],
                tooltip: {
                    formatter: '{b} ({c})'
                },
                xAxis: [
                    {gridIndex: 0, axisTick: {show:false},axisLabel: {show:false},splitLine: {show:false},axisLine: {show:false }},
                ],
                yAxis: [
                    {  gridIndex: 0, interval:0,data:materialName,
                        axisTick: {show:false},
                        axisLabel: {
                            interval:0,
                            show:true,
                            textStyle: {
                                color:'rgba(255,255,255,.8)'
                            },
                        },
                        splitLine: {show:false},
                        axisLine: {show:false,lineStyle:{color:"#6173a3"}
                        },
                    }
                ],
                series: [
                    {
                        name: '原材料',
                        type: 'bar',xAxisIndex: 0,yAxisIndex: 0,barWidth:10,
                        itemStyle:{normal:{color:'#86c9f4'}},
                        label:{normal:{show:true,position:"right", textStyle:{color:"rgba(255,255,255,.8)"}}},
                        data: materialNum,
                    }

                ]
            };
            material.setOption(material_option);
        }
    });

    // 2. 设备
    $.ajax({
        url : '/board/getEquipmentState',
        data : {"produceNumber":produceNumber},
        success : function (ret) {
            // 设备名称
            let equipName = [];
            // 设备状态名称
            let equipStateName = [];
            // 设备状态值
            let equipStateNum = [];
            // 柱形图的长度
            let data = [];
            for (let i = 0; i< ret.length; i++) {
                if (ret[i].equipmentName != null) {
                    equipName.push(ret[i].equipmentName);
                }
                if (ret[i].isStop != null) {
                    equipStateNum.push(ret[i].isStop);
                }
                data.push(5);
                if (ret[i].isStop === 0) {
                    equipStateName.push("停机");
                } else {
                    equipStateName.push("运行");
                }
            }

            let equip_option = {
                backgroundColor: '#0f375f',
                title:[{
                    text:'设备',
                    textStyle: {
                        fontWeight:400,
                        color:'#ffffff'
                    },
                    left:'5%',
                    top:'5'
                },
                    {
                        text:equipName.length,
                        textStyle: {
                            color:'#2fb7e7'
                        },
                        right:'5%',
                        top:'5'
                    }],
                grid: [
                    {top: '40', bottom: '10',left:'100',right:'50' },
                ],
                tooltip: {
                    formatter: '{b} ({c})'
                },
                xAxis: [
                    {gridIndex: 0, axisTick: {show:false},axisLabel: {show:false},splitLine: {show:false},axisLine: {show:false }},
                ],
                yAxis: [
                    {  gridIndex: 0, interval:0,data:equipName,
                        axisTick: {show:false}, axisLabel: {show:true,
                            interval:0,
                            textStyle: {
                                fontFamily : "微软雅黑",
                                fontSize : 12,
                                color: 'rgba(255,255,255,.8)'

                            }

                        },splitLine: {show:false},
                        axisLine: {show:false,lineStyle:{color:"#6173a3"}
                        },
                    }
                ],
                series: [
                    {
                        name: '设备运行状态',
                        type: 'bar',xAxisIndex: 0,yAxisIndex: 0,barWidth:'10',
                        itemStyle: {
                            normal: {
                                color: function(params) {
                                    if(equipStateNum[params.dataIndex] == 1){
                                        return "#35b34a";
                                    }else {
                                        return "#ff4848";
                                    }
                                }
                            }
                        },
                        label:{
                            normal: {
                                formatter: function(param) {
                                    return equipStateName[param.dataIndex];
                                },

                                show: true,
                                position: 'right',
                                textStyle: {
                                    fontFamily : "微软雅黑",
                                    fontSize : 12,
                                    color: 'rgba(255,255,255,.8)'

                                }
                            }},
                        data: data,
                    }

                ]
            };
            equip.setOption(equip_option);
        }
    });

    // 3. 产品
    $.ajax({
        url : '/board/getGoodsCount',
        data : {'produceNumber' : produceNumber},
        success : function (ret) {
            // 坐标名称
            let name = ['已生产','未生产','不合格','维修数'];
            // 数量
            let num = [ret.completeCount,ret.unCompleteCount,ret.unQualifiedCount,ret.repairCount];

            let product_option = {
                backgroundColor: '#0f375f',
                title:[{
                    text:'产品',
                    textStyle: {
                        fontWeight:400,
                        color:'#ffffff'
                    },
                    left:'5%',
                    top:'5'
                },
                    {
                        text:ret.planAmount,
                        textStyle: {
                            color:'#2fb7e7'
                        },
                        right:'5%',
                        top:'5'
                    }],
                grid: [
                    {top: '40', bottom: '10',left:'60',right:'50' },
                ],
                tooltip: {
                    formatter: '{b} ({c})'
                },
                xAxis: [
                    {gridIndex: 0, axisTick: {show:false},axisLabel: {show:false},splitLine: {show:false},axisLine: {show:false }},
                ],
                yAxis: [
                    {  gridIndex: 0, interval:0,data:name,
                        axisTick: {show:false},
                        axisLabel: {show:true,
                            interval:0,
                            textStyle: {
                                fontFamily : "微软雅黑",
                                fontSize : 12,
                                color: 'rgba(255,255,255,.8)'

                            }

                        },
                        splitLine: {show:false},
                        axisLine: {show:false,lineStyle:{color:"#6173a3"}
                        },
                    }
                ],
                series: [
                    {
                        name: '产品',
                        type: 'bar',xAxisIndex: 0,yAxisIndex: 0,barWidth:'10',
                        itemStyle:{normal:{color:'#86c9f4'}},
                        label:{normal:{show:true,position:"right", textStyle:{fontSize : 12,
                                    color: 'rgba(255,255,255,.8)'}}},
                        data: num,
                    }

                ]
            };
            product.setOption(product_option);
        }
    });

    // 4. 设备异常
    $.ajax({
        url : '/board/getEquipmentAbnormal',
        data : {"produceNumber":produceNumber},
        success : function (ret) {
            // 设备名称
            let equipName = [];
            // 异常次数
            let amount = [];
            // 相同长度的柱形图
            let data = [];
            // 所有设备异常总次数
            let sumAmount = 0;
            for (let i = 0; i< ret.length; i++) {
                equipName.push(ret[i].equipmentName);
                amount.push(ret[i].amount);
                data.push(5);
                sumAmount += ret[i].amount;
            }


            let equipException_option = {
                backgroundColor: '#0f375f',
                title:[{
                    text:'设备异常',
                    textStyle: {
                        fontWeight:400,
                        color:'#ffffff'

                    },
                    left:'5%',
                    top:'5'
                },
                    {
                        text:sumAmount,
                        textStyle: {
                            color:'#2fb7e7'
                        },
                        right:'5%',
                        top:'5'
                    }],
                grid: [
                    {top: '40', bottom: '10',left:'100',right:'30' },
                ],
                tooltip: {
                    formatter: '{b} ({c})'
                },
                xAxis: [
                    {gridIndex: 0, axisTick: {show:false},axisLabel: {show:false},splitLine: {show:false},axisLine: {show:false }},
                ],
                yAxis: [
                    {  gridIndex: 0, interval:0,data:equipName,
                        axisTick: {show:false},  axisLabel: {show:true,
                            interval:0,
                            textStyle: {
                                fontFamily : "微软雅黑",
                                fontSize : 12,
                                color: 'rgba(255,255,255,.8)'

                            }

                        },splitLine: {show:false},
                        axisLine: {show:false,lineStyle:{color:"#6173a3"}
                        },
                    }
                ],
                series: [
                    {
                        name: '设备运行异常次数',
                        type: 'bar',xAxisIndex: 0,yAxisIndex: 0,barWidth:'10',
                        itemStyle: {
                            normal: {
                                color: function(params) {
                                    if(amount[params.dataIndex] == 0){
                                        return "#35b34a";
                                    }else if (amount[params.dataIndex] < 5) {
                                        return "#ca9135";
                                    } else {
                                        return "#ff4848";
                                    }
                                }
                            }
                        },
                        label:{
                            normal: {
                                formatter: function(param) {
                                    return amount[param.dataIndex];
                                },

                                show: true,
                                position: 'right',
                                textStyle: {
                                    fontSize : 12,
                                    color: 'rgba(255,255,255,.8)'

                                }
                            }},
                        data: data,
                    }

                ]
            };
            equipException.setOption(equipException_option);

        }
    });

    // 5. 在岗人数
    $.ajax({
        url : '/board/getOrderEmpNum',
        data : {'produceNumber':produceNumber},
        success : function (ret) {
            // 工序名称
            let orderName = [];
            // 工序在线人数
            let orderEmpNum = [];
            // 工序在线总人数
            let sumEmp;

            for (let i = 0; i< ret.length; i++) {
                orderName.push(ret[i].orderName);
                orderEmpNum.push(ret[i].amount);
                sumEmp = ret[i].empSumNum;
            }


            let inWorkNum_option = {
                backgroundColor: '#0f375f',
                title:[{
                    text:'在岗人数',
                    textStyle: {
                        color:'#ffffff',
                        fontWeight:400,
                    },
                    left:'5%',
                    top:'5'
                },
                    {
                        text:sumEmp,
                        textStyle: {
                            color:'#2fb7e7'
                        },
                        right:'5%',
                        top:'5'
                    }],
                grid: [
                    {top: '40', bottom: '10',left:'60',right:'30' },
                ],
                tooltip: {
                    formatter: '{b} ({c})'
                },
                xAxis: [
                    {gridIndex: 0, axisTick: {show:false},axisLabel: {show:false},splitLine: {show:false},axisLine: {show:false }},
                ],
                yAxis: [
                    {  gridIndex: 0, interval:0,data:orderName,
                        axisTick: {show:false},  axisLabel: {show:true,
                            interval:0,
                            textStyle: {
                                fontFamily : "微软雅黑",
                                fontSize : 12,
                                color: 'rgba(255,255,255,.8)'

                            }

                        },splitLine: {show:false},
                        axisLine: {show:false,lineStyle:{color:"#6173a3"}
                        },
                    }
                ],
                series: [
                    {
                        name: '在岗人数',
                        type: 'bar',xAxisIndex: 0,yAxisIndex: 0,barWidth:'10',
                        itemStyle:{normal:{color:'#86c9f4'}},
                        label:{normal:{show:true,position:"right", textStyle:{fontSize : 12,
                                    color: 'rgba(255,255,255,.8)'}}},
                        data: orderEmpNum,
                    }

                ]
            };
            inWorkNum.setOption(inWorkNum_option);
        }
    });

    spi(produceNumber);
    tpj(produceNumber);
    aoi(produceNumber);
    ict(produceNumber);
    fct(produceNumber);
}

function spi(workNumber) {
    $.ajax({
        url : '/json/boardTechnology/getSpiSum',
        data: {workNumber : workNumber},
        success: function (ret) {
            $('#spi-modelName').text(ret.modelName);
            $('#spi-sumAmount').text(ret.sumAmount);
            $('#spi-passAmount').text(ret.passAmount);
        }
    })
}

function tpj(workNumber) {
    $.ajax({
        url : '/json/boardTechnology/getTpjSum',
        data: {workNumber : workNumber},
        success: function (ret) {
            $('#tpj-pickUpSum').text(ret.pickUpSum);
            $('#tpj-placeSum').text(ret.placeSum);
            $('#tpj-pickErrorSum').text(ret.pickErrorSum);
            $('#tpj-visionErrorSum').text(ret.visionErrorSum);
            $('#tpj-dumpSum').text(ret.dumpSum);
        }
    })
}

function aoi(workNumber) {
    $.ajax({
        url : '/json/boardTechnology/getAoiSum',
        data: {workNumber : workNumber},
        success: function (ret) {
            $('#aoi-rowNum').text(ret.rowNum);
            $('#aoi-pointsTotalSum').text(ret.pointsTotalSum);
            $('#aoi-wrongPointsTotalSum').text(ret.wrongPointsTotalSum);
        }
    })
}

function ict(workNumber) {
    $.ajax({
        url : '/json/boardTechnology/getIctSum',
        data: {workNumber : workNumber},
        success: function (ret) {
            $('#ict-rowNum').text(ret.rowNum);
            $('#ict-sumAmount').text(ret.sumAmount);
            $('#ict-qualifiedAmount').text(ret.qualifiedAmount);
        }
    })
}

function fct(workNumber) {
    $.ajax({
        url : '/json/boardTechnology/getFctSum',
        data: {workNumber : workNumber},
        success: function (ret) {
            $('#fct-rowNum').text(ret.rowNum);
            $('#fct-qualifiedSum').text(ret.qualifiedSum);
        }
    })
}
