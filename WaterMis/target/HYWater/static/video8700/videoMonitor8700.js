$('#globMB').show()
var liveIp = "";
var liveLength = 9;
var isCkplayer = false;
var monitorIpList = [];
var monitorIp="";
var maxH = ($('body').height()-105)/3;

$.post(CONTEXT_PATH + "/sysConfig/getSysConfigByKey/VIDEO_IP", function (result) {
    liveIp = result;
    getVideoType()
});
function getVideoType(){
    $.post(CONTEXT_PATH + "/sysConfig/getSysConfigByKey/VIDEO_TYPE", function (result) {
        if(result == 'chplayer') {
            $('#videTiele').hide();
            isCkplayer = true
            setTimeout(function () {
                LOADING.hide();
                doCkplayer()
            },1200)
        }
        if(result != 'chplayer') {
            isCkplayer = false;
            $('#chPlayerCtr').hide();
            doHikang()
        }
    });
}


function chengeLiveNum(self, liveNum) {
    if(liveLength == liveNum)return false;
    LOADING.show();
    $('#chPlayerCtr span').css({'background':'#ccc', 'color':'#1d2120'})
    $(self).css({'background':'#1d2120', 'color':'#fff'})
    $('#videos').html('');
    liveLength = liveNum;
    creatVideo()
    if(liveNum == 1) {
        monitorIp = monitorIpList[monitorIpList.length-1];
        showLive(monitorIp, 'video')
    }
    if(liveNum>=9){
        monitorIpList.map(function (v, index) {
            showLive(v, 'video'+index)
        })
    }
    LOADING.hide();
}

function doCkplayer() {
    creatVideo();
    var childrenNodes =""
    try {
        var parentNode =  ztreeBulid.zTreeObj.getNodeByParam('id', GLOBAL_SELECT_DEVICE['pId'])
        childrenNodes = parentNode.children;
    } catch (e) {
    }
    childrenNodes.map(function (v, index) {
        monitorIpList.push(v['monitorIp']);
        showLive(v['monitorIp'], 'video'+index)
    });
}
function creatVideo() {
    if(liveLength>=9){
        for(var i=0; i< liveLength; i++ ){
            $('#videos').append(' <div class="win_container" id="video'+i+'" style="float: left;margin:1px;background-color:black;width: 33%; height: '+maxH+'px;min-height: 200px;min-width: 300px" name="ocx_1">\n' +
                ' </div>')
        }
    }
    if(liveLength == 1){
        $('#videos').append(' <div class="win_container" id="video" style="float: left;margin:5px 0px;background-color:black;width: 100%; height: '+(maxH*3)+'px;min-height: 200px;min-width: 300px" name="ocx_1">\n' +
            ' </div>')
    }
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
function doHikang(){
    var videoSize = "9";
    var videoMap = new Map();
    var PATH = CONTEXT_PATH;
    $(function () {
        $("#spv").hide();
        $("#videTiele").hide();
        $('input[type=radio][name=ivuRadioGroup_1527558698200_1]').change(function() {
            if (videoSize != this.value) {
                videoSize = this.value;
                SetWnd(videoSize);
            }
        });
        $("#closeVideo").click(function() {
            stopAllPreview();
        });

        $("#fullScreen").click(function() {
            SetFullScreen();
        });

        var height = $(window).height() - $(".top-navbar").height() - $(".query-parameter").height();
        $("#spv").height(height-12);

        LOADING.hide();
        if ("IE" != getBrowserType()) {
            $("#videTiele").hide();
            var height = $(window).height() - $(".top-navbar").height();
            $("#preview_1").height(height);
            showErrorMsgVideo('初始化视频播放插件失败！请确认使用IE浏览器并安装最新视频插件。最新视频插件点击右上角【帮助-下载视频控件】');
            return;
        }

        setTimeout(function(){//1秒后跳转
            var childrenNodes = "";
            try {
                var parentNode =  ztreeBulid.zTreeObj.getNodeByParam('id', GLOBAL_SELECT_DEVICE['pId'])
                var childrenNodes = parentNode.children;
            } catch (e) {
            }

            if(!init()){
                return;
            }
            SetLocalParam();
            $("#videTiele").show();
            $("#spv").show();
            LOADING.hide();
            if("" != childrenNodes && undefined != childrenNodes && null != childrenNodes){
                childrenNodes.map(function (v) {
                    addVideo( v['monitorIp']);
                });
            }
        },1000);
    });


    function addVideo(videoUUid) {

        if ("IE" != getBrowserType()) {
            return;
        }
        //videoUUid = "17dce5c88f094a39bb2a6b67852861af";
        if(videoMap.get(videoUUid) ){
            //return;
        }
        videoMap.set(videoUUid,videoUUid);

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
    function init() {
        try {
            var ocxObj = document.getElementById("spv");
            var languageType = 1;
            ocxObj.MPV_Init(languageType);
            SetWnd(videoSize);
            ocxObj.MPV_SetMainToolBar("");
            SetToolBar();
        } catch (e) {
            showErrorMsgVideo('初始化视频播放插件失败！请确认使用IE浏览器并安装最新视频插件。最新视频插件点击右上角【帮助-下载视频控件】');
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
        if (ret != 0) {
            alert("设置本地参数失败");
        }
    }

//停止所有设备
    function stopAllPreview() {
        var ocxObj = document.getElementById("spv");
        var ret = ocxObj.MPV_StopAllPreview();
        if (ret != 0) {
            alert("停止所有预览失败");
        }
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
        spvxOcx.MPV_StartPreview(xml);
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
//点击节点默认调用方法
function callRefresh(obj,pre) {
    if(isCkplayer){
        if(liveLength == 1){
            monitorIpList[0] = obj.monitorIp;
            showLive(obj.monitorIp, 'video');
            return false;
        }
        var _index =  monitorIpList.indexOf(obj.monitorIp);
        if(_index>=0){
            showLive(obj.monitorIp, 'video'+_index);
        }else{
            var num = monitorIpList.length;
            if(num>=9){
                monitorIpList[8] = obj.monitorIp;
                // $('#video8').html('');
                showLive(obj.monitorIp, 'video8');
            }
            if(0<= num <9){
                monitorIpList.push(obj.monitorIp);
                showLive(obj.monitorIp, 'video'+num);
            }
        }

    }else {
        addVideo(obj.monitorIp);
    }
}

