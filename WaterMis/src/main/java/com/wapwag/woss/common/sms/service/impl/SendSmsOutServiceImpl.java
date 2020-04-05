package com.wapwag.woss.common.sms.service.impl;

import com.wapwag.woss.common.sms.outernet.SendSMS;
import com.wapwag.woss.common.sms.outernet.SendSMSService;
import com.wapwag.woss.common.sms.outernet.SendSMSServiceSoap;
import com.wapwag.woss.common.sms.service.ISendSmsOutService;
import org.springframework.stereotype.Service;

@Service
public class SendSmsOutServiceImpl implements ISendSmsOutService {


    @Override
    public boolean sendSms(SendSMS sms) {
        try{
            SendSMSService service_service = new SendSMSService();
            SendSMSServiceSoap webCXFService = service_service.getSendSMSServiceSoap();
            return webCXFService.sendSMS(sms.getMobile(),sms.getMessage(),sms.getSubcategory(),null,null,null,null);
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
        }
}
