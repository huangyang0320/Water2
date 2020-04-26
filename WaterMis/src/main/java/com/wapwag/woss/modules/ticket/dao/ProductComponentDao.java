package com.wapwag.woss.modules.ticket.dao;


import com.wapwag.woss.common.persistence.CrudDao;
import com.wapwag.woss.common.persistence.annotation.MyBatisDao;
import com.wapwag.woss.modules.biz.entity.ProductComponent;
import com.wapwag.woss.modules.ticket.Entity.ProductComponentData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 设备配件管理DAO接口
 * @author jxm
 * @version 2018-05-23
 */
@MyBatisDao("com.wapwag.woss.modules.ticket.dao.ProductComponent")
public interface ProductComponentDao extends CrudDao<ProductComponentData> {

    List<ProductComponentData> findAllProductComponentList();

    List<ProductComponent> findProductComponentReasonListById(@Param("deviceIds")String deviceIds);


}