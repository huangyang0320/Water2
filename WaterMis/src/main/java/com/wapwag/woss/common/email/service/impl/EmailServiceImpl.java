package com.wapwag.woss.common.email.service.impl;

import com.wapwag.woss.common.email.MailSenderInfo;
import com.wapwag.woss.common.email.SimpleMailSender;
import com.wapwag.woss.common.email.service.IEmailService;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements IEmailService {
    @Override
    public boolean sendEmail(MailSenderInfo mailInfo) {
        return SimpleMailSender.sendTextMail(mailInfo);
    }
}
