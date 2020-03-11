
let myChart = null;
$(function() {
    $("#layout").height(window.innerHeight * 0.65);
    myChart = echarts.init(document.getElementById('layout'));
    let option = {
        tooltip: {
            formatter(params) {
                return params.dataType === 'node'?'<p>操作人: ' + params.data.username + '</p>' + '<p>操作时间: ' + params.data.createTime + '</p>':null;
            },
        },
        animationDurationUpdate: 1500,
        animationEasingUpdate: 'quinticInOut',
        series : [
            {
                type: 'graph',
                layout: 'none',
                symbolSize: 60,
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
                            fontSize: 160
                        }
                    }
                },
                lineStyle: {
                    normal: {
                        color: '#0000FF'
                    }
                },
                data: [],
                links: []

            }
        ]
    };
    myChart.setOption(option);
    window.onresize = myChart.resize;

    /*$.post("/json/retrospect/getGoodsRetrospect",{goodsCode:'123456789'},
        function(json){
            console.log(json);
            if(json!= null){
                myChart.setOption({
                    series: [{
                        type: 'graph',
                        data: json.data,
                        links: json.links
                    }]
                });
            }
        },"JSON");*/

});


//查询方法
function queryTrace() {
    let code = $('#productCode').textbox('getValue');
    if(code == null || code === '') return;
    $.post("/json/retrospect/getGoodsRetrospect",{"goodsCode":code},
        function(json){
        console.log(json);
            if(json!= null){
                if (json.status == 0) {
                    $.messager.alert(lang.getText("promptNotice_prompt"),lang.getText("base_operate_7"), "info");
                } else if (json.status == 1) {
                    myChart.setOption({
                        series: [{
                            type: 'graph',
                            data: json.data,
                            links: json.links
                        }]
                    });
                }
            }
        },"JSON");
}

/**
 * 把时间戳转换成 yyyy-MM-dd hh:mm:ss 格式的字符串
 */
function timestampToString(value) {
    let date =  new Date(value);
    let y = 1900+date.getFullYear();
    let m = "0"+(date.getMonth()+1);
    let d = "0"+date.getDate();
    return y+"-"+m.substring(m.length-2,m.length)+"-"+d.substring(d.length-2,d.length);
}