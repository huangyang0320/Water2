package com.wapwag.woss.modules.home.util.hik;

/**
 * 海康接口返回对象
 * 
 * @author gongll
 *
 */
public class ResultInfo {

	private String errorCode;
	private String errorMessage;
	private ResultData data;
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
	public ResultData getData() {
		return data;
	}
	public void setData(ResultData data) {
		this.data = data;
	}
}
