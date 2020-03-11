package com.hvisions.mes.domain;

import java.util.Date;
import lombok.Data;

@Data
public class EquipmentTpjHeadS {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 旋臂ID
     */
    private Integer gantryId;

    /**
     * 旋臂上的头ID  一个旋臂上10个头
     */
    private Integer headId;

    /**
     * 吸料数量
     */
    private Integer pickUp;

    /**
     * 贴装数量
     */
    private Integer place;

    /**
     * 吸料错误数量
     */
    private Integer pickError;

    /**
     * 检测出错数量
     */
    private Integer visionError;

    /**
     * 抛料数量
     */
    private Integer dump;

    /**
     *
     */
    private Integer equipmentId;

    /**
     *
     */
    private Date dtCreate;

    /**
     *
     */
    private String workNumber;
}

