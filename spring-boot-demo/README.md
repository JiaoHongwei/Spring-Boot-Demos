[GitHub](https://github.com/spring-projects/spring-boot)

[官方文档](https://docs.spring.io/spring-boot/docs/2.1.0.RELEASE/reference/htmlsingle/)

---

### 1.1 Spring Boot 是什么？

- Spring Boot（Boot顾名思义，是引导的意思） 框架是用于简化Spring应用从搭建到开发的过程。
- 应用开箱即用，只要通过一个指令，包括命令行 `java -jar` 、SpringApplication 应用启动类 、 Spring Boot Maven 插件等，就可以启动应用了。
- 另外，Spring Boot 强调只需要很少的配置文件，所以在开发生产级 Spring 应用中，让开发变得更加高效和简易。目前，Spring Boot 版本是 2.x 版本。

#### 1.1.1 Spring Boot 2.x 特性

1. SpringApplication 应用类；
2. 自动配置；
3. 外化配置；
4. 内嵌容器；
5. Starter 组件；
6. 还有对日志、Web、消息、测试及扩展等支持。

#### 1.1.2 Spring Boot 2.x Starter 组件

> Spring Boot 官方提供了很多 Starter 组件，涉及 Web、模板引擎、SQL 、NoSQL、缓存、验证、日志、测试、内嵌容器，还提供了事务、消息、安全、监控、大数据等支持。

- Web   ：Spring Web、Spring WebFlux 等；
- 模板引擎：Thymeleaf、FreeMarker、Groovy、Mustache 等；
- SQL：MySQL 、H2 等；
- NoSQL：Redis、MongoDB、Cassandra、Elasticsearch 等；
- 验证框架：Hibernate Validator、Spring Validator 等；
- 日志框架：Log4j2、Logback 等；
- 测试：JUnit、Spring Boot Test、AssertJ、Mockito 等；
- 内嵌容器：Tomcat、Jetty、Undertow 等。

#### 1.1.3 Spring Boot 应用场景

- Spring Boot 模块众多，代表着应用场景也非常广泛，包括 Web 应用、SOA 及微服务等。在 Web 应用中，Spring Boot 封装了 Spring MVC 即可以提供 MVC 模式开发传统的 Web，又可以开发 REST API ，来开发 Web、APP、Open API 各种应用。
- 在 SOA 及微服务中，用 Spring Boot 可以包装每个服务，本身可以提供轻量级 REST API 服务接口。也可以整合流行的 RPC 框架（Dubbo 等），提供 RPC 服务接口，只要简单地加入对应的 Starter 组件即可。
- 在微服务实战中，推荐使用 Spring Cloud，是一套基于 Spring Boot 实现分布式系统的工具，适用于构建微服务。