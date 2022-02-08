package com.lhw.consumer.config;

import com.lhw.dubbo_api.utils.ApplicationHandle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：linhw
 * @date ：22.2.8 17:24
 * @description：
 * @modified By：
 */
@Configuration
public class ApplicationConfig {

    @Bean
    public ApplicationHandle initApplicationContext(){
        return new ApplicationHandle();
    }

}
