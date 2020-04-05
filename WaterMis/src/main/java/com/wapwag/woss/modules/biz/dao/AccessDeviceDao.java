package com.wapwag.woss.modules.biz.dao;

import com.wapwag.woss.common.persistence.CrudDao;
import com.wapwag.woss.common.persistence.annotation.MyBatisDao;
import com.wapwag.woss.modules.biz.entity.AccessDevice;

import java.util.List;

/**
 * 门禁DAO接口
 * @author yuxt
 * @version 2016-08-27
 */
@MyBatisDao("com.wapwag.woss.modules.biz.dao.AccessDeviceDao")
public interface AccessDeviceDao extends CrudDao<AccessDevice> {
	List<AccessDevice> findByPumpId(String pumpId);
}