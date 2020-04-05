package com.wapwag.woss.modules.biz.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wapwag.woss.common.persistence.Page;
import com.wapwag.woss.common.service.CrudService;
import com.wapwag.woss.modules.biz.entity.PumpFaultInfo;
import com.wapwag.woss.modules.biz.dao.PumpFaultInfoDao;

/**
 * 水泵故障信息Service
 * @author yuxt
 * @version 2016-08-27
 */
@Service
@Transactional(readOnly = true)
public class PumpFaultInfoService extends CrudService<PumpFaultInfoDao, PumpFaultInfo> {

	public PumpFaultInfo get(String id) {
		return super.get(id);
	}
	
	public List<PumpFaultInfo> findList(PumpFaultInfo pumpFaultInfo) {
		return super.findList(pumpFaultInfo);
	}
	
	public Page<PumpFaultInfo> findPage(Page<PumpFaultInfo> page, PumpFaultInfo pumpFaultInfo) {
		return super.findPage(page, pumpFaultInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(PumpFaultInfo pumpFaultInfo) {
		super.save(pumpFaultInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(PumpFaultInfo pumpFaultInfo) {
		super.delete(pumpFaultInfo);
	}
	
}