package com.lhw.rpc_starter.serializes;

import java.io.*;

/**
 * @author ：linhw
 * @date ：22.2.21 14:04
 * @description：JDK序列化方式
 * @modified By：
 */
public class JDKSerialize implements Serializer {

    @Override
    public byte[] serialize(Object obj) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (ObjectOutputStream stream = new ObjectOutputStream(out)) {
            stream.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    @Override
    public Object deserialize(byte[] bytes, Class<?> clazz) {
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        try (ObjectInputStream inputStream = new ObjectInputStream(in)) {
            return inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
