package com.wapwag.woss.common.hkthirdsdk.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="root")  
@XmlAccessorType(XmlAccessType.FIELD) 
public class LoginResult {

	private String errorCode;
	private String result;
	private String sessionId;
}
