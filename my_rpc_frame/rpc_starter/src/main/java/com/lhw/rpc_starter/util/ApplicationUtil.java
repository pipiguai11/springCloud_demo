//package com.lhw.rpc_starter.util;
//
//import org.springframework.beans.BeansException;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @author ：linhw
// * @date ：22.2.21 10:54
// * @description：上下文应用工具类
// * @modified By：
// */
////@Configuration
//public class ApplicationUtil implements ApplicationContextAware {
//
//    public static ApplicationContext applicationContext;
//
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        ApplicationUtil.applicationContext = applicationContext;
//    }
//
//    public static ApplicationContext getApplicationContext(){
//        return ApplicationUtil.applicationContext;
//    }
//
//    public static Object getBean(String beanName){
//        return applicationContext.getBean(beanName);
//    }
//
//    public static Object getBean(Class<?> beanType){
//        return applicationContext.getBean(beanType);
//    }
//}
