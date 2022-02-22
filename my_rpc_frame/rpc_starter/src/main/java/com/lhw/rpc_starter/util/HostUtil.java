package com.lhw.rpc_starter.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author ：linhw
 * @date ：22.2.18 10:54
 * @description：本机ip地址工具类
 * @modified By：
 */
public class HostUtil {

    public static String getLocalHost() {
        String host = "";
        try {
            host = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return host;
    }

}
