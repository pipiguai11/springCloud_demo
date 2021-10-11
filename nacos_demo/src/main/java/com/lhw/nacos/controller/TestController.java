package com.lhw.nacos.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

/**
 * @author ：linhw
 * @date ：21.7.16 11:09
 * @description：
 * @modified By：
 */
@RestController
public class TestController {

    @NacosValue(value = "${myName}", autoRefreshed = true)
    private String name;

    @Autowired
    private ApplicationContext applicationContext;

    @RequestMapping(value = "/test" ,method = RequestMethod.GET)
    @ResponseBody
    public String get(){
        return "success" + "--------------" + name;
    }

    @RequestMapping(value = "get/{value}",method = RequestMethod.GET)
    @ResponseBody
    public String getValue(@PathVariable String value){
        String result = applicationContext.getEnvironment().getProperty(value);
        System.out.println(result);
        return result;
    }

}
