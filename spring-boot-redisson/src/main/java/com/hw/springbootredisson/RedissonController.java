package com.hw.springbootredisson;

import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
        map.put("2", "3");
        map.put("3", "4");

        BoundHashOperations<String, String, String> hashOperations = redisTemplate.boundHashOps("test");
        String t1 = hashOperations.get("1");
        String t2 = hashOperations.get("2");
        String t3 = hashOperations.get("3");

        return ResponseEntity.ok(t1 + '_' + t2 + '_' + t3);
    }

    @RequestMapping("distributedLock/{count}")
    public ResponseEntity distributedLock(@PathVariable int count) {
        long startTime = System.currentTimeMillis();
        CountDownLatch countDownLatch = new CountDownLatch(count);
        ThreadPoolExecutor service = new ThreadPoolExecutor(
                count, count, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>(),
                Thread::new, new ThreadPoolExecutor.DiscardOldestPolicy()
        );
        RLock lock = redissonClient.getLock("anyLock");
        boolean ifLock = count % 2 == 0;
        for (int i = 0; i < count; i++) {
            service.execute(() -> {
                try {
                    countDownLatch.await();
                    if (ifLock) {
                        lock.lock();
                    }
                    System.out.println(Thread.currentThread().getName() + " get distributed lock... " + System.currentTimeMillis());
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(Thread.currentThread().getName() + " del distributed lock... " + System.currentTimeMillis());
                    if (ifLock) {
                        lock.unlock();
                    }
                }
            });
            countDownLatch.countDown();
        }
        service.shutdown();
        return ResponseEntity.ok(count);
    }

}
