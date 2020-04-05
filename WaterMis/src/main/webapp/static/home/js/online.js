$(function() {
    var dom = document.getElementById("container2");
    var myChart = echarts.init(dom);
    var app = {};
    option = null;
    option = {
        
        legend: {
            x : 'center',
            y : 'bottom',
            itemWidth: 8,
            itemHeight: 8,
            textStyle:{//图例文字的样式
                color:'#fff',
                fontSize:12
            },
            data:['镜湖区','戈江区','三山区']
        },
        calculable : true,
        series : [
            {
                name:'',
                type:'pie',
                radius : [30, 100],
                center : ['50%', '45%'],
                roseType : 'area',
                data:[
                    {value:10, name:'镜湖区',itemStyle:{normal:{color:'#ff7800'}}},
                    {value:20, name:'戈江区',itemStyle:{normal:{color:'#23eb6a'}}},
                    {value:30, name:'三山区',itemStyle:{normal:{color:'#7627cb'}}}
                   
                ]
            }
        ]
    };
    ;
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }
});