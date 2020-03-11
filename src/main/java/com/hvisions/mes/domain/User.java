package com.hvisions.mes.domain;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Builder(toBuilder = true)
@Accessors(fluent = true)
public class User {
    /**
     *
     */
    private String name;

    /**
     *
     */
    private Integer age;
}

