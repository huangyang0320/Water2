/**
 * Global constant
  * @type {{}}
 */
var GLOBAL = {
    // add html hash to prevent html cache can't be removed after project update
    htmlHash: '?v=HTML_HASH',
    rootPath: 'current root path of the application',
    adminPath: '/a',
    contextPath: '/',
    fileServer: 'https://web.cloud4water.com',
    session: '',
    local: '',
    currentMenuId: 'home/home.html',
    ajaxConfig: '',
    filterView: '',
    filterDeviceView: '',
    selectDefaultNode: '',
    selectNode: '',
    treeView: '',
    format: '',
    // frontend device service meta store
    metaStore: {
        serviceList: {}, // service map like {'serviceId':'serviceInfo'}
        chartServiceList: {}, // like serviceList without bool service
        serviceMap: {}, // service map like {'deviceId': {'serviceId':'serviceInfo'}}
        deviceList: {}, // service List like {'deviceId': ['serviceList']}
        put: function (service) {
            var serviceId = service['serviceId'];
            if (!GLOBAL.metaStore.serviceList.hasOwnProperty(serviceId)) {

                var serviceUnit = service['unit'];

                if (!serviceUnit || serviceUnit === '') {
                    serviceUnit = 'empty';
                }

                // add aditional highcarts component id
                service['seriesId'] = 'seriesId-' + serviceId;
                service['yAxisId'] = 'yAxisId-' + serviceUnit;

                GLOBAL.metaStore.serviceList[serviceId] = service;
            }
        },
        get: function (serviceId) {
            return GLOBAL.metaStore.serviceList[serviceId];
        },
        // format specific service value with ratio and round format
        format: function (serviceId, value) {
            var service = GLOBAL.metaStore.serviceList[serviceId];

            if (service) {
                var multiple = service['multiple'];
                if (value && multiple && Number(multiple) > 0 && Number(value) > 0) {
                    value = value * multiple;
                }
            }
            return GLOBAL.format.numberFormat(value);
        }
    },
    tree: {
        filterView: ''
    },
    errorHandler: '',
    EXPORT_MUST_NUM: 1500,
    treeType: {
        allTree: 'all',
        cityTree: 'city',
        deviceTree: 'device',
        waterTree: 'water'
    },
    setHighchartsColors: ''
};

(function (GLOBAL, $) {

    //set webapp root path, admin path, contextpath
    var contextPath = location.pathname;

    if (contextPath) {
        GLOBAL.rootPath = '/' + contextPath.split('/')[1];
        GLOBAL.contextPath = GLOBAL.rootPath + GLOBAL.adminPath;
    }

    GLOBAL.session = {
        sessionObject: window.sessionStorage,
        size: function() {
            return this.sessionObject.length
        },
        contain: function(key) {
            return !!this.get(key);
        },
        set: function(key, value) {
            this.sessionObject.setItem(key, JSON.stringify(value));
        },
        addList: function(key, value) {
            var list = !!this.get(key) ? JSON.parse(this.get(key)) : [];
            list.push(value);
            this.set(key, JSON.stringify(list));
        },
        setList: function(key, value) {
            var list = [];
            list.push(value);
            this.set(key, list)
        },
        get: function(key) {
            return JSON.parse(this.sessionObject.getItem(key));
        },
        remove: function(key) {
            this.sessionObject.removeItem(key);
        },
        clear: function() {
            this.sessionObject.clear();
        }
    };

    GLOBAL.local = {
        localObject: window.localStorage,
        size: function() {
            return this.localObject.length
        },
        contain: function(key) {
            return !!this.get(key);
        },
        set: function(key, value) {
            this.localObject.setItem(key, JSON.stringify(value));
        },
        setList: function(key, value) {
            var list = this.contain(key)? JSON.parse(this.get(key)) : [] ;
            list.push(value);
            this.set(key, JSON.stringify(list));
        },
        get: function(key) {
            return this.localObject.getItem(key);
        },
        remove: function(key) {
            this.localObject.removeItem(key);
        },
        clear: function() {
            this.localObject.clear();
        }
    };

    /**
     * filter org tree to adapt different module
     * @param tree org ztree object
     * @param orgType WPG GROUP CUSTOMER
     * @param level specify the max level of the org tree view
     */
    GLOBAL.filterView = function (tree, orgType, level) {
        //reduce the tree view level when the org is the single org
        if (orgType === 'CUSTOMER') {
            level -= 1;
        }
        tree.getNodesByFilter(function (node) {
            if (node.level === level) {
                tree.removeChildNodes(node);
            }
        });
    };

    GLOBAL.filterDeviceView = function (tree, deviceType) {
        tree.getNodesByFilter(function (node) {
            if (node['treeInfo']['nodeType'] === 'device'
                && node['treeInfo']['deviceType']
                && node['treeInfo']['deviceType'] === deviceType) {
                var nodePath = node.getPath();
                for (var i = 0; i < nodePath.length; i++) {
                    if (!nodePath[i].hasOwnProperty('waterCount')) {
                        nodePath[i]['waterCount'] = 0;
                    }
                    nodePath[i]['waterCount']++;
                }
            }
        });

        var nodes = tree.getNodesByFilter(function (node) {
            return !node['waterCount'];
        });

        for (var i = 0; i < nodes.length; i++) {
            tree.removeNode(nodes[i], false);
        }
    };

    GLOBAL.selectDefaultNode = function (tree, orgType) {
        tree.getNodesByFilter(function (node) {
            if (node.isParent === false) {
                tree.selectNode(node);
                return true;
            }
        }, true);
    };

    GLOBAL.selectNode = function (treeView, selectNodeId) {

        GLOBAL[treeView].getNodesByFilter(function (node) {
            if (node['treeInfo']['nodeType'] === 'device' && node['id'] === selectNodeId) {
                GLOBAL[treeView].selectNode(node);
                return true;
            }
        }, true);
    };

    GLOBAL.ajaxConfig = {
        emulateJSON: true,
        // use before callback
        before: function(request) {

            // abort previous request, if exists
            if (this.previousRequest) {
                this.previousRequest.abort();
            }

            // set previous request on Vue instance
            this.previousRequest = request;
        }
    };

    GLOBAL.format = {
        numberFormat: function (value) {

            var numberPattern = /^(-)?\d+(\.\d+)?$/;

            if (numberPattern.test(value)) {
                return Number(Number(value).toFixed(3));
            }

            return '-';
        }
    };

    // global logout ajax request filter
    $(document).ajaxError(function(event, jqxhr) {
        var userStatus = jqxhr ? jqxhr.getAllResponseHeaders() : null;
        if (userStatus && userStatus.indexOf("x-user-status: logout") > -1) {
            top.window.location = GLOBAL.rootPath + "/cloud/login/login.html";
        }
    });

    /**
     * Global browser window exception event
     * Tip: currently window.onerror event could only available on simple js code,
     * but like vue and other js frameword except jquery, it has own error handler,
     * and won't throw error to the root window, so it doesn't work for these framework
     * @param msg error message
     * @param url the url of the script or the document related to the error
     * @param line the line number(if available) the error occurred
     * @param column the column number(if available) the error occurred
     * @param error the error object defined in the window
     */
    GLOBAL.errorHandler = function (msg, url, line, column, error) {
        GLOBAL.session.set('error' + new Date().getTime(), JSON.stringify(arguments));
    };

    /**
     * Start out with a darkened base color (negative brighten), and end up with a much brighter color.
     * If the pie chart is divided into too many blocks, the color discrimination will not be too obvious.
     * @param obj that transfer parameters to you, contains the following properties:
     * baseColor the basic color, which produces a specified number of color drops(supports only 16 hex).
     * chartObj the base Object of Highcharts.
     * sum the total of pie chart blocks, determine color quantity.
     * flag the boolean value that determine you want to create color drops.
     */ 
    GLOBAL.setHighchartsColors = function(obj) {
        var colors = [], flag = obj['flag'], chartObj = obj['chartObj'];
        if(true === flag && 'object' === typeof(chartObj)) {
            var regx = new RegExp('[a-fA-F0-9]+');
            var baseColor = obj['baseColor'];
            var sum = parseInt(obj['sum'], 10);
            if( !(baseColor && 'string' === typeof(baseColor) && baseColor.charAt(0) === '#' && regx.test(baseColor.substring(1))
             && (4 === baseColor.length || 7 === baseColor.length)) ) {
                baseColor = '#000000';
            }
            if(!sum) {
                sum = 10;
            }
            for (var i = 0; i < sum; i += 1) {
                colors.push(chartObj.Color(baseColor).brighten((i - 3) / sum).get());
            }
        } else {
            colors = ['#7cb5ec', '#434348', '#90ed7d', '#f7a35c', '#8085e9', '#f15c80', '#e4d354', '#8085e8', '#8d4653', '#91e8e1'];
        }
        return colors;
    };

    GLOBAL.metaStore.getMeta = function (deviceIds, callback) {
        var notExistIds = [];

        if (deviceIds) {
            if (Array.isArray(deviceIds) && deviceIds.length > 0) {
                deviceIds.map(function(deviceId) {
                    if (!GLOBAL.metaStore.deviceList.hasOwnProperty(deviceId)) {
                        notExistIds.push(deviceId);
                    }

                })
            } else if(!GLOBAL.metaStore.deviceList.hasOwnProperty(deviceIds)) {
                notExistIds.push(deviceIds);
            }
        }

        if (notExistIds.length > 0) {
            // global device service api
            var SERVICE_API = GLOBAL.contextPath + '/monitor/v2/device/' + notExistIds + '/services';

            $.post(SERVICE_API, function (serviceList) {
                if (serviceList && Array.isArray(serviceList) && serviceList.length > 1) {

                    notExistIds.map(function (deviceId) {

                        if (!GLOBAL.metaStore.deviceList.hasOwnProperty(deviceId)) {
                            GLOBAL.metaStore.deviceList[deviceId] = serviceList;
                            GLOBAL.metaStore.serviceMap[deviceId] = {};
                            GLOBAL.metaStore.chartServiceList[deviceId] = [];

                            serviceList.map(function (service) {

                                var serviceId = service['serviceId'];
                                if (service['deviceId'] === deviceId && !GLOBAL.metaStore.serviceList.hasOwnProperty(serviceId)) {
                                    // add aditional class to display bool variable
                                    if (service['type'] === 'bool') {
                                        if (service['serviceName'].indexOf('故障') > -1
                                            || service['serviceName'].indexOf('报警') > -1
                                            || service['serviceName'].indexOf('跳闸') > -1) {
                                            service['on'] = 'status-pic-error';
                                        } else {
                                            service['on'] = 'status-pic-power';
                                        }
                                        service['off'] = 'status-pic-stop';
                                    } else if (!GLOBAL.metaStore.chartServiceList.hasOwnProperty(serviceId)) {
                                        GLOBAL.metaStore.chartServiceList[deviceId].push(service);
                                    }

                                    if (!GLOBAL.metaStore.serviceMap[deviceId].hasOwnProperty(serviceId)) {
                                        GLOBAL.metaStore.serviceMap[deviceId][serviceId] = service;
                                    }

                                    var serviceUnit = service['unit'];

                                    if (!serviceUnit || serviceUnit === '') {
                                        service['unit'] = '';
                                        serviceUnit = 'empty';
                                    }

                                    // add aditional highcarts component id
                                    service['seriesId'] = 'seriesId-' + serviceId;
                                    service['yAxisId'] = 'yAxisId-' + serviceUnit;

                                    GLOBAL.metaStore.serviceList[serviceId] = service;
                                }
                            });
                        }

                    });

                    callback(true);
                } else {
                    callback(false);
                }
            });
        } else {
            callback(true);
        }

    };

})(GLOBAL, jQuery);