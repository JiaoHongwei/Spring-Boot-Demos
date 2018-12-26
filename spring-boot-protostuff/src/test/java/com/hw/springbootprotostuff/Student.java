package com.hw.springbootprotostuff;

import io.protostuff.Tag;

/**
 * @Description protostuff 实体类
 * @Author hw
 * @Date 2018/12/26 14:50
 * @Version 1.0
 */
public class Student {
    @Tag(1)
    private String ssss;
    @Tag(2)
    private String name;
    @Tag(3)
    private int age;
    @Tag(4)
    private int sex;

    @Override
    public String toString() {
        return "Student{" +
                "ssss='" + ssss + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                '}';
    }

    public String getSsss() {
        return ssss;
    }

    public void setSsss(String ssss) {
        this.ssss = ssss;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
