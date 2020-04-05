package com.wapwag.woss.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wapwag.woss.common.hkthirdsdk.pojo.ConfigInfo;
import com.wapwag.woss.common.hkthirdsdk.pojo.DoorRecoad;
import com.wapwag.woss.common.persistence.CrudDao;
import com.wapwag.woss.common.persistence.annotation.MyBatisDao;
import com.wapwag.woss.modules.sys.entity.AccessCtrlRecords;

@MyBatisDao("com.wapwag.woss.modules.sys.dao.AccessCtrlRecordsDao")
public interface AccessCtrlRecordsDao extends CrudDao<AccessCtrlRecords> {
    int deleteByPrimaryKey(String doorId);

    int insert(AccessCtrlRecords record);

    int insertSelective(AccessCtrlRecords record);

    AccessCtrlRecords selectByPrimaryKey(String doorId);

    int updateByPrimaryKeySelective(AccessCtrlRecords record);

    int updateByPrimaryKey(AccessCtrlRecords record);
    
    List<AccessCtrlRecords> countLoginUser(AccessCtrlRecords accessCtrlRecords);

	List<AccessCtrlRecords> exportList(AccessCtrlRecords info);
	
	int saveAccessRecord(@Param("list") List<DoorRecoad> doorRecoads);
	
	String getBeginTime();
	
	List<ConfigInfo> configInfos();
}