package com.lhw.gatewaybase.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：linhw
 * @date ：21.10.8 16:25
 * @description：控制层
 * @modified By：
 */
@RestController
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String get() {
        return "success";
    }

    @RequestMapping(value = "/filter", method = RequestMethod.GET)
    public String filter() {
        return "filter";
    }

    /**
     * 用来测试通过全局过滤器获取到请求头参数，并将其存放到系统对象中
     *      测试时需要注意：只有通过网关才会触发到全局过滤器，如果直接调用这个方法是不会触发全局过滤器的
     * @return
     */
//    @RequestMapping(value = "get/header", method = RequestMethod.GET)
//    public String header(){
//        final Request authRequest = RequestContainer.getTemp();
//        logger.info("请求参数信息："+ JSON.toJSONString(authRequest));
//        // 清理容器，防止内存泄漏
//        RequestContainer.remove();
//        return "success";
//    }

}
