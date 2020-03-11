package com.hvisions.mes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author dpeng
 * @description 开启WebSocket支持
 * @date 2019-07-19 15:39
 */
@Configuration
public class WebSocketConfig {

//  @Bean
  public ServerEndpointExporter serverEndpointExporter() {
    return new ServerEndpointExporter();
  }
}
