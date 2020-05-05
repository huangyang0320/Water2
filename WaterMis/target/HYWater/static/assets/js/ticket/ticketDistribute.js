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

function showDevice(){
    var type=$("#workType").val();
    if(type=="巡检工单"){
        $("#device").hide();
    }else{
        $("#device").show();
    }
}

function showChannelOrAddress(){
    var type=$("#workType").val();
    if(type=="告警工单"){
        $("#channelShow").hide();
        $("#addressShow").show();
    }else{
        $("#addressShow").hide();
        $("#channelShow").show();
    }
}
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
            $("#channelName").val(data.channelName);
            $("#alarmContent").val(data.title);
            $("#phName").val(data.pumpName);
            $("#address").val(data.address);

            $("#alarmTime").val(data.eventTime);
            $("#alarmStatus").val(data.currentStatusName);
            $("#alarmLevel").val(data.ticketLevel);
            if(data.ticketType=="1"){//告警的设备
                $("#processName").val(data.deviceName1);
            }else{
                $("#processName").val(data.deviceName);
            }

            $("#planStartTime").val(data.startTime);
            $("#planEndTime").val(data.endTime);
            $("#alarmReason").val(data.ticketReason);
            $("#planContent").val(data.ticketDescription);
            $("#status").val(data.status);
            //初始话部门对应的处理人 列表
            getApprovalUserList(data.deptId);
            $("#buttonId").html("分发");

            showDevice()
            showChannelOrAddress()

            //非告警工单  去掉不要的信息
            if(data.ticketType!=1){
                $("#alarmTimeAndAlarmLevel").css("display","none");
            }
        }
    });
}

function getApprovalUserList(deptId){
    var url =CONTEXT_PATH+"/ticket/getUserListByDeptId/"+deptId+"?"+ Math.random();
    jQuery.ajax({
        type : 'POST',
        contentType : 'application/json',
        url : url,
        dataType : 'json',
        success : function(data) {
            console.log(data);
            $("#handleUserId").html("");
            $.each(data, function (i, item) {
                jQuery("#handleUserId").append("<option value="+ item.id+">"+ item.name+"</option>");
            });
        }
    });
}


function approval(){
        var status = $("#status").val();
        var approveOpinion = $("#approveOpinion").val();

        if (approveOpinion == "") {
            Ewin.alert('处理结果不能为空!!!');
            //$('#alertShow').modal('show');
            return false;
        }
        if (status == 4 && $("#approveOperation").val() == "") {
            Ewin.alert('审核结果不能为空!!!');
            //$('#alertShow').modal('show');
            return false;
        }
       // Ewin.alert('确认要分发工单?');
        Ewin.confirm({ message: "确认要分发工单?" }).on(function (e) {
            if (!e) {
                return;
            }
            clickOk();
        });

}

function clickOk(){
    var ticketId = $("#ticketId").val();
    var status = $("#status").val();
    //var approvalById= $("#approvalById").val();
    var approveOpinion = $("#approveOpinion").val();
    var handleUserId = $("#handleUserId").val();

    var json = {
        "ticketId": ticketId,
        "approveOpinion": approveOpinion,
        "handleStatus": "01",
        "handleUserId": handleUserId,
        "handleUserId": handleUserId
    };//分配到人

    var url = CONTEXT_PATH + "/ticket/handleWorkOrder?" + Math.random();
    jQuery.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: url,
        dataType: 'json',
        data: JSON.stringify(json),
        success: function (data) {
            if (data.status == "success") {
                frameElement.api.close();
                Ewin.alert(data.message);
            } else {
                Ewin.alert(data.message);
            }
        }
    });
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
                        approveOpinion ="创建工单";
                    }else{
                        approveOpinion=data[i].approveOpinion;
                    }

                    var nodeId=data[i].nodeId==undefined?"":"环节名:"+data[i].nodeId;
                    html+="<div class=\"col-md-11\">\n" +
                        "                            <div class=\"form-group\">\n" +
                        "                                <label for=\"approveOpinion\">操作时间:"+ data[i].createDate +"|操作人:"+data[i].ticketLogName+"|"+nodeId+"</label>\n" +
                        "                                <textarea class=\"form-control\" rows=\"3\" readonly  style=\"resize: none;\">"+approveOpinion+"</textarea>\n" +
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




