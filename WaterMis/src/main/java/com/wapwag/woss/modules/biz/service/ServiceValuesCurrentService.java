package com.wapwag.woss.modules.biz.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wapwag.woss.common.service.CrudService;
import com.wapwag.woss.modules.biz.dao.ServiceValuesCurrentDao;
import com.wapwag.woss.modules.biz.entity.ServiceValuesCurrent;

@Service
@Transactional(readOnly = true)
public class ServiceValuesCurrentService extends CrudService<ServiceValuesCurrentDao,ServiceValuesCurrent>{
	
	@Autowired
	private ServiceValuesCurrentDao serviceValuesCurrentDao;
	
	@Transactional(readOnly = false)
	public void saveCurrentValues() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		serviceValuesCurrentDao.saveOrUpdate("service_values_"+sdf.format(new Date()));
	}
}
