
let myChart = null;
$(function() {
    myChart = echarts.init(document.getElementById('layout'));
    let option = {
        tooltip: {},
        animationDurationUpdate: 1500,
        animationEasingUpdate: 'quinticInOut',
        series : [
            {
                type: 'graph',
                layout: 'none',
                symbolSize: 30,
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
                data: [],
                links: []

            }
        ]
    };
    myChart.setOption(option);


});

//查询方法
function queryTrace() {
    let code = $('#materialCode').textbox('getValue');
    if(code == null || code == '') return;
    $.post("/json/traceHistory/materialTrace",{"materialCode":code},
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
};
