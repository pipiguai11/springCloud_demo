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
            return this.rpcResponse;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            //每次获取完结果之后就把CountDownLatch重置为1，为下次的RPC调用做准备，否则rpcResponse没有覆盖到就返回了，会是上一次请求的rpcResponse
            //不过这种方式有一个问题，那就是只能是串行的处理RPC请求，不能并行.
            latch = new CountDownLatch(1);
        }
        return null;
    }

}
