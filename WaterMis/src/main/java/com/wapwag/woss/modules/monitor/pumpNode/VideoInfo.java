package com.wapwag.woss.modules.monitor.pumpNode;

/**
 * @author zx
 * @since 2018/5/22
 */
public class VideoInfo {

    private String videoNo;
    private String videoName;
    private String monitorIp;
	public String getVideoNo() {
		return videoNo;
	}
	public void setVideoNo(String videoNo) {
		this.videoNo = videoNo;
	}
	public String getVideoName() {
		return videoName;
	}
	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	public String getMonitorIp() {
		return monitorIp;
	}

	public void setMonitorIp(String monitorIp) {
		this.monitorIp = monitorIp;
	}
}
