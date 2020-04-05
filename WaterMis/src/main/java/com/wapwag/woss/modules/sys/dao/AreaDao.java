package com.wapwag.woss.modules.sys.dao;

import com.wapwag.woss.common.persistence.TreeDao;
import com.wapwag.woss.common.persistence.annotation.MyBatisDao;
import com.wapwag.woss.modules.sys.entity.Area;

/**
 * 区域DAO接口
 */
@MyBatisDao
public interface AreaDao extends TreeDao<Area> {
	
}
