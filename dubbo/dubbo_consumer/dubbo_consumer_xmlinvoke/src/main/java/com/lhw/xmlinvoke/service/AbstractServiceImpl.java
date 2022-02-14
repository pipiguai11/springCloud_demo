package com.lhw.xmlinvoke.service;

import com.lhw.dubbo_api.service.InfoService;
import com.lhw.dubbo_api.utils.EchoUtil;
import org.apache.dubbo.rpc.service.EchoService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：linhw
 * @date ：22.2.14 13:39
 * @description：抽象实现类
 * @modified By：
 */
public abstract class AbstractServiceImpl implements InfoService {

    private List<Object> services = new ArrayList<>();

    public void registryService(Object service){
        if (!services.contains(service)){
            System.out.println("注册了一个服务【" + service.getClass().getSimpleName() + "】");
            services.add(service);
        }
    }

    @Override
    public void echoTest(){
        services.forEach(s -> {
            String status = EchoUtil.echoTest((EchoService)s);
            if (!"OK".equals(status)){
                System.out.println(s.getClass().getSimpleName() + " is not OK，当前是不可用状态");
            }
            System.out.println(s.getClass().getSimpleName() + " is OK");
        });
    }

}
