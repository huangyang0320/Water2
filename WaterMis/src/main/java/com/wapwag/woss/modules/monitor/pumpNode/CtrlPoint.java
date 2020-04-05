package com.wapwag.woss.modules.monitor.pumpNode;

/**
 * @author zx
 * @since 2018/5/22
 */
public class CtrlPoint {
	private float  ratio;
	private String maxData;
	private String minData;
	private String deviceId;
	
	private String dataType;
    private String functionId;
    
	public float getRatio() {
		return ratio;
	}

	public void setRatio(float ratio) {
		this.ratio = ratio;
	}

	public String getMaxData() {
		return maxData;
	}

	public void setMaxData(String maxData) {
		this.maxData = maxData;
	}

	public String getMinData() {
		return minData;
	}

	public void setMinData(String minData) {
		this.minData = minData;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getFunctionId() {
		return functionId;
	}

	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}
	
	
	
}
