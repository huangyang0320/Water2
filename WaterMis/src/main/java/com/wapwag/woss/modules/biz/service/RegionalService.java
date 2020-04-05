package com.wapwag.woss.modules.biz.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wapwag.woss.common.persistence.Page;
import com.wapwag.woss.common.service.CrudService;
import com.wapwag.woss.modules.biz.entity.Regional;
import com.wapwag.woss.modules.biz.dao.RegionalDao;

/**
 * 地区Service
 * @author yuxt
 * @version 2016-08-27
 */
@Service
@Transactional(readOnly = true)
public class RegionalService extends CrudService<RegionalDao, Regional> {

	public Regional get(String id) {
		return super.get(id);
	}
	
	public List<Regional> findList(Regional regional) {
		return super.findList(regional);
	}
	
	public Page<Regional> findPage(Page<Regional> page, Regional regional) {
		return super.findPage(page, regional);
	}
	
	@Transactional(readOnly = false)
	public void save(Regional regional) {
		super.save(regional);
	}
	
	@Transactional(readOnly = false)
	public void delete(Regional regional) {
		super.delete(regional);
	}
	
}