var alertType = '0';
var total = 0;
var hideColumns;
var exportType="0";
var topObject = new Array();
var lastObject = new Array();
var highObject = new Array();
var topColor = ['#abc93b', '#abc93b', '#abc93b', '#abc93b', '#abc93b', '#abc93b', '#abc93b', '#abc93b', '#abc93b', '#abc93b'];
var lastColor = ['#542ff4', '#542ff4', '#542ff4', '#542ff4', '#542ff4', '#542ff4', '#542ff4', '#542ff4', '#542ff4', '#542ff4'];
var highColor = ['#8085e8', '#8085e8', '#8085e8', '#8085e8', '#8085e8', '#8085e8', '#8085e8', '#8085e8', '#8085e8', '#8085e8'];
$(function(){
	$("#lastLi").hide();
	$("#highLi").hide();
	 $("#query").click(function(){
		 
		 $("#topLi").addClass("active");
		 $("#lastLi").removeClass("active");
		 $("#highLi").removeClass("active");
		 
		 $("#lastLi").hide();
		$("#highLi").hide();
		 
		getEnergyRank();
		 
    });
	 
	 $("#topLi").click(function(){
		 var titleName = "前十名千吨水百米能耗";
		 showChar(titleName,topObject,$('#char_top'),topColor);
	 });
	 
	 $("#lastLi").click(function(){
		 var titleName = "后十名千吨水百米能耗";
		 showChar(titleName,lastObject,$('#char_top'),lastColor);
	 });
	 
	 $("#highLi").click(function(){
		 var titleName = "超过50%千吨水百米能耗";
		 showChar(titleName,highObject,$('#char_top'),highColor);
	 });
	 
    $(".warn-time-begin").datetimepicker({
        language:  'zh-CN',
        format: 'yyyy-mm-dd',
        weekStart: 1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2, 
        minView: 2,
        forceParse: 0
    });

	$("#beginTime").datetimepicker({
	}).on("changeDate",function(ev){
		var beginTime=$("#beginTime").val();
		var endTime=$("#endTime").val();
		if(beginTime!="" && endTime!=""){
			if(beginTime>endTime){
				$("#beginTime").val('');
				showErrorMsg("开始时间不能大于结束时间!");
				return false;
			}
		}
	});

	$("#endTime").datetimepicker({
	}).on("changeDate",function(ev){
		var beginTime=$("#beginTime").val();
		var endTime=$("#endTime").val();
		if(beginTime!="" && endTime!=""){
			if(beginTime>endTime){
				$("#endTime").val('');
				showErrorMsg("结束时间不能小于开始时间!");
				return false;
			}
		}
	});
	initTime();
	getEnergyRank();
	
});

function getEnergyRank() {
	topObject = new Array();
	lastObject = new Array();
	highObject = new Array();
    LOADING.show()
	var titleName = "前十名千吨水百米能耗";
	var url = CONTEXT_PATH+"/energy/statRank"+'?'+ Math.random();
	var sendData = new Object();
	sendData.startDate =  $("#beginTime").val();
	sendData.endDate = $("#endTime").val();
	sendData.queryType = $("#type").val();
	sendData =   JSON.stringify(sendData)
    try {
    	$.ajax({
            type: 'POST',
            contentType: 'application/json',
            url: url, 
            data: sendData,
            dataType: 'JSON',
            success: function(data) {
            	window.dataChart = data
            	if(undefined != data && null != data){
            		var dataMsg = new Array();
            		var dataObject = new Object();
            		if(undefined != data.top && null != data.top){
            			titleName = "前十名千吨水百米能耗";
            			
            			dataMsg = new Array();
            			divObject = $('#char_top');
            			
            			 if(data.top.length < 1){
            				 titleName = "暂无数据";
                         }
            			
            			for(var i = 0;i < data.top.length;i++){
            				dataObject = new Object();
            				dataObject.name = data.top[i].name;
            				dataObject.y = parseInt(data.top[i].y) < 1 ? 1:parseInt(data.top[i].y);
            				dataMsg[i] = dataObject;
            			}
            			topObject = dataMsg;
            			showChar(titleName,dataMsg,divObject,topColor);
            		}else{
            			var titleName = "暂无数据";
            			showChar(titleName,dataMsg,$('#char_top'),topColor);
            		}
                    if(undefined != data.last && null != data.last){
                        titleName = "后十名千吨水百米能耗";
                        divObject = $('#char_last');

                        dataMsg = new Array();
                        divObject = $('#char_last');
                        if(data.last.length > 0){
                        	$("#lastLi").show();
                        }
                        for(var i = 0;i < data.last.length;i++){
                            dataObject = new Object();
                            dataObject.name = data.last[i].name;
                            dataObject.y = parseInt(data.last[i].y);
                            dataMsg[i] = dataObject;
                        }
                        lastObject = dataMsg; 
                        //$(this).attr('isOpen', 'true');
                        //showChar(titleName,dataMsg,divObject);
                    }

                    if(undefined != data.high && null != data.high){
                        titleName = "超过50%千吨水百米能耗";
                        divObject = $('#char_high');

                        dataMsg = new Array();
                        divObject = $('#char_high');
                        if(data.high.length > 0){
                        	$("#highLi").show();
                        }
                        for(var i = 0;i < data.high.length;i++){
                            dataObject = new Object();
                            dataObject.name = data.high[i].name;
                            dataObject.y = parseInt(data.high[i].y);
                            dataMsg[i] = dataObject;
                        }
                        highObject = dataMsg; 
                        //$(this).attr('isOpen', 'true');
                        //showChar(titleName,dataMsg,divObject);
                    }
            	}
                LOADING.hide();
            },
            error: function (data) {
            }
        });
    } catch (e) {

    }
}

$('#tabThisPage a').click(function (e) {
    e.preventDefault()
	var targetId = $(this).attr('href');
	var isOpen = $(this).attr('isOpen');
	if(targetId == "#profile" && isOpen == "false" ){
		$('#charLastCopy').html($('#char_last').html())
	}
    if(targetId == "#messages" && isOpen == "false" ){
        $('#charHighCopy').html($('#char_high').html())
	}
    $(this).tab('show')
})




function showChar(titleName,dataMsg,divObj,colorObj){
	LOADING.hide();
	var tempArr = new Array();
	var tempObj;
	if(dataMsg.length > 0){
		for(var i = 0;i<10;i++){
			tempObj = new Object();
			if(dataMsg.length - i > 0){
				tempObj.name = dataMsg[i].name;
				tempObj.y = dataMsg[i].y;
			}else{
				tempObj.name = "";
				tempObj.y = "";
			}
			tempArr[i] = tempObj;
			
		}
	}else{
		titleName = "";
	}
	divObj.highcharts({
		chart: {
				type: 'bar'
		},
		title: {
				text: titleName
		},
		xAxis: {
				type: 'category'
		},
		yAxis: {
				title: {
						text: '千吨水百米能耗(kWh/(m³·MPa))'
				},
				labels: {
                    formatter: function() {
                    	if(this.value > 1000000){
                    		return this.value / 1000000 +'百万';	
                    	}else if(this.value > 10000){
                    		return this.value / 10000 +'万';	
                    	}else if(this.value > 1000){
                    		return this.value / 1000 +'千';	
                    	}else{
                    		return this.value;
                    	}
                    }
                }
		},
		legend: {
				enabled: false
		},
		colors: colorObj,
		plotOptions: {
				series: {
						borderWidth: 0,
						dataLabels: {
								enabled: true,
								format: '{point.y} (kWh/(m³·MPa))'
						}
				}
		},
		tooltip: {
				headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
				pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y} (kWh/(m³·MPa))</b><br/>'
		},
		series: [{
				name: '千吨水百米能耗',
				colorByPoint: true,
				data: tempArr
		}],
		
    });
	
}

function initTime(){
   var myDate = new Date();
   var weekTime = 24*60*60*1000*30;
   $("#beginTime").val(formatDate(myDate.getTime() - weekTime));
   $("#endTime").val(formatDate(myDate.getTime()));
}
function formatDate(strTime) {
    var date = new Date(strTime);
    var mouth = date.getMonth()+1;
    var mouths = "";
    mouths = "" + mouth;
    if(mouth <10){
    	mouths = "0" + mouths;
    }
    var day = date.getDate();
    var days = "";
    days = "" + day;
    if(day <10){
    	days = "0" + days;
    }
    return date.getFullYear()+"-"+mouths+"-"+days;
}