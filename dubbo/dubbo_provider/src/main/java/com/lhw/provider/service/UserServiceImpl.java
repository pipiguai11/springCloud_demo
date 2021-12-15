package com.lhw.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.lhw.dubbo_api.model.User;
import com.lhw.dubbo_api.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    public UserServiceImpl() {
        System.out.println("userServiceImple类加载了");
    }

    @Override
    public List<User> userInfo() {
        System.out.println("UserServiceImpl 类被调用了");
        User u = new User("lhw", 1, 18);
        User u2 = new User("hw", 2, 19);
        User u3 = new User("w", 3, 20);
        List<User> userList = new ArrayList<>();
        userList.add(u);
        userList.add(u2);
        userList.add(u3);
        return userList;
    }
}
