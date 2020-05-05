var alertType = '0';
var total = 0;
var hideColumns;
var exportType="0";
var ischeck = false;
$(function(){
	//LOADING.show();
	var pumpHouseId = '0036584c-eddd-439e-9f02-6d9fa734bf13';
	if(undefined == pumpHouseId || "" == pumpHouseId || null == pumpHouseId){
		showErrorMsg("请选择一个设备");
		$("#pumpHouseId").val("");
	}else{
		$("#pumpHouseId").val("0036584c-eddd-439e-9f02-6d9fa734bf13");
	}
    $("#query").click(function(){
        ischeck = true;
    	$('#dataTables-example').bootstrapTable('removeAll');
    	$('#dataTables-example').bootstrapTable('refresh');
    	//initBootTable();
    	countUser();
    });
    
    // datetimepicker init
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

    //初始化时间
    initTime();
    initBootTable();


});

function callRefresh(device) {
	var pumpHouseId = device.pumpHouseId;
	$("#e_table").html("");
    $("#pumpHouseId").val(pumpHouseId);
    initBootTable();
}

function initBootTable(){
	var url = CONTEXT_PATH+"/device/arrRecord"+'?'+ Math.random();
    // 初始化无滚动条
    $("#page-wrapper").css("overflow-y", "hidden");
	// table height
    var calcHeight = function() {
        return $(window).height() - $(".top-navbar").height() - 25;
    };

    $(window).resize(function() {
        $('#dataTables-example').bootstrapTable('resetView', {height : calcHeight()});
        $("#entertimes-compare").height(calcHeight() - 74);
    });
	 
	$('#dataTables-example').bootstrapTable({
    	url:url,
    	cache:false,
    	striped:true,
        height:calcHeight(),//设定高度，固定头部
        search: false,//是否搜索
        queryParamsType:'',
        queryParams:queryParams,
        pageSize:20,
        pageNumber:1,
        sidePagination:'server',
        searchOnEnterKey: false,//回车搜索
        onLoadSuccess:function(data){
        	total = data.total;
        	if(alertType == '1'){
     		   LOADING.hide();
     		   alertType = '0';
     	   }
        },
        onLoadError:function(data){
       	   LOADING.hide();
       	   alertType = '0';
       	   $("tbody").html('<tr class="no-records-found"><td colspan="12">没有找到匹配的记录</td></tr>');
        },
        pagination: true,//是否分页
        pageList:[20,30,50,100],
        searchOnEnterKey: false,//回车搜索
        clickToSelect:true,
        showRefresh: false,//刷新按钮
        showColumns: true,//列选择按钮
        minimumCountColumns:2,
        smartDisplay:true,
        showExport:true,
        exportDataType:'all',
        locale: "zh-CN",//中文支持
        detailView: true, //是否显示详情折叠
        columns : [ {
			field : 'eventTime',
			title : '事件时间',
			align : 'center'
		}, {
			field : 'personName',
			title : '持卡人',
			align : 'center'
		}, {
			field : 'cardNum',
			title : '卡号',
			align : 'center'
		}, {
			field : 'departName',
			title : '部门',
			align : 'center'
		}, {
			field : 'doorName',
			title : '门禁名称',
			align : 'center'
		}, {
			field : 'controllerName',
			title : '控制器名称',
			align : 'center'
		} ],
		detailFormatter : function(index, row) {// 详情信息
			var html = [];
			html.push('<p class="detail-view">' + '事件时间'
					+ ' : ' + toTrim(row.eventTime) + '</p>');
			html.push('<p class="detail-view">' + '持卡人'
					+ ' : ' + toTrim(row.personName) + '</p>');
			html.push('<p class="detail-view">' + '卡号' + ' : '
					+ toTrim(row.cardNum) + '</p>');
			html.push('<p class="detail-view">' + '部门' + ' : '
					+ toTrim(row.departName) + '</p>');
			html.push('<p class="detail-view">' + '门禁名称'
					+ ' : ' + toTrim(row.doorName) + '</p>');
			html.push('<p class="detail-view">' + '控制器名称'
					+ ' : ' + toTrim(row.controllerName) + '</p>');
			return html.join('');
		}
    });
    /* 饼图高度 */
    $("#entertimes-compare").height(calcHeight() - 74);
    countUser();
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

function queryParams(params) {
    if(ischeck){
        params.pageNumber = 1;
        ischeck = false;
    }
	//LOADING.show();
    //配置参数 
	//配置参数  
    var userName = $("#userName").val();
    if(isEmp(userName)){
    	userName = "";
    }else{
    	userName = jQuery.base64.encode(userName);
    }
    var temp = {     
      pageSize: params.pageSize,   //页面大小  
      pageNumber: params.pageNumber,  //页码  
      pumpHouseId: $("#pumpHouseId").val(),  
      userId: userName,  
      beginTime: $("#beginTime").val(),  
      endTime: $("#endTime").val(),
      exportType:exportType,
      exportMustNum:EXPORT_MUST_NUM
	};
	exportType='0';  
    return temp;  
  }
function countUser(){
	$("#user").val("");
	var url = CONTEXT_PATH+"/device/arrRecord/countUser"+'?'+ Math.random();
	var user = $("#userName").val();
	if(!isEmp(user)){
		$("#user").val(jQuery.base64.encode(user));
	}
    $("#countSubmit").ajaxSubmit( { 
 	    type : 'POST',    
 	    url : url,    
 	    dataType : 'json', 
 	    success : function(data){
 	    	LOADING.hide();
 	    	var dataMsg = new Array();
 	    	for(var i=0;i<data.length;i++){
 	    		dataMsg[i] = new Array();
 	    		dataMsg[i][0] = data[i].personName +"(" + data[i].eventType +" 天)";
 	    		dataMsg[i][1] = parseInt(data[i].eventType);
 	    	}
 	    	render(dataMsg);
	    },
	    error : function(data)
   	    {
	    	LOADING.hide();
	    	render([]);
	    }
	  });
}
function render(dataMsg){
	/* 不同人员进出泵房次数对比-饼状图 */
    $('#entertimes-compare').highcharts({
        colors: ['#24CBE5', '#64E572', '#FF9655', '#058DC7', '#50B432', '#ED561B', '#DDDF00', '#FFF263', '#6AF9C4'],
        chart: {
            backgroundColor: {
                linearGradient: { x1: 0, y1: 0, x2: 1, y2: 1 },
                stops: [
                    [0, 'rgb(255, 255, 255)'],
                    [1, 'rgb(240, 240, 255)']
                ]
            },
            borderWidth: 2,
            plotBackgroundColor: 'rgba(255, 255, 255, .9)',
            plotShadow: true,
            plotBorderWidth: 1
        },
        exporting: {
            enabled: false
        },
        title: {
            text: null,
            style: {
                color: '#000',
                font: 'bold 12px "Trebuchet MS", Verdana, Microsoft YaHei'
            }
        },
        legend: {
            itemStyle: {
                font: '9pt Trebuchet MS, Verdana, Microsoft YaHei',
                color: 'black'

            },
            itemHoverStyle: {
                color: '#039'
            },
            itemHiddenStyle: {
                color: 'gray'
            }
        },
        labels: {
            style: {
                color: '#99b'
            }
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        navigation: {
            buttonOptions: {
                theme: {
                    stroke: '#CCCCCC'
                }
            }
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: false,
                    format: '<b>{point.name}</b>: {point.percentage:.1f} %'
                },
                showInLegend: true
            }
        },
        series: [{
            type: 'pie',
            name: '出勤对比',
            data: dataMsg
        }],
        credits: {
            enabled: false//不显示highCharts版权信息
        }     
    });

} 
function toTrim(str){
 	if(undefined == str || null == str){
 		return "-";
 	}
 	return str;
 }
function showAllColumns(){
	hideColumns = $('#dataTables-example').bootstrapTable('getHiddenColumns');
	for(var i=0;i<hideColumns.length;i++){
		$('#dataTables-example').bootstrapTable('showColumn',hideColumns[i].field);
	}
}

function hideColumn(){
	if(undefined == hideColumns){
		return;
	}
	for(var i=0;i<hideColumns.length;i++){
		$('#dataTables-example').bootstrapTable('hideColumn',hideColumns[i].field);
	}
}