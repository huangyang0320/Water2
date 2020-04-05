
var total = 0;
var exportType="0";
$(function(){
    //初始化时间
    initTime();
    // datetimepicker init
    $(".warn-time-begin, .warn-time-end").datetimepicker({
        language : 'zh-CN',
        format : 'yyyy-mm-dd hh:ii:ss',
        weekStart: 1,
        todayBtn:  1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        forceParse: 0,
        showMeridian: 1
    });


    $("#startTimeStamp").datetimepicker({
    }).on("changeDate",function(ev){
        var  startTimeStamp=$("#startTimeStamp").val();
        var endTimeStamp=$("#endTimeStamp").val();
        if( startTimeStamp!="" && endTimeStamp!=""){
            if( startTimeStamp>endTimeStamp){
                $("#startTimeStamp").val('');
                showErrorMsg("开始时间不能大于结束时间!");
                return false;
            }
        }
    });

    $("#endTimeStamp").datetimepicker({
    }).on("changeDate",function(ev){
        var  startTimeStamp=$("#startTimeStamp").val();
        var endTimeStamp=$("#endTimeStamp").val();
        if( startTimeStamp!="" && endTimeStamp!=""){
            if( startTimeStamp>endTimeStamp){
                $("#endTimeStamp").val('');
                showErrorMsg("结束时间不能小于开始时间!");
                return false;
            }
        }
    });

});


function initTime(){
    var myDate = new Date();
    var weekTime = 24*60*60*1000*7;
    $("#beginTime").val(formatDate(myDate.getTime())+" 00:00:00");
    $("#endTime").val(formatDate(myDate.getTime())+" 23:59:59");
    /*$("#planStartTime").val(myDate.Format("yyyy-MM-dd HH:mm:ss"));
     myDate.setDate(myDate.getDate() + 7)
    $("#planEndTime").val(myDate.Format("yyyy-MM-dd HH:mm:ss"));*/
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
