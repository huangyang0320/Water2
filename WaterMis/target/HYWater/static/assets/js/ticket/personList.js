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
	// initTime();
	//默认查询人员列表
	var url = CONTEXT_PATH+"/ticketController/personList?"+Math.random();
    initBootTable(url);
    
    //查询事件
    $("#query").click(function(){
        ischeck = true;
		LOADING.show();
		$('#dataTables-example').bootstrapTable('removeAll');
		$('#dataTables-example').bootstrapTable('refresh');
		//initBootTable(url);
    });

});
    
function initBootTable(url){
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
		sidePagination: 'server',
		pagination: false,//是否分页
		showColumns: true,//列选择按钮
		minimumCountColumns: 2,
		pageList: [20,30,50,100],
		searchOnEnterKey: false,//回车搜索
		clickToSelect: true,
		showRefresh: false,//刷新按钮
		smartDisplay: true,
		showExport: false,
		exportDataType: 'all',
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
		detailView: false, //是否显示详情折叠
		columns: [{
		   field: 'no',
		   title: '工号',
		   align: 'center'
		},{
		   field: 'name',
		   title: '姓名',
		   align: 'center'
		},{
		   field: 'mobile',
		   title: '手机号',
		   align: 'center'
		},{
		   field: 'longi',
		   title: '经度',
		   align: 'center'
		},{
		   field: 'lati',
		   title: '纬度',
		   align: 'center'
		},{
		   field: 'roleName',
		   title: '角色名称',
		   align: 'center'
		},{
		   field: 'officeName',
		   title: '组织名称',
		   align: 'center'
		},{
		   field: 'onlineStatus',
		   title: '在线状态',
		   align: 'center'
		},{
		   field: 'loginName',
		   title: '登录账号',
		   align: 'center'
		}],
		detailFormatter: function(index, row) {// 详情信息
		   var html = [];
		   // html.push('<p class="detail-view">' + '姓名' + ' : ' + toTrim(row.name) + '</p>');
		   return html.join('');
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
	var keyword = jQuery.base64.encode($("#keyword").val());
	var temp = {
		pageSize: params.pageSize,   //页面大小
		pageNumber: params.pageNumber,  //页码
        keyword: keyword,
	};
	exportType='0';
	return temp;
}

function doClean() {

}





