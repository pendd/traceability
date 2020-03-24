
package com.hvisions.mes.util;

import com.hvisions.mes.domain.SysConfig;
import com.hvisions.mes.domain.Tree;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmailUtil {

    //  设置服务器
    private static String KEY_SMTP = "mail.smtp.host";

    //  建立会话
    private MimeMessage message;
    private Session session;

    public EmailUtil() {
    }

    public void sendMail(SysConfig sysConfig, String email, Tree tree) {

        try {

            Properties props = System.getProperties();

            //props.setProperty(KEY_SMTP, sysConfig.getMailSmtp());
            //props.setProperty("mail.smtp.port","465");
            // props.setProperty("mail.smtp.auth", "true");

            props.put("mail.smtp.starttls.enable","true");
            props.setProperty("mail.host", "smtp.qq.com");
            props.setProperty("mail.transport.protocol", "smtp");
            props.setProperty("mail.smtp.auth", "true");
            props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.setProperty("mail.smtp.port", "465");
            props.setProperty("mail.smtp.socketFactory.port","465");

            session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(sysConfig.getMailUser(), sysConfig
                        .getMailPassword());
                }
            });
            session.setDebug(true);
            message = new MimeMessage(session);

            String headName = tree.getTitle(); // 标题
            String sendHtml = tree.getContent();// 内容
            String receiveUser = email;// 地址
            doSendHtmlEmail(sysConfig, headName, sendHtml, receiveUser);
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     *  * 发送邮件  *   * @param headName  *            邮件头文件名  * @param sendHtml
     *  *            邮件内容  * @param receiveUser  *            收件人地址  
     */
    public void doSendHtmlEmail(SysConfig sysConfig, String headName, String sendHtml,
        String receiveUser) {
        try {
            // 发件人
            InternetAddress from = new InternetAddress(sysConfig.getMailUser());

            message.setFrom(from);
            // 收件人
            InternetAddress to = new InternetAddress(receiveUser);
            message.setRecipient(Message.RecipientType.TO, to);
            // 邮件标题
            message.setSubject(headName);
            String content = sendHtml.toString();
            // 邮件内容,也可以使纯文本"text/plain"
            message.setContent(content, "text/html;charset=GBK");
            message.saveChanges();
            Transport transport = session.getTransport("smtp");
            // smtp验证，就是你用来发邮件的邮箱用户名密码
            transport.connect(sysConfig.getMailSmtp(), sysConfig.getMailUser(), sysConfig
                .getMailPassword());

            // 发送
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            log.info("send success!");
        } catch (Exception ex) {
            ex.getMessage();
        }
    }

}
