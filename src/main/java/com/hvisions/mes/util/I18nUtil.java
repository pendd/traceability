package com.hvisions.mes.util;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class I18nUtil {
    /**
     * 获得本地化消息.
     *
     * @param code
     *            消息id.比如: user.signup.error.name
     * @param messageSource
     *            实现了MessageSource接口的任意实例,通常为ApplicationContext对象.非空.
     * @param args
     *            对应code中的参数. 比如: user.signup.error.name = {0} not valid.
     */
    public static final String getMessage(String code, MessageSource messageSource,
            Object... args) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(code, args, "", locale);
    }

    /**
     * 获得本地化消息.
     *
     * @param code
     *            消息id.比如: user.signup.error.name
     * @param messageSource
     *            实现了MessageSource接口的任意实例,通常为ApplicationContext对象.非空.
     */
    public static final String getMessage(String code, MessageSource messageSource) {
        return getMessage(code, messageSource, (Object[]) null);
    }
}
