package com.hw.springbootzookeeperdistributedlock.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.TimeUnit;

/**
 * @Description 基于zookeeper的开源客户端Cruator实现分布式锁
 * @Author hw
 * @Date 2019/2/12 11:27
 * @Version 1.0
 */
@Slf4j
public class ZookeeperDistributedLock {
    // 可重入排它锁
    private InterProcessMutex interProcessMutex;
    /**
     * 竞争资源标识
     */
    private String lockName;
    /**
     * zookeeper 分布式锁根节点
     */
    private String root = "/distributed/lock/";

    private static String ZK_URL = "127.0.0.1:2181";

    private static CuratorFramework curatorFramework;

    static {
        curatorFramework = CuratorFrameworkFactory.newClient(ZK_URL, new ExponentialBackoffRetry(1000, 3));
        curatorFramework.start();
    }

    /**
     * 实例化 InterProcessMutex
     *
     * @param lockName
     */
    public ZookeeperDistributedLock(String lockName) {
        this.lockName = lockName;
        interProcessMutex = new InterProcessMutex(curatorFramework, root + lockName);
    }


    /**
     * 获取锁
     */
    public void acquireLock() {
        /**
         * 阻塞等待的
         */
//        try {
//            interProcessMutex.acquire();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        /**
         * 最大重试次数 和 等待时间
         */
        int flag = 0;
        try {
            //重试2次，每次最大等待2s，也就是最大等待4s
            while (!interProcessMutex.acquire(2, TimeUnit.SECONDS)) {
                flag++;
                // 重试两次
                if (flag > 1) {
                    break;
                }
            }
        } catch (Exception e) {
            log.error("Zookeeper Distributed Lock Acquire Exception,message:" + e.getMessage());
        }

        if (flag > 1) {
            log.info("Thread:" + Thread.currentThread().getId() + " acquire distributed lock  busy");
        } else {
            log.info("Thread:" + Thread.currentThread().getId() + " acquire distributed lock  success");
        }

    }

    /**
     * 释放锁
     */
    public void releaseLock() {
        try {
            if (interProcessMutex != null && interProcessMutex.isAcquiredInThisProcess()) {
                interProcessMutex.release();
                curatorFramework.delete().inBackground().forPath(root + lockName);
                log.info("Thread:" + Thread.currentThread().getId() + " release distributed lock  success");
            }
        } catch (Exception e) {
            log.error("Thread:" + Thread.currentThread().getId() + " release distributed lock  exception=" + e.getMessage());
        }
    }
}
