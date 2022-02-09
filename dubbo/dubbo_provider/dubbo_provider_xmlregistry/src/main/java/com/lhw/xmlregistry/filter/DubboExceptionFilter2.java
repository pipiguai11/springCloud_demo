package com.lhw.xmlregistry.filter;


import java.lang.reflect.Method;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.apache.dubbo.common.utils.ReflectUtils;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.ListenableFilter;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.service.GenericService;

/**
 * @author ：linhw
 * @date ：22.2.9 12:09
 * @description：Dubbo异常过滤器重写
 * @modified By：
 */
@Activate(
        group = {"provider"}
)
public class DubboExceptionFilter2 extends ListenableFilter {
    public DubboExceptionFilter2() {
        super.listener = new DubboExceptionFilter2.ExceptionListener();
        System.out.println("dubbo过滤器加载了");
    }

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        return invoker.invoke(invocation);
    }

    static class ExceptionListener implements Listener {
        private Logger logger = LoggerFactory.getLogger(DubboExceptionFilter2.class);

        ExceptionListener() {
        }

        @Override
        public void onResponse(Result appResponse, Invoker<?> invoker, Invocation invocation) {
            if (appResponse.hasException() && GenericService.class != invoker.getInterface()) {
                try {
                    Throwable exception = appResponse.getException();
                    if (exception instanceof RuntimeException || !(exception instanceof Exception)) {
                        try {
                            Method method = invoker.getInterface().getMethod(invocation.getMethodName(), invocation.getParameterTypes());
                            Class<?>[] exceptionClassses = method.getExceptionTypes();
                            Class[] var7 = exceptionClassses;
                            int var8 = exceptionClassses.length;

                            for(int var9 = 0; var9 < var8; ++var9) {
                                Class<?> exceptionClass = var7[var9];
                                if (exception.getClass().equals(exceptionClass)) {
                                    return;
                                }
                            }
                        } catch (NoSuchMethodException var11) {
                            return;
                        }
                        String serviceFile = ReflectUtils.getCodeBase(invoker.getInterface());
                        String exceptionFile = ReflectUtils.getCodeBase(exception.getClass());
                        // 新增代码块 Begin
                        if (exceptionFile != null && exception.getClass().getName().startsWith("com.xxx")) {
                            //判断异常类如果是com.xxx开头则属于自定义业务异常，无需额外处理，直接return 消费者拿到的就是 服务提供者锁抛出的异常
                            this.logger.info("DubboExceptionFilter 业务校验异常:" + exception.getMessage());
                            return;
                        }
                        // 新增代码块 end

                        this.logger.error("Got unchecked and undeclared exception which called by " + RpcContext.getContext().getRemoteHost() + ". service: " + invoker.getInterface().getName() + ", method: " + invocation.getMethodName() + ", exception: " + exception.getClass().getName() + ": " + exception.getMessage(), exception);

                        if (serviceFile != null && exceptionFile != null && !serviceFile.equals(exceptionFile)) {
                            // 因为这里有判断!serviceFile.equals(exceptionFile) 所以新增代码如果写在这里有一定的风险会导致此处无法进入该IF内部
                            String className = exception.getClass().getName();
                            if (!className.startsWith("java.") && !className.startsWith("javax.")) {
                                if (!(exception instanceof RpcException)) {
                                    appResponse.setException(new RuntimeException(StringUtils.toString(exception)));
                                }
                            }
                        }
                    }
                } catch (Throwable var12) {
                    this.logger.warn("Fail to ExceptionFilter when called by " + RpcContext.getContext().getRemoteHost() + ". service: " + invoker.getInterface().getName() + ", method: " + invocation.getMethodName() + ", exception: " + var12.getClass().getName() + ": " + var12.getMessage(), var12);
                }
            }
        }

        @Override
        public void onError(Throwable e, Invoker<?> invoker, Invocation invocation) {
            this.logger.error("Got unchecked and undeclared exception which called by " + RpcContext.getContext().getRemoteHost() + ". service: " + invoker.getInterface().getName() + ", method: " + invocation.getMethodName() + ", exception: " + e.getClass().getName() + ": " + e.getMessage(), e);
        }

        public void setLogger(Logger logger) {
            this.logger = logger;
        }
    }
}