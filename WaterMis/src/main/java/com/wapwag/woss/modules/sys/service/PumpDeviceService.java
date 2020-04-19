package com.wapwag.woss.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wapwag.woss.common.persistence.Page;
import com.wapwag.woss.common.service.CrudService;
import com.wapwag.woss.modules.sys.entity.PumpHouse;
import com.wapwag.woss.modules.sys.dao.DeviceDao;
import com.wapwag.woss.modules.sys.dao.ProjectDao;
import com.wapwag.woss.modules.sys.dao.PumpDeviceRepairDao;
import com.wapwag.woss.modules.sys.dao.PumpServiceValueDao;
import com.wapwag.woss.modules.sys.entity.Alarm;
import com.wapwag.woss.modules.sys.entity.Archive;
import com.wapwag.woss.modules.sys.entity.PumpDevice;
import com.wapwag.woss.modules.sys.entity.DeviceAccessories;
import com.wapwag.woss.modules.sys.entity.Project;

/**
 * 设备Service
 */
@Service
@Transactional(readOnly = true)
public class PumpDeviceService extends CrudService<DeviceDao, PumpDevice> {

	@Autowired
	private DeviceDao deviceDao;

	@Autowired
	private PumpDeviceRepairDao pumpDeviceRepairDao;
	
	@Autowired
	private PumpServiceValueDao pumpSensorDataDao;
	
	@Autowired
	private ProjectDao projectDao;

	public Page<PumpDevice> findPage(Page<PumpDevice> page, PumpDevice deviceInfo) {
		return super.findPage(page, deviceInfo);

	}

	public List<PumpHouse> findPumpHouse(PumpHouse pumpHouse) {
		return deviceDao.findPumpHouse(pumpHouse);
	}


	public Archive getArchiveByDevId(String deviceId) {
		return deviceDao.getArchiveByDevId(deviceId);
	}
	
	public List<Project> getProject() {
		return projectDao.getProjectNameAndId();
	}
	
	public List<Project> getProjectByRole(String userId) {
		return projectDao.getProjectNameAndIdAndRole(userId);
	}
	
	public List<PumpDevice> getAreaList(String userId) {
		return deviceDao.getAreaList(userId);
	}
	
	public List<PumpDevice> getPumpHouseByAreaId(String id) {
		return deviceDao.getPumpHouseByAreaId(id);
	}

	public List<PumpDevice> getDeviceByProjectId(PumpDevice info) {
		return deviceDao.getDeviceByProjectId(info);
	}

	public List<DeviceAccessories> getAccessoriesByDevId(String deviceId) {
		return deviceDao.getAccessoriesByDevId(deviceId);
	}

	public List<PumpHouse> pumpHouses(Alarm info) {
		return deviceDao.pumpHouses(info);
	}

	public List<PumpDevice> getProjectByAreaId(String areaId,String userId) {
		return deviceDao.getProjectByAreaId(areaId,userId);
	}

	public List<Alarm> pumpCount(PumpHouse info) {
		if ("area".equals(info.getMemo())) {
			return deviceDao.pumpCountArea(info);
		}else {
			return deviceDao.pumpCountLocal(info);
		}
	}

	public String countPumpHouse(Alarm alarmInfo) {
		return deviceDao.countPumpHouse(alarmInfo);
	}

	public List<PumpDevice> getDeviceIdByPumpId(String pumpId) {
		return deviceDao.getDeviceIdByPumpId(pumpId);
	}
}
