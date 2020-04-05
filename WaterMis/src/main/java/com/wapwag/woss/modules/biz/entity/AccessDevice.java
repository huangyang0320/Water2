package com.wapwag.woss.modules.biz.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.wapwag.woss.common.persistence.DataEntity;

/**
 * 门禁Entity
 * @author yuxt
 * @version 2016-08-27
 */
public class AccessDevice extends DataEntity<AccessDevice> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 门禁名称
	private String memo;		// 备忘
	private String accessType;		// 出入类型
	private String lockType;		// 门锁类型
	private String connectType;		// 连接类型
	private String manufactor;		// 生产商
	private String assemblor;		// 集成商
	private String accIpAddr;		// IP地址
	private String macAddr;		// MAC地址
	private Date installDate;		// 安装时间
	
	public AccessDevice() {
		super();
	}

	public AccessDevice(String id){
		super(id);
	}

	@Length(min=1, max=255, message="门禁名称长度必须介于 1 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="备忘长度必须介于 0 和 255 之间")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	@Length(min=0, max=50, message="出入类型长度必须介于 0 和 50 之间")
	public String getAccessType() {
		return accessType;
	}

	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}
	
	@Length(min=0, max=50, message="门锁类型长度必须介于 0 和 50 之间")
	public String getLockType() {
		return lockType;
	}

	public void setLockType(String lockType) {
		this.lockType = lockType;
	}
	
	@Length(min=0, max=50, message="连接类型长度必须介于 0 和 50 之间")
	public String getConnectType() {
		return connectType;
	}

	public void setConnectType(String connectType) {
		this.connectType = connectType;
	}
	
	@Length(min=0, max=255, message="生产商长度必须介于 0 和 255 之间")
	public String getManufactor() {
		return manufactor;
	}

	public void setManufactor(String manufactor) {
		this.manufactor = manufactor;
	}
	
	@Length(min=0, max=255, message="集成商长度必须介于 0 和 255 之间")
	public String getAssemblor() {
		return assemblor;
	}

	public void setAssemblor(String assemblor) {
		this.assemblor = assemblor;
	}
	
	@Length(min=0, max=50, message="IP地址长度必须介于 0 和 50 之间")
	public String getAccIpAddr() {
		return accIpAddr;
	}

	public void setAccIpAddr(String accIpAddr) {
		this.accIpAddr = accIpAddr;
	}
	
	@Length(min=0, max=50, message="MAC地址长度必须介于 0 和 50 之间")
	public String getMacAddr() {
		return macAddr;
	}

	public void setMacAddr(String macAddr) {
		this.macAddr = macAddr;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getInstallDate() {
		return installDate;
	}

	public void setInstallDate(Date installDate) {
		this.installDate = installDate;
	}
	
}