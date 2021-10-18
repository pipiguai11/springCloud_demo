package com.lhw.gatewaybase.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

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

    /**
     * 用于测试断路器，提供给gateway-breaker访问的
     *      请求进来，当id=1的时候，线程睡眠500毫秒，而我在断路器中设置了超时限制为200毫秒，也就是说这个请求必定是超时的。
     *      因此它会返回一个异常，判定这个请求失败了
     *      当请求达到一定的次数，触发断路器，后续几次请求不会再访问到这个接口
     *      等到时间窗口结束，则重新计算
     * @param id
     * @return
     * @throws InterruptedException
     */
    @RequestMapping(value = "/account/{id}", method = RequestMethod.GET)
    public String account(@PathVariable("id") int id) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + "---- id：【" + id + "】");
        if(1==id) {
            Thread.sleep(500);
        }

        return new Date().toString();
    }

}
