package com.wapwag.woss.modules.biz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wapwag.woss.common.persistence.Page;
import com.wapwag.woss.common.service.CrudService;
import com.wapwag.woss.modules.biz.entity.PumpDeviceRepairInfo;
import com.wapwag.woss.modules.biz.dao.PumpDeviceRepairInfoDao;

/**
 * 水泵故障维护信息Service
 * @author yuxt
 * @version 2016-08-27
 */
@Service
@Transactional(readOnly = true)
public class PumpDeviceRepairInfoService extends CrudService<PumpDeviceRepairInfoDao, PumpDeviceRepairInfo> {
	
	@Autowired
	private PumpDeviceRepairInfoDao pumpDeviceRepairInfoDao;

	public PumpDeviceRepairInfo get(String id) {
		return super.get(id);
	}
	
	public List<PumpDeviceRepairInfo> findList(PumpDeviceRepairInfo pumpDeviceRepairInfo) {
		return super.findList(pumpDeviceRepairInfo);
	}
	
	public Page<PumpDeviceRepairInfo> findPage(Page<PumpDeviceRepairInfo> page, PumpDeviceRepairInfo pumpDeviceRepairInfo) {
		return super.findPage(page, pumpDeviceRepairInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(PumpDeviceRepairInfo pumpDeviceRepairInfo) {
		super.save(pumpDeviceRepairInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(PumpDeviceRepairInfo pumpDeviceRepairInfo) {
		super.delete(pumpDeviceRepairInfo);
	}
	  
	    /**  
	    * @Title: getLastReplaceInfo  
	    * @Description: TODO(获取更换配件的list 按照时间倒叙)  
	    * @param @param deviceId
	    * @param @param accessoriesXh
	    * @param @return    参数  
	    * @return List<PumpDeviceRepairInfo>    返回类型  
	    * @throws  
	    */  
	public List<PumpDeviceRepairInfo> getLastReplaceInfo(String deviceId, String accessoriesXh) {
		return pumpDeviceRepairInfoDao.getLastReplaceInfo(deviceId, accessoriesXh);
	}
	
}