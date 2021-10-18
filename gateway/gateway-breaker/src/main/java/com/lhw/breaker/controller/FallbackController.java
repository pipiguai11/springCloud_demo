package com.lhw.breaker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ：linhw
 * @date ：21.10.18 11:24
 * @description：请求失败后转发控制层
 * @modified By：
 */
@RestController
public class FallbackController {

    @GetMapping("/myFallback")
    public String fallback(){
        return "fallback ----" + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

}
