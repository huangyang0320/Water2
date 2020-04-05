package com.wapwag.woss.modules.monitor.pumpNode;

import java.util.List;

/**
 * @author zx
 * @since 2018/5/22
 */
public class CurDevicePos {

    private List<Poi> remoteCtrlPos;
    private List<Poi> inletPos;
    private List<Poi> outletPos;
    private List<Poi> deviceNamePos;
    private List<Poi> tankPos;
    private List<List<Poi>> pumpPos;
	public List<Poi> getRemoteCtrlPos() {
		return remoteCtrlPos;
	}
	public void setRemoteCtrlPos(List<Poi> remoteCtrlPos) {
		this.remoteCtrlPos = remoteCtrlPos;
	}
	public List<Poi> getInletPos() {
		return inletPos;
	}
	public void setInletPos(List<Poi> inletPos) {
		this.inletPos = inletPos;
	}
	public List<Poi> getOutletPos() {
		return outletPos;
	}
	public void setOutletPos(List<Poi> outletPos) {
		this.outletPos = outletPos;
	}
	public List<Poi> getDeviceNamePos() {
		return deviceNamePos;
	}
	public void setDeviceNamePos(List<Poi> deviceNamePos) {
		this.deviceNamePos = deviceNamePos;
	}
	public List<List<Poi>> getPumpPos() {
		return pumpPos;
	}
	public void setPumpPos(List<List<Poi>> pumpPos) {
		this.pumpPos = pumpPos;
	}
	public List<Poi> getTankPos() {
		return tankPos;
	}
	public void setTankPos(List<Poi> tankPos) {
		this.tankPos = tankPos;
	}
    
}
