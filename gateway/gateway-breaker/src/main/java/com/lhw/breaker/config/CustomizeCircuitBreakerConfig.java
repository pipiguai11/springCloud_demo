package com.lhw.breaker.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * @author ：linhw
 * @date ：21.10.18 09:46
 * @description：自定义断路器配置
 * @modified By：
 */
@Configuration
public class CustomizeCircuitBreakerConfig {

    @Bean
    public ReactiveResilience4JCircuitBreakerFactory defaultCustomizer() {

        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.TIME_BASED) // 滑动窗口的类型为时间窗口
                .slidingWindowSize(10) // 时间窗口的大小为10秒
                .minimumNumberOfCalls(5) // 在单位时间窗口内最少需要5次调用才能开始进行统计计算
                .failureRateThreshold(50) // 在单位时间窗口内调用失败率达到50%后会启动断路器
                .enableAutomaticTransitionFromOpenToHalfOpen() // 允许断路器自动由打开状态转换为半开状态
                .permittedNumberOfCallsInHalfOpenState(5) // 在半开状态下允许进行正常调用的次数
                .waitDurationInOpenState(Duration.ofSeconds(5)) // 断路器打开状态转换为半开状态需要等待5秒【默认60秒】
                .recordExceptions(Throwable.class) // 所有异常都当作失败来处理
                .build();

        ReactiveResilience4JCircuitBreakerFactory factory = new ReactiveResilience4JCircuitBreakerFactory();
        factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .timeLimiterConfig(TimeLimiterConfig
                        .custom()
                        .timeoutDuration(Duration.ofMillis(200))  //设置200毫秒超时限制
                        .build())
                .circuitBreakerConfig(circuitBreakerConfig).build());

        return factory;
    }
}