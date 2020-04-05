package com.wapwag.woss.modules.biz.entity;

import org.hibernate.validator.constraints.Length;

import com.wapwag.woss.common.persistence.DataEntity;

/**
 * 水泵设备标牌Entity
 * @author yuxt
 * @version 2016-08-27
 */
public class DevicePumpLabel extends DataEntity<DevicePumpLabel> {
	
	private static final long serialVersionUID = 1L;
	private Device device;		// 设备ID
	private String modelNo;		// 水泵型号
	private String sbedgsll;		// 额定供水流量
	private String ratedPower;		// 额定功率
	private String sbedgsyc;		// 额定供水扬程
	private String speed;		// 转速
	
	public DevicePumpLabel() {
		super();
	}

	public DevicePumpLabel(String id){
		super(id);
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}
	
	@Length(min=0, max=20, message="水泵型号长度必须介于 0 和 20 之间")
	public String getModelNo() {
		return modelNo;
	}

	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}
	
	@Length(min=0, max=200, message="额定供水流量长度必须介于 0 和 200 之间")
	public String getSbedgsll() {
		return sbedgsll;
	}

	public void setSbedgsll(String sbedgsll) {
		this.sbedgsll = sbedgsll;
	}
	
	@Length(min=0, max=200, message="额定功率长度必须介于 0 和 200 之间")
	public String getRatedPower() {
		return ratedPower;
	}

	public void setRatedPower(String ratedPower) {
		this.ratedPower = ratedPower;
	}
	
	@Length(min=0, max=200, message="额定供水扬程长度必须介于 0 和 200 之间")
	public String getSbedgsyc() {
		return sbedgsyc;
	}

	public void setSbedgsyc(String sbedgsyc) {
		this.sbedgsyc = sbedgsyc;
	}
	
	@Length(min=0, max=200, message="转速长度必须介于 0 和 200 之间")
	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}
	
}