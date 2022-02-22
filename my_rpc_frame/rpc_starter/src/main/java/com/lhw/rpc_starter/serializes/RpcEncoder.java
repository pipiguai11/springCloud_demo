package com.lhw.rpc_starter.serializes;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author ：linhw
 * @date ：22.2.21 13:57
 * @description：Rpc序列化编码格式
 * @modified By：
 */
public class RpcEncoder extends MessageToByteEncoder<Object> {

    private Class<?> clazz;

    public RpcEncoder(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        Serializer serializer = new JDKSerialize();
        if (clazz.isInstance(msg)) {
            byte[] b = serializer.serialize(msg);
            out.writeInt(b.length);
            out.writeBytes(b);
        }
    }
}
