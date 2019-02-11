package com.hw.springbootredisdistributedlock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * redis 分布式锁实现
 * @author hw
 */
@SpringBootApplication
public class SpringBootRedisDistributedLockApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootRedisDistributedLockApplication.class, args);
    }

}

