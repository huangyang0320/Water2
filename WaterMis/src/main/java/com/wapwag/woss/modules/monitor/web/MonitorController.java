package com.wapwag.woss.modules.monitor.web;

import com.alibaba.fastjson.JSONObject;
import com.wapwag.woss.modules.biz.entity.*;
import com.wapwag.woss.modules.home.entity.RunTimeVO;
import com.wapwag.woss.modules.monitor.service.MonitorService;
import com.wapwag.woss.modules.ticket.Entity.TicketDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * MonitorController
 * Created by Administrator on 2016/8/2.
 */
@RestController
@RequestMapping("${adminPath}/monitor")
@Api(description = "acgis地图相关API")
public class MonitorController {

    private final MonitorService monitorService;

    @Autowired
    public MonitorController(MonitorService monitorService) {
        this.monitorService = monitorService;
    }

    @RequestMapping("/statistic/{deviceId}/realtime")
    public Map<String, Object> queryDeviceStatistic(@PathVariable String deviceId, @RequestParam String date,
                                                    @RequestParam(required = false) String dimen) {
        return monitorService.queryDeviceStatistic(deviceId, date, dimen);
    }
    
    @RequestMapping("/statistic/{deviceId}/mapRealDetail")
    public List<Device> mapRealDetail(@PathVariable String deviceId) {
    	List<Device> devices = monitorService.mapRealDetail(deviceId);
    	if (null == devices) {
    		devices = new ArrayList<Device>();
		}
    	return devices;
    }

    @RequestMapping("/statistic/{deviceId}/history")
    public List<Map<String, String>> queryDeviceHistory(@PathVariable String deviceId, @RequestParam String index,
                                                        @RequestParam String dimen, @RequestParam String startDate,
                                                        @RequestParam String endDate) {
        return monitorService.queryDeviceHistory(deviceId, index, dimen, startDate, endDate);
    }
    
    @RequestMapping("/statistic/history")
    @ApiOperation(value = "根据测点获取历史曲线数据", httpMethod = "POST", notes = "")
    public List<ChartSeriesDto> queryDeviceHistory1(@RequestParam String deviceIds, @RequestParam String index,
                                                        @RequestParam String dimen, @RequestParam String startDate,
                                                        @RequestParam String endDate,@RequestParam String addOnDate) {
        return monitorService.queryDeviceHistory(deviceIds, index, dimen, startDate, endDate,addOnDate);
    }
    
    @RequestMapping("/statistic/consume")
    public List<Map<String, String>> queryDeviceConsume(@RequestParam String deviceId,
                                                        @RequestParam String dimen,
                                                        @RequestParam String startDate,
                                                        @RequestParam String endDate) {
        return monitorService.queryDeviceConsume(deviceId, dimen, startDate, endDate);
    }

    @RequestMapping("/statistic/diffDevice/consume")
    public List<Map<String, String>> queryDiffDeviceConsume(@RequestParam String deviceIds,
                                                            @RequestParam String dimen,
                                                            @RequestParam String startDate,
                                                            @RequestParam String endDate,
                                                            @RequestParam String deviceIndexs) {
    	
        return monitorService.queryDiffDeviceConsume(deviceIds, dimen, startDate, endDate);
    }
    
    @RequestMapping("/device/{deviceId}/data/latest")
    public List<Map<String, Object>> getLatestDeviceData( @PathVariable String deviceId, @RequestParam(required = false) String serviceId) {
        return monitorService.getLatestDeviceData( deviceId, serviceId);
    }

    @RequestMapping(value = "/device/{deviceId}/data/services")
    public List<Map<String, Object>> getServicesData( @PathVariable String deviceId, String serviceIds) {
    	serviceIds = "D0";
        return monitorService.getServiceData( deviceId, serviceIds);
    }

    @RequestMapping(value = "/device/{deviceId}/services")
    public List<Map<String, Object>> getServiceInfo(@PathVariable String deviceId) {
        return monitorService.getServiceInfo(deviceId);
    }

    @RequestMapping("/statistic/{deviceId}/mapOnlineStat")
    public List<Device> mapOnlineStat(@PathVariable String deviceId) {
    	List<Device> devices = monitorService.mapOnlineStat(deviceId);
    	if (null == devices) {
    		devices = new ArrayList<Device>();
		}
    	return devices;
    }
    

    @RequestMapping("/statistic/pumpOnlineStat")
    public String pumpOnlineStatText(@RequestBody String date) {
        String result=null;
        if(null==date)return result;
        List<String>pumpId=JSONObject.parseArray(date,java.lang.String.class);
        result = monitorService.pumpOnlineStat(pumpId);
        return result;
    }
    
    /**
     * 根据泵房Id获取相关信息
     * @param pumpInfoDto
     * @return
     */

    @RequestMapping("/statistic/findPumpHouseInfoById")
    @ResponseBody
    @ApiOperation(value = "根据地图图标获取泵房数据", httpMethod = "POST", response = PumpHouseInfo.class , notes = "")
    public PumpHouseInfo findPumpHouseInfoById(@RequestBody PumpInfoDto pumpInfoDto) {
        return monitorService.findPumpHouseInfoById(pumpInfoDto.getPumpHouseId());
    }
    
    @RequestMapping("/statistic/getPointInfoListByDeviceIds")
    @ResponseBody
    @ApiOperation(value = "根据泵房ID获取设备集合数据", httpMethod = "POST", response = DeviceInfo.class , notes = "")
    public List<DeviceInfo> getPointInfoListByDeviceIds(@RequestBody PumpInfoDto pumpInfoDto) {
        return monitorService.getPointInfoListByDeviceIds(pumpInfoDto.getPumpHouseId());
    }

    /**
     * 请求设备的当天泵机运行时长
     * @param pumpInfoDto
     * @return
     */
    @RequestMapping("/statistic/getPumpRunTimeByDeviceIds")
    @ResponseBody
    public List<RunTimeVO> getPumpRunTimeByDeviceIds(@RequestBody PumpInfoDto pumpInfoDto) {
        return monitorService.getPumpRunTimeByPHId(pumpInfoDto.getPumpHouseId());
    }
    
    @RequestMapping("/statistic/getDeviceStatus")
    @ResponseBody
    public List<DeviceInfo> getDeviceStatus(@RequestBody PumpInfoDto pumpInfoDto) {
        return monitorService.getDeviceStatus(pumpInfoDto.getPumpHouseId());
    }
    

}
