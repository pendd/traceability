package com.hvisions.mes.domain.PdaDomain;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class PdaRequestParam {

    private String language;
    private String version;
    private String account;
    private Integer userId;
    private Map<String,Object> params = new HashMap<>();

}
