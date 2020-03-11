package com.hvisions.mes.domain;

import java.util.Date;
import lombok.Data;

@Data
public class AssignRefCode {
    /**
     *
     */
    private Integer id;

    /**
     *
     */
    private Date createTime;

    /**
     * 壳子上的码
     */
    private String goodsQrCode;

    /**
     * 壳子里面的成品的二维码
     */
    private String materialQrCode;

    /**
     * 操作员
     */
    private Integer userId;


    /**
     *  生产工单号
     */
    private String produceNumber;

    private Integer orderId;

    private Integer teamId;

    /**
     *  产品码
     */
    private String goodsCode;

    private Integer lineId;
}

