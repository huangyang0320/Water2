var alertType = '0';
var total = 0;
var hideColumns;
var exportType="0";
var alarmInitCount = 0;
var alarmPage = "1";
var alarm_higth = 1;
var ischeck = false;
var url = CONTEXT_PATH+"/alarmStatController/alarmList?"+ Math.random();

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
    // 告警信息列表
    $("#alarmList").click(function(){
        $('#exportBtn')[0].style.display = 'inline-block'
    	if("1" ==alarmPage){
    		return;
    	}
    	alarmPage = "1";
        initBootTable(url);
    	// $('#dataTables-example').bootstrapTable('removeAll');
    	// $('#dataTables-example').bootstrapTable('refresh');
    	areaCount();  // 告警等级对比
        // alarmTypeCount(); // 告警类型对比
    });
    // 告警数统计
    $("#alarmDetail").click(function(){
        $('#exportBtn')[0].style.display = 'none'
    	if("2" ==alarmPage){
    		return;
    	}
    	alarmPage = "2";
    	countAlarmTimes();  /*设备每天告警次数对比*/
    	alarmTypeCountDetail();  // 告警类型对比
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
    // 自定义按钮导出数据
    function exportData(){
        $('#dataTables-example').tableExport({
            type: 'excel',
            exportDataType: "all",
            ignoreColumn: [0],//忽略某一列的索引
            fileName: '告警信息列表' + moment().format('YYYY-MM-DD'),//下载文件名称
            onCellHtmlData: function (cell, row, col, data){//处理导出内容,自定义某一行、某一列、某个单元格的内容
                console.info(data);
                return data;
            },
        });
    }
    function initBootTable(url){
        $("#page-wrapper").css("overflow-y", "hidden");
        $("#page-inner").css("padding-right", "25px");
        // table height
        var calcHeight = function() {
            var height = $(window).height() - $(".top-navbar").height() - $(".panel-heading").height() -
                $(".nav-tabs").height() - 72;
            console.log(height)
            return height;
        };

        $(window).resize(function() {
            $('#dataTables-example').bootstrapTable('resetView', {height : calcHeight() + 55});

        });


            function operateFormatter(value, row, index) {
                if(!row.ticketId){
                    return ['<button type="button" id="createWorkOrder" class="btn btn-info">创建工单</button>'].join('');
                }else{
                    return ['<button type="button" id="showWorkOrder" class="btn btn-warning">查看工单</button>'].join('');
                }

            }
            window.operateEvents = {
                'click #createWorkOrder': function (e, value, row, index) {
                    if(row.ticketId){
                        alertError(row)
                    }else{
                        createWorkOrder1(row)
                    }
                },
                 'click #showWorkOrder': function (e, value, row, index) {
                     showWorkOrder1(row)
                 }
            };




   	    $('#dataTables-example').bootstrapTable({
           url:url,
       	   cache:false,
       	   striped:true,
           height:calcHeight() + 55,//设定高度，固定头部
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
           smartDisplay:true,
            // toolbar: "#toolbar",//显示工具栏
            // showExport: true,//工具栏上显示导出按钮
            // exportTypes: ['json', 'xml', 'png', 'csv', 'txt', 'sql', 'doc', 'excel', 'xlsx', 'pdf'],//导出格式
            // exportOptions: {//导出设置
            //     fileName: 'Tablexxx',//下载文件名称
            // },
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
               sortable: true,
               cellStyle: formatTableUnit,
               formatter: paramsMatter,
           },{
               field: 'deviceName',
               title: '电气位置',
               align: 'center',
               sortable: true,
               cellStyle: formatTableUnit,
               formatter: paramsMatter,
           },{
               field: 'alarmTypeRemarks',
               title: '设备位置',
               align: 'center',
               sortable: true,
               cellStyle: formatTableUnit,
               formatter: paramsMatter,
           }/*,{
               field: 'deviceId',
               title: '设备编号',
               align: 'center'
           }*/,{
               field: 'alarmInfo',
               title: '告警内容',
               align: 'center',
               sortable: true,
               cellStyle: formatTableUnit,
               formatter: paramsMatter,
           },{
               field: 'alarmTime',
               title: '告警时长(h)',
               align: 'center',
               sortable: true,
               cellStyle: formatTableUnit,
               formatter: paramsMatter,
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
               sortable: true,
               cellStyle: formatTableUnit,
               formatter: paramsMatter,
           },{
               field: 'endDate',
               title: '结束时间',
               align: 'center',
               sortable: true,
               cellStyle: formatTableUnit,
               formatter: paramsMatter,
           },/*{
               field: 'confirmStat',
               title: '告警确认状态',
               align: 'center'
           },*/{
               field: 'createWorkOrder',
               title: '创建工单',
               align: 'center',
               events: operateEvents,//给按钮注册事件
               formatter: operateFormatter
           }],
           detailFormatter: function(index, row) {// 详情信息
               var html = [];
               html.push('<p class="detail-view">' + '操作人员' + ' : ' + toTrim(row.userName) + '</p>');
               html.push('<p class="detail-view">' + '告警状态' + ' : ' + toTrim(row.confirmStat) + '</p>');
               html.push('<p class="detail-view">' + '操作时间' + ' : ' + toTrim(row.confirmDate) + '</p>');
               html.push('<p class="detail-view">' + '设备编号' + ' : ' + toTrim(row.deviceId) + '</p>');
               html.push('<p class="detail-view">' + '操作原因' + ' : ' + toTrim(row.confirmReson) + '</p>');
               html.push('<p class="detail-view">' + '解决方案' + ' : ' + toTrim(row.alarDescription) + '</p>');
               return html.join('');
           }
        });
        // 表格高度自适应
        // $('#dataTables-example').bootstrapTable('resetView',{height:"auto"});

        //表格超出宽度鼠标悬停显示td内容
        function paramsMatter(value, row, index) {
            var span = document.createElement("span");
            span.setAttribute("title", value);
            span.innerHTML = value;
            return span.outerHTML;
        }
        //td宽度以及内容超过宽度隐藏
        function formatTableUnit(value, row, index) {
            return {
                css: {
                    "white-space": "nowrap",
                    "text-overflow": "ellipsis",
                    "overflow": "hidden",
                    "max-width": "60px"
                }
            }
        }
//confirmStat confirmDate
        areaCount();
        alarmTypeCount();
        setTicketId("1");
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

function setTicketId(mark){
    $.ajax({
        type:"POST",
        url: CONTEXT_PATH+'/ticket/getTicketId',
        async: false,
        datatype:"JSON",
        data:{ticketType:mark},
        success: function (res) {
            $("#ticketId").val(res);
        }
    })
}

function queryMaintenanceWorkerDept(deptId) {
    //var url = CONTEXT_PATH+"/alarmStatController/queryMaintenanceWorkerUser?"+ Math.random();
    var url =CONTEXT_PATH+"/ticket/getDept?"+ Math.random();
    jQuery.ajax({
        type : 'POST',
        contentType : 'application/json',
        url : url,
        dataType : 'json',
        success : function(data) {
            $("#deptId").html("");
            $.each(data, function (i, item) {
                jQuery("#deptId").append("<option value="+ item.deptId+" mgName="+item.mgName+">"+ item.deptName+"</option>");
                if(i==0){
                    $("#mgName").val(item.mgName);
                }
            });
            if(deptId!=null&&deptId!=''&&deptId!=undefined){
                $("#deptId").val(deptId);
                $("#mgName").val(jQuery("#deptId option:selected").attr("mgName"));
            }
        }
    });
}
function myModalWorkOrder(row,flag) {

    //queryAlarmWorkTemplate();
    queryMaintenanceWorkerDept(row.deptId);
    $("#alarmContent").val(row.phName+"-"+row.alarmTypeRemarks+'发生了'+row.alarmInfo);
    $("#alarmTime").val(row.startDate);
    $("#phName").val(row.phName);
    $("#phId").val(row.phId);
    $("#address").val(row.address);
    if(row.ticketId!=null&&row.ticketId!=''){
        $("#ticketId").val(row.ticketId);
    }
    $("#processName").val(row.deviceName);
    $("#deviceId").val(row.deviceId);
    $("#alarmReason").val(row.alarmReason);
    $("#alarmLevel").val(row.alarmLevel);
    $("#planContent").val(row.alarDescription);
    $("#taskSource").val("二供");

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

    var myDate = new Date();
    $("#planStartTime").val(myDate.Format("yyyy-MM-dd HH:mm:ss"));
    myDate.setDate(myDate.getDate() + 7)
    $("#planEndTime").val(myDate.Format("yyyy-MM-dd HH:mm:ss"));

    formValidator();
    //重置验证
    $("#workOrder").data('bootstrapValidator').destroy();
    $('#workOrder').data('bootstrapValidator',null);
    formValidator();

    //查看 0  创建 1
    if(flag=="1"){
        disShow();
    }else if(flag=="0"){
        disHide();
    }
}
function disShow(){

    $("#alarmLevel").removeAttr("readonly");
    $("#planStartTime").removeAttr("disabled");
    $("#planEndTime").removeAttr("disabled");
    $("#alarmReason").removeAttr("disabled");
    $("#planContent").removeAttr("disabled");
    $("#deptId").removeAttr("disabled");
}
function disHide(){
    $("#alarmLevel").attr("readonly","true");
    $("#planStartTime").attr("disabled","true");
    $("#planEndTime").attr("disabled","true");
    $("#alarmReason").attr("disabled","true");
    $("#planContent").attr("disabled","true");
    $("#deptId").attr("disabled","true");
}

	function alertError() {
        $('#alertErrorMessage').html('该告警已经创建工单,无法创建');
        $('#alertErrorMessage').css("color","red");
        $('#alertError').modal('show');
    }

    function hideCreateBtn() {
        $("#createBtn").hide();
    }
    function showCreateBtn() {
        $("#createBtn").show();
    }
    function createWorkOrder1(self) {
        //创建按钮显示
        showCreateBtn();
        //打开创建工单页面
        $('#myWorkModal1').modal('show');
        //工单页面赋值
        myModalWorkOrder(self,"1");
        //给工单编号赋值
        setTicketId("1");
    }

    function showWorkOrder1(self) {
        //创建按钮隐藏
        hideCreateBtn();
        //打开创建工单页面
        $('#myWorkModal1').modal('show');
        //工单页面赋值
        myModalWorkOrder(self,"0");

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
           // var _url = CONTEXT_PATH+"/alarmStatController/submitWorkOrder?"+ Math.random();
            var _url = CONTEXT_PATH+"/ticket/createWorkOrder?"+ Math.random();
            $("#workOrder").ajaxSubmit( {
                type : 'POST',
                url : _url,
                dataType : 'json',
                success : function(data){
                    if(data.status == "success"){
                        $('#myWorkModal1').modal('hide'); // 关闭模态框
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
        $('#myWorkModal1').modal('show');

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