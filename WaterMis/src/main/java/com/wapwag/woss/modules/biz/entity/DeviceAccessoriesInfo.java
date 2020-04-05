package com.wapwag.woss.modules.biz.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wapwag.woss.common.persistence.DataEntity;

/**
 * 设备配件信息Entity
 * @author yuxt
 * @version 2016-08-27
 */
public class DeviceAccessoriesInfo extends DataEntity<DeviceAccessoriesInfo> {
	
	private static final long serialVersionUID = 1L;
	private String accessoriesName;		// 配件名称
	private Device device;		// 设备编码
	private String accessoriesNo;		// 配件型号
	private String accessoriesUnits;		// 所属单元
	private String accessoriesItemid;		// 所属条目
	private Date insertTime;		// 插入时间
	private Date lastUpdateTime;		// 最后更新时间
	private String accessoriesType;		// 配件类型
	
	public DeviceAccessoriesInfo() {
		super();
	}

	public DeviceAccessoriesInfo(String id){
		super(id);
	}

	@Length(min=0, max=50, message="配件名称长度必须介于 0 和 50 之间")
	public String getAccessoriesName() {
		return accessoriesName;
	}

	public void setAccessoriesName(String accessoriesName) {
		this.accessoriesName = accessoriesName;
	}
	
	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}
	
	@Length(min=0, max=50, message="配件型号长度必须介于 0 和 50 之间")
	public String getAccessoriesNo() {
		return accessoriesNo;
	}

	public void setAccessoriesNo(String accessoriesNo) {
		this.accessoriesNo = accessoriesNo;
	}
	
	@Length(min=0, max=50, message="所属单元长度必须介于 0 和 50 之间")
	public String getAccessoriesUnits() {
		return accessoriesUnits;
	}

	public void setAccessoriesUnits(String accessoriesUnits) {
		this.accessoriesUnits = accessoriesUnits;
	}
	
	@Length(min=0, max=50, message="所属条目长度必须介于 0 和 50 之间")
	public String getAccessoriesItemid() {
		return accessoriesItemid;
	}

	public void setAccessoriesItemid(String accessoriesItemid) {
		this.accessoriesItemid = accessoriesItemid;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="插入时间不能为空")
	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="最后更新时间不能为空")
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	
	@Length(min=0, max=50, message="配件类型长度必须介于 0 和 50 之间")
	public String getAccessoriesType() {
		return accessoriesType;
	}

	public void setAccessoriesType(String accessoriesType) {
		this.accessoriesType = accessoriesType;
	}
	
}