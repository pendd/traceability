package com.hvisions.mes.util;

import com.hvisions.mes.domain.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * @author dpeng
 * @description 发送简易邮件工具类
 * @date 2019-08-28 22:46
 */
public class EmailUtils {

    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * 发送简易邮件
     *
     * @param email
     * @return
     */
    public boolean sendSimpleMail(Email email) {
        boolean flag = true;
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            //发件人
            simpleMailMessage.setFrom(email.getSender());
            //收件人
            simpleMailMessage.setTo(email.getRecipient());
            //邮件主题
            simpleMailMessage.setSubject(email.getSubject());
            //邮件内容
            simpleMailMessage.setText(email.getContent());
            javaMailSender.send(simpleMailMessage);
        } catch (MailException e) {
            flag = false;
        }
        return flag;
    }

}
