package com.hw.springbootguava.controller;

import com.hw.springbootguava.service.AccessLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 * @Author hw
 * @Date 2018/12/20 11:26
 * @Version 1.0
 */
@RestController
public class BusinessController {

    @Autowired
    private AccessLimitService accessLimitService;

    @RequestMapping("/hello")
    public ResponseEntity hello(@RequestParam String id) throws InterruptedException {
        System.out.println("request ... " + id + " " + System.currentTimeMillis());

        if (accessLimitService.tryAcquire()) {
            return ResponseEntity.ok("aceess success " + id + " [" + System.currentTimeMillis() + "]");
        } else {
            return ResponseEntity.ok("aceess limit " + id + " [" + System.currentTimeMillis() + "]");
        }
    }

}
