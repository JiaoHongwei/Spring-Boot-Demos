### 1.9 Spring Boot 2.x 集成 Thymeleaf

- 完整源码： [Spring-Boot-Demos](https://github.com/JiaoHongwei/Spring-Boot-Demos)

#### 1.9.1 在pom中引入依赖


```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
        <!--用来非严格的Html格式-->
        <!-- https://mvnrepository.com/artifact/net.sourceforge.nekohtml/nekohtml -->
        <dependency>
            <groupId>net.sourceforge.nekohtml</groupId>
            <artifactId>nekohtml</artifactId>
            <version>${nekohtml.version}</version>
        </dependency>

```

#### 1.9.2 yml文件中配置


```yml
spring:
  thymeleaf:
    cache: false # 开发时关闭缓存,不然没法看到实时页面
    mode: LEGACYHTML5 # 用非严格的 HTML
    encoding: UTF-8
    servlet:
      content-type: text/html

```

#### 1.9.3 创建HelloController


```java

package com.hw.springbootthymeleaf.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Description TODO
 * @Author hw
 * @Date 2018/11/21 18:07
 * @Version 1.0
 */
@Controller
public class HelloController {

    @GetMapping("index/{data}")
    public String index(Model model, @PathVariable String data) {
        model.addAttribute("key", data);
        return "index";
    }
}

```

#### 1.9.4 添加模板文件

- thymeleaf模板文件默认在 resource/templates 目录下
- 记得每个文件都要添加 `xmlns:th="http://www.thymeleaf.org"` 声明才能使用thymeleaf语法


```html
<!DOCTYPE html SYSTEM "http://www.thymeleaf.org/dtd/xhtml1-strict-thymeleaf-spring4-4.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Hello Thymeleaf</title>
</head>
<body>
<div>
    <span>访问 Model：</span><span th:text="${key}"></span>
</div>
</body>
</html>
```
