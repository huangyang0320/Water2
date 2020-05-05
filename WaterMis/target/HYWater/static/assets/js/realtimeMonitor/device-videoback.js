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
	OnInit();
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

function OnInit() {
	try {
		var options = {
			divId: "ocx_playback",
			theme: "Red"
		};

		try {
			ocx_playback = new PlaybackOCX(options);
		} catch (e) {}
		try {
			ocx_playback.registerCallback(0x02000006, CallBackSetToken);
		} catch (ex) {

		}
		var previewGlobalXml = '<?xml version="1.0" encoding="utf-8"?>';
		previewGlobalXml += '<global>';
		previewGlobalXml += '<snap picture="jpeg" save="c:/snap"/>';
		previewGlobalXml += '<clip packSize="256" save="c:/cut"/>';
		previewGlobalXml += '<performance decode="5"/>';
		previewGlobalXml += '</global>';
		try {
			ocx_playback.setGlobalParam(previewGlobalXml);
		} catch (ex) {}
	} catch (e) {}

	 var phID = GLOBAL_SELECT_DEVICE['monitorIp'];
//	if ("" != phID) {
//		addVideo(phID);
//	}
    setTimeout(function(){//2秒后跳转  
    	if(-1 == ocx_playback.getSelectedWindow()){
    		showErrorMsgVideo('初始化视频播放插件失败！请确认使用IE浏览器并安装最新视频插件。最新视频插件点击右上角【帮助-下载视频控件】');
    		return;
    	}
        addVideo(phID);
     },2000); 
}

function GetQueryiframe(key) {
	var search = window.location.search.slice(1); //得到get方式提交的查询字符串
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
	} catch (ex) {}
	return "";
}


function addVideo(pumpHouseId) {
	if ("IE" != getBrowserType()) {
		return;
	}
	pumpID = pumpHouseId;
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
	endTime = new Date().format("yyyy-MM-dd hh:mm:ss");
	beginTime = endTime.split(" ")[0] + " 00:00:00";
	OnPlayback();
}

function InitOcx() {
	try {
		var options = {
			divId: "ocx_playback",
			theme: "Red"
		};

		try {
			ocx_playback = new PlaybackOCX(options);
		} catch (e) {}
		try {
			//ocx_playback.registerCallback(0x02000006, CallBackSetToken);
		} catch (ex) {

		}
		var previewGlobalXml = '<?xml version="1.0" encoding="utf-8"?>';
		previewGlobalXml += '<global>';
		previewGlobalXml += '<snap picture="jpeg" save="c:/snap"/>';
		previewGlobalXml += '<clip packSize="256" save="c:/cut"/>';
		previewGlobalXml += '<performance decode="5"/>';
		previewGlobalXml += '</global>';
		try {
			ocx_playback.setGlobalParam(previewGlobalXml);
		} catch (ex) {}
		//设置全局参数----------------------end-------------------
	} catch (e) {

	}
}

function CallBackSetToken(reqId) {
	$.ajax({
		type: 'GET',
		contentType: 'application/json',
		url: PATH + '/hik/getAppKeyByCode/' + pumpID + "?" + new Date().getTime(),
		success: function(data) {
			if (undefined == data || null == data || '' == data) {
				return;
			}
			var token = data;
			var result = ocx_playback.setToken(reqId, token);
			//alert(result);
		}
	});
}


function OnPlayback() {
	$.ajax({
		type: 'POST',
		contentType: 'application/json',
		url: PATH + '/hik/hikBackResouseXML' + "?" + new Date().getTime(),
		data: '{"videoNo":"' + pumpID + '",' + '"beginTime":"' + beginTime + '",' + '"endTime":"' + endTime + '"}',
		success: function(data) {
			if (undefined == data || null == data || '' == data) {
				return;
			}
			var backXml = data;
			var result = ocx_playback.setPlayback(beginTime, endTime, backXml);

		}
	});

}

//点击节点默认调用方法
function callRefresh(obj) {	   
   addVideo(obj.monitorIp);
}