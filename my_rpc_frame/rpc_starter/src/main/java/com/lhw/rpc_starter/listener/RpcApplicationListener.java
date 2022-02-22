package com.lhw.rpc_starter.listener;

import com.lhw.rpc_starter.annotation.RpcReference;
import com.lhw.rpc_starter.annotation.RpcService;
import com.lhw.rpc_starter.model.Service;
import com.lhw.rpc_starter.proxy.InvokeProxy;
import com.lhw.rpc_starter.register.Register;
import com.lhw.rpc_starter.register.RegisterProperty;
import com.lhw.rpc_starter.remote.Server;
import com.lhw.rpc_starter.util.HostUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ：linhw
 * @date ：22.2.17 15:35
 * @description：上下文监听器，监听到有上下文初始化或者刷新的时候，调用这个类
 * @modified By：
 */
public class RpcApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    //记录注册了的Service，用于服务提供者RPC调用指定方法并返回响应
    public static Map<String, Object> SERVICE_MAP = new ConcurrentHashMap<>(16);

    private RegisterProperty registerProperty;
    private Register register;
    private InvokeProxy invokeProxy;
    private Server server;

    public RpcApplicationListener(RegisterProperty registerProperty,
                                  Register register,
                                  InvokeProxy invokeProxy,
                                  Server server) {
        this.registerProperty = registerProperty;
        this.register = register;
        this.invokeProxy = invokeProxy;
        this.server = server;
        System.out.println("上下文事件监听器启动成功");
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //获取到根上下文，然后从根上下文中获取bean来注册服务，以及将远程服务引用注入bean中
//        if (null == event.getApplicationContext().getParent()){
        ApplicationContext applicationContext = event.getApplicationContext();
        registerService(applicationContext);
        injectService(applicationContext);
//        }
    }

    /**
     * 扫描整体，获取到所有添加了@RpcService注解的类，将其注册成一个服务
     *
     * @param applicationContext
     */
    private void registerService(ApplicationContext applicationContext) {
        //1.拿到上下文中所有带有@RpcService注解的bean
        Collection<Object> beans = applicationContext.getBeansWithAnnotation(RpcService.class).values();
        if (CollectionUtils.isEmpty(beans)) {
            return;
        }
        //2.拿到bean的元数据，包括包路径、接口名
        beans.forEach(bean -> {
            Class<?> clazz = bean.getClass();
            Class<?>[] interfaces = clazz.getInterfaces();
            String interfaceName = interfaces[0].getName();
            //3.将这些元数据赋值给Service
            Service service = Service.builder()
                    .serviceName(interfaceName)
                    .host(HostUtil.getLocalHost())
                    .port(registerProperty.getPort())
                    .clazz(clazz).build();
            //4.将service注册到注册中心。
            register.register(service);
            SERVICE_MAP.put(interfaceName, bean);
        });
        new Thread(() -> {
            server.start();
        }).start();
    }

    /**
     * 给所有应用注入远程引用对象（也就是加了@RpcReference注解的属性）
     *
     * @param applicationContext
     */
    private void injectService(ApplicationContext applicationContext) {
        //1、获取当前上下文中所有的bean的名字
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            Class<?> clazz = applicationContext.getType(beanName);
            if (null == clazz) {
                continue;
            }
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                Annotation annotation = field.getAnnotation(RpcReference.class);
                if (null == annotation) {
                    continue;
                }
                Object bean = applicationContext.getBean(beanName);
                Class<?> fieldType = field.getType();
                field.setAccessible(true);
                //生成一个代理，然后将其与field属性绑定
                try {
                    field.set(bean, invokeProxy.getProxy(fieldType));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
