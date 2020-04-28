
$(function () {
	// getPumpData();
    getPumStatus()
})

function getPumpData() {
	jQuery.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : 'a' + '/homeV2/getDeviceAreaData' + "?"
			+ Math.random(),
		dataType : 'json',
		success : function(data) {
			if (undefined == data || data.length < 1) {
				return;
			} else {
				echarts_02(data);
			}
		},
		error : function(data) {

		}
	});
}
function isInArray(arr,value){
    for(var i = 0; i < arr.length; i++){
        if(value === arr[i]){
            return true;
        }
    }
    return false;
}
// 获取泵房状态
function getPumStatus() {
	var alarmList = []
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        async: false,
        url: CONTEXT_PATH + '/treeType/getTreeDataByType' + "?" + new Date().getTime(),
        dataType: 'JSON',
        data: JSON.stringify({
            treeTypeValue: GLOBAL_TREE_TYPR,
            isSpread: GLOBAL_TREE_PUMP_EQT,
            isDeviceOrVideo: GLOBAL_TREE_VIDEO_EQT,
        }),
        success: function (res) {
            GLOBAL_REGION_LIST = res
                window.pumpList = [];
            	let areaList = res
                var k=0;
                areaList.map(function (val) {
                    if(val.pumpHouseId){
                        window.pumpList.push(val)
                    }
                })
            var allPumpArray = []
            window.pumpList.map(function (v) {
            	if(allPumpArray.indexOf((v.pumpHouseId)) == -1) {
                    allPumpArray.push(v.pumpHouseId);
				}
            })
            $.ajax({
                type: 'POST',
                async: false,
                contentType: 'application/json',
                url: CONTEXT_PATH + '/monitor/statistic/pumpOnlineStat',
                data: JSON.stringify(allPumpArray),
                dataType: 'JSON',
                success: function (res) {
                	console.log('222',res)
                    //获取告警的泵房 集合
                    var alarmListTmpe = [];
                    $.ajax({
                        type: 'POST',
                        async:false,
                        contentType: 'application/json',
                        url: CONTEXT_PATH + '/alarmStatController/getAlarmByPumpIds',
                        data: JSON.stringify({phIdList:allPumpArray}) ,
                        dataType: 'JSON',
                        //traditional : true,
                        success: function (data) {
                        	console.log('666666',data)
                            if(data!=null && data.length){
                                for (var i in data) {
                                    alarmListTmpe.push(data[i].phId)
                                }
                            }
                            alarmList = alarmListTmpe;
                        	console.log(alarmList)
                            let pumpnum1 = 0,pumpnum2 = 0,pumpnum3 = 0,pumpnum4 = 0
							console.log(window.pumpList)
                            allPumpArray.map(function (v) {
                                if (isInArray(alarmList, v)) {
                                    pumpnum4++
                                } else if (isInArray(res, v)) {
                                    pumpnum2++
                                } else {
                                    pumpnum3++
                                }
                            })
							let statusObj = [{
                        		"name":"在线","itemStyle":"","value":pumpnum2},
								{"name":"离线","itemStyle":"","value":pumpnum3},
								{"name":"告警","itemStyle":"","value":pumpnum4},]
                            echarts_02(statusObj)
                        }
                    })

                }
            })
        }
    })
}
function echarts_02(data) {
	console.log(data)
	var colorArr=["#218de0", "#01cbb3", "#85e647", "#5d5cda", "#05c5b0", "#c29927", "#c29927","#8dc2b2","#c2bd60","#c27212"];
	var colorAlpha=['rgba(60,170,211,0.64)','rgba(1,203,179,0.64)','rgba(133,230,71,0.65)','rgba(93,92,218,0.65)','rgba(5,197,176,0.65)','rgba(194,153,39,0.65)','rgba(93,92,218,0.64)','rgba(5,197,176,0.65)','rgba(5,197,176,0.65)']

	var lableData=[];
	//为data 加样式

	for(var i=0;i<data.length;i++){
		var itemStyle={
						normal:{
							borderColor: colorArr[i],
							borderWidth:2,
							shadowBlur: 20,
							shadowColor: colorArr[i],
							shadowOffsetx: 25,
							shadowOffsety: 20,
							color:colorAlpha[i]
						}
					};
		data[i].itemStyle=itemStyle;
		lableData.push(data[i].name)
	}


	var myChart = echarts.init(document.getElementById('echart_02'));
	var option = {
		// backgroundColor:"#090e36",
		/*legend: {
			top:'0%',
			data:lableData,
			textStyle: {
				color: 'rgba(255,255,255,.5)',
				fontSize:'12',
			}
		},*/
		grid: {
			containLabel: true
		},
		tooltip: {
			trigger: 'item',
			formatter: "{b} : {c} ({d}%)"
		},
		legend: {
			show:true,
			x: 'right',
			orient: 'vertical',
			data:lableData,
			textStyle: {
				color: 'rgba(255,255,255,.5)',
				fontSize:'12',
			}
		},

		polar: {},
		angleAxis: {
			interval: 1,
			type: 'category',
			data: [],
			z: 10,
			axisLine: {
				show: false,
				lineStyle: {
					color: "#0B4A6B",
					width: 1,
					type: "solid"
				},
			},
			axisLabel: {
				interval: 0,
				show: true,
				color: "#0B4A6B",
				margin: 8,
				fontSize: 16
			},
		},
		radiusAxis: {
			min: 20,
			max: 120,
			interval: 20,
			axisLine: {
				show: false,
				lineStyle: {
					color: "#0B3E5E",
					width: 1,
					type: "solid"
				},
			},
			axisLabel: {
				formatter: '{value} %',
				show: false,
				padding: [0, 0, 20, 0],
				color: "#0B3E5E",
				fontSize: 16
			},
			splitLine: {
				lineStyle: {
					color: "#07385e",
					width: 2,
					type: "dashed"
				}
			}
		},
		// calculable: true,
		series: [ {
			// stack: 'a',
			type: 'pie',
			radius: ['45%','70%'],
			center: ['50%', '50%'],//图型  位置
			// roseType: 'radius',
			// zlevel:10,
			// startAngle: 100,

			label: {
				normal: {
					textStyle: {
						color: "rgba(255, 255, 255)"
					},
					// formatter: [ '{b}','{d}%'].join('\n'),
					formatter:function(params){
						return params.name+"\n"+params.value + ' (' +params.percent+'%）'
					},
					// rich: {
					// 	b: {
					// 		color: '#fff',
					// 		fontSize: 14,
					// 		lineHeight: 20
					// 	},
					// 	d: {
					// 		color: '#fff',
					// 		fontSize: 14,
					// 		height: 20
					// 	},
					// },
				}
			},
			labelLine: {
				// normal: {
				// 	show: true,
				// 	length: 10,
				// 	length2: 45,
				// 	lineStyle: {
				// 		color: '#aaa'
                //
				// 	}
				// },
				// emphasis: {
				// 	show: false
				// }
			},
			data: data
		}, ]
	}


	myChart.setOption(option);
	window.addEventListener("resize",function(){
		myChart.resize();
	});
}



		
		
		


		









