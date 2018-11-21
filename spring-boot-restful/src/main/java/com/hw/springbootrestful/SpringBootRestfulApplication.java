package com.hw.springbootrestful;

import com.hw.springbootrestful.utils.RedisCacheUtil;
import com.hw.springbootrestful.web.filter.XssFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author hw
 */
@EnableCaching
@EnableSwagger2
@SpringBootApplication
public class SpringBootRestfulApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootRestfulApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean MyFilterRegistration(RedisCacheUtil redisCacheUtil) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new XssFilter(redisCacheUtil));
        registration.addUrlPatterns("/*");
        registration.setName("xssFilter");
        registration.setOrder(1);
        return registration;
    }
}
