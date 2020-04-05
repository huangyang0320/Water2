package com.wapwag.woss.modules.biz.dao;

import com.wapwag.woss.common.persistence.CrudDao;
import com.wapwag.woss.common.persistence.annotation.MyBatisDao;
import com.wapwag.woss.modules.biz.entity.PermissionInfo;

/**
 * 授权信息DAO接口
 * @author yuxt
 * @version 2016-08-27
 */
@MyBatisDao
public interface PermissionInfoDao extends CrudDao<PermissionInfo> {
	
}