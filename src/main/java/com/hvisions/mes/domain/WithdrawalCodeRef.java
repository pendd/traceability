package com.hvisions.mes.domain;

import lombok.Data;

@Data
public class WithdrawalCodeRef {
    /**
     *
     */
    private Integer id;

    /**
     * 物料二维码
     */
    private String qrCode;

    /**
     * 退料表ID
     */
    private Integer withdrawalId;

    /**
     * 数量
     */
    private Integer amount;
}

