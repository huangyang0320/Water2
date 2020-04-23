package com.wapwag.woss.modules.monitor.web;

import com.wapwag.woss.modules.biz.entity.ChartSeriesDto;
import com.wapwag.woss.modules.biz.entity.DeviceInfo;
import com.wapwag.woss.modules.monitor.service.MonitorV2Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author ChangWei Li
 * @version 2017-12-26 13:09
 */
@RestController
@RequestMapping("${adminPath}/monitor/v2")
@Api(description = "房实时相关API")
public class MonitorV2Controller {

	private final MonitorV2Service monitorV2Service;

    @Autowired
    public MonitorV2Controller(MonitorV2Service monitorV2Service) {
        this.monitorV2Service = monitorV2Service;
    }

	@RequestMapping("/device/{deviceId}/data/latest/{dbName}")
    @ApiOperation(value = "根据测点获取曲线/列表实时数据", httpMethod = "POST", notes = "")
    public List<Map<String, Object>> getLatestDeviceData(
            @PathVariable String dbName, @PathVariable String deviceId,
            @RequestParam(required = false) String serviceId) {
        return monitorV2Service.getLatestDeviceData(dbName, deviceId, serviceId);
    }

    @RequestMapping(value = "/device/{deviceId}/data/services/{dbName}")
    public List<Map<String, Object>> getServicesData(
            @PathVariable String dbName,
            @PathVariable String deviceId, String serviceIds) {
        return monitorV2Service.getServiceData(dbName, deviceId, serviceIds);
    }

    @RequestMapping(value = "/device/{deviceId}/services")
    public List<Map<String, Object>> getServiceInfo(@PathVariable String deviceId) {
    	List<Map<String, Object>> reult  = monitorV2Service.getServiceInfo(deviceId);
    	for(int i=0;i<reult.size();i++){
    		reult.get(i).put("index", i%7);
    	}
        return reult;
    }

    @RequestMapping("/devices/{deviceIds}/data/{serviceIds}/latest/{dbName}")
    public List<ChartSeriesDto> getLatestDevicesData(
            @PathVariable String dbName, @PathVariable String deviceIds,
            @PathVariable String serviceIds) {
        return monitorV2Service.getLatestDevicesData(dbName, deviceIds, serviceIds);
    }

    @RequestMapping("/devices/{deviceIds}/data/{serviceIds}/latestHis/{dbName}")
    public List<ChartSeriesDto> getLatestDevicesDataHis(
            @PathVariable String dbName, @PathVariable String deviceIds,
            @PathVariable String serviceIds) {
        return monitorV2Service.getLatestDevicesDataHis(dbName, deviceIds, serviceIds);
    }

    @RequestMapping("/devices/{deviceId}/data/latest/{dbName}")
    public List<Map<String, Object>> getLastDevicesData(@PathVariable String dbName, @PathVariable String deviceId){
    	return monitorV2Service.getLatestDevicesData(dbName, deviceId);
    }
}
