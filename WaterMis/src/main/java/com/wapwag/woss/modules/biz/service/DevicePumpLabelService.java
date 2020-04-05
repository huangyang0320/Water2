package com.wapwag.woss.modules.biz.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wapwag.woss.common.persistence.Page;
import com.wapwag.woss.common.service.CrudService;
import com.wapwag.woss.modules.biz.entity.DevicePumpLabel;
import com.wapwag.woss.modules.biz.dao.DevicePumpLabelDao;

/**
 * 水泵设备标牌Service
 * @author yuxt
 * @version 2016-08-27
 */
@Service
@Transactional(readOnly = true)
public class DevicePumpLabelService extends CrudService<DevicePumpLabelDao, DevicePumpLabel> {

	public DevicePumpLabel get(String id) {
		return super.get(id);
	}
	
	public List<DevicePumpLabel> findList(DevicePumpLabel devicePumpLabel) {
		return super.findList(devicePumpLabel);
	}
	
	public Page<DevicePumpLabel> findPage(Page<DevicePumpLabel> page, DevicePumpLabel devicePumpLabel) {
		return super.findPage(page, devicePumpLabel);
	}
	
	@Transactional(readOnly = false)
	public void save(DevicePumpLabel devicePumpLabel) {
		super.save(devicePumpLabel);
	}
	
	@Transactional(readOnly = false)
	public void delete(DevicePumpLabel devicePumpLabel) {
		super.delete(devicePumpLabel);
	}
	
}