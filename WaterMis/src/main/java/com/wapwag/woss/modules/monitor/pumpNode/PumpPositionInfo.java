package com.wapwag.woss.modules.monitor.pumpNode;

import com.wapwag.woss.common.persistence.DataEntity;

/**
 * 组态点位实体类
 * @author zx
 * @version 2018-05-22
 */
public class PumpPositionInfo extends DataEntity<PumpPositionInfo> {

	
	private static final long serialVersionUID = 1L;
	
	public PumpPositionInfo() {
		super();
	}
	
	public PumpPositionInfo(String id) {
		super();
		this.id = id;
	}

	private String pumpName; // 组态图名称
	private String pumpUrl; // 组态图URL
	private String remoteCtrlPos; // 远程控制坐标
	private String inletPos; // 进口压力坐标
	private String outletPos; // 出口压力坐标
	private String deviceNamePos; // 设备名称坐标
	private String pumpPos; // 水泵位置坐标
	private String tankPos;
	public String getPumpName() {
		return pumpName;
	}
	public void setPumpName(String pumpName) {
		this.pumpName = pumpName;
	}
	public String getPumpUrl() {
		return pumpUrl;
	}
	public void setPumpUrl(String pumpUrl) {
		this.pumpUrl = pumpUrl;
	}
	public String getRemoteCtrlPos() {
		return remoteCtrlPos;
	}
	public void setRemoteCtrlPos(String remoteCtrlPos) {
		this.remoteCtrlPos = remoteCtrlPos;
	}
	public String getInletPos() {
		return inletPos;
	}
	public void setInletPos(String inletPos) {
		this.inletPos = inletPos;
	}
	public String getOutletPos() {
		return outletPos;
	}
	public void setOutletPos(String outletPos) {
		this.outletPos = outletPos;
	}
	public String getDeviceNamePos() {
		return deviceNamePos;
	}
	public void setDeviceNamePos(String deviceNamePos) {
		this.deviceNamePos = deviceNamePos;
	}
	public String getPumpPos() {
		return pumpPos;
	}
	public void setPumpPos(String pumpPos) {
		this.pumpPos = pumpPos;
	}

	public String getTankPos() {
		return tankPos;
	}

	public void setTankPos(String tankPos) {
		this.tankPos = tankPos;
	}
	
	

}
