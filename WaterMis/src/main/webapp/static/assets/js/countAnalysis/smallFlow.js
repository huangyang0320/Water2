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
        type: 'column',
        animation: Highcharts.svg, // don't animate in old IE
        marginTop: 50,
        //spacingTop: 50,
        backgroundColor: {
            linearGradient: { x1: 0, y1: 0, x2: 1, y2: 1 },
            stops: [
                [0, 'rgb(255, 255, 255)'],
                [1, 'rgb(240, 240, 255)']
            ]
        },
        borderWidth: 1,
        borderColor: '#ddd',
        plotBackgroundColor: 'rgba(255, 255, 255, .9)',
        plotShadow: true,
        plotBorderWidth: 1,
        zoomType: "xy",
        //showAxes: true, //初始化时显示坐标轴
    },
    exporting: {
      enabled: false
    },
    tooltip: {
        enabled: true,
        crosshairs: true,
        shared: true,//
        // formatter: function () {
        //     return "<b>" + this.series.name + "</b><br/>" +
        //         Highcharts.dateFormat("%Y-%m-%d %H:%M:%S", this.x, null) + "<br/>" +
        //         Highcharts.numberFormat(this.y, 3, null, null);
        // }
        formatter: function () {
            var s = this.x + '日' + '<br/>';
            s += '<b>' + this.points[0].series.name + ' : ' + Highcharts.numberFormat(this.points[0].y, 1, null, null) + ' kWh' + '</b><br/>';
            s += '<b>' + this.points[1].series.name + ' : ' + Highcharts.numberFormat(this.points[1].y, 1, null, null) + ' m³' + '</b><br/>';
            return s;
        }
    },
    title: {
        text: "",
        style: {
            color: '#000',
            font: '14px Trebuchet MS, Verdana, Microsoft YaHei, sans-serif'
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
        //tickPixelInterval: 150,
        gridLineWidth: 1, //网格线线条宽度
        lineColor: '#000', //轴线
        tickColor: '#000', //刻度线
        labels: {
            style: {
                color: '#000',
                font: '11px Trebuchet MS, Verdana, Microsoft YaHei, sans-serif'
            }
        },
        tickmarkPlacement: 'on'
    },
    yAxis: [{
        title: {
            text: "耗电量（kWh）",
            style: {
                color: '#333',
                fontSize: '12px',
                fontFamily: 'Trebuchet MS, Verdana, Microsoft YaHei, sans-serif'
            }
        },
        //visible:false,
        minorTickInterval: 'auto',
        //tickPixelInterval: 50,
        lineWidth: 1, //轴线宽度
        lineColor: '#000',
        tickWidth: 1, //刻度线宽度
        tickColor: '#000',
        min: 0, // 这个用来控制y轴的开始刻度（最小刻度），另外还有一个表示最大刻度的max属性
        //startOnTick: false, // y轴坐标是否从某一刻度开始（这个设定与标题无关）
        labels: {
            style: {
                color: '#000',
                font: '11px Trebuchet MS, Verdana, Microsoft YaHei, sans-serif'
            },
            format: "{value}"
        }
    },{
        title: {
            text: "流量（m³）",
            style: {
                color: '#333',
                fontSize: '12px',
                fontFamily: 'Trebuchet MS, Verdana, Microsoft YaHei, sans-serif'
            }
        },
        min: 0,
        lineWidth: 1,
        lineColor: '#000',
        tickWidth: 1,
        tickColor: '#000',
        opposite: true //是否将坐标轴显示在对立面
    }],
    legend: {
        maxHeight: 80
    },
    lang: {
        noData: "没有数据，请修改查询条件....." //真正显示的文本
    },
    noData: {
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

pageLoading(initData)
rightMenuMsg();
/**
 * 数据的初始化
 */
function initData() {
    preLoad();
    var currentDate = new Date().Format("yyyy-MM-dd");
    var dateRegion = new Date().getCurrentDay(currentDate, "yyyy-MM-dd HH:mm:ss");
    var selectDevice = GLOBAL_SELECT_DEVICE;
    initQueryBox(currentDate);
    var $queryForm = $("#queryForm");
    $queryForm.find("input[name='dimen']").val("day");
    $queryForm.find("input[name='startDate']").val(dateRegion[0]);
    $queryForm.find("input[name='endDate']").val(dateRegion[1]);
    $queryForm.find("input[name='addOnDate']").val(0);
    $queryForm.find("input[name='deviceId']").val(selectDevice.id);
    $("#statisticDate").change();
}

function preLoad() {
    initLayout();
    $("#highcharts-history-data").highcharts(HISTORY_DATA.DEMO)
}

function initQueryBox(currentDate) {
    var $queryForm = $("#queryForm");
    var $formDate = $(".form_datetime");

    $formDate.datetimepicker(DATE_CONFIG(2, 2, "yyyy-mm-dd")).val(currentDate);
    //DATE_ADD_CONFIG("hourtime");

    $("#statisticDate").on("change", function() {
        var $timeDimen = $queryForm.find("input[name='dimen']");
        var $startDate = $queryForm.find("input[name='startDate']");
        var $endDate = $queryForm.find("input[name='endDate']");
        var dateArray = [];
        if ($timeDimen.val() == "day") {
            dateArray = new Date().getCurrentDay($(this).val(), "yyyy-MM-dd HH:mm:ss");
        } else if ($timeDimen.val() == "month") {
            dateArray = new Date().getCurrentMonth($(this).val(), "yyyy-MM-dd HH:mm:ss");
        } else {
            dateArray = new Date().getCurrentYear($(this).val(), "yyyy-MM-dd HH:mm:ss");
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
        //DATE_ADD_CONFIG(className);
        var formDate = $(".form_datetime");
        formDate.datetimepicker("remove");

        var $queryForm = $("#queryForm");
        var $timeDimen = $queryForm.find("input[name='dimen']");

        // if(className.indexOf("hourtime") > -1){// 时
        //     formDate.datetimepicker(DATE_CONFIG(1, 1, "yyyy-mm-dd hh")).val(currentDate.Format("yyyy-MM-dd HH"));
        //     formDate.datetimepicker("update");
        //     $timeDimen.val("hour");
        // } else
        if(className.indexOf("daytime") > -1){// 日
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
        //$queryForm.find("input[name='addOnDate']").val(0);
        $("#statisticDate").change();
    });

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

function initChart(){
    LOADING.show();
    //var addOnTime =  $("#queryForm").find("input[name='addOnDate']").val();
    var dimen = $("#queryForm").find("input[name='dimen']").val();
    HISTORY_DATA.DEMO.title.text = "小流量保压状态";
    HISTORY_DATA.DEMO.xAxis.labels.formatter = DEVICE_DIMEN()[dimen]["formatter"];
    HISTORY_DATA.DEMO.xAxis.categories = DEVICE_DIMEN()[dimen]["categories"];
    //HISTORY_DATA.DEMO.exporting.filename =  "小流量保压-"+$("#queryForm").find("input[name='endDate']").val();
    $.post(CONTEXT_PATH + "/smallFlow/getSmallFlow?" + $("#queryForm").serialize(),function(result){
        HISTORY_DATA.DEMO.series = result;
        $("#highcharts-history-data").highcharts(HISTORY_DATA.DEMO);
        LOADING.hide();
    });
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
    // 展示当前所选设备

}
