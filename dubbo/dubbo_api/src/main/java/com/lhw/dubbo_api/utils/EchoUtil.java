package com.lhw.dubbo_api.utils;

import org.apache.dubbo.rpc.service.EchoService;

/**
 * @author ：linhw
 * @date ：22.2.14 13:35
 * @description：回声测试工具类
 * @modified By：
 */
public class EchoUtil {

    public static String echoTest(EchoService echoService){
        return echoService.$echo("OK").toString();
    }

}
