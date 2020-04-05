package com.wapwag.woss.common.email.service;

import com.wapwag.woss.common.email.MailSenderInfo;

public interface IEmailService {
    public boolean sendEmail(MailSenderInfo mailInfo);
}
