package com.lhw.echo;

import com.lhw.echo.handler.EchoClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetSocketAddress;

//@SpringBootApplication
public class EchoClientApplication {

//    public static void main(String[] args) {
//        SpringApplication.run(EchoClientApplication.class, args);
//    }

    private final String host;
    private final int port;

    public EchoClientApplication(String host, int port){
        this.host = host;
        this.port = port;
    }

    public static void main(String[] args) {
        new EchoClientApplication("127.0.0.1",9999).start();
    }

    public void start(){
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .remoteAddress(new InetSocketAddress(host,port))
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new EchoClientHandler());
                        }
                    });

            ChannelFuture future = bootstrap.connect().sync();

            //注册一个监听器，当客户端处理事件完成后【这里的事件是连接服务器】并返回结果时【无论是成功的还是失败的】，回调该方法
            future.addListener(future1 -> {
                System.out.println("我已经正常的连接上了服务器");
            });

            future.channel().close().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {

        }
    }

}
