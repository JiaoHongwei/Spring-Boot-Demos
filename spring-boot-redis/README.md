### 1.7 Spring Boot 2.x 集成 Redis

#### 简介

- 继续上篇的MyBatis操作，详细介绍在Spring Boot中使用RedisCacheManager作为缓存管理器，集成业务于一体。
- 完整源码： [Spring-Boot-Demos](https://github.com/JiaoHongwei/Spring-Boot-Demos)

#### 1.7.1 创建 spring-boot-redis 项目

- pom.xml文件如下：


```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.hw</groupId>
    <artifactId>spring-boot-redis</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>spring-boot-redis</name>
    <description>Demo project for Spring Boot Redis</description>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.0.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>

    <properties>
        <commons.pool2.version>2.5.0</commons.pool2.version>
        <fastjson.version>1.2.37</fastjson.version>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
            <!-- 1.5的版本默认采用的连接池技术是jedis  2.0以上版本默认连接池是lettuce, 在这里采用jedis，所以需要排除lettuce的jar -->
            <exclusions>
                <exclusion>
                    <groupId>redis.clients</groupId>
                    <artifactId>jedis</artifactId>
                </exclusion>
                <exclusion>
                    <groupId>io.lettuce</groupId>
                    <artifactId>lettuce-core</artifactId>
                </exclusion>
            </exclusions>

        </dependency>

        <!-- 添加jedis客户端 -->
        <dependency>
            <groupId>redis.clients</groupId>
            <artifactId>jedis</artifactId>
        </dependency>
        <!--spring2.1集成redis所需common-pool2-->
        <!-- 必须加上，jedis依赖此  -->
        <!-- spring boot 2.1 的操作手册有标注 大家可以去看看 地址是：https://docs.spring.io/spring-boot/docs/2.1.0.RELEASE/reference/htmlsingle/#boot-features-connecting-to-redis -->
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-pool2</artifactId>
            <version>${commons.pool2.version}</version>
        </dependency>

        <!--作为redis对象序列化-->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>${fastjson.version}</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <!--连接数据从操作-->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>1.3.2</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>


    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>


</project>
```
-   application.yml 文件如下：


```yml
spring:
  datasource:
    username: root
    url: jdbc:mysql://localhost:3306/test?characterEncoding=utf-8&serverTimezone=GMT%2B8
    password: root
    driver-class-name: com.mysql.cj.jdbc.Driver

  redis:
    #Redis数据库索引（默认为0）
    database: 0
    # Redis服务器地址
    host: 127.0.0.1
    # Redis服务器连接端口默认6379
    port: 6379
    # Redis服务器连接密码（默认为空）
    password:
    # 连接超时时间（毫秒） 用jedis pool后，设置timeout就没有意义了
    timeout: 2000ms

    jedis:
      pool:
        # 连接池最大连接数（使用负值表示没有限制）
        max-active: 8
        # 连接池最大阻塞等待时间（使用负值表示没有限制）
        max-wait: -1ms
        # 连接池中的最大空闲连接
        max-idle: 8
        # 连接池中的最小空闲连接
        min-idle: 0
#    lettuce 和 jedis一样
#    lettuce:
#      pool:
#        max-active: 8
#        max-wait: -1ms
#        max-idle: 8
#        min-idle: 0

# 打开mybatis sql 日志
logging:
  level:
    com:
      hw:
        springbootredis:
          mapper: debug

```

#### 1.7.2 添加Redis配置文件RedisConfiguration.java

- 注意：@EnableCaching会为每个bean中被 @Cacheable, @CachePut and @CacheEvict修饰的public方法进行缓存操作。

```java
package com.hw.springbootredis.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @Description 在RedisConfig类里定义RedisTemplate的bean和Redis的CacheManager
 * @Author hw
 * @Date 2018/11/20 11:56
 * @Version 1.0
 */
@EnableCaching
@Configuration
@ConditionalOnClass(RedisOperations.class)
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfiguration extends CachingConfigurerSupport {


    @Bean(name = "redisTemplate")
    @SuppressWarnings("unchecked")
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();

        //使用fastjson序列化
        FastJsonRedisSerializer fastJsonRedisSerializer = new FastJsonRedisSerializer(Object.class);
        // value值的序列化采用fastJsonRedisSerializer
        template.setValueSerializer(fastJsonRedisSerializer);
        template.setHashValueSerializer(fastJsonRedisSerializer);
        // key的序列化采用StringRedisSerializer
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean
    @ConditionalOnMissingBean(StringRedisTemplate.class)
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    //缓存管理器
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager
                .RedisCacheManagerBuilder
                .fromConnectionFactory(redisConnectionFactory);
        return builder.build();
    }
}

```

#### 1.7.3 添加Redis 操作工具类RedisCacheUtil.java


```java
package com.hw.springbootredis.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Description TODO
 * @Author hw
 * @Date 2018/11/20 11:58
 * @Version 1.0
 */
@Component
@SuppressWarnings("unchecked")
public class RedisCacheUtil<T> {

    @Autowired
    public RedisTemplate redisTemplate;


    /**
     * 使用Redis incrde 原子性递增，来解决这种高并发的秒杀或者分布式序列号生成等场景。鉴于本场景我们只用他来做计数实现间隔时间内只接收一次请求。
     */
    public long getIncrement(String redisKey) {
        return redisTemplate.opsForValue().increment(redisKey, 1);
    }

    public Boolean expire(String redisKey, long timeout, TimeUnit unit) {
        return redisTemplate.expire(redisKey, timeout, unit);
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key   缓存的键值
     * @param value 缓存的值
     * @return 缓存的对象
     */
    public <T> ValueOperations<String, T> setCacheObject(String key, T value) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        operation.set(key, value);
        return operation;
    }

    public <T> ValueOperations<String, T> setCacheObject(String key, T value, Integer timeout, TimeUnit timeUnit) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        operation.set(key, value, timeout, timeUnit);
        return operation;
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public <T> T getCacheObject(String key) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * 删除单个对象
     */
    public void deleteObject(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 删除集合对象
     */
    public void deleteObject(Collection collection) {
        redisTemplate.delete(collection);
    }

    /**
     * 缓存List数据
     *
     * @param key      缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    public <T> ListOperations<String, T> setCacheList(String key, List<T> dataList) {
        ListOperations listOperation = redisTemplate.opsForList();
        if (null != dataList) {
            int size = dataList.size();
            for (int i = 0; i < size; i++) {
                listOperation.leftPush(key, dataList.get(i));
            }
        }
        return listOperation;
    }

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public <T> List<T> getCacheList(String key) {
        List<T> dataList = new ArrayList<T>();
        ListOperations<String, T> listOperation = redisTemplate.opsForList();
        Long size = listOperation.size(key);

        for (int i = 0; i < size; i++) {
            dataList.add(listOperation.index(key, i));
        }
        return dataList;
    }

    /**
     * 缓存Set
     *
     * @param key     缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    public <T> BoundSetOperations<String, T> setCacheSet(String key, Set<T> dataSet) {
        BoundSetOperations<String, T> setOperation = redisTemplate.boundSetOps(key);
        Iterator<T> it = dataSet.iterator();
        while (it.hasNext()) {
            setOperation.add(it.next());
        }
        return setOperation;
    }

    /**
     * 获得缓存的set
     */
    public Set<T> getCacheSet(String key) {
        Set<T> dataSet = new HashSet<T>();
        BoundSetOperations<String, T> operation = redisTemplate.boundSetOps(key);
        Long size = operation.size();
        for (int i = 0; i < size; i++) {
            dataSet.add(operation.pop());
        }
        return dataSet;
    }

    /**
     * 缓存Map
     */
    public <T> HashOperations<String, String, T> setCacheMap(String key, Map<String, T> dataMap) {

        HashOperations hashOperations = redisTemplate.opsForHash();
        if (null != dataMap) {
            for (Map.Entry<String, T> entry : dataMap.entrySet()) {
                hashOperations.put(key, entry.getKey(), entry.getValue());
            }
        }
        return hashOperations;
    }

    /**
     * 获得缓存的Map
     */
    public <T> Map<String, T> getCacheMap(String key) {
        Map<String, T> map = redisTemplate.opsForHash().entries(key);
        return map;
    }


    /**
     * 缓存Map
     */
    public <T> HashOperations<String, Integer, T> setCacheIntegerMap(String key, Map<Integer, T> dataMap) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        if (null != dataMap) {
            for (Map.Entry<Integer, T> entry : dataMap.entrySet()) {
                hashOperations.put(key, entry.getKey(), entry.getValue());
            }
        }
        return hashOperations;
    }

    /**
     * 获得缓存的Map
     */
    public <T> Map<Integer, T> getCacheIntegerMap(String key) {
        Map<Integer, T> map = redisTemplate.opsForHash().entries(key);
        return map;
    }
}

```

#### 1.7.4 使用

- 创建 HelloController.java


```
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


```

- RedisTemplate使用的是 JdkSerializationRedisSerializer
- StringRedisTemplate使用的是 StringRedisSerializer
- 建议直接选择RedisTemplate，RedisCacheUtil 中也是使用的RedisTemplate进行封装。




