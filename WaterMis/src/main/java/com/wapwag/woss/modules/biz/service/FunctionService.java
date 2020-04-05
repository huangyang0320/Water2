package com.wapwag.woss.modules.biz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wapwag.woss.common.persistence.Page;
import com.wapwag.woss.common.service.CrudService;
import com.wapwag.woss.modules.biz.entity.Function;
import com.wapwag.woss.modules.biz.dao.FunctionDao;

/**
 * 功能Service
 * @author yuxt
 * @version 2016-08-27
 */
@Service
@Transactional(readOnly = true)
public class FunctionService extends CrudService<FunctionDao, Function> {
	
	@Autowired
	private FunctionDao FunctionDao;

	public Function get(String id) {
		return super.get(id);
	}
	
	public List<Function> findList(Function function) {
		return super.findList(function);
	}
	
	public Page<Function> findPage(Page<Function> page, Function function) {
		return super.findPage(page, function);
	}
	
	@Transactional(readOnly = false)
	public void save(Function function) {
		super.save(function);
	}
	
	@Transactional(readOnly = false)
	public void delete(Function function) {
		super.delete(function);
	}

	public List<Function> getByDeviceIds(String deviceIds) {
		return FunctionDao.getByDeviceIds(deviceIds);
	}
	
}