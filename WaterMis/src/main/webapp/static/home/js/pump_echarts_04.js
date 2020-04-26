
$(function () {
	getPumpData_04_day();
})

function setButtonCss(obj){
	$(obj).siblings().each(function (index, element) {
		$(this).removeClass("nav_active");
	});
	$(obj).addClass("nav_active");
}
function getPumpData_04_day(obj) {
	jQuery.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : 'a' + '/homeV2/getUseWaterHourAnalysis' + "?"
			+ Math.random(),
		dataType : 'json',
		success : function(data) {
			setButtonCss(obj);
			if (undefined == data || data.length < 1) {
				return;
			} else {
				var xName=[];
				xName.push("昨日用水量(m3)");
				xName.push("昨日用电量(kWh)");
                xName.push("");
				xName.push("今日用水量(m3)");
				xName.push("今日用电量(kWh)");
				var xData=data.xData;
				var useWaterHis=data.useWaterHis;
				var usePowerHis=data.usePowerHis;
				var useWaterReal=data.useWaterReal;
				var usePowerReal=data.usePowerReal;

				echarts_04(xData,xName,useWaterHis,usePowerHis,useWaterReal,usePowerReal);
			}
		},
		error : function(data) {

		}
	});
}

function getPumpData_04_month(obj) {
	jQuery.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : 'a' + '/homeV2/getUseWaterDayAnalysis' + "?"
			+ Math.random(),
		dataType : 'json',
		success : function(data) {
			setButtonCss(obj);
			if (undefined == data || data.length < 1) {
				return;
			} else {
				var xName=[];
				xName.push("上月用水量(m3)");
				xName.push("上月用电量(kWh)");
                xName.push("");
				xName.push("当月用水量(m3)");
				xName.push("当月用电量(kWh)");
				var xData=data.xData;
				var useWaterHis=data.useWaterHis;
				var usePowerHis=data.usePowerHis;
				var useWaterReal=data.useWaterReal;
				var usePowerReal=data.usePowerReal;

				echarts_04(xData,xName,useWaterHis,usePowerHis,useWaterReal,usePowerReal);
			}
		},
		error : function(data) {

		}
	});
}

function getPumpData_04_year(obj) {
	jQuery.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : 'a' + '/homeV2/getUseWaterMonthAnalysis' + "?"
			+ Math.random(),
		dataType : 'json',
		success : function(data) {
			setButtonCss(obj);
			if (undefined == data || data.length < 1) {
				return;
			} else {
				var xName=[];
				xName.push("去年用水量(m3)");
				xName.push("去年用电量(kWh)");
                xName.push("");
				xName.push("今年用水量(m3)");
				xName.push("今年用电量(kWh)");
				var xData=data.xData;
				var useWaterHis=data.useWaterHis;
				var usePowerHis=data.usePowerHis;
				var useWaterReal=data.useWaterReal;
				var usePowerReal=data.usePowerReal;

				echarts_04(xData,xName,useWaterHis,usePowerHis,useWaterReal,usePowerReal);
			}
		},
		error : function(data) {

		}
	});
}

function echarts_04(xData,xName,useWaterHis,usePowerHis,useWaterReal,usePowerReal) {


	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('echarts_04'));
	console.log(useWaterHis,usePowerHis,useWaterReal,usePowerReal)
	let leftmin = 999999,leftmax =-999999,rightmin=999999,rightmax=-999999
	for(let i = 0;i<useWaterHis.length;i++){
		if(useWaterHis[i]<leftmin){
			leftmin = useWaterHis[i] * 1
		}
		if(useWaterHis[i]>leftmax){
			leftmax = useWaterHis[i] * 1
		}
	}
	for(let i = 0;i<useWaterReal.length;i++){
		if(useWaterReal[i]<leftmin){
			leftmin = useWaterReal[i] * 1
		}
		if(useWaterReal[i]>leftmax){
			leftmax = useWaterReal[i] * 1
		}
	}
	for(let i = 0;i<usePowerHis.length;i++){
		if(usePowerHis[i]<rightmin){
			rightmin = usePowerHis[i] * 1
		}
		if(usePowerHis[i]>rightmax){
			rightmax = usePowerHis[i] * 1
		}
	}
	for(let i = 0;i<usePowerReal.length;i++){
		if(usePowerReal[i]<rightmin){
			rightmin = usePowerReal[i] * 1
		}
		if(usePowerReal[i]>rightmax){
			rightmax = usePowerReal[i] * 1
		}
	}
	leftmin+=0
	leftmax+=50
	rightmin-=50
	rightmax+=10
	console.log(leftmin,leftmax,rightmin,rightmax)

	// option = {
	// 	tooltip: {
	// 		trigger: 'axis',
	// 		position: [10, 10],
	// 		axisPointer: {
	// 			lineStyle: {
	// 				color: '#dddc6b',
	// 			}
	// 		}
	// 	},
	// 	legend: {
	// 		top:'0%',
	// 		data:xName,
	// 		// width:'35%',
	// 		textStyle: {
	// 			color: 'rgba(255,255,255,.5)',
	// 			fontSize:'12',
	// 		}
	// 	},
	// 	grid: {
	// 		left: '3%',
	// 		top: '30%',
	// 		right: '3%',
	// 		bottom: '3%',
	// 		containLabel: true
	// 	},
	// 	xAxis: [
	// 		{
	// 			type: 'category',
	// 			boundaryGap: false,
	// 			axisLabel:  {
	// 				textStyle: {
	// 					color: "rgba(255,255,255,.6)",
	// 					fontSize:12,
	// 				},
	// 			},
	// 			axisLine: {
	// 				lineStyle: {
	// 					color: 'rgba(255,255,255,.2)'
	// 				}
	// 			},
	// 			data: xData
	// 		}, {
	// 			axisPointer: {show: false},
	// 			axisLine: {  show: false},
	// 			position: 'bottom',
	// 			offset: 0,
	// 		}
	// 	],
    //
	// 	yAxis: [
	// 		{
	// 			type: 'value',
	// 			name:'m³',
	// 			nameTextStyle: {
	// 				color: '#ccc'
	// 			},
	// 			min: leftmin,
	// 			max: leftmax,
	// 			/*interval: 5,*/
	// 			axisTick: {show: false},
	// 			axisLine: {
	// 				lineStyle: {
	// 					color: 'rgba(255,255,255,.1)'
	// 				}
	// 			},
	// 			axisLabel:  {
	// 				textStyle: {
	// 					color: "rgba(255,255,255,.6)",
	// 					fontSize:12,
	// 				},
	// 			},
    //
	// 			splitLine: {
	// 				lineStyle: {
	// 					color: 'rgba(255,255,255,.1)'
	// 				}
	// 			}
	// 		},
	// 		{
	// 			type: 'value',//右边第一个y轴
	// 			name: 'kwh',
	// 			min: rightmin,
	// 			max: rightmax,
	// 			/*interval: 4,*/
	// 			nameTextStyle: {
	// 				color: '#ccc'
	// 			},
	// 			axisLine: {
	// 				lineStyle: {
	// 					color: 'rgba(255,255,255,.1)'
	// 				}
	// 			},
	// 			axisLabel:  {
	// 				textStyle: {
	// 					color: "rgba(255,255,255,.6)",
	// 					fontSize:12,
	// 				},
	// 			},
	// 			splitLine: {
	// 				lineStyle: {
	// 					color: 'rgba(255,255,255,.1)'
	// 				}
	// 			}
	// 		}
	// 	],
	// 	series: [
	// 		{
	// 			name: xName[0],
	// 			type: 'line',
	// 			smooth: false,
	// 			symbol: 'circle',
	// 			symbolSize: 6,
	// 			showSymbol: true,
	// 			lineStyle: {
	// 				normal: {
	// 					color: '#feff4d',
	// 					width: 2
	// 				}
	// 			},
	// 			areaStyle: {
	// 				normal: {
	// 					color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
	// 						offset: 0,
	// 						color: 'rgba(1, 132, 213, 0.4)'
	// 					}, {
	// 						offset: 0.8,
	// 						color: 'rgba(1, 132, 213, 0.1)'
	// 					}], false),
	// 					shadowColor: 'rgba(0, 0, 0, 0.1)',
	// 				}
	// 			},
	// 			itemStyle: {
	// 				normal: {
	// 					color: '#feff4d',
	// 					borderColor: 'rgba(221, 220, 107, .1)',
	// 					borderWidth: 12
	// 				}
	// 			},
	// 			data: useWaterHis
	// 		},
	// 		{
	// 			name: xName[1],
	// 			type: 'line',
	// 			smooth: false,
	// 			symbol: 'circle',
	// 			yAxisIndex: 1,
	// 			symbolSize: 5,
	// 			showSymbol: true,
	// 			lineStyle: {
	// 				normal: {
	// 					color: '#00d887',
	// 					width: 2
	// 				}
	// 			},
	// 			areaStyle: {
	// 				normal: {
	// 					color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
	// 						offset: 0,
	// 						color: 'rgba(0, 216, 135, 0.4)'
	// 					}, {
	// 						offset: 0.8,
	// 						color: 'rgba(0, 216, 135, 0.1)'
	// 					}], false),
	// 					shadowColor: 'rgba(0, 0, 0, 0.1)',
	// 				}
	// 			},
	// 			itemStyle: {
	// 				normal: {
	// 					color: '#00d887',
	// 					borderColor: 'rgba(221, 220, 107, .1)',
	// 					borderWidth: 12
	// 				}
	// 			},
	// 			data: usePowerHis
	// 		},
	// 		{
	// 			name: xName[3],
	// 			type: 'line',
	// 			smooth: false,
	// 			symbol: 'circle',
	// 			symbolSize: 6,
	// 			showSymbol: true,
	// 			lineStyle: {
	// 				normal: {
	// 					color: '#d87f53',
	// 					width: 2
	// 				}
	// 			},
	// 			areaStyle: {
	// 				normal: {
	// 					color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
	// 						offset: 0,
	// 						color: 'rgba(0, 216, 135, 0.4)'
	// 					}, {
	// 						offset: 0.8,
	// 						color: 'rgba(0, 216, 135, 0.1)'
	// 					}], false),
	// 					shadowColor: 'rgba(0, 0, 0, 0.1)',
	// 				}
	// 			},
	// 			itemStyle: {
	// 				normal: {
	// 					color: '#ff3c51',
	// 					borderColor: 'rgba(221, 220, 107, .1)',
	// 					borderWidth: 12
	// 				}
	// 			},
	// 			data: useWaterReal
	// 		},
	// 		{
	// 			name: xName[4],
	// 			type: 'line',
	// 			smooth: false,
	// 			yAxisIndex: 1,
	// 			symbol: 'circle',
	// 			symbolSize: 6,
	// 			showSymbol: true,
	// 			lineStyle: {
	// 				normal: {
	// 					color: '#d8a8c2',
	// 					width: 2
	// 				}
	// 			},
	// 			areaStyle: {
	// 				normal: {
	// 					color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
	// 						offset: 0,
	// 						color: 'rgba(0, 216, 135, 0.4)'
	// 					}, {
	// 						offset: 0.8,
	// 						color: 'rgba(0, 216, 135, 0.1)'
	// 					}], false),
	// 					shadowColor: 'rgba(0, 0, 0, 0.1)',
	// 				}
	// 			},
	// 			itemStyle: {
	// 				normal: {
	// 					color: '#d8a8c2',
	// 					borderColor: 'rgba(221, 220, 107, .1)',
	// 					borderWidth: 12
	// 				}
	// 			},
	// 			data: usePowerReal
	// 		}
	// 	]
	// };
	option = {
        title: {
            text: '',
            subtext: '',
            left: 'center'
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                animation: false
            }
        },
        legend: {
            data: xName,
			top: 0,
            textStyle: {
                color: 'rgba(255,255,255,.5)',
                fontSize:'12',
			}
        },
        axisPointer: {
            link: {xAxisIndex: 'all'}
        },
        dataZoom: [
            {
                show: true,
                realtime: true,
                height: 10,
				bottom: 15,
                start: 0,
                end: 100,
                xAxisIndex: [0, 1],
                textStyle:{
                    color:"rgba(204,187,225,0.5)",
                },
                fillerColor:"rgba(67,55,160,0.4)",
                borderColor: "rgba(204,187,225,0.5)",
            },
            {
                type: 'inside',
                realtime: true,
                height: 10,
                start: 0,
                end: 35,
                xAxisIndex: [0, 1],
                textStyle:{
                    color:"rgba(204,187,225,0.5)",
                },
                fillerColor:"rgba(67,55,160,0.4)",
                borderColor: "rgba(204,187,225,0.5)",
            }
        ],
        grid: [{
            left: 50,
            right: 50,
            height: '28%'
        }, {
            left: 50,
            right: 50,
            top: '56%',
            height: '30%'
        }],
        xAxis: [
            {
                type: 'category',
                boundaryGap: false,
                axisLine: {onZero: true},
                data: xData,
                axisLabel:  {
                    textStyle: {
                    	color: "rgba(255,255,255,.6)",
                    	fontSize:12,
                    },
				},
                axisLine: {
                    lineStyle: {
                    	color: 'rgba(255,255,255,.2)'
                    }
                },
            },
            {
                gridIndex: 1,
                type: 'category',
                boundaryGap: false,
				// show: false,
                axisLine: {onZero: true},
                data: xData,
                // position: 'top',
                axisLabel:  {
                    textStyle: {
                        color: "rgba(255,255,255,.6)",
                        fontSize:12,
                        // color: 'rgba(0,0,0,0.1)'
                    },
                },
                axisLine: {
                    lineStyle: {
                        color: 'rgba(255,255,255,.2)'
                    }
                },
            }
        ],
        yAxis: [
            {
                name: 'm³',
                type: 'value',
                nameTextStyle: {
                    color: '#ccc'
				},
                axisTick: {show: false},
                			axisLine: {
                    lineStyle: {
                        color: 'rgba(255,255,255,.1)'
                    }
                },
                axisLabel:  {
                    textStyle: {
                        color: "rgba(255,255,255,.6)",
                        fontSize:12,
                    },
                },

                splitLine: {
                    lineStyle: {
                        color: 'rgba(255,255,255,.1)'
                    }
                }
            },
            {
                gridIndex: 1,
                name: 'kWh',
                // inverse: true,
                type: 'value',
                nameTextStyle: {
                    color: '#ccc'
                },
                axisTick: {show: false},
                axisLine: {
                    lineStyle: {
                        color: 'rgba(255,255,255,.1)'
                    }
                },
                axisLabel:  {
                    textStyle: {
                        color: "rgba(255,255,255,.6)",
                        fontSize:12,
                    },
                },

                splitLine: {
                    lineStyle: {
                        color: 'rgba(255,255,255,.1)'
                    }
                }
            },

        ],
        series: [
            {
                name: xName[0],
                type: 'line',
                symbolSize: 8,
                smooth: false,
                symbol: 'circle',
                showSymbol: true,
                hoverAnimation: false,
                data: useWaterHis,
                lineStyle: {
                    				normal: {
                    					color: '#feff4d',
                    					width: 2
                    				}
                    			},
                areaStyle: {
                    				normal: {
                    					color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                    						offset: 0,
                    						color: 'rgba(1, 132, 213, 0.4)'
                    					}, {
                    						offset: 0.8,
                    						color: 'rgba(1, 132, 213, 0.1)'
                    					}], false),
                    					shadowColor: 'rgba(0, 0, 0, 0.1)',
                    				}
                    			},
                itemStyle: {
                    				normal: {
                    					color: '#feff4d',
                    					borderColor: 'rgba(221, 220, 107, .1)',
                    					borderWidth: 12
                    				}
                    			},
            },
            {
                name: xName[1],
                type: 'line',
                xAxisIndex: 1,
                yAxisIndex: 1,
                symbolSize: 8,
                smooth: false,
                symbol: 'circle',
                showSymbol: true,
                hoverAnimation: false,
                data: usePowerHis,
                lineStyle: {
                    normal: {
                        color: '#00d887',
                        width: 2
                    }
                },
                areaStyle: {
                    normal: {
                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                            offset: 0,
                            color: 'rgba(0, 216, 135, 0.4)'
                        }, {
                            offset: 0.8,
                            color: 'rgba(0, 216, 135, 0.1)'
                        }], false),
                        shadowColor: 'rgba(0, 0, 0, 0.1)',
                    }
                },
                itemStyle: {
                    normal: {
                        color: '#00d887',
                        borderColor: 'rgba(221, 220, 107, .1)',
                        borderWidth: 12
                    }
                },
            },
            {
                name: xName[3],
                type: 'line',
                symbolSize: 8,
                smooth: false,
                symbol: 'circle',
                showSymbol: true,
                hoverAnimation: false,
                data: useWaterReal,
                lineStyle: {
                    normal: {
                        color: '#d87f53',
                        width: 2
                    }
                },
                areaStyle: {
                    normal: {
                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                            offset: 0,
                            color: 'rgba(0, 216, 135, 0.4)'
                        }, {
                            offset: 0.8,
                            color: 'rgba(0, 216, 135, 0.1)'
                        }], false),
                        shadowColor: 'rgba(0, 0, 0, 0.1)',
                    }
                },
                itemStyle: {
                    normal: {
                        color: '#ff3c51',
                        borderColor: 'rgba(221, 220, 107, .1)',
                        borderWidth: 12
                    }
                },
            },
            {
                name: xName[4],
                type: 'line',
                xAxisIndex: 1,
                yAxisIndex: 1,
                symbolSize: 8,
                smooth: false,
                symbol: 'circle',
                showSymbol: true,
                hoverAnimation: false,
                data: usePowerReal,
                lineStyle: {
                    normal: {
                        color: '#d8a8c2',
                        width: 2
                    }
                },
                areaStyle: {
                    normal: {
                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                            offset: 0,
                            color: 'rgba(0, 216, 135, 0.4)'
                        }, {
                            offset: 0.8,
                            color: 'rgba(0, 216, 135, 0.1)'
                        }], false),
                        shadowColor: 'rgba(0, 0, 0, 0.1)',
                    }
                },
                itemStyle: {
                    normal: {
                        color: '#d8a8c2',
                        borderColor: 'rgba(221, 220, 107, .1)',
                        borderWidth: 12
                    }
                },
            }
        ]
	}
	// 使用刚指定的配置项和数据显示图表。
	myChart.clear()
	myChart.setOption(option);
	window.addEventListener("resize",function(){
		myChart.resize();
	});
}



		
		
		


		









