package com.wapwag.woss.modules.biz.entity;

import org.hibernate.validator.constraints.Length;

import com.wapwag.woss.common.persistence.DataEntity;

/**
 * 电气设备标牌Entity
 * @author yuxt
 * @version 2016-08-27
 */
public class DeviceElectricalLabel extends DataEntity<DeviceElectricalLabel> {
	
	private static final long serialVersionUID = 1L;
	private Device device;		// 设备ID
	private String modelNo;		// 设备型号
	private String ratedVoltage;		// 额定电压
	private String ratedPower;		// 额定功率
	private String standards;		// 执行标准
	private String controlQuantity;		// 控制台数
	private String ratedCurrent;		// 额定电流
	private String ratedFrequency;		// 电气设备标牌
	
	public DeviceElectricalLabel() {
		super();
	}

	public DeviceElectricalLabel(String id){
		super(id);
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}
	
	@Length(min=0, max=200, message="设备型号长度必须介于 0 和 200 之间")
	public String getModelNo() {
		return modelNo;
	}

	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}
	
	@Length(min=0, max=200, message="额定电压长度必须介于 0 和 200 之间")
	public String getRatedVoltage() {
		return ratedVoltage;
	}

	public void setRatedVoltage(String ratedVoltage) {
		this.ratedVoltage = ratedVoltage;
	}
	
	@Length(min=0, max=200, message="额定功率长度必须介于 0 和 200 之间")
	public String getRatedPower() {
		return ratedPower;
	}

	public void setRatedPower(String ratedPower) {
		this.ratedPower = ratedPower;
	}
	
	@Length(min=0, max=20, message="执行标准长度必须介于 0 和 20 之间")
	public String getStandards() {
		return standards;
	}

	public void setStandards(String standards) {
		this.standards = standards;
	}
	
	@Length(min=0, max=200, message="控制台数长度必须介于 0 和 200 之间")
	public String getControlQuantity() {
		return controlQuantity;
	}

	public void setControlQuantity(String controlQuantity) {
		this.controlQuantity = controlQuantity;
	}
	
	@Length(min=0, max=200, message="额定电流长度必须介于 0 和 200 之间")
	public String getRatedCurrent() {
		return ratedCurrent;
	}

	public void setRatedCurrent(String ratedCurrent) {
		this.ratedCurrent = ratedCurrent;
	}
	
	@Length(min=0, max=200, message="电气设备标牌长度必须介于 0 和 200 之间")
	public String getRatedFrequency() {
		return ratedFrequency;
	}

	public void setRatedFrequency(String ratedFrequency) {
		this.ratedFrequency = ratedFrequency;
	}
	
}