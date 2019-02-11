package com.hw.springbootredisdistributedlock;

import com.hw.springbootredisdistributedlock.util.RedisDistributedLock;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description TODO
 * @Author hw
 * @Date 2019/2/11 18:26
 * @Version 1.0
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class DistributedLockTest {


    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test() {
        String key = "com.hw";
        RedisDistributedLock lock = new RedisDistributedLock(redisTemplate, key, 5000, 20000);
        try {
            System.out.println("获取锁...");
            if (lock.lock()) {
                // 需要加锁的代码
                System.out.println("代码开始执行...");
                Thread.sleep(5000);
                System.out.println("代码执行结束...");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //为了让分布式锁的算法更稳键些，持有锁的客户端在解锁之前应该再检查一次自己的锁是否已经超时，再去做DEL操作，因为可能客户端因为某个耗时的操作而挂起，
            //操作完的时候锁因为超时已经被别人获得，这时就不必解锁了。 ————这里没有做
            lock.unlock();
            System.out.println("释放锁...");
        }
    }
}
