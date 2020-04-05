(function ($) {

    $.learuntab = {
        requestFullScreen: function () {
            var de = document.documentElement;
            if (de.requestFullscreen) {
                de.requestFullscreen();
            } else if (de.mozRequestFullScreen) {
                de.mozRequestFullScreen();
            } else if (de.webkitRequestFullScreen) {
                de.webkitRequestFullScreen();
            }
        },
        exitFullscreen: function () {
            var de = document;
            if (de.exitFullscreen) {
                de.exitFullscreen();
            } else if (de.mozCancelFullScreen) {
                de.mozCancelFullScreen();
            } else if (de.webkitCancelFullScreen) {
                de.webkitCancelFullScreen();
            }
        },
        refreshTab: function () {
            var currentId = $('.page-tabs-content').find('.active').attr('data-id');
            var target = $('.LRADMS_iframe[data-id="' + currentId + '"]');
            var url = target.attr('src');
            //$.loading(true);
            target.attr('src', url).load(function () {
                cascadeViewCallback(currentId);
            });
        },
        activeTab: function () {
            var currentId = $(this).data('id');
            if (!$(this).hasClass('active')) {
                $('.mainContent .LRADMS_iframe').each(function () {
                    if ($(this).data('id') == currentId) {
                        $(this).show().siblings('.LRADMS_iframe').hide();
                        return false;
                    }
                });
                $(this).addClass('active').siblings('.menuTab').removeClass('active');
                $.learuntab.scrollToTab(this);
                cascadeViewCallback(currentId);
            }
        },
        closeOtherTabs: function () {
            var currentId = $('.page-tabs-content').find('.active').attr('data-id');
            $('.page-tabs-content').children("[data-id]").find('.fa-remove').parents('a').not(".active").each(function () {
                $('.LRADMS_iframe[data-id="' + $(this).data('id') + '"]').remove();
                $(this).remove();
            });
            $('.page-tabs-content').css("margin-left", "0");
            // close otherTabs
            cascadeViewCallback(currentId);
        },
        closeTab: function () {
            var closeTabId = $(this).parents('.menuTab').data('id');
            // var currentId = $(this).parents('.menuTab').prev().data('id');
            var currentWidth = $(this).parents('.menuTab').width();
            if ($(this).parents('.menuTab').hasClass('active')) {
                if ($(this).parents('.menuTab').next('.menuTab').size()) {
                    var activeId = $(this).parents('.menuTab').next('.menuTab:eq(0)').data('id');
                    $(this).parents('.menuTab').next('.menuTab:eq(0)').addClass('active');

                    cascadeViewCallback(activeId);

                    $('.mainContent .LRADMS_iframe').each(function () {
                        if ($(this).data('id') == activeId) {
                            $(this).show().siblings('.LRADMS_iframe').hide();
                            return false;
                        }
                    });
                    var marginLeftVal = parseInt($('.page-tabs-content').css('margin-left'));
                    if (marginLeftVal < 0) {
                        $('.page-tabs-content').animate({
                            marginLeft: (marginLeftVal + currentWidth) + 'px'
                        }, "fast");
                    }
                    $(this).parents('.menuTab').remove();
                    $('.mainContent .LRADMS_iframe').each(function () {
                        if ($(this).data('id') == closeTabId) {
                            $(this).remove();
                            return false;
                        }
                    });
                }
                if ($(this).parents('.menuTab').prev('.menuTab').size()) {
                    var activeId = $(this).parents('.menuTab').prev('.menuTab:last').data('id');
                    $(this).parents('.menuTab').prev('.menuTab:last').addClass('active');

                    cascadeViewCallback(activeId);

                    $('.mainContent .LRADMS_iframe').each(function () {
                        if ($(this).data('id') == activeId) {
                            $(this).show().siblings('.LRADMS_iframe').hide();
                            return false;
                        }
                    });
                    $(this).parents('.menuTab').remove();
                    $('.mainContent .LRADMS_iframe').each(function () {
                        if ($(this).data('id') == closeTabId) {
                            $(this).remove();
                            return false;
                        }
                    });
                }
            }
            else {
                $(this).parents('.menuTab').remove();
                $('.mainContent .LRADMS_iframe').each(function () {
                    if ($(this).data('id') == closeTabId) {
                        $(this).remove();
                        return false;
                    }
                });
                $.learuntab.scrollToTab($('.menuTab.active'));
            }
            return false;
        },
        addTab: function (event) {
            $('.menu-list').removeClass('open');// 点击菜单item隐藏菜单面板
            $(".navbar-custom-menu>ul>li.open").removeClass("open");
            var dataId = $(this).attr('data-id');
            if (dataId != "") {
                //top.$.cookie('nfine_currentmoduleid', dataId, { path: "/" });
            }
            var dataUrl = $(this).attr('data-id');
            var menuName = $.trim($(this).text());
            var flag = true;
            if (dataUrl == undefined || $.trim(dataUrl).length == 0) {
                return false;
            }
            $('.menuTab').each(function () {
                if ($(this).data('id') === dataUrl) {
                    if (!$(this).hasClass('active')) {
                        $(this).addClass('active').siblings('.menuTab').removeClass('active');
                        $.learuntab.scrollToTab(this);
                        $('.mainContent .LRADMS_iframe').each(function () {
                            if ($(this).data('id') === dataUrl) {
                                $(this).show().siblings('.LRADMS_iframe').hide();
                                // if the click event is triggered by js, reload the iframe
                                if (event['isTrigger'] === 3) {
                                    $(this)[0].contentWindow.window.location.reload();
                                }
                                return false;
                            }
                        });
                    }
                    flag = false;

                    //open exist tab
                    cascadeViewCallback(dataUrl);

                    return false;
                }
            });
            if (flag) {
                var $mainContent = $('.mainContent');
                var removeIcon = '';
                if(dataUrl !== "home/home.html" && dataUrl !== "theme/theme.html" && dataUrl !== "theme/wpgtheme.html") {
                    removeIcon = ' <i class="fa fa-remove"></i>';
                }
                var str = '<a href="javascript:;" class="active menuTab" data-id="' + dataUrl + '">' + menuName + removeIcon + '</a>';
                $('.menuTab').removeClass('active');
                var str1 = '<iframe class="LRADMS_iframe" id="iframe' + dataId + '" name="iframe' + dataId + '"  width="100%" height="100%" src="' + dataUrl + GLOBAL.htmlHash + '" frameborder="0" data-id="' + dataUrl + '" data-tree-view="' + $(this).attr('data-tree-view') + '" seamless></iframe>';
                $mainContent.find('iframe.LRADMS_iframe').hide();
                $mainContent.append(str1);
                //$.loading(true);
                cascadeViewCallback(dataId);
                $('.mainContent iframe:visible').load(function () {
                    var iframeWindow = $('.mainContent iframe:visible')[0].contentWindow;
                    iframeWindow.onerror = GLOBAL.errorHandler;
                    var iframe$ = iframeWindow.$;

                    //inject global controll for every iframe
                    // if (iframe$ && !iframe$('#globalScript').length) {
                    //     iframe$('body').append('<script id="globalScript" src="' + GLOBAL.rootPath + '/cloud/assets/ajax/wendu.ajaxhook.min.js"></script>');
                    //     iframe$('body').append('<script src="' + GLOBAL.rootPath + '/cloud/assets/layer/3.0.3/layer.js"></script>');
                    //     iframe$('body').append('<script src="' + GLOBAL.rootPath + '/cloud/index/scripts/global_hook.js"></script>');
                    // }
                    
                    // load page to init homepage location
                    changeBreadCrumb();
                });
                $('.menuTabs .page-tabs-content').append(str);
                $.learuntab.scrollToTab($('.menuTab.active'));
            }

            return false;
        },
        scrollTabRight: function () {
            var marginLeftVal = Math.abs(parseInt($('.page-tabs-content').css('margin-left')));
            var tabOuterWidth = $.learuntab.calSumWidth($(".content-tabs").children().not(".menuTabs").not(".menu-list"));
            var visibleWidth = $(".content-tabs").outerWidth(true) - tabOuterWidth;
            var scrollVal = 0;
            if ($(".page-tabs-content").width() < visibleWidth) {
                return false;
            } else {
                var tabElement = $(".menuTab:first");
                var offsetVal = 0;
                while ((offsetVal + $(tabElement).outerWidth(true)) <= marginLeftVal) {
                    offsetVal += $(tabElement).outerWidth(true);
                    tabElement = $(tabElement).next();
                }
                offsetVal = 0;
                while ((offsetVal + $(tabElement).outerWidth(true)) < (visibleWidth) && tabElement.length > 0) {
                    offsetVal += $(tabElement).outerWidth(true);
                    tabElement = $(tabElement).next();
                }
                scrollVal = $.learuntab.calSumWidth($(tabElement).prevAll());
                if (scrollVal > 0) {
                    $('.page-tabs-content').animate({
                        marginLeft: 0 - scrollVal + 'px'
                    }, "fast");
                }
            }
        },
        scrollTabLeft: function () {
            var marginLeftVal = Math.abs(parseInt($('.page-tabs-content').css('margin-left')));
            var tabOuterWidth = $.learuntab.calSumWidth($(".content-tabs").children().not(".menuTabs").not(".menu-list"));
            var visibleWidth = $(".content-tabs").outerWidth(true) - tabOuterWidth;
            var scrollVal = 0;
            if ($(".page-tabs-content").width() < visibleWidth) {
                return false;
            } else {
                var tabElement = $(".menuTab:first");
                var offsetVal = 0;
                while ((offsetVal + $(tabElement).outerWidth(true)) <= marginLeftVal) {
                    offsetVal += $(tabElement).outerWidth(true);
                    tabElement = $(tabElement).next();
                }
                offsetVal = 0;
                if ($.learuntab.calSumWidth($(tabElement).prevAll()) > visibleWidth) {
                    while ((offsetVal + $(tabElement).outerWidth(true)) < (visibleWidth) && tabElement.length > 0) {
                        offsetVal += $(tabElement).outerWidth(true);
                        tabElement = $(tabElement).prev();
                    }
                    scrollVal = $.learuntab.calSumWidth($(tabElement).prevAll());
                }
            }
            $('.page-tabs-content').animate({
                marginLeft: 0 - scrollVal + 'px'
            }, "fast");
        },
        scrollToTab: function (element) {
            var marginLeftVal = $.learuntab.calSumWidth($(element).prevAll()), marginRightVal = $.learuntab.calSumWidth($(element).nextAll());
            var tabOuterWidth = $.learuntab.calSumWidth($(".content-tabs").children().not(".menuTabs").not(".menu-list"));
            var visibleWidth = $(".content-tabs").outerWidth(true) - tabOuterWidth;
            var scrollVal = 0;
            if ($(".page-tabs-content").outerWidth() < visibleWidth) {
                scrollVal = 0;
            } else if (marginRightVal <= (visibleWidth - $(element).outerWidth(true) - $(element).next().outerWidth(true))) {
                if ((visibleWidth - $(element).next().outerWidth(true)) > marginRightVal) {
                    scrollVal = marginLeftVal;
                    var tabElement = element;
                    while ((scrollVal - $(tabElement).outerWidth()) > ($(".page-tabs-content").outerWidth() - visibleWidth)) {
                        scrollVal -= $(tabElement).prev().outerWidth();
                        tabElement = $(tabElement).prev();
                    }
                }
            } else if (marginLeftVal > (visibleWidth - $(element).outerWidth(true) - $(element).prev().outerWidth(true))) {
                scrollVal = marginLeftVal - $(element).prev().outerWidth(true);
            }
            $('.page-tabs-content').animate({
                marginLeft: 0 - scrollVal + 'px'
            }, "fast");
        },
        calSumWidth: function (element) {
            var width = 0;
            $(element).each(function () {
                width += $(this).outerWidth(true);
            });
            return width;
        },
        init: function () {
            $("#menu-container").on('click', '.menuItem', $.learuntab.addTab);
            $('.menuTabs').on('click', '.menuTab i', $.learuntab.closeTab);
            $('.menuTabs').on('click', '.menuTab', $.learuntab.activeTab);
            $('.tabLeft').on('click', $.learuntab.scrollTabLeft);
            $('.tabRight').on('click', $.learuntab.scrollTabRight);
            $('.tabReload').on('click', $.learuntab.refreshTab);
            $('.tabCloseCurrent').on('click', function () {
                $('.page-tabs-content').find('.active i').trigger("click");
            });
            $('.tabCloseAll').on('click', function () {
                $('.page-tabs-content').children("[data-id]").find('.fa-remove').each(function () {
                    $('.LRADMS_iframe[data-id="' + $(this).data('id') + '"]').remove();
                    $(this).parents('a').remove();
                });
                $('.page-tabs-content').children("[data-id]:first").each(function () {
                    $('.LRADMS_iframe[data-id="' + $(this).data('id') + '"]').show();
                    $(this).addClass("active");
                });
                $('.page-tabs-content').css("margin-left", "0");
            });
            $('.tabCloseOther').on('click', $.learuntab.closeOtherTabs);
            $('.fullscreen').on('click', function () {
                if (!$(this).attr('fullscreen')) {
                    $(this).attr('fullscreen', 'true');
                    $.learuntab.requestFullScreen();
                } else {
                    $(this).removeAttr('fullscreen');
                    $.learuntab.exitFullscreen();
                }
            });

            // close tree menu
            $('#orgTreeWrapper').on('click', '.closeIcon', function() {
                $('#' + GLOBAL.treeType[GLOBAL.treeView] + 'MenuNav').hide();
            });

            // show menu
            $('#menu-container').on('mouseover', '.parent-container', function() {
                $(this).children('.menu-hover').stop(true, false).animate({top: '-102px'}, 300);
            });
            $('#menu-container').on('mouseout', '.parent-container', function() {
                $(this).children('.menu-hover').stop(true, false).animate({top: '0px'}, 300);
            });

            // 预警、告警 show layer
            layer.config({
                extend: 'myskin/style.css',
                skin: 'layer-myskin'
            });
            var layConfig = {
                type: 2,
                maxmin: false,
                shadeClose : true,
                shade : false,
                offset: 'rb',
                shift: 2,
                area : [ '700px', '300px' ]
            };
            $('.showPrestat').on('click', function() {
            	var layerObj = $('.layer-anim-02');
          	    $.each(layerObj, function(){
          	        var timesIndex = $(this).attr('times');
          	        layer.close(timesIndex);
          	    });
                layConfig.title = '预警信息';
                layConfig.content = 'homepageWarning/showPrestat.html';
                layer.open(layConfig);
            });

            $('.showAlaStat').on('click', function() {
            	var layerObj = $('.layer-anim-02');
          	    $.each(layerObj, function(){
          	        var timesIndex = $(this).attr('times');
          	        layer.close(timesIndex);
          	    });            	
                layConfig.title = '告警信息';
                layConfig.content = 'homepageWarning/showAlaStat.html';
                layer.open(layConfig);
            });

        }
    };
    $.learunindex = {
        load: function () {
            $("body").removeClass("hold-transition")
            var calcHeight = function() {
                var height = $(window).height() - $(".main-header").height() - $(".content-tabs").height();
                $("#menu-container").css("max-height", height - 24);
                $(".metismenu").css("max-height", height - 32);
                $("#content-wrapper").find(".mainContent").height(height);
            };
            calcHeight();
            $(window).resize(function (e) {
                calcHeight();
            });
            $(".sidebar-toggle").click(function () {
                if (!$("body").hasClass("sidebar-collapse")) {
                    $("body").addClass("sidebar-collapse");
                } else {
                    $("body").removeClass("sidebar-collapse");
                }
            });
            /*$(window).load(function () {
                window.setTimeout(function () {
                    $('#ajax-loader').fadeOut();
                }, 300);
            });*/
        },
        jsonWhere: function (data, action) {
            if (action == null) return;
            var reval = new Array();
            $(data).each(function (i, v) {
                if (action(v)) {
                    reval.push(v);
                }
            });
            return reval;
        },
        loadMenu: function () {
            // remote menu url
            var menuURL = GLOBAL.contextPath + "/user/menu";

            $.get(menuURL, function(data) {
                $.each(data, function (i, obj) {
                    /* parent container */
                    var parentContainer = $("<div class='parent-container'></div>");

                    /* parent menu */
                    var parent = $("<div class='menu-parent'></div>");
                    // parent a
                    var parentA = $("<a href='javascript:;'><i class='fa'></i><span></span></a>");
                    parentA.children("i").addClass(obj['icon']);
                    parentA.children("span").text(obj['title']);
                    parent.append(parentA);
                    // children men
                    if(obj['enabled']) {
                        var child = $("<div class='menu-child'></div>");
                        var row = obj['subMenuList'];
                        $.each(row, function(i, sub) {
                            if(sub['enabled']) {
                                var childA = $("<a class='menuItem button--ujarak' href='javascript:void(0)'></a>");
                                childA.prop("title", sub['title']).attr("data-id", sub['url']).text(sub['title']);

                                var treeView = sub['treeView'];

                                // filter treeView,only two values is valid now
                                if (GLOBAL.treeType.hasOwnProperty(sub['treeView'])) {
                                    childA.attr("data-tree-view", sub['treeView']);
                                }

                                child.append(childA);
                            }
                        });
                        parent.append(child);
                    }

                    /* hover div */
                    var hoverCss = 'url(' + GLOBAL.rootPath + obj['img'] + ') no-repeat';
                    var hoverDiv = $("<div class='menu-hover'></div>").css('background', hoverCss).css('background-size', '100% 100%');
                    parentContainer.append(parent).append(hoverDiv);

                    // set max-width for menu-container
                    var num = data.length;
                    var maxWidth;
                    if(num > 4) {
                        maxWidth = '760px';
                    } else {
                        maxWidth = 170*num + 16*num + 16 + 'px';
                    }
                    $("#menu-container").css('max-width', maxWidth);
                    
                    // fill page
                    $("#menu-container").append(parentContainer);
                });

                // load init
                init();
            });
        }
    };
    $(function () {
        $.ajaxSetup({cache:false});
        $.learunindex.load();
        $.learunindex.loadMenu();
        $.learuntab.init();
    });
    
})(jQuery);
