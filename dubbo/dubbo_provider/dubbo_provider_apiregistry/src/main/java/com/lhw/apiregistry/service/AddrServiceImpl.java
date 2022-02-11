package com.lhw.apiregistry.service;

import com.lhw.dubbo_api.service.AddrService;

import java.util.List;
import java.util.Map;

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

    @Override
    public String getPreviewFullUrl(String code, String fileName) {
        return null;
    }

    @Override
    public Map<String, List<Object>> countCatalogData(Integer size) {
        return null;
    }
}
