package com.hvisions.mes.service.exception;

public enum ErrorCode {

    BAD_REQUEST(400, 400),
    UNAUTHORIZED(401, 401),
    UNBIND_ACCOUNT(402, 404),
    UNBIND_OK(404, 200),
    UNBIND_TEMPLATE(403, 404),
    FORBIDDEN(403, 403),
    INTERNAL_SERVER_ERROR(500, 500),

    SIGNUP_ERROR(1000, 400),

    NO_TOKEN(1102, 401),
    REPEAT_LOGIN(1103, 400),
    USER_ERROR_NAME(1104,300);
    public int code;
    public int httpStatus;

    ErrorCode(int code, int httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
    }
}
