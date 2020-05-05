/*
 * pumpGuide.js
 * modified for pumpGuide.html
 *
 *****************************************************
 *****************************************************
 */
 (function($){
 	'use strict';

 	/************************************************/
 	/* 初始化页面 */
 	/************************************************/
 	// 初始化无滚动条
    $("#page-wrapper").css("overflow-y", "hidden");
    $("#page-inner").css("padding-right", "25px");
    // 定义可视区域高度
	var calcheight = function() {
		var height1 = $(window).height() - 100;
		var height2 = $(window).height() - $(".panel-heading").height() - 115;
		$("#page-inner").height(height1);
		$(".panel-body").height(height2);
	};
	calcheight();
	$(window).resize(function() {
		calcheight();
	});

	// 定义颜色数组
	var colorArr = {"bhyl": "#09C", "sbpl": "#4F9EBC", "ssdl": "#55BF3B", "ssll": "#7798BF", 
						"ljll": "#1AD86B","jkyl": "#09C"};
	// 定义单位对象
	var unitArr = {"bhyl": "MPa", "sbpl": "Hz", "ssdl": "kWh", "ssll": "m³/h", "ljll": "m³/h", "jkyl": "MPa"};

	// 初始化时间控件
	$("#rowSize").change(function() {
		LOADING.show();
		$("#boxId").html("");
		countVListalues();
	});
	
	// 初始化时间控件
	$("#qryType").change(function() {
		LOADING.show();
		$("#boxId").html("");
		countVListalues();
	});
	$("#area").change(function() {
		LOADING.show();
		$("#boxId").html("");
		countVListalues();
	});
	qryAreaList();
	function qryAreaList(){
	   	$("#area").empty();
	   	$("#area").append("<option value = '-1'>全部</option>");
	   	jQuery.ajax( { 
		    type : 'POST',  
		    contentType : 'application/json',  
		    url : CONTEXT_PATH+'/device/getAllAreas?'+ Math.random(),    
		    dataType : 'json', 
		    success : function(data){
		    	if(undefined == data){
			          return;
		        }else{
		          $.each(data, function(i, item) {
		        	  
		        	  $("#area").append("<option value = '" + item.deviceId + "'> "+item.deviceName  + "</option>");
		        	})
		        }
		    	countVListalues();
		    },
		    error : function(data)
	   	    {
		    	nodataMessage($(".panel-body"), "没有匹配到当前指标的数据.", "append");
		    	LOADING.hide();
		    }
		 });
	   }
	//统计查询
	function countVListalues() {
		var qryType = $("#qryType").val();
		var areaId = $("#area").val();
		var rowSize = parseInt($("#rowSize").val());
		if($(".nodata-match")){
			$(".nodata-match").remove();
		}
		jQuery.ajax({
			type : 'POST',
			contentType : 'application/json',
			url : CONTEXT_PATH+'/count/countVListalues/' + qryType + "/" + areaId +"?" + Math.random(),
			dataType : 'json',
			success : function(data) {
				if (undefined == data || 0 == data.length) {
					nodataMessage($(".panel-body"), "没有匹配到当前指标的数据.", "append");
		 	    	LOADING.hide();
					return;
				} else {
					var tempNum = data.length % rowSize;
					if(data.length <= rowSize){
						tempNum = 1;
					}else{
						if(0 != tempNum){
							tempNum = parseInt(data.length / rowSize) + 1;
						}else{
							tempNum = parseInt(data.length / rowSize);
						}
					}
					initBoxLine(tempNum);
					var $qryType = $("#qryType").val();
					var pumpNames = new Array();
					var pvs = new Array();
		 	    	for(var i=0;i<tempNum;i++){
		 	    		var pumpName = new Array();
		 	    		var pv = new Array();
		 	    		for(var j=0;j<rowSize;j++){
		 	    			var dataObj = {};
		 	    			var colorObj = {};
		 	    			var normalValue;
		 	    			var maxValue;
		 	    			var minValue;
		 	    			if((i*rowSize + j) < data.length){
		 	    				pumpName[j] =  data[i*rowSize + j].pumpHouseName + "-" + data[i*rowSize + j].deviceId;
		 	    				// 获取颜色
		 	    				normalValue = parseFloat(data[i*rowSize + j].pv); 
		 	    				maxValue = parseFloat(data[i*rowSize + j].max);
		 	    				minValue = parseFloat(data[i*rowSize + j].min);
		 	    				if('2' == data[i*rowSize + j].total){
		 	    					colorObj = getGradientColor('#ccc');
		 	    				}
		 	    				else if(maxValue != NaN && normalValue != NaN  && normalValue > maxValue){
									colorObj = getGradientColor('#f21829');
								} else if(minValue != NaN && normalValue != NaN  && normalValue < minValue) {
									colorObj = getGradientColor('#f9eb09');
								} else {
									colorObj = getGradientColor(colorArr[$qryType]);
								}
								dataObj.y = normalValue;
								dataObj.color = colorObj;
		 	    				pv.push(dataObj);
		 	    			}else{
		 	    				pumpName[j] = " ";
		 	    				pv[j] = {};
		 	    			}
			 	    	}
		 	    		pumpNames[i] = pumpName;
		 	    		pvs.push(pv);
		 	    	}
		 	    	initBox(pumpNames,pvs, $qryType);
				}
			},
			error : function(data) {
				nodataMessage($(".panel-body"), "没有匹配到当前指标的数据.", "append");
				LOADING.hide();
			}
		});
	}
	
	function initBoxLine(tempNum){
		var boxHtml = "";
		for(var i=0;i<tempNum;i++){
			boxHtml = boxHtml + '<li><div id="device-index-compare-1"></div></li>';
	    }
		$("#boxId").html(boxHtml);
	}
	
	
	/************************************************/
 	/* 指标柱状图展示 */
 	/************************************************/
	function initBox(pumpNames,pvs,indexType){
		// Create the chart
		var rowList = $(".panel-body").find("li");
		$.each(rowList, function(i, row){
			$(row).children().highcharts({
			    chart: {
			        type: 'column',
			        backgroundColor: null,
			    },
			    title: false,
			    plotOptions: {
		            series: {
		            	cursor: 'pointer',
		                borderWidth: 0,
		                dataLabels: {
		                    enabled: true,
		                    format: '{point.y}' + unitArr[indexType],
		                    style: {
		                		color: '#666',
		                		fontSize: '12px',
		                		fontFamily: 'Microsoft YaHei',
		                		fontWeight: 'normal'
		                	}
		                },
		                events: {
		                	click: function(event) {
		                		// debugger;
		                		PUMPGUIDE_DEVICEID=[];
		                		var device = {};
		                		var device_a = "";
		                		// var deviceId = "BHBHHY11P1";
		                		// var deviceName = event.point["category"].split("-")[0];
		                		var deviceId = event.point["category"].split("-")[1];
		                		// 判断device是否为空
		                		if(isEmptyObject(deviceId)){
		                			alert("当前用户权限无法查看该设备的实时数据！");
		                			return;
		                		} else {
		                			device_a = "<a href='#'>"+device["deviceId"]+"</a>";
			                		// 加载页面
                                    GLOBAL_URL_ADDRESS = "realtimeDataNew.html"
                                    $pageWrapper.load('realtimeDataNew.html', function() {
			                			LOADING.show();
			                			refreshLeftMenuStatus("实时数据");
	    								$("#deviceMenuRight").show(function(){
	    									(device, device_a);
	    									//补
	    									//var temp={"id":deviceId};
                                            //GLOBAL_SELECT_LIST.push(temp);
	    									//GLOBAL_SELECT_LIST.push(temp);
	    								});
                                        let node = ztreeBulid.zTreeObj.getNodeByParam('id', deviceId)
										if(node){
                                            GLOBAL_SELECT_DEVICE = node;
                                            if(!GLOBAL_SELECT_LIST_CHO0SE) GLOBAL_SELECT_LIST_CHO0SE[0] = [];
                                            if(!GLOBAL_SELECT_LIST) GLOBAL_SELECT_LIST[0] = [];
                                            GLOBAL_SELECT_LIST_CHO0SE[0] = node;
                                            GLOBAL_SELECT_LIST[0] = node;
                                            pageChange(true);
										}
	    							});
		                		}
		                	}
		                }
		            }
		        },
		        exporting: {
		        	enabled: false
		        },
		        tooltip: {
					followPointer: true,
					shared: true,
					useHTML: true,
					headerFormat: '<small>{point.key}</small><table>',
					pointFormat: '<tr><td style="color: {series.color}">值: </td>' +
					'<td style="text-align: right"><b>{point.y}</b></td></tr>',
					footerFormat: '</table>'
		        },
		        legend: {
		        	enabled: false
		        },
			    xAxis: {
			    	tickWidth:0,
					// lineWidth:0,
					lineColor: '#999',
					labels: {
						style: {
		            		fontSize: '12px',
		            		fontFamily: 'Microsoft YaHei'
		            	}
					},
			        categories: pumpNames[i]
			    },
				yAxis : {
					title: false,
					tickWidth:1,
					gridLineWidth: 1,
					lineWidth:1,
					lineColor: '#D0D0D0',
					fontColor:'#D0D0D0'
				},
			    series: [{
			        data: pvs[i]
			    }],
			    credits: {
		            enabled: false//不显示highCharts版权信息
		        }
			}, function(chart) {
				// SetEveryOnePointColor(chart,indexType);
				LOADING.hide();
			});
		});
	}

	/************************************************/
 	/* 设置每一个数据点的颜色值 */
 	/************************************************/  
    /*function SetEveryOnePointColor(chart,type) {              
        //获得第一个序列的所有数据点  
        var pointsList = chart.series[0].points; 
        //遍历设置每一个数据点颜色  
        for (var i = 0; i < pointsList.length; i++) {
        	var oldColor = '';
        	var normalValue = chart.series[0].data[i].y;
        	var maxValue = chart.series[0].data[i].max;
        	var minValue = chart.series[0].data[i].min;
        	if(maxValue != NaN && normalValue != NaN  && normalValue > maxValue){
				oldColor = '#f21829';
			} else if(minValue != NaN && normalValue != NaN  && normalValue < minValue) {
				oldColor = '#f9eb09';
			} else {
				oldColor = colorArr[type];
			}
            chart.series[0].points[i].update({  
                color: getGradientColor(oldColor)
            });  
        }
        
    }*/
    // 返回渐变色
    function getGradientColor(color) {
    	return  {  
            linearGradient: { x1: 0, y1: 0, x2: 1, y2: 0 }, //横向渐变效果 如果将x2和y2值交换将会变成纵向渐变效果  
            stops: [
	            [0, color],
	            [0.3, 'white'],
	            [1, color]
	        ] 
        }
    }

 })(jQuery);