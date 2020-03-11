package com.hvisions.mes.util;

public class NumberUtil {
    /**
     * 转换value为int值,如果value为null则返回0
     */
    public static final int toInt(Number value) {
        value = nullToZero(value);
        return value.intValue();
    }

    /**
     * 转换value为float值,如果value为null则返回0
     */
    public static final float toFloat(Number value) {
        value = nullToZero(value);
        return value.floatValue();
    }

    /**
     * 转换value为double值,如果value为null则返回0
     */
    public static final double toDouble(Number value) {
        value = nullToZero(value);
        return value.doubleValue();
    }

    /**
     * 转换null值为0
     */
    public static final Number nullToZero(Number value) {
        return value == null ? 0 : value;
    }
}
