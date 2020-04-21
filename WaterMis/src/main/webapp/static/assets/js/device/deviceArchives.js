pageLoading(initThis);
function initThis() {
	$("input[readonly=readonly]").each(function(){
		this.value = "";
	});
	var deviceId = GLOBAL_SELECT_DEVICE.deviceId;
	// 初始化无滚动条
	$("#page-wrapper").css("overflow-y", "hidden");
	$("#page-inner").css("padding-right", "25px");
	// 计算高度
	var calcHeight = function() {
		var height = $(window).height() - $(".top-navbar").height() - 24;
		$(".devicefiles-container").height(height);
		$(".table-scroll").height($(window).height() - 300);
	};
	calcHeight();
	$(window).resize(function() {
		calcHeight();
	});

	// 展示当前所选设备
    //

	// 设备基本信息/项目详细信息
	$("#device-program-info").find("span").on(
			"click",
			function() {
				var _this = $(this);
				var i = _this.index();
				_this.addClass("span-active").siblings().removeClass(
						"span-active");
				$(".showInfo").find("li").eq(i).removeClass("hide-table")
						.siblings().addClass("hide-table");
			});

	// 设备配件信息
	$("#device-fitting").find("span").on(
			"click",
			function() {
				var _this = $(this);
				var i = _this.index();
				_this.addClass("span-active").siblings().removeClass(
						"span-active");
				$(".showInfo2").find("li").eq(i).removeClass("hide-table")
						.siblings().addClass("hide-table");
			});
	$("#e_table").html("");
	$("#a_table").html("");
	$("#p_table").html("");
	if (undefined == deviceId || "" == deviceId || null == deviceId) {
		showErrorMsg("请选择一个设备");
		return false;
	}
	initData(deviceId);
}
function callRefresh(device) {

	LOADING.show();
	var deviceId = device.deviceId;
	$("#e_table").html("");
	$("#a_table").html("");
	$("#p_table").html("");
	initData(deviceId);
	initAccessories(deviceId);
}
function defChoose (){

	LOADING.show();
	var deviceId = GLOBAL_SELECT_DEVICE['deviceId'];
	$("#e_table").html("");
	$("#a_table").html("");
	$("#p_table").html("");
	initData(deviceId);
	initAccessories(deviceId);

}
function initData(deviceId) {
	$("input[readonly=readonly]").each(function(){
		this.value = "";
	});
	$("#projectMemo").val("");
	jQuery.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : CONTEXT_PATH+'/device/deviceArchive/' + deviceId+'?'+ Math.random(),
		dataType : 'json',
		success : function(data) {
			//加载配件信息
			initAccessories(deviceId);
			// 设备信息
			$("#deviceId_d").val(data.id);
			$("#deviceName").val(data.name);
			$("#dateManufacture").val(dateFormatEmp(data.dateManufacture));
			$("#datePurchase").val(dateFormatEmp(data.datePurchase));
			$("#purchaseAmount").val(data.purchaseAmount);
			$("#createtime").val(dateFormatEmp(data.createtime));
			$("#updatetime").val(dateFormatEmp(data.updatetime));
			// 项目信息
			if(undefined != data.pumpList){
				$("#pumpName").val(data.pumpList[0].pumpHouseName);
				$("#pumpAddress").val(data.pumpList[0].pumpHouseAddress);
				$("#deviceManufacturers").val(data.pumpList[0].deviceManufacturers);
				$("#deviceManufacturersInformation").val(data.pumpList[0].deviceManufacturersInformation);
				$("#selfControlManufacturers").val(data.pumpList[0].selfControlManufacturers);
				$("#selfControlManufacturersInformation").val(data.pumpList[0].selfControlManufacturersInformation);
				$("#constructionSide").val(data.pumpList[0].constructionSide);
				$("#constructionSideInformation").val(data.pumpList[0].constructionSideInformation);
				$("#construction").val(data.pumpList[0].construction);
				$("#constructionInformation").val(data.pumpList[0].constructionInformation);
				$("#property").val(data.pumpList[0].property);
				$("#propertyInformation").val(data.pumpList[0].propertyInformation);
				$("#cellName").val(data.pumpList[0].cellAdress);
				$("#cellAdress").val(data.pumpList[0].cellName);
				$("#handoverTime").val(data.pumpList[0].handoverTime);
			}

			// 项目信息
			if(undefined != data.projectList){
				$("#projectId").val(data.projectList[0].projectId);
				$("#projectName").val(data.projectList[0].projectName);
				$("#projectManager")
						.val(data.projectList[0].projectManager);
				$("#projectAddress")
						.val(data.projectList[0].projectAddress);
				$("#projectMemo").val(data.projectList[0].projectMemo);
				$("#salesman").val(data.projectList[0].salesman);
				$("#contractType").val(data.projectList[0].contractType);
				$("#buyerName").val(data.projectList[0].buyerName);
				$("#buyerContactsName").val(
						data.projectList[0].buyerContactsName);
				$("#signDate").val(dateFormatEmp(data.projectList[0].signDate));
				$("#arrivalDate").val(dateFormatEmp(data.projectList[0].arrivalDate));
				$("#yjtsDate").val(dateFormatEmp(data.projectList[0].yjtsDate));
				$("#htqdDw").val(data.projectList[0].htqdDw);
			}
			// 成套标牌
			if(undefined != data.setLabels){
				$("#equipmentModelNo_S").val(
						data.setLabels[0].equipmentModelNo);
				$("#deviceId_e").val(data.setLabels[0].deviceId);
				$("#pupmpModelNo").val(data.setLabels[0].pupmpModelNo);
				$("#sbedgsll_S").val(data.setLabels[0].sbedgsll);
				$("#powerRating").val(data.setLabels[0].powerRating);
				$("#wlgGg").val(data.setLabels[0].wlgGg);
				$("#shapgeSize").val(data.setLabels[0].shapgeSize);
				$("#llkzq").val(data.setLabels[0].llkzq);
				$("#qygGg").val(data.setLabels[0].qygGg);
				$("#sxGg").val(data.setLabels[0].sxGg);
				$("#weight").val(data.setLabels[0].weight);
				$("#environmentalTemperature").val(
						data.setLabels[0].environmentalTemperature);
				$("#pumpQuantity").val(data.setLabels[0].pumpQuantity);
				$("#cabinetModelNo").val(data.setLabels[0].cabinetModelNo);
				$("#powerVoltage").val(data.setLabels[0].powerVoltage);
				$("#sbedgsyc_S").val(data.setLabels[0].sbedgsyc);
				$("#totalPower").val(data.setLabels[0].totalPower);
				$("#zyzzGg").val(data.setLabels[0].zyzzGg);
				$("#scbcq").val(data.setLabels[0].scbcq);
				$("#exFactoryDate").val(data.setLabels[0].exFactoryTime);
				$("#fullDeviceXh").val(data.setLabels[0].fullDeviceXh);
				$("#znYinshui").val(data.setLabels[0].znYinshui);
				$("#fbXinghao").val(data.setLabels[0].fbXinghao);
				$("#qtGuige").val(data.setLabels[0].qtGuige);
				$("#slKongzhi").val(data.setLabels[0].slKongzhi);
				$("#wyBuchang").val(data.setLabels[0].wyBuchang);
			}
			// 电器标牌
			if(undefined != data.electricalLabels){
				$("#deviceId_a").val(data.electricalLabels[0].deviceId);
				$("#modelNo").val(data.electricalLabels[0].modelNo);
				$("#ratedVoltage").val(
						data.electricalLabels[0].ratedVoltage);
				$("#ratedPower_A").val(data.electricalLabels[0].ratedPower);
				$("#standards").val(data.electricalLabels[0].standards);
				$("#controlQuantity").val(
						data.electricalLabels[0].controlQuantity);
				$("#ratedCurrent").val(
						data.electricalLabels[0].ratedCurrent);
				$("#ratedFrequency").val(
						data.electricalLabels[0].ratedFrequency);
			}
			// 水泵标牌
			if(undefined != data.pumpLabels){
				$("#modelNo_p").val(data.pumpLabels[0].modelNo);
				$("#sbedgsll_p").val(data.pumpLabels[0].sbedgsll);
				$("#ratedPower_p").val(data.pumpLabels[0].ratedPower);
				$("#sbedgsyc_p").val(data.pumpLabels[0].sbedgsyc);
				$("#speed_p").val(data.pumpLabels[0].speed);
			}
		},
		error : function(data) {
			showErrorMsg("操作失败，请稍候重试");
		}
	});
}
function initAccessories(deviceId) {
	$("#e_table").html("");
	$("#p_table").html("");
	$("#a_table").html("");

	jQuery.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : CONTEXT_PATH+'/device/accessories/' + deviceId+'?'+ Math.random(),
		dataType : 'json',
		success : function(data) {
			$("#e_table").html("");
			$("#p_table").html("");
			$("#a_table").html("");
			for (var i = 0; i < data.length; i++) {
				if (1 == data[i].accessoriesType) {
					$("#e_table").append(
							"<tr><td class='td-second td-first'>"
									+ data[i].accessoriesName
									+ "</td><td class='td-second td-first'>"
									+ data[i].accessoriesNo
									+ "</td><td class='td-second td-first'>"
									+ data[i].countNum + "</td></tr>");
				} else if (2 == data[i].accessoriesType) {
					$("#a_table").append(
							"<tr><td class='td-second td-first'>"
									+ data[i].accessoriesName
									+ "</td><td class='td-second td-first'>"
									+ data[i].accessoriesNo
									+ "</td><td class='td-second td-first'>"
									+ data[i].countNum + "</td></tr>");
				} else if (3 == data[i].accessoriesType) {
					$("#p_table").append(
							"<tr><td class='td-second td-first'>"
									+ data[i].accessoriesName
									+ "</td><td class='td-second td-first'>"
									+ data[i].accessoriesNo
									+ "</td><td class='td-second td-first'>"
									+ data[i].countNum + "</td></tr>");
				}
			}
			LOADING.hide();
		},
		error : function() {
			LOADING.hide();
		}
	});
}