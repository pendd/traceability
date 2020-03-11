package com.hvisions.mes.domain;

import lombok.Data;

/**
 * 原料入库中的叫料列表功能
 *
 * @author dpeng
 * @create 2019-04-03 16:44
 *   modified by dpeng  2019-05-14 11:26    后期要删除，务再使用
 */
@Data
public class CallOffMaterial {

    private Integer id;

    private Long detailId;

    private String materialCode;

    private int status;

}
