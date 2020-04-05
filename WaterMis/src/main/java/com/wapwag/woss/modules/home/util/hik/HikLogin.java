package com.wapwag.woss.modules.home.util.hik;

/**
 * 登录信息
 * @author gongll
 *
 */
public class HikLogin {

	private String appKey;
	private String secret;
	private String userUuid;
	private String url;
	
	private String userName;
	
	private String videoIP;
	private String videoPort;
	private String videoUrl;
	
	private String netZoneUUid;
	
	private long initTime;

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getUserUuid() {
		return userUuid;
	}

	public void setUserUuid(String userUuid) {
		this.userUuid = userUuid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getVideoIP() {
		return videoIP;
	}

	public void setVideoIP(String videoIP) {
		this.videoIP = videoIP;
	}

	public String getVideoPort() {
		return videoPort;
	}

	public void setVideoPort(String videoPort) {
		this.videoPort = videoPort;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getNetZoneUUid() {
		return netZoneUUid;
	}

	public void setNetZoneUUid(String netZoneUUid) {
		this.netZoneUUid = netZoneUUid;
	}

	public long getInitTime() {
		return initTime;
	}

	public void setInitTime(long initTime) {
		this.initTime = initTime;
	}
}
