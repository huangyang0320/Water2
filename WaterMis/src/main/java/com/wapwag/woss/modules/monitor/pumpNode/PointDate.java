package com.wapwag.woss.modules.monitor.pumpNode;

/**
 * @author zx
 * @since 2018/6/14
 */
public class PointDate {
    private String pointCode;
    private String pointValue;

    public String getPointCode() {
        return pointCode;
    }

    public void setPointCode(String pointCode) {
        this.pointCode = pointCode;
    }

    public String getPointValue() {
        return pointValue;
    }

    public void setPointValue(String pointValue) {
        this.pointValue = pointValue;
    }

    public PointDate(String pointCode, String pointValue) {
        this.pointCode = pointCode;
        this.pointValue = pointValue;
    }

    public PointDate() {
    }
}
