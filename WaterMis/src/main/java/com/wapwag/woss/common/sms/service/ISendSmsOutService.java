package com.wapwag.woss.common.sms.service;

import com.wapwag.woss.common.sms.outernet.SendSMS;

public interface ISendSmsOutService {
    public boolean sendSms(SendSMS sms) throws Exception;
}
