package com.wapwag.woss.modules.monitor.pumpNode;

/**
 * @author zx
 * @since 2018/5/22
 */
public class PumpPosition {

    private Long id;
    private String pumpName;
    private String pumpUrl;
    private String remoteCtrlPos;
    private String inletPos;
    private String outletPos;
    private String deviceNamePos;
    private String pumpPos;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
    

}
