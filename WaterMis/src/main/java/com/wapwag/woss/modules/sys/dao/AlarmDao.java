package com.wapwag.woss.modules.sys.dao;

import java.util.List;

import com.wapwag.woss.common.persistence.CrudDao;
import com.wapwag.woss.common.persistence.annotation.MyBatisDao;
import com.wapwag.woss.modules.sys.entity.Alarm;

/**
 * 告警日志DAO接口
 */
@MyBatisDao("com.wapwag.woss.modules.sys.dao.AlarmDao")
public interface AlarmDao extends CrudDao<Alarm> {

	
	List<Alarm> areaAlarmCount(Alarm alarm);
	List<Alarm> typeAlarmCount(Alarm alarm);
	List<Alarm> findList(Alarm alarm);
	
	List<Alarm> getAlarmType(String type);
	
	List<Alarm> exportList(Alarm alarm);
	String findListCount(Alarm alarm);
	List<Alarm> countAlarmTimes(Alarm alarm);
}
