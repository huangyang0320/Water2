package com.wapwag.woss.modules.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.wapwag.woss.common.persistence.CrudDao;
import com.wapwag.woss.common.persistence.annotation.MyBatisDao;
import com.wapwag.woss.modules.sys.entity.ValueCount;

/**
 * @author leo
 * @since 2018-01-29 15:07:31
 */
@MyBatisDao("com.wapwag.woss.modules.sys.dao.AccumulationMapper")
public interface AccumulationMapper  extends CrudDao<ValueCount> {
	
	List<Map<String,Object>> queryStatAccumulation(@Param("beginTime") String beginTime,
			@Param("endTime") String endTime,
			@Param("tableName") String tableName,
			@Param("operTime") String operTime);
	
	List<Map<String,Object>> queryStatPass(@Param("beginTime") String beginTime,
			@Param("endTime") String endTime,
			@Param("tableName") String tableName,
			@Param("operTime") String operTime);

	int batchInsert(@Param("list") List<Map<String, Object>> statVal,
			@Param("tableName") String tableName);
	
	List<Map<String, String>> devPumps();
	
	List<Map<String, Object>> getOneSummary(String dateTime);
	List<Map<String, Object>> getTwoHourAvg(@Param("startTime") String startTime,@Param("endTime") String endTime);
	List<Map<String, String>> getAllDevConf();
	List<Map<String, String>> getPumpDevSize();
}
