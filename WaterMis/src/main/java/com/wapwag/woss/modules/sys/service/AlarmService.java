package com.wapwag.woss.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wapwag.woss.common.persistence.Page;
import com.wapwag.woss.common.service.CrudService;
import com.wapwag.woss.modules.sys.dao.AccessCtrlRecordsDao;
import com.wapwag.woss.modules.sys.dao.AlarmDao;
import com.wapwag.woss.modules.sys.entity.Alarm;

/**
 * 告警日志Service
 */
@Service
@Transactional(readOnly = true)
public class AlarmService extends CrudService<AlarmDao, Alarm> {

	@Autowired
	private AlarmDao alarmDao;
	
	public Page<Alarm> findPage(Page<Alarm> page, Alarm alarmInfo) {
		return super.findPage(page, alarmInfo);
		
	}
	
	public List<Alarm> areaAlarmCount(Alarm alarmInfo) {
		if ("area".equals(alarmInfo.getRemarks())) {
			return alarmDao.areaAlarmCount(alarmInfo);	
		}
		return alarmDao.typeAlarmCount(alarmInfo);
		
	}

	public List<Alarm> getAlarmType(String type) {
		return alarmDao.getAlarmType(type);
	}
	
	public List<Alarm> exportList(Alarm alarm) {
		return alarmDao.exportList(alarm);
	}
	
	public List<Alarm> findAllList(Alarm alarm) {
		return alarmDao.findAllList(alarm);
	}
	
	public List<Alarm> countAlarmTimes(Alarm alarm) {
		return alarmDao.countAlarmTimes(alarm);
	}
	
	public String findListCount(Alarm alarm) {
		return alarmDao.findListCount(alarm);
	}
	
}
