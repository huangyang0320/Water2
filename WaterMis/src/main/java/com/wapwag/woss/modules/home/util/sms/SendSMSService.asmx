<?xml version="1.0" encoding="utf-8"?>
<wsdl:definitions xmlns:s="http://www.w3.org/2001/XMLSchema" xmlns:soap12="http://schemas.xmlsoap.org/wsdl/soap12/" xmlns:mime="http://schemas.xmlsoap.org/wsdl/mime/" xmlns:tns="http://10.61.98.5/webservice/sms/" xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/" xmlns:tm="http://microsoft.com/wsdl/mime/textMatching/" xmlns:http="http://schemas.xmlsoap.org/wsdl/http/" xmlns:soapenc="http://schemas.xmlsoap.org/soap/encoding/" targetNamespace="http://10.61.98.5/webservice/sms/" xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">
  <wsdl:types>
    <s:schema elementFormDefault="qualified" targetNamespace="http://10.61.98.5/webservice/sms/">
      <s:element name="SendSMS">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="mobile" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="message" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="subcategory" type="s:string" />
            <s:element minOccurs="1" maxOccurs="1" name="preSendTime" nillable="true" type="s:dateTime" />
            <s:element minOccurs="1" maxOccurs="1" name="expiredTime" nillable="true" type="s:dateTime" />
            <s:element minOccurs="1" maxOccurs="1" name="sendLevel" nillable="true" type="s:int" />
            <s:element minOccurs="1" maxOccurs="1" name="DND" nillable="true" type="s:int" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="SendSMSResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="1" maxOccurs="1" name="SendSMSResult" type="s:boolean" />
          </s:sequence>
        </s:complexType>
      </s:element>
    </s:schema>
  </wsdl:types>
  <wsdl:message name="SendSMSSoapIn">
    <wsdl:part name="parameters" element="tns:SendSMS" />
  </wsdl:message>
  <wsdl:message name="SendSMSSoapOut">
    <wsdl:part name="parameters" element="tns:SendSMSResponse" />
  </wsdl:message>
  <wsdl:portType name="SendSMSServiceSoap">
    <wsdl:operation name="SendSMS">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">发送短信</wsdl:documentation>
      <wsdl:input message="tns:SendSMSSoapIn" />
      <wsdl:output message="tns:SendSMSSoapOut" />
    </wsdl:operation>
  </wsdl:portType>
  <wsdl:binding name="SendSMSServiceSoap" type="tns:SendSMSServiceSoap">
    <soap:binding transport="http://schemas.xmlsoap.org/soap/http" />
    <wsdl:operation name="SendSMS">
      <soap:operation soapAction="http://10.61.98.5/webservice/sms/SendSMS" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
  </wsdl:binding>
  <wsdl:binding name="SendSMSServiceSoap12" type="tns:SendSMSServiceSoap">
    <soap12:binding transport="http://schemas.xmlsoap.org/soap/http" />
    <wsdl:operation name="SendSMS">
      <soap12:operation soapAction="http://10.61.98.5/webservice/sms/SendSMS" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
  </wsdl:binding>
  <wsdl:service name="SendSMSService">
    <wsdl:port name="SendSMSServiceSoap" binding="tns:SendSMSServiceSoap">
      <soap:address location="http://220.180.134.38:600/SendSMSService.asmx" />
    </wsdl:port>
    <wsdl:port name="SendSMSServiceSoap12" binding="tns:SendSMSServiceSoap12">
      <soap12:address location="http://220.180.134.38:600/SendSMSService.asmx" />
    </wsdl:port>
  </wsdl:service>
</wsdl:definitions>