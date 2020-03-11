let myChart;
$(function(){
    myChart = echarts.init(document.getElementById('layout2'));
    option = {
        tooltip: {},
        animationDurationUpdate: 1500,
        animationEasingUpdate: 'quinticInOut',
        series : [
            {
                type: 'graph',
                layout: 'none',
                symbolSize: 50,
                roam: true,
                label: {
                    normal: {
                        show: true
                    }
                },
                edgeSymbol: ['circle', 'arrow'],
                edgeSymbolSize: [4, 10],
                edgeLabel: {
                    normal: {
                        textStyle: {
                            fontSize: 20
                        }
                    }
                },
                data:[],
                links:[]
                /*data: [{
                    name: '原料库A',
                    x: -500,
                    y: 70,
                    itemStyle:{
                        color:'#2DBFC1'
                    }
                }, {
                    name: '线边库A',
                    x: -400,
                    y: 170,
                    itemStyle:{
                        color:'#2DBFC1'
                    }
                }, {
                    name: '夹右端外径，顶左端',
                    x: -400,
                    y: 270,
                    itemStyle:{
                        color:'#656FE9'
                    }
                }, {
                    name: '质检A',
                    x: -300,
                    y: 270,
                    itemStyle:{
                        color:'#EEB903'
                    }
                }, {
                    name: '夹左端外径，46.04外径上中心架',
                    x: -400,
                    y: 370,
                    itemStyle:{
                        color:'#656FE9'
                    }
                }, {
                    name: '半成品A',
                    x: -400,
                    y: 470,
                    itemStyle:{
                        color:'#2DBFC1'
                    }
                }, {
                    name: '线边库C',
                    x: 0,
                    y: 470,
                    itemStyle:{
                        color:'#2DBFC1'
                    }
                }, {
                    name: '夹右端外径，顶右端',
                    x: -400,
                    y: 570,
                    itemStyle:{
                        color:'#656FE9'
                    }
                }, {
                    name: '夹左端外径（垫铜片），顶右端',
                    x: -400,
                    y: 670,
                    itemStyle:{
                        color:'#656FE9'
                    }
                }, {
                    name: '成品C',
                    x: -200,
                    y: 670,
                    itemStyle:{
                        color:'#2DBFC1'
                    }
                }, {
                    name: '成品质检',
                    x: 0,
                    y: 670,
                    itemStyle:{
                        color:'#EEB903'
                    }
                }, {
                    name: '成品入库',
                    x: 200,
                    y: 670 ,
                    itemStyle:{
                        color:'#2DBFC1'
                    }
                }, {
                    name: '经销商',
                    x: 400,
                    y: 670,
                    itemStyle:{
                        color:'#2DBFC1'
                    }
                }, {
                    name: '原料库B',
                    x: 400,
                    y: 570,
                    itemStyle:{
                        color:'#2DBFC1'
                    }
                }, {
                    name: '半成品B',
                    x: 400,
                    y: 470,
                    itemStyle:{
                        color:'#2DBFC1'
                    }
                }, {
                    name: '夹右端外径（垫铜片），顶右端',
                    x: 400,
                    y: 370,
                    itemStyle:{
                        color:'#656FE9'
                    }
                }, {
                    name: '质检B',
                    x: 500,
                    y: 325,
                    itemStyle:{
                        color:'#EEB903'
                    }
                }, {
                    name: '掉头，双顶(鸡心夹位置垫铜片)',
                    x: 400,
                    y: 270,
                    itemStyle:{
                        color:'#656FE9'
                    }
                }, {
                    name: '线边库B',
                    x: 400,
                    y: 170,
                    itemStyle:{
                        color:'#2DBFC1'
                    }
                }, {
                    name: '原料库D',
                    x: 500,
                    y: 70,
                    itemStyle:{
                        color:'#2DBFC1'
                    }
                }, {
                    name: '原料库C',
                    x: 0,
                    y: 70 ,
                    itemStyle:{
                        color:'#2DBFC1'
                    }
                }],
                links: [{
                    source: 0,
                    target: 1,
                    lineStyle: {
                        color: 'blue',
                        width: 3
                    }
                },{
                    source: 1,
                    target: 2,
                    lineStyle: {
                        color: 'blue',
                        width: 3
                    }
                },{
                    source: 2,
                    target: 3,
                    lineStyle: {
                        color: 'blue',
                        width: 3
                    }
                },{
                    source: 3,
                    target: 4,
                    lineStyle: {
                        color: 'blue',
                        width: 3
                    }
                },{
                    source: 4,
                    target: 5,
                    lineStyle: {
                        color: 'blue',
                        width: 3
                    }
                },{
                    source: 5,
                    target: 6,
                    lineStyle: {
                        color: 'blue',
                        width: 3
                    }
                },{
                    source: 6,
                    target: 7,
                    lineStyle: {
                        color: 'blue',
                        width: 3
                    }
                },{
                    source: 7,
                    target: 8,
                    lineStyle: {
                        color: 'blue',
                        width: 3
                    }
                },{
                    source: 8,
                    target: 9,
                    lineStyle: {
                        color: 'blue',
                        width: 3
                    }
                },{
                    source: 9,
                    target: 10,
                    lineStyle: {
                        color: 'blue',
                        width: 3
                    }
                },{
                    source: 10,
                    target: 11,
                    lineStyle: {
                        color: 'blue',
                        width: 3
                    }
                },{
                    source: 11,
                    target: 12,
                    lineStyle: {
                        color: 'blue',
                        width: 3
                    }
                },{
                    source: 13,
                    target: 6,
                    lineStyle: {
                        color: 'blue',
                        width: 3
                    }
                },{
                    source: 14,
                    target: 13,
                    lineStyle: {
                        color: 'blue',
                        width: 3
                    }
                },{
                    source: 15,
                    target: 14,
                    lineStyle: {
                        color: 'blue',
                        width: 3
                    }
                },{
                    source: 16,
                    target: 15,
                    lineStyle: {
                        color: 'blue',
                        width: 3
                    }
                },{
                    source: 17,
                    target: 16,
                    lineStyle: {
                        color: 'blue',
                        width: 3
                    }
                },{
                    source: 18,
                    target: 17,
                    lineStyle: {
                        color: 'blue',
                        width: 3
                    }
                },{
                    source: 19,
                    target: 18,
                    lineStyle: {
                        color: 'blue',
                        width: 3
                    }
                },{
                    source: 0,
                    target: 18,
                    lineStyle: {
                        color: 'blue',
                        width: 3
                    }
                },{
                    source: 20,
                    target: 1,
                    lineStyle: {
                        color: 'blue',
                        width: 3
                    }
                },{
                    source: 1,
                    target: 18,
                    lineStyle: {
                        color: 'blue',
                        width: 3
                    }
                }
                ]*/
            }
        ]
    };
    myChart.setOption(option);

});

//查询方法
function queryTrace() {
    let code = $('#productCode').val();
    console.log(code);
    if(code == null || code === '') return;
    $.post("/json/retrospect/getGoodsRetrospect",{"goodsCode":code},
        function(json){
            if(json!= null){
                myChart.setOption({
                    series: [{
                        type: 'graph',
                        data: json.data,
                        links: json.links
                    }]
                });
            }
        },"JSON");
}