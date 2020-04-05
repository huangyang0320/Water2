package com.wapwag.woss.modules.home.util.hik;

import java.util.List;
import java.util.Map;

/**
 * 网络站点
 * @author gongll
 *
 */
public class NetZone {

	private String errorCode;
	private String errorMessage;
	private List<Map<String,Object>> data;
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public List<Map<String, Object>> getData() {
		return data;
	}
	public void setData(List<Map<String, Object>> data) {
		this.data = data;
	}
}
