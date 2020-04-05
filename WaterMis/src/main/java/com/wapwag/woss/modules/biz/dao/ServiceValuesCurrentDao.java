package com.wapwag.woss.modules.biz.dao;

import org.apache.ibatis.annotations.Param;

import com.wapwag.woss.common.persistence.CrudDao;
import com.wapwag.woss.common.persistence.annotation.MyBatisDao;
import com.wapwag.woss.modules.biz.entity.ServiceValuesCurrent;


  
    /**  
    * @ClassName: ServiceValuesCurrentMapper  
    * @Description: TODO()  
    * @author jiaxm  
    * @date 2018年7月23日  
    *    
    */  
@MyBatisDao("com.wapwag.woss.modules.biz.dao.ServiceValuesCurrentDao")
public interface ServiceValuesCurrentDao extends CrudDao<ServiceValuesCurrent> {
	public void saveOrUpdate(@Param("tableName") String tableName);
}