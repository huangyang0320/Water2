/**
 * Global constant
 */

var GLOBAL = {};

/**
 * root path for the application
 */
var ROOT_PATH = function getContextPath() {
    var pathName = document.location.pathname;
    var index = pathName.substr(1).indexOf("/");
    return pathName.substr(0, index + 1);
}();

/**
 * context path for the application
 */
var CONTEXT_PATH = ROOT_PATH + "/a";

/**
 * Image server context path
 * @type {string}
 */
GLOBAL.IMG_CONTEXT_PATH = "";
GLOBAL.IMG_CONTEXT_PATH_HEFEI = ROOT_PATH.replace("Mis", "");
GLOBAL.IMG_CONTEXT_PATH_BAIDU = "/HFWater";

/**
 * 页面分组
 * @type {string[]}
 * @private
 */
// 需要展示右侧树的列表
var _page_url_show_list = ["home-map-baiduNew.html", "realtimeData.html","deviceArchives.html", "themeDeviceStat.html", "historyData.html", "energyanalysisNew.html", "poweranalysis.html", "lifeprediction.html","videoMonitor.html","video.html", "videoMonitorBacl.html","videoHis.html","energyanalysis.html","onlineData_v2.html","themeStatNew.html","themeStatNew.html","configuration.html","historyDataNewBak.html","realtimeDataNew.html","historyDataNew.html" ,"pumpGuide.html", "pumpBandDiagram.html", "smallFlow.html"];

// 需要展示右侧树复选框的列表
var _page_url_check_list = [
    "energyanalysisNew.html",
    // "historyDataNewBak.html",
    // "realtimeDataNew.html",
    "pumpBandDiagram.html"
];
// 需要展示右侧树到设备的列表
var _page_url_equipment_list = ["energyanalysisNew.html","energyanalysis.html","onlineData_v2.html","historyDataNew.html","poweranalysis.html","themeStatNew.html","themeStatNew.html","themeDeviceStat.html","realtimeDataNew.html","deviceArchives.html","lifeprediction.html","realtimeData.html","historyDataNewBak.html","pumpGuide.html", "pumpBandDiagram.html", "smallFlow.html"];

// 需要展示右侧树到泵房的列表
var _page_url_pump_list = ["home-map-baidu.html", "home-map-baiduNew.html", "configuration.html"];

// 需要展示右侧树到视频的列表
var _page_url_video_list = ["videoMonitor.html","video.html", "videoMonitorBacl.html","videoHis.html"];//视频树单选


/**
 * Global data object
 */
var GLOBAL_LOGIN_USER;

var GLOBAL_URL_ADDRESS = ""; // 跳转的页面地址

var GLOBAL_TREE_TYPR = ""; // 几种树的类型

var GLOBAL_TREE_PUMP_EQT = true; // 展示到泵房或设备 true/false 设备/泵房

var GLOBAL_TREE_VIDEO_EQT = 1; // 展示设备或视频 1设备 2 视频

var GLOBAL_SELECT_DEVICE; // 当前选择的树节点对象

var ALARM_TO_MAP_PUMP_ID = ""; // 告警转到地图泵房ID

var GLOBAL_SELECT_LIST = [];// 选择的设备数组
var GLOBAL_SELECT_LIST_B = []; //只有树调用

var GLOBAL_SELECT_LIST_CHO0SE = []; // 选中的设备或者泵房

// var GLOBAL_SELECT_LIST_TYPE = 'device'; // 选中的列表类型
var GLOBAL_SELECT_LIST_TYPE = 'pump'; // 选中的列表类型

var GLOBAL_SELECT_LIST_ISSHOWCHECK = true; // 选中的列表是否多选

var GLOBAL_PUMP_MSG_MAP = "" ; // 当前泵房详情

var GLOBAL_REGION_LIST = [];

var GLOBAL_DEVICE_LIST = [];

var GLOBAL_DEVICE_META = {};

var PUMPHOUSELIST = [];

var DEVICE_PUMPHOUSELIST = {};

var PUMPGUIDE_DEVICEID=[];//泵站导航选中的设备ID


/**
 * The delay time of the millseconds for real time data query
 * @type {number}
 */
var REALDATA_DELAY_TIME;

var EXPORT_MUST_NUM = 1500;

var DATE_CONFIG = function (startView, minView, format) {
    return {
        language: "zh-CN",
        format: format,
        weekStart: 1,
        autoclose: 1,
        startView: startView,
        minView: minView,
        forceParse: 0,
        todayBtn: true,
        todayHighlight: true
    }
};

var DATA_MINUTE_CONFIG = function() {
    var categories = [];
    for (var i = 0; i <= 11; i++) {
        categories.push(i*5 + "");
    }
    return categories;
}();

var DATA_HOUR_CONFIG = function() {
    var categories = [];
    for (var i = 0; i < 60; i++) {
        categories.push(i + "");
    }
    return categories;
}();

var DATA_DAY_CONFIG = function() {
    var categories = [];
    for (var i = 0; i < 24; i++) {
        categories.push(i + "");
    }
    return categories;
}();
var DATA_MONTH_CONFIG = function() {
    var categories = [];
    for (var i = 1; i < 32; i++) {
        categories.push(i + "");
    }
    return categories;
}();
var DATA_YEAR_CONFIG = function() {
    var categories = [];
    for (var i = 1; i < 13; i++) {
        categories.push(i + "");
    }
    return categories;
}();

var DATE_ADD_CONFIG = function (dimen) {
    var $addOnTime = $("#addOnTime");
    $addOnTime.text("");
    var i = 0;
    if (dimen.indexOf("hourtime") > -1) {
        for (; i <= 15; i++) {
            $addOnTime.append("<option value='" + i +"'>" + i +"</option>")
        }
    } else if (dimen.indexOf("daytime") > -1) {
        for (; i <= 7; i++) {
            $addOnTime.append("<option value='" + i +"'>" + i +"</option>")
        }
    } else if (dimen.indexOf("monthtime") > -1) {
        for (; i <= 12; i++) {
            $addOnTime.append("<option value='" + i +"'>" + i +"</option>")
        }
    } else {
        for (; i <= 10; i++) {
            $addOnTime.append("<option value='" + i +"'>" + i +"</option>")
        }
    }
};

var DEVICE_DIMEN = function (unit) {
    return {
        "minute": {
            categories: DATA_MINUTE_CONFIG,
            formatter: function () {
                return this.value + '秒';
            },
            tooltip: {
                crosshairs: true,
                shared: true,
                formatter: function () {
                    var s = this.x + '秒' + '<br/>';
                    $.each(this.points, function () {
                        s +=  '<b>' + this.series.name + ' : ' + Highcharts.numberFormat(this.y, 3, null, null) + " " + unit + "</b><br/>";
                    });
                    return s;
                }
            }
        },
        "hour": {
            categories: DATA_HOUR_CONFIG,
            formatter: function () {
                return this.value + '分';
            },
            tooltip: {
                crosshairs: true,
                shared: true,
                formatter: function () {
                    var s = this.x + '分' + '<br/>';
                    $.each(this.points, function () {
                       s +=  '<b>' + this.series.name + ' : ' + Highcharts.numberFormat(this.y, 3, null, null) + " " + unit + "</b><br/>";
                    });
                    return s;
                }
            }
        },
        "day": {
            categories: DATA_DAY_CONFIG,
            formatter: function () {
                return this.value + '时';
            },
            tooltip: {
                crosshairs: true,
                shared: true,
                formatter: function () {
                    var s = this.x + '时' + '<br/>';
                    $.each(this.points, function () {
                        s +=  '<b>' + this.series.name + ' : ' + Highcharts.numberFormat(this.y, 3, null, null) + " " + unit + "</b><br/>";
                    });
                    return s;
                }
            }
        },
        "month": {
            categories: DATA_MONTH_CONFIG,
            formatter: function () {
                return this.value + '日';
            },
            tooltip: {
                crosshairs: true,
                shared: true,
                formatter: function () {
                    var s = this.x + '日' + '<br/>';
                    $.each(this.points, function () {
                        s +=  '<b>' + this.series.name + ' : ' + Highcharts.numberFormat(this.y, 3, null, null) + " " + unit + "</b><br/>";
                    });
                    return s;
                }
            }
        },
        "year": {
            categories: DATA_YEAR_CONFIG,
            formatter: function () {
                return this.value + '月';
            },
            tooltip: {
                crosshairs: true,
                shared: true,
                formatter: function () {
                    var s = this.x + '月' + '<br/>';
                    $.each(this.points, function () {
                        s +=  '<b>' + this.series.name + ' : ' + Highcharts.numberFormat(this.y, 3, null, null) + " " + unit + "</b><br/>";
                    });
                    return s;
                }
            }
        }
    }
};

/**
 * Gloabal dom object
 */
var GLOABAL_LOADING;

var LOADING = {};

$(function () {

    GLOBAL.map = {
        showDeviceSummaryDelayTime: 10
    };

    GLOBAL.queryLatestALarm = {
        timeInterval: 10 * 1000,
        convertTime: function(time) {
            if (time) {
                var alarmRate = time;
                if (alarmRate === "none") {
                    alarmRate = -1;
                } else if (alarmRate.indexOf(":s") > -1) {
                    alarmRate = parseInt(alarmRate.replace(":s", ""));
                } else if (alarmRate.indexOf(":m") > -1) {
                    alarmRate = parseInt(alarmRate.replace(":m", "")) * 60;
                } else if (alarmRate.indexOf(":h") > -1) {
                    alarmRate = parseInt(alarmRate.replace(":h", "")) * 60 * 60;
                } else {
                    alarmRate = 10;
                }

                this.timeInterval =  alarmRate * 1000;
            } else {
                this.timeInterval =  10 * 1000;
            }
            return this.timeInterval;
        },
        setTime: function(interval) {
            if (this.timer) {
                clearInterval(this.timer);
            }
            this.start(interval);
        },
        start: function(interval) {
            this.method()
            if (interval && this.convertTime(interval) > 0) {
                this.timer = setInterval(this.method, this.timeInterval);
            }
        },
        method: function() {
            $.post(CONTEXT_PATH + "/alarmStatController/indexAlarmSize", function (result) {
                if (!isNaN(result) && Number(result) > 0) {
                    $("#showWarnDialog").find("span").text(result);
                }else{
                	$("#showWarnDialog").find("span").text('');
                }
            });
        }
    };
    LOADING.show = function(){
        $('#globMB').show()
    }
    LOADING.hide = function(){
        $('#globMB').hide()
    }
    GLOABAL_LOADING = {
        _defualtTimeout: 500,

        validate: function(timeout) {
            return !isNaN(timeout) && timeout > 0;
        },

        delay: function(callback, timeout) {
            setTimeout(callback, timeout);
        },

        show: function(timeout) {
            if (this.validate(timeout)) {
                this._defualtTimeout = timeout;
            }
            this.delay(function() {
                LOADING.show()
            }, this._defualtTimeout);
        },

        hide: function(timeout) {
            if (this.validate(timeout)) {
                this._defualtTimeout = timeout;
            }
            this.delay(function() {
                LOADING.hide()
            }, this._defualtTimeout);
        }
    };


    // REALDATA_DELAY_TIME = 1000 * 60 * 5;
    REALDATA_DELAY_TIME = 5 * 1000;

    $.ajaxPrefilter(function( options) {
        options.async = true;
    });

    $.ajaxSetup ({
        //关闭AJAX相应的缓存
        cache: false,
        error: function () {

        }
    });

    Highcharts.setOptions({
        global: {
            useUTC: false
        },
        lang: {
            resetZoom: "恢复",
            noData: "当前区间没有数据"
        }
    });

    $(document).on("keydown", function(event) {
        if (event.keyCode === 13) {
            event.preventDefault();
            event.stopPropagation();
        }
    });

    GLOBAL.areaList = [];

    GLOBAL.deviceMenu = "";

    GLOBAL.session = {
        sessionObject: window.sessionStorage,
        size: function() {
            return this.sessionObject.length
        },
        contain: function(key) {
          return !!this.get(key);
        },
        set: function(key, value) {
            this.sessionObject.setItem(key, value);
        },
        addList: function(key, value) {
            var list = !!this.get(key) ? JSON.parse(this.get(key)) : [];
            list.push(value);
            this.set(key, JSON.stringify(list));
        },
        setList: function(key, value) {
            var list = [];
            list.push(value);
            this.set(key, list)
        },
        get: function(key) {
            return JSON.parse(this.sessionObject.getItem(key));
        },
        remove: function(key) {
            this.sessionObject.removeItem(key);
        },
        clear: function() {
            this.sessionObject.clear();
        }
    };

    GLOBAL.local = {
        localObject: window.localStorage,
        size: function() {
            return this.localObject.length
        },
        contain: function(key) {
            return !!this.get(key);
        },
        set: function(key, value) {
            this.localObject.setItem(key, value);
        },
        setList: function(key, value) {
            var list = this.contain(key)? JSON.parse(this.get(key)) : [] ;
            list.push(value);
            this.set(key, JSON.stringify(list));
        },
        get: function(key) {
            return this.localObject.getItem(key);
        },
        remove: function(key) {
            this.localObject.removeItem(key);
        },
        clear: function() {
            this.localObject.clear();
        }
    };

    GLOBAL.clearObject = function(obj) {
        if (obj && typeof obj == "object") {
            for (var property in obj) {
                if (obj.hasOwnProperty(property))
                    delete obj[property];
            }
        }
    };

    GLOBAL.getDeviceMeta = function () {
        var doCallBack = function(fn) {
            if ((typeof fn) == "function") {
                fn(GLOBAL_DEVICE_META[deviceId]);

            } else if (typeof fn == "object") {
                fn[0](GLOBAL_DEVICE_META[deviceId]);
                for (var i = 1; i < fn.length; i++) {
                    fn[i](GLOBAL_DEVICE_META[deviceId]);
                }
            }
        };

        //
        // if (callback != undefined) {
        //     if (GLOBAL_DEVICE_META && GLOBAL_DEVICE_META.hasOwnProperty(deviceId)) {
        //         doCallBack(callback);
        //     } else {
        //         GLOBAL_DEVICE_META[deviceId] = {};
        //         $.post(CONTEXT_PATH + "/home/device/" + deviceId + "/metadata", function (result) {
        //             if (result && $.trim(result) != "") {
        //                 result.map(function (item) {
        //                     GLOBAL_DEVICE_META[deviceId][item["code"]] = item;
        //                 });
        //                 doCallBack(callback);
        //             } else {
        //                 console.error("No device metadata");
        //             }
        //         });
        //     }
        // }
    };

    GLOBAL.isrendered = true;

    GLOBAL.deviceConsumeMeta = {};

    GLOBAL.getDeviceConsumeMeta = function (deviceId, callback) {

        var deviceIds = deviceId.split(",");
        var pumphouseIds = {};

        if (deviceIds && deviceIds.length > 0) {
            deviceIds.map(function(item) {
                if (!GLOBAL.deviceConsumeMeta.hasOwnProperty(item)) {
                    pumphouseIds[DEVICE_PUMPHOUSELIST[item]] = "";
                }
            });

            pumphouseIds = Object.keys(pumphouseIds);
        }

        var doCallBack = function(fn) {
            if ((typeof fn) == "function") {
                fn();

            } else if (typeof fn == "object") {
                fn[0](GLOBAL.deviceConsumeMeta[deviceId]);
                for (var i = 1; i < fn.length; i++) {
                    fn[i]();
                }
            }
        };


        if (callback != undefined) {
        	//根据设备反推泵房
        	pumphouseIds=GLOBAL_SELECT_DEVICE['pumpHouseId'];
            if ($.trim(pumphouseIds) === "") {
                doCallBack(callback);
            } else {
                $.post(CONTEXT_PATH + "/home/pumphouseId/metadata/consume?pumphouseIds=" + pumphouseIds, function (result) {
                    if (result && $.trim(result) != "" && result.length > 0) {
                        var i;
                        var group = {};
                        for (i = 0; i < result.length; i++) {
                            var tmp = result[i];
                            var tmpPumpHouseId = tmp["ID_PUMP_HOUSE"];
                            if (!group.hasOwnProperty(tmpPumpHouseId)) {
                                group[tmpPumpHouseId] = [];
                            }
                            group[tmpPumpHouseId].push(tmp)
                        }

                        for (i = 0; i < deviceIds.length; i++) {
                            var deviceId = deviceIds[i];
                            for (var name in group) {
                                if (deviceId.indexOf(name) > -1) {
                                    var relatedIndexs = [];
                                    var tmpIndexs = [];
                                    for (var j = 0; j < result.length; j++) {
                                        var deviceConsumeMeta = result[j];
                                        GLOBAL.deviceConsumeMeta[deviceId] = null;

                                        if (deviceConsumeMeta["ID_DEVICE"] === deviceId) {
                                            if (deviceConsumeMeta["IS_CFGS"] === "0") {
                                                relatedIndexs.push(deviceConsumeMeta["POWER_FROM"]);
                                                relatedIndexs.push(deviceConsumeMeta["FLOW_FROM"]);
                                                break;
                                            } else {
                                                relatedIndexs = tmpIndexs;
                                            }
                                        } else {
                                            tmpIndexs.push(deviceConsumeMeta["POWER_FROM"]);
                                            tmpIndexs.push(deviceConsumeMeta["FLOW_FROM"]);
                                        }
                                    }
                                    GLOBAL.deviceConsumeMeta[deviceId] = relatedIndexs;
                                }
                            }
                        }
                    }

                    doCallBack(callback);
                });
            }
        }
    };

});

function getDeviceMeta(deviceId, callback) {
    var doCallBack = function(fn) {
        if ((typeof fn) == "function") {
            fn(GLOBAL_DEVICE_META[deviceId]);

        } else if (typeof fn == "object") {
            fn[0](GLOBAL_DEVICE_META[deviceId]);
            for (var i = 1; i < fn.length; i++) {
                fn[i](GLOBAL_DEVICE_META[deviceId]);
            }
        }
    };


    if (callback != undefined) {
        if (GLOBAL_DEVICE_META && GLOBAL_DEVICE_META.hasOwnProperty(deviceId)) {
            doCallBack(callback);
        } else {
            GLOBAL_DEVICE_META[deviceId] = {};
            $.post(CONTEXT_PATH + "/home/device/" + deviceId + "/metadata", function (result) {
                if (result && $.trim(result) != "") {
                    result.map(function (item) {
                        GLOBAL_DEVICE_META[deviceId][item["code"]] = item;
                    });
                    doCallBack(callback);
                } else {
                    console.error("No device metadata");
                }
            });
        }
    }
}

function aysncCallBack(fn, args, timeout) {
    timeout = !timeout ? 500 : timeout;
    if ((typeof fn) == "function") {
        setTimeout(fn(args), timeout);

    } else if (typeof fn == "object") {
        for (var i = 0; i < fn.length; i++) {
            if (navigator.userAgent.indexOf("MSIE 9") > -1) {
                fn[i](args[i]);
            } else {
                setTimeout(fn[i](args[i]), timeout);
            }
        }
    }
}
