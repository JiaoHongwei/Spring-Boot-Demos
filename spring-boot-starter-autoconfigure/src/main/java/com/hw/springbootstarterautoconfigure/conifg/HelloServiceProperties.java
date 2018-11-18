package com.hw.springbootstarterautoconfigure.conifg;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Description TODO
 * @Author hw
 * @Date 2018/11/18 22:57
 * @Version 1.0
 */

/**
 * @ConfigurationProperties 自动匹配application.properties文件中hello.msg的值，然后赋值给类属性msg，这里的msg默认值为“spring boot”
 */
@ConfigurationProperties(prefix = "hello")
public class HelloServiceProperties {

    private static final String MSG = "spring boot";

    private String msg = MSG;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
