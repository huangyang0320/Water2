
package com.wapwag.woss.common.sms.Intranet;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="mobile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="subcategory" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="preSendTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="expiredTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="sendLevel" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="DND" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "mobile",
    "message",
    "subcategory",
    "preSendTime",
    "expiredTime",
    "sendLevel",
    "dnd"
})
@XmlRootElement(name = "SendSMS")
public class SendSMS {

    protected String mobile;
    protected String message;
    protected String subcategory;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar preSendTime;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar expiredTime;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer sendLevel;
    @XmlElement(name = "DND", required = true, type = Integer.class, nillable = true)
    protected Integer dnd;


    public  SendSMS(){}
    public  SendSMS(String mobile,String message,String subcategory){
        this.mobile=mobile;
        this.message=message;
        this.subcategory=subcategory;
    }

    /**
     * ��ȡmobile���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * ����mobile���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMobile(String value) {
        this.mobile = value;
    }

    /**
     * ��ȡmessage���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessage() {
        return message;
    }

    /**
     * ����message���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessage(String value) {
        this.message = value;
    }

    /**
     * ��ȡsubcategory���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubcategory() {
        return subcategory;
    }

    /**
     * ����subcategory���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubcategory(String value) {
        this.subcategory = value;
    }

    /**
     * ��ȡpreSendTime���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPreSendTime() {
        return preSendTime;
    }

    /**
     * ����preSendTime���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPreSendTime(XMLGregorianCalendar value) {
        this.preSendTime = value;
    }

    /**
     * ��ȡexpiredTime���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getExpiredTime() {
        return expiredTime;
    }

    /**
     * ����expiredTime���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setExpiredTime(XMLGregorianCalendar value) {
        this.expiredTime = value;
    }

    /**
     * ��ȡsendLevel���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSendLevel() {
        return sendLevel;
    }

    /**
     * ����sendLevel���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSendLevel(Integer value) {
        this.sendLevel = value;
    }

    /**
     * ��ȡdnd���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDND() {
        return dnd;
    }

    /**
     * ����dnd���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDND(Integer value) {
        this.dnd = value;
    }

}
