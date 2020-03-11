$(function() {
        var chartOperatingTime = null; //时间对比饼状图
        var chartMaterialCompare = null; //物料消耗
        var chartProductionCompare = null; //产量对比
        var chartStopchart = null; //停机
        var chartEnergy = null; //机械效率图表对象
        var chartRateProduce = null; //生产效率图表对象

      //初始化显示
       // loadData();
      //加载数据


        function loadData() {
            $.ajax({
                url : "/dashboard/produce/dashboard_data",
                cache:false,
                async:false,
                method : "get",
                success : function(json) {
                    var dashboardData = json.data;

                    //显示数据
                    showData(dashboardData);
                }
            });
        };


    function showData(data)
    {
        showStopChart(data.StopChart.data);
       showOperatingTimeChart(data.OperateTime.data);
        showMaterialChart(data.EqpAll);
        if(data.EqpAll.gz.data.quantity!=undefined)
        {
            showProductionChart(data.EqpAll.gz.data.quantity);
        }
        if(data.EqpAll.cp.data.energy!=undefined)
        {
            showEnergy(data.EqpAll.cp.data.energy);
        }

        showInfromationList(data.EqpAll);
    }
    //时间对比饼状图
    function showOperatingTimeChart(operatingTimeArray) {

        //构建数据数组
        var runningTime = null; //运行时间
        var stopTime = null; //停机时间
        var faultTime = null; //故障时间
        runningTime=operatingTimeArray.runTime;
        stopTime=operatingTimeArray.stopTime;
        faultTime=operatingTimeArray.faultTime;


        //图表配置项
        var option = {
                color: ["#30c174", "#ff4000", "#fcc316", ],

                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                legend: {

                    bottom: 0,
                    itemWidth: 10,
                    itemHeight: 10,

                    textStyle: {
                        color: '#333',
                        fontSize: 12
                    },
                icon:"diamond",
                    data: ['运行', '停止', '故障', ]
                },
                grid:{
                    left:0,
                    right:0,
                    bottom:0,
                    top:0,
                },
                calculable: true,
                series: [{
                        name: 'Operating Time',
                        type: 'pie',
                        radius: ['17%', '62%'],
                        center: ['50%', '50%'],
                        roseType: 'radius',
                        label: {
                            normal: {
                                show: false,
                                formatter:"{b}\n{c}h",
                            },
                            emphasis: {
                                show: false
                            }
                        },
                        lableLine: {
                            normal: {
                                show: false
                            },
                            emphasis: {
                                show: false
                            }
                        },
                        data: [{
                                value: runningTime,
                                name: '运行',
                                "itemStyle": {
                                    "normal": {
                                        shadowBlur: 30,
                                        shadowColor:"#30c174",

                                    }
                                },
                            },
                            {
                                value: stopTime,
                                name: '停止',
                                "itemStyle": {
                                    "normal": {
                                        shadowBlur: 30,
                                        shadowColor:"#ff4000",

                                    }
                                },
                            },
                            {
                                value:faultTime,
                                name: '故障',
                                "itemStyle": {
                                    "normal": {
                                        shadowBlur: 30,
                                        shadowColor:"#fcc316",

                                    }
                                },
                            },

                        ]
                    },
                    {
                        name: '内圈',
                        type: 'pie',
                        radius: ['11%', '14%'],
                        center: ['50%', '50%'],
                        hoverAnimation:false,
                        roseType: 'radius',
                        label: {
                            normal: {
                                show: false
                            },
                            emphasis: {
                                show: false
                            }
                        },
                        tooltip:{show:false},
                        lableLine: {
                            normal: {
                                show: false
                            },
                            emphasis: {
                                show: false
                            }
                        },
                        data: [{
                                value: 10,
                                "itemStyle": {
                                    "normal": {
                                        "color": "#bfd8f5",

                                    }
                                },
                                name: '内圈'
                            },


                        ]
                    },

                ]
            };

        //显示图表。
        if (chartOperatingTime) {
            chartOperatingTime.setOption(option, true);
        } else {
            chartOperatingTime = echarts.init(document.getElementById("chart_perating_time"));
            chartOperatingTime.setOption(option);
        }
    };

    //进度条物料
    function showMaterialChart(materialChart)
    {//data.EqpAll.data.inforList.bottleQuantity
        var finshedProduction = null; //运行时间
           var tbConsumption = null; //停机时间
           var bottleConsumption  = null; //故障时间
           if(materialChart.gz.data.inforList!=undefined)
           {
            finshedProduction=materialChart.gz.data.inforList.bottleQuantity;
           }
           else
            {
            finshedProduction=0
            }

           if(materialChart.tb.data.inforList!=undefined)
           {
            tbConsumption=materialChart.tb.data.inforList.bottleQuantity;
           }
           else
            {
            tbConsumption=0
            }

           if(materialChart.cp.data.inforList!=undefined)
           {
            bottleConsumption=materialChart.cp.data.inforList.bottleQuantity;
           }
           else
            {
            bottleConsumptions=0
            }


           var n = Math.max(finshedProduction,tbConsumption,bottleConsumption);
        //横向柱图
        var blueBar = [finshedProduction, tbConsumption, bottleConsumption, ];
        var grayBar = [n*1.3, n*1.3, n*1.3];
        var xuhao = [3, 2, 1];

        var items = ['灌装耗材', '贴标消耗', '瓶坯消耗'];
        var option = {
        title: {
            show: false
        },
        color: ['#6087ce'], //进度条颜色
        grid: {
            left: '5%', //如果离左边太远就用这个......
            right: '5%',
            bottom: '5%',
            top: '5%',
            containLabel: true
        },
        xAxis: [{
                show: false,
                max:'dataMax',
            },
            //由于下边X轴已经是百分比刻度了,所以需要在顶部加一个X轴,刻度是金额,也隐藏掉
            {
                show: false,
            }
        ],
        yAxis: {
            type: 'category',
            axisLabel: {
                show: false, //让Y轴数据不显示
            },
            itemStyle: {

            },
            axisTick: {
                show: false, //隐藏Y轴刻度
            },
            axisLine: {
                show: false, //隐藏Y轴线段
            },
            data: items,
        },
        series: [
            //背景色--------------------我是分割线君------------------------------//
            {
                show: true,
                type: 'bar',
                barGap: '-100%',
                barWidth: '17', //统计条宽度
                itemStyle: {
                    normal: {
                        barBorderRadius: 10,
                        color: 'rgba(217, 225, 233,1)'
                    },
                },
                z: 1,
                data: grayBar,
            },
            //蓝条--------------------我是分割线君------------------------------//
            {
                show: true,
                type: 'bar',
                barGap: '-100%',
                barWidth: '17', //统计条宽度
                itemStyle: {
                    normal: {
                        barBorderRadius: [0, 10, 10, 0], //统计条弧度
                    },
                },
                max: 1,
                label: {
                    normal: {
                        show: true,
                        //label 的position位置可以是top bottom left,right,也可以是固定值
                        //在这里需要上下统一对齐,所以用固定值
                        position: [0, '-100%'],
                        rich: { //富文本
                            black: { //自定义颜色
                                color: '#333',
                            },


                            yellow: {
                                color: '#6087ce',
                                fontWeight: '500',
                            },
                        },
                        formatter: function(data) {
                            //富文本固定格式{colorName|这里填你想要写的内容}
                            return ' {black|' + xuhao[data.dataIndex] + '  ' + items[data.dataIndex] + '}' + '{black|  }{yellow|' + blueBar[data.dataIndex] + '}';

                        },
                    }
                },
                labelLine: {
                    show: false,
                },
                z: 2,
                data: blueBar,
            },

        ]
    };
        //显示图表。
        if (chartMaterialCompare) {
            chartMaterialCompare.setOption(option, true);
        } else {
            chartMaterialCompare = echarts.init(document.getElementById("chart_material_consumption"));
            chartMaterialCompare.setOption(option);
        }

    }
    //产量 对比折线图
    function showProductionChart(productionArray){
        var quantityTime = []; //时间数组
        var idealQuantity = []; //理论产量数组
        var actualQuantity = []; //实际产量数组
        $.each(productionArray, function(idx, item){
            quantityTime.push(dateutils.format(new Date(item.quantity_time), "hh:mm")); //时间只要小时、分钟
            idealQuantity.push(item.ideal_quantity);
            actualQuantity.push(item.actual_quantity);
        });
        var option = {

                    title: {
                        show:false
                    },
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            lineStyle: {
                                color: '#ddd'
                            }
                        },
                        formatter:"{b}<br>{c}t",
                        backgroundColor: 'rgba(255,255,255,1)',
                        padding: [5, 10],
                        textStyle: {
                            color: '#7588E4',
                        },
                        extraCssText: 'box-shadow: 0 0 5px rgba(0,0,0,0.3)'
                    },
                    legend: {
                        show:true,
                        data:['实际产量','理论产量'],
                        bottom:10,
                        itemGap:40,
                    },
                    grid: { // 控制图的大小，调整下面这些值就可以，
                    show:true,
                        left: 50,
                        right: 20,
                        top:30,
                        bottom: 60,

                        borderWidth:1,
                        borderColor:"#c4dbf7"
                    },
                    xAxis: {
                        type: 'category',
                        data: quantityTime,
                        boundaryGap: false,
                        axisLine:{lineStyle:{color:"#c4dbf7"}},
                        splitLine:{lineStyle:{color:"#c4dbf7"}},
                        axisTick:{show:false},
                        axisLabel:{color:"#333333"}
                    },
                    yAxis: {
                        name: '',
                        type: 'value',
                        axisLine:{show:false,lineStyle:{color:"#c4dbf7"}},
                        splitLine:{lineStyle:{color:"#c4dbf7"}},
                        axisTick:{show:false},
                        axisLabel:{color:"#333333"}
                    },
                    series: [{
                        name: '实际产量',
                        type: 'line',

                        showSymbol: true,
                        symbol: 'rect',
                        symbolSize: 10,
                        label:{
                                normal: {
                                    show: false,
                                    position: 'top',
                                    color:"#3497cb"
                                }
                            },
                        data: actualQuantity,
                        areaStyle: {
                            normal: {
                                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                                    offset: 0,
                                    color: 'rgba(33,173,253,1)'
                                }, {
                                    offset: 1,
                                    color: 'rgba(255,255,255,0.5)'
                                }], false)
                            }
                        },
                        itemStyle: {
                            normal: {
                                color: '#21adfd'
                            }
                        },
                        lineStyle: {
                            normal: {
                                width: 2
                            }
                        }
                    },{
                        name: '理论产量',
                        type: 'line',

                        showSymbol: true,
                        symbol: 'circle',
                        symbolSize: 10,
                        label:{
                                normal: {
                                    show: false,
                                    position: 'top',
                                    color:"#3497cb"
                                }
                            },
                        data: idealQuantity,

                        itemStyle: {
                            normal: {
                                color: 'rgba(96,135,206,1)'
                            }
                        },
                        lineStyle: {
                            normal: {
                                width: 2
                            }
                        }
                    }]
                };
        //显示图表。
        if (chartProductionCompare) {
            chartProductionCompare.setOption(option, true);
        } else {
            chartProductionCompare = echarts.init(document.getElementById("chart_production_compare"));
            chartProductionCompare.setOption(option);
        }
    }

    function showStopChart(stopArray)
    {
        var faultName = []; //停机原因
        var stopTime = []; //停机时间
        var stopCount = []; //停机次数
        $.each(stopArray, function(idx, item){
            faultName.push(item.fault_problem); //时间只要小时、分钟
            stopTime.push(item.break_time);
            stopCount.push(item.count);
        });
        var option = {

                title: {
                    show: false
                },

                legend: {
                data:['停机时间','停机次数'],
                show: true,
                textStyle:{color:"#333",fontSize:12,},
                itemGap:150,
                bottom:10
                },
                grid: { // 控制图的大小，调整下面这些值就可以，
                    show: true,
                    left: 50,
                    right: 20,
                    top: 30,
                    bottom: 60,

                    borderWidth: 1,
                    borderColor:"#c4dbf7"
                },
                xAxis: {
                    type: 'category',
                    data: faultName,
                    boundaryGap: true,

                    axisLine:{lineStyle:{color:"#c4dbf7"}},
                        splitLine:{lineStyle:{color:"#c4dbf7"}},
                    axisTick: {
                        show: false
                    },
                    axisLabel: {
                        color: "#333"
                    }
                },
                yAxis: [{
                        name: '停机时间',

                        type: 'value',
                        axisLine:{lineStyle:{color:"#c4dbf7"}},
                        splitLine:{lineStyle:{color:"#c4dbf7"}},
                        axisTick: {
                            show: false
                        },
                    splitNumber:3,
                        axisLabel: {
                            color: "#333"
                        }
                    },
                    {
                        name: '停机次数',

                        type: 'value',

                        axisLine:{lineStyle:{color:"#c4dbf7"}},
                        splitLine:{show:false,lineStyle:{color:"#c4dbf7"}},
                        axisTick: {
                            show: false
                        },
                        axisLabel: {
                            show: true,
                            color: "#333"
                        }
                    },
                ],
                series: [{
                        name: '停机时间',
                        type: 'bar',

                        showSymbol: true,
                        symbol: 'circle',
                        symbolSize: 10,
                        label: {
                            normal: {
                                show: false,
                                position: 'top',
                                color: "#3497cb"
                            }
                        },
                        data:stopTime,
                        barWidth:'50',
                        itemStyle: {
                            normal: {
                                color: "#6087ce"
                            }
                        },

                    },
                    {
                        name: '停机次数',
                        type: 'line',
                        yAxisIndex: 1,
                        showSymbol: true,
                        symbol: 'rect',
                        symbolSize: 10,
                        label: {
                            normal: {
                                show: false,
                                position: 'top',
                                color: "#3497cb"
                            }
                        },
                        data: stopCount,

                        itemStyle: {
                            normal: {
                                color: '#21adfd'
                            }
                        },
                        lineStyle: {
                            normal: {
                                width: 3
                            }
                        }
                    }
                ]
            };
        if (chartStopchart) {
            chartStopchart.setOption(option, true);
        } else {
            chartStopchart = echarts.init(document.getElementById("chart_stop"));
            chartStopchart.setOption(option);
        }
    }

    function showEnergy(energyArray)
    {
        var xAxisData = ["电耗","气耗","水耗"];
        var seriesData = [energyArray.unit_electric,0,0];
        var colorDate=[];
        var option = {
                color: ['#3398DB'],
                tooltip : {
                    trigger: 'axis',
                    axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                        type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                    }
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis : [
                    {
                        type : 'category',
                        data : xAxisData,
                        axisTick: {
                            alignWithLabel: true
                        }
                    }
                ],
                yAxis : [
                    {
                        type : 'value'
                    }
                ],
                series : [
                    {
                        name:'能耗',
                        type:'bar',
                        barWidth: '60%',
                        data:seriesData
                    }
                ]
            };
        if (chartEnergy) {
            chartEnergy.setOption(option, true);
        } else {
            chartEnergy = echarts.init(document.getElementById("chart_energy"));
            chartEnergy.setOption(option);
        }
    }

    function change(a)
    {
        if(a==0)
        {
            return '停止';
        }
        else if(a==1)
        {
            return '运行';
        }
        else if(a==9)
        {
            return '故障';
        }
    }


    function showInfromationList(infromationList)
    {
        //总
        var mechanical_cp=0;
        var mechanical_tb=0;
        var mechanical_gz=0;
        var produce_cp=0;
        var produce_tb=0;
        var produce_gz=0;
        if(infromationList.cp.data.rate!=undefined)
        {
            mechanical_cp=infromationList.cp.data.rate.machine;
            produce_cp=infromationList.cp.data.rate.produce;
        }
        if(infromationList.gz.data.rate!=undefined)
        {
            mechanical_gz=infromationList.gz.data.rate.machine;
            produce_gz=infromationList.gz.data.rate.produce;
        }
        if(infromationList.tb.data.rate!=undefined)
        {
            mechanical_tb=infromationList.tb.data.rate.machine;
            produce_tb=infromationList.tb.data.rate.produce;
        }
        var mechanical=Math.round((mechanical_cp+mechanical_gz+mechanical_tb)*100/3);
        var produce=Math.round((produce_cp+produce_tb+produce_gz)*100/3);
        $("#mechanical_efficiency").text(mechanical+"%");
        $("#production_efficiency").text(produce+"%");

        $("#cp_mechanical_efficiency").text(Math.round(mechanical_cp*100)+"%");
        $("#cp_production_efficiency").text(Math.round(produce_cp*100)+"%");



        $("#tb_mechanical_efficiency").text(Math.round(mechanical_tb*100)+"%");
        $("#tb_production_efficiency").text(Math.round(produce_tb*100)+"%");
        $("#yt_mechanical_efficiency").text(Math.round(mechanical_gz*100)+"%");
        $("#yt_production_efficiency").text(Math.round(produce_gz*100)+"%");




        if(infromationList.cp.data.state!=undefined)
        {
            $("#cp_eqpt_state").text(change(infromationList.cp.data.state.eqpt_state));
        }
        if(infromationList.cp.data.prod!=undefined)
        {
            $("#cp_producition").text(infromationList.cp.data.prod.current_quantity);
            $("#cp_product_code").text(infromationList.cp.data.prod.prod_code);
        }
        if(infromationList.cp.data.currentMaterial!=undefined)
        {
            $("#cp_material_consumption").text(infromationList.cp.data.currentMaterial.currentmaterial);

        }
        if(infromationList.cp.data.currentFault.length!=0)
        {
            $("#cp_fault").text(infromationList.cp.data.currentFault[0].faultProblem);

        }







        if(infromationList.tb.data.state!=undefined)
        {
            $("#tb_eqpt_state").text(change(infromationList.tb.data.state.eqpt_state));
        }
        if(infromationList.tb.data.prod!=undefined)
        {
            $("#tb_producition").text(infromationList.tb.data.prod.current_quantity);
            $("#tb_product_code").text(infromationList.tb.data.prod.prod_code);
        }
        if(infromationList.tb.data.currentMaterial!=undefined)
        {
            $("#tb_material_consumption").text(infromationList.tb.data.currentMaterial.currentmaterial);

        }
        if(infromationList.tb.data.currentFault.length!=0)
        {
            $("#tb_fault").text(infromationList.tb.data.currentFault[0].faultProblem);

        }


        if(infromationList.gz.data.state!=undefined)
        {
            $("#yt_eqpt_state").text(change(infromationList.gz.data.state.eqpt_state));
        }
        if(infromationList.gz.data.prod!=undefined)
        {
            $("#yt_producition").text(infromationList.gz.data.prod.current_quantity);
            $("#yt_product_code").text(infromationList.gz.data.prod.prod_code);
        }
        if(infromationList.gz.data.currentMaterial!=undefined)
        {
            $("#yt_material_consumption").text(infromationList.gz.data.currentMaterial.currentmaterial);

        }
        if(infromationList.gz.data.currentFault.length!=0)
        {
            $("#yt_fault").text(infromationList.gz.data.currentFault[0].faultProblem);

        }







//        $("#tb_eqpt_state").text(change(infromationList.tb.data.state.eqpt_state));
//        $("#tb_producition").text(infromationList.tb.data.prod.current_quantity);
//        $("#tb_material_consumption").text(infromationList.tb.data.currentMaterial.currentmaterial);
//        $("#tb_fault").text(infromationList.tb.data.currentFault[0].faultProblem);
//        $("#tb_product_code").text(infromationList.tb.data.prod.prod_code);
//
//        $("#yt_eqpt_state").text(change(infromationList.gz.data.state.eqpt_state));
//        $("#yt_producition").text(infromationList.gz.data.prod.current_quantity);
//        $("#yt_material_consumption").text(infromationList.gz.data.currentMaterial.currentmaterial);
//        $("#yt_fault").text(infromationList.gz.data.currentFault[0].faultProblem);
//        $("#yt_product_code").text(infromationList.gz.data.prod.prod_code);


//wearing_part
        var actual_left_cp=-1;
        var actual_left_tb=-1;
        var actual_left_gz=-1;
        var l_actual_left_cp=-1;
        var l_actual_left_tb=-1;
        var l_actual_left_gz=-1;
        if(infromationList.cp.data.wearing_part.actual_left!=undefined)
        {
            actual_left_cp=infromationList.cp.data.wearing_part.actual_left;
        }
        if(infromationList.tb.data.wearing_part.actual_left!=undefined)
        {
            actual_left_tb=infromationList.tb.data.wearing_part.actual_left;
        }
        if(infromationList.gz.data.wearing_part.actual_left!=undefined)
        {
            actual_left_gz=infromationList.gz.data.wearing_part.actual_left;
        }
        if(actual_left_cp!=-1&&actual_left_tb!=-1&&actual_left_gz!=-1)
        {
            compareChange(infromationList.cp.data.wearing_part,infromationList.tb.data.wearing_part,infromationList.gz.data.wearing_part);
        }


        if(infromationList.cp.data.maintenance.actual_left!=undefined)
        {
            l_actual_left_cp=infromationList.cp.data.maintenance.actual_left;
        }
        if(infromationList.tb.data.maintenance.actual_left!=undefined)
        {
            l_actual_left_tb=infromationList.tb.data.maintenance.actual_left;
        }
        if(infromationList.gz.data.maintenance.actual_left!=undefined)
        {
            l_actual_left_gz=infromationList.gz.data.maintenance.actual_left;
        }

        if(l_actual_left_cp!=-1&&l_actual_left_gz!=-1&&l_actual_left_tb!=-1)
        {
            compareLu(infromationList.cp.data.maintenance,infromationList.tb.data.maintenance,infromationList.gz.data.maintenance);
        }

    }


    //========================== 定时执行 =========================
   // setInterval("loadData()", 10 * 1000);
    //setInterval("loadother()", 10 * 1000);
    window.loadData = loadData;
});


function loadother()
{
    window.location.href="../analysis/labelingMachineAnalysis.html";
}

function compareChange(a,b,c)
{//infromationList.cp.EqpAll.data.wearing_part.actual_left,infromationList.tb.EqpAll.data.wearing_part.actual_left,infromationList.gz.EqpAll.data.wearing_part.actual_left
    var n = Math.min(a.actual_left,b.actual_left,c.actual_left);
    if(n==a)
    {
        $("#change").text(a.part_name);
        $("#change_value").text(a.actual_duration+"/"+a.life_duration);
        //$("#unit").text(a.actual_unit);
    }
    else if(n==b)
        {
        $("#change").text(b.part_name);
        $("#change_value").text(b.actual_duration+"/"+b.life_duration);
        //$("#unit").text(b.actual_unit);
    }
    else
        {
        $("#change").text(c.part_name);
        $("#change_value").text(c.actual_duration+"/"+c.life_duration);
        //$("#unit").text(c.actual_unit);
        }
}

function compareLu(a,b,c)
{//infromationList.cp.EqpAll.data.wearing_part.actual_left,infromationList.tb.EqpAll.data.wearing_part.actual_left,infromationList.gz.EqpAll.data.wearing_part.actual_left
    var n = Math.min(a.actual_left,b.actual_left,c.actual_left);
    if(n==a)
    {
        $("#lu_name").text(a.plan_part);
        $("#lu_value").text(a.actual_duration+"/"+a.life_duration);
//        ${"#l_unit"}.text(infromationList.maintenance.actual_unit);
        //$("#l_unit").text(a.actual_unit);
    }
    else if(n==b)
        {
        $("#lu_name").text(b.plan_part);
        $("#lu_value").text(b.actual_duration+"/"+b.life_duration);
//        ${"#l_unit"}.text(infromationList.maintenance.actual_unit);
        //$("#l_unit").text(b.actual_unit);
    }
    else
        {
        $("#lu_name").text(c.plan_part);
        $("#lu_value").text(c.actual_duration+"/"+c.life_duration);
//        ${"#l_unit"}.text(infromationList.maintenance.actual_unit);
        //$("#l_unit").text(c.actual_unit);
        }
}

function convertTime(jsonTime, format) {
    var date = new Date(parseInt(jsonTime.replace("/Date(", "").replace(")/", ""), 10));
    var formatDate = date.format(format);
    return formatDate;
}
Date.prototype.format = function (format) {
    var date = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S+": this.getMilliseconds()
    };

    if (/(y+)/i.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
    }

    for (var k in date) {
        if (new RegExp("(" + k + ")").test(format)) {
          format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? date[k] : ("00" + date[k]).substr(("" + date[k]).length));
        }
    }

    return format;
}
//testfunction
function testfunction(type){
    $.ajax({
         url : "/dashboard/produce/chooseEqpt",
         cache:false,
         async:false,
         method : "get",
         success : function(json) {

             if(type=="cp")
             {
                 window.location.href =json.cp +"page/frame.html";
             }
             if(type=="tb")
             {
                 window.location.href = json.tb+"page/frame.html";
             }
             if(type=="gz")
             {
                 window.location.href =json.gz+"page/frame.html";
             }

         }
     });


    //window.location.href = "http://localhost:9199/page/frame.html";
}




/**
 * 退出登录
 */
function logout() {
   $.ajax({
        url : "/json/Users/logout",
        method : "post",
        success : function(json, textStatus, jqXHR) {

                window.location.href = "/page/login.html";



        }
    });
}