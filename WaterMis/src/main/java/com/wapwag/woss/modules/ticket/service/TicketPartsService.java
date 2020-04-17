package com.wapwag.woss.modules.ticket.service;

import com.alibaba.fastjson.JSONObject;
import com.wapwag.woss.common.persistence.Page;
import com.wapwag.woss.common.service.CrudService;
import com.wapwag.woss.common.service.ServiceException;
import com.wapwag.woss.modules.home.entity.User;
import com.wapwag.woss.modules.ticket.Entity.TicketParts;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.commons.lang3.StringUtils;

import com.wapwag.woss.modules.ticket.dao.TicketPartsDao;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

/**
 * ---------------------------
 *  (TicketPartsServiceImpl)         
 * ---------------------------
 */
@Service
@Transactional(readOnly = true)
public class TicketPartsService extends CrudService<TicketPartsDao, TicketParts> {

	@Autowired
	private TicketPartsDao ticketPartsDao;

	@Transactional(readOnly = false)
	public JSONObject addOrUpdate(TicketParts ticketParts, User user){
		int res=0;
		JSONObject result=new JSONObject();
		Date date=new Date();
		String userName=user.getName();
		if(ticketParts!=null){
			if(!TextUtils.isEmpty(ticketParts.getId())){//修改
				ticketParts.setUpdateUser(userName);
				ticketParts.setUpdateTime(date);
				res=ticketPartsDao.update(ticketParts);
				if(res>0){
					result.put("code","200");
					result.put("status","success");
					result.put("message","配件记录修改成功!");
				}else{
					result.put("code","500");
					result.put("message","配件记录修改失败!");
				}
			}else{//添加
				ticketParts.setId(UUID.randomUUID().toString());
				ticketParts.setCreateUser(userName);
				ticketParts.setCreateTime(date);
				res=ticketPartsDao.add(ticketParts);
				if(res>0){
					result.put("code","200");
					result.put("status","success");
					result.put("message","配件记录添加成功!");
				}else{
					result.put("code","500");
					result.put("message","配件记录添加失败!");
				}
			}
		}
		return result;
	}

	@Transactional(readOnly = false)
	public JSONObject deleteTicket(TicketParts ticketParts, User user){
		int res=0;
		JSONObject result=new JSONObject();
		Date date=new Date();
		String userName=user.getName();
		if(ticketParts!=null){
			if(!TextUtils.isEmpty(ticketParts.getId())){
				ticketParts.setDeleteFlag("1");
				ticketParts.setDeleteUser(userName);
				ticketParts.setDeleteTime(date);
				res=ticketPartsDao.update(ticketParts);
				if(res>0){
					result.put("code","200");
					result.put("status","success");
					result.put("message","配件记录删除成功!");
				}else{
					result.put("code","500");
					result.put("message","配件记录删除失败!");
				}
			}
		}
		return result;
	}

	/**
	 *
	 * @param page 分页对象
	 * @param ticketParts
	 * @return
	 */
	@Transactional(readOnly = true)
	public Page<TicketParts> findPage(Page<TicketParts> page, TicketParts ticketParts) {
		return super.findPage(page, ticketParts);
	}

}