package com.hvisions.mes.security;

import com.hvisions.mes.Param;
import com.hvisions.mes.service.exception.ErrorCode;
import com.hvisions.mes.service.exception.ValidationException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 权限处理
 * @author 王海巍
 *
 */
//@Aspect
//@Component
public class PermissionAspect {
    private static Logger logger = LoggerFactory.getLogger(PermissionAspect.class);

    @Before("execution(public * com.hvisions.mes.rest.*Controller.*(Long,..))")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "."
                + joinPoint.getSignature().getName());
        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));

        ApplicationContext ctx = RequestContextUtils.findWebApplicationContext(request);

        Object[] args = joinPoint.getArgs();

        // 权限验证:
        // 1: 对id进行验证,只能操作自己的id. 被验证的控制器中的方法第一个参数必须是Long,用户id
        Integer id = (Integer) args[0];
        Security security = (Security) request.getAttribute(Param.TOKEN_COOKIE_NAME);
        Integer yourId = security.getId();
        if (yourId == null || !yourId.equals(id)) {
            logger.warn("越权行为! 不能操作其他id的数据. 你的id:%d 要操作的的id:%d", yourId, id);
            ValidationException ex = new ValidationException("permission.no", ErrorCode.FORBIDDEN,
                    ctx);
            ex.addErrorResult("id", "permission.no");
            throw ex;
        }

    }
}
