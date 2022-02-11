package com.lhw.dubbo_api.service;

import java.util.List;
import java.util.Map;

/**
 * @author ：linhw
 * @date ：22.2.7 15:01
 * @description：
 * @modified By：
 */
public interface AddrService {

    void check();

    /**
     * 获得资料预览的url全路径
     * @param code
     * @param fileName
     * @return
     */
    String getPreviewFullUrl(String code, String fileName);


    /**
     * 统计目录以及文件的资料
     * @return
     */
    Map<String, List<Object>> countCatalogData(Integer size);

}
