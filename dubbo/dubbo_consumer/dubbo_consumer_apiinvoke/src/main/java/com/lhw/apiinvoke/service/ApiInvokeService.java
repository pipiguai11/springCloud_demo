package com.lhw.apiinvoke.service;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author ：linhw
 * @date ：22.2.8 16:41
 * @description：API调用的方式，调用远程服务
 *
 *      通过ReferenceConfig对象即可实现和xml同样的服务发现功能。
 *
 *      使用这种方式可以不用写xml配置文件
 *
 * @modified By：
 */
@Service
public class ApiInvokeService {

    private static ApplicationConfig applicationConfig;

    private static RegistryConfig registryConfig;

    static {
        //配置应用信息
        applicationConfig = new ApplicationConfig();
        applicationConfig.setName("consumer");
        //配置注册中心
        registryConfig = new RegistryConfig();
        registryConfig.setAddress("zookeeper://127.0.0.1:2181");
    }

    public <T> Object invokeService(Class<T> interfaceClazz, String methodName){
        //配置需要发现的服务
        ReferenceConfig<T> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setApplication(applicationConfig);
        referenceConfig.setRegistry(registryConfig);
//        ReferenceConfig<T> referenceConfig = (ReferenceConfig<T>) ApplicationHandle.getBean(ReferenceConfig.class);
        referenceConfig.setInterface(interfaceClazz.getName());
        T service = referenceConfig.get();

        Object result = null;

        try {
            //这里的逻辑只能是调用无参的服务方法，如果需要传参则另外实现，或者在这里做扩展实现
            Method method = interfaceClazz.getDeclaredMethod(methodName);
            result = method.invoke(service);

        }catch (NoSuchMethodException e){
            System.out.println("在【" + interfaceClazz.getSimpleName() + "】服务中不存在【" + methodName + "】方法");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return result;
    }

}
