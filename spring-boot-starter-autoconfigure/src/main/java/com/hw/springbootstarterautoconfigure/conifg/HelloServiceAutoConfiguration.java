package com.hw.springbootstarterautoconfigure.conifg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description TODO
 * @Author hw
 * @Date 2018/11/18 23:01
 * @Version 1.0
 */
@Configuration
@EnableConfigurationProperties(HelloServiceProperties.class)
@ConditionalOnClass(HelloService.class)
@ConditionalOnProperty(prefix = "hello", matchIfMissing = true, value = "enabled")
public class HelloServiceAutoConfiguration {

    @Autowired
    HelloServiceProperties helloServiceProperties;

    @Bean
    @ConditionalOnMissingBean(HelloService.class)
    public HelloService helloService() {
        HelloService service = new HelloService();
        service.setMsg(helloServiceProperties.getMsg());
        return service;
    }
}
