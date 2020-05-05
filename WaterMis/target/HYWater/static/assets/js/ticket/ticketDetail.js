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
    initDetails();
function showDevice(ticketType){
    if(ticketType=="2"){
        $("#device").hide();
    }else{
        $("#device").show();
    }
}
function showChannelOrAddress(ticketType){
    var type=$("#workType").val();
    if(ticketType=="1"){
        $("#channelShow").hide();
        $("#addressShow").show();
    }else{
        $("#addressShow").hide();
        $("#channelShow").show();
    }
}
function initDetails(){
    //编辑表格
    $('#reportTable').bootstrapTable({
        //数据来源的网址
        url:CONTEXT_PATH+'/ticketParts/getTicketPartsList?ticketId='+ticketId+"&"+Math.random(),
        method: 'post',
        height:'300',
        editable:true,//开启编辑模式
        clickToSelect: true,
        showPaginationSwitch:false, //显示分页切换按钮
        search: false,  //显示检索框
        showRefresh: false,  //显示刷新按钮
        showToggle:false, //显示切换按钮来切换列表/卡片视图
        pagination: true,
        pageList:  [10],
        pageSize:10,
        pageNumber:1,
        formatShowingRows:function(){
            return ''
        },
        formatNoMatches:function(){
            return '点击上方‘增加’按钮录入配件'
        },
        columns: [
            {checkbox: true},/*{
                field: 'index',
                align: 'center',
                title: '序号',
                formatter: function (value, row, index) {
                    return index + 1;
                }
            },*/
            {
                field:"type",
                edit:{
                    type:'select',//下拉框
                    //数据来源地址
                    //url:'user/getUser.htm',
                    data:[{id:1,text:'水泵类型1'},{id:2,text:'水泵类型2'}],
                    valueField:'id',
                    textField:'text',
                    onSelect:function(val,rec){
                        console.log(val,rec);
                    }
                },
                title:"类型",
                align:"center",
                width:"200px"
            },
            {
                field:"name",
                edit:{
                    type:'select',
                    data:[{id:1,text:'水泵装置1'},{id:2,text:'水泵装置2'}],
                    valueField:'id',
                    textField:'text',
                    onSelect:function(val,rec){
                        console.log(val,rec);
                    }
                },
                title:"名称",
                align:"center",
                width:"200px"
            },
            {
                field:"nums",
                title:"数量",
                align:"center",
                edit: {
                    type: 'text',
                    title: '数量',
                    validate: function (v) {
                        if (isNaN(v)) return '数量必须是数字';
                        var age = parseInt(v);
                        if (age <= 0) return '数量必须是正整数';
                    }
                }
            },
            {
                field:"price",
                title:"单价",
                align:"center",
                edit: {
                    type: 'text',
                    title: '单价',
                    validate: function (v) {
                        if (isNaN(v)) return '单价必须是数字';
                        var price = parseFloat(v);
                        if (price <= 0) return '数量必须是正数';
                    }
                }
            }
        ]
    });

};
function getTicketInfo(ticketId){
    var url = CONTEXT_PATH+"/ticket/getTicketInfo?"+ Math.random();
    jQuery.ajax({
        type : 'POST',
        contentType : 'application/x-www-form-urlencoded',
        url : url,
        dataType : 'json',
        data: {"ticketId":ticketId},
        success : function(data) {
            $("#showTicketPartsTable").hide();
            if(data.ticketType=="2"){//巡检
                $("#showTicketPartsTable").hide();
            }else{
                if(data.status=="3"||data.status=="4"||data.status=="5"){
                    $("#showTicketPartsTable").show();
                }
            }
            showDevice(data.ticketType);
            showChannelOrAddress(data.ticketType)

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

            $("#buttonId").html(data.currentStatusName);
            /*if(data.status==4){//增加审核操作
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
            }else{*/
                $("#buttonId").html(data.currentStatusName);
           // }
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
                    Ewin.alert(data.message);
                }else{
                    Ewin.alert(data.message);
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
                        approveOpinion ="创建工单";
                    }else{
                        approveOpinion=data[i].approveOpinion;
                    }

                    var nodeId=data[i].nodeId==undefined?"":"环节名:"+data[i].nodeId;
                    html+="<div class=\"col-md-11\">\n" +
                        "                            <div class=\"form-group\">\n" +
                        "                                <label for=\"approveOpinion\">操作时间:"+ data[i].createDate +"|操作人:"+data[i].ticketLogName+"|"+nodeId+"</label>\n" +
                        "                                <textarea class=\"form-control\" rows=\"3\" readonly style=\"resize: none;\">"+approveOpinion+"</textarea>\n" +
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




