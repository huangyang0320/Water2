package com.wapwag.woss.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wapwag.woss.common.persistence.CrudDao;
import com.wapwag.woss.common.persistence.annotation.MyBatisDao;
import com.wapwag.woss.modules.sys.entity.PumpHouse;
import com.wapwag.woss.modules.sys.entity.StatTotalInfo;

@MyBatisDao("com.wapwag.woss.modules.sys.dao.PumpRunStatDao")
public interface PumpRunStatDao  extends CrudDao<PumpHouse> {
	/** 泵运行统计 */
	String pumpHistoryMaxTime();

	int addPumpRunStat(StatTotalInfo info);

	List<String> qrychangeDevices(StatTotalInfo info);

	int modChangeStatus(@Param("list") List<String> list,
			@Param("beginTime") String beginTime);

	int addNullRunStat(StatTotalInfo info);
}
