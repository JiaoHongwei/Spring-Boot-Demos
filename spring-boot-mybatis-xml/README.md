### 1.6 Spring Boot 2.x 集成 MyBatis

#### 简介

- 详细介绍如何在Spring Boot中整合MyBatis，并通过注解方式实现映射。
- 完整源码： [Spring-Boot-Demos](https://github.com/JiaoHongwei/Spring-Boot-Demos)

#### 1.6.1 创建 spring-boot-mybatis 项目

- pom文件如下

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.hw</groupId>
    <artifactId>spring-boot-mybatis</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>spring-boot-mybatis</name>
    <description>Demo project for Spring Boot MyBatis</description>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.0.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
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
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
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

这里不引入spring-boot-starter-jdbc依赖，是由于mybatis-spring-boot-starter中已经包含了此依赖

- application.yml 配置数据库连接

```yml
spring:
  datasource:
    username: root
    url: jdbc:mysql://localhost:3306/test
    password: root
    driver-class-name: com.mysql.cj.jdbc.Driver
```

- 创建user表

```sql
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
```

#### 1.6.2 使用MyBatis注解方式

因为最初设计时，MyBatis 是一个 XML 驱动的框架。配置信息是基于 XML 的，而且映射语句也是定义在 XML 中的。而到了 MyBatis 3，就有新选择了。MyBatis 3 构建在全面且强大的基于 Java 语言的配置 API 之上。这个配置 API 是基于 XML 的 MyBatis 配置的基础，也是新的基于注解配置的基础。注解提供了一种简单的方式来实现简单映射语句，而不会引入大量的开销。

##### 1.6.2.1 User实体类

```java
package com.hw.springbootmybatis.entity;

/**
 * @Description TODO
 * @Author hw
 * @Date 2018/11/19 1:10
 * @Version 1.0
 */
public class User {

    private Long id;
    private String name;
    private Integer age;

   // get set 方法
}

```

##### 1.6.2.2 创建User映射的操作UserMapper


```java
package com.hw.springbootmybatis.mapper;

import com.hw.springbootmybatis.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Description TODO
 * @Author hw
 * @Date 2018/11/19 1:14
 * @Version 1.0
 */
@Mapper
public interface UserMapper {

    @Select("SELECT * FROM USER WHERE NAME = #{name}")
    User findByName(@Param("name") String name);

    @Insert("INSERT INTO USER(NAME, AGE) VALUES(#{name}, #{age})")
    int insert(@Param("name") String name, @Param("age") Integer age);

}


```

#### 1.6.2.3 创建单元测试


```
package com.hw.springbootmybatis;

import com.hw.springbootmybatis.entity.User;
import com.hw.springbootmybatis.mapper.UserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootMybatisApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void contextLoads() {
    }


    @Test
    @Rollback
    public void findByName() throws Exception {
        userMapper.insert("AAA", 20);
        User u = userMapper.findByName("AAA");
        Assert.assertEquals(20, u.getAge().intValue());
    }

}

```
#### 1.6.2.4 源码地址

[注解方式源码](https://github.com/JiaoHongwei/Spring-Boot-Demos/tree/master/spring-boot-mybatis)

不幸的是，Java 注解的的表达力和灵活性十分有限。尽管很多时间都花在调查、设计和试验上，最强大的 MyBatis 映射并不能用注解来构建——并不是在开玩笑，的确是这样。比方说，C#属性就没有这些限制，因此 MyBatis.NET 将会比 XML 有更丰富的选择。也就是说，基于 Java 注解的配置离不开它的特性。

#### 1.6.3 使用MyBatis XML方式


##### 1.6.3.1 yml文件：

```yml

spring:
  datasource:
    username: root
    url: jdbc:mysql://localhost:3306/test?characterEncoding=utf-8&serverTimezone=GMT%2B8
    password: root
    driver-class-name: com.mysql.cj.jdbc.Driver


mybatis:
  config-location: classpath:mybatis-config.xml
  mapper-locations: classpath:mapper/*.xml
  type-aliases-package: com.hw.springbootmybatisxml.entity
```

##### 1.6.3.2 创建配置文件mybatis-config.xml

在resource目录下创建config目录并创建mybatis-config.xml文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN" "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <settings>
        <setting name="callSettersOnNulls" value="true"/>
        <setting name="cacheEnabled" value="true"/>
        <setting name="lazyLoadingEnabled" value="true"/>
        <setting name="aggressiveLazyLoading" value="true"/>
        <setting name="multipleResultSetsEnabled" value="true"/>
        <setting name="useColumnLabel" value="true"/>
        <setting name="useGeneratedKeys" value="false"/>
        <setting name="autoMappingBehavior" value="PARTIAL"/>
        <setting name="defaultExecutorType" value="SIMPLE"/>
        <setting name="mapUnderscoreToCamelCase" value="true"/>
        <setting name="localCacheScope" value="SESSION"/>
        <setting name="jdbcTypeForNull" value="NULL"/>
    </settings>

    <typeAliases>
        <typeAlias alias="Integer" type="java.lang.Integer" />
        <typeAlias alias="Long" type="java.lang.Long" />
        <typeAlias alias="HashMap" type="java.util.HashMap" />
        <typeAlias alias="LinkedHashMap" type="java.util.LinkedHashMap" />
        <typeAlias alias="ArrayList" type="java.util.ArrayList" />
        <typeAlias alias="LinkedList" type="java.util.LinkedList" />
    </typeAliases>
</configuration>

```
##### 1.6.3.3 在resource创建 mapper/userMapper.xml


```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="com.hw.springbootmybatisxml.mapper.UserMapper">
    <resultMap id="BaseResultMap" type="com.hw.springbootmybatisxml.entity.User">
        <id column="id" property="id" jdbcType="NUMERIC"/>
        <result column="name" property="name" jdbcType="VARCHAR"/>
        <result column="age" property="age" jdbcType="NUMERIC"/>
    </resultMap>

    <sql id="Base_Column_List">
        id, name, age
    </sql>

    <select id="findByName" parameterType="java.lang.String" resultMap="BaseResultMap">
        SELECT
        <include refid="Base_Column_List"/>
        FROM user
        WHERE name = #{name}
    </select>

<!--如果涉及多个类型那就必须去掉parameterType；
第二个是#{id}...及后面的参数写法也是错误的，
因为在MyBatis3中不会识别这样的参数，所以应该改成#{arg0}，{arg1}这样的形式。-->
    <insert id="insert">
       INSERT INTO
       		user
       		(name,age)
       	VALUES
       		(#{arg0}, #{arg1})
    </insert>


</mapper>
```

##### 1.6.3.4 User实体类

```java
package com.hw.springbootmybatisxml.entity;

/**
 * @Description TODO
 * @Author hw
 * @Date 2018/11/19 1:10
 * @Version 1.0
 */
public class User {

    private Long id;
    private String name;
    private Integer age;

   // get set 方法
}

```

##### 1.6.3.5 创建User映射的操作UserMapper

```java
package com.hw.springbootmybatisxml.mapper;

import com.hw.springbootmybatisxml.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description TODO
 * @Author hw
 * @Date 2018/11/19 1:36
 * @Version 1.0
 */
@Mapper
public interface UserMapper {

    User findByName(String name);

    void insert(String name,int age);

}

```

##### 1.6.3.6 创建测试类


```java
package com.hw.springbootmybatisxml;

import com.hw.springbootmybatisxml.entity.User;
import com.hw.springbootmybatisxml.mapper.UserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootMybatisXmlApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void contextLoads() {
    }


    @Test
    @Rollback
    public void findByName() throws Exception {
        userMapper.insert("ccc", 20);
        User u = userMapper.findByName("ccc");
        Assert.assertEquals(20, u.getAge().intValue());
    }

}

```

##### 1.6.3.7 源码

[xml方式源码](https://github.com/JiaoHongwei/Spring-Boot-Demos/tree/master/spring-boot-mybatis-xml)

