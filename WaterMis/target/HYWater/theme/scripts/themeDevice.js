var onlineData = {
	loading : {
		show : function() {
			parent.LOADING.show();
		},
		hide : function() {
			if (parent.LOADING) {
				parent.LOADING.hide();
			}
		}
	}
};
var inData = [];
var outData = [];
var gitData = [];
var taskisRun = '1';
var currentDevice = new Object();
currentDevice.id = "1212";
currentDevice.name = "西安";

var closeLoading = function() {
	// 关闭首页 Loading 加载层
	$('#ajax-loader', parent.document).fadeOut();
};
var cascadeView = function() {
    location.reload();
};
parent.pageLoading(initThem)
function initThem() {
	var deviceId = parent.GLOBAL_SELECT_DEVICE['deviceId'];
	var name = parent.GLOBAL_SELECT_DEVICE['deviceName'];
	initPage(deviceId, name);

	// init bar : trend chart of electric、alarm、maintenance、order
	$(".tab-ul>li>a").on(
			'click',
			function() {
				var _this = $(this), targetId = _this.attr('data-href');
				$('.tab-ul').children().removeClass('active');
				_this.parent().addClass('active');
				$('.tab-content').children().removeClass('tab-active');
				$(targetId).addClass('tab-active');
				// reload echart
				var charUrl = "";
				if ("#electricTrend" == targetId) {
					charUrl = "countEleStatByDevice";
				} else if ("#alarmTrend" == targetId) {
					charUrl = "countAlarmTimesStatByDevId";
				} else if ("#maintenanceTrend" == targetId) {
					charUrl = "countMaintenanceTrendDev";
				} else if ("#orderTrend" == targetId) {
					charUrl = "countWorkStat";
				} else {
					return;
				}
				initStatisticHourAla(targetId.split('#')[1], _this
						.attr('data-unit'), _this.text().substring(0, 4),
						charUrl,deviceId);
			});

	$(".tab-ul>li>a").eq(0).click();

};

// 修改用户默认连接
function initPage(id, name) {
	summaryStat(id, name); // 初始化加载数据
	countDeviceArea(id); // 区域设备占比
	staticUserWork();// 人员统计
	statisticEleByLocationInit(id); // 供水高峰耗电量对比
	// initWorkStat();
	// initStatisticHourEle(0, 1); // 日时刻耗电量 上一天/下一天
	countDeviceType(id); // 设备类型占比
	traSuppPumpStat(id);
	statisticAlarmType(id); // 告警信息类型对比
	devEleCom(id);
	onlineData.loading.hide();
};

// 统计设备信息
function summaryStat(id, name) {
	jQuery.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : 'a' + '/count/summaryStat/' + id + "?" + Math.random(),
		dataType : 'json',
		success : function(data) {
			if (undefined == data) {
				return;
			} else {
				// $("#supplierChart").css("background-image","url(http://36.41.173.156:8000/HFWater/upload/1513824658733.bmp)");
				$("#pump_num").html(name);
				if(data.v1) $("#v1").html(data.v1);
				if(data.v2) $("#v2").html(data.v2);
				if(data.statEle) $("#statEle").html(data.statEle);
				if(data.v4) $("#v4").html(data.v4);
				if(data.v3) $("#statEle").html(data.v3);
				if(data.devRunTime.indexOf('-') > -1){
					$("#runTime").html('-');
				}else{
					$("#runTime").html(data.devRunTime);
				}
				
				$("#cityName").html(currentDevice.name + "设备分布情况");
				if ('' == data.videoCode) {
					$("#onlineDataPage").attr('src', '');
				} else {
					$("#onlineDataPage")
							.attr(
									'src',
									'video8700.html?videoNo=' + data.videoCode + '&rnd=' + new Date().getTime()
										);
				}
				if ('' != data.devicePicUrl && null != data.devicePicUrl) {
					$("#deviceImg").attr(
							'src', data.devicePicUrl);
				} else {
					$("#deviceImg")
							.attr('src', 'static/assets/img/default.png');
				}
			}
		},
		error : function(data) {
			$("#cityName").html(currentDevice.name + "设备分布情况");
		}
	});
}
// 日时刻告警数 上一天/下一天
function initStatisticHourAla(target, unit, name, charUrl,deviceId) {
	jQuery.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : 'a' + '/count/' + charUrl +'/'+ deviceId + "?" + Math.random(),
		dataType : 'json',
		success : function(data) {
			if (data.length < 2) {
				return;
			} else {
				var names = new Array();
				var pvs = new Array();
				var index = 0;
				for (var i = 0; i < data.length; i++) {
					if (null != data[i].name) {
						names[index] = data[i].name;
						pvs[index] = parseInt(data[i].memo);
						index++;
					}
				}
				trendChartConfig(target, unit, name, names, pvs);
			}
		},
		error : function(data) {
		}
	});
}
function statisticHourAla(names, pvs) {
	$('#dailyAlarmCompare').highcharts(
			{
				colors : [ '#f45b5b', '#8085e9', '#8d4654', '#7798BF',
						'#aaeeee', '#ff0066', '#eeaaee', '#55BF3B', '#DF5353',
						'#7798BF', '#aaeeee' ],
				title : {
					text : '日时刻报警数对比',
					style : {
						fontSize : '15px',
						fontWeight : 'bold',
						fontFamily : 'Microsoft YaHei'
					}
				},
				exporting : {
					enabled : false
				},
				tooltip : {
					enabled : false
				},
				legend : {
					enabled : false
				},
				plotOptions : {
					series : {
						borderWidth : 0,
						dataLabels : {
							enabled : true,
							format : '{point.y}',
							style : {
								color : '#333',
								fontSize : '12px',
								fontFamily : 'Microsoft YaHei',
								fontWeight : 'normal'
							}
						}
					}
				},
				xAxis : {
					tickInterval : 1,
					lineColor : '#999',
					labels : {
						style : {
							fontSize : '12px',
							fontFamily : 'Microsoft YaHei'
						}
					},
					categories : names
				},
				yAxis : {
					title : false,
					gridLineColor : '#eee',
					labels : {
						enabled : false
					}
				},
				series : [ {
					data : pvs
				} ],
				credits : {
					enabled : false
				// 不显示highCharts版权信息
				}
			});
}

// 修改用户默认连接
function modDefaultDB(areaId) {
	var cityMsg = $("#breadcrumb").text();
	jQuery.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : 'a' + '/system/modDefaultDB/' + areaId + "?" + Math.random(),
		dataType : 'json',
		success : function(data) {
			if (data.length > 0) {
				window.location.reload();
			} else {
				$("#breadcrumb").text(cityMsg);
				layer.msg('系统错误，请稍后再试', {
					icon : 7,
					offset : [ '30%' ]
				});
			}
		},
		error : function(data) {
			$("#breadcrumb").text(cityMsg);
			layer.msg('系统错误，请稍后再试', {
				icon : 7,
				offset : [ '30%' ]
			});
		}
	});
}
function nodataMessage(elem, msg, method, type) {
	var $nodataHTML = $("<div></div>");
	var className = "nodata-match";
	if (type != undefined && $.trim(type) != "" && type != null) {
		className = className + "-" + type;
	}
	$nodataHTML.addClass(className);
	$nodataHTML.text(msg);
	if (method == "append") {
		$(elem).append($nodataHTML);
	} else {
		$(elem).html($nodataHTML);
	}
}
// 区位数据统计
function countLocation() {
	jQuery.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : 'a' + '/count/countLocation' + "?" + Math.random(),
		dataType : 'json',
		success : function(data) {
			if (undefined == data) {
				return;
			} else {
				$("#areaCountLocation").html("");
				var htmlMsg = "";
				for (var i = 0; i < data.length; i++) {

					if (undefined != data[i].name) {
						htmlMsg = htmlMsg + '<label>' + data[i].name
								+ '</label><span>' + data[i].memo
								+ '</span><br>';
					}
				}
				$("#areaCountLocation").html(htmlMsg);
			}
		},
		error : function(data) {

		}
	});
}

/** ************************************************** */
/** 环形饼图统计模板 */
/** ************************************************** */
function ringChar(dataMsg, titleName, detailName, colors, divObject) {
	divObject.highcharts({
		colors : colors,
		chart : {
			type : 'pie',
			style : {
				fontSize : '11px',
				fontFamily : 'Microsoft YaHei'
			},
			options3d : {
				enabled : true,
				alpha : 45
			}
		},
		title : {
			text : titleName,
			style : {
				fontSize : '15px',
				fontWeight : 'bold',
				fontFamily : 'Microsoft YaHei'
			}
		},
		exporting : {
			enabled : false
		},
		plotOptions : {
			pie : {
				allowPointSelect : true,
				cursor : 'pointer',
				innerSize : 100,
				dataLabels : {
					enabled : true,
					style : {
						color : '#666',
						fontSize : '12px',
						fontFamily : 'Microsoft YaHei'
					}
				},
				depth : 45
			}
		},
		series : [ {
			name : detailName,
			style : {
				fontSize : '12px',
				fontFamily : 'Microsoft YaHei'
			},
			data : dataMsg
		} ],
		credits : {
			enabled : false
		// 不显示highCharts版权信息
		}
	});
}

/** ************************************************** */
/* 圆柱饼图统计模板 */
/** ************************************************** */
function cylinderChar(dataMsg, titleName, detailName, colors, divObject,
		closeLoading) {
	divObject.highcharts({
		colors : colors,
		chart : {
			type : 'pie',
			backgroundColor : null,
			style : {
				fontSize : '11px',
				fontFamily : 'Microsoft YaHei'
			},
			options3d : {
				enabled : true,
				alpha : 45,
				beta : 0
			}
		},
		title : {
			text : titleName,
			style : {
				fontSize : '15px',
				fontWeight : 'bold',
				fontFamily : 'Microsoft YaHei'
			}
		},
		exporting : {
			enabled : false
		},
		legend : {
			itemStyle : {
				fontWeight : 'bold',
				fontSize : '12px'
			}
		},
		xAxis : {
			gridLineWidth : 1,
			labels : {
				style : {
					fontSize : '12px'
				}
			}
		},
		yAxis : {
			// minorTickInterval: 'auto',
			title : {
				style : {
					textTransform : 'uppercase'
				}
			},
			labels : {
				style : {
					fontSize : '12px'
				}
			}
		},
		plotOptions : {
			pie : {
				allowPointSelect : true,
				cursor : 'pointer',
				depth : 35,
				dataLabels : {
					enabled : true,
					style : {
						color : '#666',
						fontSize : '11px',
						fontFamily : 'Microsoft YaHei'
					}
				}
			}
		},
		series : [ {
			type : 'pie',
			name : detailName,
			style : {
				fontSize : '12px',
				fontFamily : 'Microsoft YaHei'
			},
			data : dataMsg
		} ],
		credits : {
			enabled : false
		// 不显示highCharts版权信息
		},
		// Highstock specific
		navigator : {
			xAxis : {
				gridLineColor : '#D0D0D8'
			}
		},
		rangeSelector : {
			buttonTheme : {
				fill : 'white',
				stroke : '#C0C0C8',
				'stroke-width' : 1,
				states : {
					select : {
						fill : '#D0D0D8'
					}
				}
			}
		},
		scrollbar : {
			trackBorderColor : '#C0C0C8'
		},
		// General
		background2 : '#E0E0E8'
	}, function() {
		if (closeLoading) {
			closeLoading();
		}
	});
}

// 设备类型占比
function countDeviceType(id) {
	jQuery.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : 'a' + '/count/pumpRatedPower/'+id + "?" + Math.random(),
		dataType : 'json',
		success : function(data) {
			if (undefined == data || data.length < 1) {
				var dataMsg = new Array();
				var dataVal = new Array();
				deviceTypeStat(dataMsg,dataVal);
				
			} else {
				var dataMsg = new Array();
				var dataVal = new Array();
				for (var i = 0; i < data.length; i++) {
					dataMsg[i] = data[i].name;
					dataVal[i] = parseInt(data[i].memo);
				}
				deviceTypeStat(dataMsg,dataVal);
			}
		},
		error : function(data) {

		}
	});
}
function initWorkStat() {
	$("#orderHtml").html("");
	jQuery.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : 'a' + '/count/orderTypeStat' + "?" + Math.random(),
		dataType : 'json',
		success : function(data) {
			if (undefined == data) {
				return;
			} else {
				var dataMsg = new Array();
				dataMsg[0] = 0;
				dataMsg[1] = 0;
				dataMsg[2] = 0;
				dataMsg[3] = 0;
				var orderHtml = "";
				for (var i = 0; i < data.length; i++) {
					orderHtml += '<tr><td>' + i + '</td><td>' + data[i].name
							+ '</td><td>' + data[i].memo + '</td></tr>';
					if ('待确认' == data[i].name) {
						dataMsg[0] = parseInt(data[i].memo);
					}
					if ('已确认' == data[i].name) {
						dataMsg[1] = parseInt(data[i].memo);
					}
					if ('已完成' == data[i].name) {
						dataMsg[2] = parseInt(data[i].memo);
					}
					if ('退回' == data[i].name) {
						dataMsg[3] = parseInt(data[i].memo);
					}
				}
				$("#orderHtml").html(orderHtml);
				if (0 == dataMsg[0]) {
					dataMsg[1] = 1;
				}
				if (0 == dataMsg[2]) {
					dataMsg[3] = 1;
				}
				// orderTypeStatDetail(dataMsg);
			}
		},
		error : function(data) {

		}
	});
}
function traSuppPumpStat(id) {
	inData = [];
	outData = [];
	gitData = [];
	jQuery.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : 'a' + '/count/staticPressMin/' + id + "?" + Math.random(),
		dataType : 'json',
		success : function(data) {
			var nowTime = new Date().getTime();
			nowTime = nowTime + 8 * 60 * 60 * 1000;
			var valData;
			for (var i = 0; i < 12; i++) {

				valData = new Object();
				valData.value = [];
				valData.value[0] = nowTime + (i - 12) * 5000;
				valData.value[1] = data["in"][i];
				inData.push(valData);

				valData = new Object();
				valData.value = [];
				valData.value[0] = nowTime + (i - 12) * 5000;
				valData.value[1] = data["out"][i];
				outData.push(valData);

				valData = new Object();
				valData.value = [];
				valData.value[0] = nowTime + (i - 12) * 5000;
				valData.value[1] = data["git"][i];
				gitData.push(valData);
			}
			supModStat(id);
		},
		error : function(data) {

		}
	});

}
function runStatDevice(deviceId) {
	jQuery.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : 'a' + '/count/runStatDevice/' + deviceId + "?" + Math.random(),
		dataType : 'json',
		success : function(data) {
			if (undefined == data || data.length < 1) {
				return;
			} else {
				var dataMsg = new Array();
				var areaDeviceList = new Array();
				var deviceSum = 0;
				var maxNum = 0;
				var j = 0;
				for (var i = 0; i < data.length; i++) {
					if (undefined == data[i][0] || null == data[i][0] || '' == data[i][0]) {
						continue;
					}
					var values = new Object();
					values.name = data[i][0];
					values.value = parseInt(data[i][1]);
					deviceSum += values.value;
					dataMsg[j] = values;
					j++;
					areaDeviceList.push(values);
				}
				deviceCompair(deviceSum, areaDeviceList);
			}
		},
		error : function(data) {
		}
	});

}
function countDeviceArea(deviceId) {
	runStatDevice(deviceId);
	jQuery.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : 'a' + '/count/deviceStatTheme/' + deviceId + "?" + Math.random(),
		dataType : 'json',
		success : function(data) {
			if (undefined == data || data.length < 1) {
				return;
			} else {
				var dataMsg = new Array();
				var areaDeviceList = new Array();
				var deviceSum = 0;
				var maxNum = 0;
				for (var i = 0; i < data.length; i++) {
					var values = new Object();
					values.name = data[i].name;
					values.value = parseInt(data[i].memo);
					if (maxNum < values.value) {
						maxNum = values.value;
					}
					deviceSum += values.value;
					dataMsg[i] = values;
					if (values.value > 0) {
						areaDeviceList.push(values);
					}
				}
				//deviceCompair(deviceSum, areaDeviceList);
				var deviceHtml = "";
				var temperature = '-';
				var moderate = '-';
				for (var i = 0; i < data.length; i++) {
					if(data[i][0].indexOf('环境温度') > -1){
						temperature = data[i][2];
						continue;
					}
					if(data[i][0].indexOf('湿度') > -1){
						moderate = data[i][2];
						continue;
					}
					values.name = data[i][0];
					values.value = data[i][1];
					deviceHtml += '<li><span style="width: 30%">';
					deviceHtml += data[i][0];
					deviceHtml += '</span><span style="width: 35%">';
					if('0' == data[i][1]){
						deviceHtml += '停止';
					}else if('1' == data[i][1]){
						deviceHtml += '运行';
					}else if('2' == data[i][1]){
						deviceHtml += '告警';
					}else{
						deviceHtml += '-';
					}
					deviceHtml += '</span><span style="width: 35%">';
					if (0 == parseInt(data[i].memo)) {
						deviceHtml += '-</span></li>';
					} else {
						deviceHtml += data[i][2];
						deviceHtml += '</span></li>';
					}

				}
				var titleMsg = '泵房温度: '+temperature+'- | 泵房湿度: '+moderate;
				$("#titleMsg").html(titleMsg);
				$("#deviceRate").html(deviceHtml);
				if (data.length > 5) {
					var aa = $('.areaDeviceNumCompare .compareData');
					$('.areaDeviceNumCompare .compareData').myScroll({
						speed : 40,
						rowHeight : 28
					});
				}
				// createMap(maxNum,dataMsg);
			}
		},
		error : function(data) {
		}
	});
}

// 维保信息区域对比
function statisticRepaicType() {
	var titleName = "维保信息区域对比";
	var detailName = "维保次数";
	var divObject = $('#MaintenanceCompareByArea');
	/*
	 * var colors = [ '#487285', '#55BF3B', '#DF5353', '#7798BF', '#aaeeee',
	 * '#f45b5b', '#8085e9', '#8d4654', '#7798BF', '#aaeeee', '#eeaaee',
	 * '#ff0066' ];
	 */
	var colors = parent.GLOBAL.setHighchartsColors({
		baseColor : '#f5be32',
		chartObj : Highcharts,
		flag : true
	});
	jQuery.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : 'a' + '/count/statisticRepaicType' + "?" + Math.random(),
		dataType : 'json',
		success : function(data) {
			if (undefined == data) {
				nodataMessage(divObject, "没有" + titleName + "数据.", "html",
						"ala");
				return;
			} else {
				var dataMsg = new Array();
				for (var i = 0; i < data.length; i++) {
					dataMsg[i] = new Array();
					dataMsg[i][0] = data[i].name;
					dataMsg[i][1] = parseInt(data[i].memo);
				}
				if (dataMsg.length < 1) {
					nodataMessage(divObject, "没有" + titleName + "数据.", "html",
							"ala");
				} else {
					cylinderChar(dataMsg, titleName, detailName, colors,
							divObject);
				}
			}
		},
		error : function(data) {
			nodataMessage(divObject, "没有" + titleName + "数据.", "html", "ala");
		}
	});
}

// 人员状态对比
function staticUserWork() {
	jQuery.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : 'a' + '/count/staticUserWork' + "?" + Math.random(),
		dataType : 'json',
		success : function(data) {
			if (undefined == data || data.length == 0) {
				$("#perRun_num").html('-');
				$("#perNot_num").html('-');
				return;
			} else {
				for (var i = 0; i < data.length; i++) {
					if (data[i].memo.indexOf('空闲') > -1) {
						$("#perNot_num").html(data[i].name);
					} else {
						$("#perRun_num").html(data[i].name);
					}
				}
			}
		},
		error : function(data) {
			$("#perRun_num").html('-');
			$("#perNot_num").html('-');
		}
	});
}

// 区域维保项目对比
function devEleCom(id) {
	jQuery.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : 'a' + '/count/devEleCom/' + id + "?" + Math.random(),
		dataType : 'json',
		success : function(data) {
			if (undefined == data || data.length == 0) {
				closeLoading();
				return;
			} else {
				var dataMsg = new Array();
				for (var i = 0; i < data.length; i++) {
					var dataVal = new Object();
					dataVal.name = data[i].name;
					dataVal.value = parseInt(data[i].memo);
					dataMsg[i] = dataVal;
				}
				areaProjectStat(dataMsg);
			}
		},
		error : function(data) {
			$("#perRun_num").html('-');
			$("#perNot_num").html('-');
		}
	});
}
// 日时刻耗电量统计
function initStatisticHourEle(time, type) {
	var titleName = "";
	if ('1' == type) {
		titleName += "日 每小时";
	} else if ('2' == type) {
		titleName += "月 每天";
	} else if ('3' == type) {
		titleName += "年 每月";
	}
	jQuery.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : 'a' + '/count/statisticHourEle/' + time + "/" + type + "?"
				+ Math.random(),
		dataType : 'json',
		success : function(data) {
			if (data.length < 2) {
				// statisticHourEle(new Array(),new Array());
				// LOADING.hide();
				nodataMessage($("#dailyElectricityCompare"), "没有匹配到 "
						+ data[0].memo + titleName + "耗电量数据.", "html", "ele");
				return;
			} else {
				if ($(".nodata-match-ele")) {
					$(".nodata-match-ele").remove();
				}
				var names = new Array();
				var pvs = new Array();
				var index = 0;
				var tableVN = "";
				var lastMsg = "";
				if (type == 2) {
					lastMsg = "日";
				} else if (type == 3) {
					lastMsg = "月";
				} else {
					lastMsg = "时";
				}
				for (var i = 0; i < data.length; i++) {
					if ('date_time' != data[i].name) {
						tableVN = data[i].name;
						names[index] = tableVN + lastMsg;
						if ('0' == data[i].memo) {
							pvs[index] = '';
						} else {
							pvs[index] = parseInt(data[i].memo);
						}
						index++;
					} else {
						titleName = data[i].memo + titleName;
					}
				}
				statisticHourEle(names, pvs, titleName);
			}
		},
		error : function(data) {
			// statisticHourEle(new Array(),new Array());
			// LOADING.hide();
			nodataMessage($("#dailyElectricityCompare"),
					"系统异常, 没有匹配到耗电量数据, 请稍后再试！", "html", "ele");
			return;
		}
	});
}
function statisticHourEle(names, pvs, titleName) {
	// Loop over the chart colors and make them gradients from white
	Highcharts.setOptions({
		colors : [ '#55BF3B' ]
	});
	var colors = Highcharts.getOptions().colors;
	if (typeof colors[0] == "string") {
		$.each(colors, function(i, color) {
			colors[i] = {
				linearGradient : {
					x1 : 0,
					y1 : 0,
					x2 : 1,
					y2 : 0
				},
				stops : [ [ 0, color ], [ 0.3, 'white' ], [ 1, color ] ]
			};
		});
	}
	$("#dailyElectricityCompare").highcharts({
		chart : {
			type : 'column',
			backgroundColor : null,
		},
		title : {
			text : titleName + '耗电量对比 (单位 : kWh)',
			style : {
				fontSize : '15px',
				fontWeight : 'bold',
				fontFamily : 'Microsoft YaHei'
			}
		},
		plotOptions : {
			series : {
				borderWidth : 0,
				dataLabels : {
					enabled : true,
					format : '{point.y}',
					style : {
						color : '#777',
						fontSize : '12px',
						fontFamily : 'Microsoft YaHei',
						fontWeight : 'normal'
					}
				}
			}
		},
		exporting : {
			enabled : false
		},
		tooltip : {
			enabled : false
		},
		legend : {
			enabled : false
		},
		xAxis : {
			tickWidth : 0,
			lineColor : '#999',
			labels : {
				style : {
					fontSize : '12px',
					fontFamily : 'Microsoft YaHei'
				}
			},
			categories : names
		},
		yAxis : {
			title : false,
			gridLineColor : '#eee',
			labels : {
				enabled : false
			}
		},
		series : [ {
			data : pvs
		} ],
		credits : {
			enabled : false
		// 不显示highCharts版权信息
		}
	}, function() {
		// LOADING.hide();
	});
}

// 告警信息类型对比
function statisticAlarmType(devId) {
	jQuery.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : 'a' + '/count/statisticAlarmType/' + devId + "?" + Math.random(),
		dataType : 'json',
		success : function(data) {
			if (undefined == data) {
				return;
			} else {
				var dataMsg = new Array();
				var dataVal = new Array();
				for (var i = 0; i < data.length; i++) {
					dataMsg[i] = data[i].name;
					dataVal[i] = parseInt(data[i].memo);
				}
				alarmTypeStat(dataMsg, dataVal);
			}
		},
		error : function(data) {
		}
	});
}

// 供水高峰耗电量对比
function statisticEleByLocationInit(type) {
	jQuery
			.ajax({
				type : 'POST',
				contentType : 'application/json',
				url : 'a' + '/count/statisticEleByDevice/' + type + "?"
						+ Math.random(),
				dataType : 'json',
				success : function(data) {
					if (undefined == data || data.length < 1) {
						$("#ele_max").html('-');
						$("#ele_min").html('-');
						return;
					} else {
						var dataMsg = new Array();
						var indexPre = 0;
						$.each(data[0], function(i, item) {
							if (item > 0) {
								if (i.indexOf("高") > -1) {
									$("#ele_max").html(item);
								} else {
									$("#ele_min").html(item);
								}
							}
						});
					}
				},
				error : function(data) {

				}
			});
}

// 泵后压力雷电图
function statisticPumpPressureInit(type) {
	var divObject = $('#PumpPressureRadaCount');
	var titleName = "泵后压力统计";
	var unit = ' MPa';
	if ("01" == type) {
		titleName = "频率统计";
		unit = ' Hz';
	}
	jQuery.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : 'a' + '/count/statisticPumpPressure/' + type + "?"
				+ Math.random(),
		dataType : 'json',
		success : function(data) {
			if (undefined == data || data.length < 1) {
				nodataMessage(divObject, "没有" + titleName + "数据.", "html",
						"ala");
				return;
			} else {
				var names = data[0];
				for (var i = 0; i < names.length; i++) {
					names[i] = names[i] + unit;
				}
				var counts = new Array();
				for (var i = 0; i < data[1].length; i++) {
					counts[i] = parseInt(data[1][i]);
				}
				if (counts.length < 1) {
					nodataMessage(divObject, "没有" + titleName + "数据.", "html",
							"ala");
				} else {
					statisticPumpPressureFun(names, counts, titleName);
				}
			}
		},
		error : function(data) {
			nodataMessage(divObject, "没有" + titleName + "数据.", "html", "ala");
		}
	});
}
function statisticPumpPressureFun(names, counts, titleName) {
	$('#PumpPressureRadaCount').highcharts({
		colors : [ '#7798BF' ],
		chart : {
			polar : true,
			type : 'line'
		},
		title : {
			text : titleName,
			style : {
				fontSize : '15px',
				fontWeight : 'bold',
				fontFamily : 'Microsoft YaHei'
			}
		},
		pane : {
			size : '80%'
		},
		xAxis : {
			tickmarkPlacement : 'on',
			lineWidth : 0,
			categories : names
		},
		yAxis : {
			gridLineInterpolation : 'polygon',
			lineWidth : 0,
			min : 0
		},
		legend : {
			enabled : false
		},
		exporting : {
			enabled : false
		},
		tooltip : {
			shared : true,
			pointFormat : '<span>{series.name}: <b>{point.y}</b> 台<br/>'
		},
		series : [ {
			name : '设备数量',
			data : counts,
			pointPlacement : 'on'
		} ],
		credits : {
			enabled : false
		// 不显示highCharts版权信息
		}
	});
}

// 供水峰值统计
function initStatisticHourHot(dateTime, type) {
	var titleName = '耗电量热力图';
	var divObject = $('#eleHotChar');
	jQuery.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : 'a' + '/count/hotChar/' + dateTime + '/' + type + '/2' + "?"
				+ Math.random(),
		dataType : 'json',
		success : function(data) {
			var colArr = new Array();
			var array = new Array();
			var rowArr = new Array();
			colArr[0] = '耗电量(kWh)'
			$.each(data, function(i, item) {
				rowArr[i] = item.dateTime.substring(item.dateTime.length - 2,
						item.dateTime.length);
				if ('1' == type) {
					rowArr[i] = rowArr[i] + "时";
				} else if ('2' == type) {
					rowArr[i] = rowArr[i] + "日";
				} else {
					rowArr[i] = rowArr[i] + "月";
				}
				if (i == 0) {
					var tileTime = item.dateTime.substring(0,
							item.dateTime.length - 2);
					if ("-" == tileTime.substring(tileTime.length - 1,
							tileTime.length)) {
						tileTime = tileTime.substring(0, tileTime.length - 1);
					}
					titleName = tileTime + " " + titleName;
				}
				var arr = new Array();
				arr[0] = i;
				arr[1] = 0;
				arr[2] = parseInt(item.pv);
				array[i] = arr;
			});

			if (array.length < 1) {
				nodataMessage(divObject, "没有" + titleName + "数据.", "html",
						"ala");
			} else {
				eleHotChar(array, rowArr, colArr, titleName);
			}
		},
		error : function(data) {
			nodataMessage(divObject, "没有" + titleName + "数据.", "html", "ala");
		}
	});
}

function eleHotChar(array, rowArr, colArr, titleName) {
	Highcharts.setOptions({
		colors : [ '#f0ad4e' ]
	});
	// 日
	$('#eleHotChar').highcharts(
			{
				chart : {
					type : 'heatmap',
					marginTop : 40,
					marginBottom : 80
				},
				title : {
					text : titleName,
					style : {
						fontSize : '15px',
						fontWeight : 'bold',
						fontFamily : 'Microsoft YaHei'
					}
				},
				xAxis : {
					categories : rowArr
				},
				yAxis : {
					categories : colArr,
					title : null
				},
				colorAxis : {
					min : 0,
					minColor : '#FFFFFF',
					maxColor : Highcharts.getOptions().colors[0]
				},
				legend : {
					enabled : false
				},
				exporting : {
					enabled : false
				},
				tooltip : {
					formatter : function() {
						return '<b>'
								+ this.series.xAxis.categories[this.point.x]
								+ '<br>' + '<b>'
								+ this.series.yAxis.categories[this.point.y]
								+ '<br></b>' + this.point.value + ' kWh';
					}
				},
				series : [ {
					name : '设备数量',
					borderWidth : 1,
					data : array,
					dataLabels : {
						enabled : true,
						color : '#000000'
					}
				} ],
				credits : {
					enabled : false
				// 不显示highCharts版权信息
				}
			});
}

function createMap(maxNum, dataMsg) {
	var dom = document.getElementById("map");
	var myChart = echarts.init(dom);
	option = null;
	// myChart.showLoading();

	var mapJsonUrl = 'theme/scripts/map/' + '610100.json';
	$.get(mapJsonUrl, function(geoJson) {

		// myChart.hideLoading();

		echarts.registerMap('HK', geoJson);

		myChart.setOption({
			title : {
				enabled : false
			},
			tooltip : {
				trigger : 'item',
				formatter : '{b}<br/>{c} (套设备)',
				textStyle : {
					fontSize : 12
				}
			},
			legend : {
				show : false
			},
			dataRange : {
				x : 10,
				y : 240,
				min : 0,
				max : maxNum,
				color : [ '#31588A', '#638CCC', '#90B2E4', '#33425B',
						'#3498DB', '#ECF0F1', '#8ED2C9', '#00AAA0' ],
				text : [ '高', '低' ],
				textStyle : {
					color : '#fff'
				},
				calculable : true
			},
			series : [ {
				name : '区名称',
				type : 'map',
				mapType : 'HK',
				label : {
					normal : {
						show : true,
						textStyle : {
							color : '#3d2d2d'
						}
					},
					emphasis : {
						show : true,
						textStyle : {
							color : '#fff'
						}
					}
				},
				data : dataMsg
			} ]
		});
	});
	;
	/*
	 * if (option && typeof option === "object") { myChart.setOption(option,
	 * true); }
	 */
}

function deviceCompair(deviceSum, dataMsg) {
	var areaDevChart = echarts.init(document.getElementById('pie-container'));
	var areaDevOption = {
		color : [ '#8AACFF', '#626FE6', '#6D42C7', '#09A599', '#6A759B',
				'#21273D', '#B9D4F1', '#2E3B3E', '#50666B', '#0B409C' ],
		title : {
			text : deviceSum,
			subtext : '小时',
			x : 'center',
			y : '42',
			textStyle : {
				color : '#030b1e',
				fontSize : 20
			},
			subtextStyle : {
				color : '#030b1e',
				fontSize : 12
			}
		},
		tooltip : {
			show : false
		},
		legend : {
			show : false
		},
		series : {
			name : '设备数量',
			type : 'pie',
			radius : [ '50%', '70%' ],
			startAngle : 300,
			avoidLabelOverlap : false,
			hoverAnimation : false,
			label : {
				normal : {
					show : true,
					position : 'outside',
					formatter : '{c}\n{b}',
					textStyle : {
						fontSize : 12
					}
				},
				emphasis : {
					show : true
				}
			},
			labelLine : {
				normal : {
					show : true,
					length : 8,
					length2 : 16
				}
			},
			data : dataMsg
		}

	};
	areaDevChart.setOption(areaDevOption);
}

function alarmTypeStat(dataMsg, dataVal) {
	// init bar : alarm information type ratio
	var alarmInfoChart = echarts.init(document.getElementById('deviceType'));
	var alarmInfoOption = {
		textStyle : {
			color : '#fff'
		},
		tooltip : {
			trigger : 'axis',
			axisPointer : {
				type : 'shadow'
			},
			textStyle : {
				fontSize : 12
			},
			confine : true
		},
		legend : {
			show : false
		},
		xAxis : {
			type : 'value',
			position : 'top',
			axisTick : {
				show : false
			},
			axisLabel : {
				show : false
			},
			axisLine : {
				lineStyle : {
					type : 'dashed',
					color : '#fff'
				}
			},
			splitLine : {
				show : false
			}
		},
		yAxis : {
			type : 'category',
			axisLine : {
				lineStyle : {
					type : 'dashed',
					color : '#fff'
				}
			},
			axisTick : {
				show : false
			},
			data : dataMsg
		},
		series : {
			name : '告警数量',
			type : 'bar',
			barWidth : 10,
			itemStyle : {
				normal : {
					color : function(params) {
						var colorList = [ '#d1cccc', '#f8bebe', '#1ba4a0',
								'#b4d379', '#fa5b15', '#526b2d', '#1c3c46',
								'#5bb9d4', '#8b530a', '#a47333' ];
						return colorList[params.dataIndex];
					}
				},
				emphasis : {
					shadowBlur : 10,
					shadowOffsetX : 0,
					shadowColor : 'rgba(0, 0, 0, 0.5)'
				}
			},
			data : dataVal
		},
		grid : {
			left : '5%',
			right : '8%',
			bottom : 30,
			height : 168,
			containLabel : true
		}
	};
	alarmInfoChart.setOption(alarmInfoOption);
}

function randomData() {
	now = new Date(+now + oneDay);
	value = value + Math.random() * 21 - 10;
	return {
		name : now.toString(),
		value : [
				[ now.getFullYear(), now.getMonth() + 1, now.getDate() ]
						.join('/'), Math.round(value) ]
	}
}

function supModStat(id) {
	// init bar : automated transformation vendor statistics
	var supplierChart = echarts.init(document.getElementById('supplierChart'));
	var supplierOption = {
		title : {
			text : ''
		},
		legend : {
			data : [ '进口压力' ]
		},
		tooltip : {
			trigger : 'axis'
		},
		legend : {
			show : false
		},
		xAxis : {
			type : 'time',
			splitLine : {
				show : false
			},
			axisLine : {
				lineStyle : {
					color : '#fff'
				}
			},
		},
		yAxis : {
			type : 'value',
			name : '单位 : Mpa',
			boundaryGap : [ 0, '100%' ],
			splitLine : {
				show : false
			},
			axisLine : {
				lineStyle : {
					color : '#fff'
				}
			},
			axisLabel: {                   
                formatter: function (value, index) {           
                 return value.toFixed(1);      
                  }                
            }
		},
		grid : {
			left : '5%',
			right : '5%',
			bottom : 15,
			height : 125,
			containLabel : true,
		},
		series : [ {
			name : '进口压力',
			type : 'line',
			showSymbol : false,
			hoverAnimation : false,
			data : inData
		}, {
			name : '给定压力',
			type : 'line',
			showSymbol : false,
			hoverAnimation : false,
			data : gitData
		}, {
			name : '出口压力',
			type : 'line',
			showSymbol : false,
			hoverAnimation : false,
			data : outData
		} ]
	};
	if (taskisRun == '2') {
		return;
	}
	taskisRun = '2';
	supplierChart.setOption(supplierOption);
	setInterval(function() {
		jQuery.ajax({
			type : 'POST',
			contentType : 'application/json',
			url : 'a' + '/count/passStat/' + id + "?" + Math.random(),
			dataType : 'json',
			success : function(data) {

				if (inData.length == 0) {
					return;
				}
				var nowTime = (new Date()).getTime();
				var valData = new Object();
				nowTime = nowTime + 8 * 60 * 60 * 1000;
				valData.value = [];
				valData.value[0] = nowTime;
				valData.value[1] = data['in'];
				inData.shift();
				inData.push(valData);

				valData = new Object();
				valData.value = [];
				valData.value[0] = nowTime;
				valData.value[1] = data['out'];
				outData.shift();
				outData.push(valData);

				valData = new Object();
				valData.value = [];
				valData.value[0] = nowTime;
				valData.value[1] = data['git'];
				gitData.shift();
				gitData.push(valData);

				supplierChart.setOption({
					series : [ {
						data : inData
					}, {
						data : gitData
					}, {
						data : outData
					} ]
				});
			},
			error : function(data) {

			}
		});
	}, 5000);
}

function orderTypeStatDetail(dataMsg) {
	var hideStyle = {
		normal : {
			color : 'rgba(0,0,0,0)',
			label : {
				show : false
			},
			labelLine : {
				show : false
			}
		},
		emphasis : {
			color : 'rgba(0,0,0,0)'
		}
	};
	var data1 = [ {
		name : '待确认',
		value : dataMsg[0]
	}, {
		value : dataMsg[1],
		name : 'invisible',
		itemStyle : hideStyle
	} ];
	var data2 = [ {
		name : '已完成',
		value : dataMsg[2]
	}, {
		value : dataMsg[3],
		name : 'invisible',
		itemStyle : hideStyle
	} ];
	if (dataMsg[0] == 0) {
		data1 = [];
	}
	if (dataMsg[2] == 0) {
		data2 = [];
	}
	// 工单状态统计
	var orderChart = echarts.init(document.getElementById('orderChart'));
	var labelStyle = {
		normal : {
			show : true,
			formatter : '{c}\n{b}',
			textStyle : {
				fontSize : 12
			}
		},
		emphasis : {
			show : true
		}
	};
	var labelLineStyle = {
		normal : {
			show : true,
			length : 8,
			length2 : 16
		}
	};
	var hideStyle = {
		normal : {
			color : 'rgba(0,0,0,0)',
			label : {
				show : false
			},
			labelLine : {
				show : false
			}
		},
		emphasis : {
			color : 'rgba(0,0,0,0)'
		}
	};
	var orderOption = {
		tooltip : {
			show : false
		},
		legend : {
			show : false
		},
		series : [ {
			type : 'pie',
			clockWise : false,
			radius : [ 35, 45 ],
			// center: ['65%', '50%'],
			label : labelStyle,
			labelLine : labelLineStyle,
			itemStyle : {
				normal : {
					color : '#4c69df'
				}
			},
			hoverAnimation : false,
			data : data1
		}, {
			type : 'pie',
			clockWise : false,
			radius : [ 25, 35 ],
			// center: ['65%', '50%'],
			label : labelStyle,
			labelLine : labelLineStyle,
			itemStyle : {
				normal : {
					color : '#81dff7'
				}
			},
			hoverAnimation : false,
			data : data2
		} ]
	};
	orderChart.setOption(orderOption);
}
function areaProjectStat(dataMsg) {
	// init pie : regional maintenance project comparison
	var maintenanceChart = echarts.init(document
			.getElementById('maintenanceChart'));
	var maintenanceOption = {
		tooltip : {
			show : false
		},
		legend : {
			show : false
		},
		toolbox : {
			show : false,
		},
		calculable : true,
		series : {
			name : '项目数量',
			type : 'pie',
			radius : [ 30, 45 ],
			roseType : 'area',
			hoverAnimation : false,
			label : {
				normal : {
					show : true,
					formatter : '{c}\n{b}',
					textStyle : {
						fontSize : 12
					}
				},
				emphasis : {
					show : true
				}
			},
			itemStyle : {
				normal : {
					color : function(params) {
						var colorList = [ '#B793E6', '#646ECB', '#3532A7',
								'#9ED0E0', '#5C868E', '#19483F', '#003355',
								'#639A67', '#4FC1E9', '#7A57D1' ];
						return colorList[params.dataIndex];
					}
				},
				emphasis : {
					shadowBlur : 10,
					shadowOffsetX : 0,
					shadowColor : 'rgba(0, 0, 0, 0.5)'
				}
			},
			data : dataMsg
		}
	};
	maintenanceChart.setOption(maintenanceOption);
	closeLoading();
}
function trendChartConfig(target, unit, name, dataMsg, dataVal) {
	var trendChart = echarts.init(document.getElementById(target));
	var trendOption = {
		textStyle : {
			color : '#fff'
		},
		tooltip : {
			trigger : 'axis',
			axisPointer : {
				type : ''
			},
			textStyle : {
				fontSize : 12
			}
		},
		xAxis : {
			type : 'category',
			axisTick : {
				show : false
			},
			axisLine : {
				lineStyle : {
					color : '#fff'
				}
			},
			axisLabel : {
				showMinLabel : true,
				showMaxLabel : true
			},
			data : dataMsg
		},
		yAxis : {
			type : 'value',
			name : '单位 : ' + unit,
			axisLine : {
				lineStyle : {
					color : '#fff'
				}
			},
			axisTick : {
				show : false
			},
			splitLine : {
				show : false
			},
			splitNumber : 6
		},
		series : {
			type : 'line',
			name : name,
			barWidth : 6,
			itemStyle : {
				normal : {
					color : new echarts.graphic.LinearGradient(0, 0, 0, 1, [ {
						offset : 0,
						color : '#fff'
					}, {
						offset : 0.4,
						color : '#86bce2'
					}, {
						offset : 1,
						color : '#3590ce'
					} ])
				},
				emphasis : {
					shadowBlur : 10,
					shadowOffsetX : 0,
					shadowColor : 'rgba(0, 0, 0, 0.5)'
				}
			},
			data : dataVal
		},
		grid : {
			left : '5%',
			right : '5%',
			bottom : 15,
			height : 125,
			containLabel : true,
		}
	};
	var dataArr = [];
	for (var i = 1; i <= 30; i++) {
		dataArr.push(Math.round(Math.random() * 500));
	}
	;
	trendChart.setOption(trendOption);
};
function deviceTypeStat(dataMsg,dateVal){
	// init bar : number of device types
	var devTypeChart = echarts.init(document.getElementById('alarmInfo'));
	var devTypeOption = {
		textStyle: {
			color: '#fff'
		},
		tooltip: {
			trigger: 'axis',
	        axisPointer: {
	            type : 'shadow'
	        },
            textStyle: {
            	fontSize: 12
            },
            confine: true
		},
		xAxis: {
        	type: 'category',
     		axisLine: {
     			lineStyle: {
     				color: '#fff'
     			}
     		},
     		axisTick: {
     			show: false
     		},
     		axisLabel: {
     			interval: 0,
     			rotate: '-45',
     			formatter: function(val) {
    				if(val && val.length > 4) {
    					val = val.substring(0,4) + '...';
    				}
    				return val;
				}
     		},
     		data: dataMsg
	    },
	    yAxis: {
	        type: 'value',
	        axisLine: {
     			lineStyle: {
     				color: '#fff'
     			}
     		},
     		axisTick: {
     			show: false
     		},
     		splitNumber: 3,
     		splitLine: {
     			interval: 2,
     			lineStyle: {
     				color: '#4b5566',
     				type: 'dashed'
     			}
     		}
	    },
	    series: {
            name: '额定功率',
            type: 'bar',
            barWidth : 10,
            itemStyle: {   
                normal:{  
                    color: function (params){
                        var colorList = ['#ee9f5c', '#c9dc5b', '#e25a7a', '#ea4e2b', '#1ba4a0', '#cd4a95'];
                        return colorList[params.dataIndex];
                    }
                },
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            },
            data: dateVal
        },
        grid: {
                left: '5%',
                right:'10%',
                bottom: 40,
                height: 110,
                containLabel: true,
        }
	};
	devTypeChart.setOption(devTypeOption);
}