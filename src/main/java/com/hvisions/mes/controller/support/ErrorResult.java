package com.hvisions.mes.controller.support;

public class ErrorResult {

    public int status;
    public String errorMessage;

    public int code;
    public String message;
    public String name;


    public ErrorResult() {
    }

    public ErrorResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorResult(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public ErrorResult(int code, String name, String message) {
        this.code = code;
        this.name = name;
        this.message = message;
    }


}