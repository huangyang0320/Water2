package com.wapwag.woss.modules.biz.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 百度导航 泵房卡片页 弹出信息
 * @author zhaom
 *
 */
public class PumpInfoDto implements Serializable{

	private static final long serialVersionUID = -893737690542111809L;
	private String pumpHouseId;
	private List<String> deviceIds;
	public String getPumpHouseId() {
		return pumpHouseId;
	}
	public void setPumpHouseId(String pumpHouseId) {
		this.pumpHouseId = pumpHouseId;
	}
	public List<String> getDeviceIds() {
		return deviceIds;
	}
	public void setDeviceIds(List<String> deviceIds) {
		this.deviceIds = deviceIds;
	}
	
	
	

}
