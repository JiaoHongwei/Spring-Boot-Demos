package com.hw.springbootstarterautoconfigure.conifg;

/**
 * @Description TODO
 * @Author hw
 * @Date 2018/11/18 23:00
 * @Version 1.0
 */
public class HelloService {
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String sayHello() {
        return "hello " + msg;
    }
}
