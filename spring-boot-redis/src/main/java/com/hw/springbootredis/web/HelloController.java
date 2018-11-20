package com.hw.springbootredis.web;

import com.hw.springbootredis.entity.User;
import com.hw.springbootredis.service.HelloService;
import com.hw.springbootredis.utils.RedisCacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @Description TODO
 * @Author hw
 * @Date 2018/11/20 12:00
 * @Version 1.0
 */
@RestController
@RequestMapping("api/v1")
public class HelloController {

    private static final String TMP_KEY = "hello redis";

    @Autowired
    private RedisCacheUtil redisCacheUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private HelloService helloService;

    /**
     * 通过 redisCacheUtil 操作redis
     */
    @GetMapping(value = "hello_1")
    public String hello() {
        long count = redisCacheUtil.getIncrement(TMP_KEY);
        //设置有效期 60 分钟
        redisCacheUtil.expire(TMP_KEY, 60 * 60, TimeUnit.SECONDS);
        System.out.println("Hello Redis " + count);
        return "1小时之内访问了" + count + "次";
    }

    /**
     * 通过 redisTemplate 操作
     */
    @GetMapping(value = "hello_2")
    public String hello2() {

        redisTemplate.opsForValue();//操作字符串
        redisTemplate.opsForHash();//操作hash
        redisTemplate.opsForList();//操作list
        redisTemplate.opsForSet();//操作set
        redisTemplate.opsForZSet();//操作有序set

        redisTemplate.opsForValue().set("name", "hello2", 10, TimeUnit.MINUTES);
        return Objects.requireNonNull(redisTemplate.opsForValue().get("name")).toString();
    }

    /**
     * 通过 stringRedisTemplate 操作
     */
    @GetMapping(value = "hello_3")
    public String hello3() {

        System.out.println(stringRedisTemplate.opsForValue().get("name"));
        stringRedisTemplate.opsForValue();//操作字符串
        stringRedisTemplate.opsForHash();//操作hash
        stringRedisTemplate.opsForList();//操作list
        stringRedisTemplate.opsForSet();//操作set
        stringRedisTemplate.opsForZSet();//操作有序set

        stringRedisTemplate.opsForValue().set("name", "hello3", 10, TimeUnit.SECONDS);
        return Objects.requireNonNull(stringRedisTemplate.opsForValue().get("name"));
    }

    /**
     * 通过 @Cacheable 注解使用
     */
    @GetMapping(value = "hello")
    public String sayHello() {
//        helloService.insert("hw");
        User user = helloService.get("hw");
        return user.getAge().toString();
    }
}

