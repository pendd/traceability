package com.hvisions.mes.util;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class ValidationUtil {
    /**
     * 联系电话(手机/电话皆可)验证
     */
    public static final boolean isTel(String text) {
        if (isMobile(text) || isPhone(text)) {
            return true;
        }

        return false;
    }

    /**
     * 电话号码验证
     */
    public static final boolean isPhone(String text) {
        return match(text, "^(\\d{3,4}-?)?\\d{7,9}$");
    }

    /**
     * 手机号码验证
     */
    public static final boolean isMobile(String text) {
        return match(text, "[0-9]{11}");
    }

    /**
     * 密码验证. 只能包含大小写字母,数字和下划线
     */
    public static final boolean isPassword(String pwd) {
        return match(pwd, "^[_A-Za-z0-9]{6,22}$");
    }

    /**
     * 正则表达式匹配
     *
     * @param text
     *            待匹配的文本
     * @param reg
     *            正则表达式
     */
    private static final boolean match(String text, String reg) {
        if (StringUtils.isBlank(text) || StringUtils.isBlank(reg)) {
            return false;
        }

        return Pattern.compile(reg).matcher(text).matches();
    }
}
