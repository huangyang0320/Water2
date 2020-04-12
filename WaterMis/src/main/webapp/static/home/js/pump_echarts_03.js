
$(function () {
	getPumpData_03();
})

function getPumpData_03() {
	jQuery.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : 'a' + '/homeV2/getOperationsData' + "?"
			+ Math.random(),
		dataType : 'json',
		success : function(data) {
			if (undefined == data || data.length < 1) {
				return;
			} else {
				var xData=last12Month().sort();
				var yDataAlarm=[];
				var yDataMaintenance=[];
				var yDataWork=[];

				var alarmMap = new Map();
				var maintenanceMap = new Map();
				var workMap = new Map();



				if(undefined != data.alarm && data.alarm.length>0){
					for(var i=0;i<(data.alarm).length;i++){
						alarmMap.put((data.alarm[i].name),((data.alarm[i]).memo));
					}
				}

				if(undefined != data.maintenance && data.maintenance.length>0){
					for(var i=0;i<(data.maintenance).length;i++){
						maintenanceMap.put((data.maintenance[i].name),(data.maintenance[i].memo));
					}
				}

				if(undefined != data.work && data.work.length>0){
					for(var i=0;i<(data.work).length;i++){
						workMap.put((data.work[i].name),(data.work[i].memo));
					}
				}

				for(var i=0;i<xData.length;i++){
					if(alarmMap.get(xData[i])!=null && alarmMap.get(xData[i])!=""){
						yDataAlarm.push(alarmMap.get(xData[i]))
					}else{
						yDataAlarm.push(0);
					}

					if(maintenanceMap.get(xData[i])!=null && maintenanceMap.get(xData[i])!=""){
						yDataMaintenance.push(maintenanceMap.get(xData[i]))
					}else{
						yDataMaintenance.push(0);
					}

					if(workMap.get(xData[i])!=null && workMap.get(xData[i])!=""){
						yDataWork.push(workMap.get(xData[i]))
					}else{
						yDataWork.push(0);
					}
				}

				echarts_03(xData,yDataAlarm,yDataMaintenance,yDataWork);
			}
		},
		error : function(data) {

		}
	});
}

function echarts_03(xData,yDataAlarm,yDataMaintenance,yDataWork) {


	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('echarts_03'));

	option = {
		tooltip: {
			trigger: 'axis',
			axisPointer: {
				lineStyle: {
					color: '#dddc6b'
				}
			}
		},
		legend: {
			top:'0%',
			data:['告警数','工单数','维修数'],
			textStyle: {
				color: 'rgba(255,255,255,.5)',
				fontSize:'12',
			}
		},
		grid: {
			left: '10',
			top: '30',
			right: '10',
			bottom: '10',
			containLabel: true
		},

		xAxis: [{
			type: 'category',
			boundaryGap: false,
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

			data: xData

		}, {

			axisPointer: {show: false},
			axisLine: {  show: false},
			position: 'bottom',
			offset: 20,



		}],

		yAxis: [{
			type: 'value',
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
		}],
		series: [
			{
				name: '告警数',
				type: 'line',
				smooth: false,
				symbol: 'circle',
				symbolSize: 6,
				showSymbol: true,
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
				data: yDataAlarm

			},
			{
				name: '工单数',
				type: 'line',
				smooth: false,
				symbol: 'circle',
				symbolSize: 6,
				showSymbol: true,
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
				data: yDataWork

			},
			{
				name: '维修数',
				type: 'line',
				smooth: false,
				symbol: 'circle',
				symbolSize: 6,
				showSymbol: true,
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
				data: yDataMaintenance

			}

		]

	};

	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);
	window.addEventListener("resize",function(){
		myChart.resize();
	});
}



		
		
		


		









