package com.lhw.xmlinvoke.controller;

import com.lhw.dubbo_api.model.User;
import com.lhw.dubbo_api.service.InfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ServiceController {

    private static Logger log = LoggerFactory.getLogger(ServiceController.class);

    @Autowired
    @Qualifier("autoAnnotation")
    InfoService autoAnnotation;

    @Autowired
    @Qualifier("referenceInject")
    InfoService referenceInject;

    @RequestMapping("/test")
    public List<User> test() throws ClassNotFoundException {
        return autoAnnotation.getUserInfo();
    }

    @RequestMapping("/addr")
    public String addr(){
        autoAnnotation.checkAddr();
        return "success";
    }

    @RequestMapping("/test2")
    public List<User> test2() throws ClassNotFoundException {
        return referenceInject.getUserInfo();
    }

    @RequestMapping("/addr2")
    public String addr2(){
        referenceInject.checkAddr();
        return "success";
    }

    /**
     * 回声测试
     * @return
     */
    @RequestMapping("echo/test")
    public String echoTest(){
        System.out.println("autoAnnotation start");
        autoAnnotation.echoTest();

        System.out.println();
        System.out.println("referenceInject start");
        referenceInject.echoTest();
        return "success";
    }

    @RequestMapping("/rpcContext")
    public String rpcContext(){
        autoAnnotation.showRpcContextMessage();
        return "success";
    }

}
