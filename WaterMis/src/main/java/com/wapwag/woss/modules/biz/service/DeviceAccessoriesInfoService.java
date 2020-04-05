package com.wapwag.woss.modules.biz.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wapwag.woss.common.persistence.Page;
import com.wapwag.woss.common.service.CrudService;
import com.wapwag.woss.modules.biz.entity.DeviceAccessoriesInfo;
import com.wapwag.woss.modules.biz.dao.DeviceAccessoriesInfoDao;

/**
 * 设备配件信息Service
 * @author yuxt
 * @version 2016-08-27
 */
@Service
@Transactional(readOnly = true)
public class DeviceAccessoriesInfoService extends CrudService<DeviceAccessoriesInfoDao, DeviceAccessoriesInfo> {

	public DeviceAccessoriesInfo get(String id) {
		return super.get(id);
	}
	
	public List<DeviceAccessoriesInfo> findList(DeviceAccessoriesInfo deviceAccessoriesInfo) {
		return super.findList(deviceAccessoriesInfo);
	}
	
	public Page<DeviceAccessoriesInfo> findPage(Page<DeviceAccessoriesInfo> page, DeviceAccessoriesInfo deviceAccessoriesInfo) {
		return super.findPage(page, deviceAccessoriesInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(DeviceAccessoriesInfo deviceAccessoriesInfo) {
		super.save(deviceAccessoriesInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(DeviceAccessoriesInfo deviceAccessoriesInfo) {
		super.delete(deviceAccessoriesInfo);
	}
	
}