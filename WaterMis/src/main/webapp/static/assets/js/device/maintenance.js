var visibleColumns = new Array();
var total = 0;
var hideColumns;
var exportType="0";
var operPage="1";
var changeFlag = "1";
var url ="";
$(function(){
	//默认查询告警信息
	url = CONTEXT_PATH+"/device/deviceRepairList?"+ Math.random();
	$("#query").click(function(){
		LOADING.show();
		if("1" == operPage){
			$('#dataTables-example').bootstrapTable('removeAll');
	    	$('#dataTables-example').bootstrapTable('refresh');
		}else{
			initDetailQry();
		}
    });
	
	$("#mainList").click(function(){
		if("1" == operPage){
			return;
		}
		operPage = "1";
		LOADING.show();
		changeFlag = "2";
		$('#dataTables-example').bootstrapTable('removeAll');
    	$('#dataTables-example').bootstrapTable('refresh');
    	
    });
	$("#detailList").click(function(){
		if("2" == operPage){
			return;
		}
		operPage = "2";
        LOADING.show();
		initDetailQry();
    });
	
    //初始化项目查询
	//初始化地区信息
	qryAreaList();
    LOADING.hide();

	$("#areaId").change(function(){
		if('' != $("#areaId").val()){
			qryProjectList($("#areaId").val());
		}else{
			$("#project").empty();
		}
	});

	$("#project").change(function(){
		if('' != $("#project").val()){
			getPumpList($("#project").val());
		}else{
			$("#phId").empty();
		}
	});
});



function initDetailQry(){
	var projectId = $("#project").val();
	var userName = $("#userName").val();
    if(isEmp(userName)){
    	userName = "-1";
    }else{
    	userName = jQuery.base64.encode(userName);
    }
    
    if($(".nodata-match")) {
        $(".nodata-match").remove();
    }
	$("#vertical-timeline").html("");
	jQuery.ajax( { 
 	    type : 'POST',  
 	    contentType : 'application/json',  
 	    url : CONTEXT_PATH+'/device/getRepairListDeatil/'+projectId+'/'+userName+'?'+ Math.random(),    
 	    dataType : 'json', 
 	    success : function(data){
 	    	LOADING.hide();
 	    	if(data.length < 1){
                nodataMessage($("#maintenance-timeline"), "没有匹配到当前项目的维保记录.", "append");
	        }else{
	        	var detailHtml = "";
                $.each(data, function(i, item) {
                    detailHtml += '<div class="vertical-timeline-block"><div class="vertical-timeline-icon navy-bg"><i class="fa fa-briefcase"></i></div>';
                    detailHtml += '<div class="vertical-timeline-content"><h2>'+item.deviceId+'</h2>';
                    detailHtml += '<p>' + item.repairContent + '</p>';
                    detailHtml += '<span class="vertical-date"><small>维保人 :'+item.userName+'&nbsp;&nbsp;&nbsp;&nbsp;'+item.repairDate+'</small>';
                    detailHtml += '</span></div></div>';
                });
                $("#vertical-timeline").html(detailHtml);
	        }
 	    	initBootTable(url);
	    },
	    error : function(data)
   	    {
            nodataMessage($("#maintenance-timeline"), "系统异常, 请稍后再试！", "append");
	    	LOADING.hide();
	    }
	 });
}

function initBootTable(url){
	// 初始化无滚动条
    $("#page-wrapper").css("overflow-y", "hidden");
    $("#page-inner").css("padding-right", "25px");
	// table height
    var calcHeight = function() {
        var height = $(window).height() - $(".top-navbar").height() - $(".panel-heading").height() - 55;
        return height;
    };
    $("#maintenance-timeline").height(calcHeight() - 55);
    $(window).resize(function() {
        $('#dataTables-example').bootstrapTable('resetView', {height : calcHeight()});
        $("#maintenance-timeline").height(calcHeight() - 55);
    });
	console.log(url);

	 $('#dataTables-example').bootstrapTable({
     	url:url,
		 //url:"",
     	cache:false,
     	striped:true,
         height:calcHeight(),//设定高度，固定头部
         search: false,//是否搜索
         queryParamsType:'',
         queryParams:queryParams,
         pageSize:20,
         pageNumber:1,
         sidePagination:'server',
         pagination: true,//是否分页
         pageList:[20,30,50,100],
         showExport:true,
         exportDataType:'all',
         searchOnEnterKey: false,//回车搜索
         onLoadSuccess:function(data){
        	 if("2" == changeFlag){
        		 $('#dataTables-example').bootstrapTable('refresh');
        	 }
        	 changeFlag = "1";
        	 total = data.total;
             LOADING.hide();
         },
         onLoadError:function(data){
        	 changeFlag = "1";
        	 LOADING.hide();
         },
         clickToSelect:true,
         showRefresh: false,//刷新按钮
         showColumns:true,//列选择按钮
         columns: [{
        	 field: 'repairDate',
             title: '维修时间',
             align: 'center'
         },{
             field: 'projectId',
             title: '所属项目',		
             align: 'center'
         },{
        	 field: 'pumpHouseId',
        	 title: '所属泵房',
        	 visible:false,
        	 align: 'center'
         },{
             field: 'deviceId',
             title: '所属设备',
             align: 'center'
         },{
             field: 'repairContent',
             title: '维保内容',
             align: 'center'
         },{
        	 field: 'faultReason',
        	 title: '故障原因',
        	 align: 'center'
         },{
             field: 'userName',
             title: '维修人员',
             align: 'center'
         },{
             field: 'userId',
             title: '员工编号',
             visible:false,
             align: 'center'
         },{
        	 field: 'accessoriesXh',
        	 title: '故障配件',
        	 visible:false,
        	 align: 'center'
         },{
        	 field: 'solution',
        	 title: '解决方法',
        	 visible:false,
        	 align: 'center'
         },{
        	 field: 'createtime',
        	 title: '入库时间',
        	 visible:false,
        	 align: 'center'
        	 },{
        	 field: 'updatetime',
        	 title: '更新时间',
        	 visible:false,
        	 align: 'center'
        	 },{
        	 field: 'memo',
        	 title: '备注',
        	 visible:false,
        	 align: 'center'
         }],
         minimumCountColumns:2,
         smartDisplay:true,
         locale: "zh-CN",//中文支持
         detailView: true, //是否显示详情折叠
         detailFormatter: function(index, row) {// 详情信息
             var html = [];
             html.push('<p class="detail-view">' + '维修时间' + ' : ' + row.repairDate + '</p>');
             html.push('<p class="detail-view">' + '所属项目' + ' : ' + toTrim(row.projectId) + '</p>');
             html.push('<p class="detail-view">' + '所属泵房' + ' : ' + toTrim(row.pumpHouseId) + '</p>');
             html.push('<p class="detail-view">' + '所属设备' + ' : ' + toTrim(row.deviceId) + '</p>');
             html.push('<p class="detail-view">' + '员工编号' + ' : ' + toTrim(row.userId) + '</p>');
             html.push('<p class="detail-view">' + '维修人员' + ' : ' + toTrim(row.userName) + '</p>');
             html.push('<p class="detail-view">' + '维保内容' + ' : ' + toTrim(row.repairContent) + '</p>');
             html.push('<p class="detail-view">' + '故障原因' + ' : ' + toTrim(row.faultReason) + '</p>');
             html.push('<p class="detail-view">' + '解决方法' + ' : ' + toTrim(row.solution) + '</p>');
             html.push('<p class="detail-view">' + '入库时间' + ' : ' + toTrim(row.createtime) + '</p>');
             html.push('<p class="detail-view">' + '更新时间' + ' : ' + toTrim(row.updatetime) + '</p>');
             html.push('<p class="detail-view">' + '备注' + ' : ' + toTrim(row.memo) + '</p>');
             
             return html.join('');
         }
     });

     LOADING.hide();
}

function qryAreaList(){
	$("#areaId").empty();
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
				$.each(data, function(i, item) {

					$("#areaId").append("<option value = '" + item.deviceId + "'> "+item.deviceName  + "</option>");
				})


				qryProjectList($("#areaId").val());
			}
		},
		error : function(data)
		{
		}
	});
}



function qryProjectList(areaId){
	$("#project").empty();
	jQuery.ajax( {
		type : 'POST',
		contentType : 'application/json',
		url : CONTEXT_PATH+'/device/getProjectByAreaId/' + areaId+'?'+ Math.random(),
		dataType : 'json',
		success : function(data){
			if(undefined == data){
				return;
			}else{
				$("#project").empty();
				$.each(data, function(i, item) {
					$("#project").append("<option value = '" + item.id + "'> "+item.deviceName  + "</option>");
				})
				getPumpList($("#project").val());
			}



			initBootTable(url);
		},
		error : function(data)
		{
		}
	});

	function getPumpList(project){
		var url = CONTEXT_PATH+"/ticket/getPumpList?"+ Math.random();
		jQuery.ajax({
			type : 'POST',
			contentType : 'application/x-www-form-urlencoded',
			url : url,
			data: {projectId:project},
			dataType : 'json',
			success : function(data) {
				$("#phId").html("");
				for (var i = 0; i < data.length; i++) {
					jQuery("#phId").append("<option value=" + data[i].id + ">" + data[i].name + "</option>");
				}
			}
		});
	}





	/*jQuery.ajax( {
 	    type : 'POST',  
 	    contentType : 'application/json',  
 	    url : CONTEXT_PATH+'/device/getProjectByRole?'+ Math.random(),    
 	    dataType : 'json', 
 	    success : function(data){
 	    	if(undefined == data){
 		          return;
	        }else{
				$("#project").empty();
	          $.each(data, function(i, item) {
	        	  $("#project").append("<option value = '" + item.projectId + "'> "+item.projectName  + "</option>");
	        	})
	        }
 	    	initBootTable(url);
	    },
	    error : function(data)
   	    {
	    	
	    }
	 });*/
}
function queryParams(params) {
/*	if(ischeck){
		params.pageNumber = 1;
		ischeck = false;
	}
	alertType = '1';
	//配置参数
	var name = jQuery.base64.encode($("#userName").val());
	var temp = {
		pageSize: params.pageSize,   //页面大小
		pageNumber: params.pageNumber,  //页码
		areaId: $("#areaId").val(),
		projectId: $("#project").val(),
		name: name,
		exportMustNum:EXPORT_MUST_NUM
	};
	exportType='0';
	return temp;*/




	//配置参数
    var userName = $("#userName").val();
    if(isEmp(userName)){
    	userName = "";
    }else{
    	userName = jQuery.base64.encode(userName);
    }
    var temp = {     
      pageSize: params.pageSize,   //页面大小  
      pageNumber: params.pageNumber,  //页码  
      projectId: $("#project").val(),
	  phId:$("#phId").val(),
	  userName:userName,
      exportType:exportType,
      exportMustNum:EXPORT_MUST_NUM
	};
	exportType='0';
    return temp;
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