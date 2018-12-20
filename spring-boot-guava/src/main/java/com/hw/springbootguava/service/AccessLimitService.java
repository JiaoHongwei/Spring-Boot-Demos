package com.hw.springbootguava.service;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.stereotype.Service;

/**
 * @Description TODO
 * @Author hw
 * @Date 2018/12/20 11:27
 * @Version 1.0
 */
@Service
public class AccessLimitService {

    /**
     * 一秒钟只能发出5个请求
     */
    RateLimiter rateLimiter;

    public AccessLimitService() {
        rateLimiter = RateLimiter.create(5);
    }

    /**
     * 尝试获取令牌
     *
     * @return
     */
    public boolean tryAcquire() {
        return rateLimiter.tryAcquire();
    }

}
