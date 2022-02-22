package com.lhw.rpc_starter.remote;

import com.lhw.rpc_starter.model.RpcRequest;
import com.lhw.rpc_starter.model.RpcResponse;
import com.lhw.rpc_starter.serializes.RpcDecoder;
import com.lhw.rpc_starter.serializes.RpcEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author ：linhw
 * @date ：22.2.21 15:54
 * @description：客户端
 * @modified By：
 */
public class Client {

    private String host;
    private int port;

    private EventLoopGroup eventLoopGroup = new NioEventLoopGroup(1);

    private ClientHandler handler;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start(){
        Bootstrap bootstrap = new Bootstrap();
        handler = new ClientHandler();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new RpcEncoder(RpcRequest.class));
                        pipeline.addLast(new RpcDecoder(RpcResponse.class));
                        pipeline.addLast(handler);
                    }
                });
        bootstrap.connect(host, port);
    }

    public RpcResponse invoke(RpcRequest request) {
        handler.sendRequest(request);
        return handler.getRpcResponse();
    }

}
