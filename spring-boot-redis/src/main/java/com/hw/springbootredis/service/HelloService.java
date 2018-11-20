package com.hw.springbootredis.service;

import com.hw.springbootredis.entity.User;
import com.hw.springbootredis.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @Description TODO
 * @Author hw
 * @Date 2018/11/20 17:31
 * @Version 1.0
 */
@Service
public class HelloService {

    @Autowired
    private UserMapper userMapper;


    public void insert(String hw) {
        userMapper.insert(hw, 20);
    }

    /**
     * @Cacheable 使用redis存储，默认没有过期时间设置
     * @param name
     * @return
     */
    @Cacheable(value = "getUser", key = "'name='+ #name")
    public User get(String name) {
        return userMapper.findByName(name);
    }
}
