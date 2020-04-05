/**
 * 
 */
package com.wapwag.woss.modules.home.entity;

/**
 * @author gongll
 * @since 2018-04-16 15:12:23
 */
public class RunStatSTO {
	
	//("开始时间")
	private String startDate;
	
	//("结束时间")
	private String endDate;
	
	//("设备id")
	private String deviceId;
	
	 //("查询类型：日 | day、月 | month、年 | year")
	private String queryType; // day,month,year

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

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
}
