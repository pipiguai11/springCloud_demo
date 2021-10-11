package com.lhw.echo.handler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author ：linhw
 * @date ：21.9.7 15:28
 * @description：客户端处理器：使用netty的方式连接服务端，并接收/发送消息
 * @modified By：
 */
public class EchoClientHandler extends SimpleChannelInboundHandler {

    /**
     * 客户端连接时触发调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端连接");
        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!", CharsetUtil.UTF_8));
    }

    /**
     * 客户端读取到消息时触发调用
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("【" + EchoClientHandler.class.getSimpleName() + "】客户端读取到消息");

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
