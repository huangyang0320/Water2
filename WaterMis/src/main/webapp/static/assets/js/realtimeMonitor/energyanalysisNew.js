/*
 * energyanalysis.js
 */

var dateData = {
    'day':["0时","1时","2时","3时","4时","5时","6时","7时","8时","9时","10时","11时","12时","13时","14时","15时","16时","17时","18时","19时","20时","21时","22时","23时"],
    'month':[],
    'year':['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
}
var charColor = ["#6295EF", "#62EFA6", "#D562EF", "#61c0bf", "#64844A", "#2D87B9", "#5C6F61", "#533ADF", "#2C8430", "#2C8474", "#5DEAD0"]

ENERGY_ANALYSIS = {};

ENERGY_ANALYSIS.CHART_CONFIG = {
    colors: charColor ,
    chart: {
        type: 'column',
    },
    plotOptions: {
        column : {
            borderWidth: 0,
            pointWidth:10, //柱子宽度
            dataLabels: {
                style:{
                    fontSize:11
                },

                enabled: false
            }
        }
    },
    title: {
        text: '千吨水耗电量趋势分析对比',
        style: {
            color: '#333',
            fontWeight: 'bold',
            fontSize: '16px',
            fontFamily: 'Trebuchet MS, Verdana, Microsoft YaHei'
        }
    },
    xAxis: {
        crosshair: true,
        categories: dateData.day
    },
    yAxis: [{
        labels: {
            format: "{value}"
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
    }],
    tooltip: {
        shared: true
    },
    legend: {
        maxHeight: 80,
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
    series: [],
    exporting: {
        enabled: false
    },
    credits: {
        enabled: false
    },
    lang: {
        noData: "暂无数据" //真正显示的文本
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
            color: '#333'
        }
    }
};

ENERGY_ANALYSIS.CHART_COMPARE_CONFIG = {
    colors: ['#24CBE5', '#64E572', '#FF9655', '#058DC7', '#50B432', '#ED561B', '#DDDF00', '#FFF263', '#6AF9C4'],
    title: {
        text: '设备千吨水耗电量对比'
    },
    tooltip: {
        shared: true
    },
    xAxis: {
        crosshair: true,
        categories: []
    },
    yAxis: [
        {
            title: {
                text: "千吨水耗电量(度/千吨)",
            }
        }
    ],
   /* plotOptions: {
        column: {
            allowPointSelect: true,
            cursor: 'pointer',
            dataLabels: {
                enabled: false
            },
            showInLegend: true // 设置饼图是否在图例中显示
        }
    },*/
    series: [{
        type: 'column',
        size:'80%',
        name: '耗电量',
        data: []
    }],
    credits: {
        enabled: false//不显示highCharts版权信息
    },
    lang: {
        noData: "暂无数据" //真正显示的文本
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
            color: '#333'
        }
    },
    exporting: {
        enabled: false
    },

};

// 时间
function initQueryBox(currentDate) {
    var $queryForm = $("#queryForm");
    var $formDate = $(".form_datetime");

    $formDate.datetimepicker(DATE_CONFIG(2, 2, "yyyy-mm-dd")).val(currentDate);
    DATE_ADD_CONFIG("daytime");

    $("#statisticDate").on("change", function () {
        var value = $(this).val();
        var $queryForm = $("#queryForm");
        var $timeDimen = $queryForm.find("input[name='dimen']");
        var $startDate = $queryForm.find("input[name='startDate']");
        var $endDate = $queryForm.find("input[name='endDate']");
        var dateArray = [];
        var DATE = new Date()
        if ($timeDimen.val() == "day") {
            dateArray = DATE.getCurrentDay(value, "yyyy-MM-dd HH:00:00");
        } else if ($timeDimen.val() == "month") {
            dateArray = DATE.getCurrentMonth(value, "yyyy-MM-dd 00:00:00");
        } else {
            dateArray = DATE.getCurrentYear(value, "yyyy-MM-1 00:00:00");
        }

        $startDate.val(dateArray[0]);
        $endDate.val(dateArray[1]);
        getSendData();
    });

    $("#addOnTime").on("change", function () {
        $queryForm.find("input[name='addOnDate']").val($(this).val());
        getSendData();
    });

    $("#time-length").find("span").on("click", function () {
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

        if (className.indexOf("daytime") > -1) {// 日
            formDate.datetimepicker(DATE_CONFIG(2, 2, "yyyy-mm-dd")).val(new Date().Format("yyyy-MM-dd"));
            formDate.datetimepicker("update");
            $timeDimen.val("day");
            ENERGY_ANALYSIS.CHART_CONFIG.xAxis.categories = dateData.day;
        } else if (className.indexOf("monthtime") > -1) {// 月
            formDate.datetimepicker(DATE_CONFIG(3, 3, "yyyy-mm")).val(new Date().Format("yyyy-MM"));
            formDate.datetimepicker("update");
            $timeDimen.val("month");
            for(var y = 1 ;y<= getDaysInMonth($('#statisticDate').val()); y++ ){
                dateData.month.push(y + '日')
            }
            ENERGY_ANALYSIS.CHART_CONFIG.xAxis.categories = dateData.month;

        } else if (className.indexOf("yeartime") > -1) {// 年
            formDate.datetimepicker(DATE_CONFIG(4, 4, "yyyy")).val(new Date().Format("yyyy"));
            formDate.datetimepicker("update");
            $timeDimen.val("year");
            ENERGY_ANALYSIS.CHART_CONFIG.xAxis.categories = dateData.year;
        } else {
            return false;
        }
        $queryForm.find("input[name='addOnDate']").val(0);
        $("#statisticDate").change();
    });

    $(".device-btn").on("click", function (event) {

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
        if (_span.attr("class") == "device-choose") {
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
}
/*  泵房设备切换  */
$('input:radio[name="optionsRadios"]').change(function(){
    LOADING.show();
    var typePumpDev = $(this).val()
    $("#queryForm").find("input[name='choose']").val(typePumpDev);
    GLOBAL_TREE_PUMP_EQT =  (typePumpDev == 'pump')? false : true;
    ZtreeInitG(true);
});

/**
 * 根据日期算出该月多少天 y-m
 * @param date
 */
function getDaysInMonth(date){
    var trrData = date.split('-');
    var year = trrData[0];
    var month = trrData[1];
    month = parseInt(month,10);  //parseInt(number,type)这个函数后面如果不跟第2个参数来表示进制的话，默认是10进制。
    var temp = new Date(year,month,0);
    return temp.getDate();
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

/**
 * 获取pie数据
 * @param type
 * @param sendData
 */
function getPieData(type, sendData) {
	$('#energyanalysis_msg_kwh_m3').html("吨水耗电量最高：...");
	$('#energyanalysis_msg_kwh').html("耗电量最少：...");
	$('#energyanalysis_msg_m3').html("产水量最高：...");

    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: CONTEXT_PATH + '/energy/getDevsEnergyStat/'+ type + "?" + new Date().getTime(),
        // data: '{"startDate":"2018052900","endDate":"2018052923","deviceIds":["16090166"],"queryType":"day"}',
        data: sendData,
        dataType: 'JSON',
        success: function(res) {
            if(res.minmax){
                var minmax = res.minmax
                console.log(1111111,minmax)
                if(minmax.kwh_m3){
                    $('#energyanalysis_msg_kwh_m3').html("吨水耗电量最高："+ minmax.kwh_m3.maxName +  minmax.kwh_m3.maxVal )
                }
                if(minmax.m3){
                    $('#energyanalysis_msg_m3').html("产水量最高："+ minmax.m3.maxName + " 日均产水量为" + minmax.m3.maxVal )
                }
                if(minmax.kwh){
                     $('#energyanalysis_msg_kwh').html("耗电量最少："+ minmax.kwh.maxName +  minmax.kwh.minVal + "度")
                    //$('#energyanalysis_msg_kwh').html("耗电量最少："+ minmax.kwh.minVal + "度")

                }
            }
            if(res.count){
                var pieData = [];
                var XData = [];
                res.count.map(function (v) {
                    // v.value = Math.floor(Math.random()*100)
                    if(v){
                        if(v.value != "-" && v.value != "" && v.value != "null" && v.value != "undefined"){
                            pieData.push([v.name, Math.round(v.value*1000*100)/100]);
                            XData.push(v.name);
                        }
                    }
                })
                ENERGY_ANALYSIS.CHART_COMPARE_CONFIG.series[0].data = pieData;
                ENERGY_ANALYSIS.CHART_COMPARE_CONFIG.categories = XData;

                $("#energyanalysis-compare").highcharts(ENERGY_ANALYSIS.CHART_COMPARE_CONFIG);
            }

        },
        error:function(e){
            console.log(e);
        }
    });
}

/**
 * 获取chart数据
 * @param type
 * @param sendData
 */
function getChartData(type, sendData, idNamelist, chooseListPumpName) {
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: CONTEXT_PATH + '/energy/getDevEnergyStat/'+ type + "?" + new Date().getTime(),
        data: sendData,
        dataType: 'JSON',
        success: function(res) {
            if(res){
                var  seriesObj = [], series={}, seriesData = [];
                for(let k in res){
                    seriesObj = [];
                    if(res[k]){
                        for(let v in res[k]){
                            seriesData=[];
                            series={};
                            res[k][v].map(function (k) {
                                // if(k.avgData=="-") k.avgData=Math.floor(Math.random()*100);
                            	if('-' == k.avgData){
                            		seriesData.push(null);
                            	}else{
                            		seriesData.push(Number(k.avgData,3));	
                            	}
                            })

                            series.name = chooseListPumpName[v] +'-'+ idNamelist[v] ;//+ '('+ v +')';
                            series.data = seriesData;
                            seriesObj.push(series);
                        }
                    }
                    chartShow(k, seriesObj)
                }
            }
        },
        error:function(e){
            console.log(e);
        }
    });
}
/**
 * chart 渲染
 * @param type
 * @param seriesObj
 */
function chartShow(type, seriesObj) {
	var charObject = "";
    var optionTitle, yAxisTitle, yAxisFormat
    if(type == "energy" ){
        // 如果一个设备显示  水量浅蓝色#73B7EF，电量浅绿色 #8EF08C,千吨水为深绿色 #148212
        if(GLOBAL_SELECT_LIST.length <= 1){
            ENERGY_ANALYSIS.CHART_CONFIG.colors = ['#148212']
        }else{
            ENERGY_ANALYSIS.CHART_CONFIG.colors =  charColor
        }
        optionTitle = '千吨水耗电量趋势分析对比';
        yAxisTitle = "千吨水耗电量(度/千吨)";
        charObject = $("#energyanalysis-chart-energy" );
    }
    if(type == "kWh" ){
        if(GLOBAL_SELECT_LIST.length <= 1){
            ENERGY_ANALYSIS.CHART_CONFIG.colors = ['#8EF08C']
        }else{
            ENERGY_ANALYSIS.CHART_CONFIG.colors =  charColor
        }
        optionTitle = '用电量趋势对比';
        yAxisTitle = "用电量(度)";
        charObject = $("#energyanalysis-chart-kWh" );
    }
    if(type == "m3" ){
        if(GLOBAL_SELECT_LIST.length <= 1){
            ENERGY_ANALYSIS.CHART_CONFIG.colors = ['#73B7EF']
        }else{
            ENERGY_ANALYSIS.CHART_CONFIG.colors =  charColor
        }
        optionTitle = '用水量趋势分析对比';
        yAxisTitle = "用水量(吨)";
        charObject = $("#energyanalysis-chart-m3" );
    }

    var optionMsg ={
        title: {
            text: optionTitle,
        },
        yAxis: [
            {
                title: {
                    text: yAxisTitle,
                }
            }
        ],
        series:seriesObj
    }
    var optionNew = $.extend(ENERGY_ANALYSIS.CHART_CONFIG, optionMsg);
    charObject.highcharts(optionNew);
}

/**
 * 初始化请求数据
 * @param type
 */
function getSendData(chooseList) {
    LOADING.show();
    var chooseListId = [], chooseListIdName = {}, chooseListPumpName ={} ;
    chooseList = chooseList || GLOBAL_SELECT_LIST
    chooseList.map(function (val) {
        if(val.deviceId){
            chooseListId.push(val.deviceId)
            chooseListIdName[val.deviceId] = val.title;
            chooseListPumpName[val.deviceId] = ztreeBulid.zTreeObj.getNodeByParam('id',val.pId).title;
        }else{
            chooseListId.push(val.pumpHouseId)
            chooseListIdName[val.pumpHouseId] = val.title;
            chooseListPumpName[val.pumpHouseId] = ztreeBulid.zTreeObj.getNodeByParam('id',val.pId).title;
        }
    })
    var sendData={};
    var $queryForm = $("#queryForm");

    var type = $("#queryForm").find("input[name='choose']").val()||'dev'
    var deviceId = GLOBAL_SELECT_DEVICE["deviceId"];
    var queryType =  $("#queryForm").find("input[name='dimen']").val();
    var startDate =  $("#queryForm").find("input[name='startDate']").val();
    var endDate =  $("#queryForm").find("input[name='endDate']").val();

    // data: '{"startDate":"2018052900","endDate":"2018052923","deviceIds":["16090166"],"queryType":"day"}',
    sendData.startDate = startDate
    sendData.endDate = endDate
    // sendData.deviceIds = [deviceId]
    sendData.deviceIds = chooseListId
    sendData.queryType =queryType
    sendData =   JSON.stringify(sendData)

    getPieData(type, sendData) // 获取pie数据
    getChartData(type, sendData, chooseListIdName, chooseListPumpName) // 获取chart数据

    setTimeout(function() {
        LOADING.hide();
    }, 500);
}

/**
 * 根据选择的设备改变实时渲染
 */
function refreshDeviceCheck(obj){
    getSendData(obj);
}

/**
 * 初始化树默认调用
 */
function defChoose(){
    getSendData();
}


/**
 * 初始化设置数据
 */
function setData() {
    var $queryForm = $("#queryForm");
    var DATE =  new Date();
    var currentDate = DATE.Format("yyyy-MM-dd");
    var dateRegion = DATE.getCurrentDay(currentDate, "yyyy-MM-dd HH:mm:ss");

    $queryForm.find("input[name='dimen']").val("day");
    $queryForm.find("input[name='startDate']").val(dateRegion[0]);
    $queryForm.find("input[name='endDate']").val(dateRegion[1]);

    initQueryBox(currentDate)
}

/**
 * 点击树回调函数
 * @param obj
 */
function callRefresh(obj) {
  return
}

/**
 * 初始化
 */
function init() {
    setData();
    defChoose()
}

pageLoading(init)

