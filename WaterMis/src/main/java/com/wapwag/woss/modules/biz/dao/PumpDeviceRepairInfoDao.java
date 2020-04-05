package com.wapwag.woss.modules.biz.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wapwag.woss.common.persistence.CrudDao;
import com.wapwag.woss.common.persistence.annotation.MyBatisDao;
import com.wapwag.woss.modules.biz.entity.PumpDeviceRepairInfo;

/**
 * 水泵故障维护信息DAO接口
 * @author yuxt
 * @version 2016-08-27
 */
@MyBatisDao
public interface PumpDeviceRepairInfoDao extends CrudDao<PumpDeviceRepairInfo> {
	
	    /**  
	    * @Title: getLastReplaceInfo  
	    * @Description: TODO 获取最后一次更换设备配件的记录 
	    * @param @param deviceId 设备id
	    * @param @param accessoriesXh 配件类型
	    * @param @return    参数  
	    * @return List<PumpDeviceRepairInfo>    返回类型  
	    * @throws  
	    */  
	public List<PumpDeviceRepairInfo> getLastReplaceInfo(@Param("deviceId")String deviceId,@Param("accessoriesXh")String accessoriesXh);
	
}