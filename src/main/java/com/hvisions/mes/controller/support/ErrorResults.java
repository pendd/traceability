package com.hvisions.mes.controller.support;

import java.util.List;

public class ErrorResults {
    public int code;
    public String message;

    public List<ErrorResult> errors;

    public ErrorResults() {
    }

    public ErrorResults(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
