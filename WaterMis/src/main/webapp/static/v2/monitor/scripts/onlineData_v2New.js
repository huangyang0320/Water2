var  serviceData = [];

var realTimeTableFlag = false;
var realTimeReturnFlag = true;
var realTimeFullFalg = false;
var realTimeAllDateFlag = false;
var firstDeviceServices = [];
var onlineData = {
    loading: {
        show: function () {
            parent.LOADING.show();
        },
        hide: function () {
            if (parent.LOADING) {
                parent.LOADING.hide();
            }
        }
    },
    window: {
        resize: function () {
            var parentWidth = $(window).width()/3 - 30;
        }
    },
    timer: {
        chartInterval: null
    },
    contextPath: '/HYWater/a',
//    device: {
//        id: parent.GLOBAL_SELECT_DEVICE['deviceId'],
//        name: parent.GLOBAL_SELECT_DEVICE['deviceName'],
//        dbName: 'waterdb'
//    },
    chart: {
        gaugeChart: {
            config: {
                backgroundColor: '#fff',
                series: [{
                    radius: '140%',
                    name: '业务指标',
                    type: 'gauge',
                    startAngle: 180,
                    endAngle: 0,
                    splitNumber: 4,
                    center : ['50%', '75%'],
                    axisLine: {
                        lineStyle: {
                            color: [[0.5, '#5fac31'],[0.8, '#ebd374'],[1, '#c23531']],
                            width: 8
                        }
                    },
                    axisLabel: {
                        textStyle: {
                            fontWeight: 'normal',
                            color: '#000',
                            shadowColor : '#fff',
                            shadowBlur: 10
                        }
                    },
                    axisTick: {
                        length: 12,
                        lineStyle: {
                            color: 'auto'
                        }
                    },
                    splitLine: {
                        length: 16,
                        lineStyle: {
                            color: 'auto'
                        }
                    },
                    pointer: {
                        width: 5,
                        shadowColor : '#000',
                        shadowBlur: 10
                    },
                    title : {
                        offsetCenter: [0, '18%'],
                        textStyle: {
                            fontWeight: 'bolder',
                            fontSize: 12,
                            color: '#000'
                        }
                    },
                    detail : {
                        width: 80,
                        height: 22,
                        borderWidth: 1,
                        borderColor: '#ccc',
                        offsetCenter: [0, '-30%'],
                        textStyle: {
                            fontWeight: 'nomal',
                            color: '#000'
                        }
                    },
                    data: [{
                        name: '',
                        value: 0
                    }],
                    min :0,
                    max :2
                }]
            },
            instance: [],
            index: {}
        },
        onlineDataChart: {
            xAxisData: [],
            config: {
                title :{
                    text:''
                },
                chart: {
                    animation: Highcharts.svg,
                    marginRight: 10,
                    zoomType: "xy",
                    backgroundColor: null,
                    borderWidth: 0,
                    plotBackgroundColor: null,
                    plotShadow: false,
                    plotBorderWidth: 0,
                    selectionMarkerFill: 'rgba(0,0,0, 0.2)'
                },
                exporting: {
                    enabled: false
                },
                xAxis: {
                    //type: 'datetime',
                    tickmarkPlacement: 'on',
                    id: 'xAxis-dateTime',
                    categories:[],
                    gridLineWidth: 1,
                    lineColor: '#000',
                    tickColor: '#000',
                    labels: {
                        rotation: -45,
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
                    id: 'yAxisId-default',
                    min:0,
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
                    enabled: true
                },
                tooltip: {
                    crosshairs: {
                        width: 15
                    },
                    shared: true
                },
                plotOptions: {
                    series: {
                        animation: false
                    }
                },
                credits: {
                    enabled: false//不显示highCharts版权信息
                },
                series: []

            },
            instance: [],
            lang: {
                noData: "没有数据....." //真正显示的文本
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
        }
    }
};
var deviceIds = '';
var serviceIds = '';
var selectFirstDeviceId = '';
// change layer style
layer.config({
    extend: 'myskin/style.css',
    skin: 'layer-myskin'
});

parent.pageLoading(initData);
/**
 * 数据的初始化
 */
function initData() {
    initLayout()
    var selectDevices = parent.GLOBAL_SELECT_LIST;
/*    if(parent.PUMPGUIDE_DEVICEID.length>0){
    	//从泵房导航跳转过来
    	for (var i=0;i<parent.GLOBAL_REGION_LIST.length;i++){
    		console.log(parent.PUMPGUIDE_DEVICEID[0]["id"]);
    		console.log(parent.GLOBAL_REGION_LIST[i]["id"]);
    		if(parent.PUMPGUIDE_DEVICEID[0]["id"] == parent.GLOBAL_REGION_LIST[i]["id"]){
    	    	selectDevices=[];
    	    	selectDevices.push(parent.GLOBAL_REGION_LIST[i]);
    	    	break;
    		}
    	}
    }*/
    var selectDeviceIds = [];
    selectDevices.forEach(function(value){
        selectDeviceIds.push(value.id);
    });
    selectFirstDeviceId = selectDevices[0].id;
    deviceIds = selectDeviceIds.join(",");
    //初始化第一台设备测点
    initFirstDeviceServices();
    //加载测点
    initMetaDeviceList();
    //加载全部测点数据
    initServiceDataAll(false);
    setInterval(function () {
    	if(deviceIds.length>0 && serviceIds.length>0){
    		//折现图
            initChart(false)
            //表盘数据和列表
            initServiceDataAll(true)
    	}
    }, 5000);
    setInterval(function () {
    	if(deviceIds.length>0 && serviceIds.length>0){
            //表盘数据和列表
            initServiceDataAll(true)
    	}
    }, 10000);
}

//初始化仪表图
// function initGaugeChar(){
//     onlineData.chart.gaugeChart.instance = [];
//     for (var i = 0; i < 4; i++) {
//         onlineData.chart.gaugeChart.instance.push(initGaugeChart('#gaugeData' + (i + 1), null, onlineData.chart.gaugeChart.config));
//         onlineData.chart.gaugeChart.instance[i].setOption({
//             series: [{
//                 axisLabel: {
//                     formatter: function (value) {
//                         return Number(Number(value).toFixed(2));
//                     }
//                 },
//                 detail: {
//                     formatter: '{value}'
//                 },
//                 min: 0,
//                 max: 100,
//                 data: [{
//                     name: '采集中..',
//                     value: 0
//                 }]
//             }]
//         });
//     }
// }

/**
 * 初始化请求数据
 * @param type
 */
var getSendData = function() {
    var chooseList = parent.GLOBAL_SELECT_LIST;
    onlineData.loading.show();
    if(chooseList){
        var deviceIdsTmp = [];
        chooseList.forEach(function(value){
            deviceIdsTmp.push(value["deviceId"])
        });
        deviceIds = deviceIdsTmp.join(",");
        //如果第一台设备发生变化重新加载第一台设备的测点
        if(selectFirstDeviceId != deviceIdsTmp[0]){
            initFirstDeviceServices();
        }
        selectFirstDeviceId = deviceIdsTmp[0];
    }else{
    	selectFirstDeviceId = '';
        deviceIds = '';
    }
    initMetaDeviceList();
}
var switchDevice=[];
function initMetaDeviceList(){
    var $deviceNorm = $("#device-norm-data");
    $.post(parent.CONTEXT_PATH + "/biz/function/getByDeviceIds",{deviceIds:deviceIds},function(result){
        parent.LOADING.hide();
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
    var indexArray = $("#device-norm-data").find("input").serializeArray();
    var indexs = [];
    serviceIds = '';
    for (var i = 0; i < indexArray.length; i++) {
        indexs.push(indexArray[i]["value"]);
        serviceIds += indexArray[i]["value"]+",";
    }
    switchDevice=indexs;
    if(indexs.length == 0){
        onlineData.chart.onlineDataChart.config.series = [];
//        onlineData.chart.onlineDataChart.config.xAxis.categories = [];
//        onlineData.chart.gaugeChart.instance = [];
        onlineData.chart.onlineDataChart.instance = Highcharts.chart('onlineDataChart', onlineData.chart.onlineDataChart.config);
    	return;
    }
    //
    var d = deviceIds.split(',');
    if(Number(d.length)*Number(indexs.length)>50){
        parent.showErrorMsgVideo("你选中设备的参数曲线将超过50条，数据加载时间可能变长，确认要选中吗？");
    	//parent.openAlertModel("myModalAlert" , "你选中设备的参数曲线将超过50条，数据加载时间可能变长，确认要选中吗？");
    }
    onlineData.loading.show();
    initChart(true);
}

//加载实时数据折现图
function initChart(clearFlag){
	//查询前选中的测点，和查询结果返回后的测点比对，如果不一致进行处理
    var currentServiceIds = serviceIds;
    var currentSelectDeviceId = deviceIds;
    //如果上次查询的结果还没有返回就直接返回不执行查询
    if(!realTimeReturnFlag && !clearFlag){
        return
    }
    realTimeReturnFlag = false
    $.post(onlineData.contextPath + "/monitor/v2/devices/"+deviceIds+"/data/"+serviceIds+"/latest/"+"waterdb",function(result){
        realTimeReturnFlag = true
        var  flag = false;
        //解决修改测点后折线图长度不一致问题
        if(clearFlag){
            onlineData.chart.onlineDataChart.config.series = [];
            onlineData.chart.onlineDataChart.config.xAxis.categories = [];
            onlineData.chart.gaugeChart.instance = [];
        }
        //修改测点或设备后 处理
        if(serviceIds != currentServiceIds || currentSelectDeviceId != deviceIds){
            return;
        }
        for (var i = 0; i < result.length; i++) {
            if(!onlineData.chart.onlineDataChart.config.series[i]){
                onlineData.chart.onlineDataChart.config.series[i]={name:"",data:[]}
            }
            onlineData.chart.onlineDataChart.config.series[i].name = result[i]["name"];
            onlineData.chart.onlineDataChart.config.series[i].data.push(result[i]["data"][0]);
            if(onlineData.chart.onlineDataChart.config.series[i].data.length > 100){
                onlineData.chart.onlineDataChart.config.series[i].data.shift();
                flag = true;
            }
        }
        onlineData.chart.onlineDataChart.config.xAxis.categories.push(moment(new Date()).format("HH:mm:ss"));
        if(flag){
            onlineData.chart.onlineDataChart.config.xAxis.categories.shift();
        }
        if(realTimeFullFalg){
            onlineData.chart.onlineDataChart.instance = Highcharts.chart('enlargeOnlineDataChart', onlineData.chart.onlineDataChart.config);
        }else{
            onlineData.chart.onlineDataChart.instance = Highcharts.chart('onlineDataChart', onlineData.chart.onlineDataChart.config);
        }
        //当前展示的是折现tab则取消
        if(!realTimeTableFlag){
            onlineData.loading.hide();
        }
    });
}

function initFirstDeviceServices(){
    var deviceIdArry = deviceIds.split(",");
    var deviceId = deviceIdArry[0];
    //是否查询返回结果
    realTimeAllDateFlag = false;
    $.post(onlineData.contextPath + "/monitor/v2/device/"+deviceId+"/services",function(result){
    	firstDeviceServices = result;
    	initServiceDataAll(false);
    });
}

function initServiceDataAll(rd){
    $.post(onlineData.contextPath + "/monitor/v2/devices/"+selectFirstDeviceId+"/data/latest/"+"waterdb",function(result){
    	//是否查询返回结果
       	realTimeAllDateFlag = true;
        serviceData = result;
        //表盘数据
        initGaugeCharts(serviceData, rd);
        //列表数据
        initRealtimeData();
    });
}

function initGaugeCharts(serviceData, rd) {
    if(!rd){
        onlineData.chart.gaugeChart.instance = [];
        $('#rowsContainerDivs').html('');
    }
    var index = 0;
    // var chart
    for (var i = 0; i < firstDeviceServices.length; i++) {
        var service = firstDeviceServices[i];
        if(service.IS_CUMULATIVE !==1 && service.visible == '1'){
            if(! rd){
                let str = $('<div class="col-bg-3 col-ss" style="margin-right: 10px;">\n' +
                    '            <div class="panel panel-default" id="gaugeData'+ (index + 1)+'" style="height:150px;"></div>\n' +
                    '        </div>')
                $('#rowsContainerDivs').append(str)
                onlineData.chart.gaugeChart.instance.push(initGaugeChart('#gaugeData' + (index + 1), null, onlineData.chart.gaugeChart.config));
            }
            onlineData.chart.gaugeChart.index[service['serviceId']] = index;
            if(onlineData.chart.gaugeChart.instance[index]){
                onlineData.chart.gaugeChart.instance[index].setOption({
                    series: [{
                        axisLabel: {
                            formatter: function (value) {
                                return Number(Number(value).toFixed(2));
                            }
                        },
                        detail: {
                            formatter: '{value}' + (service['unit'] ? service['unit'] : '')
                        },
                        min: 0,
                        max: service['max'] ? service['max'] : 5,
                        data: [{
                            name: service.serviceName,
                            value: getValue(service.serviceName,serviceData)
                            /*value:0*/
                        }]
                    }]
                });
            }
            index++;
        }
    }
    if(index>4){
        $('#rowsContainerDivs').find('.col-bg-3').css({width:'412px',height:'150px'});
        $('#rowsContainerDivs').find('canvas').css('left','-50px');
        $('#rowsContainerDivs').css('width', index * 424 +'px' );
        $('#rowsContainerDivOuter').css('overflow-x', 'scroll' );
    }else {
        $('#rowsContainerDivs').find('.col-bg-3').css({width:'24.2%',height:'150px'});
        $('#rowsContainerDivs').find('canvas').css('left','0px');
        $('#rowsContainerDivs').css('width', '100%' );
        $('#rowsContainerDivOuter').css('overflow-x', 'scroll' );
    }
    if (index < 4) {
        for (var i = 0; i < firstDeviceServices.length; i++) {
            var service = firstDeviceServices[i];
            if (service.IS_CUMULATIVE !== 1 && service.visible !== '1' && service.min !== '' && service.max !== '') {
                onlineData.chart.gaugeChart.index[service['serviceId']] = index;
                if( onlineData.chart.gaugeChart.instance[index]){
                    onlineData.chart.gaugeChart.instance[index].setOption({
                        series: [{
                            axisLabel: {
                                formatter: function (value) {
                                    return Number(Number(value).toFixed(2));
                                }
                            },
                            detail: {
                                formatter: '{value}' + (service['unit'] ? service['unit'] : '')
                            },
                            min: 0,
                            max: service['max'] ? service['max'] : 100,
                            data: [{
                                name: service['serviceName'],
                                value: getValue(service.serviceName,serviceData)
                            }]
                        }]
                    });
                    index++;
                }
            }
        };
    }
    if (index < 4) {
        for (var chartIndex = index; chartIndex < 4; chartIndex++) {
            if(onlineData.chart.gaugeChart.instance[chartIndex]){
                onlineData.chart.gaugeChart.instance[chartIndex].setOption({
                    series: [{
                        axisLabel: {
                            formatter: function (value) {
                                return Number(Number(value).toFixed(2));
                            }
                        },
                        detail: {
                            formatter: '{value}'
                        },
                        min: 0,
                        max: 100,
                        data: [{
                            name: '未采集测点',
                            value: 0
                        }]
                    }]
                });
            }
        }
    }
}

function getValue(serviceName,serviceData) {
    if(serviceData == null || serviceData.length == 0){
        return 0;
    }
    for(var i=0;i<serviceData.length;i++){
        var info = serviceData[i];
        if(serviceName == info.serviceName){
            return info.serviceValue;
        }
    }
}

function initGaugeChart(domId, theme, option){
    var chart = echarts.init(document.querySelector(domId));
    chart.setOption(option);
    return chart;
}

//实时数据列表tab显示
function realDataTablClick(){
	initRealtimeData();
	realTimeTableFlag = true;
}
//加载实时数据列表数据
function initRealtimeData(){
    if(!realTimeAllDateFlag){
    	onlineData.loading.show();
    	return;
    }
    onlineData.loading.hide();
    if(serviceData != null){
        $("#realtimeTbody1").html("");
        $("#realtimeTbody2").html("");
        $("#realtimeTbody3").html("");
        for(var i=0;i<serviceData.length;i++){
        	html = "";
            var info = serviceData[i];
            var value = info.serviceValue
            if(info.serviceUnit){
                value +=' '+info.serviceUnit
            }
            html += '<tr class="even gradeA">'
            html +="<td>"+info.serviceName+"</td>";
            if(info.type == "ftPS" || info.type == "ftRS" || info.type == "ftVS"
            	|| info.type == "ftA"){
            	if(info.serviceName.search("故障") != -1 || info.serviceName.search("报警") != -1){
                	if(value == 1){
                		html += '<td><img src="static/assets/img/alarm.png" class="img-circle" /></td>'
                	}else{
                		html += '<td><img src="static/assets/img/unAlarm.png" class="img-circle" /></td>'
                	}
            	}else{
                	if(value == 1){
                		html += '<td><img src="static/assets/img/run.png" class="img-circle" /></td>'
                	}else{
                		html += '<td><img src="static/assets/img/unRun.png" class="img-circle" /></td>'
                	}
            	}
            }else{
            	html +="<td>"+value+"</td>";
            }
            html +="<td>"+moment(info.serviceDate).format("YYYY-MM-DD HH:mm:ss")+"</td>";
            html += "</tr>";
            if(info.type == "ftMP" || info.type == "ftAC" || info.type == "ftMF"
            	|| info.type == "ftT" || info.type == "ftTL" || info.type == "ftWQ" || info.type == "ftEE"){
                $("#realtimeTbody1").append(html);
            }else if(info.type == "ftPS" || info.type == "ftRS" || info.type == "ftVS"
            	|| info.type == "ftA"){
            	$("#realtimeTbody3").append(html);
            }else if(info.type == "ftSP"){
            	$("#realtimeTbody2").append(html);
            }
        }
    }
}

//实时数据折现图 tab点击
function onRealtimeChart(){
    realTimeTableFlag = false;
    initChart(false);
    setTimeout(function(){$('#onlineDataChart').highcharts().reflow();}, 300);
}

onlineData.window.resize();

// 重新渲染 echarts
$(window).resize(function() {
    onlineData.window.resize();
});

//放大显示
function enlarge() {
    document.getElementById('bigRowDiv').style.display='block';
    document.getElementById('fade').style.display='block'
    realTimeFullFalg = true;
    onlineData.chart.onlineDataChart.instance = Highcharts.chart('enlargeOnlineDataChart', onlineData.chart.onlineDataChart.config);
}

//返回
function back() {
    document.getElementById('bigRowDiv').style.display='none';
    document.getElementById('fade').style.display='none'
    realTimeFullFalg = false;
    onlineData.chart.onlineDataChart.instance = Highcharts.chart('onlineDataChart', onlineData.chart.onlineDataChart.config);
}