package com.lhw.rpc_starter.serializes;

/**
 * @author ：linhw
 * @date ：22.2.21 14:02
 * @description：序列化方式
 * @modified By：
 */
public interface Serializer {

    byte[] serialize(Object obj);

    Object deserialize(byte[] bytes, Class<?> clazz);

}
