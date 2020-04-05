var alertType = '0';
var total = 0;
var hideColumns;
var exportType="0";
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
	var url = CONTEXT_PATH+"/device/accessCtrl"+'?'+ Math.random();
    // 初始化无滚动条
    $("#page-wrapper").css("overflow-y", "hidden");
	 // table height
    var height = $(window).height() - $(".top-navbar").height() - $(".panel-heading").height();
	 
	$('#dataTables-example').bootstrapTable({
    	url:url,
    	cache:false,
    	striped:true,
        height:height - 21,//设定高度，固定头部
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
        detailFormatter: function(index, row) {// 详情信息
            var html = [];
            html.push('<p class="detail-view">' + '门禁编号' + ' : ' + toTrim(row.doorId) + '</p>');
            html.push('<p class="detail-view">' + '门禁名称' + ' : ' + toTrim(row.doorName) + '</p>');
            html.push('<p class="detail-view">' + '登录用户' + ' : ' + toTrim(row.userId) + '</p>');
            html.push('<p class="detail-view">' + '开启时间' + ' : ' + toTrim(row.beginTime) + '</p>');
            html.push('<p class="detail-view">' + '结束时间' + ' : ' + toTrim(row.endTime) + '</p>');
            html.push('<p class="detail-view">' + '泵房名称' + ' : ' + toTrim(row.pumpHouseId) + '</p>');
            html.push('<p class="detail-view">' + '备注' + ' : ' + toTrim(row.remark) + '</p>');
            return html.join('');
        }
    });
    /* 饼图高度 */
    $("#entertimes-compare").height(height - 75);
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
    return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
}

function queryParams(params) {
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
	var url = CONTEXT_PATH+"/device/accessCtrl/countUser"+'?'+ Math.random();
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
 	    		dataMsg[i][0] = data[i].doorId +"(" + data[i].remark + ")";
 	    		dataMsg[i][1] = parseInt(data[i].remark);
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
            name: '进出泵房频率',
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