package com.wapwag.woss.modules.biz.entity;

import java.util.Date;

public class MonitorHistoryDto {
	
	private Date beginDate;
	private Date endDate;
	private String tableName;
	private String dimen;
	private String idAndFunCode;
	
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getDimen() {
		return dimen;
	}
	public void setDimen(String dimen) {
		this.dimen = dimen;
	}
	public String getIdAndFunCode() {
		return idAndFunCode;
	}
	public void setIdAndFunCode(String idAndFunCode) {
		this.idAndFunCode = idAndFunCode;
	}

}
