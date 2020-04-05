package com.wapwag.woss.modules.biz.entity;

import org.hibernate.validator.constraints.Length;

import com.wapwag.woss.common.persistence.DataEntity;

/**
 * 水泵故障信息Entity
 * @author yuxt
 * @version 2016-08-27
 */
public class PumpFaultInfo extends DataEntity<PumpFaultInfo> {
	
	private static final long serialVersionUID = 1L;
	private AlarmInfo alarm;		// 报警编码
	private Device device;		// 设备编码
	private Integer faultLevel;		// 故障级别
	private String faultMessage;		// 故障信息
	private String faultState;		// 故障状态
	
	public PumpFaultInfo() {
		super();
	}

	public PumpFaultInfo(String id){
		super(id);
	}

	public AlarmInfo getAlarm() {
		return alarm;
	}

	public void setAlarm(AlarmInfo alarm) {
		this.alarm = alarm;
	}
	
	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}
	
	public Integer getFaultLevel() {
		return faultLevel;
	}

	public void setFaultLevel(Integer faultLevel) {
		this.faultLevel = faultLevel;
	}
	
	@Length(min=0, max=500, message="故障信息长度必须介于 0 和 500 之间")
	public String getFaultMessage() {
		return faultMessage;
	}

	public void setFaultMessage(String faultMessage) {
		this.faultMessage = faultMessage;
	}
	
	@Length(min=0, max=2, message="故障状态长度必须介于 0 和 2 之间")
	public String getFaultState() {
		return faultState;
	}

	public void setFaultState(String faultState) {
		this.faultState = faultState;
	}
	
}