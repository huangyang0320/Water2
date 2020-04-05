package com.wapwag.woss.modules.biz.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wapwag.woss.common.persistence.Page;
import com.wapwag.woss.common.service.CrudService;
import com.wapwag.woss.modules.biz.entity.AccessDevice;
import com.wapwag.woss.modules.biz.dao.AccessDeviceDao;

/**
 * 门禁Service
 * @author yuxt
 * @version 2016-08-27
 */
@Service
@Transactional(readOnly = true)
public class AccessDeviceService extends CrudService<AccessDeviceDao, AccessDevice> {

	public AccessDevice get(String id) {
		return super.get(id);
	}
	
	public List<AccessDevice> findList(AccessDevice accessDevice) {
		return super.findList(accessDevice);
	}
	
	public Page<AccessDevice> findPage(Page<AccessDevice> page, AccessDevice accessDevice) {
		return super.findPage(page, accessDevice);
	}
	
	@Transactional(readOnly = false)
	public void save(AccessDevice accessDevice) {
		super.save(accessDevice);
	}
	
	@Transactional(readOnly = false)
	public void delete(AccessDevice accessDevice) {
		super.delete(accessDevice);
	}
	
}