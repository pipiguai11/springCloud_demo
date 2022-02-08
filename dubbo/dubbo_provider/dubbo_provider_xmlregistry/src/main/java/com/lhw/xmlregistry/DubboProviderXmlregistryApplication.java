package com.lhw.xmlregistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath:config/provider2.xml"})
public class DubboProviderXmlregistryApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboProviderXmlregistryApplication.class, args);
    }

}
