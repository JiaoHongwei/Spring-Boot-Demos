package com.hw.springbootmybatis.entity;

/**
 * @Description TODO
 * @Author hw
 * @Date 2018/11/19 1:10
 * @Version 1.0
 */
public class User {

    private Long id;
    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
