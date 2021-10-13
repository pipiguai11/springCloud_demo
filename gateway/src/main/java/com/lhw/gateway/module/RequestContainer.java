package com.lhw.gateway.module;

/**
 * @author ：linhw
 * @date ：21.10.12 11:35
 * @description：请求信息容器
 * @modified By：
 */
public class RequestContainer {
    private static ThreadLocal<Request> local = new InheritableThreadLocal<>();
    private static Request temp = new Request();

    private RequestContainer() {
    }

    public static void set(Request request) {
        local.set(request);
    }

    public static Request get() {
        return local.get();
    }

    public static void remove() {
        local.remove();
    }

    public static void rewriteOAuthRequestContainer(ThreadLocal<Request> request) {
        local = request;
    }

    public static Request getTemp() {
        return temp;
    }

    public static void setTemp(Request request) {
        temp = request;
    }
}
