package com.lhw.apiregistry.service;

import com.lhw.dubbo_api.service.AddrService;

/**
 * @author ：linhw
 * @date ：22.2.7 15:02
 * @description：
 * @modified By：
 */
public class AddrServiceImpl implements AddrService {

    public AddrServiceImpl(){
        System.out.println("AddrServiceImpl 类被加载了");
    }

    @Override
    public void check() {
        System.out.println("调用了AddrServiceImpl类的check方法");
    }
}
