## gateway

### 项目架构
springboot 2.4.5 + spring cloud 2020.0.3 + nacos 2021.1

### 注意事项
1、在使用时，需要注意的是必须把spring webMvc这个依赖去除掉，因为gateway中默认使用的spring webFlux + netty架构，这两者是冲突的

2、必须选择版本对应的springboot 和spring cloud，否则很容易出现问题
![](https://note.youdao.com/yws/public/resource/5625e7f46bd2867ce16b1afc7e4d095a/xmlnote/D81C1388EF0C4E1CA353C463B2A241CA/37582)

3、在使用lb做路由（routes）的uri时，必须保证它的服务名不包含下划线，因为它的匹配正则如下：
"[a-zA-Z]([a-zA-Z]|\\d|\\+|\\.|-)*:.*"
其中就不包含下划线，还有必须是字母的组合，只有满足他这个正则表达式才能够正常的调用到服务

4、同样在使用lb做路由的uri时，必须引入spring-cloud-starter-loadbalancer(不需要额外配置，只需引入即可)，通过这个去做负载均衡调用，否则调用服务时会报403错误
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-loadbalancer</artifactId>
    <version>${spring.cloud.loadbalancer}</version>
</dependency>
这个loadbalancer其实是Ribbon的一个替代品，因为在springcloud 2020.0.x版本中，直接将Netflix给砍掉了，与此同时提供了很多的替代品，如下图
![](https://note.youdao.com/yws/public/resource/5625e7f46bd2867ce16b1afc7e4d095a/xmlnote/06BBC5EBF7EB4CE0B710FBB57B9BBF8B/37594)

## nacos
### 项目架构
springboot 2.4.5 + nacos 2021.1

### 注意事项
1、因为spring-cloud-dependencies 2020.0.0之后默认不会加载bootstrap文件了。因此需要手动引入一个依赖，去加载bootstrap配置文件
<dependency>
     <groupId>org.springframework.cloud</groupId>
     <artifactId>spring-cloud-starter-bootstrap</artifactId>
     <version>${spring.cloud.bootstrap}</version>
 </dependency>
 

2、使用这个版本的时候，可能会出现控制台一直刷nacos配置文件的内容，这个时候可以看到com.alibaba.nacos.client.config.impl下的ClientWorker等类
    只需要配置他们的日志输出级别即可。
logging:
  level:
    com:
      alibaba:
        nacos:
          client:
            config:
              impl: WARN
              
3、nacos启动时如果需要把服务注册到注册中心中去，那就需要配置服务名（${spring.cloud.nacos.discovery.service:${spring.application.name:}}）
    spring:
      application:
        name: nacos-demo
    
  匹配的顺序是，先找spring.cloud.nacos.discovery.service，如果没有配置，则再找spring.application.name，如果还没配置，则默认是""