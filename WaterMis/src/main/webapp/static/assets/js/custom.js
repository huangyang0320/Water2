/*
 * main.js
 * import begin at index.html
 * define base logic and function
 */
var alarmMsgFlag = false;
var $pageWrapper = $("#page-wrapper");
var thisUrlIsShowTree = false;
var delObject = "";
(function ($) {

    "use strict";

    /*
     ***********************************************
     页面加载
     ***********************************************
     */

    // 计算page-wrapper的高度
    var calcHeight = function() {
        var top = $(".top-navbar").height();
        $("#page-wrapper").height($(window).height() - top);
    };
    calcHeight();
    $(window).resize(function() {
        calcHeight();
    });

    $(window).load(function(){
        sessionStorage.clear();
        setTreeType(true);
        getTreeType("");
        getLeftMenuData();
    });

    $(document).ajaxError(function(event, jqxhr, settings, thrownError) {
        var userStatus = jqxhr ? jqxhr.getAllResponseHeaders() : null;
        if (userStatus
                && userStatus!== ""
                && userStatus.indexOf("x-user-status: logout") > -1) {
            window.location = ROOT_PATH + "/login.html";
        }
    });

    /*
     ***********************************************
     定义报警弹窗
     ***********************************************
     */
    /*$.dialog.notice = function( options ) {
        var opts = options || {},
            api, aConfig, hide, wrap, top,
            duration = opts.duration || 800;

        var config = {
            id: 'Notice',
            left: '50%',
            top: '100%',
            fixed: true,
            drag: true,
            resize: true,
            init: function(here){
                api = this;
                aConfig = api.config;
                wrap = api.DOM.wrap;
                top = parseInt(wrap[0].style.top);
                hide = top + wrap[0].offsetHeight;

                wrap.css('top', hide + 'px')
                .animate({top: top + 'px'}, duration, function(){
                    opts.init && opts.init.call(api, here);
                });
            },
            close: function(here){
                wrap.animate({top: hide + 'px'}, duration, function(){
                    opts.close && opts.close.call(this, here);
                    aConfig.close = $.noop;
                    api.close();
                });

                return false;
            }
        };

        for(var i in opts)
        {
            if( config[i] === undefined ) config[i] = opts[i];
        }

        return $.dialog( config );
    };*/

    /*
     ***********************************************
     程序入口 $(document).ready(function () {}
     ***********************************************
     */
    $(document).ready(function () {
        // 点击隐藏菜单栏
		$("#sideNav").click(function(){
            var _this = $(this);
			if(_this.hasClass('closed')){
				$('.navbar-side').animate({left: '0px'});
				_this.removeClass('closed');
				$('#page-wrapper').animate({'margin-left' : '180px'}, function(){
                    _this.find("i").attr("class", "fa fa-caret-left");

                    // 调用Highcharts自适应函数
                    highchartsSelfAdaption();
                    // 调用Bootstrap table自适应函数
                    BootstrapTableSelfAdaption();
                });
			}
			else{
			    _this.addClass('closed');
				$('.navbar-side').animate({left: '-180px'});
				$('#page-wrapper').animate({'margin-left' : '0px'}, function(){
                    _this.find("i").attr("class", "fa fa-caret-right");

                    // 调用Highcharts自适应函数
                    highchartsSelfAdaption();
                    // 调用Bootstrap table自适应函数
                    BootstrapTableSelfAdaption();
                });
			}
		});

        /*****************************************************/
        // highcharts自适应
        var highchartsSelfAdaption = function(){
            var chart = $(".highcharts-container").parent();
            if(chart.length > 0){
                $.each(chart, function(i, obj){
                    $(obj).highcharts().reflow();
                });
            }
        };
        // Bootstrap table自适应
        var BootstrapTableSelfAdaption = function(){
            var table = $("#dataTables-example");
            if(table.length > 0){
                table.bootstrapTable("resetView");
            }
        };
        /*****************************************************/
        // show dialog
        $("#showWarnDialog").click(function(){
            $.dialog.notice({
                title: '告警信息列表',
                width: 600,
                height: 500,
                min: false,
                time: 5,
                content: 'url:warn.html'
            });
        });
       /* $("#showWarnDialog").click(function(){
            $(".alarmTips").animate({bottom: "0px"}, 1000, function() {
                $(this).find("iframe").prop("src", "warn.html");
                $(this).find("iframe").attr('id','warnHtml')
            });
        });*/

        //log out
        $("#logout").click(function() {
            LOADING.show();
            $.post(CONTEXT_PATH + "/user/logout", function(result) {
                if (result == "success") {
                    localStorage.clear();
                    window.location = CONTEXT_PATH + "/login.html";
                }
            });
        });
    });

}(jQuery));

function getTreeType(isloading){
    window.treeTypeStatus = false
    $.ajax({
        type: 'GET',
        contentType: 'application/json',
        async: false,
        url: CONTEXT_PATH + "/sys/user/findUserTreeSelection",
        success: function (res) {
            var res = JSON.parse(res)
            if(res && res.treeSelection){
                window.treeTypeStatus = true;
                GLOBAL_TREE_TYPR = res.treeSelection;
                ZtreeInitG(isloading);
            }else {
                console.error('获取树类型失败！')
            }

        },
        error:function () {
            console.error('获取树类型失败！')
        }
    })
}

function closeTips(){
    $(".alarmTips").animate({bottom: "-200px"}, 500);
}

function leftmenu() {
    $('#main-menu').metisMenu();
    // 菜单重载样式
    $(window).bind("load resize", function () {
        // if ($(this).width() < 768) {
        //     $('div.sidebar-collapse').addClass('collapse');
        // } else {
        //     $('div.sidebar-collapse').removeClass('collapse');
        // }
    });
    // 点击菜单条目加载对应页面
    $("#main-menu").find(".nav-second-level").find("a").on("click",function(){
        // 改变选中状态
        $(this).parent().parent().parent().children("a").addClass("a-parent");
        $(this).parent().parent().parent().siblings().children("a").removeClass("a-parent");
        $(this).parent().parent().parent().parent().find(".nav-second-level").find("a").removeClass("a-child");
        $(this).addClass("a-child");

        var URL = $(this).attr("data-location");
        GLOBAL_URL_ADDRESS = URL;
        if(GLOBAL_URL_ADDRESS){
            pageChange();
        }
        return false;
    });
}
function toLoadingPage() {
    var cleanURL = ["systemOverview.html","themeStatNew.html","themeStatNew.html","energyRank.html", "pumpGuide.html", "pumpInfo.html", "alarmQuery.html", "doorAccess.html", "maintenance.html", "historyQuery.html","attendanceStat.html","accessRecord.html"];// 需要隐藏设备列表的页面
    if(GLOBAL_URL_ADDRESS != "" && GLOBAL_URL_ADDRESS != null && GLOBAL_URL_ADDRESS != undefined){
        LOADING.show();
        clean();
        // 删除页面堆积的时间控件元素，并清除对象事件
        if($(".timepicker").length>0){
            $(".timepicker").datetimepicker("remove");
        }

        $("#page-wrapper").load(GLOBAL_URL_ADDRESS, function(){
            if($.inArray(GLOBAL_URL_ADDRESS, cleanURL) > -1){
                $("#deviceMenuRight").hide()
            } else {
            }
        });
    }
}

// SCADA弹窗
function showSCADA(){
    $.dialog({
        id: 'SCADA_Page',
        title: 'SCADA导航',
        content: 'url:/HYWater/scand.html',
        width: 1280,
        height: 615,
        max: true,
        min: true,
        resize: true,
        fixed: true
    });
}

// 告警参数
function showAlert() {
    $('.ui_state_visible').show();
    $('#ldg_lockmask').show();
    var api;
    $.dialog({
        id: 'WarnParameter',
        title: '告警参数修改',
        content: 'url:warnParameter.html',
        width: 400,
        height: 120,
        fixed: true,
        max: false,
        min: false,
        resize: false,
        lock: true,
        background: '#000',
        opacity: 0.65,
        ok: function () {
        	alarmMsgFlag = true;
            api = this;
            var alarmRate = $(window.frames["WarnParameter"].document).find("#alarmRate").val();
            var alarmSubFlag = $(window.frames["WarnParameter"].document).find("#alarmSubFlag").val();
            if('1' == alarmSubFlag){
            	return false;
            }
            $(window.frames["WarnParameter"].document).find("#alarmSubFlag").val('1');
            if("none" != alarmRate){
                var re = /^[1-9]{1}[0-9]{0,4}:(s|m|h)$/;
                if(!re.test(alarmRate)){
                    showErrorAlert(api,"配置参数格式错误");
                    return false;
                }
                if((alarmRate.indexOf("s") > -1) && ((alarmRate.split(":")[0]).length == 1)){
                    showErrorAlert(api,"参数不小于10秒");
                    return false;
                }
            }
            $(window.frames["WarnParameter"].document).find("#form").ajaxSubmit( {
                url:CONTEXT_PATH+'/system/alarmConfig?'+ Math.random(),
                type : 'post',
                dataType : 'json',
                success : function(data){
                   $('.ui_state_visible').hide();
                    $('#ldg_lockmask').hide();
                    GLOBAL.queryLatestALarm.setTime(data.alarmRate);
                    if("0" == data.remarks){
                    	showSuccessMsg(api);
                        return;
                    }
                    showErrorAlert(api,"操作失败，请重试");
                    return false;
                },
                error : function(data)
                {
                    showErrorAlert(api,"操作失败，请重试");
                    return false;
                }
            });
            alarmMsgFlag = false;
            return false;
        },
        cancel: true
    });

    $('.ui_state_visible').append('<iframe  src="about:blank" frameBorder=0 allowtransparency="yes"  marginHeight=0 marginWidth=0 style="overflow: hidden; border-radius: 11px; position:absolute;visibility:inherit; top:0px;left:0px;height:100%;width:100%;z-index:-1;background-color: rgba(0, 0, 0, 0)">\n' +
        '        </iframe>')

}

// 密码重置
function showPassword() {
    var api;
    $.dialog({
        id: 'resetPassword',
        title: '重置密码',
        content: 'url:resetpassword.html',
        width: 320,
        height: 128,
        fixed: true,
        max: false,
        min: false,
        resize: false,
        lock: true,
        background: '#000',
        opacity: 0.65,
        ok: function () {
            api = this;
            var oldPwd = $(window.frames["resetPassword"].document).find("#oldPassword").val();
            var newPwd = $(window.frames["resetPassword"].document).find("#newPassword").val();
            var confirm = $(window.frames["resetPassword"].document).find("#confirmPassword").val();
            if(isEmp(oldPwd) || isEmp(newPwd) || isEmp(confirm)){
                showErrorAlert(api,"参数不可以为空");
                return false;
            }
            if(newPwd != confirm){
                showErrorAlert(api,"新密码和确认密码不相同");
                return false;
            }
            $(window.frames["resetPassword"].document).find("#form").ajaxSubmit( {
                url:CONTEXT_PATH+'/system/modPassword?'+ Math.random(),
                type:'post',
                dataType : 'json',
                success : function(data){
                    if(data.remarks == "0"){
                        showSuccessMsg(api);
                        //setTimeout(function(){window.location="/simple/a";},2000);
                        return;
                    }
                    if(data.remarks == "1"){
                        showErrorAlert(api,"旧密码输入错误");
                        return false;
                    }
                    if(data.remarks == "2"){
                        showErrorAlert(api,"密码格式错误,请重新输入");
                        return false;
                    }
                    showErrorAlert(api,"操作失败，请重试");
                    return false;
                },
                error : function(data)
                {
                    showErrorAlert(api,"操作失败，请重试");
                    return false;
                }
            });
            return false;
        },
        cancel: true
    });
}

function tree(data) {
    if (data["name"]) {
        var region = data;
        var regionNode = $("<li><a href='#'>" + data["name"] + "</a></li>").addClass("area");
        GLOBAL.areaList.push({
            id: data["id"],
            name: data["name"]
        });
        if (region.hasOwnProperty("pumpHouseList") && region["pumpHouseList"][0]["pumpHouseId"]) {
            var pumpHouseListNode = $("<ul class='submenu'></ul>");
            var pumpHouseList = region["pumpHouseList"];

            for (var i = 0; i < pumpHouseList.length; i++) {
                var pumpHouse = pumpHouseList[i];
                var pumpHouseNode = $("<li><a href='#'>" + pumpHouse["pumpHouseName"] + "</a></li>").addClass("house");

                if (pumpHouse.hasOwnProperty("deviceList") && pumpHouse["deviceList"][0]["deviceId"]) {

                    var deviceList = pumpHouse["deviceList"];
                    var deviceListNode = $("<ul class='submenu'></ul>");

                    var devicePumpHouse = pumpHouse;
                    devicePumpHouse["deviceId"] = pumpHouse["deviceList"][0]["deviceId"];
                    devicePumpHouse["regionId"] = data["id"];
                    delete devicePumpHouse["deviceList"];
                    for (var j = 0; j < deviceList.length; j++) {
                        var device = deviceList[j];
                        GLOBAL_DEVICE_LIST.push(device);
                        DEVICE_PUMPHOUSELIST[device["deviceId"]] = pumpHouse["pumpHouseId"];
                        device["pumpHouse"] = devicePumpHouse;

                        var deviceNode = $("<li><a href='javascript:;' onclick='refreshDevice(" + JSON.stringify(device) + ", this)'>"
                            + device["deviceId"] + "<br>" + device["deviceName"] + "</a></li>").addClass("device");
                        deviceListNode.append(deviceNode);
                        pumpHouseNode.append(deviceListNode);

                        var deviceTitle = device["deviceName"] + " - " + device["deviceId"];
                        var $deviceItem = $("<li class='list-group-item'><label title='" + deviceTitle + "'></label></li>");
                        $deviceItem.find("label").append($("<input id='deviceMenu" + device["deviceId"] + "' class='minimal my-checkbox' name='deviceId' type='checkbox' value='" + device["deviceId"] + "' data-device-name='" + device["deviceName"] + "'>"));
                        $deviceItem.find("label").append(deviceTitle);
                        GLOBAL.deviceMenu += $deviceItem.prop("outerHTML");
                    }

                    PUMPHOUSELIST.push(devicePumpHouse);
                }
                pumpHouseListNode.append(pumpHouseNode);
            }
        }
        regionNode.append(pumpHouseListNode);
        $("#demo-list").append(regionNode);
        sessionStorage.setItem("pumpHouseList", JSON.stringify(PUMPHOUSELIST));
        sessionStorage.setItem("deviceList", JSON.stringify(GLOBAL_DEVICE_LIST));
    }
}

// function showAllPumphouses(areaId) {
// 	$.post(parent.CONTEXT_PATH + "/monitor/statistic/pumpOnlineStat?date=" ,null, function (result) {
//     	var pumpHouseList = JSON.parse(sessionStorage.getItem("pumpHouseList"));
//         showPumphouses(pumpHouseList, areaId,result);
//
//     });
// }

function showErrorAlert(api,errorMsg){
	$(window.frames["WarnParameter"].document).find("#alarmSubFlag").val('0');
	$.dialog({icon: 'error.gif', titleIcon: 'lhgcore.gif',title:'提示',width: 200,
        content: errorMsg, max: false, min: false, resize: false,
        lock: true, parent: api, close: function(){}
	});
}
function showSuccessMsg(api){
	$(window.frames["WarnParameter"].document).find("#alarmSubFlag").val('0');
	var errorMsg = "操作成功";
	$.dialog({icon: 'success.gif', titleIcon: 'lhgcore.gif',title:'提示',width: 200,
		content: errorMsg, max: false, min: false, resize: false,
        lock: true, close: function(){

         }
	});
}
function showErrorMsg(errorMsg){
    $.dialog({icon: 'error.gif', titleIcon: 'lhgcore.gif',title:'提示',width: 200,
        content: errorMsg, max: false, min: false, resize: false,
        lock: true, close: function(){}
    });
}

function showErrorMsgVideo(errorMsg){
    $.dialog({titleIcon: 'lhgcore.gif',title:'提示',width: 650,
        content: errorMsg, max: false, min: false, resize: false,
        lock: true, close: function(){}
    });
}


function refreshDevice(arg, obj) {
    GLOBAL_SELECT_DEVICE = arg;
    /*
     * 无论是在【实时数据】页面点击设备列表 还是 在【泵站导航】页面点击柱状图，
     * 都需要更新 GLOBAL_DEVICE_META
     */
    callRefresh(arg, "");
    rightMenuMsg()

}

// 概览页面链接刷新左侧菜单状态
function refreshLeftMenuStatus(content) {
    var _this = "";
    var leftMenu = $("#main-menu").find("ul").find("li").find("a");
    for(var i=0;i<leftMenu.length;i++){
        if(content == $(leftMenu[i]).text()) {
            _this = leftMenu[i];
            break;
        }
    }
    // 修改选中状态
    $(_this).parents("li").siblings().removeClass("active");
    $(_this).parents("li").siblings().children("a").removeClass("a-parent");
    $(_this).parents("li").siblings().children('ul').collapse('hide');
    $(_this).parents("li").siblings().children("ul").find("a").removeClass("a-child");
    $(_this).parent().parent().parent().addClass("active");
    $(_this).parent().parent().parent().children("a").addClass("a-parent");
    $(_this).parent().parent().parent().children('ul').collapse('show');
    $(_this).addClass("a-child");
}

// 没有数据时提示
function nodataMessage(elem, msg, method, type) {
    var $nodataHTML = $("<div></div>");
    var className = "nodata-match";
    if(type != undefined && $.trim(type) != "" && type != null){
        className = className + "-" + type;
    }
    $nodataHTML.addClass(className);
    $nodataHTML.text(msg);
    if(method == "append") {
        $(elem).append($nodataHTML);
    } else {
        $(elem).html($nodataHTML);
    }
}

// 判断对象为空
var isEmptyObject = function(obj) {
    for (var name in obj) {
        return false;
        break;
    }
    return true;
}

function clean() {
    try {
        doClean();
    } catch (e) {
    }
}

/**
 * 配置ztree类型
 */
function ztreeType() {
    setTreeType()
}


/**
 * 用户可配树的类型
 */
function setTreeType(status){
    $.ajax({
        type: 'GET',
        contentType: 'application/json',
        async: false,
        url: CONTEXT_PATH + "/sys/user/findUserTreeSelection",
        success:function(res) {
            var res = JSON.parse(res);
            var defultChoose = res.treeSelection
            var str = "" , check = "";
            if (res && res.dicts) {
                var dataList = res.dicts
                dataList.map(function (val, i) {
                     check = (defultChoose == val.value) ? "checked" : "";
                    if (i <= 0) {
                        GLOBAL_TREE_TYPR = val.value;
                        str += '<div class="radio"><label>' +
                            '<input type="radio" style="width: 13px" name="optionsRadiosTY" id="optionsRadios' + val.value + '" value="' + val.value + '" '+ check +'>' +
                            val.label +
                            '</label></div>'
                    } else {
                        str += '<div class="radio"><label>' +
                            '<input type="radio" style="width: 13px"  name="optionsRadiosTY" id="optionsRadios' + val.value + '" value="' + val.value + '" '+ check +'>' +
                            val.label +
                            '</label></div>'
                    }
                })
            } else {
                str = '<h2>暂无数据</h2>'
            }
            $('#myModalZtreeType').find('.modal-body').html(str);

            if (!status) {
                $('#myModalZtreeType').modal('show')
            }
        }
    })
}

/**
 * 获取左侧树的数据
 */
function getLeftMenuData() {
        GLOBAL_LOGIN_USER = JSON.parse(localStorage.getItem("user"));
        $("#loginUser").text(GLOBAL_LOGIN_USER["name"]);
        if (GLOBAL_LOGIN_USER.hasOwnProperty("alarmRate")) {
            GLOBAL.queryLatestALarm.start(GLOBAL_LOGIN_USER["alarmRate"]);
        } else {
            GLOBAL.queryLatestALarm.start("10:s");
        }


        GLOBAL.IMG_CONTEXT_PATH = GLOBAL_LOGIN_USER["map"] === "baidu" ?
            GLOBAL.IMG_CONTEXT_PATH_BAIDU : GLOBAL.IMG_CONTEXT_PATH_HEFEI;

        // load page-wrapper
        $("#page-wrapper").load("themeStatNew.html", function(){
            $("#deviceMenuRight").hide()
        });


        // load right menu
        $("#right-menu-list").load("rightmenu.html", function(){
            // initProjectTree();
            // 隐藏menu
            $("#hideMenu").click(function(){
                $(".right-menu").animate({top: '-580px'}, function(){
                    $(this).hide();
                    $(".showRightMenu").fadeIn();
                });
            });
            // 显示menu ----无用代码 走yxn-header-menu.js里走的
            $(".showRightMenu").click(function(){
                $(this).fadeOut();
                $(".right-menu").animate({top: '65px'}).show();
            });

            $.get(ROOT_PATH + "/static/assets/js/leftmenu-" + GLOBAL_LOGIN_USER["map"] + ".json", function (result) {
                $(".sidebar-collapse").load("leftmenu.html", function() {
                    var $mainMenu, $li, $subMenu, $subMenuLi;
                    var menu, subMenu, subMenuItem;

                    $mainMenu = $("#main-menu");

                    if (result && result.length > 0) {
                        for (var i = 0; i < result.length; i++) {
                            menu = result[i];
                            if(menu.hasOwnProperty("enabled") && menu["enabled"] == "false"){
                                continue;
                            }
                            $li = $("<li></li>");
                            $li.append($("<a href=':;'></a>"));
                            $li.find("a").append($("<i class='fa'></i>").addClass(menu["icon"]));
                            $li.find("a").append(menu["title"]);
                            $li.find("a").append($("<span class='fa arrow'></span>"));

                            if (menu.hasOwnProperty("subMenu")) {
                                subMenu = menu["subMenu"];
                                if (subMenu && subMenu.length > 0) {
                                    $subMenu = $("<ul class='nav nav-second-level'><ul>");
                                    for (var j = 0; j < subMenu.length; j++) {
                                        subMenuItem = subMenu[j];
                                        if (subMenuItem.hasOwnProperty("enabled") && subMenuItem["enabled"] == "false") {
                                            continue;
                                        }
                                        $subMenuLi = $("<li></li>");
                                        $subMenuLi.append($("<a href='javascript:;'></a>"));
                                        if (subMenuItem["url"]) {
                                            $subMenuLi.find("a").attr("data-location", subMenuItem["url"] + ".html");
                                        } else {
                                            $subMenuLi.find("a").attr("onclick", subMenuItem["event"] + "()");
                                        }
                                        $subMenuLi.find("a").append($("<span class='fa fa-caret-right'></span>"));
                                        $subMenuLi.find("a").append(subMenuItem["title"]);
                                        $subMenu.append($subMenuLi);
                                    }
                                    $li.append($subMenu);
                                }

                            }
                            $mainMenu.append($li);
                        }
                        $mainMenu.find("li").eq(0).addClass("active");
                        $mainMenu.find("a").eq(0).addClass("a-parent");
                        $mainMenu.find("li").eq(0).find("li").eq(0).find("a").addClass("a-child");
                        leftmenu();
                        clearInterval(SYSTEM_LOADING);
                        $(".percentBar").text("100%");
                        setTimeout(function () {
                            $(".cover-container").fadeOut();
                        }, 500);
                    }
                });
            });
        });
}

/**
 * 根据类型渲染树
 */
function renderZtree() {
    LOADING.show();
    GLOBAL_TREE_TYPR = $('#myModalZtreeType input:radio:checked').val();
    $.ajax({
        type: 'GET',
        contentType: 'application/json',
        async: false,
        url: CONTEXT_PATH + "/sys/user/updateUserDefaultTree?treeSelection="+ GLOBAL_TREE_TYPR +"&" + new Date().getTime(),
        success: function (res) {
            var res = JSON.parse(res)
           if(res.status == 0){
               ZtreeInitG(true);
               $('#myModalZtreeType').modal('hide')
           }else{
        	   //$('#myModalZtreeType').modal('hide');
               console.error('修改失败， 请稍后再试！')
           }
            if(res.status == -2){
            	$('#myModalZtreeType').modal('hide')
        		showErrorMsgVideo(res.msg);
            	LOADING.hide()
            }
        }
    })
}

function UninitSpvx() {
	 try {
		 var ocxObj = document.getElementById("spv");
	    if(undefined != ocxObj && null != ocxObj){
	    	ocxObj.MPV_Uninit();
	    }
   } catch (e) {}

   try {
		 var ocxObj = document.getElementById("spb");
	    if(undefined != ocxObj && null != ocxObj){
	    	ocxObj.MPB_Uninit();
	    }
   } catch (e) {}
}
/**
*  页面切换处理树
*/
function pageChange(isJsOpen) {
	UninitSpvx();
    /* 藏右侧树 */
    $("#deviceMenuRight").hide()
    LOADING.show()
    var isJsOpen = isJsOpen || false;
    /* 显示的树 */
    if(isInArray(_page_url_show_list, GLOBAL_URL_ADDRESS)){
        window.ztreeIsRed = true;
        window.treeShowAfterLoaded = true;
        thisUrlIsShowTree = true;
        // 显示复选框
        if(isInArray(_page_url_check_list, GLOBAL_URL_ADDRESS)){
            ztreeBulid.checkBoxSH(true)
        }else{
            ztreeBulid.checkBoxSH(false)
        }

        /* 泵房树 */
        if(isInArray(_page_url_pump_list, GLOBAL_URL_ADDRESS)){
            if(GLOBAL_TREE_PUMP_EQT){
                GLOBAL_TREE_PUMP_EQT =  false
                ZtreeInitG();
            }
        }else{
            /* 设备树 */
            if(isInArray(_page_url_equipment_list, GLOBAL_URL_ADDRESS)){
                if( !GLOBAL_TREE_PUMP_EQT || GLOBAL_TREE_VIDEO_EQT != 1){
                    GLOBAL_TREE_PUMP_EQT =  true
                    GLOBAL_TREE_VIDEO_EQT =  1
                    ZtreeInitG();
                }
            }

            /* 视频树 */
            if(isInArray(_page_url_video_list, GLOBAL_URL_ADDRESS)){
                if(!GLOBAL_TREE_PUMP_EQT || GLOBAL_TREE_VIDEO_EQT != 2){
                    GLOBAL_TREE_PUMP_EQT = true
                    GLOBAL_TREE_VIDEO_EQT = 2
                    ZtreeInitG();
                    $('#right-menu-list').find('.content').append('<iframe id="iframebar" src="about:blank" frameBorder=0  marginHeight=0 marginWidth=0 style="border-radius: 11px; position:absolute;visibility:inherit; top:0px;left:0px;height:100%;width:100%;z-index:-1;background-color: rgba(0, 0, 0, 0.2);filter="progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)""></iframe>')
                }
            }
        }
        if( ztreeBulid.isShowCheck != ztreeBulid.setting.check.enable && window.ztreeIsRed){
            ZtreeInitG();
        }
        // 显示树
        $("#deviceMenuRight").fadeIn();
        if(! isJsOpen){
            toLoadingPage() // 跳转内嵌页面
        }
        rightMenuMsg()
    }else{
        thisUrlIsShowTree = false
        toLoadingPage();// 跳转内嵌页面
    }
}

/**
 * 初始化右侧树
 * @constructor
 */
function ZtreeInitG(relaoding){
    $("#keyTree").val("") // 清空树搜索
    $("#selectedDevice").hide();
    window.treeShowAfterLoaded = false;
    window.ztreeIsRed = false;
    var  relaoding = "" || relaoding;
    if(!window.treeTypeStatus){
        getTreeType(relaoding)
    }
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        async: false,
        url: CONTEXT_PATH + '/treeType/getTreeDataByType' + "?" + new Date().getTime(),
        dataType: 'JSON',
        data: JSON.stringify({
            treeTypeValue:GLOBAL_TREE_TYPR,
            isSpread: GLOBAL_TREE_PUMP_EQT,
            isDeviceOrVideo: GLOBAL_TREE_VIDEO_EQT,
        }),
        success: function (res) {
            GLOBAL_REGION_LIST = res
            window.treeShowAfterLoaded = true;
            ztreeBulid.setOption({
                "zNodes":GLOBAL_REGION_LIST,
                "setting": {
                    check: {
                        enable: ztreeBulid.isShowCheck ,
                    },
                }
            })
            if(relaoding && thisUrlIsShowTree){
                GLOBAL_SELECT_LIST_CHO0SE =[];
                defChoose() // 默认选择的节点；
                rightMenuMsg()
            }
            ztreeBulid.init();// 重新渲染树
            rightMenuMsg();
            LOADING.hide();
        },
        error:function () {
            LOADING.hide();
            window.treeShowAfterLoaded = true;
        }
    })
}

/**
 * 展示选择的设备或泵房
 *
 */

// 无用  在yxn-header-menu.js中调用
function rightMenuMsg() {
    if(ztreeBulid.isShowCheck && GLOBAL_SELECT_LIST[0]){
        var rMM =  GLOBAL_SELECT_LIST[0].title
    }else{
        if(GLOBAL_SELECT_DEVICE) var rMM =  GLOBAL_SELECT_DEVICE.title
    }
    if(rMM) {
        rMM = rMM.replace('：', "");
        rMM = rMM.replace('/', "—");
    }
    $('#rightMenuMsg').html(rMM);

    // 页面内导航
    let massage = "", areaName = "", pumpHouseName = "", deviceName = "", objdevice;
    if(ztreeBulid.isShowCheck && GLOBAL_SELECT_LIST[0]) {
        massage = "当前选中第一套设备";
        objdevice = GLOBAL_SELECT_LIST[0];
        deviceName = objdevice.title
    }
    if(!ztreeBulid.isShowCheck && GLOBAL_SELECT_DEVICE){
        massage = "当前设备";
        objdevice = GLOBAL_SELECT_DEVICE;
        deviceName = objdevice.title;
    }
    if(objdevice){
        if(objdevice['pId']){
            let nodepump = ztreeBulid.zTreeObj.getNodeByParam('id', objdevice['pId']);//获取id为1的点
            let pumpnamestr = nodepump['name'];
            pumpHouseName = $(pumpnamestr).find('.name').html();
            if($(pumpnamestr).find('.name').html()== undefined){
                pumpHouseName =  nodepump['name'];
            }

            if(nodepump['pId']){
                let nodearea = ztreeBulid.zTreeObj.getNodeByParam('id', nodepump['pId']);//获取id为1的点
                areaName = nodearea['name'];
            }
        }
        $("#selectedDevice").text(massage + ": " + areaName + "/" + pumpHouseName + "/" + deviceName).show();
    }
}


/**
 * 使用循环的方式判断一个元素是否存在于一个数组中
 * @param {Object} arr 数组
 * @param {Object} value 元素值
 */
function isInArray(arr,value){
    for(var i = 0; i < arr.length; i++){
        if(value === arr[i]){
            return true;
        }
    }
    return false;
}


function openAlertModel(id, data) {
    $('#'+id).find('.modal-body').html(data)
    $('#'+id).modal('show');
}

function openAlarmConfirm(id,delObj) {
	delObject = delObj;
	var configMsg = {
            "id": id
        };
        $.post(CONTEXT_PATH + '/alarmStatController/getAlarmById',configMsg, function(data) {
        	$("#confirmReason").val('');
        	$("#confirmId").val(data.id);
        	$("#confirmPhName").val(undefined == data.phName? '':data.phName);
        	$("#confirmDevName").val(undefined == data.deviceName? '':data.deviceName);
        	$("#confirmType").val(undefined == data.alarmType? '':data.alarmType);
        	$("#confirmDate").val(undefined == data.alarmTime? '':data.alarmTime);
        	$("#confirmRemark").html(undefined == data.alarDescription? '':data.alarDescription);
        });
    $('#alarmConfirm').modal('show');
}

function alarmIgnore(id,delObj) {
	var configMsg = {
        "id": id,
        "alarmType": "2"   //1：确认，2：忽略
    };
    $.post(CONTEXT_PATH + '/alarmStatController/indexConfig',configMsg, function(data) {
    	$.post(CONTEXT_PATH + "/alarmStatController/indexAlarmSize", function (result) {
            if (!isNaN(result) && Number(result) >= 0) {
                $("#showWarnDialog").find("span").text(result);
            }else{
            	$("#showWarnDialog").find("span").text('');
            }
        });
    	// $(delObj).parent().parent().remove();
        // showErrorMsgVideo("忽略成功");
    	//openAlertModel("myModalAlert" , "忽略成功")
    });
}

function alarmConfirm() {
	var confirmReason = jQuery.base64.encode($("#confirmReason").val());
	var configMsg = {
            "id": $("#confirmId").val(),
            "alarmType": "1",   //1：确认，2：忽略
            "confirmReson": confirmReason
        };
	$.post(parent.CONTEXT_PATH + '/alarmStatController/indexConfig',configMsg, function(data) {
		$('#alarmConfirm').modal('hide');
		$.post(CONTEXT_PATH + "/alarmStatController/indexAlarmSize", function (result) {
            if (!isNaN(result) && Number(result) > 0) {
                $("#showWarnDialog").find("span").text(result);
            }
            else{
            	$("#showWarnDialog").find("span").text('');
            }
        });
		$(delObject).parent().parent().remove();
        openAlertModel("myModalAlert" , "确认成功");
    });
}

function ConfirmModal(tit, cont, func, fc) {
    let title = tit || "";
    let content = cont || "";
    let fun = func || "" ;
    let fuc= fc || "" ;
    let target =  $('#ConfirmModal');
    target.find('.modal-title').html(title);
    target.find('.modal-body').html(content);
    $('#ConfirmBtn').attr('onclick', func + '()')
    $('#cancelBtn').attr('onclick', fuc + '()')
    target.modal('show')
}

function myModalProDeaL(p, d) {
    var pArr = p.split(';')
    var dArr = d.split(';')
    var str_darr,  str_parr;
    $("#myModalProDeaL01").html("");
    $("#myModalProDeaL02").html("");
    pArr.map(function (v) {
        if(v.length>0){
            str_parr = '<div class="radio"><label style="padding: 0px">' + v + '</label></div>'
            $("#myModalProDeaL01").append(str_parr);
        }
    })
    dArr.map(function (v) {
        if(v.length>0){
            str_darr = '<div class="radio"><label style="padding: 0px">' + v + '</label></div>'
            $("#myModalProDeaL02").append(str_darr);
        }
    })
    $('#myModalProDeaL').modal('show');
}



/**
 * heightchart 实例销毁
 * @param callback
 */

function distory() {
    let charts = document.getElementByClassName('thumbnail');获取所有图表
    for(var i=0;i<charts.length;i++){

        var chart = $(chart[i]).highcharts();获取对象

        chart.destory();销毁对象

        chart=null;
    }
}

function pageLoading(callback) {
    let timeThis = setInterval(function () {
        if(window.treeShowAfterLoaded){
            clearInterval(timeThis);
            callback();
            rightMenuMsg()
        }
    },200);
}

function jumpTicket(){
	$.post(CONTEXT_PATH + "/sysConfig/getSysConfigByKey/TICKET_WEB_URL", function (result) {
		window.open(result+"/redictLogin/"+GLOBAL_LOGIN_USER.loginName+"/"+GLOBAL_LOGIN_USER.password);
    });
}

function jumpMgSystem(){
    $.post(CONTEXT_PATH + "/sysConfig/getSysConfigByKey/MG_SYSTEM_URL", function (result) {
        alert(2);
        window.open("http://localhost:8080/HFWater/login.html");
    });
}

function formValidator() {
    $('#workOrder').bootstrapValidator({
        message: '不能为空',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            alarmContent: {
                validators: {
                    notEmpty: {
                        message: '工单内容不能为空'
                    }
                }
            },
            alarmLevel: {
                validators: {
                    notEmpty: {
                        message: '告警等级不能为空'
                    }
                }
            }
        }
    });
}

function myModalWorkOrder(row) {
    queryAlarmWorkTemplate();
    queryMaintenanceWorkerUser();
    $("#alarmContent").val(row.phName+'发生了'+row.alarmInfo);
    $("#alarmTime").val(row.startDate);
    $("#processName").val(row.deviceName);
    $("#processObject").val(row.deviceId);
    $("#alarmReason").val(row.alarmReason);
    $("#alarmLevel").val(row.alarmLevel);
    $("#planContent").val(row.alarDescription);
    $("#taskSource").val("二供");

    $("#planStartTime").datetimepicker({
        format: 'yyyy-mm-dd hh:ii:ss',
        minuteStep:1,
        minView:1,
        language: 'zh-CN',
        pickerPosition:'bottom-right',
        autoclose:true,
        startDate: new Date()
    }).on("click",function(){
        $("#planStartTime").datetimepicker("setEndDate",$("#planEndTime").val());
    });

    $("#planEndTime").datetimepicker({
        format: 'yyyy-mm-dd hh:ii:ss',
        minuteStep:1,
        minView:'hour',
        language: 'zh-CN',
        autoclose:true
    }).on("click",function(){
        $("#planEndTime").datetimepicker("setStartDate",$("#planStartTime").val());
    });

    var myDate = new Date();
    $("#planStartTime").val(myDate.Format("yyyy-MM-dd HH:mm:ss"));
    myDate.setDate(myDate.getDate() + 7)
    $("#planEndTime").val(myDate.Format("yyyy-MM-dd HH:mm:ss"));

    formValidator();
    //重置验证
    $("#workOrder").data('bootstrapValidator').destroy();
    $('#workOrder').data('bootstrapValidator',null);
    formValidator();
}


function queryAlarmWorkTemplate() {
    var url = CONTEXT_PATH+"/alarmStatController/queryAlarmWorkTemplate?"+ Math.random();
    jQuery.ajax({
        type : 'POST',
        contentType : 'application/json',
        url : url,
        dataType : 'json',
        success : function(data) {
            $("#workType").html("");
            $.each(data, function (i, item) {
                jQuery("#workType").append("<option value="+ item.value+">"+ item.label+"</option>");
            });
        }
    });
}

function queryMaintenanceWorkerUser() {
    var url = CONTEXT_PATH+"/alarmStatController/queryMaintenanceWorkerUser?"+ Math.random();
    jQuery.ajax({
        type : 'POST',
        contentType : 'application/json',
        url : url,
        dataType : 'json',
        success : function(data) {
            $("#executor").html("");
            jQuery("#executor").append("<option>"+ '未选择-默认流程'+"</option>");
            $.each(data, function (i, item) {
                jQuery("#executor").append("<option value="+ item.loginName+">"+ item.name+"</option>");
            });
        }
    });
}

function queryWhetherWorker(deviceId) {
    var url = CONTEXT_PATH+"/alarmStatController/queryWhetherWorker?"+ Math.random();
    jQuery.ajax({
        type : 'POST',
        contentType : 'application/json',
        url : url,
        dataType : 'json',
        data: deviceId,
        success : function(data) {
            if(data.status == "success"){
                $('#myWorkModal').modal('show');
            }else if(data.workNum){
                $('#alertWorkMessage').html('该设备已有'+data.workNum+'个工单是否继续创建工单');
                $('#alertWork').modal('show');
            }else {
                openAlertModel("myModalAlert" , data.message);
            }
        }
    });
}

function refresh() {
    var warnHtml = document.getElementById('warnHtml').contentWindow.getAlarmList();
}

function alertError() {
    $('#alertErrorMessage').html('该告警已经创建工单,无法创建');
    $('#alertErrorMessage').css("color","red");
    $('#alertError').modal('show');
}

function submitWorkOrder(){
    var bootstrapValidator = $("#workOrder").data('bootstrapValidator');
    //手动触发验证
    bootstrapValidator.validate();
    if(bootstrapValidator.isValid()){
        var _url = CONTEXT_PATH+"/alarmStatController/submitWorkOrder?"+ Math.random();
        $("#workOrder").ajaxSubmit( {
            type : 'POST',
            url : _url,
            dataType : 'json',
            success : function(data){
                if(data.status == "success"){
                    $('#myWorkModal').modal('hide'); // 关闭模态框
                    $('#alertErrorMessage').html(data.message);
                    $('#alertError').modal('show');
                }else{
                    $('#alertErrorMessage').html(data.message);
                    $('#alertError').modal('show');
                }
            }
        });
    }

}
