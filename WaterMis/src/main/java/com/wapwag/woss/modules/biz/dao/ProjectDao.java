package com.wapwag.woss.modules.biz.dao;

import com.wapwag.woss.common.persistence.CrudDao;
import com.wapwag.woss.common.persistence.annotation.MyBatisDao;
import com.wapwag.woss.modules.biz.entity.Project;

/**
 * 项目DAO接口
 * @author yuxt
 * @version 2016-08-27
 */
@MyBatisDao
public interface ProjectDao extends CrudDao<Project> {
	
	
	
}