package com.wapwag.woss.modules.sys.dao;

import com.wapwag.woss.common.persistence.CrudDao;
import com.wapwag.woss.common.persistence.annotation.MyBatisDao;
import com.wapwag.woss.modules.sys.entity.PumpHouse;

@MyBatisDao("com.wapwag.woss.modules.sys.dao.PumpHouseDao")
public interface PumpHouseDao extends CrudDao<PumpHouse> {
	int deleteByPrimaryKey(String pumpHouseId);

	int insert(PumpHouse record);

	int insertSelective(PumpHouse record);

	PumpHouse selectByPrimaryKey(String pumpHouseId);

	int updateByPrimaryKeySelective(PumpHouse record);

	int updateByPrimaryKey(PumpHouse record);
}