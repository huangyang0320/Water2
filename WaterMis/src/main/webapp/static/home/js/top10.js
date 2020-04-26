
$(function () {
	getTop10_frist_last();
	echarts_paiming4();
})

function getTop10_frist_last() {
	jQuery.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : 'a' + '/homeV2/getUseWaterDayAddAnalysisByPumpHouse' + "?"
			+ Math.random(),
		dataType : 'json',
		success : function(data) {
			if (undefined == data || data.length < 1) {
				return;
			} else {
				//后10
				echarts_paiming3(data.top10_Deac_Name,data.top10_Deac_Value,null);
				//前10
				echarts_paiming1(data.top10_Asc_Name,data.top10_Asc_Value,null);

				//增量 前10
				echarts_paiming2(data.top10_Add_Deac_Name,data.top10_Add_Deac_Value,data.top10_Add_Deac_Default_Value);

				//减量 前10
				echarts_paiming4(data.top10_Jian_Deac_Name,data.top10_Jian_Deac_Value,data.top10_Jian_Deac_Default_Value);
			}
		},
		error : function(data) {

		}
	});
}





function echarts_paiming1(dataName,dataValue,dataDefault) {
	var mypaiming1 = echarts.init(document.getElementById('paiming1'));
	
	var option1 = {
		title: {
		        text: '昨日用水量前10',
		        x: 'left',
		        top: "0",
		        textStyle: {
		            color: '#fff',
		            fontSize: 14
		        }
		    },
	    grid: {
	        left: '2%',
	        top: '12%',
	        bottom: '1%',
	    },
	    tooltip: {      //可以配合下方的tooltip出来单个悬浮信息.
			trigger: 'item',
			textStyle: {
				fontSize: 12,
			},
	    },
	    xAxis: {
	        type: 'value',
	        splitLine: {
	            show: false
	        },
	        axisLabel: {
	            show: false
	        },
	        axisLine: {
	            show: false
	        },
	        axisTick: {
	            show: false
	        }
	    },
	    yAxis: {
	        type: 'category',
	        data: dataName,
	        splitLine: {
	            show: false
	        },
	        axisLine: {
	            show: false
	        },
	        axisTick: {
	            show: false
	        },
	        axisLabel: {
	                show:false
	            }
	    },
	    series: [{
	        type: 'bar',
	        // data: [ 0,0,0, 0, 0, 0, 0, 210, 1150, 1310,1360, 1440, 1530, 1550, 3480,3850, 4000, 4000, 4000, 4690,23590],
			data: dataValue,
	        barWidth: 10,
	        z:10,
	        itemStyle: {
	          normal: {
	        	color: new echarts.graphic.LinearGradient(1, 0, 0, 0, [{
	        	  offset: 0,
	        	  color: '#57eabf' // 0% 处的颜色
	        	}, {
	        	  offset: 1,
	        	  color: '#2563f9' // 100% 处的颜色
	        	}], false),
	        	barBorderRadius: 14
	          }
	        },
	            label: {
	                show: true,
	                fontSize: 20,
	                distance: 10,
	                position: [0, "-100%"],
	                offset: [0, -50],
	                normal: {
	                    show: true,
	                    position: 'right',
	                    color: '#fff',
						formatter:'{c} m³'
	                    //TODO 
	                }
	            },
	    }, {
	            // current data
	             type: 'bar',
	            symbol: 'rect',
	            symbolRepeat: 'fixed',
	            symbolMargin: '70%',
	            symbolClip: true,
	            symbolSize: [10,70],
	            symbolBoundingData: 1000,
	                  
	                label: {
	                    normal: {
	                        show: true,
	                        position: 'right',
	                        offset: [-10, 70],
	                        textStyle: {
	                            color: 'darkorange',
	                            fontSize: 18
	                        }
	                    }
	                },
	           data: [
	                {
	                    "value":0,
	                    label: {
	                        normal: {
	                            show: true,
	                            position: [-10, -20],
	                            formatter:'{b}',
	                            offset: [10, 0],
	                            textStyle: {
	                                color: '#aaa',
	                                fontSize: 12
	                            }
	                        }
	                    }
	                },
					{
					    "value":0,
					    label: {
					        normal: {
					            show: true,
					            position: [-10, -20],
					            formatter:'{b}',
					            offset: [10, 0],
					            textStyle: {
					                color: '#aaa',
					                fontSize: 12
					            }
					        }
					    }
					},
	                {
	                    "value":0,
	                    label: {
	                        normal: {
	                            show: true,
	                            position: [-10, -20],
	                            formatter:'{b}',
	                            offset: [10, 0],
	                            textStyle: {
	                                color: '#aaa',
	                                fontSize: 12
	                            }
	                        }
	                    }
	                },
					{
					    "value":0,
					    label: {
					        normal: {
					            show: true,
					            position: [-10, -20],
					            formatter:'{b}',
					            offset: [10, 0],
					            textStyle: {
					                color: '#aaa',
					                fontSize: 12
					            }
					        }
					    }
					},
					{
					    "value":0,
					    label: {
					        normal: {
					            show: true,
					            position: [-10, -20],
					            formatter:'{b}',
					            offset: [10, 0],
					            textStyle: {
					                color: '#aaa',
					                fontSize: 12
					            }
					        }
					    }
					},
					{
					    "value":0,
					    label: {
					        normal: {
					            show: true,
					            position: [-10, -20],
					            formatter:'{b}',
					            offset: [10, 0],
					            textStyle: {
					                color: '#aaa',
					                fontSize: 12
					            }
					        }
					    }
					},
					{
					    "value":0,
					    label: {
					        normal: {
					            show: true,
					            position: [-10, -20],
					            formatter:'{b}',
					            offset: [10, 0],
					            textStyle: {
					                color: '#aaa',
					                fontSize: 12
					            }
					        }
					    }
					},
					{
					    "value":0,
					    label: {
					        normal: {
					            show: true,
					            position: [-10, -20],
					            formatter:'{b}',
					            offset: [10, 0],
					            textStyle: {
					                color: '#aaa',
					                fontSize: 12
					            }
					        }
					    }
					},
					{
					    "value":0,
					    label: {
					        normal: {
					            show: true,
					            position: [-10, -20],
					            formatter:'{b}',
					            offset: [10, 0],
					            textStyle: {
					                color: '#aaa',
					                fontSize: 12
					            }
					        }
					    }
					},
	                {
	                    "value":0,
	                    label: {
	                        normal: {
	                            show: true,
	                            position: [-10, -20],
	                            formatter:'{b}',
	                            offset: [10, 0],
	                            textStyle: {
	                                color: '#aaa',
	                                fontSize: 12
	                            }
	                        }
	                    }
	                },],
	            z: 99999999,
	           
	        }, {
	        type: 'bar',
	        // data: [4000, 10000, 10000, 2000, 6000,5000, 5000, 5000,5000, 5000,5000, 5000, 3480, 5000, 4000,4000, 4000, 4810, 37000],
	       //data: dataDefault,
	        barWidth: 10,
			tooltip: {
						show: false
					},
	        barGap: '-100%',
	        label: { 
	            normal: {
	                show: false,
	            }
	        },
	         itemStyle:{
	            normal:{
	                color:'#0278e7',
	                barBorderRadius : 10
	            }
	        }
	    }]
	};

	 mypaiming1.setOption(option1);
        window.addEventListener("resize",function(){
            mypaiming1.resize();
        });
    }

function echarts_paiming2(dataName,dataValue,dataDefault) {
	var mypaiming2 = echarts.init(document.getElementById('paiming2'));

	var option2 = {
		title: {
		        text: '昨日增量前10',
		        x: 'left',
		        top: "0",
		        textStyle: {
		            color: '#fff',
		            fontSize: 14
		        }
		    },
	    grid: {
	        left: '2%',
	        top: '12%',
	        bottom: '1%',
	    },
	    tooltip: {      //可以配合下方的tooltip出来单个悬浮信息.
			trigger: 'item',
			textStyle: {
				fontSize: 12,
			},
	    },
	    xAxis: {
	        type: 'value',
	        splitLine: {
	            show: false
	        },
	        axisLabel: {
	            show: false
	        },
	        axisLine: {
	            show: false
	        },
	        axisTick: {
	            show: false
	        }
	    },
	    yAxis: {
	        type: 'category',
	        data: dataName,
	        splitLine: {
	            show: false
	        },
	        axisLine: {
	            show: false
	        },
	        axisTick: {
	            show: false
	        },
	        axisLabel: {
	                show:false
	            }
	    },
	    series: [{
	        type: 'bar',
	        // data: [ 0,0,0, 0, 0, 0, 0, 210, 1150, 1310,1360, 1440, 1530, 1550, 3480,3850, 4000, 4000, 4000, 4690,23590],
			data: dataValue,
	        barWidth: 10,
	        z:10,
	        itemStyle: {
	          normal: {
	        	color: new echarts.graphic.LinearGradient(1, 0, 0, 0, [{
	        	  offset: 0,
	        	  color: '#f05f1c' // 0% 处的颜色
	        	}, {
	        	  offset: 1,
	        	  color: '#e9ea07' // 100% 处的颜色
	        	}], false),
	        	barBorderRadius: 14
	          }
	        },
	            label: {
	                show: true,
	                fontSize: 20,
	                distance: 10,
	                position: [0, "-100%"],
	                offset: [0, -50],
	                normal: {
	                    show: true,
	                    position: 'right',
	                    color: '#fff',
						formatter:'{c} m³'
	                    //TODO
	                }
	            },
	    }, {
	            // current data
	             type: 'bar',
	            symbol: 'rect',
	            symbolRepeat: 'fixed',
	            symbolMargin: '70%',
	            symbolClip: true,
	            symbolSize: [10,70],
	            symbolBoundingData: 1000,

	                label: {
	                    normal: {
	                        show: true,
	                        position: 'right',
	                        offset: [-10, 70],
	                        textStyle: {
	                            color: 'darkorange',
	                            fontSize: 18
	                        }
	                    }
	                },
	           data: [
	                {
	                    "value":0,
	                    label: {
	                        normal: {
	                            show: true,
	                            position: [-10, -20],
	                            formatter:'{b}',
	                            offset: [10, 0],
	                            textStyle: {
	                                color: '#aaa',
	                                fontSize: 12
	                            }
	                        }
	                    }
	                },
					{
					    "value":0,
					    label: {
					        normal: {
					            show: true,
					            position: [-10, -20],
					            formatter:'{b}',
					            offset: [10, 0],
					            textStyle: {
					                color: '#aaa',
					                fontSize: 12
					            }
					        }
					    }
					},
	                {
	                    "value":0,
	                    label: {
	                        normal: {
	                            show: true,
	                            position: [-10, -20],
	                            formatter:'{b}',
	                            offset: [10, 0],
	                            textStyle: {
	                                color: '#aaa',
	                                fontSize: 12
	                            }
	                        }
	                    }
	                },
					{
					    "value":0,
					    label: {
					        normal: {
					            show: true,
					            position: [-10, -20],
					            formatter:'{b}',
					            offset: [10, 0],
					            textStyle: {
					                color: '#aaa',
					                fontSize: 12
					            }
					        }
					    }
					},
					{
					    "value":0,
					    label: {
					        normal: {
					            show: true,
					            position: [-10, -20],
					            formatter:'{b}',
					            offset: [10, 0],
					            textStyle: {
					                color: '#aaa',
					                fontSize: 12
					            }
					        }
					    }
					},
					{
					    "value":0,
					    label: {
					        normal: {
					            show: true,
					            position: [-10, -20],
					            formatter:'{b}',
					            offset: [10, 0],
					            textStyle: {
					                color: '#aaa',
					                fontSize: 12
					            }
					        }
					    }
					},
					{
					    "value":0,
					    label: {
					        normal: {
					            show: true,
					            position: [-10, -20],
					            formatter:'{b}',
					            offset: [10, 0],
					            textStyle: {
					                color: '#aaa',
					                fontSize: 12
					            }
					        }
					    }
					},
					{
					    "value":0,
					    label: {
					        normal: {
					            show: true,
					            position: [-10, -20],
					            formatter:'{b}',
					            offset: [10, 0],
					            textStyle: {
					                color: '#aaa',
					                fontSize: 12
					            }
					        }
					    }
					},
					{
					    "value":0,
					    label: {
					        normal: {
					            show: true,
					            position: [-10, -20],
					            formatter:'{b}',
					            offset: [10, 0],
					            textStyle: {
					                color: '#aaa',
					                fontSize: 12
					            }
					        }
					    }
					},
	                {
	                    "value":0,
	                    label: {
	                        normal: {
	                            show: true,
	                            position: [-10, -20],
	                            formatter:'{b}',
	                            offset: [10, 0],
	                            textStyle: {
	                                color: '#aaa',
	                                fontSize: 12
	                            }
	                        }
	                    }
	                },],
	            z: 99999999,

	        }, {
	        type: 'bar',
	        // data: [4000, 10000, 10000, 2000, 6000,5000, 5000, 5000,5000, 5000,5000, 5000, 3480, 5000, 4000,4000, 4000, 4810, 37000],
	       data: dataDefault,
	        barWidth: 10,
			tooltip: {
						show: true
					},
	        barGap: '-100%',
	        label: {
	            normal: {
	                show: false,
	            }
	        },
	         itemStyle:{
	            normal:{
	                color:'#0278e7',
	                barBorderRadius : 10
	            }
	        }
	    }]
	};

	 mypaiming2.setOption(option2);
        window.addEventListener("resize",function(){
            mypaiming2.resize();
        });
    }




function echarts_paiming3(dataName,dataValue,dataDefault) {
	var mypaiming3 = echarts.init(document.getElementById('paiming3'));

	var option3 = {
		title: {
		        text: '昨日用水量后10',
		        x: 'left',
		        top: "0",
		        textStyle: {
		            color: '#fff',
		            fontSize: 14
		        }
		    },
	    grid: {
	        left: '2%',
	        top: '12%',
	        bottom: '1%',
	    },
	    tooltip: {      //可以配合下方的tooltip出来单个悬浮信息.
			trigger: 'item',
			textStyle: {
				fontSize: 12,
			},
	    },
	    xAxis: {
	        type: 'value',
	        splitLine: {
	            show: false
	        },
	        axisLabel: {
	            show: false
	        },
	        axisLine: {
	            show: false
	        },
	        axisTick: {
	            show: false
	        }
	    },
	    yAxis: {
	        type: 'category',
	        data: dataName,
	        splitLine: {
	            show: false
	        },
	        axisLine: {
	            show: false
	        },
	        axisTick: {
	            show: false
	        },
	        axisLabel: {
	                show:false
	            }
	    },
	    series: [{
	        type: 'bar',
	        // data: [ 0,0,0, 0, 0, 0, 0, 210, 1150, 1310,1360, 1440, 1530, 1550, 3480,3850, 4000, 4000, 4000, 4690,23590],
			data: dataValue,
	        barWidth: 10,
	        z:10,
	        itemStyle: {
	          normal: {
	        	color: new echarts.graphic.LinearGradient(1, 0, 0, 0, [{
	        	  offset: 0,
	        	  color: '#00d887' // 0% 处的颜色
	        	}, {
	        	  offset: 1,
	        	  color: '#61d815' // 100% 处的颜色
	        	}], false),
	        	barBorderRadius: 14
	          }
	        },
	            label: {
	                show: true,
	                fontSize: 20,
	                distance: 10,
	                position: [0, "-100%"],
	                offset: [0, -50],
	                normal: {
	                    show: true,
	                    position: 'right',
	                    color: '#fff',
						formatter:'{c} m³'
	                    //TODO
	                }
	            },
	    }, {
	            // current data
	             type: 'bar',
	            symbol: 'rect',
	            symbolRepeat: 'fixed',
	            symbolMargin: '70%',
	            symbolClip: true,
	            symbolSize: [10,70],
	            symbolBoundingData: 1000,

	                label: {
	                    normal: {
	                        show: true,
	                        position: 'right',
	                        offset: [-10, 70],
	                        textStyle: {
	                            color: 'darkorange',
	                            fontSize: 18
	                        }
	                    }
	                },
	           data: [
	                {
	                    "value":0,
	                    label: {
	                        normal: {
	                            show: true,
	                            position: [-10, -20],
	                            formatter:'{b}',
	                            offset: [10, 0],
	                            textStyle: {
	                                color: '#aaa',
	                                fontSize: 12
	                            }
	                        }
	                    }
	                },
					{
					    "value":0,
					    label: {
					        normal: {
					            show: true,
					            position: [-10, -20],
					            formatter:'{b}',
					            offset: [10, 0],
					            textStyle: {
					                color: '#aaa',
					                fontSize: 12
					            }
					        }
					    }
					},
	                {
	                    "value":0,
	                    label: {
	                        normal: {
	                            show: true,
	                            position: [-10, -20],
	                            formatter:'{b}',
	                            offset: [10, 0],
	                            textStyle: {
	                                color: '#aaa',
	                                fontSize: 12
	                            }
	                        }
	                    }
	                },
					{
					    "value":0,
					    label: {
					        normal: {
					            show: true,
					            position: [-10, -20],
					            formatter:'{b}',
					            offset: [10, 0],
					            textStyle: {
					                color: '#aaa',
					                fontSize: 12
					            }
					        }
					    }
					},
					{
					    "value":0,
					    label: {
					        normal: {
					            show: true,
					            position: [-10, -20],
					            formatter:'{b}',
					            offset: [10, 0],
					            textStyle: {
					                color: '#aaa',
					                fontSize: 12
					            }
					        }
					    }
					},
					{
					    "value":0,
					    label: {
					        normal: {
					            show: true,
					            position: [-10, -20],
					            formatter:'{b}',
					            offset: [10, 0],
					            textStyle: {
					                color: '#aaa',
					                fontSize: 12
					            }
					        }
					    }
					},
					{
					    "value":0,
					    label: {
					        normal: {
					            show: true,
					            position: [-10, -20],
					            formatter:'{b}',
					            offset: [10, 0],
					            textStyle: {
					                color: '#aaa',
					                fontSize: 12
					            }
					        }
					    }
					},
					{
					    "value":0,
					    label: {
					        normal: {
					            show: true,
					            position: [-10, -20],
					            formatter:'{b}',
					            offset: [10, 0],
					            textStyle: {
					                color: '#aaa',
					                fontSize: 12
					            }
					        }
					    }
					},
					{
					    "value":0,
					    label: {
					        normal: {
					            show: true,
					            position: [-10, -20],
					            formatter:'{b}',
					            offset: [10, 0],
					            textStyle: {
					                color: '#aaa',
					                fontSize: 12
					            }
					        }
					    }
					},
	                {
	                    "value":0,
	                    label: {
	                        normal: {
	                            show: true,
	                            position: [-10, -20],
	                            formatter:'{b}',
	                            offset: [10, 0],
	                            textStyle: {
	                                color: '#aaa',
	                                fontSize: 12
	                            }
	                        }
	                    }
	                },],
	            z: 99999999,

	        }, {
	        type: 'bar',
	        // data: [4000, 10000, 10000, 2000, 6000,5000, 5000, 5000,5000, 5000,5000, 5000, 3480, 5000, 4000,4000, 4000, 4810, 37000],
	       //data: dataDefault,
	        barWidth: 10,
			tooltip: {
						show: false
					},
	        barGap: '-100%',
	        label: {
	            normal: {
	                show: false,
	            }
	        },
	         itemStyle:{
	            normal:{
	                color:'#0278e7',
	                barBorderRadius : 10
	            }
	        }
	    }]
	};

	 mypaiming3.setOption(option3);
        window.addEventListener("resize",function(){
            mypaiming3.resize();
        });
    }



function echarts_paiming4(dataName,dataValue,dataDefault) {
	// for(let i=0;i<dataValue.length;i++){
	// 	dataValue[i] = dataValue[i]+'m³'
	// }
	var mypaiming4 = echarts.init(document.getElementById('paiming4'));

	var option4 = {
		title: {
			text: '昨日减量前10',
			x: 'left',
			top: "0",
			textStyle: {
				color: '#fff',
				fontSize: 14
			}
		},
	    grid: {
	        left: '2%',
	        top: '12%',
	        bottom: '1%',
	    },
	    tooltip: {      //可以配合下方的tooltip出来单个悬浮信息.
			trigger: 'item',
			textStyle: {
				fontSize: 12,
			},
	    },
	    xAxis: {
	        type: 'value',
	        splitLine: {
	            show: false
	        },
	        axisLabel: {
	            show: false
	        },
	        axisLine: {
	            show: false
	        },
	        axisTick: {
	            show: false
	        }
	    },
	    yAxis: {
	        type: 'category',
	        data: dataName,
	        splitLine: {
	            show: false
	        },
	        axisLine: {
	            show: false
	        },
	        axisTick: {
	            show: false
	        },
	        axisLabel: {
				show:false
			}
	    },
	    series: [
	    	{
				type: 'bar',
				data: dataValue,//[249, 260, 280,289, 300, 350,360, 370, 380, 400],
				barWidth: 10,
				z:10,
				itemStyle: {
					normal: {
						color: new echarts.graphic.LinearGradient(1, 0, 0, 0, [
							{
								offset: 0,
								color: '#d8d7cd' // 0% 处的颜色
							}, {
								offset: 1,
								color: '#d87f53' // 100% 处的颜色
							}
						], false),
						barBorderRadius: 14
					}
	        	},
	            label: {
	                show: true,
	                fontSize: 20,
	                distance: 10,
	                position: [0, "-100%"],
	                offset: [0, -50],
	                normal: {
	                    show: true,
	                    position: 'right',
	                    color: '#fff',
						formatter:'{c} m³'
	                    //TODO
	                },
				},
	    	}, {
	            // current data
	            type: 'bar',
	            symbol: 'rect',
	            symbolRepeat: 'fixed',
	            symbolMargin: '70%',
	            symbolClip: true,
	            symbolSize: [10,70],
	            symbolBoundingData: 1000,
				label: {
					normal: {
						show: true,
						position: 'right',
						offset: [-10, 70],
						textStyle: {
							color: 'darkorange',
							fontSize: 18
						}
					}
				},
	            data: [
	                {
	                    "value":0,
	                    label: {
	                        normal: {
	                            show: true,
	                            position: [-10, -20],
	                            formatter:'{b}',
	                            offset: [10, 0],
	                            textStyle: {
	                                color: '#aaa',
	                                fontSize: 12
	                            }
	                        }
	                    }
	                },
					{
					    "value":0,
					    label: {
					        normal: {
					            show: true,
					            position: [-10, -20],
					            formatter:'{b}',
					            offset: [10, 0],
					            textStyle: {
					                color: '#aaa',
					                fontSize: 12
					            }
					        }
					    }
					},
	                {
	                    "value":0,
	                    label: {
	                        normal: {
	                            show: true,
	                            position: [-10, -20],
	                            formatter:'{b}',
	                            offset: [10, 0],
	                            textStyle: {
	                                color: '#aaa',
	                                fontSize: 12
	                            }
	                        }
	                    }
	                },
					{
					    "value":0,
					    label: {
					        normal: {
					            show: true,
					            position: [-10, -20],
					            formatter:'{b}',
					            offset: [10, 0],
					            textStyle: {
					                color: '#aaa',
					                fontSize: 12
					            }
					        }
					    }
					},
					{
					    "value":0,
					    label: {
					        normal: {
					            show: true,
					            position: [-10, -20],
					            formatter:'{b}',
					            offset: [10, 0],
					            textStyle: {
					                color: '#aaa',
					                fontSize: 12
					            }
					        }
					    }
					},
					{
					    "value":0,
					    label: {
					        normal: {
					            show: true,
					            position: [-10, -20],
					            formatter:'{b}',
					            offset: [10, 0],
					            textStyle: {
					                color: '#aaa',
					                fontSize: 12
					            }
					        }
					    }
					},
					{
					    "value":0,
					    label: {
					        normal: {
					            show: true,
					            position: [-10, -20],
					            formatter:'{b}',
					            offset: [10, 0],
					            textStyle: {
					                color: '#aaa',
					                fontSize: 12
					            }
					        }
					    }
					},
					{
					    "value":0,
					    label: {
					        normal: {
					            show: true,
					            position: [-10, -20],
					            formatter:'{b}',
					            offset: [10, 0],
					            textStyle: {
					                color: '#aaa',
					                fontSize: 12
					            }
					        }
					    }
					},
					{
					    "value":0,
					    label: {
					        normal: {
					            show: true,
					            position: [-10, -20],
					            formatter:'{b}',
					            offset: [10, 0],
					            textStyle: {
					                color: '#aaa',
					                fontSize: 12
					            }
					        }
					    }
					},
	                {
	                    "value":0,
	                    label: {
	                        normal: {
	                            show: true,
	                            position: [-10, -20],
	                            formatter:'{b}',
	                            offset: [10, 0],
	                            textStyle: {
	                                color: '#aaa',
	                                fontSize: 12
	                            }
	                        }
	                    }
	                },],
	            z: 99999999,

	        }, {
	        	type: 'bar',
	       		data: dataDefault,//[ 400, 400, 400,400, 400, 400,400, 400, 400,400],
	        	barWidth: 10,
				tooltip: {
					show: true
				},
	        	barGap: '-100%',
	        	label: {
	            	normal: {
	               		show: false,
					}
				},
				itemStyle:{
					normal:{
						color:'#0278e7',
						barBorderRadius : 10
					}
				}
	    	}
	    ]
	};
	
	 mypaiming4.setOption(option4);
        window.addEventListener("resize",function(){
            mypaiming4.resize();
        });
    }





		
		
		


		









