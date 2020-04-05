package com.wapwag.woss.modules.biz.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.wapwag.woss.common.persistence.DataEntity;

/**
 * 报警信息Entity
 * @author yuxt
 * @version 2016-08-27
 */
public class AlarmInfo extends DataEntity<AlarmInfo> {
	
	private static final long serialVersionUID = 1L;
	private Project project;		// 项目编号
	private Device device;		// 设备编号
	private PumpHouse pumpHouse;		// 泵房编码
	private String alarmContent;		// 报警信息
	private String alarmReason;		// 报警原因
	private String alarmType;		// 报警类型
	private Date alarmTime;		// 报警时间
	
	public AlarmInfo() {
		super();
	}

	public AlarmInfo(String id){
		super(id);
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}
	
	public PumpHouse getPumpHouse() {
		return pumpHouse;
	}

	public void setPumpHouse(PumpHouse pumpHouse) {
		this.pumpHouse = pumpHouse;
	}
	
	@Length(min=0, max=50, message="报警信息长度必须介于 0 和 50 之间")
	public String getAlarmContent() {
		return alarmContent;
	}

	public void setAlarmContent(String alarmContent) {
		this.alarmContent = alarmContent;
	}
	
	@Length(min=0, max=50, message="报警原因长度必须介于 0 和 50 之间")
	public String getAlarmReason() {
		return alarmReason;
	}

	public void setAlarmReason(String alarmReason) {
		this.alarmReason = alarmReason;
	}
	
	@Length(min=0, max=50, message="报警类型长度必须介于 0 和 50 之间")
	public String getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="报警时间不能为空")
	public Date getAlarmTime() {
		return alarmTime;
	}

	public void setAlarmTime(Date alarmTime) {
		this.alarmTime = alarmTime;
	}
	
}