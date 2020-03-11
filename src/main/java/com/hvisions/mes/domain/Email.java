package com.hvisions.mes.domain;

import lombok.Data;

/**
 * @author dpeng
 * @description Email实体类
 * @date 2019-08-28 22:44
 */
@Data
public class Email {

    /** 邮件主题 */
    private String subject;
    /** 邮件显示内容 */
    private String content;
    /** 发送人 */
    private String sender;
    /** 收件人 */
    private String[] recipient;

}
