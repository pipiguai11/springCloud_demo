package com.lhw.xmlregistry.config;

import com.alibaba.dubbo.registry.support.AbstractRegistryFactory;
import com.alibaba.dubbo.rpc.Protocol;
import com.alibaba.dubbo.rpc.RpcContext;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.config.DubboShutdownHook;
import org.apache.dubbo.remoting.transport.netty.NettyClient;
import org.jboss.netty.channel.ChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * @author ：linhw
 * @date ：22.2.16 15:40
 * @description：优雅关闭dubbo客户端监听器
 * @modified By：
 */
@Component
public class DubboClientCloseListener implements ApplicationListener<ContextClosedEvent> {

    private static Logger LOG = LoggerFactory.getLogger(DubboClientCloseListener.class);

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {

        if(RpcContext.getContext().getRemoteHost() == null){
            LOG.info("-------------没有需要关闭的资源-----------");
            return;
        }
        LOG.info("before spring context is closed, dubbo config destroy all...");
        ExtensionLoader<Protocol> loader = ExtensionLoader.getExtensionLoader(Protocol.class);
        for(String protocolName : loader.getLoadedExtensions()) {
            try{
                Protocol protocol = loader.getLoadedExtension(protocolName);
                if(protocol !=null) {
                    // 2.关闭协议类的扩展点
                    protocol.destroy();
                }
            }catch(Throwable t) {
                LOG.warn(t.getMessage(),t);
            }
        }
        // 用反射释放NettyClient所占用的资源, 以避免不能优雅shutdown的问题
        releaseNettyClientExternalResources();
    }

    private void releaseNettyClientExternalResources() {
        try {
            Field field = NettyClient.class.getDeclaredField("channelFactory");
            field.setAccessible(true);
            ChannelFactory channelFactory = (ChannelFactory) field.get(NettyClient.class);
            channelFactory.releaseExternalResources();
            field.setAccessible(false);
            LOG.info("Release NettyClient's external resources");
        } catch (Exception e) {
            LOG.error("Release NettyClient's external resources error", e);
        }
    }
}
