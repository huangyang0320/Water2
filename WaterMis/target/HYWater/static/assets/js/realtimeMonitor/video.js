var cameraIndexCode = '001439';
var videoName = 'videoName';
var hikObject = new Object();

var pumpID = "";
var ocx;
var PATH = 'a';
var operType = '2';
var videoSize = 1;
var videoNos = new Array();
var curVideoIndex = 0;
$(function() {
	if ("IE" != getBrowserType()) {
		return;
	}
	$(window).resize(function(e) {
		window.setTimeout(function() {
			$("#preview_1").height($(window).height());
		}, 200);
	});

	var phID = GetQueryiframe("videoNo");
	if (undefined != phID && null != phID && '' != phID) {
		videoNos = phID.split('_');
		if(videoNos.length == 1){
			OnInit(1);	
		}else if(videoNos.length < 5){
			OnInit(2);
		}else{
			OnInit(3);
		}
		addVideo(videoNos[curVideoIndex]);	
	}else{
		OnInit(1);
	}
});

function getBrowserType() {
	var userAgent = navigator.userAgent; // 取得浏览器的userAgent字符串
	var isOpera = userAgent.indexOf("Opera") > -1;
	if (isOpera) {
		return "Opera"
	}
	; // 判断是否Opera浏览器
	if (userAgent.indexOf("Firefox") > -1) {
		return "FF";
	} // 判断是否Firefox浏览器
	if (userAgent.indexOf("Chrome") > -1) {
		return "Chrome";
	}
	if (userAgent.indexOf("Safari") > -1) {
		return "Safari";
	} // 判断是否Safari浏览器
	if (userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1
			&& !isOpera) {
		return "IE";
	}
	; // 判断是否IE浏览器
	if (!!window.ActiveXObject || "ActiveXObject" in window) {
		// 判断是否是IE11
		return "IE";
	}
	if (appName.indexOf('MSIE') > -1) {
		return "IE";
	}
	return "";
}

function addVideo(pumpHouseId) {
	if ("IE" != getBrowserType()) {
		return;
	}
	pumpID = pumpHouseId;
	$.ajax({
		type : 'GET',
		contentType : 'application/json',
		url : PATH + '/hik/getResouseByCode/' + pumpHouseId + "?"
				+ new Date().getTime(),
		success : function(data) {
			if (undefined == data || null == data || '' == data) {
				if(videoNos.length > curVideoIndex + 1){
					curVideoIndex ++;
					addVideo(videoNos[curVideoIndex]);
				}
				return;
			}
			OnStartPreview(data);
		},
		error : function(data){
			if(videoNos.length > curVideoIndex + 1){
				curVideoIndex ++;
				addVideo(videoNos[curVideoIndex]);
			}
		}
	});
}

function OnInit(videoSize) {
	$("#preview_1").height($(window).html());
	$("#divVideoName").text(videoName);
	if (!IsNullOrEmpty(cameraIndexCode)) {
		try {
			top.$.dialog.tips('参数有误', 1, 'tips.gif');
		} catch (e) {

		}
		return false;
	}
	if (ocx != null && ocx != undefined) {
		// return false;
	}
	var optionsLive = {
		divId : "preview_1",
		theme : 'Blue'
	}
	try {
		ocx = new PreviewOCX(optionsLive);
	} catch (e) {
	}

	/*
	 * try { if (ocx != undefined && ocx != null) {
	 * ocx.registerCallback(PREVIEW_EVENT_STOP, OnStopPreview); } } catch (ex) {
	 *  }
	 */

	// 设置全局参数----------------------start-------------------
	var previewGlobalXml = '<?xml version="1.0" encoding="utf-8"?>';
	previewGlobalXml += '<clip packSize="256" save="c:/cut"/>';
	previewGlobalXml += '<global>';
	previewGlobalXml += '<snap picture="jpeg" save="c:/snap"/>';
	previewGlobalXml += '<performance decode="5"/>';
	previewGlobalXml += '</global>';
	try {
		if (ocx != undefined && ocx != null) {
			ocx.setGlobalParam(previewGlobalXml);
		}
	} catch (ex) {

	}
	// 设置全局参数----------------------end-------------------
	// 设置操作权限----------------------start-------------------
	/*
	 * Tool_type_INFO = 0, //监控点信息 Tool_type_CONFIG = 1, //设置 没有使用
	 * Tool_type_ALARM = 2, //告警 没有使用 Tool_type_LABEL = 3, //标签 Tool_type_TALK =
	 * 4, //设备对讲 Tool_type_SOUND = 5, //打开声音 Tool_type_STREAM = 6, //主子码流切换
	 * Tool_type_ZOOM = 7, //电子放大 Tool_type_PLAYBACK = 8, //即时回放 Tool_type_PTZ =
	 * 9, //云镜控制 Tool_type_RECORD = 10 , //紧急录像 Tool_type_SAVE = 11, //即时回放保存
	 * Tool_type_SNAPSHOT = 12, //抓图 Tool_type_Close = 13, //关闭
	 * Tool_type_3D_ZOOM = 22 //3D放大
	 * 
	 */
	var previewOperAuthXml = '<?xml version="1.0" encoding="utf-8"?>';
	previewOperAuthXml += '<Privileges>';
	previewOperAuthXml += '< Privilege type="0" />'; // <!--type是按钮类型
														// 只填入需要显示的按钮，顺序按照XML解析顺序
														// -->
	previewOperAuthXml += '< Privilege type="1" />';
	previewOperAuthXml += '< Privilege type="2" />';
	previewOperAuthXml += '< Privilege type="3" />';
	previewOperAuthXml += '< Privilege type="4" />';
	previewOperAuthXml += '< Privilege type="5" />';
	previewOperAuthXml += '< Privilege type="6" />';
	previewOperAuthXml += '< Privilege type="7" />';
	previewOperAuthXml += '< Privilege type="8" />';
	previewOperAuthXml += '< Privilege type="9" />';
	previewOperAuthXml += '< Privilege type="10" />';
	previewOperAuthXml += '< Privilege type="11" />';
	previewOperAuthXml += '< Privilege type="12" />';
	previewOperAuthXml += '< Privilege type="13" />';
	previewOperAuthXml += '< Privilege type="22" />';
	previewOperAuthXml += '</ Privileges >';
	try {
		if (ocx != undefined && ocx != null) {
			var operAuth = ocx.setOperAuth(previewOperAuthXml);
		}
	} catch (ex) {
	}
	// 设置操作权限----------------------end-------------------
	try {
		ocx.registerCallback(0x0200000E, CallBackSetToken)
	} catch (ex) {

	}
	window.setTimeout(function() {
		try {
			ocx.setWindowsLayout(videoSize);
		} catch (e) {
		}
	}, 200);
}

// 开始预览----------------------start-------------------
function OnStartPreview(previewOcxOptionsXml) {
	try {
		if (ocx == undefined || ocx == null) {
			OnInit(1);
		} else {
			try {
				if (ocx == undefined || ocx == null) {
					OnInit(1);
				}
				
				var wndIdx = ocx.getSelectWindow();
				if (wndIdx < 9) {
					ocx.setSelectWindow(wndIdx + 1);
				}

				window.setTimeout(function() {
					try {
						ocx.startPreview(wndIdx, previewOcxOptionsXml);
					} catch (e) {
					}
				}, 200);
			} catch (ex) {

			}
		}
	} catch (ex) {
	}
}

function CallBackSetToken(reqId) {
	$.ajax({
		type : 'GET',
		contentType : 'application/json',
		url : PATH + '/hik/getAppKeyByCode/' + pumpID + "?"
				+ new Date().getTime(),
		success : function(data) {
			if (undefined == data || null == data || '' == data) {
				return;
			}
			ocx.setToken(reqId, data);
			
			if(videoNos.length > curVideoIndex + 1){
				curVideoIndex ++;
				addVideo(videoNos[curVideoIndex]);
			}
		},
		error : function(data){
			if(videoNos.length > curVideoIndex + 1){
				curVideoIndex ++;
				addVideo(videoNos[curVideoIndex]);
			}
		}
	});
}

// 开始预览----------------------end-------------------
// 停止预览
function OnStopPreview() {
	$("#btnStopView").attr("disabled", "disabled");
	try {
		if (ocx != null && ocx != undefined) {
			var wndIdx = ocx.getSelectWindow();
			ocx.stopPreview(wndIdx);
		}
	} catch (e) {

	}
}

/* 新页面打开视频 */
function OpenWinVideoPage() {
	if (IsNullOrEmpty(cameraIndexCode) == false) {
		try {
			top.$.dialog.tips('参数有误', 1, 'tips.gif');
		} catch (e) {

		}
		return false;
	}
	var controlTips = "";
	try {
		var objVideo = new ActiveXObject("SinglePreview.IPreviewCtrl.1");
	} catch (e) {

	}
	if (controlTips != "") {
		try {
			$.dialog.tips(controlTips, 1, 'tips.gif');
		} catch (ex) {

		}
		openVideoTip(2);
		return false;
	}
	var url = RootPath() + "/LiveMonitoring/WinOpenVideo2.html?CamIndexCode="
			+ cameraIndexCode + "&VideoName=" + escape(videoName);
	window.open(url);
}

function openVideoTip(videoVersion) {
	var controlTips = "";
	if (videoVersion == "2") {
		controlTips = "未检测到视频控件,请确认是否使用IE浏览器和是否已经安装!若未安装请点击页面顶部<span style='color:red;'>【海康视频插件下载】</span>安装后使用该功能!若已经安装旧版请先卸载后安装!";
		controlTips += "<a href='http://http://www.shwpgdc.com:801/WebCtrl_V4.0.exe' target='_blank' style='color:red;'>或点击此处下载后安装!若已经安装旧版请先卸载后安装!</a>";
	} else if (videoVersion == "1") {
		controlTips = "未检测到视频控件,请确认是否使用IE浏览器和是否已经安装!若未安装请点击页面顶部<span style='color:red;'>【天地伟业视频插件下载】</span>安装后使用该功能!若已经安装旧版请先卸载后安装!";
		controlTips += "<a href='http://http://www.shwpgdc.com:801/VideoLib.EXE' target='_blank' style='color:red;'>或点击此处下载后安装!若已经安装旧版请先卸载后安装!</a>";
	}
	try {
		$.dialog({
			id : "ZTControl",
			title : "系统提示",
			content : controlTips,
			width : 200,
			height : 100,
			left : '100%',
			top : '100%',
			fixed : true,
			drag : false,
			resize : false,
			max : false,
			min : false,
			time : 50
		});
	} catch (e) {
		try {
			top.$.dialog({
				id : "ZTControl",
				title : "系统提示",
				content : controlTips,
				width : 200,
				height : 100,
				left : '100%',
				top : '100%',
				fixed : true,
				drag : false,
				resize : false,
				max : false,
				min : false,
				time : 50
			});
		} catch (ex) {

		}
	}
}
/*
 * 新窗口打开视频
 */
function OpenWinVideo(projectName, deviveName, camIndexCode, videoName) {
	var winWidth;
	var winHeight;

	winWidth = 500;
	winHeight = 550;

	var url = "/LiveMonitoring/WinOpenVideo2.html?CamIndexCode=" + camIndexCode
			+ "&VideoName=" + escape(videoName) + "&VideoHeight=" + winHeight
			+ "&TitleName="
			+ escape(projectName + "-" + deviveName + "-" + videoName);
	openDialog_max_not_button(url, "VideoLive_" + projectName, projectName
			+ "-" + deviveName, winWidth, winHeight, true, false, false, false,
			true, function(iframe) {

			});
}
// 停止预览
function closeAllVideo() {
	$("#btnStopView").attr("disabled", "disabled");
	try {
		if (ocx != null && ocx != undefined) {
			for (var i = 0; i < 9; i++) {
				ocx.stopPreview(i);
			}
			ocx.setSelectWindow(0);
		}
	} catch (e) {

	}
}

function IsNullOrEmpty(str) {
	var isOK = true;
	if (str == undefined || str == "" || str == 'null' || str == null) {
		isOK = false;
	}
	return isOK;
}

// 点击节点默认调用方法
function callRefresh(obj, pre) {
	addVideo(obj.monitorIp);
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