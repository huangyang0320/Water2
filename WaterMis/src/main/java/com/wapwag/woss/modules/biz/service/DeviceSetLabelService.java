package com.wapwag.woss.modules.biz.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wapwag.woss.common.persistence.Page;
import com.wapwag.woss.common.service.CrudService;
import com.wapwag.woss.modules.biz.entity.DeviceSetLabel;
import com.wapwag.woss.modules.biz.dao.DeviceSetLabelDao;

/**
 * 设备配置标牌Service
 * @author yuxt
 * @version 2016-08-27
 */
@Service
@Transactional(readOnly = true)
public class DeviceSetLabelService extends CrudService<DeviceSetLabelDao, DeviceSetLabel> {

	public DeviceSetLabel get(String id) {
		return super.get(id);
	}
	
	public List<DeviceSetLabel> findList(DeviceSetLabel deviceSetLabel) {
		return super.findList(deviceSetLabel);
	}
	
	public Page<DeviceSetLabel> findPage(Page<DeviceSetLabel> page, DeviceSetLabel deviceSetLabel) {
		return super.findPage(page, deviceSetLabel);
	}
	
	@Transactional(readOnly = false)
	public void save(DeviceSetLabel deviceSetLabel) {
		super.save(deviceSetLabel);
	}
	
	@Transactional(readOnly = false)
	public void delete(DeviceSetLabel deviceSetLabel) {
		super.delete(deviceSetLabel);
	}
	
}