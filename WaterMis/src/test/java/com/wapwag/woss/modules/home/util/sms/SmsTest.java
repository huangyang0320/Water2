package com.wapwag.woss.modules.home.util.sms;

import com.wapwag.woss.common.sms.outernet.SendSMS;
import com.wapwag.woss.common.sms.outernet.SendSMSService;
import com.wapwag.woss.common.sms.outernet.SendSMSServiceSoap;

public class SmsTest {
    public static void main(String[] args) {
        SendSMSService service_service = new SendSMSService();
        SendSMSServiceSoap webCXFService = service_service.getSendSMSServiceSoap();
        SendSMS data=new SendSMS();
        getData(data);
        boolean s = webCXFService.sendSMS("15618659321","测试短信,请忽略....谢谢0001!","1",null,null,null,null);
        System.out.println(s);
    }

    public static void getData(SendSMS sms){
        sms.setMobile("18217312653");
        sms.setMessage("测试短信...忽略");
        sms.setSubcategory("1");


    }
}
