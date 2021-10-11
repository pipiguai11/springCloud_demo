package com.lhw.echo.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author ：linhw
 * @date ：21.9.7 14:58
 * @description：服务器处理器：使用netty的方式接收/发送消息
 * @modified By：
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 每个消息入站时调用，也就是通道中有消息时就会调用
     * @param channelHandlerContext
     * @param o
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        System.out.println("【" + EchoServerHandler.class.getSimpleName() +"】通道中发现消息");
        ByteBuf buf = (ByteBuf)o;
        System.out.println("Server received: " + buf.toString(CharsetUtil.UTF_8));
        //将接收到的消息发送给客户端
        channelHandlerContext.write(buf);
    }

    /**
     * 当channelRead读取消息时，判断是最后一条消息，则触发调用
     * @param channelHandlerContext
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
        //强刷缓冲区，并关闭通道
        channelHandlerContext.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * 捕获到异常时调用
     * @param channelHandlerContext
     * @param throwable
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
        throwable.printStackTrace();
        channelHandlerContext.close();
    }
}
