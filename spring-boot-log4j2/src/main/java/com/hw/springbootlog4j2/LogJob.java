package com.hw.springbootlog4j2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description TODO
 * @Author hw
 * @Date 2018/11/22 12:18
 * @Version 1.0
 */
@Component
public class LogJob {
    private final static Logger logger = LoggerFactory.getLogger(LogJob .class);

    /**
     * 2秒钟执行1次
     */
    @Scheduled(fixedRate = 2 * 1000)
    public void logging(){
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        logger.info(simpleDateFormat.format(now));
        logger.debug("-------DEBUG---------");
        logger.error(now.getTime()+"");
    }

}
