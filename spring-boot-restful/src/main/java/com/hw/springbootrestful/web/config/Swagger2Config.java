package com.hw.springbootrestful.web.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description swagger ui 配置文件
 * @Author hw
 * @Date 2018/11/21 11:52
 * @Version 1.0
 */
@Configuration
@ConditionalOnProperty(prefix = "swagger", value = {"enable"}, havingValue = "true")  //  配置文件配置是否启动swagger配置文件
public class Swagger2Config {


    @Bean
    public Docket createRestApi() {
        List<Parameter> params = new ArrayList<Parameter>();
        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(params)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hw.springbootrestful"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Boot 2.x RESTful APIs")
                .version("1.0")
                .build();
    }
}
