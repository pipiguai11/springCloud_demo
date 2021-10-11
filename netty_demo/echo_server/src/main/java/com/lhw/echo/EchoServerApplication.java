package com.lhw.echo;

import com.lhw.echo.handler.EchoServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.InputStream;
import java.util.Scanner;

//@SpringBootApplication
public class EchoServerApplication {

//    public static void main(String[] args) {
//        SpringApplication.run(EchoServerApplication.class, args);
//    }

    private final int port;

    public EchoServerApplication(int port){
        this.port = port;
    }

    public static void main(String[] args) throws InterruptedException {
        new EchoServerApplication(9999).start();
    }

    public void start() throws InterruptedException {
        //新建一个Group用于接收和处理新连接
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            //设置group、信道类型（NioServerSockerChannel）、本地地址及端口、
            // 实例化一个ChannelInitializer并将自定义的EchoServerHandler放入到Channel的pipeline中
            bootstrap.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(port)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new EchoServerHandler());
                        }
                    });
            //绑定服务器，调用sync表示同步使当前线程阻塞
            ChannelFuture future = bootstrap.bind().sync();

            //这里可以给future注册一个监听，当future这个事件处理完之后，会自动回调operationComplete方法
            future.addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    System.out.println("我已经完成了服务器的启动和绑定");
                }
            });
            System.out.println(EchoServerApplication.class.getSimpleName() + " started and listen on " + future.channel().localAddress());
            //等待服务器关闭

            //这里阻塞住，防止客户端还没连接上，服务端就先结束了
            System.out.println(new Scanner(System.in).next());

            future.channel().close().sync();
        }finally {
            group.shutdownGracefully().sync();
        }
    }

}
