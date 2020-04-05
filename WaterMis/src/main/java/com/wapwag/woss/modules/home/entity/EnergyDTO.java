/**
 * 
 */
package com.wapwag.woss.modules.home.entity;

import java.util.List;

/**
 * @author gongll
 * @since 2018-04-16 15:12:23
 */
public class EnergyDTO {
	
	//("开始时间")
	private String startDate;
	
	//("结束时间")
	private String endDate;
	
	//("设备ids")
	private List<String> deviceIds;
	
	 //("查询类型：日 | day、月 | month、年 | year")
	private String queryType; // day,month,year
	
	private String userId;

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public List<String> getDeviceIds() {
		return deviceIds;
	}

	public void setDeviceIds(List<String> deviceIds) {
		this.deviceIds = deviceIds;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
