package com.wapwag.woss.common.hkthirdsdk.pojo;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "body")
public class UserResult {

	@XmlElement
	private String errorCode;

	@XmlElement
	private String result;

	@XmlElement
	private String errorMsg;

	@XmlElement(name = "item")
	private List<UserInfo> user;

	@XmlTransient
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	@XmlTransient
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@XmlTransient
	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	@XmlTransient
	public List<UserInfo> getUser() {
		return user;
	}

	public void setUser(List<UserInfo> user) {
		this.user = user;
	}
}
