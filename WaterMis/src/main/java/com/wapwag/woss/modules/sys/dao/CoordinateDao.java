
package com.wapwag.woss.modules.sys.dao;

import java.util.List;

import com.wapwag.woss.common.persistence.CrudDao;
import com.wapwag.woss.common.persistence.QryObject;
import com.wapwag.woss.common.persistence.annotation.MyBatisDao;
import com.wapwag.woss.modules.sys.entity.Coordinate;

/**
 * 告警日志DAO接口
 */
@MyBatisDao
public interface CoordinateDao extends CrudDao<Coordinate> {

	
	List<Coordinate> getCoordinateByUserId(QryObject info);
	
	List<Coordinate> getLastCoordinateByUserId(String id);
	
	List<Coordinate> getUsers(QryObject info);
	
	int countSum();
}
