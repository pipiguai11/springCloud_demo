package com.lhw.swagger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author ：linhw
 * @date ：22.2.16 09:41
 * @description：swagger配置类
 * @modified By：
 */
@Configuration
public class ApiConfig {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORIZATION_IN = "header";

    @Bean
    public Docket api(){
        /**
         * RequestHandlerSelectors.any()和PathSelectors.any()配置选择的RequestHandler的路径
         * 如果都是any表示匹配任意路径，所有的api都能显示
         */
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(Collections.singletonList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * swagger api 文档的信息，包括版本，描述、标题、责任人等
     * @return
     */
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("MY REST API")
                .description("我的swagger api文档")
                .version("1.0")
                .contact(new Contact("LHW","https://github.com/pipiguai11","953522392@qq.com"))
                .build();
    }

    /**
     * 集成JWT，添加apiKey
     * 在swagger上可以点击右上角的登录验证，然后在每次的接口调用时都会携带上token信息
     * @return
     */
    private ApiKey apiKey(){
        return new ApiKey("JWT",AUTHORIZATION_HEADER,AUTHORIZATION_IN);
    }

    /**
     * 安全策略
     * @return
     */
    private SecurityContext securityContext(){
        return SecurityContext
                .builder()
                .securityReferences(defaultAuth())
                .build();
    }

    private List<SecurityReference> defaultAuth(){
        SecurityReference securityReference =
                new SecurityReference("JWT",
                        new AuthorizationScope[]{new AuthorizationScope("global",
                                "accessEverything")});
        return new ArrayList<SecurityReference>(){{
           add(securityReference);
        }};
    }

}
