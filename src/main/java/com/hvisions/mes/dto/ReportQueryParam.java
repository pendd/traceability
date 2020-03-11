package com.hvisions.mes.dto;

public class ReportQueryParam {
    private String startTime;
    private String endTime;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public static final ReportQueryParam newInstance(String startTime, String endTime) {
        ReportQueryParam param = new ReportQueryParam();
        param.setStartTime(startTime);
        param.setEndTime(endTime);

        return param;
    }
}
