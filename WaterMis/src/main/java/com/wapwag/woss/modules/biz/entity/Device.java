package com.wapwag.woss.modules.biz.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.wapwag.woss.common.persistence.DataEntity;

/**
 * 设备Entity
 * @author yuxt
 * @version 2016-08-27
 */
public class Device extends DataEntity<Device> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String type;		// 类型
	private String memo;		// 备忘录
	private PumpHouse pumpHouse;		// 泵房id
	private Project project;		// 项目id
	private Date dateManufacture;		// 出厂日期
	private Date datePurchase;		// 购买日期
	private String purchaseAmount;		// 购买金额
	private Date createtime;		// 开始使用时间
	private Date updatetime;		// 信息更新时间
	private long pv;
	
	public Device() {
		super();
	}

	public Device(String id){
		super(id);
	}

	@Length(min=0, max=50, message="名称长度必须介于 0 和 50 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=50, message="类型长度必须介于 0 和 50 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=255, message="备忘录长度必须介于 0 和 255 之间")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public PumpHouse getPumpHouse() {
		return pumpHouse;
	}

	public void setPumpHouse(PumpHouse pumpHouse) {
		this.pumpHouse = pumpHouse;
	}
	
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="出厂日期不能为空")
	public Date getDateManufacture() {
		return dateManufacture;
	}

	public void setDateManufacture(Date dateManufacture) {
		this.dateManufacture = dateManufacture;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="购买日期不能为空")
	public Date getDatePurchase() {
		return datePurchase;
	}

	public void setDatePurchase(Date datePurchase) {
		this.datePurchase = datePurchase;
	}
	
	public String getPurchaseAmount() {
		return purchaseAmount;
	}

	public void setPurchaseAmount(String purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="开始使用时间不能为空")
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="信息更新时间不能为空")
	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public long getPv() {
		return pv;
	}

	public void setPv(long pv) {
		this.pv = pv;
	}
	
}