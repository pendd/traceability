package com.hvisions.mes.domain;

import java.util.Date;
import lombok.Data;

/**
 * @author dpeng
 * @description PDA 消息通知
 * @date 2019-07-18 09:10
 */
@Data
public class Notice {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 通知消息
     */
    private String noticeMessage;

    /**
     * 通知时间
     */
    private Date createTime;

    /**
     * 库房ID
     */
    private Integer storeroomId;

    /**
     * 是否已读  0未读 1已读
     */
    private Integer isRead;

    /**
     * 是否已弹出  0未弹出  1已弹出
     */
    private Integer isAlert;
}

