package com.wapwag.woss.modules.sys.dao;

import org.apache.ibatis.annotations.Param;

import com.wapwag.woss.common.persistence.CrudDao;
import com.wapwag.woss.common.persistence.annotation.MyBatisDao;
import com.wapwag.woss.modules.sys.entity.StatTotalInfo;
import com.wapwag.woss.modules.sys.entity.ValueCount;

@MyBatisDao("com.wapwag.woss.modules.sys.dao.ServiceValuesDao")
public interface ServiceValuesDao extends CrudDao<ValueCount> {

	/** 查询最后统计时间 */
	String lastStatTime(StatTotalInfo info);
	
	/** 统计 */
	int statServiceValsHour(StatTotalInfo info);
	
	int statServiceValsDMY(StatTotalInfo info);
	
	int statServiceValsSummary(StatTotalInfo info);
	
	int addNullstat(StatTotalInfo info);

	String checkSVTable(StatTotalInfo info);
	
	int createServiceValueStat(StatTotalInfo info);
	
	int deleteOldVal(StatTotalInfo info);
	
	int statSumToDay(@Param("beginTime") String beginTime,@Param("endTime") String endTime);
	int statAvgToDay(@Param("beginTime") String beginTime,@Param("endTime") String endTime);
	
	String checkTableExit(@Param("tableName") String tableName);

	int createTable(@Param("tableName") String tableName);
}
