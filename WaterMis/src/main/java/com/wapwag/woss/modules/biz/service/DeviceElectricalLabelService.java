package com.wapwag.woss.modules.biz.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wapwag.woss.common.persistence.Page;
import com.wapwag.woss.common.service.CrudService;
import com.wapwag.woss.modules.biz.entity.DeviceElectricalLabel;
import com.wapwag.woss.modules.biz.dao.DeviceElectricalLabelDao;

/**
 * 电气设备标牌Service
 * @author yuxt
 * @version 2016-08-27
 */
@Service
@Transactional(readOnly = true)
public class DeviceElectricalLabelService extends CrudService<DeviceElectricalLabelDao, DeviceElectricalLabel> {

	public DeviceElectricalLabel get(String id) {
		return super.get(id);
	}
	
	public List<DeviceElectricalLabel> findList(DeviceElectricalLabel deviceElectricalLabel) {
		return super.findList(deviceElectricalLabel);
	}
	
	public Page<DeviceElectricalLabel> findPage(Page<DeviceElectricalLabel> page, DeviceElectricalLabel deviceElectricalLabel) {
		return super.findPage(page, deviceElectricalLabel);
	}
	
	@Transactional(readOnly = false)
	public void save(DeviceElectricalLabel deviceElectricalLabel) {
		super.save(deviceElectricalLabel);
	}
	
	@Transactional(readOnly = false)
	public void delete(DeviceElectricalLabel deviceElectricalLabel) {
		super.delete(deviceElectricalLabel);
	}
	
}