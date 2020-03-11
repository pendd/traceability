package com.hvisions.mes.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springside.modules.mapper.JsonMapper;

import com.hvisions.mes.Param;
import com.hvisions.mes.service.exception.ErrorCode;
import com.hvisions.mes.service.exception.ValidationException;
import com.hvisions.mes.util.CookieUtil;

/**
 * 拦截请求, 验证token. 所拦截的url在 {@link com.hvisions.mes.config.MesWebMvcConfig#addInterceptors} 配置.
 * @author 王海巍
 *
 */
//@Component
public class SecurityInterceptor extends HandlerInterceptorAdapter {

    private static Logger logger = LoggerFactory.getLogger(SecurityInterceptor.class);

    private JsonMapper jsonMapper = JsonMapper.nonEmptyMapper();

    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {
        logger.debug("================ preHandle ===================");
        logger.debug(request.getRequestURI());

        String token = CookieUtil.getTokenCookieValue(request);

        if (StringUtils.isBlank(token)) {
            ApplicationContext ctx = RequestContextUtils.findWebApplicationContext(request);
            logger.warn("token cookie not found. ");
            throw new ValidationException("token.error.notfound", ErrorCode.UNAUTHORIZED, ctx);
        }

        //解密
        token = EncryptUtil.decrypt(token);
        Security security = jsonMapper.fromJson(token, Security.class);
        if (hasExpire(security.getTime())) {
            ApplicationContext ctx = RequestContextUtils.findWebApplicationContext(request);
            logger.warn("token cookie timeout. ");
            throw new ValidationException("token.error.timeout", ErrorCode.UNAUTHORIZED, ctx);
        }

        //加密
        security.setTime(System.currentTimeMillis());
        token = EncryptUtil.encrypt(jsonMapper.toJson(security));

        CookieUtil.setTokenCookie(token, response);
        request.setAttribute(Param.TOKEN_COOKIE_NAME, security);

        //将当前用户ID放置在request中
        request.setAttribute(Param.USER_ID, security.getId());


        return true;
    }

    /**
     * 是否过期
     * @param time 要比较的时间,单位毫秒
     */
    private boolean hasExpire(long time) {
        long currentTime = System.currentTimeMillis();
        return (currentTime - time) >= Param.TOKEN_COOKIE_MAX_AGE * 1000;
    }
}
