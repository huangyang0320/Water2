package com.wapwag.woss.modules.sys.dao;

import com.wapwag.woss.common.persistence.CrudDao;
import com.wapwag.woss.common.persistence.annotation.MyBatisDao;
import com.wapwag.woss.modules.sys.entity.Alarm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 标准代码DAO接口
 */
@MyBatisDao("com.wapwag.woss.modules.sys.dao.SortDao")
public interface SortDao extends CrudDao<Alarm> {

	
	String getSortValueByCode(@Param(value = "code") String code);
}
