package com.hvisions.mes.controller.vo;

import com.hvisions.mes.domain.LineStoreroomHistory;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 线边库出库记录展示vo类
 *
 * @author dpeng
 * @create 2019-03-13 16:11
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class LineStoreroomHistoryVo extends LineStoreroomHistory{

    /**
     *  出库  入库
     */
    private String inOutTypeName;

    /**
     *  原料库   线边库
     */
    private String storeroomName;

    /**
     *  当前线边库名称
     */
    private String lineStoreroomName;

    /**
     *  已到达  未到达
     */
    private String arrive;

    /**
     *  原料名
     */
    private String materialName;

    /**
     *  员工名
     */
    private String empName;

    /**
     *  班组名
     */
    private String teamName;

    /**
     *  供应商名称
     */
    private String supplierName;
}
