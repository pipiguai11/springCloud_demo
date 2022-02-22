package com.lhw.rpc_starter.remote;

import com.lhw.rpc_starter.listener.RpcApplicationListener;
import com.lhw.rpc_starter.model.RpcRequest;
import com.lhw.rpc_starter.model.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author ：linhw
 * @date ：22.2.21 13:21
 * @description：服务端处理器
 * @modified By：
 */
//@Slf4j
public class ServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private static Logger log = LoggerFactory.getLogger(ServerHandler.class);

    /**
     * 新的客户端连接事件
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        log.info("Server handlerAdded 新的客户端连接事件 | channel={} remote={} ",ctx.channel().id(),ctx.channel().remoteAddress());
    }

    /**
     * 通道注册事件
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        log.info("Server channelRegistered 通道注册事件 | channel={} remote={}",ctx.channel().id(),ctx.channel().remoteAddress());
    }

    /**
     * 通道处于活动状态事件
     * 通道完成注册后触发
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("Server channelActive 通道处于活动状态事件 | channel={} remote={}",ctx.channel().id(),ctx.channel().remoteAddress());
    }

    /**
     * 通道数据读取完毕事件
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.info("Server channelReadComplete 通道数据读取完毕事件 | channel={} remote={}",ctx.channel().id(),ctx.channel().remoteAddress());
    }

    /**
     * 通道进入非活动状态事件
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("Server channelInactive 通道进入非活动状态事件 | channel={} remote={}",ctx.channel().id(),ctx.channel().remoteAddress());
    }

    /**
     * 通道移除事件
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        log.info("Server channelUnregistered 通道移除事件 | channel={} remote={}",ctx.channel().id(),ctx.channel().remoteAddress());
    }

    /**
     * 处理器移除事件 (断开连接)
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.info("Server handlerRemoved 处理器移除事件 | channel={} remote={}",ctx.channel().id(),ctx.channel().remoteAddress());
    }

    /**
     * 异常发生事件
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }


    /**
     * 服务端（提供端）接收到Rpc请求后进行处理，调用本地方法并返回结果
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest msg) throws Exception {
        RpcResponse response = new RpcResponse();
        //通过反射调用服务提供者的方法
        response.setRequestId(msg.getRequestId());
        try {
            String interfaceName = msg.getClassName();
            Object obj = RpcApplicationListener.SERVICE_MAP.get(interfaceName);
            if (null == obj) {
                log.error("can not find local service 【" + interfaceName + "】");
                response.setError("can not find local service 【" + interfaceName + "】");
            }
            Class<?> clazz = obj.getClass();
            Method method = clazz.getDeclaredMethod(msg.getMethodName(), msg.getParameterTypes());
            if (null == method) {
                log.error("can not find local method 【" + msg.getMethodName() + "】");
                response.setError("can not find local method 【" + msg.getMethodName() + "】");
            }
            Object result = method.invoke(obj, msg.getParameters());
            response.setResult(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ctx.writeAndFlush(response);
    }
}
