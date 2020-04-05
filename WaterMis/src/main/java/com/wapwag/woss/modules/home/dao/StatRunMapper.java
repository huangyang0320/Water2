package com.wapwag.woss.modules.home.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.wapwag.woss.common.persistence.CrudDao;
import com.wapwag.woss.common.persistence.annotation.MyBatisDao;
import com.wapwag.woss.modules.home.entity.AlarmInfo;
/**
 * 功耗统计
 * Created by Administrator on 2016/8/2.
 */
@MyBatisDao("com.wapwag.woss.modules.home.dao.StatRunMapper")
public interface StatRunMapper extends CrudDao<AlarmInfo> {

List<Map<String, String>> qryRunStat(Map<String, Object> qry);
	
	List<Map<String, Object>> qryFreStat(Map<String, Object> qry);
	
	List<String> getPumpByDevCode(@Param("devCode") String devCode);
}
