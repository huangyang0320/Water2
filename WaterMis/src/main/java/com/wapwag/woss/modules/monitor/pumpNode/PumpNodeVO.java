package com.wapwag.woss.modules.monitor.pumpNode;

import java.util.List;

/**
 * @author zx
 * @since 2018/5/22
 */
public class PumpNodeVO {
    private String id;
    private String phName;
    private String phUrl;
    private CurDevicePos curDevicePos;
    private List<PhNode> singleNode;
    private List<PhNode> multiNode;
    private List<VideoInfo> videoInfo;
    private List<DeviceInfo> deviceInfo;
    private String Identification;//是否绑定组态图标识0没有1有

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhName() {
		return phName;
	}
	public void setPhName(String phName) {
		this.phName = phName;
	}
	public String getPhUrl() {
		return phUrl;
	}
	public void setPhUrl(String phUrl) {
		this.phUrl = phUrl;
	}
	public CurDevicePos getCurDevicePos() {
		return curDevicePos;
	}
	public void setCurDevicePos(CurDevicePos curDevicePos) {
		this.curDevicePos = curDevicePos;
	}
	public List<PhNode> getSingleNode() {
		return singleNode;
	}
	public void setSingleNode(List<PhNode> singleNode) {
		this.singleNode = singleNode;
	}
	public List<PhNode> getMultiNode() {
		return multiNode;
	}
	public void setMultiNode(List<PhNode> multiNode) {
		this.multiNode = multiNode;
	}
	public List<VideoInfo> getVideoInfo() {
		return videoInfo;
	}
	public void setVideoInfo(List<VideoInfo> videoInfo) {
		this.videoInfo = videoInfo;
	}
	public List<DeviceInfo> getDeviceInfo() {
		return deviceInfo;
	}
	public void setDeviceInfo(List<DeviceInfo> deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public String getIdentification() {
		return Identification;
	}

	public void setIdentification(String identification) {
		Identification = identification;
	}
}
