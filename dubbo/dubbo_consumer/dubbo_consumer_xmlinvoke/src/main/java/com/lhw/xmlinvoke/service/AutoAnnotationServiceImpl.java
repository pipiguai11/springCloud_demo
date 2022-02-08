package com.lhw.xmlinvoke.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lhw.dubbo_api.model.User;
import com.lhw.dubbo_api.service.AddrService;
import com.lhw.dubbo_api.service.InfoService;
import com.lhw.dubbo_api.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用于测试使用<dubbo:annotation/>自动扫描包路径下的类的@Reference注解，
 * 在此之前需要去确定一下启动的dubbo配置类是哪个，看一下它的注入方式是哪种（有扫描注入，也有手动注入，两种方式的用法不一样）
 * 此种方式对应consumer.xml配置文件
 */
@Service("autoAnnotation")
public class AutoAnnotationServiceImpl implements InfoService {

    @Reference
    UserService consumerService;

    @Reference
    AddrService addrService;

    @Override
    public List<User> getUserInfo() {
        return consumerService.userInfo();
    }

    @Override
    public void checkAddr() {
        addrService.check();
    }


}
