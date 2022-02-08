package com.lhw.xmlregistry.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.lhw.dubbo_api.service.AddrService;

/**
 * @author ：linhw
 * @date ：22.2.7 15:02
 * @description：
 * @modified By：
 */
@Service
public class AddrServiceImpl implements AddrService {

    public AddrServiceImpl(){
        System.out.println("AddrServiceImpl 类被加载了");
    }

    @Override
    public void check() {
        try {
            Thread.sleep(2000);
        }catch (Exception e){

        }
        System.out.println("调用了AddrServiceImpl类的check方法");
    }
}
