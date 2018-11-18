package com.hw.springbootstarterautoconfigure.web;

import com.hw.springbootstarterautoconfigure.conifg.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 * @Author hw
 * @Date 2018/11/18 23:07
 * @Version 1.0
 */
@RestController
public class HelloController {

    /**
     * 代码中没有配置这个helloService Bean，但是自动配置能够帮忙实例化，因此可以直接注入
     */
    @Autowired
    HelloService helloService;

    @RequestMapping(value = "/helloService")
    public String sayHello() {
        return helloService.sayHello();
    }
}
