package com.lhw.rpc_starter.remote;

import com.lhw.rpc_starter.model.RpcRequest;
import com.lhw.rpc_starter.model.RpcResponse;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * @author ：linhw
 * @date ：22.2.21 13:20
 * @description：客户端处理器
 * @modified By：
 */
//@Slf4j
public class ClientHandler extends SimpleChannelInboundHandler<RpcResponse> {

    private static Logger log = LoggerFactory.getLogger(ClientHandler.class);

    private RpcResponse rpcResponse;

    private CountDownLatch latch = new CountDownLatch(1);

    private Channel channel;

    /**
     * 调用ChannelHandlerContext.fireChannelRegistered()以转发到ChannelPipeline中的下一个ChannelInboundHandler
     * 这里先拿到通道，然后本地存一份，用于发送和接收消息
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        log.info("Client channelRegistered 通道注册事件 | channel={} remote={}",ctx.channel().id(),ctx.channel().remoteAddress());
        this.channel = ctx.channel();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse msg) throws Exception {
        this.rpcResponse = msg;
        latch.countDown();
    }

    /**
     * 客户端发送Rpc请求
     *
     * @param rpcRequest
     */
    public void sendRequest(RpcRequest rpcRequest) {
        channel.writeAndFlush(rpcRequest);
    }

    /**
     * 客户端获取Rpc响应结果
     *
     * @return
     */
    public RpcResponse getRpcResponse() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this.rpcResponse;
    }

}
