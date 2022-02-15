package com.lhw.xmlinvoke.service;

import com.lhw.dubbo_api.model.User;
import com.lhw.dubbo_api.service.AddrService;
import com.lhw.dubbo_api.service.InfoService;
import com.lhw.dubbo_api.service.UserService;
import com.lhw.dubbo_api.utils.RpcContextUtil;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 用于测试使用<dubbo:annotation/>自动扫描包路径下的类的@Reference注解，
 * 在此之前需要去确定一下启动的dubbo配置类是哪个，看一下它的注入方式是哪种（有扫描注入，也有手动注入，两种方式的用法不一样）
 * 此种方式对应consumer.xml配置文件
 * @author linhw
 */
@Service("autoAnnotation")
public class AutoAnnotationServiceImpl extends AbstractServiceImpl implements InfoService {

    @DubboReference(timeout = 5000)
    UserService consumerService;

    @DubboReference
    AddrService addrService;

    @PostConstruct
    public void initService(){
        registryService(consumerService);
        registryService(addrService);
    }

    @Override
    public List<User> getUserInfo() {
        return consumerService.userInfo();
    }

    @Override
    public void checkAddr() {
        addrService.check();
    }

    @Override
    public void showRpcContextMessage(){
        System.out.println("---------------------- consumer start --------------------------");
//        RpcContextUtil.showRpcContextMessage();

        RpcContext.getServiceContext().setAttachment("index","1");
        RpcContext.getServiceContext().setAttachment("name","lhw");
        consumerService.showRpcContextInfo();
//        RpcContextUtil.showRpcContextMessage();

        System.out.println("---------------------- consumer end --------------------------");
    }

}
