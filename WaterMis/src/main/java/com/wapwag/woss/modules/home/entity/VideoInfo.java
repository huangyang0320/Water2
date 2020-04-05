package com.wapwag.woss.modules.home.entity;

import com.wapwag.woss.common.persistence.DataEntity;

public class VideoInfo extends DataEntity<VideoInfo> {

    private static final long serialVersionUID = 1L;

    private String videoId;

    private String videoName;

    private String projectId;

    private String pumpHouseId;

    private String monitorIp;

    private Integer monitorPort;

    private String memo;
    
    private String name;
    
    /** 查看视频用户名 */
    private String userName;
    
    /** 查看视频密码 */
    private String userPassword;
    
    /** 查看视频编号 */
    private String videoNo;
    
    /*** 海康视频id */
    private String hkVideoDeviceId;

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getPumpHouseId() {
        return pumpHouseId;
    }

    public void setPumpHouseId(String pumpHouseId) {
        this.pumpHouseId = pumpHouseId;
    }

    public String getMonitorIp() {
        return monitorIp;
    }

    public void setMonitorIp(String monitorIp) {
        this.monitorIp = monitorIp;
    }

    public Integer getMonitorPort() {
        return monitorPort;
    }

    public void setMonitorPort(Integer monitorPort) {
        this.monitorPort = monitorPort;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getVideoNo() {
		return videoNo;
	}

	public void setVideoNo(String videoNo) {
		this.videoNo = videoNo;
	}

	public String getHkVideoDeviceId() {
		return hkVideoDeviceId;
	}

	public void setHkVideoDeviceId(String hkVideoDeviceId) {
		this.hkVideoDeviceId = hkVideoDeviceId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}