var alertType = '0';
var total = 0;
var hideColumns;
var exportType="0";
var alarmInitCount = 0;
var alarmPage = "1";
var alarm_higth = 1;
var ischeck = false;
var url = CONTEXT_PATH+"/alarmStatController/alarmList?'+ Math.random()";

function qryAreaList(){
    $("#areaCode").empty();
    $("#areaCode").append("<option value = ''>全部</option>");
    jQuery.ajax( {
        type : 'POST',
        contentType : 'application/json',
        url : CONTEXT_PATH+'/device/getAllAreas?'+ Math.random(),
        dataType : 'json',
        success : function(data){
            if(undefined == data){
                return;
            }else{
                $("#areaCode").empty();
                $("#areaCode").append("<option value = ''>全部</option>");
                $.each(data, function(i, item) {

                    $("#areaCode").append("<option value = '" + item.deviceId + "'> "+item.deviceName  + "</option>");
                })
            }
        },
        error : function(data)
        {
        }
    });
}


$(function(){
	alarmInitCount = 0;
	//初始化时间
	initTime();
	//默认查询告警信息
    initBootTable(url);


    qryAreaList();
    
    //查询事件
    $("#query").click(function(){
        ischeck = true;
    	if("1" ==alarmPage){
    		alarmInitCount = 0;
        	LOADING.show();
        	$('#dataTables-example').bootstrapTable('removeAll');
        	$('#dataTables-example').bootstrapTable('refresh');
        	//initBootTable(url);
        	areaCount();
            alarmTypeCount();
    	}else{
    		countAlarmTimes();
    		alarmTypeCountDetail();
    	}
    });
    
    $("#alarmList").click(function(){
    	if("1" ==alarmPage){
    		return;
    	}
    	alarmPage = "1";
    	$('#dataTables-example').bootstrapTable('removeAll');
    	$('#dataTables-example').bootstrapTable('refresh');
    	areaCount();
        alarmTypeCount();
    });
    $("#alarmDetail").click(function(){
    	if("2" ==alarmPage){
    		return;
    	}
    	alarmPage = "2";
    	countAlarmTimes();
    	alarmTypeCountDetail();
    });
    
    // datetimepicker init
    $(".warn-time-begin, .warn-time-end").datetimepicker({
        language:  'zh-CN',
        format: 'yyyy-mm-dd',
        weekStart: 1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2, 
        minView: 2,
        forceParse: 0
    });
    $("#planStartTime").datetimepicker({
        format: 'yyyy-mm-dd hh:ii:ss',
        minuteStep:1,
        minView:1,
        language: 'zh-CN',
        pickerPosition:'bottom-right',
        autoclose:true,
        startDate: new Date()
    }).on("click",function(){
        $("#planStartTime").datetimepicker("setEndDate",$("#planEndTime").val());
    });

    $("#planEndTime").datetimepicker({
        format: 'yyyy-mm-dd hh:ii:ss',
        minuteStep:1,
        minView:'hour',
        language: 'zh-CN',
        autoclose:true
    }).on("click",function(){
        $("#planEndTime").datetimepicker("setStartDate",$("#planStartTime").val());
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

    formValidator();

    });

    function formValidator() {
        $('#workOrder').bootstrapValidator({
            message: '不能为空',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                alarmContent: {
                    validators: {
                        notEmpty: {
                            message: '工单内容不能为空'
                        }
                    }
                },
                alarmLevel: {
                    validators: {
                        notEmpty: {
                            message: '告警等级不能为空'
                        }
                    }
                }
            }
        });
    }

    function initBootTable(url){
        // 初始化无滚动条
        $("#page-wrapper").css("overflow-y", "hidden");
        $("#page-inner").css("padding-right", "25px");
   	    // table height
        var calcHeight = function() {
            var height = $(window).height() - $(".top-navbar").height() - $(".panel-heading").height() -
                $(".nav-tabs").height() - 72;
            return height;
        };
        $("#alarm-area-compare, #alarm-type-compare").height(calcHeight()/2 - 5);
        $("#alarm-compare-graph, #alarm-compare-pie").height(calcHeight());
        $(window).resize(function() {
            $('#dataTables-example').bootstrapTable('resetView', {height : calcHeight() + 55});
            $("#alarm-area-compare, #alarm-type-compare").height(calcHeight()/2 - 5);
            $("#alarm-compare-graph, #alarm-compare-pie").height(calcHeight());
        });
        window.operateEvents = {
            'click .jumpWork': function (e, value, row, index) {
                queryWhetherWorker(row.deviceId);
               /* queryAlarmWorkTemplate();
                queryMaintenanceWorkerUser();*/
                $("#alarmContent").val(row.phName+'发生了'+row.alarmInfo);
				$("#alarmTime").val(row.startDate);
				$("#processName").val(row.deviceName);
                $("#processObject").val(row.deviceId);
				$("#alarmReason").val(row.alarmReason);
				$("#alarmLevel").val(row.alarmLevel);
				$("#planContent").val(row.alarDescription);
				$("#taskSource").val("二供");

				//重置验证
                $("#workOrder").data('bootstrapValidator').destroy();
                $('#workOrder').data('bootstrapValidator',null);
                formValidator();

            }
        };

   	    $('#dataTables-example').bootstrapTable({
           url:url,
       	   cache:false,
       	   striped:true,
           // height:calcHeight() + 55,//设定高度，固定头部
           search: false,//是否搜索
           queryParamsType:'',
           queryParams:queryParams,
           pageSize:20,
           pageNumber:1,
           sidePagination:'server',
            sortName:"endDate",
            sortOrder:"desc",
           pagination: true,//是否分页
           showColumns: true,//列选择按钮
           minimumCountColumns:2,
           pageList:[20,30,50,100],
           searchOnEnterKey: false,//回车搜索
           clickToSelect:true,
           showRefresh: false,//刷新按钮
           showColumns: true,//列选择按钮
           smartDisplay:true,
           showExport:true,
           exportDataType:'all',
            exportOptions: {
                ignoreColumn: [0] //忽略某一列的索引
            },
           onLoadSuccess:function(data){
        	   successHide(1);
        	   total = data.total;
        	   if(alertType == '1'){
        		   alertType = '0';
        	   }
        	   console.log($('#dataTables-example')[0].querySelector('thead').querySelectorAll('th'))
               // let ths = $('#dataTables-example')[0].querySelector('thead').querySelectorAll('th')
               // for(let i=0;i<ths.length;i++){
               //     ths[i].setAttribute('data-sortable',true)
               // }
           },
           onLoadError:function(data){
          	   LOADING.hide();
          	   alertType = '0';
          	   $("tbody").html('<tr class="no-records-found"><td colspan="12">没有找到匹配的记录</td></tr>');
           },
           locale: "zh-CN",//中文支持
           detailView: true, //是否显示详情折叠
           columns: [{
               field: 'phName',
               title: '泵房名称',
               align: 'center',
               sortable: true
           },{
               field: 'deviceName',
               title: '电气位置',
               align: 'center',
               sortable: true
           },{
               field: 'alarmTypeRemarks',
               title: '设备位置',
               align: 'center',
               sortable: true
           }/*,{
               field: 'deviceId',
               title: '设备编号',
               align: 'center'
           }*/,{
               field: 'alarmInfo',
               title: '告警内容',
               align: 'center',
               sortable: true
           },{
               field: 'alarmTime',
               title: '告警时长(h)',
               align: 'center',
               sortable: true
           },{
               field: 'alarmLevel',
               title: '告警等级',
               align: 'center',
               //cellStyle:cellStylesales,
               sortable: true,
               formatter:  function(value,row,index) {
                    var a = "";
                    if(value == "一级") {
                        a = '<span style="background-color:#FF0000;color:#ffff">'+value+'</span>';
                    }else if(value == "二级"){
                        a = '<span style="background-color:#f46b1c;color:#ffff">'+value+'</span>';
                    }else if(value == "三级") {
                        a = '<span style="background-color:#f4e672;color:#ffff">'+value+'</span>';
                    }else if(value == "四级") {
                        a = '<span style="background-color:#dbf4be;color:#ffff">'+value+'</span>';
                    }else{
                        a = '<span style="background-color:#45d60c;color:#ffff">'+value+'</span>';
                    }
                    return a;
                }
           },{
               field: 'startDate',
               title: '开始时间',
               align: 'center',
               sortable: true
           },{
               field: 'endDate',
               title: '结束时间',
               align: 'center',
               sortable: true
           },/*{
               field: 'confirmStat',
               title: '告警确认状态',
               align: 'center'
           },*/{
               field: 'createWorkOrder',
               title: '创建工单',
               align: 'center',
               events: operateEvents,
               formatter: operateFormatter
           }],
           detailFormatter: function(index, row) {// 详情信息
               var html = [];
               html.push('<p class="detail-view">' + '操作人员' + ' : ' + toTrim(row.userName) + '</p>');
               html.push('<p class="detail-view">' + '设备编号' + ' : ' + toTrim(row.deviceId) + '</p>');
               html.push('<p class="detail-view">' + '操作原因' + ' : ' + toTrim(row.confirmReson) + '</p>');
               html.push('<p class="detail-view">' + '解决方案' + ' : ' + toTrim(row.alarDescription) + '</p>');
               return html.join('');
           }
        });

        areaCount();
        alarmTypeCount();
   }

function cellStylesales(value, row, index) {
    if(value!="" && value=="一级"){
        return {css:{background:'#f44336',color:'#000'}};
    }else if(value!="" && value=="二级"){
        return {css:{background:'#f46b1c',color:'#000'}};
    }else if(value!="" && value=="三级"){
        return {css:{background:'#f4e672',color:'#000'}};
    }else if(value!="" && value=="四级"){
        return {css:{background:'#dbf4be',color:'#000'}};
    }
}

	function operateFormatter(value, row, index) {
		    if(row.ticketId){
              //  return ['<button type="button" onclick="alertError()"  class="btn btn-warning">创建工单</button>'].join('');

                return ['<span style="    display: inline-block;padding: 6px 12px; margin-bottom: 0;border: 1px solid transparent;border-radius: 4px;font-size: 14px;" class=" btn-warning">已创建工单</span>'].join('');

            }else {
                return ['<button type="button"  class="jumpWork btn btn-primary">创建工单</button>'].join('');
            };

	}
	function alertError() {
        $('#alertErrorMessage').html('该告警已经创建工单,无法创建');
        $('#alertErrorMessage').css("color","red");
        $('#alertError').modal('show');
    }

	function toTrim(str){
		if(undefined == str || null == str){
			return "-";
		}
		return str;
	}
   function queryParams(params) {
    	if(ischeck){
            params.pageNumber = 1;
            ischeck = false;
		}
	   alertType = '1';

       var sortName="";
       if(params.sortName=='phName'){
           sortName = "DL.name";
       } else if (params.sortName=='deviceName'){
           sortName = "D.name";
       } else if (params.sortName=='alarmInfo'){
           sortName = "ATD.FUNCTION_REMARKS";
       } else if (params.sortName=='alarmTime'){
           sortName = "TIMESTAMPDIFF(SECOND,ALARM.Start_DATE,ALARM.End_Date)";
       } else if (params.sortName=='alarmLevel'){
           sortName = "alarm_level"
       } else if (params.sortName=='startDate'){
           sortName = "ALARM.START_DATE"
       } else if (params.sortName=='endDate'){
           sortName = "ALARM.END_DATE"
       }
	 //配置参数
     var keyword = jQuery.base64.encode($("#keyword").val());
     var type = $("#type").val();
     var temp = {
       pageSize: params.pageSize,   //页面大小
       pageNumber: params.pageNumber,  //页码
       keyword: keyword,
       areaCode: $("#areaCode").val(),
       pumpHouseName: $("#pumpHouseName").val(),
       beginTime: $("#beginTime").val(),
       type:type,
       endTime: $("#endTime").val(),
       exportType:exportType,
       exportMustNum:EXPORT_MUST_NUM,
       deviceName:$("#deviceName").val(),
         alarmInfo:$("#alarmInfo").val(),
         startAlarmTime:$("#startAlarmTime").val(),
         endAlarmTime:$("#endAlarmTime").val(),
         sortName:sortName,
         sortOrder:params.sortOrder
 	};
 	 exportType='0';
     return temp;
   }
    function initTime(){
	   var myDate = new Date();
	   var weekTime = 24*60*60*1000*7;
	   $("#beginTime").val(formatDate(myDate.getTime() - weekTime));
	   $("#endTime").val(formatDate(myDate.getTime()));
	   $("#planStartTime").val(myDate.Format("yyyy-MM-dd HH:mm:ss"));
        myDate.setDate(myDate.getDate() + 7)
	   $("#planEndTime").val(myDate.Format("yyyy-MM-dd HH:mm:ss"));
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

   function areaCount(){
	   var countObj =  $('#alarm-area-compare');
	   var url = CONTEXT_PATH+"/alarmStatController/statLevel?"+ Math.random();
	    $("#alermCount").ajaxSubmit( {
		    type : 'POST',
		    url : url,
		    dataType : 'json',
		    success : function(data){
		    	successHide(1);
		    	var dataMsg = new Array();
		    	for(var i=0;i<data.length;i++){
		    		dataMsg[i] = new Array();
		    		dataMsg[i][0] = data[i].deviceName +"(" + data[i].deviceId + ")";
		    		dataMsg[i][1] = parseInt(data[i].deviceId);
		    	}
		    	render(countObj,dataMsg,"告警等级对比");
		    },
		    error : function(data)
	   	    {
		    	render(countObj,[],"告警等级对比");
		    }
		  });
   }

   function countAlarmTimes(){
	   var url = CONTEXT_PATH+"/alarmStatController/statTime?"+ Math.random();
	    $("#alermCount").ajaxSubmit( {
		    type : 'POST',
		    url : url,
		    dataType : 'json',
		    success : function(data){
		    	successHide(1);
		    	var dataTime = new Array();
		    	var dataVal = new Array();
		    	for(var i=0;i<data.length;i++){
		    		dataTime[i] = data[i].deviceName;
		    		dataVal[i] = parseInt(data[i].deviceId);
		    	}
		    	countAlarmTimesQry(dataTime,dataVal);
		    },
		    error : function(data)
	   	    {
		    	countAlarmTimesQry([],[]);
		    }
		  });
   }

   function alarmTypeCount(){
	   var countObj =  $('#alarm-type-compare');
	   var url = CONTEXT_PATH+"/alarmStatController/statType?"+ Math.random();
	    $("#alermCount").ajaxSubmit( {
    	    type : 'POST',
    	    url : url,
    	    dataType : 'json',
    	    success : function(data){
    	    	successHide(1);
    	    	var dataMsg = new Array();
    	    	for(var i=0;i<data.length;i++){
    	    		dataMsg[i] = new Array();
    	    		dataMsg[i][0] = data[i].deviceName +"(" + data[i].deviceId + ")";
    	    		dataMsg[i][1] = parseInt(data[i].deviceId);
    	    	}
    	    	render(countObj,dataMsg,"告警类型对比");
		    },
		    error : function(data)
	   	    {
		    	LOADING.hide();
		    	render(countObj,[],"告警类型对比");
		    }
 		  });
   }
   function alarmTypeCountDetail(){
	   var countObj =  $('#alarm-compare-pie');
	   var url = CONTEXT_PATH+"/alarmStatController/statType?"+ Math.random();
	    $("#alermCount").ajaxSubmit( {
    	    type : 'POST',
    	    url : url,
    	    dataType : 'json',
    	    success : function(data){
    	    	successHide(1);
    	    	var dataMsg = new Array();
    	    	for(var i=0;i<data.length;i++){
    	    		dataMsg[i] = new Array();
    	    		dataMsg[i][0] = data[i].deviceName +"(" + data[i].deviceId + ")";
    	    		dataMsg[i][1] = parseInt(data[i].deviceId);
    	    	}
    	    	render(countObj,dataMsg,"告警类型对比");
		    },
		    error : function(data)
	   	    {
		    	LOADING.hide();
		    	render(countObj,[],"告警类型对比");
		    }
 		  });
   }

    function submitWorkOrder(){
        var bootstrapValidator = $("#workOrder").data('bootstrapValidator');
        //手动触发验证
        bootstrapValidator.validate();
        if(bootstrapValidator.isValid()){
            var _url = CONTEXT_PATH+"/alarmStatController/submitWorkOrder?"+ Math.random();
            $("#workOrder").ajaxSubmit( {
                type : 'POST',
                url : _url,
                dataType : 'json',
                success : function(data){
                    if(data.status == "success"){
                        $('#myWorkModal').modal('hide'); // 关闭模态框
                        $('#alertErrorMessage').html(data.message);
                        $('#alertError').modal('show');
                    }else{
                        $('#alertErrorMessage').html(data.message);
                        $('#alertError').modal('show');
                    }
                }
            });
        }

    }
    
    function refresh() {
        GLOBAL_URL_ADDRESS = 'alarmQuery.html';
        pageChange();
        $('.modal-backdrop').remove();
    }

    function queryAlarmWorkTemplate() {
        var url = CONTEXT_PATH+"/alarmStatController/queryAlarmWorkTemplate?"+ Math.random();
        jQuery.ajax({
            type : 'POST',
            contentType : 'application/json',
            url : url,
            dataType : 'json',
            success : function(data) {
                $("#workType").html("");
                $.each(data, function (i, item) {
                    jQuery("#workType").append("<option value="+ item.value+">"+ item.label+"</option>");
                });
            }
        });
    }

    function queryMaintenanceWorkerUser() {
        var url = CONTEXT_PATH+"/alarmStatController/queryMaintenanceWorkerUser?"+ Math.random();
        jQuery.ajax({
            type : 'POST',
            contentType : 'application/json',
            url : url,
            dataType : 'json',
            success : function(data) {
                $("#executor").html("");
                jQuery("#executor").append("<option>"+ '未选择-默认流程'+"</option>");
                $.each(data, function (i, item) {
                    jQuery("#executor").append("<option value="+ item.loginName+">"+ item.name+"</option>");
                });
            }
        });
    }

    function queryWhetherWorker(deviceId) {
        $('#myWorkModal').modal('show');

       /* var url = CONTEXT_PATH+"/alarmStatController/queryWhetherWorker?"+ Math.random();
        jQuery.ajax({
            type : 'POST',
            contentType : 'application/json',
            url : url,
            dataType : 'json',
            data: deviceId,
            success : function(data) {
                if(data.status == "success"){
                    $('#myWorkModal').modal('show');
                }else if(data.workNum){
                    $('#alertWorkMessage').html('该设备已有'+data.workNum+'个工单是否继续创建工单');
                    $('#alertWork').modal('show');
                }else {
                    openAlertModel("myModalAlert" , data.message);
                }
            }
        });*/
    }
   function render(countObj,dataMsg,titleName){
	   /* 大区报警对比 */
      countObj.highcharts({
           colors: ['#24CBE5', '#64E572', '#FF9655', '#058DC7', '#50B432', '#ED561B', '#DDDF00', '#FFF263', '#6AF9C4'],
           chart: {
               backgroundColor: {
                   linearGradient: { x1: 0, y1: 0, x2: 1, y2: 1 },
                   stops: [
                       [0, 'rgb(255, 255, 255)'],
                       [1, 'rgb(240, 240, 255)']
                   ]
               },
               borderWidth: 1,
               plotBackgroundColor: 'rgba(255, 255, 255, .9)',
               plotShadow: true,
               plotBorderWidth: 1
           },
           exporting: {
               enabled: false
           },
           title: {
               text: titleName,
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
               name: '报警频率',
               data: dataMsg
           }],
           credits: {
               enabled: false//不显示highCharts版权信息
           }
       });

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
	function downLoadExcel(){
		var keyword = jQuery.base64.encode($("#keyword").val());
		var url = CONTEXT_PATH+"/alarmStatController/alarmExport?type="+$("#type").val()+"&keyword="+
		keyword+"&beginTime="+$("#beginTime").val()+"&endTime="+$("#endTime").val();
		location.href = url;
	}
	function doClean() {

    }
	function successHide(size){
		alarmInitCount +=size;
		if(alarmInitCount == 3){
			LOADING.hide();
		}
	}

	/***********************************************************/
  /*设备每天告警次数对比*/
  /***********************************************************/
	function countAlarmTimesQry(dataTime,dataVal){
		$('#alarm-compare-graph').highcharts({
		      colors: ['#058DC7', '#50B432', '#ED561B', '#DDDF00', '#24CBE5', '#64E572', '#FF9655', '#FFF263', '#6AF9C4'],                                               
		      chart: {                                                                
		          type: 'spline',                                                     
		          animation: Highcharts.svg, // don't animate in old IE               
		          marginRight: 10,                                                    
		          backgroundColor: {
		              linearGradient: { x1: 0, y1: 0, x2: 1, y2: 1 },
		                  stops: [
		                      [0, 'rgb(255, 255, 255)'],
		                      [1, 'rgb(240, 240, 255)']
		                  ]
		          },
		          borderWidth: 1,
		          plotBackgroundColor: 'rgba(255, 255, 255, .9)',
		          plotShadow: true,
		          plotBorderWidth: 1                                                                
		      },                                                                      
		      title: {
		          text: "设备每天告警次数对比(次数)",
		          style: {
		              color: '#333',
		              font: 'bold 15px "Trebuchet MS", Verdana, Microsoft YaHei'
		          }
		      },                                                                  
		      xAxis: {                                                                
		          type: 'datetime',
		          title: false,                                                
		          tickPixelInterval: 150,
		          gridLineWidth: 1,
		          lineColor: '#000',
		          tickColor: '#000',
		          labels: {
		              style: {
		                  color: '#000',
		                  font: '11px Trebuchet MS, Verdana, Microsoft YaHei, sans-serif'
		              }
		          },
		          tickmarkPlacement: 'on',
		          categories: dataTime                                    
		      },                                                                      
		      yAxis: { 
		          title: false,                                                   
		          minorTickInterval: 'auto',
		          lineColor: '#000',
		          lineWidth: 1,
		          tickWidth: 1,
		          tickColor: '#000',
		          labels: {
		              style: {
		                  color: '#000',
		                  font: '11px Trebuchet MS, Verdana, Microsoft YaHei, sans-serif'
		              }
		          },                                                              
		          plotLines: [{                                                       
		              value: 0,                                                       
		              width: 1,                                                       
		              color: '#808080'                                                
		          }]                                                                  
		      },
		      tooltip: {
		          crosshairs: true,
		          shared: true,
		          pointFormat: '{series.name} : <b>{point.y}</b> 次 <br/>'
		      },
		      exporting: {
		          enabled: false
		      },                                                                                                                                                                                                                                                                                    
		      series: [{
		          name: '告警数量',
		          data: dataVal
		      }],
		      credits: {
		          enabled: false//不显示highCharts版权信息
		      } 
		  });
	}

  

  /*****************************************************/
  /* 重新渲染图表 */
  /*****************************************************/
  $("a[data-toggle='tab']").on("shown.bs.tab", function (e) {
      var chart = $(".highcharts-container").parent();
      if(chart.length > 0){
          $.each(chart, function(i, obj){
              $(obj).highcharts().reflow();
          });
      }
  });                         