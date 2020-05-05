/*
 * energyanalysis.js
 */
HISTORY_DATA = {};

HISTORY_DATA.isBoolVar = false;
HISTORY_DATA.maxPoint = [];
HISTORY_DATA.minPoint = [];
var listType = '1';
var metaDeviceList = null;
HISTORY_DATA.DEMO = {
    colors: ["#50B432", "#ED561B", "#DDDF00", "#24CBE5", "#64E572", "#FF9655", "#FFF263", "#6AF9C4"],
    chart: {
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
        formatter: function () {
            return "<b>" + this.series.name + "</b><br/>" +
                Highcharts.dateFormat("%Y-%m-%d %H:%M:%S", this.x, null) + "<br/>" +
                Highcharts.numberFormat(this.y, 3, null, null);
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
        type: 'datetime',
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
            text: "",
            style: {
                color: '#333',
                fontSize: '12px',
                fontFamily: 'Trebuchet MS, Verdana, Microsoft YaHei, sans-serif'
            }
        },
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
            format: "{value}Mpa"
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



ENERGY_ANALYSIS = {};
ENERGY_ANALYSIS.CHART_OPTION = {
		chart: {
	        zoomType: 'x'
	    },
		xAxis: {
            categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
        },
        yAxis:[{
            lineWidth : 1,
            title:{
                text :'y1'
            }
        },{
         title:{
                text :'y2'
            },
            lineWidth : 1,
            opposite:true
        }],
        series: [{
            data: [1,2,3,4,5,6,7,8,9],
            name: 'AA',
            yAxis:0
        }, {
            data: [4,1,5,8,7,10,13,11,11],
            yAxis:1,
            name: 'BB'
        }, {
            data: [9,10,11,12,13,14,15,16,17],
            step: 'left',
            yAxis:1,
            name: 'CC'
        }]
};
ENERGY_ANALYSIS.CHART_CONFIG = {
    colors: ['#058DC7', '#50B432', '#ED561B', '#DDDF00', '#24CBE5', '#64E572', '#FF9655', '#FFF263', '#6AF9C4'],
    chart: {
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
    title: {
        text: '用水/用电/(千)吨水耗电量分析',
        style: {
            color: '#333',
            fontWeight: 'bold',
            fontSize: '12px',
            fontFamily: 'Trebuchet MS, Verdana, Microsoft YaHei'
        }
    },
    xAxis: {
        crosshair: true,
        gridLineWidth: 1,
        lineColor: '#000',
        tickColor: '#000',
        labels: {
            style: {
                color: '#000',
                font: '11px Trebuchet MS, Verdana, Microsoft YaHei'
            }
        },
        title: {
            style: {
                color: '#333',
                fontWeight: 'bold',
                fontSize: '12px',
                fontFamily: 'Trebuchet MS, Verdana, Microsoft YaHei'
            }
        },
        tickmarkPlacement: 'on',
        categories: ["0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23"]
    },
    yAxis: [
        {
            labels: {
                format: "{value}吨"
            },
            lineWidth: 2,
            lineColor: '#058DC7',
            title: {
                text: '用水量',
                style: {
                    color: '#333',
                    fontWeight: 'bold',
                    fontSize: '12px',
                    fontFamily: 'Trebuchet MS, Verdana, Microsoft YaHei'
                }
            }
        },
        {
            labels: {
                format: "{value}度"
            },
            lineWidth: 2,
            opposite: true,
            lineColor: '#50B432',
            title: {
                text: '用电量',
                style: {
                    color: '#333',
                    fontWeight: 'bold',
                    fontSize: '12px',
                    fontFamily: 'Trebuchet MS, Verdana, Microsoft YaHei'
                }
            }
        },
        {
            labels: {
                format: "{value}度/千吨"
            },
            lineWidth: 2,
            opposite: true,
            lineColor: '#ED561B',
            title: {
                text: '千吨水耗电量',
                style: {
                    color: '#333',
                    fontWeight: 'bold',
                    fontSize: '12px',
                    fontFamily: 'Trebuchet MS, Verdana, Microsoft YaHei'
                }
            }
        },
        {
            labels: {
                format: "{value}度/吨"
            },
            lineWidth: 2,
            opposite: true,
            lineColor: '#DDDF00',
            title: {
                text: '吨水耗电量',
                style: {
                    color: '#333',
                    fontWeight: 'bold',
                    fontSize: '12px',
                    fontFamily: 'Trebuchet MS, Verdana, Microsoft YaHei'
                }
            }
        }
    ],
    tooltip: {
        shared: true
    },
    legend: {
        itemStyle: {
            font: '9pt Trebuchet MS, Verdana, sans-serif',
            color: 'black'

        },
        itemHoverStyle: {
            color: '#039'
        },
        itemHiddenStyle: {
            color: 'gray'
        }
    },
    labels: {
        style: {
            color: '#99b'
        }
    },
    navigation: {
        buttonOptions: {
            theme: {
                stroke: '#CCCCCC'
            }
        }
    },
    series: [
        {
            name: '用水量',
            type: "column",
            yAxis: 0,
            tooltip: {
                valueSuffix: ' 吨'
            }
        }, {
            name: '用电量',
            type: "column",
            yAxis: 1,
            tooltip: {
                valueSuffix: ' 度'
            }
        }, {
            name: '千吨水耗电量',
            type: "spline",
            yAxis: 2,
            tooltip: {
                valueSuffix: ' 度/千吨'
            }
        }, {
            name: '吨水耗电量',
            type: "spline",
            yAxis: 3,
            tooltip: {
                valueSuffix: ' 度/吨'
            }
        }
    ],
    exporting: {
        enabled: false
    },
    credits: {
        enabled: false
    }
};

ENERGY_ANALYSIS.CHART_COMPARE_CONFIG = {
    colors: ['#24CBE5', '#64E572', '#FF9655', '#058DC7', '#50B432', '#ED561B', '#DDDF00', '#FFF263', '#6AF9C4'],
    chart: {
        type: "pie",
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
        plotBorderWidth: 1
    },
    title: {
        text:'吨水耗电量对比分析 (单位：%)',
        style: {
            color: '#000',
            font: 'bold 12px "Trebuchet MS", Verdana, Microsoft YaHei'
        }
    },
    legend: {
        itemStyle: {
            font: '9pt Trebuchet MS, Verdana, Microsoft YaHei',
            color: 'black'

        },
        itemHoverStyle: {
            color: '#039'
        },
        itemHiddenStyle: {
            color: 'gray'
        }
    },
    labels: {
        style: {
            color: '#99b'
        }
    },
    exporting: {
        enabled: false
    },
    tooltip: {
        pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
    },
    navigation: {
        buttonOptions: {
            theme: {
                stroke: '#CCCCCC'
            }
        }
    },
    plotOptions: {
        pie: {
            allowPointSelect: true,
            cursor: 'pointer',
            dataLabels: {
                enabled: false,
                format: '<b>{point.name}</b>: {point.percentage:.1f} %'
            },
            showInLegend: true
        }
    },
    series: [{
        type: 'pie',
        name: '对比结果',
        data: []
    }],
    credits: {
        enabled: false//不显示highCharts版权信息
    }
};

$(function() {
    preLoad();
    var currentDate = new Date().Format("yyyy-MM-dd");
    var $queryForm = $("#queryForm");
    var dateRegion = new Date().getCurrentDay(currentDate, "yyyy-MM-dd HH:mm:ss");
    ENERGY_ANALYSIS.isRendered = true;

    var deviceId = GLOBAL_SELECT_DEVICE["deviceId"];

    $queryForm.find("input[name='deviceId']").val(deviceId);
    $queryForm.find("input[name='dimen']").val("day");
    $queryForm.find("input[name='startDate']").val(dateRegion[0]);
    $queryForm.find("input[name='endDate']").val(dateRegion[1]);
    $queryForm.find("input[name='addOnDate']").val(0);
    callRefresh(false, true);
    LOADING.hide();
    aysncCallBack([
        initQueryBox,
        initDeviceList
    ], [
        currentDate
    ],
    10);

});


function preLoad() {
    initLayout();
    $("#highcharts-history-data").highcharts(HISTORY_DATA.DEMO);
    
}
function callRefresh(device, pre) {
    if (pre !== true) {
        LOADING.show();
    }

    var $queryForm = $("#queryForm");
    var deviceId = device ? device["deviceId"] : $queryForm.find("input[name='deviceId']").val();
    var dimen = $queryForm.find("input[name='dimen']").val();
    var addOnTime = $queryForm.find("input[name='addOnDate']").val();
    var startDate = $("#statisticDate").val();
    var endDate;
    var categories = [];
    var chartType = $("#highcharts-history-data").is(":hidden") ? "diff" : "same";
    $queryForm.find("input[name='deviceId']").val(deviceId);
    getDeviceMeta(deviceId, function (deviceMetas) {
        if (device) {
            initDeviceIndexList(deviceMetas);
        }

        if (pre) {
            categories = DEVICE_DIMEN()[dimen]["categories"];
        } else if (addOnTime && Number(addOnTime) >= 0) {
            doClean(false);

            startDate = new Date().lessThanDate(startDate, Number(addOnTime), dimen);
            $queryForm.find("input[name='startDate']").val(startDate);
            if (chartType == "diff") {
                startDate = $queryForm.find("input[name='startDate']").val();
                endDate = $queryForm.find("input[name='endDate']").val();
                categories = new Date().allDate(startDate, endDate, dimen);
            } else {
                    categories = DEVICE_DIMEN()[dimen]["categories"];
            }
        }

        var deviceIndex = $queryForm.find("input[name='index']").val();
        deviceIndex = "active_power";
        HISTORY_DATA.isBoolVar = deviceMetas[deviceIndex.split(",")[0]]["type"] === "Bool" || deviceMetas[deviceIndex.split(",")[0]]["type"] === "UInt16";

        if (HISTORY_DATA.isBoolVar) {
            delete HISTORY_DATA.DEMO.chart.type;
        } else {
            HISTORY_DATA.DEMO.chart.type = "spline";
        }
        HISTORY_DATA.DEMO.title.text = "";
        HISTORY_DATA.DEMO.xAxis.labels.formatter = DEVICE_DIMEN()[dimen]["formatter"];
        HISTORY_DATA.DEMO.exporting.filename = GLOBAL_SELECT_DEVICE["deviceName"] + "-" + new Date().getTime();
        HISTORY_DATA.DEMO.series = [];
        HISTORY_DATA.DEMO.xAxis.categories = categories;

        HISTORY_DATA.preRequest = $.post(CONTEXT_PATH + "/monitor/statistic/" + deviceId + "/history?" + '?deviceId=17060193&index=active_power,DSEVDelayTimeOnTime&dimen=hour&startDate=2018-04-04+11%3A00%3A00&endDate=2018-04-04+11%3A59%3A59&addOnDate=0', function(result) {
            var category;
            var series = {};
            var data = [];
            var name;
            var i;
            var left = null, median = null, right = null, k = null, l = null, tmp = null;
            if (chartType == "diff") {
                if (!result || result.length ==0) {
                    data = [];
                    category = HISTORY_DATA.DEMO.xAxis.categories;
                    for (i = 0; i < category.length; i++) {
                        data.push(null);
                    }
                    var deviceIndexs = deviceIndex.split(",");

                    for (i = 0; i < deviceIndexs.length; i++) {
                        if (HISTORY_DATA.isBoolVar) {
                            HISTORY_DATA.DEMO.series.push({
                                name: deviceMetas[deviceIndexs[i]]["name"],
                                step: true,
                                data: data
                            });
                        } else {
                            HISTORY_DATA.DEMO.series.push({
                                name: deviceMetas[deviceIndexs[i]]["name"],
                                data: data
                            });
                        }
                    }
                } else {
                    var groupResult = {};
                    for (i = 0; i < result.length; i++) {
                        var queryIndex = result[i]["queryIndex"];
                        if (HISTORY_DATA.isBoolVar && Number(result[i]["queryValue"]) > 0) {
                            result[i]["queryValue"] = 1;
                        }
                        if (!groupResult.hasOwnProperty(queryIndex)) {
                            groupResult[queryIndex] = [];
                        }
                        groupResult[queryIndex].push(result[i]);
                    }
                    for (name in groupResult) {
                        for (var j = 0; j < categories.length; j++) {
                            var queryDate = categories[j];
                            var exist = false;
                            result = groupResult[name];
                            for (i = 0; i < result.length; i++) {
                                if (result[i]["queryDate"].indexOf(queryDate) > -1) {
                                    data.push({
                                        x: queryDate,
                                        y: Number(result[i]["queryValue"].toFixed(3))
                                    });
                                    exist = true;
                                    break;
                                }
                            }
                            if (!exist) {
                                data.push({
                                    x: queryDate,
                                    y: null
                                });
                            }
                        }

                        if (result.length != 1) {
                            left = null;
                            median = null;
                            right = null;
                            for (k = 1; k < data.length; k++) {
                                if (data[k - 1]["y"] !== null && data[k]["y"] === null) {
                                    left = data[k - 1]["y"];
                                    for (l = k + 1; l < data.length; l++) {
                                        if (data[l]["y"] !== null) {
                                            right = data[l]["y"];
                                            median = Number(((left + right) / 2).toFixed(3));
                                            for (tmp = k; tmp < l; tmp++) {
                                                data[tmp]["y"] = median;
                                            }
                                            k = l;
                                            break;
                                        }
                                    }
                                }
                            }
                        }

                        if (HISTORY_DATA.isBoolVar) {
                            HISTORY_DATA.DEMO.series.push({
                                name: deviceMetas[name]["name"],
                                step: true,
                                data: data
                            });
                        } else {
                            HISTORY_DATA.DEMO.series.push({
                                name: deviceMetas[name]["name"],
                                data: data
                            });
                        }
                        data = [];
                    }
                }
            } else {
                if (!result || result.length ==0) {
                    data = [];
                    category = HISTORY_DATA.DEMO.xAxis.categories;
                    for (i = 0; i < category.length; i++) {
                        data.push(null);
                    }

                    if (HISTORY_DATA.isBoolVar) {
                        HISTORY_DATA.DEMO.series.push({
                            name: new Date().formatByDimen($(".form_datetime").val(), dimen),
                            step: true,
                            data: data
                        });
                    } else {
                        HISTORY_DATA.DEMO.series.push({
                            name: new Date().formatByDimen($(".form_datetime").val(), dimen),
                            data: data
                        });
                    }
                } else {
                    for (i = 0; i < result.length; i++) {
                        var key = new Date().filterDate(result[i]["queryDate"], 0, dimen);
                        if (!series.hasOwnProperty(key)) {
                            series[key] = [];
                        }
                        if (HISTORY_DATA.isBoolVar && Number(result[i]["queryValue"]) > 0) {
                            result[i]["queryValue"] = 1;
                        }
                        series[key].push(result[i]);
                    }
                    for (name in series) {
                        var value = series[name];
                        data = [];
                        category = HISTORY_DATA.DEMO.xAxis.categories;
                        category = category.join("|") + "|";
                        for (i = 0; i < value.length; i++) {
                            var x = Number(new Date().getDateKey(value[i]["queryDate"], dimen));
                            category = category.replace(x + "|", "");
                            data.push({
                                x: x,
                                y:  Number(series[name][i]["queryValue"].toFixed(3))
                            })
                        }
                        if (category && category != "") {
                            var emptyDate = category.split("|");
                            for (i = 0; i < emptyDate.length - 1; i++) {
                                data.push({
                                    x: emptyDate[i],
                                    y: null
                                })
                            }
                        }
                        data = data.sort(function (o, p) {
                            return Number(o["x"]) < Number(p["x"]) ? -1 : 1;
                        }).map(function (item) {
                            return item["y"];
                        });


                        if (result.length != 1) {
                            left = null;
                            median = null;
                            right = null;

                            for (k = 1; k < data.length; k++) {
                                if (data[k - 1] !== null && data[k] === null) {
                                    left = data[k - 1];
                                    for (l = k + 1; l < data.length; l++) {
                                        if (data[l] !== null) {
                                            right = data[l];
                                            median = Number(((left + right) / 2).toFixed(3));
                                            for (tmp = k; tmp < l; tmp++) {
                                                data[tmp] = median;
                                            }
                                            k = l;
                                            break;
                                        }
                                    }
                                }
                            }
                        }

                        if (HISTORY_DATA.isBoolVar) {
                            HISTORY_DATA.DEMO.series.push({
                                name: new Date().formatByDimen(name, dimen),
                                step: true,
                                data: data
                            });
                        } else {
                            HISTORY_DATA.DEMO.series.push({
                                name: new Date().formatByDimen(name, dimen),
                                data: data
                            });
                        }
                    }
                }
            }

            if (chartType == "diff") {
                HISTORY_DATA.DEMO.yAxis.labels = {
                    format: "{value}" + deviceMetas[deviceIndex.split(",")[0]]["unit"]
                };
                HISTORY_DATA.DEMO.tooltip = DEVICE_DIMEN(deviceMetas[deviceIndex.split(",")[0]]["unit"])[dimen]["tooltip"];
                HISTORY_DATA.DEMO.exporting.filename += "-环比曲线";
            } else {
                // title = deviceMetas[deviceIndex]["memo"];
                HISTORY_DATA.DEMO.yAxis.labels = {
                    format: "{value}" + deviceMetas[deviceIndex]["unit"]
                };
                HISTORY_DATA.DEMO.tooltip = DEVICE_DIMEN(deviceMetas[deviceIndex]["unit"])[dimen]["tooltip"];
                HISTORY_DATA.DEMO.exporting.filename += "-同比曲线";
            }

            var currentChart = null;
            if (chartType == "same") {
                /* 同比曲线 */
                currentChart = $("#highcharts-history-data").highcharts(ENERGY_ANALYSIS.CHART_OPTION);
            } else {
                /* 反比曲线 */
                currentChart = $("#highcharts-history-data2").highcharts(ENERGY_ANALYSIS.CHART_OPTION);
            }
            showMaxMinTip(currentChart.highcharts());
            
            setTimeout(function() {
                LOADING.hide();
            }, 500);
        });
    });
}

function initQueryBox(currentDate) {
    var $queryForm = $("#queryForm");
    var $formDate = $(".form_datetime");

    $formDate.datetimepicker(DATE_CONFIG(2, 2, "yyyy-mm-dd")).val(currentDate);
    DATE_ADD_CONFIG("daytime");

    $("#statisticDate").on("change", function() {
        var value = $(this).val();
        var $queryForm = $("#queryForm");
        var $timeDimen = $queryForm.find("input[name='dimen']");
        var $startDate = $queryForm.find("input[name='startDate']");
        var $endDate = $queryForm.find("input[name='endDate']");
        var dateArray = [];

        if ($timeDimen.val() == "day") {
            dateArray = new Date().getCurrentDay(value, "yyyy-MM-dd HH:00:00");
        } else if ($timeDimen.val() == "month") {
            dateArray = new Date().getCurrentMonth(value, "yyyy-MM-dd 00:00:00");
        } else {
            dateArray = new Date().getCurrentYear(value, "yyyy-MM-1 00:00:00");
        }

        $startDate.val(dateArray[0]);
        $endDate.val(dateArray[1]);
        callRefresh();
    });

    $("#addOnTime").on("change", function () {
        $queryForm.find("input[name='addOnDate']").val($(this).val());
        callRefresh();
    });

    /* 日历插件 */
    $("#time-length").find("span").on("click", function(){
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
        var $timeDimen = $queryForm.find("input[name='dimen']");

        if(className.indexOf("daytime") > -1){// 日
            formDate.datetimepicker(DATE_CONFIG(2, 2, "yyyy-mm-dd")).val(new Date().Format("yyyy-MM-dd"));
            formDate.datetimepicker("update");
            $timeDimen.val("day");
        } else if(className.indexOf("monthtime") > -1){// 月
            formDate.datetimepicker(DATE_CONFIG(3, 3, "yyyy-mm")).val(new Date().Format("yyyy-MM"));
            formDate.datetimepicker("update");
            $timeDimen.val("month");

        } else if(className.indexOf("yeartime") > -1){// 年
            formDate.datetimepicker(DATE_CONFIG(4, 4, "yyyy")).val(new Date().Format("yyyy"));
            formDate.datetimepicker("update");
            $timeDimen.val("year");
        } else {
            return false;
        }
        $queryForm.find("input[name='addOnDate']").val(0);
        $("#statisticDate").change();
    });

    $(".device-btn").on("click", function(event){
        var _span = $(this).find("span");
        var _i = $(this).find("i");
        if(_span.attr("class") == "device-choose") {
        	$("#deviceCodes").slideUp();
        	
            _span.text("确定 ");
            _span.attr("class", "sure-choose");
            _i.attr("class", "fa fa-angle-double-up");
            $("#device-menu").slideDown();
        } else {
        	$("#deviceCodes").slideDown();
            $("body").unbind();
            _span.text("请选择设备 ");
            _span.attr("class", "device-choose");
            _i.attr("class", "fa fa-angle-double-down");
            $("#device-menu").slideUp();
            reloadDiffDeviceChart(true);
        }

        event.stopPropagation();
        event.preventDefault();
        return false;
    });
}

function initDeviceList() {
    var $deviceMenu = $("#device-menu");
    /* bootstrap 列表组 设备列表 */
    $deviceMenu.empty();

    var device = GLOBAL_SELECT_DEVICE;
    $deviceMenu.empty();
    $deviceMenu = $deviceMenu.append(GLOBAL.deviceMenu);

    $deviceMenu.find("input[value='" + GLOBAL_SELECT_DEVICE["deviceId"] + "']").eq(0).prop("checked", true);
}

function initLayout() {
    var $deviceMenu = $("#device-menu");
    // 初始化无滚动条
    $("#page-wrapper").css("overflow-y", "hidden");
    // 计算高度
    var calcHeight = function() {
        var height = $(window).height() - $(".chart-time").height() - 116;
        $("#highcharts-history-data").height(height+3);
        $deviceMenu.height(height + 70);
        var width = $(".bar-chart-title").width();
        $deviceMenu.width(width + 7);
    };
    calcHeight();
    $(window).resize(function() {
        calcHeight();
    });

    // 展示当前所选设备
    //
}

function callRefreshs(device, pre) {
    LOADING.show();
    var $queryForm = $("#queryForm");
    var $deviceMenu = $("#device-menu");

    var deviceId, dimen, addOnTime, startDate, endDate, categories;

    dimen = $queryForm.find("input[name='dimen']").val();
    addOnTime = $queryForm.find("input[name='addOnDate']").val();
    startDate = $("#statisticDate").val();

    doClean(false);

    if (device) {

        deviceId = device["deviceId"];
        $deviceMenu.find("input").each(function () {
            // $(this).icheck("unchecked")
            $(this).prop("checked", false);
        });
        $queryForm.find("input[name='deviceIds']").val(deviceId);
        $deviceMenu.find("input[value='" + deviceId + "']").prop("checked", true);
    } else if (pre) {
        deviceId = $queryForm.find("input[name='deviceId']").val();
        $queryForm.find("input[name='deviceIds']").val(deviceId);
        //categories = DEVICE_DIMEN()[dimen]["categories"];
        categories = new Date().allDate(getStartDate(), getEndDate(), dimen);
    } else {
        deviceId = $queryForm.find("input[name='deviceId']").val();
        if (addOnTime && Number(addOnTime) >= 0) {
            startDate = new Date().lessThanDate(startDate, Number(addOnTime), dimen);
            $queryForm.find("input[name='startDate']").val(startDate);
            endDate = $queryForm.find("input[name='endDate']").val();
            categories = new Date().allDate(startDate, endDate, dimen);
        } else {
            categories = DEVICE_DIMEN()[dimen]["categories"];
        }
    }

    $queryForm.find("input[name='deviceId']").val(deviceId);

    GLOBAL.getDeviceConsumeMeta(deviceId, [function (deviceConsumeMeta) {
        if (deviceConsumeMeta) {
            $queryForm.find("input[name='deviceIndexs']").val(GLOBAL.deviceConsumeMeta[deviceId].join(","));
            reloadDeviceConsume(deviceId, categories, $queryForm.serialize());
            reloadDiffDeviceChart();
        } else {
            setTimeout(function() {
                LOADING.hide();
            }, 500);
        }
    }]);
}
function getStartDate() {
	var date = new Date();
    var seperator1 = "-";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate + " 00:00:00"
    return currentdate;
}
function getEndDate() {
	var date = new Date();
	date.setDate(date.getDate()+1)
    var seperator1 = "-";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate + " 00:00:00"
    return currentdate;
}
function reloadDeviceConsume(deviceId, categories, queryParams) {
    ENERGY_ANALYSIS.preRequest = $.post(CONTEXT_PATH + "/monitor/statistic/consume?" + queryParams, function(result) {
        if (categories) {
            ENERGY_ANALYSIS.CHART_CONFIG.xAxis.categories = categories;
        } else {
            categories = ENERGY_ANALYSIS.CHART_CONFIG.xAxis.categories;
        }
        var i = 0;
        var name;
        var data = [];
        var activePower, cumulativeFlow;

        ENERGY_ANALYSIS.CHART_CONFIG.series[1].data = null;
        ENERGY_ANALYSIS.CHART_CONFIG.series[0].data = null;

        if (result && result.length > 0) {

            var groupResult = {};
            for (i = 0; i < result.length; i++) {
                var queryIndex = result[i]["queryIndex"] + result[i]["queryName"];
                if (!groupResult.hasOwnProperty(queryIndex)) {
                    groupResult[queryIndex] = [];
                }
                groupResult[queryIndex].push(result[i]);
            }

            var multiFlowResult;
            var multiPowerResult;
            var tmpResult;
            for (name in groupResult) {
                if (name.indexOf("流量") > -1) {
                    tmpResult = groupResult[name];
                    if (multiFlowResult) {
                        for (i = 0; i < tmpResult.length; i++) {
                            if (multiFlowResult[i] && tmpResult[i]) {
                                multiFlowResult[i]["queryValue"] += tmpResult[i]["queryValue"];
                            }
                        }
                    } else {
                        multiFlowResult = tmpResult;
                    }
                    delete groupResult[name];
                }

                if (name.indexOf("电") > -1 || name.indexOf("功耗") > -1) {
                    tmpResult = groupResult[name];
                    if (multiPowerResult) {
                        for (i = 0; i < tmpResult.length; i++) {
                            if (multiPowerResult[i] && tmpResult[i]) {
                                multiPowerResult[i]["queryValue"] += tmpResult[i]["queryValue"];
                            }
                        }
                    } else {
                        multiPowerResult = tmpResult;
                    }
                    delete groupResult[name];
                }
            }

            groupResult["累计流量"] = multiFlowResult;
            groupResult["累计电量"] = multiPowerResult;
            for (name in groupResult) {
                for (var j = 0; j < categories.length; j++) {
                    var queryDate = categories[j];
                    var exist = false;
                    result = groupResult[name];
                    if (result && result.length > 0) {
                    	 for (i = 0; i < result.length; i++) {
                             if (result[i]["queryDate"].indexOf(queryDate) > -1) {
                                 data.push({
                                     x: result[i]["queryDate"],
                                     y: Number(result[i]["queryValue"].toFixed(3))
                                 });
                                 exist = true;
                                 break;
                             }
                         }
                    }
                   
                    if (!exist) {
                        data.push({
                            x: queryDate,
                            y: null
                        });
                    }
                }

                if (name.indexOf("电") > -1 || name.indexOf("功耗") > -1) {
                    ENERGY_ANALYSIS.CHART_CONFIG.series[1].data = data;
                } else if (name.indexOf("流量") > -1) {
                    ENERGY_ANALYSIS.CHART_CONFIG.series[0].data = data;
                }
                data = [];
            }

            cumulativeFlow = ENERGY_ANALYSIS.CHART_CONFIG.series[0].data;
            activePower = ENERGY_ANALYSIS.CHART_CONFIG.series[1].data;

            for (i = 0; i < categories.length; i++) {
                data.push({
                    x: categories[i],
                    y: null
                });
            }

            activePower = activePower ? activePower : data;
            cumulativeFlow = cumulativeFlow ? cumulativeFlow : data;
        } else {
            for (i = 0; i < categories.length; i++) {
                data.push({
                    x: categories[i],
                    y: null
                });
            }

            ENERGY_ANALYSIS.CHART_CONFIG.series[0].data = data;
            ENERGY_ANALYSIS.CHART_CONFIG.series[1].data = data;
            activePower = data;
            cumulativeFlow = data;
        }

        data = [];
        for (i = 0; i < categories.length; i++) {
            if (!cumulativeFlow || !cumulativeFlow[i]["y"] || Number(cumulativeFlow[i]["y"]) == 0) {
                data.push({
                    x: categories[i],
                    y: null
                })
            } else {
                data.push({
                    x: categories[i],
                    y: Number((Number(activePower[i]["y"]) / Number(cumulativeFlow[i]["y"])).toFixed(3)) * 1000
                })
            }
        }
        ENERGY_ANALYSIS.CHART_CONFIG.series[2].data = data;
        data = [];
        for (i = 0; i < categories.length; i++) {
            if (!cumulativeFlow || !cumulativeFlow[i]["y"] || Number(cumulativeFlow[i]["y"]) == 0) {
                data.push({
                    x: categories[i],
                    y: null
                })
            } else {
                data.push({
                    x: categories[i],
                    y: Number((Number(activePower[i]["y"]) / Number(cumulativeFlow[i]["y"])).toFixed(3))
                })
            }
        }
        ENERGY_ANALYSIS.CHART_CONFIG.series[3].data = data;
        /* 能耗分析折线图 */
        alert(11);
        $("#energyanalysis-chart").highcharts(ENERGY_ANALYSIS.CHART_OPTION);
    });
}

function reloadDiffDeviceChart(loading) {
    if (loading) {
        LOADING.show();
    }
    var $queryForm = $("#queryForm");
    var deviceIdStr = $("#device-menu").find("input[name='deviceId']").serialize();
    var deviceId = $queryForm.find("input[name='deviceId']").val();
    var deviceIds = deviceIdStr.replace(/deviceId=/g, "").replace(/&/g, ",");
    if('' == deviceIds || null == deviceIds){
    	deviceIds = GLOBAL_SELECT_DEVICE["deviceId"];
    }
    $queryForm.find("input[name='deviceIds']").val(deviceIds);

    GLOBAL.getDeviceConsumeMeta(deviceIds, [function () {

        var deviceIdArray = deviceIds.split(",");
        var tmpId = {};
        var tmpIndexs;

        deviceIdArray.map(function(device) {
            tmpIndexs = GLOBAL.deviceConsumeMeta[device];
            if (tmpIndexs) {
                tmpIndexs.map(function (item) {
                    tmpId[item] = item;
                });
            }
        });

        if (tmpId) {
            tmpId = Object.keys(tmpId);
            $queryForm.find("input[name='diffDeviceIndexs']").val(tmpId.join(","));
        }


        ENERGY_ANALYSIS.preRequestDiff = $.post(CONTEXT_PATH + "/monitor/statistic/diffDevice/consume?" + $queryForm.serialize(), function (result) {
            var chartData = [];

            if (result && $.trim(result) != "" && result.length > 0) {
                var data = {};
                var i;

                for (var j = 0; j < deviceIdArray.length; j++) {
                    tmpId = deviceIdArray[j];

                    var tmpDeviceConsumeMeta = GLOBAL.deviceConsumeMeta[tmpId];

                    if (tmpDeviceConsumeMeta) {

                        tmpDeviceConsumeMeta = tmpDeviceConsumeMeta.join(",");
                        for (i = 0; i < result.length; i++) {

                            if (tmpDeviceConsumeMeta.indexOf(result[i]["queryId"]) > -1) {
                                if (!data.hasOwnProperty(tmpId)) {

                                    var deviceName = tmpId;
                                    if (tmpId === GLOBAL_SELECT_DEVICE["deviceId"]) {
                                        deviceName = GLOBAL_SELECT_DEVICE["deviceName"];
                                    } else {
                                        var deviceMenuId = "#deviceMenu" + deviceName;

                                        if ($(deviceMenuId).length > 0) {
                                            deviceName = $(deviceMenuId).attr("data-device-name");
                                        }
                                    }

                                    data[tmpId] = {
                                        "name": deviceName,
                                        "flow": 0,
                                        "energy": 0,
                                        "getRate": function () {
                                            if (this["flow"] > 0 && this["energy"] > 0) {
                                                return (Number(this["energy"]) / Number(this["flow"])).toFixed(3) * 1000;
                                            } else {
                                                return "-";
                                            }
                                        }
                                    };
                                }

                                if (result[i]["queryName"].indexOf("流量") > -1) {
                                    data[tmpId]["flow"] = Number(Number(result[i]["queryValue"]) + Number(data[tmpId]["flow"])).toFixed(3);
                                }

                                if (result[i]["queryName"].indexOf("电") > -1 || result[i]["queryName"].indexOf("功耗") > -1) {
                                    data[tmpId]["energy"] = Number(Number(result[i]["queryValue"]) + Number(data[tmpId]["energy"])).toFixed(3);
                                }

                            }

                        }
                    }
                }

                if (data[deviceId]) {
                    $(".cumulativeFlow").text(data[deviceId]["flow"]);
                    $(".activePower").text(data[deviceId]["energy"]);
                    $(".flowEnergyRate").text(data[deviceId].getRate());
                } else {
                    $(".cumulativeFlow").text("-");
                    $(".activePower").text("-");
                    $(".flowEnergyRate").text("-");
                }

                for (var name in data) {
                    chartData.push({
                        "name": data[name]["name"],
                        "y": isNaN(data[name].getRate()) ? null : Number(data[name].getRate())
                    });
                }

            } else {
                $(".cumulativeFlow").text("-");
                $(".activePower").text("-");
                $(".flowEnergyRate").text("-");
            }

            ENERGY_ANALYSIS.CHART_COMPARE_CONFIG.series[0].data = chartData;

            /* 各设备对比3D饼图 */
            $("#energyanalysis-compare").highcharts(ENERGY_ANALYSIS.CHART_COMPARE_CONFIG);

            setTimeout(function() {
                LOADING.hide();
            }, 500);
        });

    }]);
}

/**
 * delete global variable and prevent conflict function and variable
 * @param refresh should leave current page,if not it'll abort pending ajax request
 */
function doClean(refresh) {
    $("body").unbind();
    if (ENERGY_ANALYSIS) {
        if (ENERGY_ANALYSIS.preRequest) {
            ENERGY_ANALYSIS.preRequest.abort();
        }

        if (ENERGY_ANALYSIS.preRequestDiff) {
            ENERGY_ANALYSIS.preRequestDiff.abort();
        }
        if (refresh) {
            delete ENERGY_ANALYSIS;
        }
    }
}