package com.wapwag.woss.modules.monitor.pumpNode;

import java.util.List;

/**
 * @author zx
 * @since 2018/5/22
 */
public class PumpService {
    private String idDevice;
    private String idService;
	private String tagName;//测点名称
	private String unit;//单位
    private String pv;//值
	private String code;

	private String phId;
	private List<String> codeList;
	private List<String> deviceList;

	public String getIdDevice() {
		return idDevice;
	}

	public void setIdDevice(String idDevice) {
		this.idDevice = idDevice;
	}

	public String getIdService() {
		return idService;
	}

	public void setIdService(String idService) {
		this.idService = idService;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getPv() {
		return pv;
	}

	public void setPv(String pv) {
		this.pv = pv;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


	public String getPhId() {
		return phId;
	}

	public void setPhId(String phId) {
		this.phId = phId;
	}

	public List<String> getDeviceList() {
		return deviceList;
	}

	public void setDeviceList(List<String> deviceList) {
		this.deviceList = deviceList;
	}

	public List<String> getCodeList() {
		return codeList;
	}

	public void setCodeList(List<String> codeList) {
		this.codeList = codeList;
	}
}
