HISTORY_DATA = {};

HISTORY_DATA.isBoolVar = false;
HISTORY_DATA.maxPoint = [];
HISTORY_DATA.minPoint = [];
var listType = '1';
var metaDeviceList = null;
HISTORY_DATA.DEMO = {
    colors: [
		"#7cb5ec",
		"#90ed7d",
		"#f7a35c",
		"#8085e9",
	],
    chart: {
        type:'column',
        animation: Highcharts.svg, // don't animate in old IE
        marginRight: 10,
        backgroundColor: {
            linearGradient: { x1: 0, y1: 0, x2: 1, y2: 1 },
            stops: [
                [0, 'rgb(255, 255, 255)'],
                [1, 'rgb(240, 240, 255)']
            ]
        },
        borderColor: '#ddd',
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
        shared: true//,
        // formatter: function () {
        //     return "<b>" + this.series.name + "</b><br/>" +
        //         Highcharts.dateFormat("%Y-%m-%d %H:%M:%S", this.x, null) + "<br/>" +
        //         Highcharts.numberFormat(this.y, 3, null, null);
        // }
    },
    title: {
        text: "",
        style: {
            color: '#000',
            font: '14px "Trebuchet MS", Verdana, Microsoft YaHei, sans-serif'
        }
    },
    xAxis: {
        //type: 'datetime',
        title: {
            text: "",
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
            }
        },
        tickmarkPlacement: 'on'
    },
    yAxis: {
        title: {
            text: "运行时长",
            style: {
                color: '#333',
                fontSize: '12px',
                fontFamily: 'Trebuchet MS, Verdana, Microsoft YaHei, sans-serif'
            }
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
    credits: {
        enabled: false//不显示highCharts版权信息
    }
};
HISTORY_DATA.PIEDATE={
	colors: ["#7cb5ec","#90ed7d","#f7a35c","#8085e9"],
    title: {
        text: '运行频率'
    },
    chart: {
        marginRight: 0
    },
    exporting: {
        enabled: false
    },
    tooltip: {
        headerFormat: '',
        pointFormat: '{point.name}: <b>{point.percentage:.1f}%</b>'
    },
    plotOptions: {
        pie: {
            allowPointSelect: true,  // 可以被选择
            cursor: 'pointer',       // 鼠标样式
            dataLabels: {
                enabled: true,
                format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                style: {
                    color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                }
            }
        }
    },
    series: [{
        type: 'pie',
        name: '',
        size:'55%',
        data: []
    }],
    credits: {
        enabled: false//不显示highCharts版权信息
    }
};
// 初始化
pageLoading(initData)

rightMenuMsg();
/**
 * 数据的初始化
 */
function initData() {
	//
    preLoad();
    var currentDate = new Date().Format("yyyy-MM-dd");
    var dateRegion = new Date().getCurrentDay(currentDate, "yyyy-MM-dd HH:mm:ss");
    var selectDevice = GLOBAL_SELECT_DEVICE;
    initQueryBox(currentDate);
    var $queryForm = $("#queryForm");
    $queryForm.find("input[name='deviceId']").val(selectDevice.id);
    $queryForm.find("input[name='queryType']").val("day");
    $queryForm.find("input[name='startDate']").val(dateRegion[0]);
    $queryForm.find("input[name='endDate']").val(dateRegion[1]);
    LOADING.hide();
    initChart()
}
function preLoad() {
    initLayout();
    $("#highcharts-history-data").highcharts(HISTORY_DATA.DEMO)
}

function initQueryBox(currentDate) {
    var $queryForm = $("#queryForm");
    var $formDate = $(".form_datetime");

    $formDate.datetimepicker(DATE_CONFIG(1, 1, "yyyy-mm-dd")).val(currentDate);
    DATE_ADD_CONFIG("day");

    $("#statisticDate").on("change", function() {
        var $timeDimen = $queryForm.find("input[name='queryType']");
        var $startDate = $queryForm.find("input[name='startDate']");
        var $endDate = $queryForm.find("input[name='endDate']");
        var dateArray = [];
        if ($timeDimen.val() == "day") {
            dateArray = new Date().getCurrentDay($(this).val(), "yyyy-MM-dd HH:00:00");
        } else if ($timeDimen.val() == "month") {
            dateArray = new Date().getCurrentMonth($(this).val(), "yyyy-MM-dd 00:00:00");
        } else {
            dateArray = new Date().getCurrentYear($(this).val(), "yyyy-MM-1 00:00:00");
        }
        $startDate.val(dateArray[0]);
        $endDate.val(dateArray[1]);
        initChart()
    });


    /* 日历插件 */
    $("#time-length").find("span").on("click", function(){
        var currentDate = new Date();
        if ($(this).hasClass("active")) {
            return;
        }
        var _this = $(this);
        _this.siblings().removeClass("active");
        _this.addClass("active");
        // 日期控件切换
        var className = _this.attr("class");
        DATE_ADD_CONFIG(className);
        var formDate = $(".form_datetime");
        formDate.datetimepicker("remove");

        var $queryForm = $("#queryForm");
        var $timeDimen = $queryForm.find("input[name='queryType']");

        if(className.indexOf("hourtime") > -1){// 时
            formDate.datetimepicker(DATE_CONFIG(1, 1, "yyyy-mm-dd hh")).val(currentDate.Format("yyyy-MM-dd HH"));
            formDate.datetimepicker("update");
            $timeDimen.val("hour");
        } else if(className.indexOf("daytime") > -1){// 日
            formDate.datetimepicker(DATE_CONFIG(2, 2, "yyyy-mm-dd")).val(currentDate.Format("yyyy-MM-dd"));
            formDate.datetimepicker("update");
            $timeDimen.val("day");
        } else if(className.indexOf("monthtime") > -1){// 月
            formDate.datetimepicker(DATE_CONFIG(3, 3, "yyyy-mm")).val(currentDate.Format("yyyy-MM"));
            formDate.datetimepicker("update");
            $timeDimen.val("month");

        } else if(className.indexOf("yeartime") > -1){// 年
            formDate.datetimepicker(DATE_CONFIG(4, 4, "yyyy")).val(currentDate.Format("yyyy"));
            formDate.datetimepicker("update");
            $timeDimen.val("year");
        } else {
            return false;
        }
        $queryForm.find("input[name='addOnDate']").val(0);
        $("#statisticDate").change();
    });

    //初始化时间控件
    var currentDate = new Date();
    var formDate = $(".form_datetime");
    formDate.datetimepicker("remove");

    var $queryForm = $("#queryForm");
    var $timeDimen = $queryForm.find("input[name='queryType']");

    formDate.datetimepicker(DATE_CONFIG(2, 2, "yyyy-mm-dd")).val(currentDate.Format("yyyy-MM-dd"));
    formDate.datetimepicker("update");
    $timeDimen.val("day");
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
}

//设备树切换调用
function callRefresh(device, pre) {
    //获取设备id 并存放到表单中
    var deviceId = device ? device["id"] : $("#queryForm").find("input[name='deviceId']").val();
    $("#queryForm").find("input[name='deviceId']").val(deviceId);
    //初始化界面
    initChart();
}

function defChoose (){
	var deviceId = GLOBAL_SELECT_DEVICE['deviceId'];
	 $("#queryForm").find("input[name='deviceId']").val(deviceId);
	//初始化界面
	 initChart();
}

function initChart(){
    LOADING.show();
    var dimen = $("#queryForm").find("input[name='queryType']").val();
    var categories = [];
    HISTORY_DATA.DEMO.title.text = "";
    HISTORY_DATA.DEMO.xAxis.labels.formatter = DEVICE_DIMEN()[dimen]["formatter"];
    HISTORY_DATA.DEMO.xAxis.categories = categories;
    $.post(CONTEXT_PATH + "/statRunController/qryRunStat?" + $("#queryForm").serialize(),function(result){
        HISTORY_DATA.DEMO.series = result.series
        if(dimen == "year"){
            HISTORY_DATA.DEMO.yAxis.labels.format =  "{value}天"
            HISTORY_DATA.DEMO.yAxis.max = null
        }else if(dimen == "month"){
            HISTORY_DATA.DEMO.yAxis.labels.format =  "{value}小时"
            HISTORY_DATA.DEMO.yAxis.max = null
        }else if(dimen == "day"){
            HISTORY_DATA.DEMO.yAxis.labels.format =  "{value}分"
            HISTORY_DATA.DEMO.yAxis.max = 60
        }
        HISTORY_DATA.DEMO.xAxis.categories = result.xAxis[0]["data"]
        $("#highcharts-history-data").highcharts(HISTORY_DATA.DEMO);
        LOADING.hide();
    });
    $.post(CONTEXT_PATH + "/statRunController/qryFrequencyStat?" + $("#queryForm").serialize(),function(result){
        var $pieChartDevContent = $("#device-norm-data").find(".list-group");
        $pieChartDevContent.empty();
        var nmberlength = result.length;
        result.forEach(function (element) {
            if(nmberlength <=4){
                var listr = $("<li class='list-group-item col-md-"+ 12/nmberlength +"'><div style='height: 200px'></div></li>");
                $('#device-norm-data').css('overflow-x', 'none');
                $('#device-norm-data').find('.list-group').css('width', "auto");
            }else{
                var listr = $("<li class='list-group-item' style='width: 380px;display: inline-block;'><div style='height: 200px'></div></li>");
                $('#device-norm-data').css('overflow-x', 'scroll');
                $('#device-norm-data').find('.list-group').css('width', nmberlength*380+"px");
            }
            $pieChartDevContent.append(listr)
            var pieDate = deepClone(HISTORY_DATA.PIEDATE)
            pieDate.title.text=element.title
            pieDate.series[0].data = element.data
            if(element.data){
                var statusPie = false;
                element.data.map(function (val) {
                    if(val.y != "0"){
                        statusPie = true
                    }
                })
            }
            if(statusPie){
                listr.find("div").highcharts(pieDate);
            }else{
                listr.find("div").html('<p style="color: #333; font-size: 18px; width: 100%; text-align: center;padding: 5px">'+ element.title+'</p>' +
                    '<p style="color: #333; width: 100%;padding-top: 40px;text-align: center;font-size: 14px">暂无数据</p>')
            }

        })
        $('#device-norm-data .highcharts-container').css('height', '200px !important')
    });
}
function deepClone(obj){
    var result;
    var oClass=isClass(obj);
    if(oClass==="Object"){
        result={};
    }else if(oClass==="Array"){
        result=[];
    }else{
        return obj;
    }
    for(var key in obj){
       var copy=obj[key];
       if(isClass(copy)=="Object"){
           result[key]=arguments.callee(copy);//递归调用
        }else if(isClass(copy)=="Array"){
           result[key]=arguments.callee(copy);
        }else{
            result[key]=obj[key];
        }
    }
 return result;
}
//判断对象的数据类型
function isClass(o){
    if(o===null) return "Null";
    if(o===undefined) return "Undefined";
    return Object.prototype.toString.call(o).slice(8,-1);
}
/**
 * delete global variable and prevent conflict function and variable
 * @param refresh should leave current page,if not it'll abort pending ajax request
 */
function doClean(refresh) {
    if (HISTORY_DATA) {
        if (HISTORY_DATA.preRequest && HISTORY_DATA.preRequest.readyState !== 4) {
            HISTORY_DATA.preRequest.abort();
        }
        if (refresh) {
            delete HISTORY_DATA;
        }
    }
}
