package com.wapwag.woss.modules.biz.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wapwag.woss.common.persistence.CrudDao;
import com.wapwag.woss.common.persistence.annotation.MyBatisDao;
import com.wapwag.woss.modules.biz.entity.Function;

/**
 * 功能DAO接口
 * @author yuxt
 * @version 2016-08-27
 */
@MyBatisDao
public interface FunctionDao extends CrudDao<Function> {
	List<Function> getByDeviceIds(@Param("deviceIds")String deviceIds);
	
}