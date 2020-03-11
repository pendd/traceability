package com.hvisions.mes.controller.vo;

import com.hvisions.mes.domain.LineStoreroomOutHistory;
import lombok.Data;

/**
 * 线边库出库记录展示vo类
 *
 * @author dpeng
 * @create 2019-03-13 16:11
 */
@Data
public class LineStoreroomOutHistoryVo {

    /**
     *  线边库出库对象
     */
    private LineStoreroomOutHistory lineStoreroomOutHistory;

    /**
     *  原料库   线边库
     */
    private String storeroomName;

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
}
