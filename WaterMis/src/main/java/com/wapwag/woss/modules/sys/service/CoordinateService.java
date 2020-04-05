package com.wapwag.woss.modules.sys.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wapwag.woss.common.persistence.Page;
import com.wapwag.woss.common.persistence.QryObject;
import com.wapwag.woss.common.service.CrudService;
import com.wapwag.woss.modules.sys.dao.CoordinateDao;
import com.wapwag.woss.modules.sys.entity.BootPage;
import com.wapwag.woss.modules.sys.entity.Coordinate;
import com.wapwag.woss.modules.sys.entity.PumpServiceValue;

/**
 * 设备Service
 */
@Service
@Transactional(readOnly = true)
public class CoordinateService extends CrudService<CoordinateDao, Coordinate> {
	@Autowired
	private CoordinateDao coordinateDao;
	
	public List<Coordinate> getUsers(QryObject info) {
		return coordinateDao.getUsers(info);
		
	}
	
	public int coordinateDao() {
		return coordinateDao.countSum();
	}
	
	public List<Coordinate> getLastCoordinateByUserId(String id){
		return coordinateDao.getLastCoordinateByUserId(id);
	}
	
	public List<Coordinate> getCoordinateByUserId(QryObject info){
		return coordinateDao.getCoordinateByUserId(info);
	}
	
}
