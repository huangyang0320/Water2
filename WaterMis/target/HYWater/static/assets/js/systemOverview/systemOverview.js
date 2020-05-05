/*
 * &systemOverview.html
 * Data exchange and Display chart
 *******************************
 *******************************
 */
(function($) {
	'use strict';

	/*****************************************************/
	// 初始化无滚动条
    $("#page-wrapper").css("overflow-y", "hidden");
    // 定义可视区域高度
    var calcHeight = function() {
    	var height = $(window).height() - 70;
    	$(".chart-container").height(height);
    };
    calcHeight();
    $(window).resize(function() {
    	calcHeight();
    });
    
    // 初始化高度
    $("#deviceTypeAccounting").height(320);
    $("#areaDeviceAccounting").height(320);
    $("#dailyElectricityCompare").height(320);
    $("#dailyAlarmCompare").height(320);
    $("#MaintenanceCompareByArea").height(320);
    $("#AlarmCompareByType").height(320);
    var eleIndex = '1';
    // 获取系统当前时间
    var currentTime = "";
    function getSysCurrentTime() {
    	var sysCurrentTime = "";
    	var date = new Date();
    	var year = date.getFullYear();
    	var month = date.getMonth() + 1;
    	var day = date.getDate();
    	if(month.length < 2){
    		month = "0" + month;
    	}
    	if(day.length < 2){
    		day = "0" + day;
    	}
    	sysCurrentTime = year + "-" + month + "-" + day;
    	currentTime = sysCurrentTime;
    	return sysCurrentTime;
    }
    // 上一天/下一天/回调函数
    function timeChangeCallBack(num, callBack) {
    	callBack(num,eleIndex);
    }

	countDevice();
	countDeviceType();
	countDeviceArea();
	statisticRepaicType();
	statisticAlarmType();
	initStatisticHourEle(0,1);
	initStatisticHourAla(0);
	countLocation();

	// 日时刻耗电量 上一天/下一天
	var timeBarEle = 0;
	$("#lastDayEle").on("click", function() {
		$("#nextDayEle").removeClass("disabled");
		timeBarEle ++;
    	timeChangeCallBack(timeBarEle, initStatisticHourEle);
    	if(timeBarEle == 7) {
    		$("#lastDayEle").addClass("disabled");
		}
	});
	$("#ele1").on("click", function() {
		if(eleIndex == '1'){
			return;
		}
		 $("#lastDayEle").removeClass("disabled");
		 $("#nextDayEle").addClass("disabled");
		 $("#lastDayEle").html("上一天");
		 $("#nextDayEle").html("下一天");
		eleIndex = '1';
		timeBarEle = 0;
		initStatisticHourEle(timeBarEle,eleIndex)
		
	});
	$("#ele2").on("click", function() {
		if(eleIndex == '2'){
			return;
		}
		 $("#lastDayEle").removeClass("disabled");
		 $("#nextDayEle").addClass("disabled");
		 $("#lastDayEle").html("上一月");
		 $("#nextDayEle").html("下一月");
		eleIndex = '2';
		timeBarEle = 0;
		initStatisticHourEle(timeBarEle,eleIndex)
		
	});
	$("#ele3").on("click", function() {
		if(eleIndex == '3'){
			return;
		}
		 $("#lastDayEle").removeClass("disabled");
		 $("#nextDayEle").addClass("disabled");
		 $("#lastDayEle").html("上一年");
		 $("#nextDayEle").html("下一年");
		eleIndex = '3';
		timeBarEle = 0;
		initStatisticHourEle(timeBarEle,eleIndex)
		
	});
	$("#nextDayEle").on("click", function() {
		$("#lastDayEle").removeClass("disabled");
		timeBarEle --;
		timeChangeCallBack(timeBarEle, initStatisticHourEle);
    	if(timeBarEle == 0) {
    		$("#nextDayEle").addClass("disabled");
		}
	});
	// 日时刻告警数 上一天/下一天
	var timeBarAla = 0;
	$("#lastDayAla").on("click", function() {
		$("#nextDayAla").removeClass("disabled");
		timeBarAla ++;
    	timeChangeCallBack(timeBarAla, initStatisticHourAla);
    	if(timeBarAla == 7) {
    		$("#lastDayAla").addClass("disabled");
		}
	});
	$("#nextDayAla").on("click", function() {
		$("#lastDayAla").removeClass("disabled");
		timeBarAla --;
		timeChangeCallBack(timeBarAla, initStatisticHourAla);
    	if(timeBarAla == 0) {
    		$("#nextDayAla").addClass("disabled");
		}
	});

	// pie图色系
	function setPieChartColors(color) {
		var colors = [], i;
        for (i = 0; i < 10; i += 1) {
            // Start out with a darkened base color (negative brighten), and end
            // up with a much brighter color
            colors.push(Highcharts.Color(color).brighten((i - 3) / 7).get());
        }
        return colors;
	}

	// 统计设备信息
	function countDevice() {
		jQuery.ajax({
			type : 'POST',
			contentType : 'application/json',
			url : CONTEXT_PATH+'/count/countDevice' + "?" + Math.random(),
			dataType : 'json',
			success : function(data) {
				if (undefined == data) {
					return;
				} else {
					$("#deviceSum").html(data.pumpSum);
					// $("#deviceRunTime").html(data.deviceRunTime + "(小时)");
					$("#deviceElement").html(data.deviceElement);
					$("#deviceFlow").html(data.deviceFlow);
					$("#systemRunTime").html(data.systemRunTime);
				}
			},
			error : function(data) {
                console.log(data)
			}
		});
	}
	// 获取设备的区位
	function countLocation() {
		jQuery.ajax({
			type : 'POST',
			contentType : 'application/json',
			url : CONTEXT_PATH+'/count/countLocation' + "?" + Math.random(),
			dataType : 'json',
			success : function(data) {
				if (undefined == data) {
					return;
				} else {
					$("#areaCountLocation").html("");
					var htmlMsg = "";
					for(var i=0;i<data.length;i++){
						if(undefined != data[i].name && null != data[i].name){
							htmlMsg =htmlMsg + '<label>'+data[i].name+'</label><span>'+data[i].memo+'</span><br>';
						}
		 	    	}
					$("#areaCountLocation").html(htmlMsg);
				}
			},
			error : function(data) {
                console.log(data)
			}
		});
	}

	// 统计地区下面的设备
	function countDeviceArea() {
		jQuery.ajax({
			type : 'POST',
			contentType : 'application/json',
			url : CONTEXT_PATH+'/count/countDeviceArea' + "?" + Math.random(),
			dataType : 'json',
			success : function(data) {
				if (undefined == data || data.length < 1) {
					renderArea(new Array());
					return;
				} else {
					var dataMsg = new Array();
		 	    	for(var i=0;i<data.length;i++){
		 	    		dataMsg[i] = new Array();
		 	    		dataMsg[i][0] = data[i].name;
		 	    		dataMsg[i][1] = parseInt(data[i].memo);
		 	    	}
		 	    	renderArea(dataMsg);
				}
			},
			error : function(data) {
                console.log(data)
			}
		});
	}

	//统计不同类型的设备
	function countDeviceType() {
		jQuery.ajax({
			type : 'POST',
			contentType : 'application/json',
			url : CONTEXT_PATH+'/count/countDeviceType' + "?" + Math.random(),
			dataType : 'json',
			success : function(data) {
				if (undefined == data || data.length < 1) {
					renderType(new Array());
					return;
				} else {
					var dataMsg = new Array();
		 	    	for(var i=0;i<data.length;i++){
		 	    		if(data[i].name && data[i].memo){
                            dataMsg[i] = new Array();
                            dataMsg[i][0] = data[i].name;
                            dataMsg[i][1] = parseInt(data[i].memo);
                        }
		 	    	}
		 	    	renderType(dataMsg);
				}
			},
			error : function(data) {
                console.log(data);
			}
		});
	}

	// 统计不同区域的维保信息
	function statisticRepaicType() {
		jQuery.ajax({
			type : 'POST',
			contentType : 'application/json',
			url : CONTEXT_PATH+'/count/statisticRepaicType' + "?" + Math.random(),
			dataType : 'json',
			success : function(data) {
				if (undefined == data) {
					return;
				} else {
					var dataMsg = new Array();
		 	    	for(var i=0;i<data.length;i++){
		 	    		if(data[i].name && data[i].memo){
                            dataMsg[i] = new Array();
                            dataMsg[i][0] = data[i].name;
                            dataMsg[i][1] = parseInt(data[i].memo);
                        }
		 	    	}
                    let series= {};
		 	    	if(dataMsg.length>0){
		 	    		series = {
							type: 'pie',
								name: '维保次数',
							style: {
								fontSize: '12px',
								fontFamily: 'Microsoft YaHei'
							},
							data: dataMsg
						}
                    }
                    renderRep(series);
				}
			},
			error : function(data) {
				console.log(data)
			}
		});
	}
	// 统计不同类型的告警数量
	function statisticAlarmType() {
		jQuery.ajax({
			type : 'POST',
			contentType : 'application/json',
			url : CONTEXT_PATH+'/count/statisticAlarmType' + "?" + Math.random(),
			dataType : 'json',
			success : function(data) {
				let series = {};
				if(data && data.length>0){
                    var dataMsg = new Array();
                    for(var i=0;i<data.length;i++){
                    	if(data[i].name && data[i].memo){
                            dataMsg[i] = new Array();
                            dataMsg[i][0] = data[i].name;
                            dataMsg[i][1] = parseInt(data[i].memo);
                        }
                    }
                    series = {
                        name: '告警数量',
                        style: {
                            fontSize: '12px',
                            fontFamily: 'Microsoft YaHei'
                        },
                        data: dataMsg
                    }
				}
				renderAla(series);
			},
			error : function(data) {
                console.log(data)
			}
		});
	}
	// 日时刻耗电量统计
	function initStatisticHourEle(time,type) {
		var titleName = "";
		if('1' == type){
				titleName += "日 每小时";
			}else if('2' == type){
				titleName += "月 每天";
			}else if('3' == type){
				titleName += "年 每月";
			}
		jQuery.ajax({
			type : 'POST',
			contentType : 'application/json',
			url : CONTEXT_PATH+'/count/statisticHourEle/' + time + "/" + type + "?" + Math.random(),
			dataType : 'json',
			success : function(data) {
				if (data.length < 2) {
					// statisticHourEle(new Array(),new Array());
					LOADING.hide();
					nodataMessage($("#dailyElectricityCompare"), 
						"没有匹配到 "+data[0].memo+ titleName +"耗电量数据.", "html", "ele");
					return;
				} else {
					if($(".nodata-match-ele")){
						$(".nodata-match-ele").remove();
					}
					var names = new Array();
					var pvs = new Array();
					var index = 0;
					var tableVN = "";
					var lastMsg = "";
					if(type == 2){
						lastMsg = "日";
					}else if(type == 3){
						lastMsg = "月";
					}else{
						lastMsg = "时";
					}
		 	    	for(var i=0;i<data.length;i++){
		 	    		if('date_time' != data[i].name){
		 	    			tableVN = data[i].name;
		 	    			if("00" == tableVN){
		 	    				tableVN = "24"
		 	    			}
		 	    			names[index] = tableVN + lastMsg;
		 	    			if('0' == data[i].memo){
		 	    				pvs[index] = '';
		 	    			}else{
		 	    				pvs[index] = parseInt(data[i].memo);	
		 	    			}
			 	    		index++;
		 	    		}else{
		 	    			titleName = data[i].memo + titleName;
		 	    		}
		 	    	}
		 	    	statisticHourEle(names,pvs,titleName);
				}
			},
			error : function(data) {
				// statisticHourEle(new Array(),new Array());
				LOADING.hide();
				nodataMessage($("#dailyElectricityCompare"), 
					"系统异常, 没有匹配到耗电量数据, 请稍后再试！", "html", "ele");
				return;
			}
		});
	}
	// 日时刻告警数统计
	function initStatisticHourAla(time,type) {
		jQuery.ajax({
			type : 'POST',
			contentType : 'application/json',
			url : CONTEXT_PATH+'/count/statisticHourAla/' + time + "?" + Math.random(),
			dataType : 'json',
			success : function(data) {
				if (data.length < 2) {
					// statisticHourAla(new Array(),new Array());
					LOADING.hide();
					nodataMessage($("#dailyAlarmCompare"), 
						"没有匹配到 "+data[0].memo+" 的日时刻告警数据.", "html", "ala");
					return;
				} else {
					if($(".nodata-match-ala")){
						$(".nodata-match-ala").remove();
					}
					var names = new Array();
					var pvs = new Array();
					var index = 0;
		 	    	for(var i=0;i<data.length;i++){
		 	    		if('date_time' != data[i].name){
		 	    			names[index] = data[i].name;
			 	    		pvs[index] = parseInt(data[i].memo);
			 	    		index++;
		 	    		}
		 	    	}
		 	    	statisticHourAla(names,pvs);
				}
			},
			error : function(data) {
				// statisticHourAla(new Array(),new Array());
				LOADING.hide();
				nodataMessage($("#dailyAlarmCompare"), 
					"系统异常, 没有匹配到告警数据, 请稍后再试！", "html", "ala");
				return;
			}
		});
	}
	/*****************************************************/
	/* 设备类型占比 */
	/*****************************************************/
	function renderType(dataMsg){
		$('#deviceTypeAccounting').highcharts({
	        /*colors: ['#f45b5b', '#8085e9', '#8d4654', '#aaeeee', '#7798BF', '#ff0066', '#eeaaee',
	            '#55BF3B', '#DF5353', '#7798BF', '#aaeeee'
	        ],*/
	        colors: setPieChartColors('#4cb1cf'),
	        chart: {
	            type: 'pie',
	            backgroundColor: null,
	            style: {
	            	fontSize: '11px',
	                fontFamily: 'Microsoft YaHei'
	           	},
	            options3d: {
	                enabled: true,
	                alpha: 45,
	                beta: 0
	            }
	        },
	        title: {
	        	text: '设备类型占比',
	            style: {
	                fontSize: '15px',
	                fontWeight: 'bold',
	                fontFamily: 'Microsoft YaHei'
	            }
	        },
	        exporting: {
	        	enabled: false
	        },
	        legend: {
	            itemStyle: {
	                fontWeight: 'bold',
	                fontSize: '12px'
	            }
	        },
	        xAxis: {
	            gridLineWidth: 1,
	            labels: {
	                style: {
	                    fontSize: '12px'
	                }
	            }
	        },
	        yAxis: {
	            // minorTickInterval: 'auto',
	            title: {
	                style: {
	                    textTransform: 'uppercase'
	                }
	            },
	            labels: {
	                style: {
	                    fontSize: '12px'
	                }
	            }
	        },
	        plotOptions: {
	            pie: {
	                allowPointSelect: true,
	                cursor: 'pointer',
	                depth: 35,
	                dataLabels: {
	                    enabled: true,
	                    style: {
	                        color: '#666',
	                        fontSize: '11px',
	                        fontFamily: 'Microsoft YaHei'
	                    }
                	}
	            }
	        },
	        series: [{
	            type: 'pie',
	            name: '设备数量',
	            style: {
	            	fontSize: '12px',
	                fontFamily: 'Microsoft YaHei'
	            },
	            data: dataMsg
	        }],
	        credits: {
	            enabled: false//不显示highCharts版权信息
	        },
	        // Highstock specific
	        navigator: {
	            xAxis: {
	                gridLineColor: '#D0D0D8'
	            }
	        },
	        rangeSelector: {
	            buttonTheme: {
	                fill: 'white',
	                stroke: '#C0C0C8',
	                'stroke-width': 1,
	                states: {
	                    select: {
	                        fill: '#D0D0D8'
	                    }
	                }
	            }
	        },
	        scrollbar: {
	            trackBorderColor: '#C0C0C8'
	        },
	        // General
	        background2: '#E0E0E8'
	    });
	}
	
	/*****************************************************/
	/* 区域设备占比 */
	/*****************************************************/
	function renderArea(dataMsg){
		$('#areaDeviceAccounting').highcharts({
			/*colors: ['#203a72', '#0c8db9', '#5eb9b1', '#403c64', '#e5a5ff', '#eeaaee', '#55BF3B', 
				'#DF5353', '#7798BF', '#aaeeee', '#f45b5b', '#8085e9', '#8d4654', '#7798BF', 
				'#aaeeee', '#ff0066'
			],*/
			colors: setPieChartColors('#0099cd'),
	        chart: {
	            type: 'pie',
	            style: {
	            	fontSize: '11px',
	                fontFamily: 'Microsoft YaHei'
	           	},
	            options3d: {
	                enabled: true,
	                alpha: 45
	            }
	        },
	        title: {
	        	text: '区域设备占比',
	            style: {
	                fontSize: '15px',
	                fontWeight: 'bold',
	                fontFamily: 'Microsoft YaHei'
	            }
	        },
	        exporting: {
	        	enabled: false
	        },
	        plotOptions: {
	            pie: {
	            	allowPointSelect: true,
	                cursor: 'pointer',
	                innerSize: 100,
	                dataLabels: {
	                    enabled: true,
	                    style: {
	                        color: '#666',
	                        fontSize: '12px',
	                        fontFamily: 'Microsoft YaHei'
	                    }
                	},
	                depth: 45
	            }
	        },
	        series: [{
	            name: '设备数量',
	            style: {
	            	fontSize: '12px',
	                fontFamily: 'Microsoft YaHei'
	            },
	            data: dataMsg
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
	    });
	}

	/*****************************************************/
    /* 日时刻耗电量对比 */
    /*****************************************************/
	function statisticHourEle(names,pvs,titleName){
		// Loop over the chart colors and make them gradients from white
		Highcharts.setOptions({
	        colors: ['#55BF3B']
	    });
	    var colors = Highcharts.getOptions().colors;
	    if(typeof colors[0] == "string"){
	        $.each(colors, function(i, color) {
	            colors[i] = {
	                linearGradient: { x1: 0, y1: 0, x2: 1, y2: 0 },
	                stops: [
	                    [0, color],
	                    [0.3, 'white'],
	                    [1, color]
	                ]
	            };
	        });
	    }
	    $("#dailyElectricityCompare").highcharts({
	        chart: {
	            type: 'column',
	            backgroundColor: null,
	        },
	        title: {
	            text: titleName + '耗电量对比 (单位 : kWh)',
	            style: {
	                fontSize: '15px',
	                fontWeight: 'bold',
	                fontFamily: 'Microsoft YaHei'
	            }
	        },
	        plotOptions: {
	            series: {
	                borderWidth: 0,
	                dataLabels: {
	                    enabled: true,
	                    format: '{point.y}',
	                    style: {
	                        color: '#777',
	                        fontSize: '12px',
	                        fontFamily: 'Microsoft YaHei',
	                        fontWeight: 'normal'
	                    }
	                }
	            }
	        },
	        exporting: {
	            enabled: false
	        },
	        tooltip: {
	            enabled: false
	        },
	        legend: {
	            enabled: false
	        },
	        xAxis: {
	            tickWidth:0,
	            lineColor: '#999',
	            labels: {
	                style: {
	                    fontSize: '12px',
	                    fontFamily: 'Microsoft YaHei'
	                }
	            },
	            categories: names
	        },
	        yAxis : {
	            title: false,
	            gridLineColor: '#eee',
	            labels: {
	                enabled:false
	            }
	        },
	        series: [{
	            data:pvs
	        }],
	        credits: {
	            enabled: false//不显示highCharts版权信息
	        }
	    }, function() {
        	LOADING.hide();
        });
	}
    

    /*****************************************************/
    /* 维保信息区域对比 */
    /*****************************************************/
    function renderRep(dataMsg){
    	$('#MaintenanceCompareByArea').highcharts({
            /*colors: ['#487285' ,'#55BF3B', '#DF5353', '#7798BF', '#aaeeee', '#f45b5b', '#8085e9', 
                '#8d4654', '#7798BF', '#aaeeee', '#eeaaee', '#ff0066'
            ],*/
            colors: setPieChartColors('#20c5ab'),
            chart: {
                type: 'pie',
                backgroundColor: null,
                style: {
                    fontSize: '11px',
                    fontFamily: 'Microsoft YaHei'
                },
                options3d: {
                    enabled: true,
                    alpha: 45,
                    beta: 0
                }
            },
            title: {
                text: '区域维保项目对比',
                style: {
                    fontSize: '15px',
                    fontWeight: 'bold',
                    fontFamily: 'Microsoft YaHei'
                }
            },
            legend: {
                itemStyle: {
                    fontWeight: 'bold',
                    fontSize: '12px'
                }
            },
            exporting: {
                enabled: false
            },
            xAxis: {
                gridLineWidth: 1,
                labels: {
                    style: {
                        fontSize: '12px'
                    }
                }
            },
            yAxis: {
                // minorTickInterval: 'auto',
                title: {
                    style: {
                        textTransform: 'uppercase'
                    }
                },
                labels: {
                    style: {
                        fontSize: '12px'
                    }
                }
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: true,
                        style: {
                            color: '#666',
                            fontSize: '12px',
                            fontFamily: 'Microsoft YaHei'
                        }
                    },
                    depth: 35
                }
            },
            series: [dataMsg],
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
        });
    }
    

    /*****************************************************/
    /* 告警信息类型对比 */
    /*****************************************************/
    function renderAla(dataMsg){
    	$('#AlarmCompareByType').highcharts({
            /*colors: ['#aaeeee', '#20c5ab', '#7798BF', '#eeaaee', '#f45b5b', '#8085e9', '#8d4654',
                '#7798BF', '#55BF3B', '#DF5353', '#aaeeee', '#ff0066'
            ],*/
            colors: setPieChartColors('#aaeeee'),
            chart: {
                type: 'pie',
                style: {
                    fontSize: '11px',
                    fontFamily: 'Microsoft YaHei'
                },
                options3d: {
                    enabled: true,
                    alpha: 45
                }
            },
            title: {
                text: '告警信息类型对比',
                style: {
                    fontSize: '15px',
                    fontWeight: 'bold',
                    fontFamily: 'Microsoft YaHei'
                }
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    innerSize: 100,
                    dataLabels: {
                        enabled: true,
                        style: {
                            color: '#666',
                            fontSize: '12px',
                            fontFamily: 'Microsoft YaHei'
                        }
                    },
                    depth: 45
                }
            },
            exporting: {
                enabled: false
            },
            series: [dataMsg],
            credits: {
                enabled: false//不显示highCharts版权信息
            }
        });
    }
    

    /*****************************************************/
    /* 日时刻报警数对比 */
    /*****************************************************/
    function statisticHourAla(names,pvs){
    	$('#dailyAlarmCompare').highcharts({
            colors: ['#f45b5b', '#8085e9', '#8d4654', '#7798BF', '#aaeeee', '#ff0066', '#eeaaee',
                '#55BF3B', '#DF5353', '#7798BF', '#aaeeee'
            ],
            title: {
                text: '日时刻报警数对比',
                style: {
                    fontSize: '15px',
                    fontWeight: 'bold',
                    fontFamily: 'Microsoft YaHei'
                }
            },
            exporting: {
                enabled: false
            },
            tooltip: {
                enabled: false
            },
            legend: {
                enabled: false
            },
            plotOptions: {
                series: {
                    borderWidth: 0,
                    dataLabels: {
                        enabled: true,
                        format: '{point.y}',
                        style: {
                            color: '#333',
                            fontSize: '12px',
                            fontFamily: 'Microsoft YaHei',
                            fontWeight: 'normal'
                        }
                    }
                }
            },
            xAxis: {
                tickInterval: 1,
                lineColor: '#999',
                labels: {
                    style: {
                        fontSize: '12px',
                        fontFamily: 'Microsoft YaHei'
                    }
                },
                categories: names
            },
            yAxis: {
                title: false,
                gridLineColor: '#eee',
                labels: {
                    enabled:false
                }
            },
            series: [{
                data: pvs
            }],
            credits: {
                enabled: false//不显示highCharts版权信息
            }
        }, function() {
        	LOADING.hide();
        });
    }

    /*****************************************************/
    /* 告警信息弹框 */
    /*****************************************************/
    $("#click_to_showAlarm").on("click", function() {
    	$.dialog.notice({
            title: '告警信息列表',
            width: 600,
            height: 200,
            min: false,
			id:'warnHtml',
            content: 'url:warn.html'
        });
    });

    /*****************************************************/
    /* 页面链接处理 */
    /*****************************************************/
    $("#viewpage_link").find("a").on("click", function(){
    	var _this = $(this);
    	var URL = _this.attr("href");
    	var content = _this.attr("title");
    	if(URL != "javascript:;" && URL != "" && URL != null && URL != undefined){
    		LOADING.show();
        	clean();
    		$pageWrapper.load(URL, function() {
    			refreshLeftMenuStatus($.trim(content));
    			$("#deviceMenuRight").show();
    		});
    	}
    	return false;
    });

})(jQuery);