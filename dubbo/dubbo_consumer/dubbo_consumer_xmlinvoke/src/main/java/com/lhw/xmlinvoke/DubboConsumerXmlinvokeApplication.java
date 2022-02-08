package com.lhw.xmlinvoke;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath:config/consumer2.xml"})
public class DubboConsumerXmlinvokeApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboConsumerXmlinvokeApplication.class, args);
    }

}
