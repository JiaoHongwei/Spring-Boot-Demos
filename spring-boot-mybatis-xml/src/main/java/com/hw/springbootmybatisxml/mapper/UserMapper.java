package com.hw.springbootmybatisxml.mapper;

import com.hw.springbootmybatisxml.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description TODO
 * @Author hw
 * @Date 2018/11/19 1:36
 * @Version 1.0
 */
@Mapper
public interface UserMapper {

    User findByName(String name);

    void insert(String name,int age);

}
