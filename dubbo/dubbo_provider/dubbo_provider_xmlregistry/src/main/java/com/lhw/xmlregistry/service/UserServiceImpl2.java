package com.lhw.xmlregistry.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.lhw.dubbo_api.model.User;
import com.lhw.dubbo_api.service.UserService;

import java.util.LinkedList;
import java.util.List;

@Service
public class UserServiceImpl2 implements UserService {
    @Override
    public List<User> userInfo() {
        System.out.println("UserServiceImpl2 类被调用了");
        List<User> users = new LinkedList<>();
        User u = new User("xxx", 3, 12);
        users.add(u);
        return users;
    }
}
