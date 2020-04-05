package com.wapwag.woss.modules.monitor.pumpNode;

/**
 * @author leo
 * @since 2018/4/9 17:54
 */
public class Point {

    private String pointCode;
    private String pointName;
    private Float pointValue;
    private String pointUnit;
    private String pointType;  // bool,real

    public String getPointCode() {
        return pointCode;
    }

    public void setPointCode(String pointCode) {
        this.pointCode = pointCode;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public Float getPointValue() {
        return pointValue;
    }

    public void setPointValue(Float pointValue) {
        this.pointValue = pointValue;
    }

    public String getPointUnit() {
        return pointUnit;
    }

    public void setPointUnit(String pointUnit) {
        this.pointUnit = pointUnit;
    }

    public String getPointType() {
        return pointType;
    }

    public void setPointType(String pointType) {
        this.pointType = pointType;
    }
}
