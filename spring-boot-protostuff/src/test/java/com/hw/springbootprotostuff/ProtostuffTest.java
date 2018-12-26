package com.hw.springbootprotostuff;

import com.hw.springbootprotostuff.entity.Student;
import com.hw.springbootprotostuff.utils.ProtostuffUtil;
import org.junit.Test;

import java.util.Arrays;
import java.util.UUID;

/**
 * @Description TODO
 * @Author hw
 * @Date 2018/12/26 15:00
 * @Version 1.0
 */
public class ProtostuffTest {

    @Test
    public void test1() {
        Student student = new Student();
        student.setId(UUID.randomUUID().toString());
        student.setName("protostuff");
        student.setAge(18);
        student.setSex(1);
        System.out.println("序列化前:" + student.toString());
        long startTime = System.currentTimeMillis();
        byte[] serializer = ProtostuffUtil.serializer(student);
        long endTime = System.currentTimeMillis();
        System.out.println("序列化后:" + (endTime - startTime) + "ms " + serializer.length + " " + Arrays.toString(serializer));


        long startTime1 = System.currentTimeMillis();
        Student deserializer = ProtostuffUtil.deserializer(serializer, Student.class);
        long endTime1 = System.currentTimeMillis();
        System.out.println("反序列化:" + (endTime1 - startTime1) + "ms " + deserializer.toString());
    }
}
