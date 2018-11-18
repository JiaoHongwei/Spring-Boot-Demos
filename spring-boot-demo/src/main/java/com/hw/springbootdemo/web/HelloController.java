package com.hw.springbootdemo.web;

import org.springframework.web.bind.annotation.*;

/**
 * @Description TODO
 * @Author hw
 * @Date 2018/11/18 20:47
 * @Version 1.0
 */
@RestController
@RequestMapping("api/v1")
public class HelloController {

    @GetMapping(value = "hello")
    public String hello() {
        return "Hello Spring Boot!";
    }
}
