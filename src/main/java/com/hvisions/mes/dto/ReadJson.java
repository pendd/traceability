package com.hvisions.mes.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author dpeng
 * @description 服务器上配置相关信息
 * @date 2019-09-16 20:24
 */
@Data
public class ReadJson {

    /**
     *  批号
     */
    private String batch;

    /**
     *  批号截至时间
     */
    private Date endTime;


}
