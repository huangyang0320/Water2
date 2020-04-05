package com.wapwag.woss.modules.biz.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wapwag.woss.common.persistence.Page;
import com.wapwag.woss.common.service.CrudService;
import com.wapwag.woss.modules.biz.entity.AlarmInfo;
import com.wapwag.woss.modules.biz.dao.AlarmInfoDao;

/**
 * 报警信息Service
 * @author yuxt
 * @version 2016-08-27
 */
@Service
@Transactional(readOnly = true)
public class AlarmInfoService extends CrudService<AlarmInfoDao, AlarmInfo> {

	public AlarmInfo get(String id) {
		return super.get(id);
	}
	
	public List<AlarmInfo> findList(AlarmInfo alarmInfo) {
		return super.findList(alarmInfo);
	}
	
	public Page<AlarmInfo> findPage(Page<AlarmInfo> page, AlarmInfo alarmInfo) {
		return super.findPage(page, alarmInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(AlarmInfo alarmInfo) {
		super.save(alarmInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(AlarmInfo alarmInfo) {
		super.delete(alarmInfo);
	}
	
}