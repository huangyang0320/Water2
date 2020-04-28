package com.wapwag.woss.modules.ticket.service;

import com.wapwag.woss.common.service.CrudService;
import com.wapwag.woss.modules.biz.entity.ProductComponent;
import com.wapwag.woss.modules.ticket.Entity.ProductComponentData;
import com.wapwag.woss.modules.ticket.dao.ProductComponentDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductComponentService extends CrudService<ProductComponentDao, ProductComponentData> {

	@Autowired
	private ProductComponentDao productComponentDao;

	public List<ProductComponentData> findAllList(){
		return productComponentDao.findAllProductComponentList();
	}

	public List<ProductComponentData> findProductComponentReasonListById(List<String> list){
		return productComponentDao.findProductComponentReasonListById(list);
	}
}
