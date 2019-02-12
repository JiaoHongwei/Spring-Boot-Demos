package com.hw.springbootzookeeperdistributedlock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 通过curator 实现 zookeeper 分布式锁
 * @author hw
 */
@SpringBootApplication
public class SpringBootZookeeperDistributedLockApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootZookeeperDistributedLockApplication.class, args);
    }

}

