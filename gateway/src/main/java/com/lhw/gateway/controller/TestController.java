package com.lhw.gateway.controller;

import org.springframework.web.bind.annotation.*;

/**
 * @author ：linhw
 * @date ：21.10.8 16:25
 * @description：控制层
 * @modified By：
 */
@RestController
public class TestController {

    @RequestMapping(value = "/get" , method = RequestMethod.GET)
    public String get(){
        return "success";
    }

    @RequestMapping(value = "/filter" , method = RequestMethod.GET)
    public String filter(){
        return "filter";
    }

}
