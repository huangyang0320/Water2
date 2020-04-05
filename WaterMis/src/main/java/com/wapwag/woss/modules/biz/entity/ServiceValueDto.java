package com.wapwag.woss.modules.biz.entity;

import java.util.Date;

public class ServiceValueDto {
	private Date queryDate;
	private double queryValue;
	public Date getQueryDate() {
		return queryDate;
	}
	public void setQueryDate(Date queryDate) {
		this.queryDate = queryDate;
	}
	public double getQueryValue() {
		return queryValue;
	}
	public void setQueryValue(double queryValue) {
		this.queryValue = queryValue;
	}

}
