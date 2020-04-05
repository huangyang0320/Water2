var columnNameDate;
var hideColumns;
var exportType="0";
var defaultDeviceId;
var rowData = new Array();
var columnCode = new Array();
var total=0;
var allCodeNames = ['泵前压力(MPa)','泵后压力(MPa)','水箱液位(M)','耗电量(kWh)','瞬时流量(m³/h)','累积流量(m³)','电压Ua(V)','电压Ub(V)','电压Uc(V)','电流Ia(A)','电流Ib(A)','电流Ic(A)','1#主泵运行(运行|停止)','1#主变频频率(Hz)','1#主泵故障(次)','1#主泵工频(运行|停止)','1#主泵休息(次)','2#主泵运行(运行|停止)','2#主变频频率(Hz)','2#主泵故障(次)','2#主泵工频(运行|停止)','2#主泵休息(次)','无水故障(次)','高水故障(次)','地面积水报警(次)','相序故障(次)','超压故障(次)','门禁报警(次)'];
var detailArray = ['泵前压力(MPa)','泵后压力(MPa)','水箱液位(M)','累积流量(m³)','电压Ua(V)','电压Ub(V)','电压Uc(V)','电流Ia(A)','电流Ib(A)','电流Ic(A)','1#主泵运行(运行|停止)','1#主变频频率(Hz)','1#主泵故障(次)','1#主泵工频(运行|停止)','1#主泵休息(次)','2#主泵运行(运行|停止)','2#主变频频率(Hz)','2#主泵故障(次)','2#主泵工频(运行|停止)','2#主泵休息(次)','无水故障(次)','高水故障(次)','地面积水报警(次)','相序故障(次)','超压故障(次)','门禁报警(次)'];
var countTag = ["报警","故障","休息","工频","运行"];
var loadTag = '0';
var operType = '0';
var columnsBody = new Array();
var detailSearhReg = [/前压力/,/后压力/,/水箱/,/累积流量1/,/电压Ua|UAB/,/电压Ub|UBC/,/电压Uc|UAC/,/Ia/,/Ib/,/Ic/,/1#泵运行/,/1.*频率/,/1#泵故障/,/1.*泵工频/,/1.*泵休息/,/2#泵运行/,/2.*频率/,/2#泵故障/,/2.*泵工频/,/2.*泵休息/,/无水/,/高水/,/地面积水报警/,/相序故障/,/超压故障/,/门禁报警/];
var allTitleArrayReg = [/前压力/,/后压力/,/水箱/,/耗电量/,/瞬时流量1/,/累积流量1/,/电压Ua|UAB/,/电压Ub|UBC/,/电压Uc|UAC/,/Ia/,/Ib/,/Ic/,/1#泵运行/,/1.*频率/,/1#泵故障/,/1.*泵工频/,/1.*泵休息/,/2#泵运行/,/2.*频率/,/2#泵故障/,/2.*泵工频/,/2.*泵休息/,/无水/,/高水/,/地面积水报警/,/相序故障/,/超压故障/,/门禁报警/];
$(function() {
	var deviceId = GLOBAL_SELECT_DEVICE.deviceId;
	var projectId = GLOBAL_SELECT_DEVICE.projectId;



	// 初始化无滚动条
    $("#page-wrapper").css("overflow-y", "hidden");

	// 初始化加载默认参数
	initDefaultValues(projectId, deviceId);

	// 初始化时间控件
	initTime();
	$("#project").change(function() {
		if (!isEmp($("#project").val())) {
			qryDeviceList($("#project").val(), "");
		} else {
			LOADING.hide();
			showErrorMsg("请选择一个设备");
			$("#device").empty();
			$("#device").append("<option value = ''>==请选择设备==</option>");
		}
	});
	$("#dataType").change(function() {
		var qryType = $("#dataType").val();
		if("ho" == qryType){
			$("#endTime").val($("#beginTime").val());
		}
		if("da" == qryType){
			var beginTime = $("#beginTime").val();
			var endTime = $("#endTime").val();
			var beginTimes = beginTime.split("-");
			var endTimes = endTime.split("-");
			if(beginTimes[1] != endTimes[1]){
				var day = new Date(beginTimes[0],beginTimes[1],0);
				var countDays = day.getDate();
				$("#endTime").val(beginTimes[0] + '-' + beginTimes[1] + '-' + countDays);
			}
		}
		
	});
	$("#beginTime").change(function() {
		var qryType = $("#dataType").val();
		if("ho" == qryType){
			$("#endTime").val($("#beginTime").val());
		}
		if("da" == qryType){
			var beginTime = $("#beginTime").val();
			var endTime = $("#endTime").val();
			var beginTimes = beginTime.split("-");
			var endTimes = endTime.split("-");
			if(beginTimes[1] != endTimes[1]){
				var day = new Date(beginTimes[0],beginTimes[1],0);
				var countDays = day.getDate();
				$("#endTime").val(beginTimes[0] + '-' + beginTimes[1] + '-' + countDays);
			}
		}
	});
	$("#endTime").change(function() {;
		var qryType = $("#dataType").val();
		if("ho" == qryType){
			$("#beginTime").val($("#endTime").val());
		}
		if("da" == qryType){
			var beginTime = $("#beginTime").val();
			var endTime = $("#endTime").val();
			var beginTimes = beginTime.split("-");
			var endTimes = endTime.split("-");
			if(beginTimes[1] != endTimes[1]){
				$("#beginTime").val(endTimes[0] + '-' + endTimes[1] + '-' + 1);
			}
		}
	});

	// 查询事件
	$("#query").click(function() {
		LOADING.show();
		if (isEmp($("#device").val())) {
			LOADING.hide();
			showErrorMsg("请选择一个设备");
			return;
		}
		//$('#dataTables-example').bootstrapTable('destroy');
		columnsBody = null;
		initShowBody('');
		//$('#dataTables-example').bootstrapTable('refresh');
	});
	// datetimepicker init
	$(".warn-time-begin, .warn-time-end").datetimepicker({
		language : 'zh-CN',
		format : 'yyyy-mm-dd',
		weekStart : 1,
		autoclose : 1,
		todayHighlight : 1,
		startView : 2,
		minView : 2,
		forceParse : 0
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

	defaultDeviceId = deviceId;
	initShowBody(deviceId);
	LOADING.hide();
});

function initBootTable() {
	$('#dataTables-example').bootstrapTable('destroy');
	var url = CONTEXT_PATH+"/device/sensorHistory" + "?" + Math.random();

	$("#page-inner").css("padding-right", "25px");
	// table height
	var calcHeight = function() {
		var height = $(window).height() - $(".top-navbar").height() - $(".panel-heading").height();
		return height;
	};
	$(window).resize(function() {
		$('#dataTables-example').bootstrapTable('resetView', {height : calcHeight()+17});
	});

	$('#dataTables-example').bootstrapTable(
	{
		url : url,
		cache : false,
		striped : true,
		height : calcHeight()+17,// 设定高度，固定头部
		search : false,// 是否搜索
		queryParamsType : '',
		queryParams : queryParams,
		pageSize : 20,
		pageNumber : 1,
		sidePagination : 'server',
		pagination : true,// 是否分页
		pageList:[20,30,50,100],
		searchOnEnterKey : false,// 回车搜索
		clickToSelect : true,
		showRefresh : false,// 刷新按钮
		showExport:true,
		exportDataType:'all',
		onPostHeader : function(data) {
			if (loadTag == '1') {
				loadTag = '0';
			}
		},
		onLoadSuccess:function(data){
			LOADING.hide();
			total = data.total;
			loadTag = '1';
        },
        onLoadError:function(data){
        	total=0;
       	   LOADING.hide();
        },
		showColumns : true,// 列选择按钮
		minimumCountColumns : 2,
		smartDisplay : true,
		locale : "zh-CN",// 中文支持
		columns:columnsBody,
		detailView : true, // 是否显示详情折叠
		detailFormatter : function(index, row) {// 详情信息
			
			var html = [];
			var textMsg , i , size;
			var divHtml;
			$.each(columnNameDate,function(i, item) {
				var columnValue = row[item];
				if(undefined == columnValue){
					columnValue = '-';
				}
				var filedMsg = item;
				if(/压力/.test(filedMsg)){
					filedMsg = filedMsg +"(MPa)";
				}else if(/水箱|水位/.test(filedMsg)){
					filedMsg = filedMsg +"(M)";
				}else if(/累积流量|累计流量/.test(filedMsg)){
					filedMsg = filedMsg +"(m³)";
				}else if(/耗电量/.test(filedMsg)){
					filedMsg = filedMsg +"(kWh)";
				}else if(/瞬时流量/.test(filedMsg)){
					filedMsg = filedMsg +"(m³/h)";
				}else if(/电压Ua|UAB|电压Ub|UBC|电压Uc|UAC/.test(filedMsg)){
					filedMsg = filedMsg +"(V)";
				}else if(/Ia|Ib|Ic/.test(filedMsg)){
					filedMsg = filedMsg +"(A)";
				}else if(/频频/.test(filedMsg)){
					filedMsg = filedMsg +"(Hz)";
				}else if(/故障|报警|休息|运行|远程控制电动阀/.test(filedMsg)){
					filedMsg = filedMsg +"(次)";
				}else if(/远程控制电动阀/.test(filedMsg)){
					filedMsg = filedMsg +"(总)";
				}
				if(i < 10){
					if(i ==0){
						divHtml = "<div class='table-detail'><table>";
						divHtml = divHtml +"<tr>"+
		                "<td>"+filedMsg+"</td>"+
		                "<td>"+columnValue+"</td>"+
		                "</tr>";
					}else if(i ==9){
						divHtml = divHtml +"<tr>"+
		                "<td>"+filedMsg+"</td>"+
		                "<td>"+columnValue+"</td>"+
		                "</tr>";
						divHtml = divHtml + "</table></div>";
						html.push(divHtml);
						divHtml="1";
					}else{
						divHtml = divHtml +"<tr>"+
		                "<td>"+filedMsg+"</td>"+
		                "<td>"+columnValue+"</td>"+
		                "</tr>";
					}
					
				}else if(i<20){
					if(i ==10){
						divHtml = "<div class='table-detail'><table>";
						divHtml = divHtml +"<tr>"+
						"<td>"+filedMsg+"</td>"+
		                "<td>"+columnValue+"</td>"+
		                "</tr>";
					}else if(i ==19){
						divHtml = divHtml +"<tr>"+
						"<td>"+filedMsg+"</td>"+
		                "<td>"+columnValue+"</td>"+
		                "</tr>";
						divHtml = divHtml + "</table></div>";
						html.push(divHtml);
						divHtml="1";
					}else{
						divHtml = divHtml +"<tr>"+
						"<td>"+filedMsg+"</td>"+
		                "<td>"+columnValue+"</td>"+
		                "</tr>";
					}
					
				}else if(i<30){
					if(i ==20){
						divHtml = "<div class='table-detail'><table>";
						divHtml = divHtml +"<tr>"+
						"<td>"+filedMsg+"</td>"+
		                "<td>"+columnValue+"</td>"+
		                "</tr>";
					}else if(i ==29){
						divHtml = divHtml +"<tr>"+
						"<td>"+filedMsg+"</td>"+
		                "<td>"+columnValue+"</td>"+
		                "</tr>";
						divHtml = divHtml + "</table></div>";
						html.push(divHtml);
						divHtml="1";
					}else{
						divHtml = divHtml +"<tr>"+
						"<td>"+filedMsg+"</td>"+
		                "<td>"+columnValue+"</td>"+
		                "</tr>";
					}
				}
					
				
			});
			if(divHtml != '1'){
				divHtml = divHtml + "</table></div>";
				html.push(divHtml);
				divHtml="";
			}	
			return html.join('');
		}
	});
}

///计算报警、运行、停止、休息等统计
function countSum(titleName,value){
	
	if(isEmp(value)){
		value = '';
		count = '';
	}else{
		count = value.split("|")[1];
		value = value.split("|")[0];
	}
	for(var i=0;i<countTag.length;i++){
		if(titleName.indexOf(countTag[i])>-1){
			if(isEmp(value) || isEmp(count)){
				if(i<3){
					return '-';
				}else{
					return '-|-';
				}
				
			}else{
				if(i<3){
					return parseInt(parseFloat(value)*parseFloat(count));
				}else{
					return parseInt(parseFloat(value)*parseFloat(count)) + '|' + parseInt((1 - parseFloat(value))*parseFloat(count));
				}
			}
		}
	}
	if(isEmp(value)){
		return "-";
	}else{
		return value;
	}
}

//初始化加载详细信息
function initDetailArray(){
	for(var i =0;i<detailArray.length;i++){
		$.each(columnNameDate, function(index, item) {
			
			if(detailSearhReg[i].test(item.tagName)){
				rowData[i] = [item.deviceId,item.eu];
			}
		});
	}
}
//组装查询条件
function queryParams(params) {
	LOADING.show();
	var deviceId = $("#device").val();
	if (isEmp(deviceId) || operType == '1') {
		deviceId = defaultDeviceId;
		operType='0';
	}
	var temp = {
		pageSize : params.pageSize, // 页面大小
		pageNumber : params.pageNumber, // 页码
		deviceId : deviceId,
		type : $("#dataType").val(),
		beginTime : $("#beginTime").val(),
		endTime : $("#endTime").val(),
		exportType:exportType,
	    exportMustNum:EXPORT_MUST_NUM
	};
	exportType='0';
	return temp;
}
//查询项目列表
function qryProjectList(projectId, deviceId) {
	$("#project").empty();
	$("#project").append("<option value = ''>==请选择项目==</option>");
	jQuery.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : CONTEXT_PATH+'/device/getProjectByRole' + "?" + Math.random(),
		dataType : 'json',
		success : function(data) {
			if (undefined == data) {
				return;
			} else {
				$.each(data, function(i, item) {

					$("#project").append("<option value = '" + item.projectId + "'> "+ item.projectName + "</option>");
				})
				if (!isEmp(projectId)) {
					$("#project").val(projectId);
					qryDeviceList(projectId, deviceId);
				} else {
					$("#device").empty();
					$("#device").append("<option value = ''>==请选择设备==</option>");
				}
			}
		},
		error : function(data) {

		}
	});
}
//查询设备列表
function qryDeviceList(projectId, deviceId) {
	$("#device").empty();
	$("#device").append("<option value = ''>==请选择设备==</option>");
	$("#device").val("");
	jQuery.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : CONTEXT_PATH+'/device/getDevicesByProjectId/' + projectId + "?"+ Math.random(),
		dataType : 'json',
		success : function(data) {
			if (undefined == data) {
				return;
			} else {
				$.each(data, function(i, item) {
					$("#device").append("<option value = '" + item.deviceId + "'> "+ item.deviceName + "</option>");
					if(i==0){
                        $("#deviceDefault").val(item.deviceId);
                        deviceId=$("#deviceDefault").val();
					}
				});
				if (!isEmp(deviceId)) {
					$("#device").val(deviceId);
				}
			}
            if (isEmp(projectId) || isEmp(deviceId)) {
                LOADING.hide();
                showErrorMsg("请选择一个设备");
                return;
            }
		},
		error : function(data) {
		}
	});
}
//初始化加载时间
function initTime() {
	var myDate = new Date();
	var weekTime = 24 * 60 * 60 * 1000 * 5;
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
function isEmp(str) {

	if (undefined == str || null == str || "" == str) {
		return true;
	} else {
		return false;
	}
}
//初始化body内容
function initShowBody(deviceId) {
	if (isEmp(deviceId)) {
		deviceId = $("#device").val();
	}
	var dataType = $("#dataType").val();
	var beginTime = $("#beginTime").val();
	var tableName = "service_values_today";
	var valuesTable = beginTime.split('-');
	var mouthMsg = valuesTable[1].length>1?valuesTable[1]:("0"+valuesTable[1]);
	var dayMsg = valuesTable[2].length>1?valuesTable[2]:("0"+valuesTable[2]);
	if('ho' == dataType){
		tableName = "service_values_hour_" + valuesTable[0] + mouthMsg + dayMsg;
	}else if('da' == dataType){
		tableName = "service_values_day_" + valuesTable[0] + mouthMsg;
    }else if('mo' == dataType){
    	tableName = "service_values_month";
	}else if('ye' == dataType){
		tableName = "service_values_year";
	}else{
		
	}
	jQuery.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : CONTEXT_PATH+'/device/getDeviceCodeById/' + deviceId +'/'+tableName + "?"+ Math.random(),
		dataType : 'json',
		success : function(data) {
			if (undefined == data) {
				return;
			} else {
				columnNameDate = data;
				var index=0;
				columnsBody = new Array();
				
				$.each(columnNameDate,function(i, item) {
					if(i == 0){
						columnsBody[index] = {field: 'createDate',title:"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+'记录时间'+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;",align: 'center'};
					}
					//电压特殊处理
					var filedMsg = item;
					if(/压力/.test(filedMsg)){
						filedMsg = filedMsg +"(MPa)";
					}else if(/水箱|水位/.test(filedMsg)){
						filedMsg = filedMsg +"(M)";
					}else if(/累积流量|累计流量/.test(filedMsg)){
						filedMsg = filedMsg +"(m³)";
					}else if(/耗电量/.test(filedMsg)){
						filedMsg = filedMsg +"(kWh)";
					}else if(/瞬时流量/.test(filedMsg)){
						filedMsg = filedMsg +"(m³/h)";
					}else if(/电压Ua|UAB|电压Ub|UBC|电压Uc|UAC/.test(filedMsg)){
						filedMsg = filedMsg +"(V)";
					}else if(/Ia|Ib|Ic/.test(filedMsg)){
						filedMsg = filedMsg +"(A)";
					}else if(/频频/.test(filedMsg)){
						filedMsg = filedMsg +"(Hz)";
					}else if(/故障|报警|休息|运行|远程控制电动阀/.test(filedMsg)){
						filedMsg = filedMsg +"(次)";
					}else if(/远程控制电动阀/.test(filedMsg)){
						filedMsg = filedMsg +"(总)";
					}
				
					if(i < 8){
						columnsBody[i+1] = {field:item,title:filedMsg,align: 'center'};
					}else{
						columnsBody[i+1] = {field:item,title:filedMsg,align: 'center'};
						columnsBody[i+1].visible=false;
					}
				});
				initBootTable();
			}
		},
		error : function(data) {
		}
	});
}
function toTrim(str) {
	if (undefined == str || null == str) {
		return "-";
	}
	return str;
}
function initDefaultValues(projectId, deviceId) {
	// 初始化项目列表
	qryProjectList(projectId, deviceId);
}
function callRefresh(device) {
	operType = '1';
	var deviceId = device.deviceId;
	var projectId = device.projectInfo.projectId;
	initDefaultValues(projectId, deviceId);
	defaultDeviceId = deviceId;
	initShowBody(deviceId);
}
function modBootMsg() {
	var allTableData = $('#dataTables-example').bootstrapTable('getData');
	for (i = 0; i < allTableData.length; i++) {
		var rowObj = allTableData[i];
		rowObj.status = "否";
		$('#dataTables-example').bootstrapTable('updateRow', {
			index : i,
			row : rowObj
		});
	}
}
function formatterNor(value,row,index){
	if(isEmp(value)){
		return '-';
	}else{
	    return value.split("|")[0];	
	}
	
}
function formatterRest(value,row,index){
	if(isEmp(value)){
		return '-';
	}else{
		return (1-parseInt(parseFloat(value.split("|")[0]))*parseFloat(value.split("|")[1]));
	}
}
function formatterSum(value,row,index){
	if(isEmp(value)){
		return '-';
	}else{
		return parseInt(parseFloat(value.split("|")[0])*parseFloat(value.split("|")[1]));
	}
}
function formatterCom(value,row,index){
	if(isEmp(value)){
		return '-|-';
	}else{
		return parseInt(parseFloat(value.split("|")[0])*parseFloat(value.split("|")[1])) +'|' +parseInt(parseFloat(1-value.split("|")[0])*parseFloat(value.split("|")[1]));
	}
	
}
//显示所有的列，用于导出所有的别
function showAllColumns(){
	hideColumns = $('#dataTables-example').bootstrapTable('getHiddenColumns');
	for(var i=0;i<hideColumns.length;i++){
		$('#dataTables-example').bootstrapTable('showColumn',hideColumns[i].field);
	}
}

//隐藏为显示的列
function hideColumn(){
	
}