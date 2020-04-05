package com.wapwag.woss.modules.sys.dao;

import com.wapwag.woss.common.persistence.CrudDao;
import com.wapwag.woss.common.persistence.annotation.MyBatisDao;
import com.wapwag.woss.modules.sys.entity.PumpFault;

@MyBatisDao
public interface PumpFaultDao extends CrudDao<PumpFault> {
	int deleteByPrimaryKey(String faultId);

	int insert(PumpFault record);

	int insertSelective(PumpFault record);

	PumpFault selectByPrimaryKey(String faultId);

	int updateByPrimaryKeySelective(PumpFault record);

	int updateByPrimaryKey(PumpFault record);
}