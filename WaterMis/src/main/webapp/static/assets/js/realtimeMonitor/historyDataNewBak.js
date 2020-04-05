HISTORY_DATA = {};

HISTORY_DATA.isBoolVar = false;
HISTORY_DATA.maxPoint = [];
HISTORY_DATA.minPoint = [];
var listType = '1';
var metaDeviceList = null;
HISTORY_DATA.DEMO = {
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
    lang: {
        noData: "没有数据，请修改查询条件....." //真正显示的文本
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

pageLoading(initData)
rightMenuMsg();
/**
 * 数据的初始化
 */
function initData() {
    preLoad();
    var currentDate = new Date().Format("yyyy-MM-dd HH");
    var dateRegion = new Date().getCurrentHour(currentDate, "yyyy-MM-dd HH:mm:ss");
    var selectDevices = GLOBAL_SELECT_LIST;
    var selectDeviceIds = [];
    selectDevices.forEach(function(value){
        selectDeviceIds.push(value.id);
    });
    initMetaDeviceList(selectDeviceIds.join(","));
    initQueryBox(currentDate);
    var $queryForm = $("#queryForm");
    $queryForm.find("input[name='deviceIds']").val(selectDeviceIds);
    $queryForm.find("input[name='dimen']").val("hour");
    $queryForm.find("input[name='startDate']").val(dateRegion[0]);
    $queryForm.find("input[name='endDate']").val(dateRegion[1]);
    $queryForm.find("input[name='addOnDate']").val(0);

    //以下是为了解决  日历控件 出错（选择的时候年变成了不存在的年）
    var className = "hourtime";
    DATE_ADD_CONFIG(className);
    var formDate = $(".form_datetime");
    formDate.datetimepicker("remove");

    var $queryForm = $("#queryForm");
    var $timeDimen = $queryForm.find("input[name='dimen']");

    if(className.indexOf("hourtime") > -1) {// 时  日历控件直接进入到小时的选择页面
        formDate.datetimepicker(DATE_CONFIG(1, 1, "yyyy-mm-dd hh")).val(currentDate.Format("yyyy-MM-dd HH"));
        formDate.datetimepicker("update");
        $timeDimen.val("hour");
    }

}
var switchDevice=[];
function initMetaDeviceList(deviceIds){
    var $deviceNorm = $("#device-norm-data");
    $.post(CONTEXT_PATH + "/biz/function/getByDeviceIds",{deviceIds:deviceIds},function(result){
        var $deviceNormData = $deviceNorm.find(".list-group");
        $deviceNormData.empty();
        result.forEach(function(value,index){
        	if(value.type == "ftMP" || value.type == "ftAC" || value.type == "ftMF"
            	|| value.type == "ftT" || value.type == "ftTL" || value.dataType=='uint' || value.dataType== 'real'){
                var li = $("<li class='list-group-item'><label></label></li>");
                var li1 = li.clone();
                var input = $("<input type='checkbox'>")
                    .prop("name", "sameCompare")
                    .prop("value", value["name"])
                    .addClass("minimal");
                if(index==0){input.prop("checked",true);}
                if(switchDevice.length>0){
                    for(var i=0;i<switchDevice.length;i++){
                        if(value["name"]==switchDevice[i]){
                            input.prop("checked",true);
                        }
                    }
                }
                li.find("label").append(input);
                li.find("label").append('<div class="colour_black_'+index%7+'">'+value["memo"]+"</div>");
                li.find("label").prop("title", value["memo"]);
                $deviceNormData.append(li);
        	}
        });
        $("input[type='checkbox'].minimal").icheck({
            checkboxClass: 'icheckbox_minimal-blue',
            radioClass: 'iradio_minimal-blue'
        }).on("click", function () {
            switchDeviceData();
        });
        switchDeviceData();
    });
}
function preLoad() {
    initLayout();
    $("#highcharts-history-data").highcharts(HISTORY_DATA.DEMO)
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
        console.log(dateArray);
        $startDate.val(dateArray[0]);
        $endDate.val(dateArray[1]);

        //以前是时间叠加，现在改成时间段（间接使用时间叠加）
        //计算两个日期之间的 时间差
        var startTime=$("#startDate").val().substring($("#startDate").val().length-2);
        var endTime=$("#statisticDate").val().substring($("#statisticDate").val().length-2);
        if(startTime!="" && endTime!=""){
            var k =parseInt(endTime)-parseInt(startTime)
            $queryForm.find("input[name='addOnDate']").val(k);
        }
        initChart()
    });

    $("#addOnTime").on("change", function () {
        $queryForm.find("input[name='addOnDate']").val($(this).val());
        initChart();
    });

    $("#startDate").on("change", function () {
        //以前是时间叠加，现在改成时间段（间接使用时间叠加）
        //计算两个日期之间的 时间差
        var startTime=$("#startDate").val().substring($("#startDate").val().length-2);
        var endTime=$("#statisticDate").val().substring($("#statisticDate").val().length-2);
        if(startTime!="" && endTime!=""){
            var k =parseInt(endTime)-parseInt(startTime)
            $queryForm.find("input[name='addOnDate']").val(k);
        }

        initChart();
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

    /**
     * 导出excel
     */
    $("#export").on("click", function () {
        var $chart = $("#highcharts-history-data");
        $chart.highcharts().downloadXLS();
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
//勾选/取消测点后触发
function switchDeviceData() {
    var $queryForm = $("#queryForm");
    var indexArray = $("#device-norm-data").find("input").serializeArray();
    var indexs = [];
    for (var i = 0; i < indexArray.length; i++) {
        indexs.push(indexArray[i]["value"]);
    }
    switchDevice=indexs;
    $queryForm.find("input[name='index']").val(indexs.join(","));
    
    //var d = deviceIds.split(',');
    var selectDevices = GLOBAL_SELECT_LIST;
    var selectDeviceIds = [];
    selectDevices.forEach(function(value){
        selectDeviceIds.push(value.id);
    });
    if(Number(selectDeviceIds.length)*Number(indexs.length)>50){
        parent.showErrorMsgVideo("你选中设备的参数曲线将超过50条，数据加载时间可能变长，确认要选中吗？");
    	//parent.openAlertModel("myModalAlert" , "你选中设备的参数曲线将超过50条，数据加载时间可能变长，确认要选中吗？");
    }
    
    initChart();
}
function defChoose() {
    refreshDeviceCheck(GLOBAL_SELECT_LIST);
}
function refreshDeviceCheck(device) {
    var deviceIds;
    if(device){
        var deviceIdsTmp = [];
        device.forEach(function(value){
            deviceIdsTmp.push(value["id"])
        });
        deviceIds = deviceIdsTmp.join(",");
    }else{
        deviceIds = '';
    }
    $("#queryForm").find("input[name='deviceIds']").val(deviceIds);
    initMetaDeviceList(deviceIds)
    // 展示当前所选设备


}
function initChart(){
    LOADING.show();
    var addOnTime =  $("#queryForm").find("input[name='addOnDate']").val();
    var dimen = $("#queryForm").find("input[name='dimen']").val();
    var categories = [];
    if (addOnTime && Number(addOnTime) >= 0) {
        doClean(false);
        categories = DEVICE_DIMEN()[dimen]["categories"];
    }else{
        categories = DEVICE_DIMEN()[dimen]["categories"];
    }
    HISTORY_DATA.DEMO.title.text = "";
    HISTORY_DATA.DEMO.xAxis.labels.formatter = DEVICE_DIMEN()[dimen]["formatter"];
    HISTORY_DATA.DEMO.xAxis.categories = categories;

    HISTORY_DATA.DEMO.exporting.filename =  "历史数据-"+$("#queryForm").find("input[name='endDate']").val();
    $.post(CONTEXT_PATH + "/monitor/statistic/history?" + $("#queryForm").serialize(),function(result){
        HISTORY_DATA.DEMO.series = result
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

$(function(){
    $("#startDate").datetimepicker({
    }).on("changeDate",function(ev){
        var startDate=$("#startDate").val();
        var statisticDate=$("#statisticDate").val();
        if(startDate!="" && statisticDate!=""){
            if(startDate>statisticDate){
                $("#startDate").val('');
                showErrorMsg("开始时间不能大于结束时间!");
                return false;
            }
        }
    });

    $("#statisticDate").datetimepicker({
    }).on("changeDate",function(ev){
        var startDate=$("#startDate").val();
        var statisticDate=$("#statisticDate").val();
        if(startDate!="" && statisticDate!=""){
            if(startDate>statisticDate){
                $("#statisticDate").val('');
                showErrorMsg("结束时间不能小于开始时间!");
                return false;
            }
        }
    });
})
