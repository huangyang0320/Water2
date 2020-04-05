package com.wapwag.woss.common.sms.service;

import com.wapwag.woss.common.sms.Intranet.SendSMS;

public interface ISendSmsService {
    public boolean sendSms(SendSMS sms) throws Exception;
}
