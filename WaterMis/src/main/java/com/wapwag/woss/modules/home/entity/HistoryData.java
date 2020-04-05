package com.wapwag.woss.modules.home.entity;

/**
 * @author leo
 * @since 2018-03-02 15:53:40
 */
public class HistoryData {

    public HistoryData(){

    }

    private Float avgData;

    private String idService;
    
    private String idDevice;

    private String dateTime;
    
	private Float minData;
	
	private Float maxData;
	
	private String unit;
	
    public Float getMinData() {
		return minData;
	}

	public void setMinData(Float minData) {
		this.minData = minData;
	}

	public Float getMaxData() {
		return maxData;
	}

	public void setMaxData(Float maxData) {
		this.maxData = maxData;
	}

	public Float getAvgData() {
        return avgData;
    }

    public void setAvgData(Float avgData) {
        this.avgData = avgData;
    }

    public String getIdService() {
        return idService;
    }

    public void setIdService(String idService) {
        this.idService = idService;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
    
    public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

    public HistoryData(Float avgData, String idService, String dateTime) {
        this.avgData = avgData;
        this.idService = idService;
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "HistoryData{" +
                "avgData=" + avgData +
                ", idService='" + idService + '\'' +
                ", dateTime='" + dateTime + '\'' +
                '}';
    }

    public String getIdDevice() {
		return idDevice;
	}

	public void setIdDevice(String idDevice) {
		this.idDevice = idDevice;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HistoryData that = (HistoryData) o;

        if (idService != null ? !idService.equals(that.idService) : that.idService != null) return false;
        return dateTime != null ? dateTime.equals(that.dateTime) : that.dateTime == null;
    }

    @Override
    public int hashCode() {
        int result = idService != null ? idService.hashCode() : 0;
        result = 31 * result + (dateTime != null ? dateTime.hashCode() : 0);
        return result;
    }
}
