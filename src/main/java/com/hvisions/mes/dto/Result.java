package com.hvisions.mes.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result {
    private String message;
    private Integer code;
    private Object data;

    public static Result newSuccessResult(Object data, String message) {
        Result r = new Result();
        r.setMessage(message);
        r.setCode(200);
        r.setData(data);
        return r;
    }

    public static Result newSuccessResult(Object data) {
        return newSuccessResult(data, "ok");
    }

    public static Result newSuccessResult() {
        return newSuccessResult(null, "ok");
    }

    public static Result newSuccessResult(String message) {
        return newSuccessResult(null, message);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
