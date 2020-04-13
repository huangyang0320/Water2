var alertType = '0';
var total = 0;
var hideColumns;
var exportType="0";
var alarmInitCount = 0;
var alarmPage = "1";
var alarm_higth = 1;
var ischeck = false;
$(function(){
    alarmInitCount = 0;
    //初始化时间
    initTime();
    //默认查询工单信息
    //var url = CONTEXT_PATH+"/ticketController/ticketList?"+Math.random();
    var url = CONTEXT_PATH+"/ticket/getTicketList?"+Math.random();


    initBootTable(url);

    //查询事件
    $("#query").click(function(){
        ischeck = true;
        LOADING.show();
        $('#dataTables-example').bootstrapTable('removeAll');
        $('#dataTables-example').bootstrapTable('refresh');
        $('#dataTables-example').bootstrapTable('selectPage', 1);
        //initBootTable(url);
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

    $("#createBeginTime").datetimepicker({
    }).on("changeDate",function(ev){
        var createBeginTime=$("#createBeginTime").val();
        var createEndTime=$("#createEndTime").val();
        if(createBeginTime!="" && createEndTime!=""){
            if(createBeginTime>createEndTime){
                $("#createBeginTime").val('');
                showErrorMsg("开始时间不能大于结束时间!");
                return false;
            }
        }
    });

    $("#createEndTime").datetimepicker({
    }).on("changeDate",function(ev){
        var createBeginTime=$("#createBeginTime").val();
        var createEndTime=$("#createEndTime").val();
        if(createBeginTime!="" && createEndTime!=""){
            if(createBeginTime>createEndTime){
                $("#createEndTime").val('');
                showErrorMsg("结束时间不能小于开始时间!");
                return false;
            }
        }
    });

    $("#startBeginTime").datetimepicker({
    }).on("changeDate",function(ev){
        var startBeginTime=$("#startBeginTime").val();
        var startEndTime=$("#startEndTime").val();
        if(startBeginTime!="" && startEndTime!=""){
            if(startBeginTime>startEndTime){
                $("#startBeginTime").val('');
                showErrorMsg("开始时间不能大于结束时间!");
                return false;
            }
        }
    });

    $("#startEndTime").datetimepicker({
    }).on("changeDate",function(ev){
        var startBeginTime=$("#startBeginTime").val();
        var startEndTime=$("#startEndTime").val();
        if(startBeginTime!="" && startEndTime!=""){
            if(startBeginTime>startEndTime){
                $("#startEndTime").val('');
                showErrorMsg("结束时间不能小于开始时间!");
                return false;
            }
        }
    });
});

function initBootTable(url){
    var url = CONTEXT_PATH+"/ticket/getHandleTicketList?"+Math.random();
   // var url = CONTEXT_PATH+"/device/pumpHouse?'+ Math.random()";
    // 初始化无滚动条
    $("#page-wrapper").css("overflow-y", "hidden");
    $("#page-inner").css("padding-right", "25px");
    // table height
    var calcHeight = function() {
        var height = $(window).height() - $(".top-navbar").height() - $(".panel-heading").height() -
            $(".nav-tabs").height() - 72;
        return height;
    };

    $(window).resize(function() {
        $('#dataTables-example').bootstrapTable('resetView', {height : calcHeight() + 55});

    });
    $('#dataTables-example').bootstrapTable({
        url: url,
        cache: false,
        striped: true,
        height: calcHeight() + 55,//设定高度，固定头部
        search: false,//是否搜索
        queryParamsType: '',
        queryParams: queryParams,
        pageSize: 20,
        pageNumber: 1,
        sortName: "createDate",//根据什么排序
        sortOrder: "desc",//排序
        sidePagination: 'server',
        pagination: true,//是否分页
        showColumns: true,//列选择按钮
        minimumCountColumns: 2,
        pageList: [20,30,50,100],
        searchOnEnterKey: false,//回车搜索
        clickToSelect: true,
        showRefresh: false,//刷新按钮
        smartDisplay: true,
        showExport: false,
        exportDataType: 'all',
        rowStyle: rowStyle,
        onLoadSuccess:function(data){
            LOADING.hide();
            total = data.total;
            if(alertType == '1'){
                alertType = '0';
            }
        },
        onLoadError:function(data){
            LOADING.hide();
            alertType = '0';
            $("tbody").html('<tr class="no-records-found"><td colspan="20">没有找到匹配的记录</td></tr>');
        },
        locale: "zh-CN",//中文支持
        detailView: true, //是否显示详情折叠
        columns: [{
            field: 'deviceCode',
            title: '工单编号',
            align: 'center',
            sortable: true
        },{
            field: 'ticketTypeName',
            title: '工单类型',
            align: 'center',
            sortable: true
        },{
            field: 'title',
            title: '工单内容',
            align: 'center',
            sortable: true
        },/*{
            field: 'currentNodeName',
            title: '当前节点名称',
            align: 'center'
        },*/{
            field: 'currentStatusName',
            title: '当前工单状态',
            align: 'center',
            sortable : true,
            sortable: true
        },/*{
            field: 'currentUser',
            title: '当前操作人',
            align: 'center'
        },{
            field: 'taskSource',
            title: '工单来源',
            align: 'center'
        },*/{
            field: 'pumpName',
            title: '泵房名称',
            align: 'center',
            sortable : true,
            sortable: true
        },{
            field: 'deviceName',
            title: '设备名称',
            align: 'center',
            sortable: true
        },/*{
            field: 'ticketLevel',
            title: '故障等级',
            align: 'center'
        },*/{
            field: 'address',
            title: '泵房地址',
            align: 'center',
            sortable: true
        },{
            field: 'createDate',
            title: '工单创建时间',
            align: 'center',
            sortable : true,
            sortable: true
        },{
            field: 'operateSatus',
            title: '操作',
            align: 'center',
           /* events: operateEvents,*/
            formatter: operateFormatter
        }],
        detailFormatter: function(index, row) {// 详情信息
            var html = [];
            html.push('<p class="detail-view">' + '工单计划开始时间' + ' : ' + toTrim(row.startTime) + '</p>');
            html.push('<p class="detail-view">' + '工单计划结束时间' + ' : ' + toTrim(row.endTime) + '</p>');
            html.push('<p class="detail-view">' + '告警时间' + ' : ' + toTrim(row.eventTime) + '</p>');
            html.push('<p class="detail-view">' + '告警级别' + ' : ' + toTrim(row.ticketLevel) + '</p>');
            html.push('<p class="detail-view">' + '可能原因' + ' : ' + toTrim(row.ticketReason) + '</p>');
            html.push('<p class="detail-view">' + '解决方案' + ' : ' + toTrim(row.ticketDescription) + '</p>');
            return html.join('');
        }
    });

}

function operateFormatter(value, row, index) {
    //status:1 待分发 2 待接单   3处理中  4审核中  5完成
    if(row.status==1){
        return ['<button type="button" onclick="ticketDistribute(\''+row.ticketId+'\')" class="btn btn-warning">待分发</button>'].join('');
    }
    if(row.status==2){
        return ['<button type="button" onclick="signIn(\''+row.ticketId+'\')" class="btn btn-warning">待接单</button>'].join('');
    }
    if(row.status==5){
        return "完成";
    }else {
        return ['<button type="button" onclick="ticketHandle(\''+row.ticketId+'\')"  class="jumpWork btn btn-primary">'+row.currentStatusName+'</button>'].join('');
    };

}

function signIn(ticketId){
    var url = CONTEXT_PATH+"/ticket/signIn?"+ Math.random();
    jQuery.ajax({
        type : 'POST',
        contentType : 'application/x-www-form-urlencoded',
        url : url,
        dataType : 'json',
        data: {"ticketId":ticketId},
        success : function(data) {
            $("#query").click();
        }
    });
}

function ticketDistribute(ticketId){
    $.dialog({
        id: 'ticket-habdle',
        title: '工单分发',
        content: 'url:ticketDistribute.html?ticketId=' + ticketId + '&rnd=' + new Date().getTime(),
        width: 1200,
        height: 500,
        fixed: true,
        max: true,
        min: false,
        resize: false,
        lock: true,
        top:120,
        background: '#000',
        opacity: 0.65,
        ok: false,
        drag: false,
        close : function() {
            $("#query").click();
        }
    });
}


function ticketHandle(ticketId){
    $.dialog({
        id: 'ticket-habdle',
        title: '工单处理',
        content: 'url:ticketHandle.html?ticketId=' + ticketId + '&rnd=' + new Date().getTime(),
        width: 1200,
        height: 500,
        fixed: true,
        max: true,
        min: false,
        resize: false,
        lock: true,
        top:120,
        background: '#000',
        opacity: 0.65,
        ok: false,
        drag: false,
        close : function() {
            $("#query").click();
        }
    });
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
    //配置参数
    var ticketType = $("#ticketType").val();
    var pumpName = $("#pumpName").val();
    var alarmContent = $("#alarmContent").val();

    var createBeginTime =$("#createBeginTime").val();
    if(createBeginTime!=""){
        createBeginTime = createBeginTime+" 00:00:00";
    }

    var createEndTime =$("#createEndTime").val();
    if(createEndTime!=""){
        createEndTime = createEndTime+" 23:59:59";
    }

    var startBeginTime =$("#startBeginTime").val();
    if(startBeginTime!=""){
        startBeginTime = startBeginTime+" 00:00:00";
    }

    var startEndTime =$("#startEndTime").val();
    if(startEndTime!=""){
        startEndTime = startEndTime+" 23:59:59";
    }

    var sortName="";
    if(params.sortName=='pumpName'){
        sortName = "p.name";
    } else if (params.sortName=='currentStatusName'){
        sortName = "t.status";
    } else if (params.sortName=='createDate'){
        sortName = "t.create_Date";
    } else if (params.sortName=='deviceCode'){
        sortName = "t.device_code";
    } else if (params.sortName=='ticketTypeName'){
        sortName = "t.ticket_type"
    } else if (params.sortName=='title'){
        sortName = "t.title"
    } else if (params.sortName=='deviceName'){
        sortName = "de.NAME"
    } else if (params.sortName=='address'){
        sortName = "t.address"
    }
    // console.log(params)
    var temp = {
        pageSize: params.pageSize,   //页面大小
        pageNumber: params.pageNumber,  //页码
        limit : params.limit,
        offset : params.offset,
        sortName : sortName,
        sortOrder : params.sortOrder,
        ticketType: ticketType,
        pumpName: pumpName,
        title: alarmContent,
        createBeginTime: createBeginTime,
        createEndTime: createEndTime,
        startBeginTime: startBeginTime,
        startEndTime: startEndTime,
        // beginTime: $("#beginTime").val()+" 00:00:00",
        // endTime: $("#endTime").val()+" 23:59:59",
        // exportType: exportType,
        // exportMustNum: EXPORT_MUST_NUM
    };
    exportType='0';
    return temp;
}

function initTime(){
    var myDate = new Date();
    var weekTime = 24*60*60*1000*30;
    $("#createBeginTime").val(formatDate(myDate.getTime() - weekTime));
    $("#createEndTime").val(formatDate(myDate.getTime()));
    $("#startBeginTime").val(formatDate(myDate.getTime() - weekTime));
    $("#startEndTime").val(formatDate(myDate.getTime()));
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

//工单未关闭 且超出计划结束时间，该条工单记录标红
function rowStyle(row, index) {
    if (row.currentStatus != '9999' && row.planEndTime <= getCurrentDate()){
        return {css:{'color':'red'}};
    }
    return {css:{}};
}

//获取当前日期，格式 YYYY-MM-DD HH:mm:ss
function getCurrentDate() {
    var d = new Date();
    var years = d.getFullYear();
    var month = add_zero(d.getMonth() + 1);
    var days = add_zero(d.getDate());
    var hours = add_zero(d.getHours());
    var minutes = add_zero(d.getMinutes());
    var seconds = add_zero(d.getSeconds());
    var currentDate = years + "-" + month + "-" + days + " " + hours + ":" + minutes + ":" + seconds;
    return currentDate;
}
function add_zero(temp) {
    if (temp < 10) return "0" + temp;
    else return temp;
}

function showAllColumns(){
    hideColumns = $('#dataTables-example').bootstrapTable('getHiddenColumns');
    for(var i=0; i<hideColumns.length; i++){
        $('#dataTables-example').bootstrapTable('showColumn', hideColumns[i].field);
    }
}

function hideColumn(){
    if(undefined == hideColumns){
        return;
    }
    for(var i=0; i<hideColumns.length; i++){
        $('#dataTables-example').bootstrapTable('hideColumn', hideColumns[i].field);
    }
}

// function downLoadExcel(){
// 	var keyword = jQuery.base64.encode($("#keyword").val());
// 	var url = CONTEXT_PATH+"/alarmStatController/alarmExport?type="+$("#type").val()+"&keyword="+
// 	keyword+"&beginTime="+$("#beginTime").val()+"&endTime="+$("#endTime").val();
// 	location.href = url;
// }

function doClean() {

}





