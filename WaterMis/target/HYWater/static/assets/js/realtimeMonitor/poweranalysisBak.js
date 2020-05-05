
LOADING.show();
var powerPoint = new Object();
var hzPoint = new Object();
var powerMax = 0;
var hzMax = 0;
var powerChar;
var hzChar;
var names;
/*
 * &poweranalysis.html
 * Description of pump operating conditions
 ******************************************
 ******************************************
 */

Highcharts.setOptions({ global: { useUTC: false } });

var currentDevice =  new Object();

(function($) {
    'use strict';
    $("#page-wrapper").css("overflow-y", "hidden");
	// 计算高度
	var calcHeight = function() {
		var height = $(window).height() - $(".top-navbar").height();
		var height2 = height - $(".panel-heading").height() - 45;
		var height3 = height - $("#running-compare").height() - 87;
		$(".pump-running-status").height(height2);
		$(".chart-height").height(height3);
	};
	calcHeight();
	$(window).resize(function() {
		calcHeight();
	});
    // 运行状态模块高度初始化
    powerRunFlag = "1";

    //

	//获取系统默认设备
	var deviceId = GLOBAL_SELECT_DEVICE.deviceId;
	currentDevice.id = deviceId;
    //加载设备运行状态、运行时间、默认功率对比
    initQryFunction(deviceId);

    $("a[data-toggle='tab']").on("shown.bs.tab", function(e) {
        var chart = $(".highcharts-container").parent();
        if (chart.length > 0) {
            $.each(chart, function(i, obj) {
                $(obj).highcharts().reflow();
            });
        }
    });
    initNames();
})(jQuery);

//存放最新查询到的数据  功率、频率
var realPowerVal = 0;
var realHzVal = 0;
//查询的最新时间
var powerRunFlag = "1";

function initNames(){
	jQuery.ajax({
        type : 'POST',
        contentType : 'application/json',
        url : CONTEXT_PATH + '/device/pumpNames/' + currentDevice['id']  + "?"
        + Math.random(),
        dataType : 'json',
        success : function(data) {
        	names = new Array();
        	if(undefined != data && null != data){
        		names = data;
        		 powerStatInit();
        		    hzStatInit();
        		    setInterval(function () {
        		    	performanceRel();
        		    },10000);
        	}else{
        		return;
        	}
        },
        error : function(data) {
        }
    });
}

function performanceRel(){
	jQuery.ajax({
        type : 'POST',
        contentType : 'application/json',
        url : CONTEXT_PATH + '/device/performanceRel/' + currentDevice['id']   + "?"
        + Math.random(),
        dataType : 'json',
        success : function(data) {
        	var powerSeries = powerChar.series;
        	var x = new Date().getTime();
        	if(undefined != powerSeries[0]){
        		x = powerSeries[0].data[powerSeries[0].data.length -1].x;
        	}
            x += 10000;
            var y;
            for(var i = 0;i < powerSeries.length;i++){
            	if(undefined != data['kW'] && null != data['kW'])
            	y = data['kW'][powerSeries[i].name];
            	if(undefined == y || null == y){
            		y = 0;
            	}
            	y = parseFloat(y);
            	powerSeries[i].addPoint([x, y], true, true);
            }
            
            var hzSeries = hzChar.series;
            
            x = new Date().getTime();
        	if(undefined != hzSeries[0]){
        		x = hzSeries[0].data[hzSeries[0].data.length -1].x;
        	}
            x += 10000;
            for(var i = 0;i < hzSeries.length;i++){
            	if(undefined != data['Hz'] && null != data['Hz'])
            	y = data['Hz'][hzSeries[i].name];
            	if(undefined == y || null == y){
            		y = 0;
            	}
            	y = parseFloat(y);
            	hzSeries[i].addPoint([x, y], true, true);
            }
        },
        error : function(data) {
        }
    });
}

function initQryFunction(deviceId) {
    $("#pumpRunStatus").html("");
    jQuery.ajax({
        type : 'POST',
        contentType : 'application/json',
        url : CONTEXT_PATH + '/count/countPowerRun/' + currentDevice['id']  + "?"
        + Math.random(),
        dataType : 'json',
        success : function(data) {
            LOADING.hide();
            if (undefined == data) {
                return;
            } else {
                var pumpHtml = "";
                if (undefined != data[0] && data[0].length > 0) {
                    var pumpStatus = data[0];
                    for (var i = 0; i < pumpStatus.length; i++) {
                        pumpHtml += createPumpRunStatus(pumpStatus[i][0],
                            pumpStatus[i][1]);
                    }
                    $("#pumpRunStatus").html(pumpHtml);
                }
                if("" === pumpHtml){
                    $("#pumpRunStatus").html(createPumpRunStatus("", "-1"));
                }
                var indexVal = 0;
                var boxName = '本月运行时间对比';
                if (undefined != data[1] && data[1].length > 0) {
                    var dataMsg = new Array();
                    for (var i = 0; i < data[1].length; i++) {
                        if(undefined == data[1][i][0] || null == data[1][i][0] || '' == data[1][i][0]){
                            continue;
                        }
                        dataMsg[indexVal] = new Array();
                        dataMsg[indexVal][0] = data[1][i][0];
                        dataMsg[indexVal][1] = parseInt(data[1][i][1]);
                        indexVal++;
                    }
                    initBoxsRunTime(boxName, dataMsg);
                }else{
                    initBoxsRunTime(boxName, []);
                }
                boxName = '本月启停次数对比';
                indexVal = 0;
                if (undefined != data[2] && data[2].length > 0) {
                    var dataMsg = new Array();
                    for (var i = 0; i < data[2].length; i++) {
                        if(undefined == data[2][i][0] || null == data[2][i][0]  || '' == data[2][i][0]){
                            continue;
                        }
                        dataMsg[indexVal] = new Array();
                        dataMsg[indexVal][0] = data[2][i][0];
                        dataMsg[indexVal][1] = parseInt(data[2][i][1]) + 1;
                        indexVal++;
                    }
                    initBoxsRunTimes(boxName, dataMsg);
                }else{
                    initBoxsRunTimes(boxName, []);
                }
                boxName = '额定功率对比';
                indexVal = 0;
                if (undefined != data[3] && data[3].length > 0) {
                    var dataMsg = new Array();
                    for (var i = 0; i < data[3].length; i++) {
                        if(undefined == data[3][i][0] || null == data[3][i][0] || '' == data[3][i][0]){
                            continue;
                        }
                        dataMsg[indexVal] = new Array();
                        dataMsg[indexVal][0] = data[3][i][0];
                        dataMsg[indexVal][1] = parseInt(data[3][i][1]) + 1;
                        indexVal++;
                    }
                    initBoxsRat(boxName, dataMsg);
                }else{
                    initBoxsRat(boxName, []);
                }

            }
        },
        error : function(data) {
            LOADING.hide();
        }
    });
}

//点击右侧列表事件
function callRefresh(device, pre) {
	LOADING.show();
	var deviceId = device.deviceId;
	currentDevice.id = deviceId;
	initQryFunction(deviceId);

    $("a[data-toggle='tab']").on("shown.bs.tab", function(e) {
        var chart = $(".highcharts-container").parent();
        if (chart.length > 0) {
            $.each(chart, function(i, obj) {
                $(obj).highcharts().reflow();
            });
        }
    });
    initNames();
}
function initBoxsRunTime(boxName, values) {
    $('#running-compare')
        .highcharts(
            {
                colors : [ '#24CBE5', '#64E572', '#FF9655', '#058DC7',
                    '#50B432', '#ED561B', '#DDDF00', '#FFF263',
                    '#6AF9C4' ],
                chart : {
                    type : 'pie',
                    options3d : {
                        enabled : true,
                        alpha : 45,
                        beta : 0
                    }
                },
                title : {
                    text : boxName,
                    style : {
                        color : '#2a2323',
                        font : 'bold 14px "Trebuchet MS", Verdana, Microsoft YaHei'
                    }
                },
                legend : {
                    enabled : false
                },
                exporting : {
                    enabled : false
                },
                tooltip : {
                    pointFormat : '{series.name} : <b>{point.y} 小时</b>'
                },
                plotOptions : {
                    pie : {
                        allowPointSelect : true,
                        cursor : 'pointer',
                        depth : 35,
                        dataLabels : {
                            enabled : true,
                            format : '{point.name}',
                            style : {
                                color : '#666',
                                fontSize : '10px',
                                fontFamily : 'Microsoft YaHei'
                            }
                        }
                    }
                },
                series : [ {
                    type : 'pie',
                    name : '运行时间',
                    data : values
                } ],
                credits : {
                    enabled : false
                    // 不显示highCharts版权信息
                }
            });
}
function initBoxsRunTimes(boxName, values) {
    $('#startstop-compare')
        .highcharts(
            {
                colors : [ '#24CBE5', '#64E572', '#FF9655', '#058DC7',
                    '#50B432', '#ED561B', '#DDDF00', '#FFF263',
                    '#6AF9C4' ],
                chart : {
                    type : 'pie',
                    options3d : {
                        enabled : true,
                        alpha : 45,
                        beta : 0
                    }
                },
                title : {
                    text : boxName,
                    style : {
                        color : '#2a2323',
                        font : 'bold 14px "Trebuchet MS", Verdana, Microsoft YaHei'
                    }
                },
                legend : {
                    enabled : false
                },
                exporting : {
                    enabled : false
                },
                tooltip : {
                    pointFormat : '{series.name} : <b>{point.y} 次</b>'
                },
                plotOptions : {
                    pie : {
                        allowPointSelect : true,
                        cursor : 'pointer',
                        depth : 35,
                        dataLabels : {
                            enabled : true,
                            format : '{point.name}',
                            style : {
                                color : '#666',
                                fontSize : '10px',
                                fontFamily : 'Microsoft YaHei'
                            }
                        }
                    }
                },
                series : [ {
                    type : 'pie',
                    name : '启停次数',
                    data : values
                } ],
                credits : {
                    enabled : false
                    // 不显示highCharts版权信息
                }
            });
}
function initBoxsRat(boxName, values) {
    $('#ratedpower-compare')
        .highcharts(
            {
                colors : [ '#24CBE5', '#64E572', '#FF9655', '#058DC7',
                    '#50B432', '#ED561B', '#DDDF00', '#FFF263',
                    '#6AF9C4' ],
                chart : {
                    type : 'pie',
                    options3d : {
                        enabled : true,
                        alpha : 45,
                        beta : 0
                    }
                },
                title : {
                    text : boxName,
                    style : {
                        color : '#2a2323',
                        font : 'bold 14px "Trebuchet MS", Verdana, Microsoft YaHei'
                    }
                },
                legend : {
                    enabled : false
                },
                exporting : {
                    enabled : false
                },
                tooltip : {
                    pointFormat : '{series.name} : <b>{point.y} kW</b>'
                },
                plotOptions : {
                    pie : {
                        allowPointSelect : true,
                        cursor : 'pointer',
                        depth : 35,
                        dataLabels : {
                            enabled : true,
                            format : '{point.name}',
                            style : {
                                color : '#666',
                                fontSize : '10px',
                                fontFamily : 'Microsoft YaHei'
                            }
                        }
                    }
                },
                series : [ {
                    type : 'pie',
                    name : '额定功率',
                    data : values
                } ],
                credits : {
                    enabled : false
                    // 不显示highCharts版权信息
                }
            });
}
function createPumpRunStatus(pumpName, statusFlag) {
    var returnHtml = "";
    if('-1' == statusFlag){
        returnHtml = '<div class="statusPump not-collected"><span class="">未采集</span></div>';
        return returnHtml;
    }
    if ('1' == statusFlag) {
        returnHtml = '<div class="statusPump"><div class="status-left"><img src="static/assets/img/pump.jpg"></div>';
        returnHtml += '<div class="status-right"><span class="pump-status running-status">运行</span><br>';
        returnHtml += '<span class="pump-name">' + pumpName
            + '</span></div></div>';
        return returnHtml;
    }
    if ('2' == statusFlag) {
        returnHtml = '<div class="statusPump"><div class="status-left"><img src="static/assets/img/pump.jpg"></div>';
        returnHtml += '<div class="status-right"><span class="pump-status stop-status">停止</span><br>';
        returnHtml += '<span class="pump-name">' + pumpName
            + '</span></div></div>';
        return returnHtml;
    }
    if ('3' == statusFlag) {
        returnHtml = '<div class="statusPump"><div class="status-left"><img src="static/assets/img/pump.jpg"></div>';
        returnHtml += '<div class="status-right"><span class="pump-status running-status">变频运行</span><br>';
        returnHtml += '<span class="pump-name">' + pumpName
            + '</span></div></div>';
        return returnHtml;
    }
    if ('4' == statusFlag) {

        returnHtml = '<div class="statusPump"><div class="status-left"><img src="static/assets/img/pump.jpg"></div>';
        returnHtml += '<div class="status-right"><span class="pump-status falut-status">故障</span><br>';
        returnHtml += '<span class="pump-name">' + pumpName
            + '</span></div></div>';
        return returnHtml;
    }
    if ('5' == statusFlag) {
        returnHtml = '<div class="statusPump"><div class="status-left"><img src="static/assets/img/pump.jpg"></div>';
        returnHtml += '<div class="status-right"><span class="pump-status running-status">工频运行</span><br>';
        returnHtml += '<span class="pump-name">' + pumpName
            + '</span></div></div>';
        return returnHtml;
    }
    if ('0' == statusFlag) {
        returnHtml = '<div class="statusPump not-collected"><span class="">'+pumpName+' 未采集</span></div>';
        return returnHtml;
    }
    return returnHtml;
}
function powerStat(data){
	Highcharts.setOptions({
	    global: {
	        useUTC: false
	    }
	});
	powerChar = new Highcharts.Chart({
	    chart: {
	    	renderTo: 'realtime-power-chart',
	    	type: 'spline'
	        
	    },
	    title: {
	        text: ''
	    },
	    xAxis: {
	        type: 'datetime',
	        tickPixelInterval: 150
	    },
	    yAxis: {
	        title: {
	            text: '功率(kW)'
	        },
	        plotLines: [{
	            value: 0,
	            width: 1,
	            color: '#808080'
	        }]
	    },
	    tooltip: {
	        crosshairs: {
	            width: 15
	        },
	        shared: true
	    },
        legend: {
        	verticalAlign: "top"
        },
	    exporting: {
	        enabled: false
	    },
	    series: data
	});
}

function powerStatInit(){
	jQuery.ajax({
        type : 'POST',
        contentType : 'application/json',
        url : CONTEXT_PATH + '/device/performancePow/' + currentDevice['id']   + "?"
        + Math.random(),
        dataType : 'json',
        success : function(data) {
        	var seriesData = [];
        	var beginTime = new Date() - 60*60*1000;
        	beginTime =beginTime - beginTime % 10000;
        	var x,y;
        	for(var i = 0;i < 360;i++){
        		x = beginTime + i*10000;
        		for(var j = 0;j < names.length;j++){
        			if(null == seriesData[j]){
        				seriesData.push({name: names[j],data:[]});
        			}
        			
        			if(data[names[j]] == null){
        				y = 0;
        			}else{
        				y = data[names[j]][x];
        				if(undefined == y || null == y){
        					if(0 == i){
        						y = data[names[j]][1]; 
        					}else{
        						y = seriesData[j].data[i-1].y;
        					}
        				}
        			}
        			y = parseFloat(y);
        			
        			seriesData[j].data.push({x:x,y:y});
        		}
        		
        	}
        	powerStat(seriesData);
        },
        error : function(data) {
        }
    });
}

function hzStatInit(){
	jQuery.ajax({
        type : 'POST',
        contentType : 'application/json',
        url : CONTEXT_PATH + '/device/performanceHz/'+ currentDevice['id']   + "?"
        + Math.random(),
        dataType : 'json',
        success : function(data) {
        	var seriesData = [];
        	var beginTime = new Date() - 60*60*1000;
        	beginTime =beginTime - beginTime % 10000;
        	var x,y;
        	for(var i = 0;i < 360;i++){
        		x = beginTime + i*10000;
        		for(var j = 0;j < names.length;j++){
        			if(null == seriesData[j]){
        				seriesData.push({name: names[j],data:[]});
        			}
        			
        			if(data[names[j]] == null){
        				y = 0;
        			}else{
        				y = data[names[j]][x];
        				if(undefined == y || null == y){
        					if(0 == i){
        						y = data[names[j]][1]; 
        					}else{
        						y = seriesData[j].data[i-1].y;
        					}
        				}
        			}
        			y = parseFloat(y);
        			
        			seriesData[j].data.push({x:x,y:y});
        		}
        		
        	}
        	hzStat(seriesData);
        },
        error : function(data) {
        }
    });
}
function hzStat(data){
	Highcharts.setOptions({
	    global: {
	        useUTC: false
	    }
	});
	hzChar = new Highcharts.Chart({
	    chart: {
	    	renderTo: 'pump-frequency-chart',
	    	type: 'spline'
		        
	    },
	    title: {
	        text: ''
	    },
	    xAxis: {
	        type: 'datetime',
	        tickPixelInterval: 1
	    },
	    yAxis: {
	        title: {
	            text: '频率(Hz)'
	        },
	        plotLines: [{
	            value: 0,
	            width: 1,
	            color: '#808080'
	        }]
	    },
	    tooltip: {
	        crosshairs: {
	            width: 15
	        },
	        shared: true
	    },
        legend: {
        	verticalAlign: "top"
        },
	    exporting: {
	        enabled: false
	    },
	    series: data
	});
}

