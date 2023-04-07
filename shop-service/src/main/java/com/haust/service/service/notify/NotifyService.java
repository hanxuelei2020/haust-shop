package com.haust.service.service.notify;

import com.haust.common.response.SmsResult;
import com.haust.common.type.NotifyType;

public interface NotifyService {
    public void notifySms(String phoneNumber, String message);
    public SmsResult notifySmsTemplate(String phoneNumber, NotifyType notifyType, String[] params);
    public SmsResult notifySmsTemplateSync(String phoneNumber, NotifyType notifyType, String[] params);
    public void notifyWxTemplate(String touser, NotifyType notifyType, String[] params);
    public void notifyWxTemplate(String touser, NotifyType notifyType, String[] params, String page);
    public void notifyMail(String subject, String content);
    public void notifySslMail(String subject, String content);

    default public boolean isMailEnable() {
        return false;
    }

    default public boolean isSmsEnable() {
        return false;
    }

    default public boolean isWxEnable() {
        return false;
    }
}
