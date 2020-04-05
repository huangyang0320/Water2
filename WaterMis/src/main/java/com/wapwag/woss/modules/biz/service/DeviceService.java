package com.wapwag.woss.modules.biz.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wapwag.woss.common.persistence.Page;
import com.wapwag.woss.common.service.CrudService;
import com.wapwag.woss.modules.biz.entity.Device;
import com.wapwag.woss.modules.biz.dao.DeviceDao;

/**
 * 设备Service
 * @author yuxt
 * @version 2016-08-27
 */
@Service
@Transactional(readOnly = true)
public class DeviceService extends CrudService<DeviceDao, Device> {

	public Device get(String id) {
		return super.get(id);
	}
	
	public List<Device> findList(Device device) {
		return super.findList(device);
	}
	
	public Page<Device> findPage(Page<Device> page, Device device) {
		return super.findPage(page, device);
	}
	
	@Transactional(readOnly = false)
	public void save(Device device) {
		super.save(device);
	}
	
	@Transactional(readOnly = false)
	public void delete(Device device) {
		super.delete(device);
	}
	
}