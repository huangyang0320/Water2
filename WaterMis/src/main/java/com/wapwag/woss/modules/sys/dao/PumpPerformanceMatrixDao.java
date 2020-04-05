package com.wapwag.woss.modules.sys.dao;

import com.wapwag.woss.common.persistence.CrudDao;
import com.wapwag.woss.common.persistence.annotation.MyBatisDao;
import com.wapwag.woss.modules.sys.entity.PumpPerformanceMatrix;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 水泵性能曲线DAO接口
 */
@MyBatisDao("com.wapwag.woss.modules.sys.dao.PumpPerformanceMatrixDao")
public interface PumpPerformanceMatrixDao extends CrudDao<PumpPerformanceMatrix> {

	List<PumpPerformanceMatrix> queryMatrix(@Param("pumpModel") String pumpModel,@Param("hz") String hz);

	List<String> queryPumpModel();

	List<String> queryHzByPumpModel(@Param("pumpModel") String pumpModel);
}
