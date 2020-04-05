package com.wapwag.woss.modules.biz.entity;

import com.wapwag.woss.modules.sys.entity.User;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import com.wapwag.woss.common.persistence.DataEntity;

/**
 * 水泵故障维护信息Entity
 * @author yuxt
 * @version 2016-08-27
 */
public class PumpDeviceRepairInfo extends DataEntity<PumpDeviceRepairInfo> {
	
	private static final long serialVersionUID = 1L;
	private Project project;		// 项目编码
	private Device device;		// 设备编码
	private PumpHouse pumpHouse;		// 泵房编码
	private User user;		// user_id
	private Date repairTime;		// 维修时间
	private String repairContent;		// 维修内容
	private String accessoriesXh;		// 故障配件
	private String faultReason;		// 故障原因
	private String solution;		// 解决方法
	private String memo;		// 备注
	private Date createtime;		// 入库时间
	private Date updatetime;		// 更新时间
	
	public PumpDeviceRepairInfo() {
		super();
	}

	public PumpDeviceRepairInfo(String id){
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
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="维修时间不能为空")
	public Date getRepairTime() {
		return repairTime;
	}

	public void setRepairTime(Date repairTime) {
		this.repairTime = repairTime;
	}
	
	@Length(min=0, max=50, message="维修内容长度必须介于 0 和 50 之间")
	public String getRepairContent() {
		return repairContent;
	}

	public void setRepairContent(String repairContent) {
		this.repairContent = repairContent;
	}
	
	@Length(min=0, max=50, message="故障配件长度必须介于 0 和 50 之间")
	public String getAccessoriesXh() {
		return accessoriesXh;
	}

	public void setAccessoriesXh(String accessoriesXh) {
		this.accessoriesXh = accessoriesXh;
	}
	
	@Length(min=0, max=50, message="故障原因长度必须介于 0 和 50 之间")
	public String getFaultReason() {
		return faultReason;
	}

	public void setFaultReason(String faultReason) {
		this.faultReason = faultReason;
	}
	
	@Length(min=0, max=50, message="解决方法长度必须介于 0 和 50 之间")
	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}
	
	@Length(min=0, max=2000, message="备注长度必须介于 0 和 2000 之间")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	
}