package com.hvisions.mes;

/**
 * 常量类
 *
 * @author 王海巍
 *
 */
public class Param {
    private Param() {
    }

    public static final String URL_LOGIN = "/login";
    public static final String URL_LOGOUT = "/logout";
    public static final String URL_LICENSE = "/json/user/license";

    public static final String TOKEN_COOKIE_NAME = "token";
    /** cookie最大存活时间,10小时 */
    public static final int TOKEN_COOKIE_MAX_AGE = 60 * 60 * 10;
    public static final String TOKEN_COOKIE_PATH = "/";

    public static final String USER_ID = "emp_id";
    public static final String ACCOUNT_ID = "account_id";

    /** 用户类型:正常 */
    public static final Integer USER_TYPE_NORMAL = 1;
    /** 记录状态标记:管理员 */
    public static final Integer USER_TYPE_ADMIN = 2;

    /** 记录状态标记:正常 */
    public static final Integer FLAG_NORMAL = 1;
    /** 记录状态标记:已删除 */
    public static final Integer FLAG_DELETE = 2;
    /** 记录状态标记:已禁用 */
    public static final Integer FLAG_DISABLE = 3;

    /** 定时器执行间隔时间，单位毫秒 */
    public static final int ORDER_TIMER_INTERVAL = 20000;

    /** 区间（秒） */
    public static final int DURATION_SECOND = 1;
    /** 区间（分钟） */
    public static final int DURATION_MINUTE = 2;
    /** 区间（小时） */
    public static final int DURATION_HOUR = 3;
    /** 区间（班次） */
    public static final int DURATION_SHIFT = 4;
    /** 区间（日） */
    public static final int DURATION_DAY = 5;
    /** 区间（周） */
    public static final int DURATION_WEEK = 6;
    /** 区间（月） */
    public static final int DURATION_MONTH = 7;
    /** 区间（季度） */
    public static final int DURATION_QUARTER = 8;
    /** 区间（年） */
    public static final int DURATION_YEAR = 9;
}
