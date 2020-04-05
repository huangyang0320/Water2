package com.wapwag.woss.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wapwag.woss.common.persistence.Page;
import com.wapwag.woss.common.service.CrudService;
import com.wapwag.woss.modules.sys.dao.AttendanceDao;
import com.wapwag.woss.modules.sys.entity.AttRecord;

@Service
@Transactional(readOnly = true)
public class AttendanceService  extends CrudService<AttendanceDao, AttRecord> {
	
	@Autowired
	private AttendanceDao accessCtrlRecordsDao;
	
	public Page<AttRecord> findPage(Page<AttRecord> page, AttRecord accessCtrlRecords) {
		return super.findPage(page, accessCtrlRecords);
	}
	
	public List<AttRecord> countLoginUser(AttRecord accessCtrlRecords){
		return accessCtrlRecordsDao.countLoginUser(accessCtrlRecords);
	}

	public List<AttRecord> exportList(AttRecord info) {
		return accessCtrlRecordsDao.exportList(info);
	}
}
