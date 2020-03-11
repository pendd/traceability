package com.hvisions.mes.util;

import org.springside.modules.mapper.JsonMapper;

import com.fasterxml.jackson.databind.JavaType;

public class JsonUtil {
    public static final String toJson(Object obj) {
        return new JsonMapper().toJson(obj);
    }

    public static final <T> T fromJson(String jsonString, Class<T> clazz) {
        return new JsonMapper().fromJson(jsonString, clazz);
    }

    public static final <T> T fromJson(String jsonString, JavaType javaType) {
        return new JsonMapper().fromJson(jsonString, javaType);
    }

    public static final void update(String jsonString, Object obj) {
        new JsonMapper().update(jsonString, obj);
    }
}
