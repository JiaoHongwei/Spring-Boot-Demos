package com.hw.springbootprotostuff.controller;

import com.hw.springbootprotostuff.dto.RestResponse;
import com.hw.springbootprotostuff.entity.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @Description TODO
 * @Author hw
 * @Date 2018/12/26 15:34
 * @Version 1.0
 */
@RestController
@RequestMapping("api")
public class BusinessController {

    @RequestMapping("hello")
    public ResponseEntity hello(@RequestParam String name) {
        Student student = new Student();
        student.setId(UUID.randomUUID().toString());
        student.setName(name);
        student.setAge(18);
        student.setSex(1);
        return ResponseEntity.ok(student);
    }

    @RequestMapping("test")
    public RestResponse test(@RequestParam String name) {
        Student student = new Student();
        student.setId(UUID.randomUUID().toString());
        student.setName(name);
        student.setAge(18);
        student.setSex(1);
        return new RestResponse("200", student.toString());
    }

}
