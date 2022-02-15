package com.lhw.xmlregistry.service;

//import com.alibaba.dubbo.config.annotation.Service;
import com.lhw.dubbo_api.model.User;
import com.lhw.dubbo_api.service.RpcContextService;
import com.lhw.dubbo_api.service.UserService;
import com.lhw.dubbo_api.utils.RpcContextUtil;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;

import java.util.LinkedList;
import java.util.List;

//@Service
@DubboService
public class UserServiceImpl2 implements UserService {

    @DubboReference(timeout = 5000)
    private RpcContextService rpcContextService;

    @Override
    public List<User> userInfo() {
        System.out.println("UserServiceImpl2 类被调用了");
        List<User> users = new LinkedList<>();
        User u = new User("xxx", 3, 12);
        users.add(u);
        return users;
    }

    @Override
    public void showRpcContextInfo() {
        System.out.println("-------------------- UserServiceImpl start ---------------------");
        //首先被远程调用后，先输出一下RpcContext的内容
        RpcContextUtil.showRpcContextMessage();
        System.out.println("从RpcContext中取出来的隐式参数 index： " + RpcContext.getServiceContext().getAttachment("index"));
        System.out.println("从RpcContext中取出来的隐式参数 name： " + RpcContext.getServiceContext().getAttachment("name"));

        System.out.println();
        //如果这里不赋值，则会把从消费者那里传过来的隐式参数继续作为本地调用的隐式参数传递下去（即name=lhw，index=1）
//        RpcContext.getServiceContext().setAttachment("name","hwhwhwhw");
//        RpcContext.getServiceContext().setAttachment("index","2");
        //然后内部发起一个远程调用，然后再次输出RpcContext的内容
        rpcContextService.invoke();
//        RpcContextUtil.showRpcContextMessage();

        System.out.println("-------------------- UserServiceImpl end ---------------------");
    }


}
