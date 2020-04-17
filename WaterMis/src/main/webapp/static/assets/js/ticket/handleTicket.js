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
   var status= GetQueryiframe("status");
   var flag=1;
    getTicketLogList(ticketId);
    getTicketInfo(ticketId);

initDetails();

showBtn();

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
            $("#deptId").val(data.deptId);


            $("#buttonId").html(data.currentStatusName);
            if(data.status==4){//增加审核操作
                //隐藏退回按钮
                $("#backButton").hide();

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
                $("#buttonId").html("处理");
            }
            //非告警工单  去掉不要的信息
            if(data.ticketType!=1){
                $("#alarmTimeAndAlarmLevel").css("display","none");
            }
        }
    });
}




function approval(flag2){
    var status = $("#status").val();
    var approveOpinion = $("#approveOpinion").val();

    if (approveOpinion == "") {
        $('#alertShowMessage').html('处理结果不能为空!!!');
        $('#alertShow').modal('show');
        return false;
    }
    if (status == 4 && $("#approveOperation").val() == "") {审核结果不能为空
        $('#alertShowMessage').html('审核结果不能为空!!!');
        $('#alertShow').modal('show');
        return false;
    }
    var str="";
    if(flag2=="1"){
        str = $("#buttonId").html();
    }else{
        str="退单"
    }
    this.flag=flag2
    $('#alertWorkMessage').html("确认要"+str+"工单?");
    $('#alertWork').modal('show');
}

function clickOk(){
    var ticketId = $("#ticketId").val();
    var approveOpinion = $("#approveOpinion").val();
    var approveOperation = $("#approveOperation").val();
    var deptId = $("#deptId").val();
    var json = "";

    //回退
    if (flag == 0) {
        json = {"ticketId": ticketId, "approveOpinion": approveOpinion, "handleStatus": "02", "deptId": deptId};
    } else {
        //处理 前往待审核
        if (approveOperation == "" || approveOperation == undefined) {
            //处理  发往待审核
            json = {"ticketId": ticketId, "approveOpinion": approveOpinion, "handleStatus": "03", "deptId": deptId};
        } else {
            if (approveOperation == "N") {
                json = {
                    "ticketId": ticketId,
                    "approveOpinion": approveOpinion,
                    "approveOperation": approveOperation,
                    "handleStatus": "04",
                    "deptId": deptId
                };
            } else if (approveOperation == "Y") {
                json = {
                    "ticketId": ticketId,
                    "approveOpinion": approveOpinion,
                    "approveOperation": approveOperation,
                    "handleStatus": "05",
                    "deptId": deptId
                };
            }

        }
    }

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
                alert(data.message);
            } else {
                alert(data.message);
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
                        approveOpinion ="创建工单.....";
                    }else{
                        approveOpinion=data[i].approveOpinion;
                    }
                    var nodeId=data[i].nodeId==undefined?"":"环节名:"+data[i].nodeId;
                    html+="<div class=\"col-md-11\">\n" +
                        "                            <div class=\"form-group\">\n" +
                        "                                <label for=\"approveOpinion\">操作时间:"+ data[i].createDate +"|操作人:"+data[i].ticketLogName+"|"+nodeId+"</label>\n" +
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



//配件增删改查
/*function save_para_table(){

    var tableinfo = gettableinfo();
    alert(tableinfo);


}
//get table infomation
function gettableinfo(){
    var key = "";
    var value = "";
    var tabledata = "";
    var table = $("#para_table");
    var tbody = table.children();
    var trs = tbody.children();
    for(var i=1;i<trs.length;i++){
        var tds = trs.eq(i).children();
        for(var j=0;j<tds.length;j++){
            if(j==0){
                if(tds.eq(j).text()==null||tds.eq(j).text()==""){
                    return null;
                }
                key = "key\":\""+tds.eq(j).text();
            }
            if(j==1){
                if(tds.eq(j).text()==null||tds.eq(j).text()==""){
                    return null;
                }
                value = "value\":\""+tds.eq(j).text();
            }
        }
        if(i==trs.length-1){
            tabledata += "{\""+key+"\",\""+value+"\"}";
        }else{
            tabledata += "{\""+key+"\",\""+value+"\"},";
        }
    }
    tabledata = "["+tabledata+"]";
    return tabledata;
}

function tdclick(tdobject){
    var td=$(tdobject);
    td.attr("onclick", "");
    //1,取出当前td中的文本内容保存起来
    var text=td.text();
    //2,清空td里面的内容
    td.html(""); //也可以用td.empty();
    //3，建立一个文本框，也就是input的元素节点
    var input=$("<input>");
    //4，设置文本框的值是保存起来的文本内容
    input.attr("value",text);
    input.bind("blur",function(){
        var inputnode=$(this);
        var inputtext=inputnode.val();
        var tdNode=inputnode.parent();
        tdNode.html(inputtext);
        tdNode.click(tdclick);
        td.attr("onclick", "tdclick(this)");
    });
    input.keyup(function(event){
        var myEvent =event||window.event;
        var kcode=myEvent.keyCode;
        if(kcode==13){
            var inputnode=$(this);
            var inputtext=inputnode.val();
            var tdNode=inputnode.parent();
            tdNode.html(inputtext);
            tdNode.click(tdclick);
        }
    });

    //5，将文本框加入到td中
    td.append(input);
    var t =input.val();
    input.val("").focus().val(t);
//              input.focus();

    //6,清除点击事件
    td.unbind("click");
}

function addtr(){
    var table = $("#para_table");
    var tr= $("<tr>" +
        "<td><select class='form-control' id='deptId'name='deptId'></td>" +
        "<td><select class='form-control' id='deptId'name='deptId'></td></td>" +
        "<td><input type='text' class='form-control' id='nums' name='nums'></td>" +
        "<td><input type='text' class='form-control' id='alarmContent' name='alarmContent'></td>" +
        "<td  align='center'><button type='button'  class='btn btn-primary'  onclick='save(this)'>"+"保存"+"</button>"+
        "<button type='button'  class='btn btn-warning'  onclick='deletetr(this)'>"+"删除"+"</button></td></tr>");
    table.append(tr);
}
//删除的数据
function deletetr(tdobject){
    var td=$(tdobject);
    td.parents("tr").remove();
}
//保存的数据
function save(tdobject){
    var td=$(tdobject);
    console.log(td)
    return
    var url =CONTEXT_PATH+"/ticketParts/addOrUpdateTicketParts";
    jQuery.ajax({
        type : 'POST',
        contentType : 'application/json',
        url : url,
        params: {

        },
        dataType : 'json',
        success : function(data) {
            if(data.status == "success"){
                frameElement.api.close();
                alert(data.message);
            }else{
                alert(data.message);
            }
        }
    });
}*/
/*
var curRow = {};
$(function () {
    $("#ticket_parts").bootstrapTable({
        toolbar: "#toolbar",
        idField: "Id",
        pagination: true,
        showRefresh: true,
        search: true,
        clickToSelect: true,
        queryParams: function (param) {
            return {};
        },
        url: "/ticketParts/getTicketPartsList",
        columns: [/!*{
            checkbox: true
        }, *!/{
            field: "type",
            title: "类型",
            formatter: function (value, row, index) {
                return "<a href=\"#\" name=\"UserName\" data-type=\"text\" data-pk=\""+row.Id+"\" data-title=\"用户名\">" + value + "</a>";
            }
        },{
            field: "name",
            title: "名称",
            formatter: function (value, row, index) {
                return "<a href=\"#\" name=\"UserName\" data-type=\"text\" data-pk=\""+row.Id+"\" data-title=\"用户名\">" + value + "</a>";
            }
        }, {
            field: "nums",
            title: "数量",
            editable: {
                type: 'text',
                title: '数量',
                validate: function (v) {
                    if (isNaN(v)) return '数量必须是数字';
                    var age = parseInt(v);
                    if (age <= 0) return '数量必须是正整数';
                }
            }
        }, {
            field: "price",
            title: "单价",
            editable: {
                type: 'text',
                title: '单价',
                validate: function (v) {
                    if (isNaN(v)) return '单价必须是数字';
                    var age = parseInt(v);
                    if (age <= 0) return '单价必须是正整数';
                }
            }
        },
            {
                field: "DeptId",
                title: "部门",
                editable: {
                    type: 'select',
                    title: '部门',
                    source:[{value:"1",text:"研发部"},{value:"2",text:"销售部"},{value:"3",text:"行政部"}]

                    /!*source: function () {
                        var result = [];
                        $.ajax({
                            url: '/Editable/GetDepartments',
                            async: false,
                            type: "get",
                            data: {},
                            success: function (data, status) {
                                $.each(data, function (key, value) {
                                    result.push({ value: value.ID, text: value.Name });
                                });
                            }
                        });
                        return result;
                    }*!/
                }
            }],
        onClickRow: function(row, $element) {
            curRow = row;
        },
        onLoadSuccess: function(aa, bb, cc) {
            $("#ticket_parts a").editable({
                url: function (params) {
                    var sName = $(this).attr("name");
                    curRow[sName] = params.value;
                    $.ajax({
                        type: 'POST',
                        url: "/Editable/Edit",
                        data: curRow,
                        dataType: 'JSON',
                        success: function (data, textStatus, jqXHR) {
                            alert('保存成功！');
                        },
                        error: function () { alert("error");}
                    });
                },
                type: 'text'
            });
        },
    });
});


*/

function showBtn(){
    if(status=="3"){
        $('#addRowbtn').prop('disabled', false); //启用
        $('#sava').prop('disabled', false); //启用
        $('#delete').prop('disabled', false); //启用
    }else if(status=="4"){
        $('#addRowbtn').prop('disabled', true); //禁用
        $('#sava').prop('disabled', true); //禁用
        $('#delete').prop('disabled', true); //禁用
    }
}

function initDetails(){
    //编辑表格
    $('#reportTable').bootstrapTable({
        //数据来源的网址
        url:CONTEXT_PATH+'/ticketParts/getTicketPartsList?ticketId='+ticketId+"&"+Math.random(),
        method: 'post',
        editable:true,//开启编辑模式
        clickToSelect: true,
        showPaginationSwitch:true, //显示分页切换按钮
        search: false,  //显示检索框
        showRefresh: false,  //显示刷新按钮
        showToggle:false, //显示切换按钮来切换列表/卡片视图
        pagination: true,
        pageList:  [20,30,50,100],
        pageSize:20,
        pageNumber:1,
        columns: [{checkbox: true},
            {field:"type",edit:{
                    type:'select',//下拉框
                    //数据来源地址
                    //url:'user/getUser.htm',
                    data:[{id:1,text:'水泵类型1'},{id:2,text:'水泵类型2'}],
                    valueField:'id',
                    textField:'text',
                    onSelect:function(val,rec){
                        console.log(val,rec);
                    }
                },title:"类型",align:"center",width:"200px"},
            {field:"name",edit:{
                    type:'select',//下拉框
                    //数据来源地址
                    //url:'user/getUser.htm',
                    data:[{id:1,text:'水泵装置1'},{id:2,text:'水泵装置2'}],
                    valueField:'id',
                    textField:'text',
                    onSelect:function(val,rec){
                        console.log(val,rec);
                    }
                },title:"名称",align:"center",width:"200px"},

            {field:"nums",title:"数量",align:"center",edit: {
                    type: 'text',
                    title: '数量',
                    validate: function (v) {
                        if (isNaN(v)) return '数量必须是数字';
                        var age = parseInt(v);
                        if (age <= 0) return '数量必须是正整数';
                    }
                }},
            {field:"price",title:"单价",align:"center",edit: {
                    type: 'text',
                    title: '单价',
                    validate: function (v) {
                        if (isNaN(v)) return '单价必须是数字';
                        var price = parseFloat(v);
                        if (price <= 0) return '数量必须是正数';
                    }
                }}
        ],
        onClickCell: function(field, value, row, $element) {
            $element.attr('contenteditable', true);
            $element.blur(function() {
                let index = $element.parent().data('index');
                let tdValue = $element.html();

                saveDataTable(index, field, tdValue);
            })
        }
    });
    $('#addRowbtn').click(function(){
        var count = $('#reportTable').bootstrapTable('getData').length;
        $('#reportTable').bootstrapTable('insertRow',{index:count,row: {
                id: '',
                type: '',
                name: '',
                nums: '',
                price: ''
            }});
    });


};
function saveDataTable(index, field, value) {
    $('#reportTable').bootstrapTable('updateCell', {
        index: index,       //行索引
        field: field,       //列名
        value: value        //cell值
    })
}
function deleteRow(){
    var row = $('#reportTable').bootstrapTable('getSelections');
    if(row.length==1){
        var id=row[0]["id"];
        if(id==null||id==""||id==undefined){
            $('#reportTable').bootstrapTable("refresh");
            alert("配件记录删除成功!");
        }else{
            $.ajax({
                url: CONTEXT_PATH +'/ticketParts/deleteTicketParts',
                type: 'post',
                traditional: true,
                dataType: 'json',
                data: {"id":id},
                success: function (data) {
                    if (data.status == "success") {
                        alert(data.message);
                        $('#reportTable').bootstrapTable("refresh");
                    }
                },
                error: function () {
                    alert(data.message);
                }
            })
        }

    }else{
        alert("请选中一行")
    }
}
function validRow(row) {
    var name = row[0]["name"];
    var type = row[0]["type"];
    var nums = row[0]["nums"];
    var price = row[0]["price"];
    if(type==null||type==""||type==undefined){
        alert("类型不能为空!")
        return false;
    }
    if(name==null||name==""||name==undefined){
        alert("名称不能为空!")
        return false;
    }
    if(nums==null||nums==""||nums==undefined){
        alert("数量不能为空!")
        return false;
    }else{
        if (isNaN(nums)){
            alert("数量必须是数字!")
            return false;
        }
        if (!(/(^[1-9]\d*$)/.test(nums))) {
            alert("数量必须是正整数!")
            return false;
        }
    }

    if(price==null||price==""||price==undefined){
        alert("单价不能为空!")
        return false;
    }else{
        if (isNaN(price)){
            alert("单价必须是数字!")
            return false;
        }
        var priceInt = parseFloat(price);
        if (priceInt <= 0){
            alert("单价数量必须是正数!")
            return false;
        }
    }

    return true

}
function save(){
    var row = $('#reportTable').bootstrapTable('getSelections');
    if(row.length==1){
        if(!validRow(row)){
            return
        }
        var id=row[0]["id"];
        if(id==null||id==""||id==undefined){
            id=null;
        }
        $.ajax({
            url: CONTEXT_PATH +'/ticketParts/addOrUpdateTicketParts',
            type: 'post',
            traditional: true,
            dataType: 'json',
            data: {"id":id,"ticketId":ticketId,"type":row[0]["type"],"name":row[0]["name"],"nums":row[0]["nums"],"price":row[0]["price"]},
            success: function (data) {
                console.log(data)
                if (data.status == "success") {
                    alert(data.message);
                    $('#reportTable').bootstrapTable("refresh");
                }
            },
            error: function () {
                alert(data.message);
            }
        })
    }else{
        alert("请选中一行")
    }
}
//新增

/*
function insert() {
    //绑定附件
    $('#button').click(function () {
        $('#fjTable').bootstrapTable('insertRow', {
            index: 0,
            row: {
                YGFJ_YG_ID: '',
                YGFJ_NAME: '',
                YGFJ_STATE: '',
                YGFJ_BZ: ''
            }
        });
    });
    //新增
    $('#getTableData').click(function () {
        let dataList = JSON.parse(JSON.stringify($('#fjTable').bootstrapTable('getData')));
        let data = [];
        //新增数组
        let addData = [];
        for (var i = 0; i < dataList.length; i++) {
            if (dataList[i].YGFJ_ID == null) {
                //新增数组填充
                data.push(dataList[i]);
            }
        }
        $.ajax({
            url: '/YGJFJ/AddFj',
            type: 'post',
            traditional: true,
            dataType: 'json',
            data: { data: JSON.stringify(data), status: 'add' },
            success: function (data) {
                if (data == "success") {
                    alert("新增成功！");
                    $('#fjTable').bootstrapTable("refresh");
                }
            },
            error: function () {
                alert("新增失败！");

            }
        })
    });
}
//修改
function update(index) {
    let dataList = JSON.parse(JSON.stringify($('#fjTable').bootstrapTable('getData')));
    let result = [];
    for (var i = 0; i < dataList.length; i++) {
        if (dataList[i].YGFJ_ID != null) {
            //修改数组填充
            result.push({ "YGFJ_ID": dataList[i].YGFJ_ID, "YGFJ_YG_ID": dataList[i].YGFJ_YG_ID, "YGFJ_NAME": dataList[i].YGFJ_NAME, "YGFJ_STATE": dataList[i].YGFJ_STATE, "YGFJ_BZ": dataList[i].YGFJ_BZ });
        }
    }
    let data = result[index];//根据下表获取当前修改的第几行的数据
    $.ajax({
        url: '/YGJFJ/AddFj',
        type: 'POST',
        traditional: true,
        data: { data: JSON.stringify(data), status: 'update' },
        success: function (data) {
            if (data == "success") {
                alert("修改成功！");
                $('#fjTable').bootstrapTable("refresh");
            }

        }, error: function () {
            alert("修改成功！");
        }

    });

}
//删除
function deleteD(id) {
    if (confirm('是否确认删除？')) {
        $.ajax({
            url: '/YGJFJ/AddFj',
            type: 'POST',
            traditional: true,
            data: { data: JSON.stringify(id), status: 'delete' },
            success: function (data) {
                if (data=="success") {
                    alert("删除成功！");
                    $('#fjTable').bootstrapTable("refresh");
                }

            }, error: function () {
                alert("删除失败！");
            }

        });
    }
}

//绑定表格



function Binding() {
    $('#fjTable').bootstrapTable('destroy');
    $('#fjTable').bootstrapTable({
        fitColumns: true,
        toolbar: '#toolbarFj',
        striped: true,
        pageSize: 5,
        //clickToSelect:true,
        pageList: [5, 10, 15, 20],
        pagination: true,
        sortable: true,
        dataType: "json",
        dataField: "data",
        totalField: "totals",
        sidePagination: 'server',
        pageNumber: 1,
        detailView: false,
        method: "Get",
        url: CONTEXT_PATH+'/ticketParts/getTicketPartsList?'+Math.random(),
        columns: [[
            {
                field: "type", title: "类型", width: 150,
                editable: {
                    type: "select",
                    source: [{value:1,text:'水泵类型1'},{value:2,text:'水泵类型2'}],
                    /!*function () {
                        var result = [];
                        $.ajax({
                            url: '/YGJFJ/GetYG',
                            type: "get",
                            async: false,
                            data: {},
                            success: function (data, status) {
                                $.each(data, function (key, value) {
                                    result.push({value: value.YGJFJ_ID, text: value.YGJFJ_NUM});
                                });
                            }
                        });
                        return result;
                    },*!/
                    placement: 'right',
                    title: "选择类型",           //编辑框的标题
                    disabled: false,           //是否禁用编辑
                    mode: "popup",            //编辑框的模式：支持popup和inline两种模式，默认是popup
                    validate: function (value) { //字段验证
                        if (!$.trim(value)) {
                            return '不能为空';
                        }
                    }
                }  //动态select

            },{
                field: "name", title: "名称", width: 150,
                editable: {
                    type: "select",
                    source: [{value:1,text:'水泵1'},{value:2,text:'水泵2'}],
                    /!*function () {
                        var result = [];
                        $.ajax({
                            url: '/YGJFJ/GetYG',
                            type: "get",
                            async: false,
                            data: {},
                            success: function (data, status) {
                                $.each(data, function (key, value) {
                                    result.push({value: value.YGJFJ_ID, text: value.YGJFJ_NUM});
                                });
                            }
                        });
                        return result;
                    },*!/
                    placement: 'right',
                    title: "选择名称",           //编辑框的标题
                    disabled: false,           //是否禁用编辑
                    mode: "popup",            //编辑框的模式：支持popup和inline两种模式，默认是popup
                    validate: function (value) { //字段验证
                        if (!$.trim(value)) {
                            return '不能为空';
                        }
                    }
                }  //动态select

            },{
                field: "nums",
                title: "数量",
                editable: {
                    type: 'text',
                    title: '数量',
                    validate: function (v) {
                        if (isNaN(v)) return '数量必须是数字';
                        var age = parseInt(v);
                        if (age <= 0) return '数量必须是正整数';
                    }
                }
            }, {
                field: "price",
                title: "单价",
                editable: {
                    type: 'text',
                    title: '单价',
                    validate: function (v) {
                        if (isNaN(v)) return '单价必须是数字';
                        var price = parseFloat(v);
                        if (price <= 0) return '单价必须是正数';
                    }
                }
            },
            {
                field: "Operate", title: "操作", width: 55, formatter: function (value, row, index) {
                    return '<a href="###" onclick="update(\'' + index + '\')">编辑</a> &nbsp; &nbsp;<a href="###" onclick="deleteD(\'' + row.YGFJ_ID + '\')">删除</a>';
                }
            },
        ]],

    });
}
*/

/*

let $table = $('#table');
let $button = $('#button');
let $getTableData = $('#getTableData');

$button.click(function() {

    var count = $button.bootstrapTable('getData').length;
    $button.bootstrapTable('insertRow',{index:count,row: {
            name: '1',
            type: '11',
            nums: '111',
            price: '1111'
        }});
});

$table.bootstrapTable({
    url: CONTEXT_PATH+'/ticketParts/getTicketPartsList?'+Math.random(),
    toolbar: '#toolbar',
    clickEdit: true,
    showToggle: true,
    pagination: true,       //显示分页条
    showColumns: true,
    showPaginationSwitch: true,     //显示切换分页按钮
    showRefresh: true,      //显示刷新按钮
    //clickToSelect: true,  //点击row选中radio或CheckBox
    columns: [{
        checkbox: true
    }, {
        field: 'type',
        title: '类型'
    }, {
        field: 'name',
        title: '名称'
    }, {
        field: 'nums',
        title: '数量'
    }, {
        field: 'price',
        title: '单价'
    },],
    /!**
     * @param {点击列的 field 名称} field
     * @param {点击列的 value 值} value
     * @param {点击列的整行数据} row
     * @param {td 元素} $element
     *!/
    onClickCell: function(field, value, row, $element) {
        $element.attr('contenteditable', true);
        $element.blur(function() {
            let index = $element.parent().data('index');
            let tdValue = $element.html();

            saveData(index, field, tdValue);
        })
    }
});

$getTableData.click(function() {
    alert(JSON.stringify($table.bootstrapTable('getData')));
});

function saveData(index, field, value) {
    $table.bootstrapTable('updateCell', {
        index: index,       //行索引
        field: field,       //列名
        value: value        //cell值
    })
}*/
