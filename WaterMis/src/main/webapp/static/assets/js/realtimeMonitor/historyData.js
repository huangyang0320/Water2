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

$(initData());

function initData() {
	$("#moni").on("click", function () {
        listType = "1";
        initDevice();
    });
    
    $("#statPar").on("click", function () {
    	listType = "2";
    	initDevice();
    });
    preLoad();
    var currentDate = new Date().Format("yyyy-MM-dd HH");
    var dateRegion = new Date().getCurrentHour(currentDate, "yyyy-MM-dd HH:mm:ss");
    var deviceId = GLOBAL_SELECT_DEVICE["deviceId"];
    var $queryForm = $("#queryForm");

    $queryForm.find("input[name='deviceId']").val(deviceId);
    $queryForm.find("input[name='dimen']").val("hour");
    $queryForm.find("input[name='startDate']").val(dateRegion[0]);
    $queryForm.find("input[name='endDate']").val(dateRegion[1]);
    $queryForm.find("input[name='addOnDate']").val(0);

    getDeviceMeta(deviceId, [function (deviceMetas) {
        var deviceIndex;
        for (var name in deviceMetas) {
            if (!deviceMetas.hasOwnProperty(name)) {
                continue;
            }
            deviceIndex = deviceMetas[name]["code"];
            break;
        }
        $("#queryForm").find("input[name='index']").val(deviceIndex);

        callRefresh(false, true);

        aysncCallBack([
                initDeviceIndexList,
                initQueryBox
            ], [
                deviceMetas,
                currentDate
            ],
        10);
    }]);
    LOADING.hide();
}

function preLoad() {
    initLayout();
    $("#highcharts-history-data").highcharts(HISTORY_DATA.DEMO)
}

function initDevice() {
	var $deviceNorm = $("#device-norm-data");
    var $deviceNorm2 = $("#device-norm-data2");
    var $deviceNormData = $deviceNorm.find(".list-group");
    var $deviceNormData2 = $deviceNorm2.find(".list-group");

    $deviceNormData.empty();
    $deviceNormData2.empty();
	var deviceMetas = metaDeviceList;
	var deviceMetas = metaDeviceList;
	
    var $deviceNorm = $("#device-norm-data");
    var $deviceNorm2 = $("#device-norm-data2");
    var $deviceNormData = $deviceNorm.find(".list-group");
    var $deviceNormData2 = $deviceNorm2.find(".list-group");

    $deviceNormData.empty();
    $deviceNormData2.empty();
    var index = 0;
    for (var name in deviceMetas) {

        if (!deviceMetas.hasOwnProperty(name)) {
            continue;
        }
        if('1' == listType){
        	if(undefined == deviceMetas[name].type || 'bool' == deviceMetas[name].type){
        		continue;
        	}
        }else{
        	if(undefined != deviceMetas[name].type && 'bool' != deviceMetas[name].type){
        		continue;
        	}
        }

        var deviceMeta = deviceMetas[name];
        var li = $("<li class='list-group-item'><label></label></li>");
        var li1 = li.clone();
        var input = $("<input type='radio'>")
            .prop("name", "sameCompare")
            .prop("value", deviceMeta["code"])
            .addClass("minimal");
        li.find("label").append(input);
        li.find("label").append('<div class="colour_black_'+index%7+'">'+deviceMeta["name"]+"</div>");
        li.find("label").prop("title", deviceMeta["name"]);
        $deviceNormData.append(li);

        input = $("<input type='checkbox'>")
            .prop("name", "diffCompare")
            .prop("value", deviceMeta["code"])
            .attr("data-unit", deviceMeta["unit"])
            .addClass("minimal");
        li1.find("label").append(input);
        li1.find("label").append('<div class="colour_black_'+index%7+'">'+deviceMeta["name"]+"</div>");
        li1.find("label").prop("title", deviceMeta["name"]);
        $deviceNormData2.append(li1);
        index++;
    }
    $deviceNorm.find("input").eq(0).prop("checked", true);
    $("#queryForm").find("input[name='index']").val($deviceNorm.find("input").eq(0).val());

    $deviceNorm2.find("input").eq(0).prop("checked", true);

    $("input[type='radio'].minimal").icheck({
        checkboxClass: 'icheckbox_minimal-blue',
        radioClass: 'iradio_minimal-blue'
    }).on("click", function () {
        switchDeviceData();
    });

    $("input[type='checkbox'].minimal").icheck({
        checkboxClass: 'icheckbox_minimal-blue',
        radioClass: 'iradio_minimal-blue'
    }).on("click", function () {
        var _this = this;

        var currentUnit;

        $deviceNorm2.find("input:checked").each(function () {
            if ($(this).val() !== $(_this).val()) {
                currentUnit = $(this).attr("data-unit");
                return false;
            }
        });

        if ($deviceNorm2.find("input:checked").length == 0) {
            $(_this).icheck("checked");
            return;
        } else if ($deviceNorm2.find("input:checked").length === 10 && currentUnit === $(this).attr("data-unit")) {
            // if the checked input length sums to 10 and current input unit is the same as others checked,
            // use timer to disable this input checked
            $(_this).icheck("unchecked");
            return;
        } else if (currentUnit !== $(this).attr("data-unit")) {
            $deviceNorm2.find("input:checked").each(function () {
                $(this).icheck("unchecked");
            });
            $(this).icheck("checked");
        }

        switchDeviceData();
    });
}
function initDeviceIndexList(deviceMetas) {
	metaDeviceList = deviceMetas;
	var deviceMetas = metaDeviceList;
	
    var $deviceNorm = $("#device-norm-data");
    var $deviceNorm2 = $("#device-norm-data2");
    var $deviceNormData = $deviceNorm.find(".list-group");
    var $deviceNormData2 = $deviceNorm2.find(".list-group");

    $deviceNormData.empty();
    $deviceNormData2.empty();
    var index = 0;
    for (var name in deviceMetas) {

        if (!deviceMetas.hasOwnProperty(name)) {
            continue;
        }
        if('1' == listType){
        	if('bool' == deviceMetas[name].type){
        		continue;
        	}
        }else{
        	if('bool' != deviceMetas[name].type){
        		continue;
        	}
        }

        var deviceMeta = deviceMetas[name];
        var li = $("<li class='list-group-item'><label></label></li>");
        var li1 = li.clone();
        var input = $("<input type='radio'>")
            .prop("name", "sameCompare")
            .prop("value", deviceMeta["code"])
            .addClass("minimal");
        li.find("label").append(input);
        li.find("label").append('<div class="colour_black_'+index%7+'">'+deviceMeta["name"]+"</div>");
        li.find("label").prop("title", deviceMeta["name"]);
        $deviceNormData.append(li);

        input = $("<input type='checkbox'>")
            .prop("name", "diffCompare")
            .prop("value", deviceMeta["code"])
            .attr("data-unit", deviceMeta["unit"])
            .addClass("minimal");
        li1.find("label").append(input);
        li1.find("label").append('<div class="colour_black_'+index%7+'">'+deviceMeta["name"]+"</div>");
        li1.find("label").prop("title", deviceMeta["name"]);
        $deviceNormData2.append(li1);
        index++;
    }
    $deviceNorm.find("input").eq(0).prop("checked", true);
    $("#queryForm").find("input[name='index']").val($deviceNorm.find("input").eq(0).val());

    $deviceNorm2.find("input").eq(0).prop("checked", true);

    $("input[type='radio'].minimal").icheck({
        checkboxClass: 'icheckbox_minimal-blue',
        radioClass: 'iradio_minimal-blue'
    }).on("click", function () {
        switchDeviceData();
    });

    $("input[type='checkbox'].minimal").icheck({
        checkboxClass: 'icheckbox_minimal-blue',
        radioClass: 'iradio_minimal-blue'
    }).on("click", function () {
        var _this = this;

        var currentUnit;

        $deviceNorm2.find("input:checked").each(function () {
            if ($(this).val() !== $(_this).val()) {
                currentUnit = $(this).attr("data-unit");
                return false;
            }
        });

        if ($deviceNorm2.find("input:checked").length == 0) {
            $(_this).icheck("checked");
            return;
        } else if ($deviceNorm2.find("input:checked").length === 10 && currentUnit === $(this).attr("data-unit")) {
            // if the checked input length sums to 10 and current input unit is the same as others checked,
            // use timer to disable this input checked
            $(_this).icheck("unchecked");
            return;
        } else if (currentUnit !== $(this).attr("data-unit")) {
            $deviceNorm2.find("input:checked").each(function () {
                $(this).icheck("unchecked");
            });
            $(this).icheck("checked");
        }

        switchDeviceData();
    });
}

function initQueryBox(currentDate) {
    var $queryForm = $("#queryForm");
    var $formDate = $(".form_datetime");

    $formDate.datetimepicker(DATE_CONFIG(1, 1, "yyyy-mm-dd hh")).val(currentDate);
    DATE_ADD_CONFIG("hourtime");

    $("#statisticDate").on("change", function() {
        var $timeDimen = $queryForm.find("input[name='dimen']");
        var $startDate = $queryForm.find("input[name='startDate']");
        var $endDate = $queryForm.find("input[name='endDate']");
        var dateArray = [];
        if ($timeDimen.val() == "hour") {
            dateArray = new Date().getCurrentHour($(this).val(), "yyyy-MM-dd HH:mm:00");
        } else if ($timeDimen.val() == "day") {
            dateArray = new Date().getCurrentDay($(this).val(), "yyyy-MM-dd HH:00:00");
        } else if ($timeDimen.val() == "month") {
            dateArray = new Date().getCurrentMonth($(this).val(), "yyyy-MM-dd 00:00:00");
        } else {
            dateArray = new Date().getCurrentYear($(this).val(), "yyyy-MM-1 00:00:00");
        }
        $startDate.val(dateArray[0]);
        $endDate.val(dateArray[1]);
        callRefresh();
    });

    $("#addOnTime").on("change", function () {
        $queryForm.find("input[name='addOnDate']").val($(this).val());
        callRefresh();
    });

    /* 重新渲染图表 */
    $("a[data-toggle='tab']").on("shown.bs.tab", function () {
        switchDeviceData(true);
        callRefresh();
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
        var $timeDimen = $queryForm.find("input[name='dimen']");


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

    $("#export").on("click", function () {
        var $chart = $("#highcharts-history-data");
        var chartType = $chart.is(":hidden") ? "diff" : "same";
        if (chartType == "same") {
            $chart.highcharts().downloadXLS();
        } else {
            $("#highcharts-history-data2").highcharts().downloadXLS();
        }
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

    // 展示当前所选设备
    //
}

function switchDeviceData(flag) {
    var $queryForm = $("#queryForm");
    var indexArray;
    if (!$("#highcharts-history-data").is(":hidden")) {
        indexArray = $("#device-norm-data").find("input").serializeArray();
    } else {
        indexArray = $("#device-norm-data2").find("input").serializeArray();
    }
    var indexs = [];
    for (var i = 0; i < indexArray.length; i++) {
        indexs.push(indexArray[i]["value"]);
    }
    $queryForm.find("input[name='index']").val(indexs.join(","));
    if (flag) {
        return;
    }
    callRefresh(false,true);
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
        HISTORY_DATA.isBoolVar = deviceMetas[deviceIndex.split(",")[0]]["type"] === "Bool" || deviceMetas[deviceIndex.split(",")[0]]["type"] === "UInt16";

        if (HISTORY_DATA.isBoolVar) {
            delete HISTORY_DATA.DEMO.chart.type;
        } else {
            HISTORY_DATA.DEMO.chart.type = "spline";
        }
        HISTORY_DATA.DEMO.title.text = "";
        HISTORY_DATA.DEMO.xAxis.labels.formatter = DEVICE_DIMEN()[dimen]["formatter"];
        HISTORY_DATA.DEMO.exporting.filename = GLOBAL_SELECT_DEVICE["name"] + "-" + new Date().getTime();
        HISTORY_DATA.DEMO.series = [];
        HISTORY_DATA.DEMO.xAxis.categories = categories;

        HISTORY_DATA.preRequest = $.post(CONTEXT_PATH + "/monitor/statistic/" + deviceId + "/history?" + $queryForm.serialize(), function(result) {
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
                currentChart = $("#highcharts-history-data").highcharts(HISTORY_DATA.DEMO);
            } else {
                /* 反比曲线 */
                currentChart = $("#highcharts-history-data2").highcharts(HISTORY_DATA.DEMO);
            }

            showMaxMinTip(currentChart.highcharts());
            
            setTimeout(function() {
                LOADING.hide();
            }, 500);
        });
    });
}

function showMaxMinTip(currentChart) {
    HISTORY_DATA.maxPoint = [];
    HISTORY_DATA.minPoint = [];
    for (var k = 0; k < currentChart.series.length && !HISTORY_DATA.isBoolVar; k++) {
        var maxValue = currentChart.series[k].dataMax;
        var minValue = currentChart.series[k].dataMin;

        if (maxValue == undefined || minValue == undefined) {
            break;
        }

        var points = currentChart.series[k].data;

        var maxFlag = true;
        var minFlag = true;
        if(maxValue != minValue){
            for (var i = 60; i >= 0; i--) {
                if (maxFlag && points[i] && points[i].y == maxValue) {
                    maxFlag = false;
                    HISTORY_DATA.maxPoint[k] = points[i];
                    HISTORY_DATA.maxPoint[k].update({
                        marker: {
                            symbol: 'url('+ROOT_PATH+'/static/assets/img/max.png)'
                        }
                    });
                } else if (minFlag && points[i] && points[i].y == minValue) {
                    minFlag = false;
                    HISTORY_DATA.minPoint[k] = points[i];
                    HISTORY_DATA.minPoint[k].update({
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
