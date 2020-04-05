package com.wapwag.woss.modules.monitor.pumpNode;


import java.util.List;

/**
 * @author leo
 * @since 2018/4/9 17:51
 */
public class CtrlParam {
    private String deviceId;//设备id

    private Integer deviceType;//设备类型,1为工控机, 2网关

    private Integer manualStatus;//是否有手动过控制,1有,0没有,前端根据值显示不同的页面样式

    private Point rStartUp;//本地或远程，1远程，0本地

    private Point rEStop;//远程急停，1急停，0急停复位

    private Point rManual;//R_Manual手动控制状态，1手动;  如果想要远程手动,需要在R_StartUp=1的情况下,远程把R_Manual的值修改为1

    private Point rAuto;//远程自动，以这个为总开关

    private Point rfeedback;//远程急停复位反馈

    private List<Point> setParam;//设置参数列表

    private List<List<Point>> pumpParam;//水泵参数

    private Point doorAccess;//门禁

    private String doorSource;//门禁来源0无门禁1海康门禁0节点门禁

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public Integer getManualStatus() {
        return manualStatus;
    }

    public void setManualStatus(Integer manualStatus) {
        this.manualStatus = manualStatus;
    }

    public Point getrStartUp() {
        return rStartUp;
    }

    public void setrStartUp(Point rStartUp) {
        this.rStartUp = rStartUp;
    }

    public Point getrEStop() {
        return rEStop;
    }

    public void setrEStop(Point rEStop) {
        this.rEStop = rEStop;
    }

    public Point getrManual() {
        return rManual;
    }

    public void setrManual(Point rManual) {
        this.rManual = rManual;
    }

    public List<Point> getSetParam() {
        return setParam;
    }

    public void setSetParam(List<Point> setParam) {
        this.setParam = setParam;
    }

    public List<List<Point>> getPumpParam() {
        return pumpParam;
    }

    public void setPumpParam(List<List<Point>> pumpParam) {
        this.pumpParam = pumpParam;
    }

    public Point getrAuto() {
        return rAuto;
    }

    public void setrAuto(Point rAuto) {
        this.rAuto = rAuto;
    }

    public Point getRfeedback() {
        return rfeedback;
    }

    public void setRfeedback(Point rfeedback) {
        this.rfeedback = rfeedback;
    }

    public CtrlParam() {

    }

    public Point getDoorAccess() {
        return doorAccess;
    }

    public void setDoorAccess(Point doorAccess) {
        this.doorAccess = doorAccess;
    }

    public String getDoorSource() {
        return doorSource;
    }

    public void setDoorSource(String doorSource) {
        this.doorSource = doorSource;
    }

    public CtrlParam(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public CtrlParam(Integer deviceType, Integer manualStatus) {
        this.deviceType = deviceType;
        this.manualStatus = manualStatus;
    }

}
