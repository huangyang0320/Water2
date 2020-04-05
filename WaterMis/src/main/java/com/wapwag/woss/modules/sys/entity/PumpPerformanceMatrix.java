package com.wapwag.woss.modules.sys.entity;

import com.wapwag.woss.common.persistence.DataEntity;

import java.util.Date;

/**
 * 水泵性能曲线实体类
 * @author zx
 * @version 2018-09-19
 */
public class PumpPerformanceMatrix extends DataEntity<PumpPerformanceMatrix> {


	private static final long serialVersionUID = 1L;

	public PumpPerformanceMatrix() {
		super();
	}

	public PumpPerformanceMatrix(String id) {
		super();
		this.id = id;
	}

	private String pumpModel; // 水泵型号
	private String hz; // 工作频率
	private Double flow; // 流量m³/小时
	private Double lift; // 扬程
	private Double inputPower; // 输入功率KW
	private Double outputPower; // 输出功率KW
	private Double efficiency; // 效率
	private String powerFactor; // 功率因数
	private String state; // 状态0无效，1有效
	private Date createTime; // 创建时间
	private String createId;
	private Date updateTime; // 修改时间
	private String updateId;

	private int pageNo;
	private int PageSize;

	public String getPumpModel() {
		return pumpModel;
	}

	public void setPumpModel(String pumpModel) {
		this.pumpModel = pumpModel;
	}

	public String getHz() {
		return hz;
	}

	public void setHz(String hz) {
		this.hz = hz;
	}

	public Double getFlow() {
		return flow;
	}

	public void setFlow(Double flow) {
		this.flow = flow;
	}

	public Double getLift() {
		return lift;
	}

	public void setLift(Double lift) {
		this.lift = lift;
	}

	public Double getInputPower() {
		return inputPower;
	}

	public void setInputPower(Double inputPower) {
		this.inputPower = inputPower;
	}

	public Double getOutputPower() {
		return outputPower;
	}

	public void setOutputPower(Double outputPower) {
		this.outputPower = outputPower;
	}

	public Double getEfficiency() {
		return efficiency;
	}

	public void setEfficiency(Double efficiency) {
		this.efficiency = efficiency;
	}

	public String getPowerFactor() {
		return powerFactor;
	}

	public void setPowerFactor(String powerFactor) {
		this.powerFactor = powerFactor;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public String getUpdateId() {
		return updateId;
	}

	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return PageSize;
	}

	public void setPageSize(int pageSize) {
		PageSize = pageSize;
	}
}
