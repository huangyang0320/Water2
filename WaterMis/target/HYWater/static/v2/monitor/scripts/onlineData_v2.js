var onlineData = {
    loading: {
        show: function () {
            parent.LOADING.show();
        },
        hide: function () {
            if (parent.LOADING) {
                parent.LOADING.hide();
            }
        }
    },
    window: {
        resize: function () {
            var parentWidth = $(window).width()/3 - 30;
            $('.titleContainer table').width(parentWidth);
            $('.statusContainer table').width(parentWidth);
        }
    },
    timer: {
        chartInterval: null
    },
    contextPath: '/HYWater/a',
    device: {
        id: parent.GLOBAL_SELECT_DEVICE['deviceId'],
        name: parent.GLOBAL_SELECT_DEVICE['deviceName'],
        dbName: 'waterdb'
    },
    chart: {
        gaugeChart: {
            config: {
                backgroundColor: '#fff',
                series: [{
                    radius: '140%',
                    name: '业务指标',
                    type: 'gauge',
                    startAngle: 180,
                    endAngle: 0,
                    splitNumber: 4,
                    center : ['50%', '75%'],
                    axisLine: {
                        lineStyle: {
                            color: [[0.5, '#5fac31'],[0.8, '#ebd374'],[1, '#c23531']],
                            width: 8
                        }
                    },
                    axisLabel: {
                        textStyle: {
                            fontWeight: 'normal',
                            color: '#000',
                            shadowColor : '#fff',
                            shadowBlur: 10
                        }
                    },
                    axisTick: {
                        length: 12,
                        lineStyle: {
                            color: 'auto'
                        }
                    },
                    splitLine: {
                        length: 16,
                        lineStyle: {
                            color: 'auto'
                        }
                    },
                    pointer: {
                        width: 5,
                        shadowColor : '#000',
                        shadowBlur: 10
                    },
                    title : {
                        offsetCenter: [0, '18%'],
                        textStyle: {
                            fontWeight: 'bolder',
                            fontSize: 12,
                            color: '#000'
                        }
                    },
                    detail : {
                        width: 80,
                        height: 22,
                        borderWidth: 1,
                        borderColor: '#ccc',
                        offsetCenter: [0, '-30%'],
                        textStyle: {
                            fontWeight: 'nomal',
                            color: '#000'
                        }
                    },
                    data: [{
                        name: '泵后压力',
                        value: 0
                    }]
                }]
            },
            instance: [],
            index: {}
        },
        onlineDataChart: {
            xAxisData: [],
            config: {
                title :{
                    text:null
                },
                colors: ['#FFB6C1','#778899','#ADD8E6','#E1FFFF','#20B2AA','#90EE90','#F08080'],
                chart: {
                    animation: Highcharts.svg,
                    marginRight: 10,
                    zoomType: "xy",
                    backgroundColor: null,
                    borderWidth: 0,
                    plotBackgroundColor: null,
                    plotShadow: false,
                    plotBorderWidth: 0
                },
                exporting: {
                    enabled: false
                },
                xAxis: {
                    id: 'xAxis-dateTime',
                    categories: onlineDataChart.xAxisData,
                    gridLineWidth: 1,
                    lineColor: '#000',
                    tickColor: '#000',
                    labels: {
                        style: {
                            color: '#000',
                            font: '11px Trebuchet MS, Verdana, Microsoft YaHei, sans-serif'
                        }
                    },
                    title: {
                        style: {
                            color: '#333',
                            fontSize: '12px',
                            fontFamily: 'Trebuchet MS, Verdana, Microsoft YaHei, sans-serif'

                        }
                    }
                },
                yAxis: {
                    id: 'yAxisId-default',
                    min:0,
                    minorTickInterval: 'auto',
                    lineColor: '#000',
                    lineWidth: 1,
                    tickWidth: 1,
                    tickColor: '#000',
                    labels: {
                        style: {
                            color: '#000',
                            font: '11px Trebuchet MS, Verdana, Microsoft YaHei, sans-serif'
                        }
                    },
                    title: {
                        text: "",
                        style: {
                            color: '#333',
                            fontSize: '12px',
                            fontFamily: 'Trebuchet MS, Verdana, Microsoft YaHei, sans-serif'
                        }
                    },
                    plotLines: [{
                        value: 0,
                        width: 1,
                        color: '#808080'
                    }]
                },
                legend: {
                    verticalAlign: "top"
                },
                tooltip: {
                    crosshairs: {
                        width: 15
                    },
                    shared: true
                },
                plotOptions: {
                    series: {
                        fillOpacity: 0.5,
                        events: {
                            //控制图标的图例legend不允许切换
                            legendItemClick: function () {
                                return false; //return  true 则表示允许切换
                            }
                        }
                    }
                },
                credits: {
                    enabled: false//不显示highCharts版权信息
                },
                series: []

            },
            instance: []
        }
    }
};

// change layer style
layer.config({
    extend: 'myskin/style.css',
    skin: 'layer-myskin'
});
var layerConfig = {
    btn: ['确定', '取消'],
    shade: [0.3, '#000'],
    maxmin : false,
    move: false,
    area : ['320px', '182px;'],
    offset: ['20%']
};

var cascadeView = function (device) {
    onlineData.loading.show();
    onlineData.device = {
        id: device['deviceId'],
        name: device['deviceName'],
        dbName: 'waterdb'
    };
    model.initHistoryChartData();
};

var delayTimeout;
var colorIndex = -1;
var delay = function (callback) {
    stopDelay();
    delayTimeout = setTimeout(callback, 500);
};
var stopDelay = function () {
    if (delayTimeout) {
        clearTimeout(delayTimeout);
    }
};

//

onlineData.window.resize();

var model = new Vue({
    el: '#page-wrapper',
    mounted: function() {
        this.$nextTick(function () {
            onlineData.chart.onlineDataChart.instance = Highcharts.chart('onlineDataChart', onlineData.chart.onlineDataChart.config);

            for (var i = 0; i < 4; i++) {
                onlineData.chart.gaugeChart.instance.push(model.initChart('#gaugeData' + (i + 1), null, onlineData.chart.gaugeChart.config));
            }
            model.initHistoryChartData();
        });
    },
    data: {
        colorData: {},
        seriesData: [],
        onlineData: [],
        settingData: [],
        pumpData: [],
        chartData: [],
        selectedServices: [],
        chartServiceInfo: [], // number type service array list
        serviceMap: {}, // boolean type service array object
        currentDate: +new Date(),
        dateInterval: 15 * 1000,
        selectedServiceUnit: '',
        remoteMode: [
            {name: '远程自动', isOn: true},
            {name: '远程手动', isOn: false},
            {name: '急停', isOn: false}
        ],
        remoteDoor: [
            {name: '远程开门', isOn: true},
            {name: '远程关门', isOn: false}
        ],
        pumpStatusList: [
            {name: '1#泵', status: 'run', isOn: true, remarks: '运行', frequency: '40'},
            {name: '2#泵', status: 'rest', isOn: true, remarks: '休息', frequency: '20'},
            {name: '3#泵', status: 'stop', isOn: false, remarks: '停止', frequency: '0'},
            {name: '4#泵', status: 'fault', isOn: true, remarks: '故障', frequency: '1000'},
            {name: '5#泵', status: 'warn', isOn: true, remarks: '告警', frequency: '100'},
            {name: '6#泵', status: 'unknown', isOn: undefined, remarks: '未知', frequency: null}
        ]
    },
    watch: {
        selectedServices: function () {
            delay(function () {
                model.removeSeries();
                var yAxisColor;

                model.selectedServices.map(function (serviceId) {

                    var service = model.serviceMap[serviceId];

                    // if service has no unit use default color
                    if (service['unit'] !== '') {
                        if (!model.colorData.hasOwnProperty(service['unit'])) {
                            model.colorData[service['unit']] = onlineData.chart.onlineDataChart.config.colors[++colorIndex];
                        }
                        yAxisColor = model.colorData[service['unit']];
                    } else {
                        yAxisColor = Highcharts.getOptions().colors[8];
                    }

                    var series = {
                        yAxis: service['yAxisId'],
                        id: service['seriesId'],
                        name: service['serviceName'],
                        data: model.seriesData,
                        tooltip: {
                            valueSuffix: service['unit']
                        },
                        color: yAxisColor
                    };

                    if (!onlineData.chart.onlineDataChart.instance.get(service['yAxisId'])) {
                        onlineData.chart.onlineDataChart.config.yAxis.id = service['yAxisId'];
                        onlineData.chart.onlineDataChart.config.yAxis.labels.format = "{value}" + service['unit'];
                        onlineData.chart.onlineDataChart.config.yAxis.lineColor = yAxisColor;
                        onlineData.chart.onlineDataChart.config.yAxis.title.style.color = yAxisColor;
                        onlineData.chart.onlineDataChart.config.yAxis.labels.style.color = yAxisColor;
                        onlineData.chart.onlineDataChart.instance.addAxis(onlineData.chart.onlineDataChart.config.yAxis);
                    }

                    if (onlineData.chart.onlineDataChart.instance.get(service['seriesId'])) {
                        onlineData.chart.onlineDataChart.instance.get(service['seriesId']).update(series);
                    } else {
                        onlineData.chart.onlineDataChart.instance.addSeries(series);
                    }

                });

                model.toggleService();
            });
        }
    },
    methods: {
        redrawLine: function () {
            onlineData.chart.onlineDataChart.instance.resize();
        },
        limitService: function (item) {
            if (model.selectedServices.length > 6) {
                model.selectedServices = model.selectedServices.splice(model.selectedServices.indexOf(item['serviceId']), 1);
            } if (model.selectedServices.length < 1) {
                model.selectedServices = [item['serviceId']];
            }
        },
        formateDate: function (timestamp) {
            return moment(timestamp).format("YYYY-MM-DD HH:mm:ss");
        },
        removeSeries: function () {

            // remove average plot line when the service is changed
            onlineData.chart.onlineDataChart.instance.yAxis[0].removePlotLine('plotLine-average');
            var seriesCount = onlineData.chart.onlineDataChart.instance.series.length;
            var yAxisCount = onlineData.chart.onlineDataChart.instance.yAxis.length;

            var i = 0;
            var deleteSeries = [];

            if (yAxisCount) {
                for (i = 0; i < yAxisCount; i++) {
                    var yAxisId = onlineData.chart.onlineDataChart.instance.yAxis[i].options.id;
                    yAxisId = yAxisId.substring(8);

                    if (!(model.selectedServices.indexOf(yAxisId) > -1)) {
                        deleteSeries.push('yAxisId-' + yAxisId);
                    }

                }
            }

            if (seriesCount) {

                for (i = 0; i < seriesCount; i++) {
                    var seriesId = onlineData.chart.onlineDataChart.instance.series[i].options.id;
                    seriesId = seriesId.substring(9);

                    if (!(model.selectedServices.indexOf(seriesId) > -1)) {
                        deleteSeries.push('seriesId-' + seriesId);
                    }
                }

            }

            if (deleteSeries.length > 0) {
                deleteSeries.map(function (seriesId) {
                    if (onlineData.chart.onlineDataChart.instance.get(seriesId)) {
                        onlineData.chart.onlineDataChart.instance.get(seriesId).remove(false);
                    }
                });

                onlineData.chart.onlineDataChart.instance.redraw();
            }
        },
        toggleService: function() {

            var seriesConfig = function(name, data) {
                return {
                    name: name,
                    data: data
                };
            };

            var historyDataURL = onlineData.contextPath + '/monitor/v2/device/' + onlineData.device.id + '/data/services/' + onlineData.device.dbName;

            model.$http.post(historyDataURL, { serviceIds: model.selectedServices.join(',') }, GLOBAL.ajaxConfig)
                .then(function (response) {
                    var deviceData = [];

                    if (response.body) {
                        deviceData = response.body;
                    }

                    var reduce = {};
                    var legendData = [];
                    var series = [];
                    model.settingData = [];
                    model.pumpData = [];

                    deviceData.map(function (dataPoint) {
                        if (!reduce.hasOwnProperty(dataPoint['serviceId'])) {
                            reduce[dataPoint['serviceId']] = [];
                        }
                        reduce[dataPoint['serviceId']].push(dataPoint);
                    });

                    var tmpData = {};

                    // define average plotline in the highcharts
                    var plotLine = {
                        // the plot line only display when there is only one selected service
                        enable: model.selectedServices.length === 1,
                        count: 0,
                        sum: 0,
                        average: function () {
                            if (this.count > 0) {
                                return Number((this.sum / this.count).toFixed(3));
                            }
                        },
                        config: function () {

                            var serviceUnit = model.selectedServiceUnit;

                            if (!serviceUnit || serviceUnit === 'undefined') {
                                serviceUnit = '';
                            }

                            return {
                                value: this.average(),
                                color: 'red',
                                width: 5,
                                label: {
                                    text: '平均值：' + this.average() + serviceUnit,
                                    align: 'right',
                                    x: -10,
                                    y: -20
                                },
                                id: 'plotLine-average'
                            };
                        }
                    };

                    onlineData.chart.onlineDataChart.xAxisData.map(function (datePoint) {
                        datePoint = moment(datePoint, 'HH:mm:ss').format("x");
                        if (deviceData.length) {
                            for (var serviceId in reduce) {
                                if (!tmpData.hasOwnProperty(serviceId)) {
                                    tmpData[serviceId] = [];
                                }
                                for (var i = 0; i < reduce[serviceId].length; i++) {
                                    var dataPoint = reduce[serviceId][i];

                                    if (dataPoint['serviceDate'] >= datePoint || i === reduce[serviceId].length - 1) {
                                        var pointValue = model.formatServiceValue(serviceId, dataPoint['serviceValue']);
                                        if (plotLine.enable) {
                                            plotLine.count++;
                                            plotLine.sum += pointValue;
                                        }
                                        tmpData[serviceId].push(pointValue);
                                        break;
                                    }
                                }
                            }
                        }
                    });

                    var index = 0;

                    model.selectedServices.map(function (serviceId) {
                        model.chartServiceInfo.map(function (service) {
                            if (service['serviceId'] === serviceId) {

                                if (!tmpData[serviceId]) {
                                    tmpData[serviceId] = [];
                                    for (var i = 240; i > 0; i--) {
                                        tmpData[serviceId].push('-');
                                    }
                                }

                                var seriesId = service['seriesId'];

                                if (onlineData.chart.onlineDataChart.instance.get(seriesId)) {
                                    onlineData.chart.onlineDataChart.instance.get(seriesId).update({
                                        id: seriesId,
                                        data: tmpData[serviceId]
                                    });
                                } else {
                                    onlineDataChart.addSeries({
                                        id: seriesId,
                                        name: service['serviceName'],
                                        data: tmpData[serviceId]
                                    });
                                }

                                if (plotLine.enable) {
                                    onlineData.chart.onlineDataChart.instance.yAxis[0].addPlotLine(plotLine.config());
                                }


                                series[index] = seriesConfig(service['serviceName'], tmpData[serviceId]);
                                legendData.push(service['serviceName']);
                                index++;
                            }
                        });
                    });

                    model.chartData = tmpData;

                    model.dynamicUpdate(model.updateOnlineData, model.dateInterval, true);
                });
        },
        initChart: function(domId, theme, option) {
            var chart = echarts.init(document.querySelector(domId), theme);
            chart.setOption(option);
            return chart;
        },
        initHistoryChartData: function() {

            // dispose of online data array
            model.onlineData = [];

            var deviceId = onlineData.device.id;

            GLOBAL.metaStore.getMeta(deviceId, function(success) {

                if (!success) {
                    onlineData.loading.hide();
                    return;
                }

                model.serviceMap = GLOBAL.metaStore.serviceMap[deviceId];
                model.chartServiceInfo = GLOBAL.metaStore.chartServiceList[deviceId];

                model.currentDate = +new Date() - 15 * 1000;
                onlineData.chart.onlineDataChart.xAxisData = [];
                var seriesData = [];

                var i;
                for (i = 240; i > 0; i--) {
                    onlineData.chart.onlineDataChart.xAxisData.push(moment(model.currentDate - model.dateInterval * i).format("HH:mm:ss"));
                    seriesData.push('-');
                }

                onlineData.chart.onlineDataChart.instance.update({
                    xAxis: {
                        id: 'xAxis-dateTime',
                        categories: onlineData.chart.onlineDataChart.xAxisData
                    }
                });

                if (model.chartServiceInfo.length === 0) {
                    onlineData.loading.hide();
                    model.dynamicUpdate(model.updateOnlineData, model.dateInterval);
                    return;
                }

                model.seriesData  = seriesData;
                model.selectedServiceUnit = model.chartServiceInfo[0]['unit'];
                model.selectedServices = [model.chartServiceInfo[0]['serviceId']];

                var index = 0;
                model.chartServiceInfo.map(function(service) {
                    if (service['IS_CUMULATIVE'] !== 1 && service['visible'] === '1' && index < 4) {

                        onlineData.chart.gaugeChart.index[service['serviceId']] = index;

                        onlineData.chart.gaugeChart.instance[index].setOption({
                            series: [{
                                axisLabel: {
                                    formatter: function (value) {
                                        return Number(Number(value).toFixed(2));
                                    }
                                },
                                detail: {
                                    formatter: '{value}' + (service['unit'] ? service['unit'] : '')
                                },
                                min: 0,
                                max: service['max'] ? service['max'] : 100,
                                data: [{
                                    name: service['serviceName'],
                                    value: 0
                                }]
                            }]
                        });

                        index++;
                    }
                });

                if (index < 4) {
                    model.chartServiceInfo.map(function(service) {
                        if (service['IS_CUMULATIVE'] !== 1 && service['visible'] !== '1' && service['min'] !== '' && service['max'] !== '' && index < 4) {

                            onlineData.chart.gaugeChart.index[service['serviceId']] = index;

                            onlineData.chart.gaugeChart.instance[index].setOption({
                                series: [{
                                    axisLabel: {
                                        formatter: function (value) {
                                            return Number(Number(value).toFixed(2));
                                        }
                                    },
                                    detail: {
                                        formatter: '{value}' + (service['unit'] ? service['unit'] : '')
                                    },
                                    min: 0,
                                    max: service['max'] ? service['max'] : 100,
                                    data: [{
                                        name: service['serviceName'],
                                        value: 0
                                    }]
                                }]
                            });

                            index++;
                        }
                    });
                }

                if (index < 4) {

                    for (var chartIndex = index; chartIndex < 4; chartIndex++) {

                        onlineData.chart.gaugeChart.instance[chartIndex].setOption({
                            series: [{
                                axisLabel: {
                                    formatter: function (value) {
                                        return Number(Number(value).toFixed(2));
                                    }
                                },
                                detail: {
                                    formatter: '{value}'
                                },
                                min: 0,
                                max: 100,
                                data: [{
                                    name: '未采集测点',
                                    value: 0
                                }]
                            }]
                        });
                    }
                }
            });

        },
        updateOnlineData: function(onlyOnce) {

            var onlineDataURL = onlineData.contextPath + '/monitor/v2/device/' + onlineData.device.id + '/data/latest/' + onlineData.device.dbName;

            this.$http.get(onlineDataURL, GLOBAL.ajaxConfig).then(function (response) {
                onlineData.loading.hide();
                var resultData = response.body;
                var pumpStatusArray = [];
                model.pumpData = [];

                if (resultData && resultData.length > 0) {
                    model.onlineData = resultData;

                    model.onlineData.map(function (deviceData) {

                        var serviceId = deviceData['serviceId'];
                        var serviceName = model.serviceMap[deviceData['serviceId']]['serviceName'];
                        var serviceValue = model.formatServiceValue(deviceData['serviceId'], deviceData['serviceValue']);

                        if (onlyOnce && model.serviceMap[deviceData['serviceId']]['IS_PARAM_SETTING'] === 1) {
                            model.settingData.push({
                                serviceId: serviceId,
                                serviceName: serviceName,
                                serviceValue: serviceValue
                            });
                        }

                        if (serviceName.match(/(\d)#变频频率/)) {
                            pumpStatusArray[serviceName.match(/(\d)#变频频率/)[1]].push({
                                serviceId: serviceId,
                                serviceName: '变频频率',
                                serviceValue: serviceValue
                            });
                        }

                        if (model.serviceMap[deviceData['serviceId']]['type'] === 'bool') {

                            var matchResult = deviceData['serviceName'].match(/(\d)#(变频运行|工频运行|水泵运行|水泵停止|变频故障|水泵故障|空开跳闸)/);

                            if (matchResult) {

                                var pumpIndex = matchResult[1];
                                var pumpStatus = matchResult[2];

                                if (!pumpStatusArray[pumpIndex]) {
                                    pumpStatusArray[pumpIndex] = [];
                                }

                                pumpStatusArray[pumpIndex].push({
                                    serviceId: serviceId,
                                    serviceName: pumpStatus,
                                    serviceValue: serviceValue
                                });

                            }

                        }

                        if (onlineData.chart.gaugeChart.index.hasOwnProperty(deviceData['serviceId'])) {

                            var serviceData = deviceData['serviceValue'];

                            if (serviceData === '-') {
                                serviceData = 0;
                            }

                            onlineData.chart.gaugeChart.instance[onlineData.chart.gaugeChart.index[deviceData['serviceId']]].setOption({
                                series: [{
                                    data: [{
                                        name: deviceData['serviceName'],
                                        value: model.formatServiceValue(deviceData['serviceId'], serviceData)
                                    }]
                                }]
                            });
                        }
                    });

                }

                if (pumpStatusArray.length) {

                    pumpStatusArray.map(function (pumpStatus, index) {

                        var pumpData = {
                            pumpName: index + '#泵',
                            serviceName: null,
                            statusCode: 'stop', // default pump status is stop if no status data could be find
                            statusName: '停止',
                            feq: null
                        };

                        //变频运行|工频运行|水泵运行|水泵停止|变频故障|水泵故障|空开跳闸|变频频率
                        pumpStatus.map(function (serviceStatus) {

                            var serviceName = serviceStatus['serviceName'];

                            if (serviceName === '变频频率') {
                                pumpData['feq'] = serviceStatus['serviceValue'];
                                pumpData['serviceName'] = model.serviceMap[serviceStatus['serviceId']]['serviceName'];
                            } else if (serviceStatus['serviceValue'] === 1) {
                                if (serviceName === '工频运行' || serviceName === '水泵运行' || serviceName === '水泵停止') {
                                    pumpData['statusCode'] = 'rest';
                                    pumpData['statusName'] = '休息';
                                } else if (serviceName === '变频运行') {
                                    pumpData['statusCode'] = 'run';
                                    pumpData['statusName'] = '运行';
                                } else if (serviceName === '变频故障' || serviceName === '水泵故障' || serviceName === '空开跳闸') {
                                    pumpData['statusCode'] = 'fault';
                                    pumpData['statusName'] = '故障';
                                }
                            }

                        });

                        model.pumpData.push(pumpData);

                    });
                }

                model.updateChart();
            });
        },
        updateChart: function () {
            onlineData.chart.onlineDataChart.xAxisData.shift();
            // get current date instead of plus date interval in case of multiple trigger updateChart method
            model.currentDate = +new Date();
            // model.currentDate += model.dateInterval;
            onlineData.chart.onlineDataChart.xAxisData.push(moment(model.currentDate).format("HH:mm:ss"));

            var index = 0;
            var series = [];
            for (var serviceId in model.chartData) {

                var exist = false;

                for (var i = 0; i < model.onlineData.length; i++) {
                    var deviceData = model.onlineData[i];

                    if (deviceData['serviceId'] === serviceId) {
                        exist = true;
                        if (model.chartData.hasOwnProperty(serviceId)) {
                            model.chartData[serviceId].shift();
                            model.chartData[serviceId].push(model.formatServiceValue(serviceId, deviceData['serviceValue']));
                            series[index++] = {
                                id: 'seriesId-' + serviceId,
                                name: deviceData['serviceName'],
                                data: model.chartData[serviceId]
                            };
                            break;
                        }
                    }
                }

                if (!exist && model.chartData.hasOwnProperty(serviceId)) {
                    series[index++] = {
                        id: 'seriesId-' + serviceId,
                        data: model.chartData[serviceId]
                    };
                }
            }

            onlineData.chart.onlineDataChart.instance.update({
                xAxis: {
                    id: 'xAxis-dateTime',
                    categories: onlineData.chart.onlineDataChart.xAxisData
                },
                series: series
            });
        },
        formatServiceValue: GLOBAL.metaStore.format,
        dynamicUpdate: function (callbacks, timeInterval, args) {
            var handle;
            if (Array.isArray(callbacks)) {
                handle = function () {
                    for (var i = 0; i < callbacks.length; i++) {
                        callbacks[i]();
                    }
                };
            } else {
                handle = callbacks;
            }

            handle(args);

            if (onlineData.timer.chartInterval) {
                clearInterval(onlineData.timer.chartInterval);
            }
            onlineData.timer.chartInterval = setInterval(handle, timeInterval);
        },
        // 修改参数
        updateDeviceParams: function(event, service) {

            layerConfig.title = '<i class="fa fa-pencil-square-o"></i> ' + service['serviceName'] + ' - 修改';
            layerConfig.yes = function(index) {
                var newVal = $('#paramsVal').val();
                $(event.target).text(newVal);
                service['serviceValue'] = newVal;
                layer.close(index);
            };

            layerConfig.content = '<div class="update-params"><span>当前参数值 : </span><input id="paramsVal" class="form-ele input-max" value="'
                + service['serviceValue'] +'"></div>';
            layer.open(layerConfig);
        },
        // 切换模式
        changeMode: function(e, obj) {
            if($(e.target).hasClass('modeOff')) {
                layerConfig.title = '<i class="fa fa-pencil-square-o"></i> 远程模式修改';

                layerConfig.yes = function(index) {
                    $('.modeContainer').find('em').prop('class', 'modeOff');
                    $(e.target).prop('class', 'modeOn');
                    layer.close(index);
                };

                layerConfig.content = '<div class="update-params">确定启用 <span style="color: red;">'+obj['name']+'</span> 模式？</div>';
                layer.open(layerConfig);
            }
        },
        changeDoor: function(e, obj) {

            if($(e.target).hasClass('modeOpen') || $(e.target).hasClass('modeClose')) {
                layerConfig.title = '<i class="fa fa-pencil-square-o"></i> 远程开关门';

                var operType = obj['name'];
                if(operType == '远程关门'){
                    operType = '1';
                }else{
                    operType ='2';
                }

                layerConfig.yes = function(index) {
                    layer.close(index);
                    model.downControl(operType,index);

                };

                layerConfig.content = '<div class="update-params">确定启用 <span style="color: red;">'+obj['name']+'</span> 操作？</div>';
                layer.open(layerConfig);
            }
        },
        // 启停水泵
        controlPumps: function(obj) {

            layerConfig.title = '<i class="fa fa-power-off"></i> 水泵启停';
            if(obj['statusCode'] !== 'stop') {
                layerConfig.content = '<div class="update-params">确定停止'+obj['pumpName']+'吗？</div>';
                layerConfig.yes = function(index) {
                    obj['statusCode'] = 'stop';
                    obj['statusName'] = '停止';
                    layer.close(index);
                };
            } else {
                layerConfig.content = '<div class="update-params">确定启用'+obj['pumpName']+'吗？</div>';
                layerConfig.yes = function(index) {
                    obj['statusCode'] = 'run';
                    obj['statusName'] = '运行';
                    layer.close(index);
                };
            }

            layer.open(layerConfig);
        },
        // 频率设定
        updateFrequency: function(obj) {
            if(obj['feq']) {
                layerConfig.title = '<i class="fa fa-pencil-square-o"></i> 修改' + obj['serviceName'];
                layerConfig.yes = function(index) {
                    obj['feq'] = $('#paramsVal').val();
                    layer.close(index);
                };

                layerConfig.content = '<div class="update-params"><span>当前频率值 : </span><input id="paramsVal" class="form-ele input-max" value="'+obj['feq']+'"></div>';
                layer.open(layerConfig);
            }
        },
        downControl: function(operType,index) {
            var controalUrl = onlineData.contextPath + '/controal/door/' + onlineData.device.id + '/' + operType + '?'+Math.random();

            this.$http.get(controalUrl, GLOBAL.ajaxConfig).then(function (response) {
                layerConfig.title = '<i class="fa fa-pencil-square-o"></i> 远程开关门';
                layerConfig.btn = [];
                layerConfig.area = ['320px', '162px;'];
                var tagMsg = "操作失败";
                if(response.body == '0'){
                    tagMsg = "操作成功";
                }else if(response.body == '1'){
                    tagMsg = "设备没有配置门禁";
                }else{
                    tagMsg = response.body;
                }
                layerConfig.content = '<div class="update-params">'+tagMsg+'！</div>';
                layer.open(layerConfig);

                layerConfig.btn = ['确定', '取消'];
                layerConfig.area = ['320px', '182px;'];
                setTimeout(function(){
                    layer.close(index + 1);
                }, 2000);
            });
        }
    }
});

// 重新渲染 echarts
$(window).resize(function() {
    onlineData.window.resize();
    echarts.getInstanceByDom(document.getElementById("gaugeData1")).resize();
    echarts.getInstanceByDom(document.getElementById("gaugeData2")).resize();
    echarts.getInstanceByDom(document.getElementById("gaugeData3")).resize();
    echarts.getInstanceByDom(document.getElementById("gaugeData4")).resize();
});
