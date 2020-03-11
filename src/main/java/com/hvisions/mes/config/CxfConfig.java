//package com.hvisions.mes.config;
//import org.apache.cxf.Bus;
//
//import org.apache.cxf.jaxws.EndpointImpl;
//import org.apache.cxf.transport.servlet.CXFServlet;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.web.servlet.ServletRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import javax.xml.ws.Endpoint;
//
//import com.hvisions.mes.service.IUserService;
//
//
//@Configuration
//public class CxfConfig {
//    @Autowired
//     private Bus bus; //必须有的
//     @Autowired
//     private IUserService userService;
//
////        @Bean
////        public ServletRegistrationBean dispatcherServlet() {
////            return new ServletRegistrationBean(new CXFServlet(), "/test/*");
////        }
//
//        @Bean
//        public Endpoint endpoint() {
//            EndpointImpl endpoint = new EndpointImpl(bus, userService);
//            endpoint.publish("/IUserService");
//            return endpoint;
//        }
//
//
//
//
//}
