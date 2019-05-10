### 1.10 Spring Boot 2.x 集成 Log4j2

- 完整源码： [Spring-Boot-Demos](https://github.com/JiaoHongwei/Spring-Boot-Demos)
- 日志系统用的是Spring Boot默认的LogBack。事实上，除了使用默认的LogBack，Spring Boot还可以使用Log4j、Log42等作为自己的日志系统。

#### 概览

1. 混合 sync/async
2. 彩色日志
3. 分类输出到不同文件
4. 自动压缩日志文件并归档

#### 1.10.1 添加maven依赖

- Spring Boot默认使用LogBack，但是我们没有看到显示依赖的jar包，其实是因为所在的jar包spring-boot-starter-logging都是作为spring-boot-starter-web或者spring-boot-starter依赖的一部分。
- 如果这里要使用Log4j2，需要从spring-boot-starter-web中去掉spring-boot-starter-logging依赖，同时显示声明使用Log4j2的依赖jar包，具体如下

```xml
<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <!--排除掉spring boot 默认使用的依赖-->
            <exclusions>
                <exclusion>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-logging</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <!-- 日志 Log4j2 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-log4j2</artifactId>
        </dependency>
        <!-- Log4j2 异步支持 -->
        <dependency>
            <groupId>com.lmax</groupId>
            <artifactId>disruptor</artifactId>
            <version>${disruptor.version}</version>
        </dependency>
```
#### 1.10.2 使用


```
    private Logger logger = LogManager.getLogger(XssFilter.class);

```
