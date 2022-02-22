package com.lhw.rpc_starter.serializes;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author ：linhw
 * @date ：22.2.21 16:22
 * @description：Rpc反序列化编码格式
 * @modified By：
 */
public class RpcDecoder extends ByteToMessageDecoder {

    private Class<?> clazz;

    public RpcDecoder(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        Serializer serializer = new JDKSerialize();
        if (in.readableBytes() < 4) {
            return;
        }
        in.markReaderIndex();
        int dateLength = in.readInt();

        if (in.readableBytes() < dateLength) {
            in.resetReaderIndex();
            return;
        }

        byte[] b = new byte[dateLength];
        in.readBytes(b);
        out.add(serializer.deserialize(b, clazz));
    }
}
