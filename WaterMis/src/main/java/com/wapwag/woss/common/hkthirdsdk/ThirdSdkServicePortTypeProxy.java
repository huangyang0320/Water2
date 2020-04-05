package com.wapwag.woss.common.hkthirdsdk;

public class ThirdSdkServicePortTypeProxy implements com.wapwag.woss.common.hkthirdsdk.ThirdSdkServicePortType {
  private String _endpoint = null;
  private com.wapwag.woss.common.hkthirdsdk.ThirdSdkServicePortType thirdSdkServicePortType = null;
  
  public ThirdSdkServicePortTypeProxy() {
    _initThirdSdkServicePortTypeProxy();
  }
  
  public ThirdSdkServicePortTypeProxy(String endpoint) {
    _endpoint = endpoint;
    _initThirdSdkServicePortTypeProxy();
  }
  
  private void _initThirdSdkServicePortTypeProxy() {
    try {
      thirdSdkServicePortType = (new com.wapwag.woss.common.hkthirdsdk.ThirdSdkServiceLocator()).getthirdSdkServiceHttpSoap11Endpoint();
      if (thirdSdkServicePortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)thirdSdkServicePortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)thirdSdkServicePortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (thirdSdkServicePortType != null)
      ((javax.xml.rpc.Stub)thirdSdkServicePortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.wapwag.woss.common.hkthirdsdk.ThirdSdkServicePortType getThirdSdkServicePortType() {
    if (thirdSdkServicePortType == null)
      _initThirdSdkServicePortTypeProxy();
    return thirdSdkServicePortType;
  }
  
  public java.lang.String deleteOpsThirdDeviceBySysCode(java.lang.String xml) throws java.rmi.RemoteException{
    if (thirdSdkServicePortType == null)
      _initThirdSdkServicePortTypeProxy();
    return thirdSdkServicePortType.deleteOpsThirdDeviceBySysCode(xml);
  }
  
  public java.lang.String addUser(java.lang.String xml) throws java.rmi.RemoteException{
    if (thirdSdkServicePortType == null)
      _initThirdSdkServicePortTypeProxy();
    return thirdSdkServicePortType.addUser(xml);
  }
  
  public java.lang.String getAlarmDeviceInfo(java.lang.String xml) throws java.rmi.RemoteException{
    if (thirdSdkServicePortType == null)
      _initThirdSdkServicePortTypeProxy();
    return thirdSdkServicePortType.getAlarmDeviceInfo(xml);
  }
  
  public java.lang.String getSchedulingPage(java.lang.String xml) throws java.rmi.RemoteException{
    if (thirdSdkServicePortType == null)
      _initThirdSdkServicePortTypeProxy();
    return thirdSdkServicePortType.getSchedulingPage(xml);
  }
  
  public java.lang.String getParkingRegions(java.lang.String xml) throws java.rmi.RemoteException{
    if (thirdSdkServicePortType == null)
      _initThirdSdkServicePortTypeProxy();
    return thirdSdkServicePortType.getParkingRegions(xml);
  }
  
  public java.lang.String getParkingInfo(java.lang.String xml) throws java.rmi.RemoteException{
    if (thirdSdkServicePortType == null)
      _initThirdSdkServicePortTypeProxy();
    return thirdSdkServicePortType.getParkingInfo(xml);
  }
  
  public java.lang.String getEcsControlPage(java.lang.String xml) throws java.rmi.RemoteException{
    if (thirdSdkServicePortType == null)
      _initThirdSdkServicePortTypeProxy();
    return thirdSdkServicePortType.getEcsControlPage(xml);
  }
  
  public java.lang.String getAccessEventPage(java.lang.String xml) throws java.rmi.RemoteException{
    if (thirdSdkServicePortType == null)
      _initThirdSdkServicePortTypeProxy();
    return thirdSdkServicePortType.getAccessEventPage(xml);
  }
  
  public java.lang.String covertListToString(java.lang.String[] list) throws java.rmi.RemoteException{
    if (thirdSdkServicePortType == null)
      _initThirdSdkServicePortTypeProxy();
    return thirdSdkServicePortType.covertListToString(list);
  }
  
  public java.lang.String getAllAlarmState(java.lang.String xml) throws java.rmi.RemoteException{
    if (thirdSdkServicePortType == null)
      _initThirdSdkServicePortTypeProxy();
    return thirdSdkServicePortType.getAllAlarmState(xml);
  }
  
  public java.lang.String getCameraPreviewUrl(java.lang.String xml) throws java.rmi.RemoteException{
    if (thirdSdkServicePortType == null)
      _initThirdSdkServicePortTypeProxy();
    return thirdSdkServicePortType.getCameraPreviewUrl(xml);
  }
  
  public java.lang.String doControl(java.lang.String xml) throws java.rmi.RemoteException{
    if (thirdSdkServicePortType == null)
      _initThirdSdkServicePortTypeProxy();
    return thirdSdkServicePortType.doControl(xml);
  }
  
  public java.lang.String getCameraInfo(java.lang.String xml) throws java.rmi.RemoteException{
    if (thirdSdkServicePortType == null)
      _initThirdSdkServicePortTypeProxy();
    return thirdSdkServicePortType.getCameraInfo(xml);
  }
  
  public java.lang.String getAllPersonInspectionResult(java.lang.String xml) throws java.rmi.RemoteException{
    if (thirdSdkServicePortType == null)
      _initThirdSdkServicePortTypeProxy();
    return thirdSdkServicePortType.getAllPersonInspectionResult(xml);
  }
  
  public java.lang.String getRoutePage(java.lang.String xml) throws java.rmi.RemoteException{
    if (thirdSdkServicePortType == null)
      _initThirdSdkServicePortTypeProxy();
    return thirdSdkServicePortType.getRoutePage(xml);
  }
  
  public java.lang.String getSchedulingHistoryPage(java.lang.String xml) throws java.rmi.RemoteException{
    if (thirdSdkServicePortType == null)
      _initThirdSdkServicePortTypeProxy();
    return thirdSdkServicePortType.getSchedulingHistoryPage(xml);
  }
  
  public java.lang.String getPassCarsInfo(java.lang.String xml) throws java.rmi.RemoteException{
    if (thirdSdkServicePortType == null)
      _initThirdSdkServicePortTypeProxy();
    return thirdSdkServicePortType.getPassCarsInfo(xml);
  }
  
  public java.lang.String getPTZSeizeTime(java.lang.String xml) throws java.rmi.RemoteException{
    if (thirdSdkServicePortType == null)
      _initThirdSdkServicePortTypeProxy();
    return thirdSdkServicePortType.getPTZSeizeTime(xml);
  }
  
  public java.lang.String userOnlineHearbeat(java.lang.String xml) throws java.rmi.RemoteException{
    if (thirdSdkServicePortType == null)
      _initThirdSdkServicePortTypeProxy();
    return thirdSdkServicePortType.userOnlineHearbeat(xml);
  }
  
  public java.lang.String importPersonAndCardInfo(java.lang.String xml) throws java.rmi.RemoteException{
    if (thirdSdkServicePortType == null)
      _initThirdSdkServicePortTypeProxy();
    return thirdSdkServicePortType.importPersonAndCardInfo(xml);
  }
  
  public java.lang.String getPersonAndCards(java.lang.String xml) throws java.rmi.RemoteException{
    if (thirdSdkServicePortType == null)
      _initThirdSdkServicePortTypeProxy();
    return thirdSdkServicePortType.getPersonAndCards(xml);
  }
  
  public java.lang.String deleteUser(java.lang.String xml) throws java.rmi.RemoteException{
    if (thirdSdkServicePortType == null)
      _initThirdSdkServicePortTypeProxy();
    return thirdSdkServicePortType.deleteUser(xml);
  }
  
  public java.lang.String getAccessDoorPage(java.lang.String xml) throws java.rmi.RemoteException{
    if (thirdSdkServicePortType == null)
      _initThirdSdkServicePortTypeProxy();
    return thirdSdkServicePortType.getAccessDoorPage(xml);
  }
  
  public java.lang.String getDepartments(java.lang.String xml) throws java.rmi.RemoteException{
    if (thirdSdkServicePortType == null)
      _initThirdSdkServicePortTypeProxy();
    return thirdSdkServicePortType.getDepartments(xml);
  }
  
  public java.lang.String userLogOut(java.lang.String xml) throws java.rmi.RemoteException{
    if (thirdSdkServicePortType == null)
      _initThirdSdkServicePortTypeProxy();
    return thirdSdkServicePortType.userLogOut(xml);
  }
  
  public java.lang.String getControlUnitInfo(java.lang.String xml) throws java.rmi.RemoteException{
    if (thirdSdkServicePortType == null)
      _initThirdSdkServicePortTypeProxy();
    return thirdSdkServicePortType.getControlUnitInfo(xml);
  }
  
  public java.lang.String getAlarmChannel(java.lang.String xml) throws java.rmi.RemoteException{
    if (thirdSdkServicePortType == null)
      _initThirdSdkServicePortTypeProxy();
    return thirdSdkServicePortType.getAlarmChannel(xml);
  }
  
  public java.lang.String getDeviceStatus(java.lang.String xml) throws java.rmi.RemoteException{
    if (thirdSdkServicePortType == null)
      _initThirdSdkServicePortTypeProxy();
    return thirdSdkServicePortType.getDeviceStatus(xml);
  }
  
  public java.lang.String getPassCarsCounting(java.lang.String xml) throws java.rmi.RemoteException{
    if (thirdSdkServicePortType == null)
      _initThirdSdkServicePortTypeProxy();
    return thirdSdkServicePortType.getPassCarsCounting(xml);
  }
  
  public java.lang.String getIoPage(java.lang.String xml) throws java.rmi.RemoteException{
    if (thirdSdkServicePortType == null)
      _initThirdSdkServicePortTypeProxy();
    return thirdSdkServicePortType.getIoPage(xml);
  }
  
  public java.lang.String getAlarmEventPage(java.lang.String xml) throws java.rmi.RemoteException{
    if (thirdSdkServicePortType == null)
      _initThirdSdkServicePortTypeProxy();
    return thirdSdkServicePortType.getAlarmEventPage(xml);
  }
  
  public int[] filterControlUnitId(int[] controlUnitIds, java.lang.Integer userId) throws java.rmi.RemoteException{
    if (thirdSdkServicePortType == null)
      _initThirdSdkServicePortTypeProxy();
    return thirdSdkServicePortType.filterControlUnitId(controlUnitIds, userId);
  }
  
  public java.lang.String getPrivilegeResult(java.lang.String xml) throws java.rmi.RemoteException{
    if (thirdSdkServicePortType == null)
      _initThirdSdkServicePortTypeProxy();
    return thirdSdkServicePortType.getPrivilegeResult(xml);
  }
  
  public java.lang.String getAccessControlByUserNamePage(java.lang.String xml) throws java.rmi.RemoteException{
    if (thirdSdkServicePortType == null)
      _initThirdSdkServicePortTypeProxy();
    return thirdSdkServicePortType.getAccessControlByUserNamePage(xml);
  }
  
  public java.lang.String getCardsPageResult(java.lang.String xml) throws java.rmi.RemoteException{
    if (thirdSdkServicePortType == null)
      _initThirdSdkServicePortTypeProxy();
    return thirdSdkServicePortType.getCardsPageResult(xml);
  }
  
  public java.lang.String getPresons(java.lang.String xml) throws java.rmi.RemoteException{
    if (thirdSdkServicePortType == null)
      _initThirdSdkServicePortTypeProxy();
    return thirdSdkServicePortType.getPresons(xml);
  }
  
  public java.lang.String getGateInfo(java.lang.String xml) throws java.rmi.RemoteException{
    if (thirdSdkServicePortType == null)
      _initThirdSdkServicePortTypeProxy();
    return thirdSdkServicePortType.getGateInfo(xml);
  }
  
  public java.lang.String getEncoderInfo(java.lang.String xml) throws java.rmi.RemoteException{
    if (thirdSdkServicePortType == null)
      _initThirdSdkServicePortTypeProxy();
    return thirdSdkServicePortType.getEncoderInfo(xml);
  }
  
  public java.lang.String getAllOpsThirdDevice(java.lang.String xml) throws java.rmi.RemoteException{
    if (thirdSdkServicePortType == null)
      _initThirdSdkServicePortTypeProxy();
    return thirdSdkServicePortType.getAllOpsThirdDevice(xml);
  }
  
  public java.lang.String updateUser(java.lang.String xml) throws java.rmi.RemoteException{
    if (thirdSdkServicePortType == null)
      _initThirdSdkServicePortTypeProxy();
    return thirdSdkServicePortType.updateUser(xml);
  }
  
  public java.lang.String getAllServer(java.lang.String xml) throws java.rmi.RemoteException{
    if (thirdSdkServicePortType == null)
      _initThirdSdkServicePortTypeProxy();
    return thirdSdkServicePortType.getAllServer(xml);
  }
  
  public java.lang.String getAllRouteAndPointInfo(java.lang.String xml) throws java.rmi.RemoteException{
    if (thirdSdkServicePortType == null)
      _initThirdSdkServicePortTypeProxy();
    return thirdSdkServicePortType.getAllRouteAndPointInfo(xml);
  }
  
  public java.lang.String getProgramPage(java.lang.String xml) throws java.rmi.RemoteException{
    if (thirdSdkServicePortType == null)
      _initThirdSdkServicePortTypeProxy();
    return thirdSdkServicePortType.getProgramPage(xml);
  }
  
  public java.lang.String getRecordResultPage(java.lang.String xml) throws java.rmi.RemoteException{
    if (thirdSdkServicePortType == null)
      _initThirdSdkServicePortTypeProxy();
    return thirdSdkServicePortType.getRecordResultPage(xml);
  }
  
  public java.lang.String userLogin(java.lang.String xml) throws java.rmi.RemoteException{
    if (thirdSdkServicePortType == null)
      _initThirdSdkServicePortTypeProxy();
    return thirdSdkServicePortType.userLogin(xml);
  }
  
  public java.lang.String getPMSUrl(java.lang.String xml) throws java.rmi.RemoteException{
    if (thirdSdkServicePortType == null)
      _initThirdSdkServicePortTypeProxy();
    return thirdSdkServicePortType.getPMSUrl(xml);
  }
  
  public java.lang.String getAccessDoorRegionNotNullPage(java.lang.String xml) throws java.rmi.RemoteException{
    if (thirdSdkServicePortType == null)
      _initThirdSdkServicePortTypeProxy();
    return thirdSdkServicePortType.getAccessDoorRegionNotNullPage(xml);
  }
  
  public java.lang.String getAccessControlPage(java.lang.String xml) throws java.rmi.RemoteException{
    if (thirdSdkServicePortType == null)
      _initThirdSdkServicePortTypeProxy();
    return thirdSdkServicePortType.getAccessControlPage(xml);
  }
  
  public java.lang.String getClockRecordPage(java.lang.String xml) throws java.rmi.RemoteException{
    if (thirdSdkServicePortType == null)
      _initThirdSdkServicePortTypeProxy();
    return thirdSdkServicePortType.getClockRecordPage(xml);
  }
  
  public java.lang.String saveOrUpdateOpsThirdDevice(java.lang.String xml) throws java.rmi.RemoteException{
    if (thirdSdkServicePortType == null)
      _initThirdSdkServicePortTypeProxy();
    return thirdSdkServicePortType.saveOrUpdateOpsThirdDevice(xml);
  }
  
  
}