package com.wapwag.woss.modules.sys.entity;

import java.util.Date;

import com.wapwag.woss.common.persistence.DataEntity;

public class PumpDeviceRepair extends DataEntity<PumpDeviceRepair> {
    private String breakdownId;

    private String projectId;

    private String deviceId;

    private String pumpHouseId;

    private String userId;
    private String userName;

    private Date repairTime;

    private String repairContent;

    private String accessoriesXh;

    private String faultReason;

    private String solution;
    
    private String repairDate;

    private String isReplace;
    
    private String memo;

    private String phId;

    private String createtime;

    private String updatetime;

    public String getBreakdownId() {
        return breakdownId;
    }

    public void setBreakdownId(String breakdownId) {
        this.breakdownId = breakdownId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getPumpHouseId() {
        return pumpHouseId;
    }

    public void setPumpHouseId(String pumpHouseId) {
        this.pumpHouseId = pumpHouseId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getRepairTime() {
        return repairTime;
    }

    public void setRepairTime(Date repairTime) {
        this.repairTime = repairTime;
    }

    public String getRepairContent() {
        return repairContent;
    }

    public void setRepairContent(String repairContent) {
        this.repairContent = repairContent;
    }

    public String getAccessoriesXh() {
        return accessoriesXh;
    }

    public void setAccessoriesXh(String accessoriesXh) {
        this.accessoriesXh = accessoriesXh;
    }

    public String getFaultReason() {
        return faultReason;
    }

    public void setFaultReason(String faultReason) {
        this.faultReason = faultReason;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRepairDate() {
		return repairDate;
	}

	public void setRepairDate(String repairDate) {
		this.repairDate = repairDate;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public String getIsReplace() {
		return isReplace;
	}

	public void setIsReplace(String isReplace) {
		this.isReplace = isReplace;
	}

    public String getPhId() {
        return phId;
    }

    public void setPhId(String phId) {
        this.phId = phId;
    }
}