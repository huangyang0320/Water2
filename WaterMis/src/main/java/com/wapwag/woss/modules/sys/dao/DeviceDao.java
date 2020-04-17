package com.wapwag.woss.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wapwag.woss.common.persistence.CrudDao;
import com.wapwag.woss.common.persistence.annotation.MyBatisDao;
import com.wapwag.woss.modules.sys.entity.PumpHouse;
import com.wapwag.woss.modules.sys.entity.Alarm;
import com.wapwag.woss.modules.sys.entity.Archive;
import com.wapwag.woss.modules.sys.entity.PumpDevice;
import com.wapwag.woss.modules.sys.entity.DeviceAccessories;

@MyBatisDao("com.wapwag.woss.modules.sys.dao.DeviceDao")
public interface DeviceDao extends CrudDao<PumpDevice> {
	Archive getArchiveByDevId(String deviceId);
	
	List<PumpDevice> getDeviceByProjectId(PumpDevice info);
	List<PumpDevice> getAreaList(String userId);
	List<PumpDevice> getPumpHouseByAreaId(String id);

	List<DeviceAccessories> getAccessoriesByDevId(String deviceId);

	List<PumpDevice> getProjectByAreaId(@Param("areaId") String areaId,@Param("userId") String userId);

	String countPumpHouse(Alarm alarmInfo);

	List<PumpHouse> pumpHouses(Alarm info);

	List<Alarm> pumpCountArea(PumpHouse info);

	List<Alarm> pumpCountLocal(PumpHouse info);

	List<PumpDevice> getDeviceIdByPumpId(String pumpId);

	List<PumpHouse> findPumpHouse(PumpHouse pumpHouse);
}