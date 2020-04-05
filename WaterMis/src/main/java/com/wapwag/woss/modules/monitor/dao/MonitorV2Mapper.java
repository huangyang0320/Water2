package com.wapwag.woss.modules.monitor.dao;

import com.wapwag.woss.common.persistence.annotation.MyBatisDao;
import com.wapwag.woss.modules.biz.entity.ServiceValuesCurrent;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author ChangWei Li
 * @version 2017-12-26 13:10
 */
@MyBatisDao("com.wapwag.woss.modules.monitor.dao.MonitorV2Mapper")
public interface MonitorV2Mapper {
	
	List<Map<String, Object>> getLatestDeviceData(@Param("dbName") String dbName, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("deviceId") String deviceId, @Param("serviceId") String serviceId);

    List<Map<String, Object>> getServiceData(@Param("dbName") String dbName, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("deviceId") String deviceId, @Param("serviceIds") String serviceIds);

    List<Map<String, Object>> getServiceInfo( @Param("deviceId") String deviceId);

    List<Map<String, Object>> getLatestDevicesData(@Param("dbName") String dbName, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("deviceId") String deviceId, @Param("serviceId") String serviceId);
}
