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


   var ticketId= GetQueryiframe("ticketId");

    getTicketLogList(ticketId);
    getTicketInfo(ticketId);

function getTicketInfo(ticketId){
    var url = CONTEXT_PATH+"/ticket/getTicketInfo?"+ Math.random();
    jQuery.ajax({
        type : 'POST',
        contentType : 'application/x-www-form-urlencoded',
        url : url,
        dataType : 'json',
        data: {"ticketId":ticketId},
        success : function(data) {


            $("#ticketId").val(data.ticketId);
            $("#workType").val(data.ticketTypeName);
            $("#alarmContent").val(data.title);
            $("#phName").val(data.pumpName);
            $("#address").val(data.address);

            $("#alarmTime").val(data.eventTime);
            $("#alarmStatus").val(data.currentStatusName);
            $("#alarmLevel").val(data.ticketLevel);
            $("#processName").val(data.deviceName);

            $("#planStartTime").val(data.startTime);
            $("#planEndTime").val(data.endTime);
            $("#alarmReason").val(data.ticketReason);
            $("#planContent").val(data.ticketDescription);
            $("#status").val(data.status);

            $("#buttonId").html(data.currentStatusName);
            if(data.status==4){//增加审核操作
                $("#buttonId").html("审核");
                $("#approveOperationKZ").html("<div class=\"col-md-5\">\n" +
                    "                            <div class=\"form-group\">\n" +
                    "                                <label for=\"approveOperation\">审核:</label>\n" +
                    "                                <select id=\"approveOperation\">\n" +
                    "                                    <option value=\"\">请同选择审批结果</option>\n" +
                    "                                    <option value=\"Y\">同意</option>\n" +
                    "                                    <option value=\"N\">不同意</option>\n" +
                    "                                </select>\n" +
                    "                            </div>\n" +
                    "                        </div>");
            }else{
                $("#buttonId").html(data.currentStatusName);
            }
            //非告警工单  去掉不要的信息
            if(data.ticketType!=1){
                $("#alarmTimeAndAlarmLevel").css("display","none");
            }
        }
    });
}




function approval(){
    var ticketId= $("#ticketId").val();
    var status= $("#status").val();
    var approveOpinion= $("#approveOpinion").val();
    var approveOperation=$("#approveOperation").val();
    var json="";
    if(approveOperation==""){
        json={"ticketId":ticketId,"approveOpinion":approveOpinion};
    }else{
        json={"ticketId":ticketId,"approveOpinion":approveOpinion,"approveOperation":approveOperation};
    }

    if(approveOpinion==""){
        alert("处理结果不能为空!");
        return false;
    }if(status==4 && $("#approveOperation").val()==""){
            alert("审核结果不能为空!");
            return false;
    }else{
        var url = CONTEXT_PATH+"/ticket/handleWorkOrder?"+ Math.random();
        jQuery.ajax({
            type : 'POST',
            contentType : 'application/json',
            url : url,
            dataType : 'json',
            data:  JSON.stringify(json),
            success : function(data) {
                if(data.status == "success"){
                    frameElement.api.close();
                    alert(data.message);
                }else{
                    alert(data.message);
                }
            }
        });
    }

}


function getTicketLogList(ticketId){
    var url = CONTEXT_PATH+"/ticket/getTicketLogList?"+ Math.random();
    jQuery.ajax({
        type : 'POST',
        contentType : 'application/x-www-form-urlencoded',
        url : url,
        dataType : 'json',
        data: {"ticketId":ticketId},
        success : function(data) {
            console.log(data);
            if(data.length>0){
                var html="";

                for(var i=0;i<data.length;i++){
                    var approveOpinion="";
                    if(data[i].approveOpinion==undefined){
                        approveOpinion ="创建工单.....";
                    }else{
                        approveOpinion=data[i].approveOpinion;
                    }

                    html+="<div class=\"col-md-11\">\n" +
                        "                            <div class=\"form-group\">\n" +
                        "                                <label for=\"approveOpinion\">"+ data[i].createDate +"|"+data[i].ticketLogName+"</label>\n" +
                        "                                <textarea class=\"form-control\" rows=\"3\" readonly >"+approveOpinion+"</textarea>\n" +
                        "                            </div>\n" +
                        "                        </div>"
                }
                $("#ticketLogs").html(html);


            }
        }
    });
}


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




