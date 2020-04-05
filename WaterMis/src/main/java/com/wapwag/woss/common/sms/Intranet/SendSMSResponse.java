
package com.wapwag.woss.common.sms.Intranet;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SendSMSResult" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "sendSMSResult"
})
@XmlRootElement(name = "SendSMSResponse")
public class SendSMSResponse {

    @XmlElement(name = "SendSMSResult")
    protected boolean sendSMSResult;

    /**
     * ��ȡsendSMSResult���Ե�ֵ��
     * 
     */
    public boolean isSendSMSResult() {
        return sendSMSResult;
    }

    /**
     * ����sendSMSResult���Ե�ֵ��
     * 
     */
    public void setSendSMSResult(boolean value) {
        this.sendSMSResult = value;
    }

}
