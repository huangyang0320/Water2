package com.wapwag.woss.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wapwag.woss.common.persistence.Page;
import com.wapwag.woss.common.service.CrudService;
import com.wapwag.woss.modules.sys.dao.AccessCtrlRecordsDao;
import com.wapwag.woss.modules.sys.entity.AccessCtrlRecords;

@Service
@Transactional(readOnly = true)
public class AccessCtrlRecordsService  extends CrudService<AccessCtrlRecordsDao, AccessCtrlRecords> {
	
	@Autowired
	private AccessCtrlRecordsDao accessCtrlRecordsDao;
	
	public Page<AccessCtrlRecords> findPage(Page<AccessCtrlRecords> page, AccessCtrlRecords accessCtrlRecords) {
		return super.findPage(page, accessCtrlRecords);
	}
	
	public List<AccessCtrlRecords> countLoginUser(AccessCtrlRecords accessCtrlRecords){
		return accessCtrlRecordsDao.countLoginUser(accessCtrlRecords);
	}

	public List<AccessCtrlRecords> exportList(AccessCtrlRecords info) {
		return accessCtrlRecordsDao.exportList(info);
	}
}
