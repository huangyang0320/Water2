package com.wapwag.woss.modules.ticket.Entity;

import com.wapwag.woss.common.persistence.DataEntity;

/**
 * ---------------------------
 *  (TicketParts)         
 * ---------------------------
 * 作者：  com.yxn.generator
 * 时间：  2020-04-11 14:27:01
 * 说明：  我是由代码生成器生生成的
 * ---------------------------
 */
public class TicketParts extends DataEntity<TicketParts> {

	/** 配件ID */
	private String id;
	/** 工单ID */
	private String ticketId;
	/** 名称 */
	private String name;
	/** 类型 */
	private String type;
	/** 数量 */
	private String nums;
	/** 价格 */
	private String price;
	/** 创建人 */
	private String createUser;
	/** 创建时间 */
	private java.util.Date createTime;
	/** 更新人 */
	private String updateUser;
	/** 更新时间 */
	private java.util.Date updateTime;
	/** 删除标记 0未删除 1 已删除 */
	private String deleteFlag;
	/** 删除人 */
	private String deleteUser;
	/** 删除时间 */
	private java.util.Date deleteTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNums() {
		return nums;
	}

	public void setNums(String nums) {
		this.nums = nums;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.util.Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public java.util.Date getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(java.util.Date deleteTime) {
		this.deleteTime = deleteTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getDeleteUser() {
		return deleteUser;
	}

	public void setDeleteUser(String deleteUser) {
		this.deleteUser = deleteUser;
	}
}