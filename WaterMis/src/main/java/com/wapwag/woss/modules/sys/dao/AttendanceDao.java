package com.wapwag.woss.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wapwag.woss.common.hkthirdsdk.pojo.ClockRecordResult;
import com.wapwag.woss.common.hkthirdsdk.pojo.ConfigInfo;
import com.wapwag.woss.common.persistence.CrudDao;
import com.wapwag.woss.common.persistence.annotation.MyBatisDao;
import com.wapwag.woss.modules.sys.entity.AttRecord;

@MyBatisDao("com.wapwag.woss.modules.sys.dao.AttendanceDao")
public interface AttendanceDao extends CrudDao<AttRecord> {

	int saveAccessRecord(@Param("list") List<ClockRecordResult> doorRecoads);

	String getBeginTime();
	
	List<ConfigInfo> configInfos();
	
	List<AttRecord> exportList(AttRecord info);

	List<AttRecord> countLoginUser(AttRecord accessCtrlRecords);
}