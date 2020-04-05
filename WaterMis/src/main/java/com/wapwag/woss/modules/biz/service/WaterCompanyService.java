package com.wapwag.woss.modules.biz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wapwag.woss.common.persistence.Page;
import com.wapwag.woss.common.service.CrudService;
import com.wapwag.woss.modules.biz.dao.WaterCompanyDao;
import com.wapwag.woss.modules.biz.entity.TreeInfo;
import com.wapwag.woss.modules.biz.entity.WaterCompany;

/**
 * 水司管理
 * @author zhaom
 *
 */
@Service
@Transactional(readOnly = true)
public class WaterCompanyService extends CrudService<WaterCompanyDao, WaterCompany>{

	@Autowired
	private WaterCompanyDao waterCompanyDao;
	
	public WaterCompany get(String id) {
		return super.get(id);
	}
	
	public List<WaterCompany> findList(WaterCompany waterCompany) {
		return super.findList(waterCompany);
	}
	
	public Page<WaterCompany> findPage(Page<WaterCompany> page, WaterCompany waterCompany) {
		return super.findPage(page, waterCompany);
	}
	
	@Transactional(readOnly = false)
	public void save(WaterCompany waterCompany) {
		super.save(waterCompany);
	}
	
	
	@Transactional(readOnly = false)
	public void delete(WaterCompany waterCompany) {
		super.delete(waterCompany);
	}
	
	public List<TreeInfo> waterCompanyTree(){
		return waterCompanyDao.waterCompanyTree();			
	}
	
	public List<WaterCompany> getByPid(String id ){
		return waterCompanyDao.getByPid(id);			
	}
	
}