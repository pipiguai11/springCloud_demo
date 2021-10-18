package com.lhw.breaker;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * @author ：linhw
 * @date ：21.10.18 10:52
 * @description：
 * @modified By：
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
public class CircuitbreakerTest {

    // 测试的总次数
    private static int i=0;

    @Autowired
    private WebTestClient webClient;

    @Test
    @RepeatedTest(30)
    void testHelloPredicates() throws InterruptedException {
        // 低于15次时，gen在0和1之间切换，也就是一次正常一次超时，
        // 超过15次时，gen固定为0，此时每个请求都不会超时
        int gen = (i<15) ? (i % 2) : 0;

        // 次数加一
        i++;

        final String tag = "[" + i + "]";

        // 发起web请求
        webClient.get()
                .uri("account/" + gen)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectBody(String.class)
                .consumeWith(result  ->
                        System.out.println(tag + result.getRawStatusCode() + " - " + result.getResponseBody()));

        Thread.sleep(1000);
    }
}