package com.lhw.nacos.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author ：linhw
 * @date ：21.7.16 14:20
 * @description：上下文工具
 * @modified By：
 */
public class ApplicationUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationUtil.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext(){
        return ApplicationUtil.applicationContext;
    }

}
