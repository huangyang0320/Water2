/**
 * root path for the application
 */
var ROOT_PATH = function getContextPath() {
    var pathName = document.location.pathname;
    var index = pathName.substr(1).indexOf("/");
    return pathName.substr(0, index + 1);
}();

/**
 * context path for the application
 */
var CONTEXT_PATH = ROOT_PATH + "/a";
var ischeck = false;
//选择泵房的变量
var arysId="";
var arysName="";
var address="";

getPumpList();
queryMaintenanceWorkerDept();
initCheck();
showDevice($("#workType").val())
qryAreaList();
setTicketId("2");

var url = CONTEXT_PATH+"/device/pumpHouse?'+ Math.random()";
initBootTable(url);

$('#phId').selectpicker({});

/*window.alert = function(name){
    var iframe = document.createElement("IFRAME");
    iframe.style.display="none";
    document.documentElement.appendChild(iframe);
    window.frames[0].window.alert(name);
    iframe.parentNode.removeChild(iframe);
}
window.confirm = function (message) {
    var iframe = document.createElement("IFRAME");
    iframe.style.display = "none";
    iframe.setAttribute("src", 'data:text/plain,');
    document.documentElement.appendChild(iframe);
    var alertFrame = window.frames[0];
    var result = alertFrame.window.confirm(message);
    iframe.parentNode.removeChild(iframe);
    return result;
}*/

$("#areaId").change(function(){
    if('' != $("#areaId").val()){
        qryPumpHouseByAreaId($("#areaId").val());
    }else{
        $("#projectId").empty();
        $("#projectId").append("<option value = ''>全部</option>");
    }
});

//查询事件
$("#query").click(function(){
    ischeck = true;
    $('#dataTables-example').bootstrapTable('removeAll');
    $('#dataTables-example').bootstrapTable('refresh');
});

function showPumpInfo(){//模态框弹出加载泵房信息
    arysId="";
    arysName="";
    address="";
    $('#myModal').modal('show');
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
    var name = jQuery.base64.encode($("#name").val());
    var temp = {
        pageSize: params.pageSize,   //页面大小
        pageNumber: params.pageNumber,  //页码
        areaId: $("#areaId").val(),
        projectId: $("#projectId").val(),
        name: name,
        exportMustNum:1500
    };
    exportType='0';
    return temp;
}
function qryAreaList(){
    $("#areaId").empty();
    $("#areaId").append("<option value = ''>全部</option>");
    jQuery.ajax( {
        type : 'POST',
        contentType : 'application/json',
        url : CONTEXT_PATH+'/device/getAllAreas?'+ Math.random(),
        dataType : 'json',
        success : function(data){
            if(undefined == data){
                return;
            }else{
                $("#areaId").empty();
                $("#areaId").append("<option value = ''>全部</option>");
                $.each(data, function(i, item) {

                    $("#areaId").append("<option value = '" + item.deviceId + "'> "+item.deviceName  + "</option>");
                })
            }
        },
        error : function(data)
        {
        }
    });
}
function qryPumpHouseByAreaId(areaId){
    $("#projectId").empty();
    $("#projectId").append("<option value = ''>全部</option>");
    jQuery.ajax( {
        type : 'POST',
        contentType : 'application/json',
        url : CONTEXT_PATH+'/device/getProjectByAreaId/' + areaId+'?'+ Math.random(),
        dataType : 'json',
        success : function(data){
            if(undefined == data){
                return;
            }else{
                $("#projectId").empty();
                $("#projectId").append("<option value = ''>全部</option>");
                $.each(data, function(i, item) {

                    $("#projectId").append("<option value = '" + item.id + "'> "+item.deviceName  + "</option>");
                })
            }
        },
        error : function(data)
        {
        }
    });
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
function savePumps(){
    var row=$("#dataTables-example").bootstrapTable('getSelections');
    if(row!=null && row.length>0){
        for(var i=0;i<row.length;i++){
            arysId+=row[i]["id"]+",";
            arysName+=row[i]["pumpHouseName"]+",";
        }
        arysId=arysId.substring(0,arysId.length-1);
        arysName=arysName.substring(0,arysName.length-1);
        if(row.length=1){
            address=row[0]["cellAdress"];
        }
        //给泵房文本框设置值
        $("#phId").val(arysName);
        //设置对应的关联
        var id=  arysId.split(",");
        if(id!=null && id!='' && id.length>1){
            $("#address").val("安徽省芜湖市");
        }else{
            //选择对应的地址
            $("#address").val(address);
        }
        //加载对应的设备
        getDeviceList(id);
        setAlarmContent()//设置工单名称
    }

    $('#myModal').modal('hide');

}

function initBootTable(url){
    // 初始化无滚动条
    $("#page-wrapper").css("overflow-y", "hidden");
    $("#page-inner").css("padding-right", "25px");
    // table height
    var calcHeight = function() {
        var height = $(window).height() - $(".top-navbar").height() - $(".panel-heading").height() - 56;
        return height;
    };
    $(window).resize(function() {
        $('#dataTables-example').bootstrapTable('resetView', {height : calcHeight()+55});
    });

    $('#dataTables-example').bootstrapTable({
        url:url,
        cache:false,
        striped:true,
        height:calcHeight()+55,//设定高度，固定头部
        search: false,//是否搜索
        queryParamsType:'',
        queryParams:queryParams,
        pageSize:20,
        pageNumber:1,
        sidePagination:'server',
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
            total = data.total;
            if(alertType == '1'){
                alertType = '0';
            }
        },
        onLoadError:function(data){
            LOADING.hide();
            alertType = '0';
            $("tbody").html('<tr class="no-records-found"><td colspan="12">没有找到匹配的记录</td></tr>');
        },
        locale: "zh-CN",//中文支持
        detailView: true, //是否显示详情折叠
        columns: [ {field: 'selectItem', checkbox: true},{
            field: 'areaId',
            title: '所属区域',
            align: 'center',
        },{
            field: 'projectId',
            title: '项目名称',
            align: 'center'
        },{
            field: 'pumpHouseName',
            title: '泵房名称',
            align: 'center'
        },{
            field: 'id',
            title: '泵房编码',
            visible:false,
            align: 'center'
        },{
            field: 'cellAdress',
            title: '泵房地址',
            align: 'center'
        },{
            field: 'cellName',
            title: '小区名称',
            align: 'center'
        },/*{
               field: 'cellAdress',
               title: '小区地址',
               visible:false,
               visible:false,
               align: 'center'
           },*/{
            field: 'deviceManufacturers',
            title: '设备厂家',
            visible:false,
            align: 'center'
        },{
            field: 'deviceManufacturersInformation',
            title: '设备厂家联系方式',
            visible:false,
            align: 'center'
        },{
            field: 'selfControlManufacturers',
            title: '自控改造厂家',
            visible:false,
            align: 'center'
        },{
            field: 'selfControlManufacturersInformation',
            title: '自控改造厂家联系方式',
            visible:false,
            align: 'center'
        },{
            field: 'constructionSide',
            title: '运维方',
            visible:false,
            align: 'center'
        },{
            field: 'constructionSideInformation',
            title: '运维方联系方式',
            visible:false,
            align: 'center'
        },{
            field: 'construction',
            title: '施工方',
            visible:false,
            align: 'center'
        },{
            field: 'constructionInformation',
            title: '施工方联系方式',
            visible:false,
            align: 'center'
        },{
            field: 'property',
            title: '物业',
            visible:false,
            align: 'center'
        },{
            field: 'propertyInformation',
            title: '物业联系方式',
            visible:false,
            align: 'center'
        },{
            field: 'handoverTime',
            title: '移交时间',
            visible:false,
            align: 'center'
        }],
        detailFormatter: function(index, row) {// 详情信息
            var html = [];
            html.push('<p class="detail-view">' + '所属区域' + ' : ' + toTrim(row.areaId) + '</p>');
            html.push('<p class="detail-view">' + '项目名称' + ' : ' + toTrim(row.projectId) + '</p>');
            html.push('<p class="detail-view">' + '泵房名称' + ' : ' + toTrim(row.pumpHouseName) + '</p>');
            html.push('<p class="detail-view">' + '泵房编码' + ' : ' + toTrim(row.id) + '</p>');
            html.push('<p class="detail-view">' + '泵房地址' + ' : ' + toTrim(row.pumpHouseAddress) + '</p>');
            html.push('<p class="detail-view">' + '小区名称' + ' : ' + toTrim(row.cellName) + '</p>');
            html.push('<p class="detail-view">' + '小区地址' + ' : ' + toTrim(row.cellAdress) + '</p>');
            html.push('<p class="detail-view">' + '运维方' + ' : ' + toTrim(row.constructionSide) + '</p>');
            html.push('<p class="detail-view">' + '运维方联系方式' + ' : ' + toTrim(row.constructionSideInformation) + '</p>');
            html.push('<p class="detail-view">' + '物业' + ' : ' + toTrim(row.property) + '</p>');
            html.push('<p class="detail-view">' + '物业联系方式' + ' : ' + toTrim(row.propertyInformation) + '</p>');
            html.push('<p class="detail-view">' + '施工方' + ' : ' + toTrim(row.construction) + '</p>');
            html.push('<p class="detail-view">' + '施工方联系方式' + ' : ' + toTrim(row.constructionInformation) + '</p>');
            html.push('<p class="detail-view">' + '自控改造厂家' + ' : ' + toTrim(row.selfControlManufacturers) + '</p>');
            html.push('<p class="detail-view">' + '自控改造厂家联系方式' + ' : ' + toTrim(row.selfControlManufacturersInformation) + '</p>');
            html.push('<p class="detail-view">' + '设备厂家' + ' : ' + toTrim(row.deviceManufacturers) + '</p>');
            html.push('<p class="detail-view">' + '设备厂家联系方式' + ' : ' + toTrim(row.deviceManufacturersInformation) + '</p>');
            html.push('<p class="detail-view">' + '移交时间' + ' : ' + toTrim(row.handoverTime) + '</p>');
            html.push('<p class="detail-view">' + '备注' + ' : ' + toTrim(row.memo) + '</p>');
            return html.join('');
        }
    });

}

function getPumpList(){
    var url = CONTEXT_PATH+"/ticket/getPumpList?"+ Math.random();
    jQuery.ajax({
        type : 'POST',
        contentType : 'application/json',
        url : url,
        dataType : 'json',
        async:false,
        success : function(data) {
            $("#phId").html("");
            $("#addressStr").html("");

            for (var i = 0; i < data.length; i++) {
                jQuery("#phId").append("<option value=" + data[i].id + ">" + data[i].name + "</option>");
                jQuery("#addressStr").append("<option value=" + data[i].id + ">" + data[i].address + "</option>");
                if (i == 0) {
                    getDeviceList(data[i].id);
                    $("#address").val(data[i].address );
                }
            }
            //在加载完下拉框后设置对应的工单名称
            setAlarmContent()//初始化调用下设置工单名称
        }
    });
}

function getDeviceList(id){
    var url = CONTEXT_PATH+"/productComponent/getProductList?"+ Math.random();
    jQuery.ajax({
        type : 'POST',
        contentType : 'application/x-www-form-urlencoded',
        url : url,
        dataType : 'json',
        data: {},
        async:false,
        success : function(data) {
            $("#deviceId").html("");
            for(var i=0;i<data.length;i++) {
                jQuery("#deviceId").append("<option value=" + data[i].id + ">" + data[i].componentName + "</option>");
            }
        }
    });
}


function queryMaintenanceWorkerDept() {
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
        }
    });
}

function changerDept() {
    $("#mgName").val(jQuery("#deptId option:selected").attr("mgName"));
}

function createLxTicket(){
    //保存设置泵房多选
    var id = arysId;
    if (id != '' && id != null && id != undefined) {
        $("#phStr").val(id);
    } else {
        $("#phStr").val(null);
        $('#alertShowMessage').html('请至少选择一个泵房!!!');
        $('#alertShow').modal('show');
        return
    }
    //保存设置工单配件多选
    var deviceId = $("#deviceId").val()
    if (deviceId != '' && deviceId != null && deviceId != undefined) {
        var deviceIdStr = "";
        for (var i = 0; i < deviceId.length; i++) {
            deviceIdStr += deviceId[i] + ","
        }
        deviceIdStr = deviceIdStr.substring(0, deviceIdStr.length - 1)
        $("#deviceStr").val(deviceIdStr);
    } else {
        $("#deviceStr").val(null);
        var type = $("#workType").val();
        if (type == "3") {
            $('#alertShowMessage').html('请至少选择一个设备!!!');
            $('#alertShow').modal('show');
            return
        }

    }

    $('#alertWorkMessage').html('确认要创建工单?');
    $('#alertWork').modal('show');
}
function clickOk(){
    var _url = CONTEXT_PATH + "/ticket/createWorkOrder?" + Math.random();
    $("#workOrder").ajaxSubmit({
        type: 'POST',
        url: _url,
        dataType: 'json',
        async: false,
        success: function (data) {
            if (data.status == "success") {
                frameElement.api.close();
                alert(data.message);
            } else {
                alert(data.message);
            }

        }
    });
}

function onChangType(){
    var id=  $("#workType").val();
    if(id=="2"){
        initCheck();
    }else{
        intiWb();
    }
    setAlarmContent()//设置工单名称
    showDevice(id);
    setTicketId(id);
}

function showDevice(type){
    if(type=="2"){
        $("#device").hide();
    }else if(type=="3"){
        $("#device").show();
    }
}

function setAlarmContent(){
    var text1=$("#workType option:selected").text();
    $("#alarmContent").val(text1+"-"+arysName);
}

function initCheck(){
    var alarmReason="（1）按照规定对所负责的站点每周巡视一次，根据巡视线路和巡视内容进行巡视检查，以防止设备漏巡，设备异常时应查明原因；\n" +
        "（2）及时向运行维修单位的负责人汇报巡视中发现的问题；\n" +
        "（3）巡视时按照要求做好相关保护、报警的测试工作。做好安全生产巡视，特别是水箱上锁及门禁设防等工作；\n" +
        "（4）保持泵房进出通道及站内环境卫生和设备卫生；\n" +
        "（5）加强恶劣天气及重要保供时段的巡视工作；";
    var planContent="门禁系统----电源进线柜----控制柜----在线仪表----泵组----水箱--电动阀门--水质检查----污水泵----通风设备----卫生清理----加压地库及立管检查";
    $("#alarmReason").val(alarmReason);
    $("#planContent").val("巡检路线:"+planContent);
}

function intiWb(){
    var alarmReason="倒流防止器";
    var planContent="先行封堵后上报，拆卸、安装";
    $("#alarmReason").val(alarmReason);
    $("#planContent").val(planContent);
}



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



function GetQueryiframe(key) {
    var search = window.location.search.slice(1); // 得到get方式提交的查询字符串
    var arr = search.split("&");
    try {
        for (var i = 0; i < arr.length; i++) {
            var ar = arr[i].split("=");
            if (ar[0] == key) {
                if (unescape(ar[1]) == 'undefined') {
                    return "";
                } else {
                    return unescape(ar[1]);
                }
            }
        }
    } catch (ex) {
    }
    return "";
}




