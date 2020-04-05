package com.wapwag.woss.modules.biz.entity;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;

import com.wapwag.woss.common.persistence.DataEntity;

/**
 * 功能Entity
 * @author yuxt
 * @version 2016-08-27
 */
public class Function extends DataEntity<Function> {
	
	private static final long serialVersionUID = 1L;

    private String code;

    private String name;

    private String memo;

    private String type;

    private String dataType;

    private String unit;

    private BigDecimal initValue;

    private BigDecimal normValue;

    private BigDecimal minRv;

    private BigDecimal maxRv;

    private BigDecimal decimal;

    private BigDecimal ratio;

    private Integer isAlarm;

    private Integer isCumulative;

    private Integer isParamSetting;

    private Integer isResponsible;

    private Integer isParamStatus;

    private Integer isRangeable;

    private String version;
	
	public Function() {
		super();
	}

	public Function(String id){
		super(id);
	}

	@Length(min=0, max=100, message="名称长度必须介于 0 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=100, message="类型长度必须介于 0 和 100 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	

	
	@Length(min=0, max=100, message="数据类型长度必须介于 0 和 100 之间")
	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	@Length(min=0, max=255, message="单位长度必须介于 0 和 255 之间")
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@Length(min=0, max=255, message="备注长度必须介于 0 和 255 之间")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public BigDecimal getInitValue() {
		return initValue;
	}

	public void setInitValue(BigDecimal initValue) {
		this.initValue = initValue;
	}

	public BigDecimal getNormValue() {
		return normValue;
	}

	public void setNormValue(BigDecimal normValue) {
		this.normValue = normValue;
	}

	public BigDecimal getMinRv() {
		return minRv;
	}

	public void setMinRv(BigDecimal minRv) {
		this.minRv = minRv;
	}

	public BigDecimal getMaxRv() {
		return maxRv;
	}

	public void setMaxRv(BigDecimal maxRv) {
		this.maxRv = maxRv;
	}

	public BigDecimal getDecimal() {
		return decimal;
	}

	public void setDecimal(BigDecimal decimal) {
		this.decimal = decimal;
	}

	public BigDecimal getRatio() {
		return ratio;
	}

	public void setRatio(BigDecimal ratio) {
		this.ratio = ratio;
	}

	public Integer getIsAlarm() {
		return isAlarm;
	}

	public void setIsAlarm(Integer isAlarm) {
		this.isAlarm = isAlarm;
	}

	public Integer getIsCumulative() {
		return isCumulative;
	}

	public void setIsCumulative(Integer isCumulative) {
		this.isCumulative = isCumulative;
	}

	public Integer getIsParamSetting() {
		return isParamSetting;
	}

	public void setIsParamSetting(Integer isParamSetting) {
		this.isParamSetting = isParamSetting;
	}

	public Integer getIsResponsible() {
		return isResponsible;
	}

	public void setIsResponsible(Integer isResponsible) {
		this.isResponsible = isResponsible;
	}

	public Integer getIsParamStatus() {
		return isParamStatus;
	}

	public void setIsParamStatus(Integer isParamStatus) {
		this.isParamStatus = isParamStatus;
	}

	public Integer getIsRangeable() {
		return isRangeable;
	}

	public void setIsRangeable(Integer isRangeable) {
		this.isRangeable = isRangeable;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}