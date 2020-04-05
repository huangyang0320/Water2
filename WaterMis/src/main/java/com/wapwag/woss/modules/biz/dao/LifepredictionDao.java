package com.wapwag.woss.modules.biz.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wapwag.woss.common.persistence.annotation.MyBatisDao;
import com.wapwag.woss.modules.biz.entity.ProductComponent;

@MyBatisDao
public interface LifepredictionDao {
	
	public List<ProductComponent> getComponentByProductId(@Param("productType")String productType);
	
	public ProductComponent getProductComponentById(@Param("componentId")String componentId);

}
