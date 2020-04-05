/*
 * energyanalysis.js
 */

ENERGY_ANALYSIS = {};

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
    //var deviceId = GLOBAL_SELECT_DEVICE["deviceId"];


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
    $("#energyanalysis-chart").highcharts(ENERGY_ANALYSIS.CHART_CONFIG);
    $("#energyanalysis-compare").highcharts(ENERGY_ANALYSIS.CHART_COMPARE_CONFIG);
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

        // if (ENERGY_ANALYSIS.isRendered) {
        //     // iCheck init
        //     $("input[type='checkbox'].minimal").icheck({
        //         checkboxClass: 'icheckbox_minimal-blue',
        //         radioClass: 'iradio_minimal-blue'
        //     });
        //     ENERGY_ANALYSIS.isRendered = false;
        // }

        var _span = $(this).find("span");
        var _i = $(this).find("i");
        if(_span.attr("class") == "device-choose") {
            // $("body").bind("click", function (event) {
            //     $("body").unbind();
            //
            //     _span.text("请选择设备 ");
            //     _span.attr("class", "device-choose");
            //     _i.attr("class", "fa fa-angle-double-down");
            //     $("#device-menu").slideUp();
            //     reloadDiffDeviceChart(true);
            //
            //     event.stopPropagation();
            //     event.preventDefault();
            //     return false;
            // });
            _span.text("确定 ");
            _span.attr("class", "sure-choose");
            _i.attr("class", "fa fa-angle-double-up");
            $("#device-menu").slideDown();
        } else {
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

    // $(".device-btn").on("click", function(event){
    //
    //     if (ENERGY_ANALYSIS.isRendered) {
    //         var $deviceMenu = $("#device-menu");
    //         $deviceMenu.empty();
    //         $deviceMenu = $deviceMenu.append(GLOBAL.deviceMenu);
    //
    //         $deviceMenu.find("input[value='" + GLOBAL_SELECT_DEVICE["deviceId"] + "']").eq(0).prop("checked", true);
    //         ENERGY_ANALYSIS.isRendered = false;
    //         // iCheck init
    //         // $("input[type='checkbox'].minimal").icheck({
    //         //     checkboxClass: 'icheckbox_minimal-blue',
    //         //     radioClass: 'iradio_minimal-blue'
    //         // });
    //     }
    //
    //     var _span = $(this).find("span");
    //     var _i = $(this).find("i");
    //     if(_span.attr("class") == "device-choose") {
    //         // $("body").bind("click", function (event) {
    //         //     $("body").unbind();
    //         //
    //         //     _span.text("请选择设备 ");
    //         //     _span.attr("class", "device-choose");
    //         //     _i.attr("class", "fa fa-angle-double-down");
    //         //     $("#device-menu").slideUp();
    //         //     reloadDiffDeviceChart(true);
    //         //
    //         //     event.stopPropagation();
    //         //     event.preventDefault();
    //         //     return false;
    //         // });
    //         _span.text("确定 ");
    //         _span.attr("class", "sure-choose");
    //         _i.attr("class", "fa fa-angle-double-up");
    //         $("#device-menu").slideDown();
    //     } else {
    //         $("body").unbind();
    //         _span.text("请选择设备 ");
    //         _span.attr("class", "device-choose");
    //         _i.attr("class", "fa fa-angle-double-down");
    //         $("#device-menu").slideUp();
    //         reloadDiffDeviceChart(true);
    //     }
    //
    //     event.stopPropagation();
    //     event.preventDefault();
    //     return false;
    // });
}

function initDeviceList() {
    var $deviceMenu = $("#device-menu");
    /* bootstrap 列表组 设备列表 */
    $deviceMenu.empty();

    var device = GLOBAL_SELECT_DEVICE;
    $deviceMenu.empty();
    var deviceList =[];
	GLOBAL_REGION_LIST.forEach(function( val, index ) {
		if(val.deviceId){
			deviceList.push(val);
		}
	});
	
    for (var j = 0; j < deviceList.length; j++) {
        var device = deviceList[j];
        var deviceNode = $("<li><a href='javascript:;' onclick='refreshDevice(" + JSON.stringify(device) + ", this)'>"
            + device["deviceId"] + "<br>" + device["deviceName"] + "</a></li>").addClass("device");
        var deviceTitle = device["deviceName"] + " - " + device["deviceId"];
        var $deviceItem = $("<li class='list-group-item'><label title='" + deviceTitle + "'></label></li>");
        $deviceItem.find("label").append($("<input id='deviceMenu" + device["deviceId"] + "' class='minimal my-checkbox' name='deviceId' type='checkbox' value='" + device["deviceId"] + "' data-device-name='" + device["deviceName"] + "'>"));
        $deviceItem.find("label").append(deviceTitle);
        GLOBAL.deviceMenu += $deviceItem.prop("outerHTML");
    }
	
    $deviceMenu = $deviceMenu.append(GLOBAL.deviceMenu);
    $deviceMenu.find("input[value='" + GLOBAL_SELECT_DEVICE["deviceId"] + "']").eq(0).prop("checked", true);
}

function initLayout() {
    var $deviceMenu = $("#device-menu");
    // 初始化无滚动条
    $("#page-wrapper").css("overflow-y", "hidden");
    // 计算高度
    var calcHeight = function() {
        var height = $(window).height() - $(".chart-time").height() - 216;
        $("#energyanalysis-chart").height(height+3);
        $("#energyanalysis-compare").height(height + 66);
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

function callRefresh(device, pre) {
    var pre = pre || "";
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
    	reloadDeviceConsume(deviceId, categories, $queryForm.serialize());
        reloadDiffDeviceChart();
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
        $("#energyanalysis-chart").highcharts(ENERGY_ANALYSIS.CHART_CONFIG);
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
                    
                    data[tmpId] = {
                            "name": '',
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
                    
                    for (i = 0; i < result.length; i++) {
                    	if(result[i]["deviceId"] == tmpId) {
                    		data[tmpId]["name"] = result[i]["deviceName"];
                    		if (result[i]["queryName"].indexOf("m3") > -1){
                                data[tmpId]["flow"] = Number(Number(result[i]["queryValue"]) + Number(data[tmpId]["flow"])).toFixed(3);
                            }

                            if (result[i]["queryName"].indexOf("kWh") > -1){
                                data[tmpId]["energy"] = Number(Number(result[i]["queryValue"]));
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
                	if(isNaN(data[name].getRate())){
                		continue;
                	}
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