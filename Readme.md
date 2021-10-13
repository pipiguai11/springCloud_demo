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

5、网关和服务之间的关系
    打个比方，在一个大房子中，假设房间就是一个个的服务【存在很多个房间】，那网关就是这个大房子的大门
    我们想要进入某个房间的时候，就需要先进入大门并告诉大门我们想要去哪个房间，然后大门会把去那个房间的路线给到我们，我们再走过去。
    
   也可以理解成网关就是一个外围服务，而各个应用程序就是内层服务，他们之间维护的上下文是不同的，请求体和响应体都不是相同的。
   
6、断路器实现【待实现】
    原先是Hystrix实现的，Hystrix是Netflix实现的断路器模式工具包，不过在springcloud 2020.0.x版本之后，推荐使用resilience4j去替代它了
    需要额外引入依赖resilience4j
    resilience4j的官方文档参考 [https://cloud.spring.io/spring-cloud-circuitbreaker/reference/html/spring-cloud-circuitbreaker.html]
    
7、速率限制过滤器实现【待实现】
    通过redis基于令牌桶算法的方式实现
    需要引入spring-boot-starter-data-redis-reactive依赖
    
8、gateway的配置可以做到代码零入侵
    只需要引入依赖然后在配置文件中添加相应的配置即可
    如果需要更加细致的定制化配置，可以自定义配置类，或者过滤器实现所有功能。

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