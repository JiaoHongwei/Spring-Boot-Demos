package com.hw.springbootzookeeperdistributedlock;

import com.hw.springbootzookeeperdistributedlock.utils.ZookeeperDistributedLock;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;

/**
 * @Description TODO
 * @Author hw
 * @Date 2019/2/12 11:57
 * @Version 1.0
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ZKDistributedLockTest {

    @Test
    public void test_no_lock() {
        final CountDownLatch down = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    down.await();
                    Thread.sleep(5000);
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss|SSS");
                String orderNo = sdf.format(new Date());
                log.error("生成的订单号是：" + orderNo);
            }).start();
            down.countDown();
        }
    }

    @Test
    public void test_distributed_lock() {

        ZookeeperDistributedLock lock = new ZookeeperDistributedLock("ORDER_NUM");
        for (int i = 0; i < 30; i++) {
            try {
                lock.acquireLock();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss|SSS");
                String orderNo = sdf.format(new Date());
                log.info("生成的订单号是：" + orderNo);
            } finally {
                lock.releaseLock();
            }
        }
    }
}
