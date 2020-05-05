var ocx_playback;
var PATH = CONTEXT_PATH;
var operType = '2';
var beginTime = "";
var endTime = "";
var pumpID = "";
var hikObject = new Object();
pageLoading(initThisPagel);
function initThisPagel() {
	LOADING.hide();
	if ("IE" != getBrowserType()) {
		showErrorMsgVideo('初始化视频播放插件失败！请确认使用IE浏览器并安装最新视频插件。最新视频插件点击右上角【帮助-下载视频控件】');
		return;
	}
	InitSpb();
	var height = $(window).height() - $(".top-navbar").height();
	$("#ocx_playback").height(height);
};

$(window).resize(function () {
	var height = $(window).height() - $(".top-navbar").height();
	$("#ocx_playback").height(height);
});

function getBrowserType() {
	var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
	var isOpera = userAgent.indexOf("Opera") > -1;
	if (isOpera) {
		return "Opera"
	}; //判断是否Opera浏览器
	if (userAgent.indexOf("Firefox") > -1) {
		return "FF";
	} //判断是否Firefox浏览器
	if (userAgent.indexOf("Chrome") > -1) {
		return "Chrome";
	}
	if (userAgent.indexOf("Safari") > -1) {
		return "Safari";
	} //判断是否Safari浏览器
	if (userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera) {
		return "IE";
	}; //判断是否IE浏览器
	if (!!window.ActiveXObject || "ActiveXObject" in window) {
		// 判断是否是IE11
		return "IE";
	}
	if (appName.indexOf('MSIE') > -1) {
		return "IE";
	}
	return "";
}


//点击节点默认调用方法
function callRefresh(obj,pre) {
	 addVideo(obj.monitorIp);
}


function addVideo(videoUUid) {
	
	if ("IE" != getBrowserType()) {
		return;
	}
	//videoUUid = "17dce5c88f094a39bb2a6b67852861af";
	
	$.ajax({
		type: 'GET',
		contentType: 'application/json',
		url: PATH + '/hik/getBackResouseByCode8700/' + videoUUid + "?" + new Date().getTime(),
		success: function(data) {
			if (undefined == data || null == data || '' == data) {
				return;
			}
			try{
				StartPreview(data);	
			}catch(e){
				return;
			}
		}
	});
}

//开始预览
function StartPreview(xml){
	Date.prototype.format = function(format) {
		var args = {
			"M+": this.getMonth() + 1,
			"d+": this.getDate(),
			"h+": this.getHours(),
			"m+": this.getMinutes(),
			"s+": this.getSeconds(),
			"q+": Math.floor((this.getMonth() + 3) / 3), //quarter
			"S": this.getMilliseconds()
		};
		if (/(y+)/.test(format))
			format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
		for (var i in args) {
			var n = args[i];
			if (new RegExp("(" + i + ")").test(format))
				format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? n : ("00" + n).substr(("" + n).length));
		}
		return format;
	};
	var endTime = new Date().format("yyyy-MM-dd hh:mm:ss");
	var startTime = endTime.split(" ")[0] + " 00:00:00";
	 // 调OCX单路预览接口
	var spbOcx = document.getElementById("spb");
	spbOcx.MPB_StartPlayBack(xml, startTime, endTime);
}

window.onbeforeunload = function(event) {
	UninitSpb();
};

function InitSpb() {
	var ocxObj = document.getElementById("spb");
    var languageType = 1;
    var ret = ocxObj.MPB_Init(languageType);
    SetLocalParam();
    
}

function SetLocalParam() {
    var ocxObj = document.getElementById("spb");
    var xml = '<?xml version="1.0" encoding="UTF-8"?> ' +
        '<localParam> ' +
        '<picType>1</picType> ' +
        '<capturePath>C:\\Hikvision</capturePath> ' +
        '<recordSize>2</recordSize> ' +
        '<recordPath>C:\\Hikvision</recordPath> ' +
		'<showMsgTip>1</showMsgTip> ' +
        '</localParam>';
    var ret = ocxObj.MPB_SetLocalParam(xml);
    ocxObj.MPB_SetPlayWndCount(parseInt(1, 10));
}

function stopAllPlayback() {
    var ocxObj = document.getElementById("spb");
    var ret = ocxObj.MPB_StopAllPlayback();
}

function SetToolBar(){
	var ocxObj = document.getElementById("spb");
	var ids = $('#tbar').val();
	
	var ret = ocxObj.MPB_SetToolBar(ids);
}

function GetWnd(){
	var ocxObj = document.getElementById("spb");
	var ret = ocxObj.MPB_GetPlayWndCount();
}


function SnapPic(){
	var ocxObj = document.getElementById("spb");
	var ret = ocxObj.MPB_SnapShot(-1);
}