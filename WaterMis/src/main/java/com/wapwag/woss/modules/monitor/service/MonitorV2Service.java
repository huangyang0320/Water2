package com.wapwag.woss.modules.monitor.service;

import com.wapwag.woss.common.utils.StringUtils;
import com.wapwag.woss.modules.biz.dao.ServicesDao;
import com.wapwag.woss.modules.biz.entity.ChartSeriesDto;
import com.wapwag.woss.modules.biz.entity.Services;
import com.wapwag.woss.modules.home.dao.DeviceDao;
import com.wapwag.woss.modules.monitor.dao.MonitorV2Mapper;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author ChangWei Li
 * @version 2017-12-26 13:10
 */
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class MonitorV2Service {

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private final MonitorV2Mapper monitorV2Mapper;
    private final DeviceDao deviceDao;
    private final ServicesDao servicesDao;

    @Autowired
    public MonitorV2Service(MonitorV2Mapper monitorV2Mapper,DeviceDao deviceDao,ServicesDao servicesDao) {
        this.monitorV2Mapper = monitorV2Mapper;
        this.deviceDao = deviceDao;
        this.servicesDao = servicesDao;
    }

    public List<Map<String, Object>> getLatestDeviceData(String dbName, String deviceId, String serviceId) {
        DateTime now = DateTime.now();
        String startDate = now.plusHours(-1).toString(DATE_FORMAT);
        String endDate = now.toString(DATE_FORMAT);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        dbName = "service_values_"+sdf.format(new Date()); 
        return monitorV2Mapper.getLatestDeviceData(dbName, startDate, endDate, deviceId, serviceId);
    }

    public List<Map<String, Object>> getServiceData(String dbName, String deviceId, String serviceIds) {
        DateTime now = DateTime.now();
        String startDate = now.plusHours(-1).toString(DATE_FORMAT);
        String endDate = now.toString(DATE_FORMAT);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        dbName = "service_values_"+sdf.format(new Date()); 
        return monitorV2Mapper.getServiceData(dbName, startDate, endDate, deviceId, serviceIds);
    }

    public List<Map<String, Object>> getServiceInfo(String deviceId) {
        return monitorV2Mapper.getServiceInfo(deviceId);
    }

    public List<ChartSeriesDto> getLatestDevicesData(String dbName, String deviceIds, String serviceIds) {
        DateTime now = DateTime.now();
        String startDate = now.plusHours(-1).toString(DATE_FORMAT);
        String endDate = now.toString(DATE_FORMAT);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        dbName = "service_values_"+sdf.format(new Date());
        //设备和测点的统计
        String[] deviceIdsArray = deviceIds.split(",");
        String[] serviceIdsArray = serviceIds.split(",");
        //设备\测点
        String deviceIdAndServiceIds = "";
        List<ChartSeriesDto> returnObj = new ArrayList<ChartSeriesDto>();
        for(int i=0;i<deviceIdsArray.length;i++){
            com.wapwag.woss.modules.home.entity.DeviceInfo device = deviceDao.get(deviceIdsArray[i]);
            for(int j=0;j<serviceIdsArray.length;j++){
                //获取点表别名
                List<Services> servicesList = servicesDao.getByDeviceAndFunName(deviceIdsArray[i], serviceIdsArray[j]);
                if(servicesList.size()>0) {
              		List<Services> lst = servicesDao.getByDeviceAndFunName(deviceIdsArray[i], serviceIdsArray[j]);
                	//String svcName = ;

                    deviceIdAndServiceIds = lst.get(0).getId() +","; //deviceIdsArray[i].concat("\\").concat(serviceIdsArray[j])+",";
                    ChartSeriesDto dto = new ChartSeriesDto();
                    List<Double> data = new ArrayList<>();
                    List<Map<String, Object>> listMap = monitorV2Mapper.getLatestDevicesData(dbName, startDate, endDate, deviceIdsArray[i], deviceIdAndServiceIds);
                    Map<String, Object> map = new HashMap<>();
                    if(!listMap.isEmpty() && listMap.size() >0){
                        map = listMap.get(0);
                    }
                    if(null != map && null != map.get("serviceValue")){
                        data.add(Double.parseDouble(map.get("serviceValue").toString()));
                    } else {
                        data.add(0.0);
                    }
                    String nuit = StringUtils.isEmpty(servicesList.get(0).getUnit())?"":"("+servicesList.get(0).getUnit()+")";
                    dto.setName(device.getProjectInfo().getProjectName()+"-"+device.getDeviceName()+"-"+servicesList.get(0).getName()+nuit);
                    dto.setData(data);
                    dto.setUnit(nuit);
//                    if(i == 0){
//                        List<Map<String, Object>> serviceData = monitorV2Mapper.getLatestDevicesData(dbName, startDate, endDate, deviceIdsArray[0],"");
//                        dto.setServiceData(serviceData);
//                    }
                    returnObj.add(dto);
                }
            }
        }
        return returnObj;
    }
    
    public List<Map<String, Object>> getLatestDevicesData(String dbName, String deviceId) {
        DateTime now = DateTime.now();
        String startDate = now.plusHours(-1).toString(DATE_FORMAT);
        String endDate = now.toString(DATE_FORMAT);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        dbName = "service_values_"+sdf.format(new Date());
    	return monitorV2Mapper.getLatestDevicesData(dbName, startDate, endDate, deviceId,"");
    }
}
