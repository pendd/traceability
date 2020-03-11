package com.hvisions.mes.service.exception;

import java.util.ArrayList;

import org.springframework.context.MessageSource;

import com.hvisions.mes.controller.support.ErrorResult;
import com.hvisions.mes.controller.support.ErrorResults;
import com.hvisions.mes.util.I18nUtil;

public class ValidationException extends RuntimeException {
    private static final long serialVersionUID = -2491748850990303837L;

    public ErrorCode errorCode;
    public ErrorResults errorResults;
    private MessageSource messageSource;

    public ValidationException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;

        this.errorResults = new ErrorResults(errorCode.code, message);
    }

    public ValidationException(String messageCode, ErrorCode errorCode,
            MessageSource messageSource) {
        this(I18nUtil.getMessage(messageCode, messageSource), errorCode);
        this.messageSource = messageSource;
    }

    public void addErrorResult(ErrorResult errorResult) {
        if (this.errorResults.errors == null) {
            this.errorResults.errors = new ArrayList<>();
        }
        this.errorResults.errors.add(errorResult);
    }

    public void addErrorResult(String name, String messageCode) {
        ErrorResult errorResult = new ErrorResult(name,
                I18nUtil.getMessage(messageCode, messageSource));
        addErrorResult(errorResult);
    }

    public boolean hasError() {
        if (this.errorResults.errors == null) {
            return false;
        }
        return this.errorResults.errors.size() > 0;
    }
}
