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

getPumpList();
queryMaintenanceWorkerDept();
initCheck();

function getPumpList(){
    var url = CONTEXT_PATH+"/ticket/getPumpList?"+ Math.random();
    jQuery.ajax({
        type : 'POST',
        contentType : 'application/json',
        url : url,
        dataType : 'json',
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

function onChangPump(){
    var id=  $("#phId").val();
    //选择对应的地址
    $("#addressStr option[value='"+id+"']").attr("selected","selected");
    $("#address").val($("#addressStr").find("option:selected").text());
    //加载对应的设备
    getDeviceList(id);

    setAlarmContent()//设置工单名称
}


function getDeviceList(id){
    var url = CONTEXT_PATH+"/ticket/getDeviceList?"+ Math.random();
    jQuery.ajax({
        type : 'POST',
        contentType : 'application/x-www-form-urlencoded',
        url : url,
        dataType : 'json',
        data: {"id":id},
        success : function(data) {
            $("#deviceId").html("");
            for(var i=0;i<data.length;i++) {
                jQuery("#deviceId").append("<option value=" + data[i].id + ">" + data[i].name + "</option>");
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
                jQuery("#deptId").append("<option value="+ item.deptId+">"+ item.deptName+"</option>");
            });
        }
    });
}

function createLxTicket(){
    var _url = CONTEXT_PATH+"/ticket/createWorkOrder?"+ Math.random();
    $("#workOrder").ajaxSubmit( {
        type : 'POST',
        url : _url,
        dataType : 'json',
        success : function(data){
            if(data.status == "success"){
                frameElement.api.close();
                alert(data.message);
            }else{
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
}

function setAlarmContent(){
    var text1=$("#workType option:selected").text();
    var text2=$("#phId option:selected").text();
    $("#alarmContent").val(text1+"-"+text2);
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




