package com.lhw.rpc_starter.remote;

import com.lhw.rpc_starter.model.RpcRequest;
import com.lhw.rpc_starter.model.RpcResponse;
import com.lhw.rpc_starter.serializes.RpcDecoder;
import com.lhw.rpc_starter.serializes.RpcEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author ：linhw
 * @date ：22.2.21 16:05
 * @description：服务端
 * @modified By：
 */
public class Server {

    private int port;

    public Server(int port) {
        this.port = port;

    }

    public void start(){
        System.out.println("服务端Socket正在启动监听");
        EventLoopGroup parentGroup = new NioEventLoopGroup(1);
        EventLoopGroup clientGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(parentGroup, clientGroup)
                    .channel(NioServerSocketChannel.class)
//                    .option(ChannelOption.SO_BACKLOG, 100)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new RpcDecoder(RpcRequest.class))
                                    .addLast(new RpcEncoder(RpcResponse.class))
                                    .addLast(new ServerHandler());
                        }
                    });
            //启动服务并监听port端口
            ChannelFuture future = bootstrap.bind(port).sync();
            //等待服务关闭
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            parentGroup.shutdownGracefully();
            clientGroup.shutdownGracefully();
        }
    }

}
