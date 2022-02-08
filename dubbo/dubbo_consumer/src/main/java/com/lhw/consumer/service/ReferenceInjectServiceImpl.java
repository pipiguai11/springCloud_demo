package com.lhw.consumer.service;

import com.lhw.dubbo_api.model.User;
import com.lhw.dubbo_api.service.AddrService;
import com.lhw.dubbo_api.service.InfoService;
import com.lhw.dubbo_api.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：linhw
 * @date ：22.2.7 15:41
 * @description：通过@Resource方式注入（也就是先将该服务从注册中心发现，然后注入到消费者端的spring容器中，最后再通过@Resource注解注入），对应consumer2.xml配置文件
 *      通过<dubbo:reference/>的方式手动注入容器，在注入时需要注意的是，属性名必须和配置文件中的id名对应
 * @modified By：
 */
@Service("referenceInject")
public class ReferenceInjectServiceImpl implements InfoService {

    @Resource
    UserService consumerService;

    @Resource
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
