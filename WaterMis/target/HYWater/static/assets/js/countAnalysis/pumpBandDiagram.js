var total = 0;
var exportType="0";
var topObject = new Array();
var categoryData = new Array();
var topColor = ['#abc93b', '#abc93b', '#abc93b', '#abc93b', '#abc93b', '#abc93b', '#abc93b', '#abc93b', '#abc93b', '#abc93b'];
$(function(){

	$("#query").click(function(){
        var objId = $("#tabThisPage").find(".active").attr("id");
        if (objId == "topLi") {
            getBandDiagram();
        }
        if (objId == "lastLi") {
            getPumpStatTime();
        }
	});

    $("#topLi").click(function() {
        $("#topLi").addClass("active");
        $("#lastLi").removeClass("active");
        $("#char_last").hide();
        $("#char_top").show();
        getBandDiagram();
    });

    $("#lastLi").click(function(){
        $("#topLi").removeClass("active");
        $("#lastLi").addClass("active");
        $("#char_top").hide();
        $("#char_last").show();
        getPumpStatTime();
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

	initTime();
	initData();

    rightMenuMsg();
});

function initData() {
    var chooseList = GLOBAL_SELECT_LIST;
    getBandDiagram(chooseList);
}

function getBandDiagram(chooseList) {
	topObject = new Array();
    LOADING.show()
    var chooseListId = [];
    chooseList = chooseList || GLOBAL_SELECT_LIST;
    chooseList.map(function (val) {
        chooseListId.push(val.deviceId);
    });
    console.log(chooseListId);
	var titleName = "泵机带图";
	var url = CONTEXT_PATH+"/pumpRunTime/qryRunTime"+'?'+ Math.random();
	var sendData = new Object();
	sendData.startDate =  $("#beginTime").val()+" 00:00:00";
	sendData.endDate = $("#beginTime").val()+" 23:59:59";
	//sendData.queryType = $("#type").val();
    sendData.queryType = "day";
    sendData.deviceIds = chooseListId;
	sendData = JSON.stringify(sendData);
    try {
    	$.ajax({
            type: 'POST',
            contentType: 'application/json',
            url: url,
            data: sendData,
            dataType: 'JSON',
            success: function(data) {
            	window.dataChart = data
                categoryData = new Array();
				var dataMsg = new Array();
				var dataObject = new Object();
				divObject = $('#char_top');

            	if(undefined != data && null != data){
                    titleName = "泵机带图"
					dataMsg = new Array();

					if(data.length < 1){
						titleName = "暂无数据";
					}
					dataMsg = data.series;
					topObject = dataMsg;
					categoryData = data.xAxis[0].name;
					showChar(titleName,categoryData,dataMsg,divObject);
				}else{
					var titleName = "暂无数据";
					showChar(titleName,categoryData,dataMsg,divObject);
				}
                LOADING.hide();
            },
            error: function (data) {
            }
        });
    } catch (e) {
    }

}

function getPumpStatTime(chooseList){
    var chooseListId = [];
    chooseList = chooseList || GLOBAL_SELECT_LIST;
    chooseList.map(function (val) {
        chooseListId.push(val.deviceId);
    });
    console.log(chooseListId);
    var url = CONTEXT_PATH+"/pumpRunTime/qryStatTime"+'?'+ Math.random();
    var sendData = new Object();
    sendData.startDate =  $("#beginTime").val()+" 00:00:00";
    sendData.endDate = $("#beginTime").val()+" 23:59:59";
    //sendData.queryType = $("#type").val();
    sendData.queryType = "day";
    sendData.deviceIds = chooseListId;
    sendData = JSON.stringify(sendData);
    try {
        $.ajax({
            type: 'POST',
            contentType: 'application/json',
            url: url,
            data: sendData,
            dataType: 'JSON',
            success: function(data) {
                var $dataStr = $("#char_last")
                var tableStr, str;
                if (data.length > 0){

                }
                $("#dataTables-example").bootstrapTable('destroy');
                $("#dataTables-example").bootstrapTable({
                    search : false,// 是否搜索
                    pagination : true,// 是否分页
                    pageList:[15,30,50],
                    pageSize : 15,
                    locale: "zh-CN",
                    striped: true,
                    //height: 500,
                    columns: [{
                        field: 'pHName',
                        title: '泵房名称'
                    }, {
                        field: 'deviceId',
                        title: '设备编号'
                    }, {
                        field: 'pumpNo',
                        title: '水泵编号'
                    }, {
                        field: 'status',
                        title: '运行状态'
                    },{
                        field: 'time',
                        title: '持续时间'
                    }],
                    data: data,
                });

            },
            error: function (data) {
            }
        });
    } catch (e) {
    }
}

function showChar(titleName, categoryData, dataMsg, divObj){
	LOADING.hide();
	divObj.highcharts({
		chart: {
			type: 'bar',
            events: {
                beforePrint: function () {
                    this.oldhasUserSize = this.hasUserSize;
                    this.resetParams = [this.chartWidth, this.chartHeight, false];
                    this.setSize(1000, 600, false);
                },
                afterPrint: function () {
                    this.setSize.apply(this, this.resetParams);
                    this.hasUserSize = this.oldhasUserSize;
                }
            }
		},
		title: {
			text: titleName
		},
        subtitle: {
		    useHTML: true,
            text: '<div class="color-div bgcolorVar"></div><div style="float: left">变频运行</div>' +
                '<div class="color-div bgcolorHigh"></div><div style="float: left">工频运行</div>' +
                '<div class="color-div bgcolorStop"></div><div style="float: left">停止</div>'
        },
		xAxis: {
			type: 'category',
            categories: categoryData,
		},
		yAxis: {
            lineWidth: 1,
			title: {
				text: ''
			},
			min: 0,
            crosshair: true, //准线
            tickInterval: 12, //5分钟为1单位，12即一个小时
            //minTickInterval: 60 //最小刻度范围
			max: 288, //288*5=1440 即24小时
            labels: {
                formatter: function () {
                    return this.value/12+'时';
                }
            }
		},
		legend: {
			enabled: false
		},
		plotOptions: {
            bar: {
                stacking: 'normal',//normal
                borderWidth: 0
            }
		},
		tooltip: {
		    useHTML: true,
            formatter: function () {
            	var time = -(this.series.name.split(' ')[1] - 288);
            	var hours = parseInt(time/12);
            	var minute = time%12;
                var color = this.point.color;
                var str = "<div class='color-div' style='background-color: " + color + "'></div>" +
                        "<div style='float: left'>" + hours + '时' + minute*5 + '-' + (minute*5+5) + '分' + "</div>";
				return str;
            }
		},
		series: dataMsg,
        exporting: {
            buttons: {
                contextButton: {
                    menuItems: [
                        {
                            textKey:"printChart",
                            onclick:function(){
                                this.print()
                            }
                        },
                        {separator: true},
                        {
                            textKey:"downloadPNG",
                            onclick:function() {
                                this.exportChart()
                            }
                        },
                        {
                            textKey:"downloadJPEG",
                            onclick:function(){
                                this.exportChart({type:"image/jpeg"})
                            }
                        },
                        {
                            textKey:"downloadPDF",
                            onclick:function(){
                                this.exportChart({type:"application/pdf"})
                            }
                        },
                        {
                            textKey:"downloadSVG",
                            onclick:function(){
                                this.exportChart({type:"image/svg+xml"})
                            }
                        }
                    ]
                }
            },
            // scale: 2,//默认
            sourceWidth: 900
        },
        credits: {
            enabled: false//不显示highCharts版权信息
        }
    });
}

function initTime(){
   var myDate = new Date();
   var dayTime = 24*60*60*1000;//一天
   // $("#beginTime").val(formatDate(myDate.getTime() - dayTime));
   $("#beginTime").val(formatDate(myDate.getTime()));
   $("#endTime").val();
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

/**
 * 根据选择的设备改变实时渲染
 */
function refreshDeviceCheck(obj){
    var objId = $("#tabThisPage").find(".active").attr("id");
    if (objId == "topLi") {
        getBandDiagram(obj);
    }
    if (objId == "lastLi") {
        getPumpStatTime(obj);
    }
}