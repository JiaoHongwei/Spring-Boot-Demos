package com.hw.springbootthymeleaf.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Description TODO
 * @Author hw
 * @Date 2018/11/21 18:07
 * @Version 1.0
 */
@Controller
public class HelloController {

    @GetMapping("index/{data}")
    public String index(Model model, @PathVariable String data) {
        model.addAttribute("key", data);
        return "index";
    }
}
