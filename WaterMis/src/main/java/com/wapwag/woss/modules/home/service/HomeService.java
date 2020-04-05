package com.wapwag.woss.modules.home.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.wapwag.woss.modules.home.dao.AlarmDao;
import com.wapwag.woss.modules.home.dao.DeviceDao;
import com.wapwag.woss.modules.home.dao.ProjectDao;
import com.wapwag.woss.modules.home.dao.PumpHouseDao;
import com.wapwag.woss.modules.home.dao.RegionalDao;
import com.wapwag.woss.modules.home.entity.AlarmInfo;
import com.wapwag.woss.modules.home.entity.DeviceMeta;
import com.wapwag.woss.modules.home.entity.RegionalInfo;

/**
 * HomeService
 * Created by Administrator on 2016/8/2.
 */
@Service
public class HomeService {

    private final RegionalDao regionalDao;

    private final ProjectDao projectDao;

    private final PumpHouseDao pumpHouseDao;

    private final DeviceDao deviceDao;

    private final AlarmDao alarmDao;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    public HomeService(RegionalDao regionalDao, ProjectDao projectDao, PumpHouseDao pumpHouseDao, DeviceDao deviceDao, AlarmDao alarmDao) {
        this.regionalDao = regionalDao;
        this.projectDao = projectDao;
        this.pumpHouseDao = pumpHouseDao;
        this.deviceDao = deviceDao;
        this.alarmDao = alarmDao;
    }

//    @Cacheable(value = "deviceMetaCache", key = "'device-meta-' + #deviceId")
    public List<DeviceMeta> queryDeviceMeta(String deviceId) {
        return deviceDao.findDeviceMeta(deviceId);
    }

    public List<Map<String, String>> queryDeviceConsumeMeta(String pumphouseIds) {
        return deviceDao.findDeviceConsumeMeta(pumphouseIds);
    }

//    @Cacheable(value = "deviceCache", key = "'user-' + #userId")
    public Map<String, Object> queryAllDevice(String userId) {
        Map<String, Object> resultMap = Maps.newHashMap();
        List<RegionalInfo> aa = regionalDao.findAllDevice(userId);
        resultMap.put("deviceList", aa);
        return resultMap;
    }

//    @Cacheable(value = "deviceCache", key = "'device-' + #deviceId")
    public Map<String, Object> queryDevice(String deviceId) {
        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("device", deviceDao.get(deviceId));
        return resultMap;
    }

    public List<AlarmInfo> queryLatestAlarms(String userId) {
        Map<String, String> dateMap = getStartDateAndEndDate();
        return alarmDao.findLatestAlarms(userId, dateMap.get("startDate"), dateMap.get("endDate"));
       /* String startDate="2018-03-29 10:40:54";
        String endDate = "2018-03-29 14:43:07";
        return alarmDao.findLatestAlarms(userId, startDate, endDate);*/
    }

    public long queryLatestAlarmsCount(String userId) {
        DateTime dateTime = new DateTime();
        String startDate = DATE_TIME_FORMATTER.print(dateTime.withTime(0, 0, 0, 0));
        String endDate = DATE_TIME_FORMATTER.print(dateTime);

        return alarmDao.getCount(userId, startDate, endDate);
    }
    @SuppressWarnings("unused")
	private Map<String,String>  getStartDateAndEndDate(){
        Map<String,String> dateMap=new HashMap<String,String>();
        DateTime dateTime = new DateTime();
        String startDate = DATE_TIME_FORMATTER.print(dateTime.withTime(0, 0, 0, 0));
        String endDate = DATE_TIME_FORMATTER.print(dateTime);
        dateMap.put("startDate",startDate);
        dateMap.put("endDate",endDate);
        return dateMap;
    }
}
