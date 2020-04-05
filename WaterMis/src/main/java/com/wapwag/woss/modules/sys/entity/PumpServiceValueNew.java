package com.wapwag.woss.modules.sys.entity;

import java.util.Date;

import com.wapwag.woss.common.persistence.DataEntity;

public class PumpServiceValueNew extends DataEntity<PumpServiceValue> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7233999568261237096L;

	private Date createdate;
	private String ratio;
    private Date dateTime;

    private String deviceId;
    
    private String serviceId;

    private String tagName;

    private String pv;

    private String eu;

    private Double min;

    private Integer max;

    private Integer count;

    private Date update;
    
    private String operType;
    
    private String tableName;
    
    private String beginTime;		// 开始日期
	private String endTime;		// 结束日期

    public Date getDateTime() {
        return dateTime;
    }
    public String getRatio() {
		return ratio;
	}

	public void setRatio(String ratio) {
		this.ratio = ratio;
	}
    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getPv() {
        return pv;
    }

    public void setPv(String pv) {
        this.pv = pv;
    }

    public String getEu() {
        return eu;
    }

    public void setEu(String eu) {
        this.eu = eu;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getUpdate() {
        return update;
    }

    public void setUpdate(Date update) {
        this.update = update;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

	public String getOperType() {
		return operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
}