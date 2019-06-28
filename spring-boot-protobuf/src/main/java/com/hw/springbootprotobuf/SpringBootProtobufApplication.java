package com.hw.springbootprotobuf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;

/**
 * Sring boot使用protobuf 序列化方式返回
 *
 * @author hw
 */
@SpringBootApplication
public class SpringBootProtobufApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootProtobufApplication.class, args);
    }

    @Bean
    ProtobufHttpMessageConverter protobufHttpMessageConverter() {
        return new ProtobufHttpMessageConverter();
    }
}

