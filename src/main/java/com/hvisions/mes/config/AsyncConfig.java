package com.hvisions.mes.config;

import java.util.concurrent.Executor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author dpeng
 */
@Configuration
@EnableAsync
public class AsyncConfig {

  @Value("${hvisions.corePoolSize}")
  private Integer corePoolSize;

  @Value("${hvisions.maxPoolSize}")
  private Integer maxPoolSize;

  @Value("${hvisions.queueCapacity}")
  private Integer queueCapacity;

  @Bean
  public Executor taskExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(corePoolSize);
    executor.setMaxPoolSize(maxPoolSize);
    executor.setQueueCapacity(queueCapacity);
    executor.initialize();
    return executor;
  }
}