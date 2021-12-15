package com.lhw.consumer.service;

import com.lhw.dubbo_api.model.User;
import com.lhw.dubbo_api.service.InfoService;
import com.lhw.dubbo_api.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class InfoServiceImpl implements InfoService {

    @Resource
    UserService consumerService;

    @Override
    public List<User> getUserInfo() {
        return consumerService.userInfo();
    }
}
