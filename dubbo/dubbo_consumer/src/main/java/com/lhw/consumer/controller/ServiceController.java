package com.lhw.consumer.controller;

import com.lhw.dubbo_api.model.User;
import com.lhw.dubbo_api.service.InfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ServiceController {

    private static Logger log = LoggerFactory.getLogger(ServiceController.class);

    @Autowired
    InfoService infoServciceImpl;

    @RequestMapping("/test")
    public List<User> test() throws ClassNotFoundException {
        return infoServciceImpl.getUserInfo();
    }

    @RequestMapping("/test2")
    public String test2(){
        log.error("提示错误");
        return "success";
    }

}
