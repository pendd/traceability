package com.hvisions.mes.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

//@ControllerAdvice(assignableTypes = { UserController.class })
public class SecurityResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    private static Logger logger = LoggerFactory.getLogger(SecurityResponseBodyAdvice.class);

    @Override
    public boolean supports(MethodParameter returnType,
            Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
            MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType,
            ServerHttpRequest request, ServerHttpResponse response) {
        logger.debug("============ beforeBodyWrite =============");

        //        HttpServletRequest req = ((ServletServerHttpRequest) request).getServletRequest();
        //        HttpServletResponse res = ((ServletServerHttpResponse) response).getServletResponse();
        //
        //        Security security = (Security)req.getAttribute(UserController.TOKEN_COOKIE_NAME);
        //        if(security!=null){
        //
        //        }
        return body;
    }

}
