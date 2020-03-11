package com.hvisions.mes.security;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Security {
    /** user id */
    private Integer id;
    /** user phone */
    private String phone;
    private Integer type;
    /** 最后一次访问时间 */
    private Long time;
    private String ip;

    public String getPhone() {
        return phone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
