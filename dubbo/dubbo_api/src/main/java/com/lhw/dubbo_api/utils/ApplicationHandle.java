package com.lhw.dubbo_api.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author ：linhw
 * @date ：22.2.8 17:19
 * @description：
 * @modified By：
 */
public class ApplicationHandle implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationHandle.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplication(){
        return ApplicationHandle.applicationContext;
    }

    public static Object getBean(Class<?> clazz){
        return ApplicationHandle.applicationContext.getBean(clazz);
    }

}
