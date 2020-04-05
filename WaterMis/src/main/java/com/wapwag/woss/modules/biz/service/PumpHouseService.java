package com.wapwag.woss.modules.biz.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wapwag.woss.common.persistence.Page;
import com.wapwag.woss.common.service.CrudService;
import com.wapwag.woss.modules.biz.entity.PumpHouse;
import com.wapwag.woss.modules.biz.dao.PumpHouseDao;

/**
 * 泵房Service
 * @author yuxt
 * @version 2016-08-27
 */
@Service
@Transactional(readOnly = true)
public class PumpHouseService extends CrudService<PumpHouseDao, PumpHouse> {

	public PumpHouse get(String id) {
		return super.get(id);
	}
	
	public List<PumpHouse> findList(PumpHouse pumpHouse) {
		return super.findList(pumpHouse);
	}
	
	public Page<PumpHouse> findPage(Page<PumpHouse> page, PumpHouse pumpHouse) {
		return super.findPage(page, pumpHouse);
	}
	
	@Transactional(readOnly = false)
	public void save(PumpHouse pumpHouse) {
		super.save(pumpHouse);
	}
	
	@Transactional(readOnly = false)
	public void delete(PumpHouse pumpHouse) {
		super.delete(pumpHouse);
	}
	
}