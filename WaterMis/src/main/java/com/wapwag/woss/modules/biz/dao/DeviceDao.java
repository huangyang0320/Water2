package com.wapwag.woss.modules.biz.dao;

import com.wapwag.woss.common.persistence.CrudDao;
import com.wapwag.woss.common.persistence.annotation.MyBatisDao;
import com.wapwag.woss.modules.biz.entity.Device;

/**
 * 设备DAO接口
 * @author yuxt
 * @version 2016-08-27
 */
@MyBatisDao
public interface DeviceDao extends CrudDao<Device> {
	
}