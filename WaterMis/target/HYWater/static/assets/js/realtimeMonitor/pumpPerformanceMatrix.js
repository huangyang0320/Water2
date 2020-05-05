PERFORMANCE_MATRIX = {};

PERFORMANCE_MATRIX.pumpModel={};
PERFORMANCE_MATRIX.maxPoint = [];
PERFORMANCE_MATRIX.minPoint = [];
var pumpModel =null;
var hz = null;
var character="lift";
var first=true;
PERFORMANCE_MATRIX.DEMO = {
    colors: ["#232855", "#2980b9", "#432160", "#61c0bf", "#0960bd", "#542ff4", "#abc93b"],
    chart: {
        type:'line',
        animation: Highcharts.svg, // don't animate in old IE
        marginRight: 10,
        backgroundColor: {
            linearGradient: { x1: 0, y1: 0, x2: 1, y2: 1 },
            stops: [
                [0, 'rgb(255, 255, 255)'],
                [1, 'rgb(240, 240, 255)']
            ]
        },
        borderWidth: 1,
        plotBackgroundColor: 'rgba(255, 255, 255, .9)',
        plotShadow: true,
        plotBorderWidth: 1,
        zoomType: "xy"
    },
    exporting: {
      enabled: false
    },
    tooltip: {
        enabled: true,
        crosshairs: true,
        shared: true,
        headerFormat: '流量: {point.x} m³/h<br>',
        style: {
            color: '#000',
            font: '14px "Trebuchet MS", Verdana, Microsoft YaHei, sans-serif'
        }
    },
    title: {
        text: "",
        style: {
            color: '#000',
            font: '14px "Trebuchet MS", Verdana, Microsoft YaHei, sans-serif'
        }
    },
    xAxis: {
        title: {
            text: '流量m³/小时',
            style: {
                color: '#333',
                fontSize: '12px',
                fontFamily: 'Trebuchet MS, Verdana, Microsoft YaHei, sans-serif'

            }
        },
        tickPixelInterval: 150,
        gridLineWidth: 1,
        lineColor: '#000',
        tickColor: '#000',
        labels: {
            style: {
                color: '#000',
                font: '11px Trebuchet MS, Verdana, Microsoft YaHei, sans-serif'
            },
            format: '{value} h'
        },
        tickmarkPlacement: 'on'
    },
    yAxis: {
        title: {
            text: "",
            style: {
                color: '#333',
                fontSize: '12px',
                fontFamily: 'Trebuchet MS, Verdana, Microsoft YaHei, sans-serif'
            },
        },
        //visible:false,
        minorTickInterval: 'auto',
        tickPixelInterval: 50,
        lineColor: '#000',
        lineWidth: 1,
        tickWidth: 1,
        tickColor: '#000',
        min: 0, // 这个用来控制y轴的开始刻度（最小刻度），另外还有一个表示最大刻度的max属性
        startOnTick: false, // y轴坐标是否从某一刻度开始（这个设定与标题无关）
        labels: {
            style: {
                color: '#000',
                font: '11px Trebuchet MS, Verdana, Microsoft YaHei, sans-serif'
            },
            format: "{value}"
        },
        plotLines: [{
            value: 0,
            width: 1,
            color: '#808080'
        }]
    },
    legend: {
        maxHeight: 80
    },
    plotOptions: {
        series: {
            marker: {
                enabled: false
            }
        },
    },
    lang: {
        noData: "没有数据，请修改查询条件....." //真正显示的文本
    },
    noData: {
        // Custom css
        style: {    //对无数据标签的CSS样式。 默认值：[object Object].
            fontWeight: 'nomal',
            fontSize: '14px',
            color: 'red'
        }
    },
    credits: {
        enabled: false//不显示highCharts版权信息
    }
};
/**
 * 数据的初始化
 */
$(function() {
    LOADING.show();
    queryPumpModel();
    LOADING.hide();
});

function queryPumpModel(){
    $.post(CONTEXT_PATH + "/performanceMatrix/queryPumpModel",function(result){
        $("#pumpModel").append("<option value='null'>请选择</option>");
        if (result != 0) {
            for ( var i = 0; i < result.length; i++) {
                if(i==0){
                    $("#pumpModel").append("<option selected value="+result[i]+">"+ result[i] + "</option>");
                }else {
                    $("#pumpModel").append("<option  value="+result[i]+">"+ result[i] + "</option>");
                }
            }
            if(first){
                $("#pumpModel").val(result[0]);
            }
            findHz();
        }
    });
}
function findHz(){
    var pumpModel = $("#pumpModel").val();
    $.ajax({
        url:CONTEXT_PATH + "/performanceMatrix/queryHzByPumpModel",
        type:"post",
        timeout:"1000",
        data:{pumpModel:pumpModel},
        success:function(data){
            $("#hz option").remove();
            $("#hz").append("<option value='null'>请选择</option>");
            if (data != 0) {
                for ( var i = 0; i < data.length; i++) {
                    if(i==0){
                        $("#hz").append("<option selected value="+data[i]+">"+ data[i] + "</option>");

                    }else {
                        $("#hz").append("<option value="+data[i]+">"+ data[i] + "</option>");
                    }
                }
                if(first){
                    $("#hz").val(data[0]);
                    first=false;
                }
                onLiftTablClick();
            }
        }
    });
}

//流量扬程曲线图tab显示
function onLiftTablClick(){
    pumpModel=$("#pumpModel").val();
    hz=$("#hz").val();
    $.post(CONTEXT_PATH + "/performanceMatrix/queryMatrix?pumpModel="+pumpModel+"&hz="+hz+"&character=lift",function(result){
        PERFORMANCE_MATRIX.DEMO.xAxis.categories = result.xData;
        PERFORMANCE_MATRIX.DEMO.series = result.yData;
        PERFORMANCE_MATRIX.DEMO.yAxis.labels.format="{value} M";
        PERFORMANCE_MATRIX.DEMO.yAxis.title.text='扬程';
        $("#highcharts-pump-performanceMatrix").highcharts(PERFORMANCE_MATRIX.DEMO);
    });
    $("#myTab li").removeClass();
    $("#myTab-li").addClass("active");
    $("#tab-flowLift").show();
    $(".reportContainer").hide();
}
//流量功率曲线图tab显示
function onPowerTablClick(){
    $.post(CONTEXT_PATH + "/performanceMatrix/queryMatrix?pumpModel="+pumpModel+"&hz="+hz+"&character=power",function(result){
        PERFORMANCE_MATRIX.DEMO.xAxis.categories = result.xData;
        PERFORMANCE_MATRIX.DEMO.series = result.yData;
        PERFORMANCE_MATRIX.DEMO.yAxis.labels.format="{value} KW";
        PERFORMANCE_MATRIX.DEMO.yAxis.title.text='输入输出功率';
        $("#highcharts-pump-performanceMatrix").highcharts(PERFORMANCE_MATRIX.DEMO);
        $("#tab-flowLift").show();
        $(".reportContainer").hide();
    });
}
//流量效率曲线图tab显示
function onEfficiencyTablClick(){
    $.post(CONTEXT_PATH + "/performanceMatrix/queryMatrix?pumpModel="+pumpModel+"&hz="+hz+"&character=efficiency",function(result){
        PERFORMANCE_MATRIX.DEMO.xAxis.categories = result.xData;
        PERFORMANCE_MATRIX.DEMO.series = result.yData;
        PERFORMANCE_MATRIX.DEMO.yAxis.labels.format="{value} %";
        PERFORMANCE_MATRIX.DEMO.yAxis.title.text='效率';
        $("#highcharts-pump-performanceMatrix").highcharts(PERFORMANCE_MATRIX.DEMO);
        $("#tab-flowLift").show();
        $(".reportContainer").hide();
    });
}
/**
 * 导出excel
 */
/*$("#export").on("click", function () {
    var $chart = $("#highcharts-pump-performanceMatrix");
    $chart.highcharts().downloadXLS();
});*/

//获取表格数据
var pageNo=1;
var selectedOptionIndex = 2;
var pageSizeArr = [5, 10, 15]
var pageSize = pageSizeArr[selectedOptionIndex];
var page = pageSizeArr[selectedOptionIndex];
// 获取表格数据接口
function realDataTablClick(time) {
    $("#tab-flowLift").hide();
    $(".reportContainer").show();
    let param = {
        "pageNo" : pageNo,
        "pageSize":pageSize,
        "pumpModel":pumpModel,
        "hz":hz
    };
    $.ajax({
        type: "POST",
        url: CONTEXT_PATH + "/performanceMatrix/queryAllPerformanceMatrix"/*?pageNo="+pageNo+"&pageSize="+pageSize+"&pumpModel="+pumpModel*/,
        contentType: "application/json; charset=utf-8",
        data:JSON.stringify(param),
        dataType: "json",
        success: function (message) {
            total = message.count;
            table(message.list);
            kkPager(total);
        },
        error: function (message) {
            $("#request-process-patent").html("提交数据失败！");
        }
    });
}

function table(data) {
    var html = '';
    data.forEach(function(value, key) {
        html += '<tr>';
        html += '<td>'+value.pumpModel+'</td>';
        html += '<td>'+value.hz+'</td>';
        html += '<td>'+value.flow+'</td>';
        html += '<td>'+value.lift+'</td>';
        html += '<td>'+value.inputPower+'</td>';
        html += '<td>'+value.outputPower+'</td>';
        html += '<td>'+value.efficiency+'</td>';
        html += '<td>'+value.powerFactor+'</td>';
        html += '</tr>';
    });
    $('#tableList').children().remove();
    $('#tableList').append(html)
}

//分页
function kkPager(total) {
    var surplusPages = total / pageSize;
    var totalPage = total % pageSize > 0 ? surplusPages + 1 : surplusPages;
    kkpager.generPageHtml({
        pno: pageNo,
        total: totalPage,
        mode: 'click',
        totalRecords: total,
        isGoPage: false,
        pageSize: pageSizeArr,
        selectedOptionIndex: selectedOptionIndex,
        click: function(n) {
            pageNo = n;
            this.selectPage(pageNo);
            $(".loading").show();
            realDataTablClick();
        },
    }, true);
    $('.selectPageSize').change(function() {
//		var pageSizeArr = kkpager.pageSize;
        for(var i = 0; i < pageSizeArr.length; i++) {
            if($(this).val() == pageSizeArr[i]) {
                selectedOptionIndex = i;
                pageSize = pageSizeArr[selectedOptionIndex];
                pageNo=1;
                $(".loading").show();
                realDataTablClick();
            }
        }
    });
};

