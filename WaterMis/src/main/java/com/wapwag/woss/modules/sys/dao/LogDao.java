package com.wapwag.woss.modules.sys.dao;

import com.wapwag.woss.common.persistence.CrudDao;
import com.wapwag.woss.common.persistence.annotation.MyBatisDao;
import com.wapwag.woss.modules.sys.entity.Log;

/**
 * 日志DAO接口
 */
@MyBatisDao
public interface LogDao extends CrudDao<Log> {

}
