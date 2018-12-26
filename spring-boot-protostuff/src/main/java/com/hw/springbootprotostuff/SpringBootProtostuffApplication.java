package com.hw.springbootprotostuff;

import com.hw.springbootprotostuff.config.ProtostuffHttpMessageConverter;
import org.hibernate.validator.constraints.EAN;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootProtostuffApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootProtostuffApplication.class, args);
    }

    /**
     * 注入bean 使用自定义的 protobuff 序列化
     *
     * @return
     */
    @Bean
    public ProtostuffHttpMessageConverter protostuffHttpMessageConverter() {
        return new ProtostuffHttpMessageConverter();
    }
}

