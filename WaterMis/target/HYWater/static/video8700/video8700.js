var liveIp = "";
var isCkplayer = false;
var monitorIp="";
$.post("a/sysConfig/getSysConfigByKey/VIDEO_IP", function (result) {
    liveIp = result;
    getVideoType()
});
function getVideoType(){
    $.post("a/sysConfig/getSysConfigByKey/VIDEO_TYPE", function (result) {
        if(result == 'chplayer') {
            $('#ocx-container').hide();
            isCkplayer = true
            setTimeout(function () {
                doCkplayer()
            },1200)
        }
        if(result != 'chplayer') {
            isCkplayer = false;
            $('#video').hide();
            doHikang()
        }
    });
}

function doCkplayer() {
    var phID = GetQueryiframe("videoNo");
	videoNos = phID.split('_');
    videoNos.map(function (v) {
        showLive(v, 'video')
    });
}

function showLive(_liveId, _videoId) {
    if(_liveId && _videoId){
        var videoObject = {
            container: '#'+_videoId,//“#”代表容器的ID，“.”或“”代表容器的class
            variable: 'player',//该属性必需设置，值等于下面的new chplayer()的对象
            autoplay:true,//自动播放
            live:true,
            video:liveIp+'/mag/hls/'+ _liveId +'/1/live.m3u8'//视频地址
        };
        var player=new chplayer(videoObject);
    }
}

function doHaikang() {
	var cameraIndexCode = '001439';
	var videoName = 'videoName';
	var hikObject = new Object();

	var pumpID = "";
	var ocx;
	var PATH = 'a';
	var operType = '2';
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
			var videoSize = 1;
			if(videoNos.length == 1){
				videoSize = 1;
			}else if(videoNos.length < 5){
				videoSize = 2;
			}else{
				videoSize = 3;
			}

			if(!init(videoSize)){
				return;
			}
			for(var i = 0;i < videoNos.length;i++){
				addVideo(videoNos[i]);
			}

		}else{
			init(1);
		}
	});

	function addVideo(videoUUid) {

		if ("IE" != getBrowserType()) {
			return;
		}
		//videoUUid = "17dce5c88f094a39bb2a6b67852861af";

		$.ajax({
			type: 'GET',
			contentType: 'application/json',
			url: PATH + '/hik/getResouseByCode8700/' + videoUUid + "?" + new Date().getTime(),
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

	//初始化控件
	function init(videoSize) {
		try {
			var ocxObj = document.getElementById("spv");
			var languageType = 1;
			ocxObj.MPV_Init(languageType);
			SetWnd(videoSize);
			ocxObj.MPV_SetMainToolBar("");
			SetToolBar();
			SetLocalParam();
		} catch (e) {
			return false;
		}
		return true;
	}

	//本地化设置
	function SetLocalParam() {
		var ocxObj = document.getElementById("spv");
		var devPxRa = screen.deviceXDPI / screen.logicalXDPI;
		var height = $('#spv').height() * devPxRa ;
		var width = $('#spv').width() * devPxRa;
		var xml = '<?xml version="1.0" encoding="UTF-8"?> ' +
			'<localParam> ' +
			'<width>' + width + '</width> ' +
			'<height>' + height + '</height> ' +
			'<picType>0</picType> ' +
			'<capturePath>C:\\Hikvision</capturePath> ' +
			'<recordSize>2</recordSize> ' +
			'<recordPath>C:\\Hikvision</recordPath> ' +
			'<limitPreviewTime>1800</limitPreviewTime> ' +
			'<showMsgTip>1</showMsgTip> ' +
			'</localParam>';
		var ret = ocxObj.MPV_SetLocalParam(xml);
	}

	//停止所有设备
	function stopAllPreview() {
		var ocxObj = document.getElementById("spv");
		var ret = ocxObj.MPV_StopAllPreview();
	}

	//获取窗口数
	function GetWnd(){
		var ocxObj = document.getElementById("spv");
		return ocxObj.MPV_GetPlayWndCount();
	}

	//设置窗口数
	function SetWnd(opt){
		var ocxObj = document.getElementById("spv");
		ocxObj.MPV_SetPlayWndCount(parseInt(opt, 10));
	}


	//自定义播放工具条按钮
	function SetToolBar(){
		var ocxObj = document.getElementById("spv");
		ocxObj.MPV_SetToolBar("0,1,2,3,4,5,6,7,8,9,10,11,12,13,14");
	}

	//自定义主工具栏按钮
	function SetMainToolBar(){
		var ocxObj = document.getElementById("spv");
		ocxObj.MPV_SetMainToolBar("0,1,2,3,4,5,6");
	}

	//设置全屏
	function SetFullScreen(){
		var ocxObj = document.getElementById("spv");
		ocxObj.MPV_SetFullScreen();
	}

	//开始预览
	function StartPreview(xml){
		 // 调OCX单路预览接口
		var spvxOcx = document.getElementById("spv");
		if (xml)
		{
			spvxOcx.MPV_StartPreview(xml);
		}
	}

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

	window.onbeforeunload = function(event) {
		try {
			 var ocxObj = document.getElementById("spv");
			if(undefined != ocxObj && null != ocxObj){
				ocxObj.MPV_Uninit();
			}
	   } catch (e) {}
	};
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