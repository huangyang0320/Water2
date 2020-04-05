package com.wapwag.woss.modules.biz.entity;

import java.math.BigDecimal;

import com.wapwag.woss.common.persistence.DataEntity;

public class Services extends DataEntity<Device> {
      
	    /**  
	    * @Fields field:field:{todo}()  
	    */  
	private static final long serialVersionUID = 7817455759533246612L;
    private String name;

    private String code;

    private String type;

    private String memo;

    private String idDevice;

    private String idServiceGroup;

    private String dataType;

    private String idFunction;

    private BigDecimal ratio;

    private String initvalue;

    private String normalvalue;

    private Integer enablealarm;

    private Integer enablevaluealarm;

    private Integer enablevaluealarmreponse;

    private String rangeminimum;

    private String rangemaximum;

    private String unit;

    private String defaultVisible;

    private String dataSort;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public String getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(String idDevice) {
        this.idDevice = idDevice == null ? null : idDevice.trim();
    }

    public String getIdServiceGroup() {
        return idServiceGroup;
    }

    public void setIdServiceGroup(String idServiceGroup) {
        this.idServiceGroup = idServiceGroup == null ? null : idServiceGroup.trim();
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType == null ? null : dataType.trim();
    }

    public String getIdFunction() {
        return idFunction;
    }

    public void setIdFunction(String idFunction) {
        this.idFunction = idFunction == null ? null : idFunction.trim();
    }

    public BigDecimal getRatio() {
        return ratio;
    }

    public void setRatio(BigDecimal ratio) {
        this.ratio = ratio;
    }

    public String getInitvalue() {
        return initvalue;
    }

    public void setInitvalue(String initvalue) {
        this.initvalue = initvalue == null ? null : initvalue.trim();
    }

    public String getNormalvalue() {
        return normalvalue;
    }

    public void setNormalvalue(String normalvalue) {
        this.normalvalue = normalvalue == null ? null : normalvalue.trim();
    }

    public Integer getEnablealarm() {
        return enablealarm;
    }

    public void setEnablealarm(Integer enablealarm) {
        this.enablealarm = enablealarm;
    }

    public Integer getEnablevaluealarm() {
        return enablevaluealarm;
    }

    public void setEnablevaluealarm(Integer enablevaluealarm) {
        this.enablevaluealarm = enablevaluealarm;
    }

    public Integer getEnablevaluealarmreponse() {
        return enablevaluealarmreponse;
    }

    public void setEnablevaluealarmreponse(Integer enablevaluealarmreponse) {
        this.enablevaluealarmreponse = enablevaluealarmreponse;
    }

    public String getRangeminimum() {
        return rangeminimum;
    }

    public void setRangeminimum(String rangeminimum) {
        this.rangeminimum = rangeminimum == null ? null : rangeminimum.trim();
    }

    public String getRangemaximum() {
        return rangemaximum;
    }

    public void setRangemaximum(String rangemaximum) {
        this.rangemaximum = rangemaximum == null ? null : rangemaximum.trim();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public String getDefaultVisible() {
        return defaultVisible;
    }

    public void setDefaultVisible(String defaultVisible) {
        this.defaultVisible = defaultVisible == null ? null : defaultVisible.trim();
    }

    public String getDataSort() {
        return dataSort;
    }

    public void setDataSort(String dataSort) {
        this.dataSort = dataSort == null ? null : dataSort.trim();
    }
}