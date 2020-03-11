package com.hvisions.mes.domain;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author wanghaiwei
 * @since 2017-05-25
 */
@TableName("sys_config")
public class SysConfig extends Model<SysConfig> {

    private static final long serialVersionUID = 1L;

    @TableField("mail_addr")
    private String mailAddr;
    @TableField("mail_password")
    private String mailPassword;
    @TableId("mail_smtp")
    private String mailSmtp;
    @TableField("mail_user")
    private String mailUser;

    public String getMailAddr() {
        return mailAddr;
    }

    public void setMailAddr(String mailAddr) {
        this.mailAddr = mailAddr;
    }

    public String getMailPassword() {
        return mailPassword;
    }

    public void setMailPassword(String mailPassword) {
        this.mailPassword = mailPassword;
    }

    public String getMailSmtp() {
        return mailSmtp;
    }

    public void setMailSmtp(String mailSmtp) {
        this.mailSmtp = mailSmtp;
    }

    public String getMailUser() {
        return mailUser;
    }

    public void setMailUser(String mailUser) {
        this.mailUser = mailUser;
    }

    public static final String MAIL_ADDR = "mail_addr";

    public static final String MAIL_PASSWORD = "mail_password";

    public static final String MAIL_SMTP = "mail_smtp";

    public static final String MAIL_USER = "mail_user";

    @Override
    protected Serializable pkVal() {
        return null;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SysConfig [mailAddr=");
        builder.append(mailAddr);
        builder.append(", mailPassword=");
        builder.append(mailPassword);
        builder.append(", mailSmtp=");
        builder.append(mailSmtp);
        builder.append(", mailUser=");
        builder.append(mailUser);
        builder.append("]");
        return builder.toString();
    }

}
