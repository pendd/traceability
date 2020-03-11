package com.hvisions.mes.dto;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author dpeng
 * @date 2019-11-20 13:54
 */
public class GoodsStoreroomRefDTO {

    @NotBlank private String cMoCode;

    @NotBlank private String materialSignCode;

    @NotBlank private String qrGoodsCode;

    public String getcMoCode() {
        return cMoCode;
    }

    public void setcMoCode(String cMoCode) {
        this.cMoCode = cMoCode;
    }

    public String getMaterialSignCode() {
        return materialSignCode;
    }

    public void setMaterialSignCode(String materialSignCode) {
        this.materialSignCode = materialSignCode;
    }

    public String getQrGoodsCode() {
        return qrGoodsCode;
    }

    public void setQrGoodsCode(String qrGoodsCode) {
        this.qrGoodsCode = qrGoodsCode;
    }

    @Override
    public String toString() {
        return "GoodsStoreroomRefDTO{" +
                "cMoCode='" + cMoCode + '\'' +
                ", materialSignCode='" + materialSignCode + '\'' +
                ", qrGoodsCode='" + qrGoodsCode + '\'' +
                '}';
    }
}
