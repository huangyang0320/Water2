package com.wapwag.woss.modules.biz.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wapwag.woss.common.persistence.CrudDao;
import com.wapwag.woss.common.persistence.annotation.MyBatisDao;
import com.wapwag.woss.modules.biz.entity.Services;

  
    /**  
    * @ClassName: ServicesMapper  
    * @Description: TODO()  
    * @author jiaxm  
    * @date 2018年6月11日  
    *    
    */  
@MyBatisDao
public interface ServicesDao extends CrudDao<Services>{
	  
	    /**  
	    * @Title: getByDeviceAndFunName  
	    * @Description: TODO()  
	    * @param @param deviceId 设备code
	    * @param @param funcName
	    * @param @return    
	    * @return List<Services>    
	    */  
	public List<Services> getByDeviceAndFunName(@Param("deviceId")String deviceId,@Param("funcName")String funcName);
}