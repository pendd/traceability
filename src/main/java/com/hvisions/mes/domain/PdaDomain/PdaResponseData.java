package com.hvisions.mes.domain.PdaDomain;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
*
* 原料入库实现类
* @author mtyu
* @since 2019-03-08
*/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PdaResponseData implements Serializable {

    /**返回状态 1失败*/
    public final static Integer STATUS_FAIL = 1;

    /**返回状态 0:成功,1.失败*/
    private Integer status = 0;
    /**返回错误信息*/
    private String errorMessage = "";
    /**所有的数据存放*/
    private Map<String, Object> data = new HashMap<>();

    /** 错误码 */
    private Integer code;
    /** 后台程序错误信息 */
    private String msg;
    /** 错误名称 */
    private String name;

}

