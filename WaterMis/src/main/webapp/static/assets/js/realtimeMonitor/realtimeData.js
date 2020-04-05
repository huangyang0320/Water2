/*
 * realtimeData.js
 * Generate chart
 */
REALTIME_DATA = {};

REALTIME_DATA.MOCK = 1;
REALTIME_DATA.MOCK_DATE = new Date(new Date().getTime() - REALDATA_DELAY_TIME).Format("yyyy/MM/dd HH:mm:ss");
REALTIME_DATA.CHART_INTERVAL = {};
REALTIME_DATA.isBoolVar = false;
REALTIME_DATA.maxPoint = [];
REALTIME_DATA.minPoint = [];

// 基本配置
REALTIME_DATA.GAUGE_OPTIONS = {
    chart: {
        type: 'solidgauge'
    },
    pane: {
        center: ['50%', '90%'],
        size: '180%',
        startAngle: -90,
        endAngle: 90,
        background: {
            backgroundColor: (Highcharts.theme && Highcharts.theme.background2) || '#EEE',
            innerRadius: '60%',
            outerRadius: '100%',
            shape: 'arc'
        }
    },
    exporting: {
        enabled: false
    },
    tooltip: {
        enabled: false
    },
    // the value axis
    yAxis: {
        stops: [
            [0.1, '#55BF3B'], // green
            [0.5, '#DDDF0D'], // yellow
            [0.9, '#DF5353'] // red
        ],
        lineWidth: 0,
        minorTickInterval: null,
        tickPixelInterval: 400,
        tickWidth: 0,
        title: {
            y: -70
        },
        min: 0, // 这个用来控制y轴的开始刻度（最小刻度），另外还有一个表示最大刻度的max属性
        startOnTick: false, // y轴坐标是否从某一刻度开始（这个设定与标题无关）
        labels: {
            y: 16,
            style: {
                color: '#fff'
            }
        }
    },
    plotOptions: {
        solidgauge: {
            dataLabels: {
                y: 5,
                borderWidth: 0,
                useHTML: true
            }
        }
    },
    credits: {
        enabled: false
    }
};

REALTIME_DATA.dynamicUpdateDeviceData = function (series) {
    var queryDate = new Date(REALTIME_DATA.MOCK_DATE);
    var $queryForm = $("#queryForm");
    var deviceId = $queryForm.find("input[name='deviceId']").val();
    var deviceIndex = $queryForm.find("input[name='index']").val();
    REALTIME_DATA.MOCK += 5;
    queryDate.setSeconds(queryDate.getSeconds() + REALTIME_DATA.MOCK);
    queryDate = queryDate.Format("yyyy-MM-dd HH:mm:ss");
    $.post(CONTEXT_PATH + "/monitor/statistic/" + deviceId + "/realtime?date=" + queryDate, function (result) {
        var $realtimeDatalist = $("#realtime-datalist");
        if ($realtimeDatalist.length == 0 && !REALTIME_DATA.CHART_INTERVAL) {
            clearInterval(REALTIME_DATA.CHART_INTERVAL);
            return;
        }
        if (!series.series) {
            return;
        }
        result = result["realTimeData"];
        var $charPanel = $(".easypiechart-panel");
        var chartConfig, point, i;
        var dataList = {};
        if (result && $.trim(result) != "") {
            var results = result.split(",");
            for (i = 0; i < results.length; i++) {
                var data = results[i].split("|");
                if (dataList.hasOwnProperty(data[0])) {
                    continue;
                }
                dataList[data[0]] = Number(Number(data[1]).toFixed(3));
            }

            $realtimeDatalist.find(".deviceIndex").each(function () {
                var code = $(this).attr("data-device-index");
                if (dataList.hasOwnProperty(code)) {
                    $(this).text(Number(Number(dataList[code]).toFixed(3)));
                } else {
                    dataList[code] = Number($(this).text());
                }
            });
        } else {
            $realtimeDatalist.find(".deviceIndex").each(function () {
                var code = $(this).attr("data-device-index");
                dataList[code] = Number($(this).text());
            });
        }
        $realtimeDatalist.find(".queryDate").each(function () {
            $(this).text(queryDate);
        });

        if ($($charPanel[0]).attr("data-index")) {
            for (i = 0; i < $charPanel.length; i++) {
                var indexTmp = $($charPanel[i]).attr("data-index").split(/\s+/)[1];
                chartConfig = $($charPanel[i]).highcharts();
                point = chartConfig.series[0].points[0];
                if (dataList[indexTmp]) {
                    point.update(dataList[indexTmp]);
                } else {
                    point.update(0);
                }
            }

            var deviceIndexArray = deviceIndex.split(",");
            var x, y;
            if (deviceIndexArray && deviceIndexArray.length == 1) {
                x = new Date(queryDate.replace(/-/g, "/")).getTime(); // current time
                y = (dataList == undefined || isNaN(dataList[deviceIndex])) ? 0 : dataList[deviceIndex];
                series.series[0].addPoint([x, Number(y)], true, true, true);
            } else {
                for (i = 0; i < deviceIndexArray.length; i++) {
                    x = new Date(queryDate.replace(/-/g, "/")).getTime(); // current time
                    y = (dataList == undefined || isNaN(dataList[deviceIndexArray[i]]))
                        ? 0 : dataList[deviceIndexArray[i]];
                    series.series[i].addPoint([x, y], true, true, true);
                }
            }

            for (var k = 0; k < series.series.length && !REALTIME_DATA.isBoolVar; k++) {
                var pointCount = series.series[k].data.length;
                var maxValue = series.series[k].dataMax;
                var minValue = series.series[k].dataMin;

                var points = series.series[k].data;
                var defaultGraphic = points[pointCount - 1].graphic;
                var defaultMark = {
                    symbol: defaultGraphic.symbolName,
                    lineColor: defaultGraphic.lineColor,
                    radius: defaultGraphic.radius

                };

                var maxFlag = true;
                var minFlag = true;
                if(maxValue != minValue){
                    for (i = 60; i >= 0; i--) {
                        if (maxFlag && points[i] && points[i].y == maxValue) {
                            maxFlag = false;
                            if (REALTIME_DATA.maxPoint[k] && REALTIME_DATA.maxPoint[k].series) {
                                REALTIME_DATA.maxPoint[k].update({
                                    marker: defaultMark
                                })
                            }
                            REALTIME_DATA.maxPoint[k] = points[i];
                            REALTIME_DATA.maxPoint[k].update({
                                marker: {
                                    symbol: 'url('+ROOT_PATH+'/static/assets/img/max.png)'
                                }
                            });
                        } else if (minFlag && points[i] && points[i].y == minValue) {
                            minFlag = false;
                            if (REALTIME_DATA.minPoint[k] && REALTIME_DATA.minPoint[k].series) {
                                REALTIME_DATA.minPoint[k].update({
                                    marker: defaultMark
                                })
                            }
                            REALTIME_DATA.minPoint[k] = points[i];
                            REALTIME_DATA.minPoint[k].update({
                                marker: {
                                    symbol: 'url('+ROOT_PATH+'/static/assets/img/min.png)'
                                }
                            });
                        } else if (!maxFlag && !minFlag) {
                            break;
                        }
                    }
                }
            }
        } else {
            $(this).abort();
        }
    });
};

REALTIME_DATA.CONFIG = {
    colors: ['#058DC7', '#50B432', '#ED561B', '#DDDF00', '#24CBE5', '#64E572', '#FF9655', '#FFF263', '#6AF9C4'],
    chart: {
        type: "area",
        animation: Highcharts.svg,
        marginRight: 10,
        zoomType: "xy",
        backgroundColor: {
            linearGradient: {x1: 0, y1: 0, x2: 1, y2: 1},
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
    exporting: {
        enabled: false
    },
    title: {
        align: "center",
        style: {
            color: '#000',
            font: '14px "Trebuchet MS", Verdana, Microsoft YaHei, sans-serif'
        }
    },
    xAxis: {
        type: 'datetime',
        tickPixelInterval: 100,
        tickInterval: 10000,
        gridLineWidth: 1,
        lineColor: '#000',
        tickColor: '#000',
        labels: {
            style: {
                color: '#000',
                font: '11px Trebuchet MS, Verdana, Microsoft YaHei, sans-serif'
            }
        },
        title: {
            style: {
                color: '#333',
                fontSize: '12px',
                fontFamily: 'Trebuchet MS, Verdana, Microsoft YaHei, sans-serif'

            }
        }
    },
    yAxis: {
        minorTickInterval: 'auto',
        lineColor: '#000',
        lineWidth: 1,
        tickWidth: 1,
        tickColor: '#000',
        labels: {
            style: {
                color: '#000',
                font: '11px Trebuchet MS, Verdana, Microsoft YaHei, sans-serif'
            }
        },
        title: {
            text: "",
            style: {
                color: '#333',
                fontSize: '12px',
                fontFamily: 'Trebuchet MS, Verdana, Microsoft YaHei, sans-serif'
            }
        },
        plotLines: [{
            value: 0,
            width: 1,
            color: '#808080'
        }]
    },
    legend: {
        verticalAlign: "top"
    },
    tooltip: {
        crosshairs: true,
        formatter: function () {
            return "<b>" + this.series.name + "</b><br/>" +
                Highcharts.dateFormat("%Y-%m-%d %H:%M:%S", this.x, null) + "<br/>" +
                Highcharts.numberFormat(this.y, 3, null, null);
        }
    },
    plotOptions: {
        series: {
            fillOpacity: 0.5,
            events: {
                //控制图标的图例legend不允许切换
                legendItemClick: function () {
                    return false; //return  true 则表示允许切换
                }
            }
        }
    },
    series: [{
        name: "泵前压力",
        data: generateXData()
    }],
    credits: {
        enabled: false//不显示highCharts版权信息
    }
};
$(function() {
    var $queryForm = $("#queryForm");
    var deviceId = GLOBAL_SELECT_DEVICE["deviceId"];

    $queryForm.find("input[name='deviceId']").val(deviceId);

    getDeviceMeta(GLOBAL_SELECT_DEVICE["deviceId"], [function (deviceMetas) {
            var deviceIndex;
            for (var name in deviceMetas) {
                if (!deviceMetas.hasOwnProperty(name)) {
                    continue;
                }
                deviceIndex = deviceMetas[name]["code"];
                break;
            }
            $("#queryForm").find("input[name='index']").val(deviceIndex);

            aysncCallBack([
                initIndexPanel,
                initDeviceIndexList,
                initLayout
            ], [deviceMetas, deviceMetas], 10);
    }, initChart]);

    REALTIME_DATA.callRefresh = function (device) {
        LOADING.show();
        getDeviceMeta(device["deviceId"], function(deviceMetas) {
            initIndexPanel(deviceMetas);
            initDeviceIndexList(deviceMetas);
            switchDeviceData(null, device["deviceId"]);
            setTimeout(function() {
                LOADING.hide();
            }, 500);
        });
    };

});

function callRefresh(device) {
    LOADING.show();
    getDeviceMeta(device["deviceId"], function(deviceMetas) {
        initIndexPanel(deviceMetas);
        initDeviceIndexList(deviceMetas);
        switchDeviceData(null, device["deviceId"]);
        setTimeout(function() {
            LOADING.hide();
        }, 500);
    });
}

/**
 * generate the device index of the specified device
 * @param deviceMetas the device metadata of the specified device
 */
function initDeviceIndexList(deviceMetas) {
    REALTIME_DATA.MOCK = 1;
    var $deviceNormData = $("#device-norm-data");
    var $deviceNormList = $deviceNormData.find(".list-group");
    var $dataList = $("#realtime-datalist");
    var tbody = $dataList.find("tbody");

    $deviceNormList.empty();
    tbody.empty();

    var name;
    for (name in deviceMetas) {
        if (!deviceMetas.hasOwnProperty(name)) {
            continue;
        }
        var deviceMeta = deviceMetas[name];
        var $node = $("<li class='list-group-item'><label></label></li>");
        $node.find("label").append($("<input type='checkbox'>")
            .addClass("minimal")
            .prop("name", "deviceIndex")
            .prop("value", deviceMeta["code"])
            .attr("data-unit", deviceMeta["unit"]));
        $node.find("label").append(deviceMeta["name"]);
        $node.find("label").prop("title", deviceMeta["name"]);
        $deviceNormList.append($node);

        var tr = $("<tr class='even gradeA'></tr>");
        tr.append($("<td>" + deviceMeta["name"] + "</td>"));
        tr.append($("<td class='deviceIndex' data-device-index='" + deviceMeta["code"] + "'>-</td>"));
        tr.append($("<td>" + deviceMeta["unit"] + "</td>"));
        tr.append($("<td class='queryDate'>" + REALTIME_DATA.MOCK_DATE + "</td>"));
        tbody.append(tr);
    }
    
    $("#dataTables-example").bootstrapTable("resetView");

    $deviceNormList.find("input").eq(0).prop("checked", true);
    // icheck init
    $("input[type='checkbox'].minimal").icheck({
        checkboxClass: 'icheckbox_minimal-blue',
        radioClass: 'iradio_minimal-blue'
    }).on("click", function () {
        var _this = this;

        var currentUnit;

        $deviceNormList.find("input:checked").each(function () {
            if ($(this).val() !== $(_this).val()) {
                currentUnit = $(this).attr("data-unit");
                return false;
            }
        });

        if ($deviceNormList.find("input:checked").length == 0) {
            $(_this).icheck("checked");
            return;
        } else if ($deviceNormList.find("input:checked").length === 6 && currentUnit === $(this).attr("data-unit")) {
            // if the checked input length sums to 5 and current input unit is the same as others checked,
            // use timer to disable this input checked
            $(_this).icheck("unchecked");
            return;
        } else if (currentUnit !== $(this).attr("data-unit")) {
            $deviceNormList.find("input:checked").each(function () {
                $(this).icheck("unchecked");
            });
            $(this).icheck("checked");
        }

        switchDeviceData($(_this).val());
    });

    var deviceField = $deviceNormList.find("input").eq(0).val();
    $("#queryForm").find("input[name='index']").val(deviceField);
}

function initIndexPanel(deviceMetas) {
    // 进口压力
    $("#enterpresure").highcharts(Highcharts.merge(REALTIME_DATA.GAUGE_OPTIONS, {
        chart: {
            backgroundColor: '#3291c3'
        },
        title: {
            text: '进口压力',
            style: {
                color: '#fff',
                font: '12px "Trebuchet MS", Verdana, Microsoft YaHei, sans-serif'
            }
        },
        yAxis: {
            min: 0,
            max: 2
        },
        series: [{
            data: [0],
            dataLabels: {
                format: '<div style="text-align:center"><span style="font-size:18px;color:' +
                ((Highcharts.theme && Highcharts.theme.contrastTextColor) || '#f6f962') + '">{y}</span><br/>' +
                '<span style="font-size:12px;color:#333">Mpa</span></div>'
            }
        }]
    }));

    // 出口压力
    $("#outpressure").highcharts(Highcharts.merge(REALTIME_DATA.GAUGE_OPTIONS, {
        chart: {
            backgroundColor: '#4ca0a2'
        },
        title: {
            text: '出口压力',
            style: {
                color: '#fff',
                font: '12px "Trebuchet MS", Verdana, Microsoft YaHei, sans-serif'
            }
        },
        yAxis: {
            min: 0,
            max: 2
        },
        series: [{
            data: [0],
            dataLabels: {
                format: '<div style="text-align:center"><span style="font-size:18px;color:' +
                ((Highcharts.theme && Highcharts.theme.contrastTextColor) || '#f6f962') + '">{y}</span><br/>' +
                '<span style="font-size:12px;color:#333">Mpa</span></div>'
            }
        }]
    }));

    // 设备进水压力
    $("#deviceEnterwaterPre").highcharts(Highcharts.merge(REALTIME_DATA.GAUGE_OPTIONS, {
        chart: {
            backgroundColor: '#7ac3f0'
        },
        title: {
            text: '设备进水压力',
            style: {
                color: '#fff',
                font: '12px "Trebuchet MS", Verdana, Microsoft YaHei, sans-serif'
            }
        },
        yAxis: {
            min: 0,
            max: 2
        },
        series: [{
            data: [0],
            dataLabels: {
                format: '<div style="text-align:center"><span style="font-size:18px;color:' +
                ((Highcharts.theme && Highcharts.theme.contrastTextColor) || '#f6f962') + '">{y}</span><br/>' +
                '<span style="font-size:12px;color:#333">Mpa</span></div>'
            }
        }]
    }));

    // 超高腔压力
    $("#ultraHighpre").highcharts(Highcharts.merge(REALTIME_DATA.GAUGE_OPTIONS, {
        chart: {
            backgroundColor: '#5588a3'
        },
        title: {
            text: '超高腔压力',
            style: {
                color: '#fff',
                font: '12px "Trebuchet MS", Verdana, Microsoft YaHei, sans-serif'
            }
        },
        yAxis: {
            min: 0,
            max: 2
        },
        series: [{
            data: [0],
            dataLabels: {
                format: '<div style="text-align:center"><span style="font-size:18px;color:' +
                ((Highcharts.theme && Highcharts.theme.contrastTextColor) || '#f6f962') + '">{y}</span><br/>' +
                '<span style="font-size:12px;color:#333">Mpa</span></div>'
            }
        }]
    }));
    var $charPanel = $(".easypiechart-panel");
    /*var sampleIndex = ["泵前压力", "泵后压力", "设备进水压力", "超高腔压力"];
    var defaultIndex = ["进口压力(未采集)", "出口压力(未采集)", "设备进水压力(未采集)",
        "超高腔压力(未采集)"];
    for (var i = 0; i < $charPanel.length; i++) {
        var flag = false;
        var name = "";
        var chartConfig = $($charPanel[i]).highcharts();
        for (name in deviceMetas) {
            if (deviceMetas.hasOwnProperty(name) && deviceMetas[name]["name"].indexOf(sampleIndex[i]) > -1) {
                chartConfig.setTitle({
                    text: deviceMetas[name]["name"]
                });
                $($charPanel[i]).attr("data-index", "percent " + deviceMetas[name]["code"]);
                flag = true;
                break;
            }
        }
        if (!flag) {
            chartConfig.setTitle({
                text: defaultIndex[i]
            });
            $($charPanel[i]).attr("data-index", "percent none");
        }
    }*/
    var chartTitleList_configured = [];// 配置指标
    var chartTitleList_default = [];// 默认指标
    var flag = false;
    var chartProperty;
    var name = "";
    for (name in deviceMetas) {
        chartProperty = {};
        if (deviceMetas.hasOwnProperty(name) && deviceMetas[name]["display"] == "1") {
            chartProperty.name = deviceMetas[name]["name"];
            chartProperty.code = deviceMetas[name]["code"];
            chartProperty.max = Number(deviceMetas[name]["max"]);
            chartProperty.unit = deviceMetas[name]["unit"];
            chartTitleList_configured.push(chartProperty);
        } 
        if(chartTitleList_configured.length == 4){
            flag = true;
            break;
        }
    }
    if(!flag){
        for (name in deviceMetas) {
            chartProperty = {};
            if (deviceMetas.hasOwnProperty(name) && (deviceMetas[name]["type"] == "Float" 
                        || deviceMetas[name]["type"] == "Double")) {
                chartProperty.name = deviceMetas[name]["name"];
                chartProperty.code = deviceMetas[name]["code"];
                chartProperty.max = Number(deviceMetas[name]["max"]);
                chartProperty.unit = deviceMetas[name]["unit"];
                chartTitleList_default.push(chartProperty);
            }
            if(chartTitleList_default.length == 4){
                break;
            }
        }
    }
    for (var i = 0; i < $charPanel.length; i++) {
        var chartConfig = $($charPanel[i]).highcharts();
        if (flag) {
            chartConfig.setTitle({
                text: chartTitleList_configured[i]["name"]
            });
            chartConfig.yAxis[0].setExtremes(0, chartTitleList_configured[i]["max"], true);
            chartConfig.series[0].options.dataLabels.format = '<div style="text-align:center">'+
                    '<span style="font-size:18px;color:#f6f962">{y}</span><br/>'+
                    '<span style="font-size:12px;color:#333">'+chartTitleList_configured[i]["unit"]+'</span>'+
                '</div>';
            $($charPanel[i]).attr("data-index", "percent " + chartTitleList_configured[i]["code"]);
        } else {
			if(undefined == chartTitleList_default[i]){
				// alert(i);
				continue;
			}
            chartConfig.setTitle({
                text: chartTitleList_default[i]["name"]
            });
            chartConfig.yAxis[0].setExtremes(0, chartTitleList_default[i]["max"], true);
            chartConfig.series[0].options.dataLabels.format = '<div style="text-align:center">'+
                    '<span style="font-size:18px;color:#f6f962">{y}</span><br/>'+
                    '<span style="font-size:12px;color:#333">'+chartTitleList_default[i]["unit"]+'</span>'+
                '</div>';
            $($charPanel[i]).attr("data-index", "percent " + chartTitleList_default[i]["code"]);
        }
    }
}

function generateXData() {
    // generate an array of random data
    var data = [],
        time = (new Date(REALTIME_DATA.MOCK_DATE)).getTime(),
        i;

    for (i = -60; i <= 0; i++) {
        data.push({
            x: time + i * 5000,
            y: null
        });
    }
    return data;
}

function initLayout() {
    var $chart = $("#highcharts-realtime-data");
    var $deviceNormData = $("#device-norm-data");
    var $dataList = $("#realtime-datalist");
    // 初始化无滚动条
    $("#page-wrapper").css("overflow-y", "hidden");
    // 计算图表展示区域和设备指标显示区域的高度
    var calcHeight = function() {
        var height = $(window).height() - $(".top-navbar").height() - $("#page-inner").children("div").eq(1).height() -
        $(".panel-heading").height() - 52;
        return height;
    };

    $chart.height(calcHeight());
    $dataList.height(calcHeight());
    $deviceNormData.height(calcHeight());

    $(window).resize(function() {
        $chart.height(calcHeight());
        $dataList.height(calcHeight());
        $deviceNormData.height(calcHeight());
        $('#dataTables-example').bootstrapTable('resetView', {height : calcHeight()});
    });

    // 展示当前所选设备
    //

    /* 重新渲染图表 */
    $("a[data-toggle='tab']").on("shown.bs.tab", function () {
        $("#highcharts-realtime-data").highcharts().reflow()
    });

    // Bootstrap table init
    $("#dataTables-example").bootstrapTable({
        height:calcHeight(),//设定高度，固定头部
        search: false,//是否搜索
        pagination: false,//是否分页
        searchOnEnterKey: false,//回车搜索
        showRefresh: false,//刷新按钮
        showColumns: false,//列选择按钮
        locale: "zh-CN",//中文支持,
        detailView: false //是否显示详情折叠
    });
}

function initChart(deviceMetas) {
    var $chart = $("#highcharts-realtime-data");

    var deviceField = $("#queryForm").find("input[name='index']").val();

    REALTIME_DATA.CONFIG.title.text = "";
    REALTIME_DATA.isBoolVar = deviceMetas[deviceField.split(",")[0]]["type"] === "Bool" || deviceMetas[deviceField.split(",")[0]]["type"] === "UInt16";

    if (REALTIME_DATA.isBoolVar) {
        delete REALTIME_DATA.CONFIG.chart.type;
    } else {
        REALTIME_DATA.CONFIG.chart.type = "area";
    }

    REALTIME_DATA.CONFIG.series[0].name = deviceMetas[deviceField]["name"];
    REALTIME_DATA.CONFIG.yAxis.labels = {
        format: "{value}" + deviceMetas[deviceField]["unit"]
    };
    REALTIME_DATA.CONFIG.tooltip = {
        crosshairs: true,
        formatter: function () {
            return "<b>" + this.series.name + "</b><br/>" +
                Highcharts.dateFormat("%Y-%m-%d %H:%M:%S", this.x, null) + "<br/>" +
                Highcharts.numberFormat(this.y, 3, null, null) + deviceMetas[deviceField]["unit"];
        }
    };
    var series = $chart.highcharts(REALTIME_DATA.CONFIG);
    if (REALTIME_DATA.CHART_INTERVAL) {
        clearInterval(REALTIME_DATA.CHART_INTERVAL);
    }
    series = series.highcharts();
    REALTIME_DATA.maxPoint = [];
    REALTIME_DATA.minPoint = [];
    REALTIME_DATA.dynamicUpdateDeviceData(series);
    REALTIME_DATA.CHART_INTERVAL = setInterval(function () {
        REALTIME_DATA.dynamicUpdateDeviceData(series);
    }, 5000);
    setTimeout(function() {
        LOADING.hide();
    }, 500);

}

function switchDeviceData(deviceField, deviceId) {
    clearInterval(REALTIME_DATA.CHART_INTERVAL);
    REALTIME_DATA.MOCK_DATE = new Date(new Date().getTime() - REALDATA_DELAY_TIME).Format("yyyy/MM/dd HH:mm:ss");
    var deviceFields, deviceMeta, series;
    REALTIME_DATA.MOCK = 1;
    // generate an array of random data
    var data = [],
        time = (new Date(REALTIME_DATA.MOCK_DATE)).getTime(),
        i;

    for (i = -60; i <= 0; i++) {
        data.push({
            x: time + i * 5000,
            y: null
        });
    }
    var $queryForm = $("#queryForm");
    if (deviceId) {
        $queryForm.find("input[name='deviceId']").val(deviceId);
        $("#page-inner").find(".percent").each(function () {
            $(this).text("-");
        });
        $("#dataTables-example").find("tr.gradeA").each(function () {
            $(this).find("td").eq(1).text("-");
        });
    } else {
        deviceId = $queryForm.find("input[name='deviceId']").val();
    }
    if (deviceField) {
        deviceFields = $("#device-norm-data").find("input").serialize();
        deviceFields = deviceFields.replace(/deviceIndex=/g, "").replace(/&/g, ",");
        $queryForm.find("input[name='index']").val(deviceFields);
    } else {
        deviceField = $queryForm.find("input[name='index']").val();
        deviceFields = deviceField;
    }

    deviceMeta = GLOBAL_DEVICE_META[deviceId];

    REALTIME_DATA.CONFIG.yAxis.labels = {
        format: "{value}" + deviceMeta[deviceField]["unit"]
    };

    REALTIME_DATA.CONFIG.title.text = "";

    if (deviceFields) {
        REALTIME_DATA.CONFIG.tooltip = {
            crosshairs: true,
            shared: true,
            formatter: function () {
                var s = Highcharts.dateFormat("%Y-%m-%d %H:%M:%S", this.x, null) + "<br/>";
                $.each(this.points, function () {
                    s +=  "<b>" + this.series.name + "</b> : " +
                        Highcharts.numberFormat(this.y, 3, null, null) + deviceMeta[deviceField]["unit"] + "<br>";
                });
                return s;
            }
        };
        deviceFields = deviceFields.split(",");
        REALTIME_DATA.isBoolVar = deviceMeta[deviceFields[0]]["type"] === "Bool" || deviceMeta[deviceFields[0]]["type"] === "UInt16";

        if (REALTIME_DATA.isBoolVar) {
            delete REALTIME_DATA.CONFIG.chart.type;
        } else {
            REALTIME_DATA.CONFIG.chart.type = "area";
        }
        var tempSeries = [];
        for (i = 0; i < deviceFields.length; i++) {
            if (REALTIME_DATA.isBoolVar) {
                tempSeries.push({
                    name: deviceMeta[deviceFields[i]]["name"],
                    step: true,
                    data: data
                });
            } else {
                tempSeries.push({
                    name: deviceMeta[deviceFields[i]]["name"],
                    data: data
                });
            }
        }
        REALTIME_DATA.CONFIG.series = tempSeries
    } else {

        REALTIME_DATA.isBoolVar = deviceMeta[deviceField]["type"] === "Bool" || deviceMeta[deviceFields[0]]["type"] === "UInt16";

        if (REALTIME_DATA.isBoolVar) {
            delete REALTIME_DATA.CONFIG.chart.type;
        } else {
            REALTIME_DATA.CONFIG.chart.type = "area";
        }

        REALTIME_DATA.CONFIG.tooltip = {
            crosshairs: true,
            formatter: function () {
                return "<b>" + this.series.name + "</b><br/>" +
                    Highcharts.dateFormat("%Y-%m-%d %H:%M:%S", this.x, null) + "<br/>" +
                    Highcharts.numberFormat(this.y, 3, null, null) + deviceMeta[deviceField]["unit"];
            }
        };

        if (REALTIME_DATA.isBoolVar) {
            series = [{
                name: deviceMeta[deviceField]["name"],
                step: true,
                data: data
            }];
        } else {
            series = [{
                name: deviceMeta[deviceField]["name"],
                data: data
            }];
        }


        REALTIME_DATA.CONFIG.series = series;
    }
    series = $("#highcharts-realtime-data").highcharts(REALTIME_DATA.CONFIG);
    if (REALTIME_DATA.CHART_INTERVAL) {
        clearInterval(REALTIME_DATA.CHART_INTERVAL);
    }
    series = series.highcharts();
    REALTIME_DATA.maxPoint = [];
    REALTIME_DATA.minPoint = [];
    // set up the updating of the chart each second
    REALTIME_DATA.dynamicUpdateDeviceData(series);
    REALTIME_DATA.CHART_INTERVAL = setInterval(function () {
        REALTIME_DATA.dynamicUpdateDeviceData(series);
    }, 5000);
}

function doClean() {
    if (REALTIME_DATA.CHART_INTERVAL) {
        clearInterval(REALTIME_DATA.CHART_INTERVAL);
    }
    GLOBAL.clearObject(REALTIME_DATA);
}