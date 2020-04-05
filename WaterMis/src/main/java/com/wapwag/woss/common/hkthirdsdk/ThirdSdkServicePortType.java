/**
 * ThirdSdkServicePortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.wapwag.woss.common.hkthirdsdk;

public interface ThirdSdkServicePortType extends java.rmi.Remote {
    public java.lang.String deleteOpsThirdDeviceBySysCode(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String addUser(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getAlarmDeviceInfo(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getSchedulingPage(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getParkingRegions(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getParkingInfo(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getEcsControlPage(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getAccessEventPage(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String covertListToString(java.lang.String[] list) throws java.rmi.RemoteException;
    public java.lang.String getAllAlarmState(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getCameraPreviewUrl(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String doControl(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getCameraInfo(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getAllPersonInspectionResult(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getRoutePage(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getSchedulingHistoryPage(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getPassCarsInfo(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getPTZSeizeTime(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String userOnlineHearbeat(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String importPersonAndCardInfo(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getPersonAndCards(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String deleteUser(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getAccessDoorPage(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getDepartments(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String userLogOut(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getControlUnitInfo(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getAlarmChannel(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getDeviceStatus(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getPassCarsCounting(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getIoPage(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getAlarmEventPage(java.lang.String xml) throws java.rmi.RemoteException;
    public int[] filterControlUnitId(int[] controlUnitIds, java.lang.Integer userId) throws java.rmi.RemoteException;
    public java.lang.String getPrivilegeResult(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getAccessControlByUserNamePage(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getCardsPageResult(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getPresons(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getGateInfo(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getEncoderInfo(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getAllOpsThirdDevice(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String updateUser(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getAllServer(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getAllRouteAndPointInfo(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getProgramPage(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getRecordResultPage(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String userLogin(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getPMSUrl(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getAccessDoorRegionNotNullPage(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getAccessControlPage(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getClockRecordPage(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String saveOrUpdateOpsThirdDevice(java.lang.String xml) throws java.rmi.RemoteException;
}
