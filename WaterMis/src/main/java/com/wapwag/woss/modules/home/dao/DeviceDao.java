package com.wapwag.woss.modules.home.dao;

import com.wapwag.woss.common.persistence.CrudDao;
import com.wapwag.woss.common.persistence.annotation.MyBatisDao;
import com.wapwag.woss.modules.biz.entity.*;
import com.wapwag.woss.modules.home.entity.DeviceInfo;
import com.wapwag.woss.modules.home.entity.DeviceMeta;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * DeviceInfo dao Created by Administrator on 2016/8/2.
 */
@MyBatisDao("com.wapwag.woss.modules.home.dao.DeviceDao")
public interface DeviceDao extends CrudDao<DeviceInfo> {

	String findDeviceRealTimeData(@Param("deviceId") String deviceId,
			@Param("dimen") String dimen, @Param("tableName") String tableName);

	List<DeviceMeta> findDeviceMeta(@Param("deviceId") String deviceId);

	List<Map<String, String>> findDeviceConsumeMeta(
			@Param("pumphouseIds") String pumphouseIds);

	List<Map<String, String>> findDeviceHistoryData(
			@Param("deviceId") String deviceId, @Param("index") String index,
			@Param("dimen") String dimen, @Param("startDate") String startDate,
			@Param("endDate") String endDate);

	List<Map<String, String>> findDeviceConsume(
			@Param("deviceId") String deviceId, @Param("dimen") String dimen,
			@Param("startDate") String startDate,
			@Param("endDate") String endDate);

	List<Map<String, String>> findDeviceConsumeSpecial(
			@Param("deviceIndexs") String pumphouseIds,
			@Param("dimen") String dimen, @Param("startDate") String startDate,
			@Param("endDate") String endDate);

	List<Map<String, String>> findDiffDeviceConsume(
			@Param("deviceIds") String deviceIds, @Param("dimen") String dimen,
			@Param("startDate") String startDate,
			@Param("endDate") String endDate);

	List<Map<String, String>> findDiffDeviceConsumeSpecial(
			@Param("deviceIndexs") String pumphouseIds,
			@Param("dimen") String dimen, @Param("startDate") String startDate,
			@Param("endDate") String endDate);

	List<Device> mapRealDetail(@Param("pumpHouseId") String pumpHouseId,
			@Param("tableName") String tableName);

	List<Map<String, Object>> getLatestDeviceData(
			@Param("startDate") String startDate,
			@Param("endDate") String endDate,
			@Param("deviceId") String deviceId,
			@Param("serviceId") String serviceId,
			@Param("tableName") String tableName);

	List<Map<String, Object>> getServiceData(
			@Param("startDate") String startDate,
			@Param("endDate") String endDate,
			@Param("deviceId") String deviceId,
			@Param("serviceIds") String serviceIds,
			@Param("tableName") String tableName);

	List<Map<String, Object>> getServiceInfo(@Param("deviceId") String deviceId);

	List<Device> mapOnlineStat(@Param("deviceId") String deviceId,
			@Param("tableName") String tableName);

	List<String> pumpOnlineStat(
			@Param("deviceId") List<DevicePumpHoseDto> deviceId,
			@Param("tableName") String tableName);

	// 根据泵房找到设备
	// 补充受角色 组织下的角色约束
	List<TreeInfo> findDeviceByPumpHouse(@Param("userId") String userId);

	// 第二种 区域农村 下的 设备 角色 等关联
	List<TreeInfo> findDeviceByPumpHouseNew(@Param("userId") String userId);

	// 水务所节点树
	List<TreeInfo> findDeviceByPumpHouseAndOffice(@Param("userId") String userId);

	// 营业所节点树
	List<TreeInfo> findDeviceByPumpHouseAndBusiness(
			@Param("userId") String userId);

	// 水司节点树
	List<TreeInfo> findDeviceByWaterDepart(@Param("userId") String userId);

	// 泵房下的设备
	List<com.wapwag.woss.modules.biz.entity.DeviceInfo> findDeviceListByPumpHouse(
			@Param("pumpHouseId") String pumpHouseId);

	List<PointInfo> findDeviceRealTimeDataByPoint(
			@Param("deviceId") String deviceId, @Param("dimen") String dimen,
			@Param("tableName") String tableName);

	// 查询历史记录数据
	List<ServiceValueDto> findHistoryDataByDto(
			MonitorHistoryDto monitorHistoryDto);

	// 水司 区域 农村 泵房 设备
	List<TreeInfo> findDeviceByWaterDepartAndType(@Param("userId") String userId);

	List<DevicePumpHoseDto> findDeviceByPumpHoseId(
			@Param("pumpId") List<String> pumpId);

	List<String> findDevice(@Param("tableName") String tableName);

	List<String> findPump(@Param("deviceId") List<String> pumpId);
}
