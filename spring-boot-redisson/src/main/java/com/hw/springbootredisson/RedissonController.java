package com.hw.springbootredisson;

import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 * @Author hw
 * @Date 2019/2/22 18:29
 * @Version 1.0
 */
@RestController
@RequestMapping("api")
public class RedissonController {
    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @RequestMapping("get")
    public ResponseEntity getRedisson() {
        System.out.println("getRedisson...");

        redissonClient.getKeys().flushall();
        RMap<String, String> map = redissonClient.getMap("test", StringCodec.INSTANCE);
        map.put("1", "2");

        BoundHashOperations<String, String, String> hashOperations = redisTemplate.boundHashOps("test");
        String t = hashOperations.get("1");
        Assert.isTrue(t.equals("2"),"是否相等");

        return ResponseEntity.ok(t);
    }

}
