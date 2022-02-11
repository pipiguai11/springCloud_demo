package com.lhw.xmlregistry.service;

//import com.alibaba.dubbo.config.annotation.Service;
import com.lhw.dubbo_api.service.AddrService;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;
import java.util.Map;

/**
 * @author ：linhw
 * @date ：22.2.7 15:02
 * @description：
 * @modified By：
 */
//@Service
@DubboService
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

    @Override
    public String getPreviewFullUrl(String code, String fileName) {
        return null;
    }

    @Override
    public Map<String, List<Object>> countCatalogData(Integer size) {
        return null;
    }
}
