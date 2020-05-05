/**
 * init user authority and basic info
 */
var breadCrumbInfo = [];

var searchTree;

var changeBreadCrumb = function() {
    var $iframe = $('.mainContent iframe:visible');
    var treeView = $iframe.attr('data-tree-view');
    if(treeView !== 'undefined') {
        var iframe$ = $iframe[0].contentWindow.$;
        iframe$('.content-location').remove();
        // breadCrumb model
        var breadcrumbStyle = '<style>.content-location{position:relative;margin: 0 10px 10px 0;padding-left:15px;color:#475059;font-size:12px;line-height:25px;background-color:#fafafa} .content-location .left-location{position:relative;width:auto;height:100%} .content-location .left-location .icon-left, .content-location .right-list .icon-right{margin:0 5px 5px 0;font-size:14px;vertical-align:bottom} .content-location .right-list{position:absolute;top:0;right:15px;cursor:pointer}</style>';
        var $contentLocation = $('<div class="content-location"></div>');
        var $leftLocation = $('<div class="left-location"><icon class="icon-left fa fa-home"></icon><span id="breadcrumb"></span></div>');
        var $rightList = $('<div class="right-list"><icon class="icon-right fa fa-list"></icon><span id="chooseTips"></span></div>');

        if ('cityTree' === treeView) {
            $rightList.children('#chooseTips').prop('class', 'chooseCity').text('请选择城市');
        } else {
            $rightList.children('#chooseTips').prop('class', 'chooseDevice').text('请选择设备');
        }

        $leftLocation.children('#breadcrumb').text(breadCrumbInfo.join(' | '));
        $contentLocation.append($leftLocation).append($rightList);
        iframe$('head').append(breadcrumbStyle);
        iframe$('body').prepend($contentLocation);

        // define click event to choose city or device
        iframe$('.content-location').on('click', '.right-list', function() {
            $('#' + GLOBAL.treeType[GLOBAL.treeView] + 'MenuNav').slideDown();
        });
    }
};

var cascadeViewCallback = function (currentMenuId) {
    //every iframe toggle event occured,it need to render tree view
    var currentIframe = $('iframe[data-id="' + currentMenuId + '"]');

    if (currentIframe && currentIframe.length) {

        var $orgTreeWrapper = $('#orgTreeWrapper');
        var treeView = currentIframe.attr('data-tree-view');

        var $cityTree = $('#cityMenuNav');
        var $deviceTree = $('#deviceMenuNav');
        $cityTree.hide();
        $deviceTree.hide();

        for (var name in GLOBAL.treeType) {
            if (GLOBAL.treeType.hasOwnProperty(name)) {
                var currentMenuNav = '#' + GLOBAL.treeType[name] + 'MenuNav';
                if ($(currentMenuNav).length) {
                    $(currentMenuNav).hide();
                }
            }

        }

        //through tree view to determin tree level and visibility
        if (treeView !== 'undefined') {
            //get current select tree node or select first leaf node
            var selectNodes = GLOBAL[treeView].getSelectedNodes();
            breadCrumbInfo = [];

            try {
                if (!selectNodes || !selectNodes.length) {
                    GLOBAL.selectDefaultNode(GLOBAL[treeView]);
                    GLOBAL[treeView].getSelectedNodes()[0].getPath().map(function (itemNode) {
                        breadCrumbInfo.push(itemNode.name);
                        if (itemNode['treeInfo'] && itemNode['treeInfo']['dbName']) {
                            GLOBAL[treeView].getSelectedNodes()[0]['treeInfo']['dbName'] = itemNode['treeInfo']['dbName'];
                        }
                    });
                } else {
                    selectNodes[0].getPath().map(function (itemNode) {
                        breadCrumbInfo.push(itemNode.name);
                        if (itemNode['treeInfo'] && itemNode['treeInfo']['dbName']) {
                            selectNodes[0]['treeInfo']['dbName'] = itemNode['treeInfo']['dbName'];
                        }
                    });
                }
            } catch(e) {
                console.error(e);
            }

            GLOBAL.treeView = treeView;

        }
    }
};
        
var cascadeView = function (treeNode, obj) {
    // hide tree menu
    $('#' + GLOBAL.treeType[GLOBAL.treeView] + 'MenuNav').hide();
    // change selected element style
    $('.sidebar-nav .metismenu a').removeClass('a-selected');
    $(obj).addClass('a-selected');

    breadCrumbInfo = [];

    var $iframe = $('.mainContent iframe:visible');
    var treeView = $iframe.attr('data-tree-view');

    var iframe$ = $iframe[0].contentWindow.$;
    var $breadCrumb = iframe$('#breadcrumb');

    var currentNode = GLOBAL[treeView].getNodeByTId(treeNode['tId']);
    GLOBAL[treeView].selectNode(currentNode);

    currentNode.getPath().map(function (itemNode) {
        breadCrumbInfo.push(itemNode.name);
    });
    $breadCrumb.text(breadCrumbInfo.join(' | '));

    var childMethod = $iframe[0].contentWindow.cascadeView;
    if (childMethod) {
        childMethod(currentNode);
    }
};

/*
 * Solve problem : Unable open homepage when menu is not loaded
 * Define global method : init
 */

var logout;
var init = function () {

    var treeConfig = {
        callback:{
            onClick:function(event, treeId, treeNode){
                if(!treeNode.isParent) {
                    if (!treeNode['treeInfo']['dbName']) {
                        treeNode.getPath().map(function (itemNode) {
                            if (itemNode['treeInfo'] && itemNode['treeInfo']['dbName']) {
                                treeNode['treeInfo']['dbName'] = itemNode['treeInfo']['dbName'];
                            }
                        });
                    }
                    cascadeView(treeNode);
                }
            }
        }
    };
    var $logoutBtn = $("#logout");

    var initOrgTree = function (orgTree, org) {

        //request weather forecast html and load it manually
        var $iframe = $('#tianQiIF');
        $iframe.attr('src', 'https://is.tianqi.com/index.php?c=code&id=12&color=%23FFFFFF&icon=1&num=1&py=' + org['weatherId']);
        $iframe.show();
        var treeName;

        var $orgTree = $('#orgTreeWrapper');
        var $navSample = $('#navSample');

        for (treeName in GLOBAL.treeType) {

            if (GLOBAL.treeType.hasOwnProperty(treeName)) {
                var prefix = GLOBAL.treeType[treeName];

                var $navClone = $navSample.find('nav').clone();
                $navClone.prop('id', prefix + 'MenuNav');
                $navClone.find('ul').prop('id',prefix + 'Menu');
                $navClone.find('.ztree').prop('id', treeName);
                $navClone.appendTo($orgTree);

                GLOBAL[treeName] = $.fn.zTree.init($('#' + treeName), treeConfig, orgTree);
            }
        }

        GLOBAL.filterView(GLOBAL['cityTree'], org['type'], 2);
        GLOBAL.filterDeviceView(GLOBAL['deviceTree'], 'Pump');
        GLOBAL.filterDeviceView(GLOBAL['waterTree'], 'WaterQuality');
        GLOBAL.selectDefaultNode(GLOBAL['cityTree'], org['type'], 2);

        for (treeName in GLOBAL.treeType) {

            if (GLOBAL.treeType.hasOwnProperty(treeName)) {
                var $treeMenu = $('#' + GLOBAL.treeType[treeName] + 'Menu');
                $treeMenu.append(listTree(GLOBAL[treeName].getNodes()));
                $treeMenu.metisMenu();
            }
        }

        // open homepage
        $(".content-tabs").find(".menuItem").eq(0).click();
    };

    var listTree = function (nodes) {
        var $root = $('<ul aria-expanded="false"></ul>');
        if (nodes.length) {

            nodes.map(function (node) {

                // statistic device and pumphouse and video count for every level node
                if (node['treeInfo'] && node['treeInfo']['nodeType'] === 'pumphouse') {
                    var nodePath = node.getPath();

                    for (var i = 0; i < nodePath.length; i++) {
                        if (!nodePath[i].hasOwnProperty('count')) {
                            nodePath[i]['count'] = 0;
                            nodePath[i]['pumpHouseCount'] = 0;
                            nodePath[i]['deviceCount'] = 0;
                            nodePath[i]['videoCount'] = 0;
                        }
                        nodePath[i]['pumpHouseCount']++;
                        if (node.children.length) {
                            nodePath[i]['deviceCount'] += node.children.length;
                        }
                        if (node['treeInfo']['video']) {
                            nodePath[i]['videoCount'] += node['treeInfo']['video'].length;
                        }
                    }
                }

                var $list = $('<li></li>').prop('id', node.tId + '_metisMenu');

                if (node.children && node.children.length) {
                    $list.append('<a class="has-arrow" href="#">' + node.name + '</a>');
                    $list.append(listTree(node.children));
                } else {
                    node.getPath().map(function (itemNode) {
                        if (itemNode['treeInfo'] && itemNode['treeInfo']['dbName']) {
                            node['treeInfo']['dbName'] = itemNode['treeInfo']['dbName'];
                        }
                    });
                    $list.append("<a href='javascript:void(0)' onclick='cascadeView(" + JSON.stringify(node) + ", this)'>" + node.name + "</a>");
                }
                $root.append($list);
            });
        }

        return $root;
    };

    searchTree = function (keyword) {
        var currentMenu = '#' + GLOBAL.treeType[GLOBAL.treeView] + 'Menu';
        var $tree = $(currentMenu);

        if (keyword && keyword !== '') {
            // hide all sub li
            $tree.find('li').hide();
            var searchResult = [];

            // find top 10 node
            GLOBAL[GLOBAL.treeView].getNodesByFilter(function (node) {

                if (node.name && node.name.indexOf(keyword) > -1) {
                    searchResult.push(node);
                }
                // add fuzzy query for id
                if (node.id && node.id.indexOf(keyword) > -1) {
                    searchResult.push(node);
                }

                if (searchResult.length > 10) {
                    return false;
                }

            }, false);

            // show top 10 path node
            searchResult.map(function (node) {
                node.getPath().map(function (pathNode) {

                    var $pathNode = $('#' + pathNode.tId + '_metisMenu');
                    if ($pathNode.is(':hidden')) {
                        if (node.id !== pathNode.id) {
                            $pathNode.addClass('active');
                        }
                        $pathNode.find('a').eq(0).attr('aria-expanded', true);
                        $pathNode.find('ul').eq(0).attr('aria-expanded', true).addClass('in');
                        // $pathNode.show();
                        $pathNode.css('display', '');
                    }
                });
            });
            return;
        }

        // reset tree view
        $tree.find('li').show();
        $tree.find('li').removeClass('active');
        $tree.find('a[aria-expanded=true]').attr('aria-expanded', false);
        $tree.find('ul[aria-expanded=true]').attr('aria-expanded', false).removeClass('in');
    };

    // get user authority
    if (GLOBAL.session.contain('user')) {
        var user = GLOBAL.session.get('user');
        GLOBAL.user = user;
        if (user && user['authority']) {
            $('#userWelcome').text(user['username']);
            initOrgTree(user['authority'], user['org']);
        } else {
            $logoutBtn.click();
        }
    } else {
        $.post(GLOBAL.contextPath + "/user", function(result) {
            if (result && result['authority']) {
                GLOBAL.session.set('user', result);
                GLOBAL.user = result;
                $('#userWelcome').text(result['username']);
                initOrgTree(result['authority'], GLOBAL.user['org']);
            } else {
                $logoutBtn.click();
            }
        });
    }


    logout = function() {
        $.post(GLOBAL.contextPath + "/user/logout", function(result) {
            if (result === 'success') {
                sessionStorage.clear();
                window.location = GLOBAL.rootPath + "/cloud/login/login.html";
            }
        });
    };

    //log out
    $logoutBtn.on('click', logout);

    // the dynamic dom elements should be binded event in different way
    $(document).on('input propertychange', '.filterinput', function () {
        searchTree($(this).val());
    });

    //定时加载告警、预警信息
    var viewInExplorerStatus = function () {
        jQuery.ajax({
            type : 'POST',
            contentType : 'application/json',
            url : GLOBAL.contextPath + '/index/alarmNum?' + Math.random(),
            dataType : 'json',
            success : function(data) {
                $("#alaStatNum").html(data[0]);
                var indexTime = data[1];
                if(undefined != indexTime && null != indexTime && '' != indexTime){
                	indexTime = parseInt(indexTime);
                	indexTime = indexTime * 1000;
                }else{
                	indexTime = 60000;
                }
                setTimeout(viewInExplorerStatus, indexTime);
            },
            error : function(data) {
                setTimeout(viewInExplorerStatus, 60000);
            }
        });
    };

    viewInExplorerStatus();
    
  //定时加载告警、预警信息
    var viewInExplorerPreStatus = function () {
        jQuery.ajax({
            type : 'POST',
            contentType : 'application/json',
            url : GLOBAL.contextPath + '/index/preNum?' + Math.random(),
            dataType : 'json',
            success : function(data) {
                $("#preStatNum").html(data[0]);
                var indexTime = data[1];
                if(undefined != indexTime && null != indexTime && '' != indexTime){
                	indexTime = parseInt(indexTime);
                	indexTime = indexTime * 1000;
                }else{
                	indexTime = 60000;
                }
                setTimeout(viewInExplorerPreStatus, indexTime);
            },
            error : function(data) {
                setTimeout(viewInExplorerPreStatus, 60000);
            }
        });
    };

    viewInExplorerPreStatus();
};
