package com.wapwag.woss.modules.home.util.hik;

public class AccessData {

	private String name;
	private String uuid;
	private String status;//1：开门状态，0：关门状态
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
