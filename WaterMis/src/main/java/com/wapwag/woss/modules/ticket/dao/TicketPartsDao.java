package com.wapwag.woss.modules.ticket.dao;


import com.wapwag.woss.common.persistence.CrudDao;
import com.wapwag.woss.common.persistence.annotation.MyBatisDao;
import com.wapwag.woss.modules.ticket.Entity.TicketParts;

import java.util.List;

/**
 * ---------------------------
 *  (TicketPartsMapper)         
 * ---------------------------
 */
@MyBatisDao("com.wapwag.woss.modules.ticket.dao.TicketParts")
public interface TicketPartsDao  extends CrudDao<TicketParts> {

	/**
	 * 添加
	 *
	 * @param record
	 * @return
	 */
	int add(TicketParts record);

	/**
	 * 修改
	 *
	 * @param record
	 * @return
	 */
	@Override
	int update(TicketParts record);


	/**
	 * 查询
	 *
	 * @param record
	 * @return
	 */
	@Override
	List<TicketParts> findList(TicketParts record);


}