package com.lhw.swagger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author ：linhw
 * @date ：22.2.16 09:41
 * @description：swagger配置类
 * @modified By：
 */
@Configuration
public class ApiConfig {

    @Bean
    public Docket api(){
        /**
         * RequestHandlerSelectors.any()和PathSelectors.any()配置选择的RequestHandler的路径
         * 如果都是any表示匹配任意路径，所有的api都能显示
         */
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("MY REST API")
                .description("我的swagger api文档")
                .version("1.0")
                .contact(new Contact("LHW","https://github.com/pipiguai11","953522392@qq.com"))
                .build();
    }

}
