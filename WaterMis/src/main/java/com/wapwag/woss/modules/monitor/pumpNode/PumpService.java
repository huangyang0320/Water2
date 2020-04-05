package com.wapwag.woss.modules.monitor.pumpNode;

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
}
