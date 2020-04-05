/**
 * ThirdSdkServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.wapwag.woss.common.hkthirdsdk;

public class ThirdSdkServiceLocator extends org.apache.axis.client.Service implements com.wapwag.woss.common.hkthirdsdk.ThirdSdkService {

    public ThirdSdkServiceLocator() {
    }


    public ThirdSdkServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ThirdSdkServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for thirdSdkServiceHttpSoap11Endpoint
    private java.lang.String thirdSdkServiceHttpSoap11Endpoint_address = "http://139.196.36.66:8089/services/thirdSdkService.thirdSdkServiceHttpSoap11Endpoint/";

    public java.lang.String getthirdSdkServiceHttpSoap11EndpointAddress() {
        return thirdSdkServiceHttpSoap11Endpoint_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String thirdSdkServiceHttpSoap11EndpointWSDDServiceName = "thirdSdkServiceHttpSoap11Endpoint";

    public java.lang.String getthirdSdkServiceHttpSoap11EndpointWSDDServiceName() {
        return thirdSdkServiceHttpSoap11EndpointWSDDServiceName;
    }

    public void setthirdSdkServiceHttpSoap11EndpointWSDDServiceName(java.lang.String name) {
        thirdSdkServiceHttpSoap11EndpointWSDDServiceName = name;
    }

    public com.wapwag.woss.common.hkthirdsdk.ThirdSdkServicePortType getthirdSdkServiceHttpSoap11Endpoint() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(thirdSdkServiceHttpSoap11Endpoint_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getthirdSdkServiceHttpSoap11Endpoint(endpoint);
    }

    public com.wapwag.woss.common.hkthirdsdk.ThirdSdkServicePortType getthirdSdkServiceHttpSoap11Endpoint(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.wapwag.woss.common.hkthirdsdk.ThirdSdkServiceSoap11BindingStub _stub = new com.wapwag.woss.common.hkthirdsdk.ThirdSdkServiceSoap11BindingStub(portAddress, this);
            _stub.setPortName(getthirdSdkServiceHttpSoap11EndpointWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setthirdSdkServiceHttpSoap11EndpointEndpointAddress(java.lang.String address) {
        thirdSdkServiceHttpSoap11Endpoint_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.wapwag.woss.common.hkthirdsdk.ThirdSdkServicePortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.wapwag.woss.common.hkthirdsdk.ThirdSdkServiceSoap11BindingStub _stub = new com.wapwag.woss.common.hkthirdsdk.ThirdSdkServiceSoap11BindingStub(new java.net.URL(thirdSdkServiceHttpSoap11Endpoint_address), this);
                _stub.setPortName(getthirdSdkServiceHttpSoap11EndpointWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("thirdSdkServiceHttpSoap11Endpoint".equals(inputPortName)) {
            return getthirdSdkServiceHttpSoap11Endpoint();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://server.ws.cms.hikvision.com", "thirdSdkService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://server.ws.cms.hikvision.com", "thirdSdkServiceHttpSoap11Endpoint"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("thirdSdkServiceHttpSoap11Endpoint".equals(portName)) {
            setthirdSdkServiceHttpSoap11EndpointEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
