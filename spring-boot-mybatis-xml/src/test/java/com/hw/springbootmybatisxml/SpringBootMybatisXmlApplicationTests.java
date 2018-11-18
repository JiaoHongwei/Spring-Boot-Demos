package com.hw.springbootmybatisxml;

import com.hw.springbootmybatisxml.entity.User;
import com.hw.springbootmybatisxml.mapper.UserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootMybatisXmlApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void contextLoads() {
    }


    @Test
    @Rollback
    public void findByName() throws Exception {
        userMapper.insert("ccc", 20);
        User u = userMapper.findByName("ccc");
        Assert.assertEquals(20, u.getAge().intValue());
    }

}
