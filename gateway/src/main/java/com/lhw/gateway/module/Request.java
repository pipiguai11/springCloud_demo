package com.lhw.gateway.module;

import lombok.ToString;

import java.util.Map;
import java.util.Set;

/**
 * @author ：linhw
 * @date ：21.10.12 11:26
 * @description：自定义业务请求体
 * @modified By：
 */
@ToString
public class Request {
    /**
     * 请求参数
     */
    private Map<String, String> parameters;
    /**
     * 请求头
     */
    private Map<String, String> headers;
    /**
     * 请求方式：POST、GET、PUT、DELETE
     */
    private String method;
    /**
     * 请求全路径
     */
    private String requestURL;
    /**
     * 请求路径
     */
    private String requestURI;
    /**
     * 请求地址参数
     */
    private String queryString;
    /**
     * 请求来源地址
     */
    private String remoteHost;

//    /**
//     * 请求认证信息
//     */
//    private Authentication authentication;
//
//    /**
//     * 请求客户端信息
//     */
//    private ClientDetails requestClientDetails;

    public Request() {
    }

    public Request(Map<String, String> parameters, Map<String, String> headers, String method, String requestURL, String requestURI, String queryString, String remoteHost) {
        this.parameters = parameters;
        this.headers = headers;
        this.method = method;
        this.requestURL = requestURL;
        this.requestURI = requestURI;
        this.queryString = queryString;
        this.remoteHost = remoteHost;
    }

    public Request(Map<String, String> parameters, Map<String, String> headers, String method, String requestURL, String requestURI, String queryString, String remoteHost, Object authentication) {
        this.parameters = parameters;
        this.headers = headers;
        this.method = method;
        this.requestURL = requestURL;
        this.requestURI = requestURI;
        this.queryString = queryString;
        this.remoteHost = remoteHost;
//        this.authentication = authentication;
    }


    /**
     * 获取请求参数
     *
     * @param name 参数名
     * @return 请求参数
     */
    public String getParameter(String name) {
        return parameters.get(name);
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public Request setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
        return this;
    }

    /**
     * 获取请求头
     *
     * @param name 参数名
     * @return 请求头信息
     */
    public String getHeader(String name) {
        return headers.get(name);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Request setHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public String getMethod() {
        return method;
    }

    public Request setMethod(String method) {
        this.method = method;
        return this;
    }

    public String getRequestURL() {
        return requestURL;
    }

    public Request setRequestURL(String requestURL) {
        this.requestURL = requestURL;
        return this;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public Request setRequestURI(String requestURI) {
        this.requestURI = requestURI;
        return this;
    }

    public String getQueryString() {
        return queryString;
    }

    public Request setQueryString(String queryString) {
        this.queryString = queryString;
        return this;
    }

    public String getRemoteHost() {
        return remoteHost;
    }

    public Request setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
        return this;
    }

//    public Authentication getAuthentication() {
//        return authentication;
//    }
//
//    public Request setAuthentication(Authentication authentication) {
//        this.authentication = authentication;
//        return this;
//    }
//
//    /**
//     * 该请求是否为认证过的请求
//     *
//     * @return 返回true表示请求认证过，返回false表示未认证
//     */
//    public boolean isAuthenticated() {
//        return authentication != null;
//    }


    public Request narrowScope(Set<String> scope) {
        this.parameters.put("scope", String.join(",", scope.toArray(new String[]{})));
        return this;
    }
}