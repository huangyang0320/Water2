LIFE_DATA = {};

LIFE_DATA.isBoolVar = false;
LIFE_DATA.maxPoint = [];
LIFE_DATA.minPoint = [];
DEVICE_LEFT_DATA={};
var listType = '1';
var metaDeviceList = null;
LIFE_DATA.DEMO = {
    colors: ['#436EEE', '#50B432', '#ED561B', '#058DC7', '#DDDF00', '#24CBE5', '#64E572', '#FF9655', '#FFF263', '#6AF9C4'],
    chart: {
        animation: Highcharts.svg, // don't animate in old IE
        marginRight: 15,
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
        shared: true
    },
    title: {
        text: "生命值",
        style: {
            color: '#000',
            font: '14px "Trebuchet MS", Verdana, Microsoft YaHei, sans-serif'
        }
    },
    xAxis: {
        type: 'datetime',
        title: {
            text: "",
            style: {
                color: '#333',
                fontSize: '12px',
                fontFamily: 'Trebuchet MS, Verdana, Microsoft YaHei, sans-serif'

            }
        },
        labels: {
            style: {
                color: '#000',
                font: '11px Trebuchet MS, Verdana, Microsoft YaHei, sans-serif'
            }
        },

        tickmarkPlacement: 'on'
    },
    yAxis: {
        title: {
            text: "生命值",
            style: {
                color: '#333',
                fontSize: '12px',
                fontFamily: 'Trebuchet MS, Verdana, Microsoft YaHei, sans-serif'
            }
        },
        max: 1,
        min: 0, // 这个用来控制y轴的开始刻度（最小刻度），另外还有一个表示最大刻度的max属性
        startOnTick: false, // y轴坐标是否从某一刻度开始（这个设定与标题无关）
        labels: {
            style: {
                color: '#000',
                font: '11px Trebuchet MS, Verdana, Microsoft YaHei, sans-serif'
            },
            format: "{value}"
        }
    },
    lang: {
        noData: "没有配件信息，不能预估生命值..." //真正显示的文本
    },
    noData: {
        // Custom positioning/aligning options
        position: {    //相对于绘图区定位无数据标签的位置。 默认值：[object Object].
            // align: 'content',
            // verticalAlign: 'top'
        },
        // Custom svg attributes
        attr: {     //无数据标签中额外的SVG属性
            // 'stroke-width': 1,
            // stroke: '#cccccc'
        },
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

pageLoading(initData);

function initData() {
    initLayout();
    $("#device-data").highcharts(LIFE_DATA.DEMO);
    var deviceId = GLOBAL_SELECT_DEVICE["deviceId"];
    $("#queryForm").find("input[name='deviceId']").val(deviceId);
    $("#component").on("change", function () {
        LOADING.show();
        initComponentLeft();
    });
    initPageData(deviceId);
}

function initLayout() {
    //初始化无滚动条
    $("#page-wrapper").css("overflow-y", "hidden");
    // 计算图表展示区域和设备指标显示区域的高度
    var calcHeight = function() {
        var height = $(window).height() - $(".top-navbar").height()
            - $("#page-inner").children("div").eq(1).height() - $(".panel-heading").height() - 77;
        $(".chart-height").height(height);
    };
    calcHeight();
    $(window).resize(function() {
        calcHeight();
    });
    // 展示当前所选设备

}

function initPageData(deviceId){
    $.post(CONTEXT_PATH + "/biz/lifeprediction/initData/"+deviceId, function(result) {
    	$("#startDate").text(result.startDate);
        $("#runDays").text(result.deviceRunDays+"天");
        $("#component option").remove();
    	if(result.components.length>0){
    		$.each(result.components, function(i, item) {
    	       	 $("#component").append("<option value = '" + item.id + "'> "+item.componentName  + "</option>");
             })
             initDeviceLeft();
    	}else{
            $("#component").append("<option value = ''>配件信息为空</option>");
            initDeviceLeft();
            setTimeout(function() {
                LOADING.hide();
            }, 500);
    	}
	});
}
//设备树切换调用
function callRefresh(device, pre) {  


    //获取设备id 并存放到表单中
    var deviceId = device ? device["deviceId"] : $("#queryForm").find("input[name='deviceId']").val();  
    $("#queryForm").find("input[name='deviceId']").val(deviceId);
    //初始化界面
     initPageData(deviceId);
}
function initDeviceLeft(){
    doClean();
    var $queryForm = $("#queryForm");
    var deviceId = $queryForm.find("input[name='deviceId']").val();
    var chartType = $("#device-data").is(":hidden") ? "component" : "device";
    $queryForm.find("input[name='componentId']").val($("#component").val());
    var componentId = $("#component").val();  
    LOADING.show();  
    if(componentId == null || componentId==''){
        currentChart = $("#lifeprediction-data").highcharts(LIFE_DATA.DEMO);
        LOADING.hide();
    }else{
        //设备生命加载
        LIFE_DATA.preRequest = $.post(CONTEXT_PATH + "/biz/lifeprediction/getDeviceLifes/"+deviceId+"?" + $queryForm.serialize(), function(result) {
            DEVICE_LEFT_DATA = result;
            initComponentLeft();
        });
    }         
}
function initComponentLeft(){
    doClean();
    var $queryForm = $("#queryForm");
    var deviceId = $queryForm.find("input[name='deviceId']").val();
    var chartType = $("#device-data").is(":hidden") ? "component" : "device";
    $queryForm.find("input[name='componentId']").val($("#component").val());
    var componentId = $("#component").val(); 
    var options=$("#component option:selected");//获取当前选择项
    var componentName = options.text();//获取当前选择项的文本.
    if(componentId == null || componentId==''){
        currentChart = $("#lifeprediction-data").highcharts(LIFE_DATA.DEMO);
        LOADING.hide();
    }else{
        //配件生命预估
        LIFE_DATA.preRequest = $.post(CONTEXT_PATH + "/biz/lifeprediction/getComponentLife/"+deviceId+"?" + $queryForm.serialize(), function(result) {
            LIFE_DATA.DEMO.xAxis.categories = result.xData;
            LIFE_DATA.DEMO.series = [{name:"设备生命预估",data:DEVICE_LEFT_DATA.value},{name:componentName,data:result.value}];
            LIFE_DATA.DEMO.yAxis.min = result.minValue<DEVICE_LEFT_DATA.minValue?result.minValue:DEVICE_LEFT_DATA.minValue;
            LIFE_DATA.DEMO.xAxis.plotLines=[{
                color:'red',           //线的颜色，定义为红色
                value:result.currentIndex,   //定义在那个值上显示标示线，这里是在x轴上刻度为3的值处垂直化一条线
                dashStyle:'Dot',
                width:2                //标示线的宽度，2px
            }];
            currentChart = $("#lifeprediction-data").highcharts(LIFE_DATA.DEMO);
            setTimeout(function() {
                LOADING.hide();
            }, 500);
        });
    }     

}

/**
 * delete global variable and prevent conflict function and variable
 * @param refresh should leave current page,if not it'll abort pending ajax request
 */
function doClean() {
    LIFE_DATA.DEMO.xAxis.categories = [];
    LIFE_DATA.DEMO.series = [];
    LIFE_DATA.DEMO.title.text = "";
}
