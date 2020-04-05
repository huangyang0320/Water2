var alertType = '0';
var total = 0;
var hideColumns;
var exportType="0";
var alarmInitCount = 0;
var ischeck = false;
$(function(){
	alarmInitCount = 0;
	//初始化地区信息
	qryAreaList();
	//默认查询告警信息
	var url = CONTEXT_PATH+"/device/pumpHouse?'+ Math.random()";
    initBootTable(url);
    
    //查询事件
    $("#query").click(function(){
        ischeck = true;
    	alarmInitCount = 0;
    	LOADING.show();
    	$('#dataTables-example').bootstrapTable('removeAll');
    	$('#dataTables-example').bootstrapTable('refresh');
    	//initBootTable(url);
    	areaCount();
        alarmTypeCount();
    	
    });
    
    $("#areaId").change(function(){
		if('' != $("#areaId").val()){
			qryPumpHouseByAreaId($("#areaId").val());
		}else{
			$("#projectId").empty();
	    	$("#projectId").append("<option value = ''>全部</option>");
		}
    });

    });

    function initBootTable(url){
        // 初始化无滚动条
        $("#page-wrapper").css("overflow-y", "hidden");
        $("#page-inner").css("padding-right", "25px");
   	    // table height
        var calcHeight = function() {
            var height = $(window).height() - $(".top-navbar").height() - $(".panel-heading").height() - 56;
            return height;
        };
        $("#alarm-area-compare, #alarm-type-compare").height(calcHeight()/2 - 5);
        $(window).resize(function() {
            $('#dataTables-example').bootstrapTable('resetView', {height : calcHeight()+55});
            $("#alarm-area-compare, #alarm-type-compare").height(calcHeight()/2 - 5);
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
           onLoadSuccess:function(data){
        	   successHide(1);
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
           columns: [{
               field: 'areaId',
               title: '所属区域',
               align: 'center'
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
               title: '建设方',
               visible:false,
               align: 'center'
           },{
               field: 'constructionSideInformation',
               title: '10.61.98.65',
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

        /* 报警对比 */
        areaCount();
        alarmTypeCount();

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
       exportMustNum:EXPORT_MUST_NUM
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
   function areaCount(){
	   var countObj =  $('#alarm-area-compare');
	   var url = CONTEXT_PATH+"/device/pumpCount/area?"+ Math.random();
	    $("#alermCount").ajaxSubmit( { 
		    type : 'POST',    
		    url : url,    
		    dataType : 'json', 
		    success : function(data){
		    	successHide(1);
		    	var dataMsg = new Array();
		    	for(var i=0;i<data.length;i++){
		    		dataMsg[i] = new Array();
		    		dataMsg[i][0] = data[i].alarmId +"(" + data[i].remarks + ")";
		    		dataMsg[i][1] = parseInt(data[i].remarks);
		    	}
		    	render(countObj,dataMsg,"区域泵房对比","区域泵房");
		    },
		    error : function(data)
	   	    {
		    	render(countObj,[],"区域泵房对比","区域泵房");
		    }
		  });
   }
   
   function alarmTypeCount(){
	   var countObj =  $('#alarm-type-compare');
	   var url = CONTEXT_PATH+"/device/pumpCount/type?"+ Math.random();
       $("#alermCount").ajaxSubmit( {
    	    type : 'POST',    
    	    url : url,    
    	    dataType : 'json', 
    	    success : function(data){
    	    	successHide(1);
    	    	var dataMsg = new Array();
    	    	for(var i=0;i<data.length;i++){
    	    		dataMsg[i] = new Array();
    	    		dataMsg[i][0] = data[i].alarmId +"(" + data[i].remarks + ")";
    	    		dataMsg[i][1] = parseInt(data[i].remarks);
    	    	}
    	    	render(countObj,dataMsg,"设备区位对比","设备区位");
		    },
		    error : function(data)
	   	    {
		    	LOADING.hide();
		    	render(countObj,[],"设备区位对比","设备区位");
		    }
 		  });
   }
   function render(countObj,dataMsg,titleName,remarkName){
	   /* 大区报警对比 */
      countObj.highcharts({
           colors: ['#24CBE5', '#64E572', '#FF9655', '#058DC7', '#50B432', '#ED561B', '#DDDF00', '#FFF263', '#6AF9C4'],
           chart: {
               backgroundColor: {
                   linearGradient: { x1: 0, y1: 0, x2: 1, y2: 1 },
                   stops: [
                       [0, 'rgb(255, 255, 255)'],
                       [1, 'rgb(240, 240, 255)']
                   ]
               },
               borderWidth: 1,
               plotBackgroundColor: 'rgba(255, 255, 255, .9)',
               plotShadow: true,
               plotBorderWidth: 1
           },
           exporting: {
               enabled: false
           },
           title: {
               text: titleName,
               style: {
                   color: '#000',
                   font: 'bold 12px "Trebuchet MS", Verdana, Microsoft YaHei'
               }
           },
           legend: {
               itemStyle: {
                   font: '9pt Trebuchet MS, Verdana, Microsoft YaHei',
                   color: 'black'

               },
               itemHoverStyle: {
                   color: '#039'
               },
               itemHiddenStyle: {
                   color: 'gray'
               }
           },
           labels: {
               style: {
                   color: '#99b'
               }
           },
           tooltip: {
               pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
           },
           navigation: {
               buttonOptions: {
                   theme: {
                       stroke: '#CCCCCC'
                   }
               }
           },
           plotOptions: {
               pie: {
                   allowPointSelect: true,
                   cursor: 'pointer',
                   dataLabels: {
                       enabled: false,
                       format: '<b>{point.name}</b>: {point.percentage:.1f} %'
                   },
                   showInLegend: true
               }
           },
           series: [{
               type: 'pie',
               name: remarkName,
               data: dataMsg
           }],
           credits: {
               enabled: false//不显示highCharts版权信息
           }     
       });

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
	function doClean() {

    }
	function successHide(size){
		alarmInitCount +=size;
		if(alarmInitCount == 3){
			LOADING.hide();
		}
	}