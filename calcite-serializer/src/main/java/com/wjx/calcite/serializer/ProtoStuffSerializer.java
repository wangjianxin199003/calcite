package com.wjx.calcite.serializer;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.wjx.calcite.protocol.RpcRequest;
import com.wjx.calcite.protocol.RpcResponse;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wangjianxin01
 */
public class ProtoStuffSerializer implements Serializer {


    private static Map<Class<?>, Schema<?>> cachedSchema = new ConcurrentHashMap<Class<?>, Schema<?>>();


    @SuppressWarnings("unchecked")
    private static <T> Schema<T> getSchema(Class<T> cls) {
        Schema<T> schema = (Schema<T>) cachedSchema.get(cls);
        if (schema == null) {
            schema = RuntimeSchema.createFrom(cls);
            cachedSchema.put(cls, schema);
        }
        return schema;
    }

    @SuppressWarnings("unchecked")
    public <T> byte[] serialize(T obj) {
        Class<T> cls = (Class<T>) obj.getClass();
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try {
            Schema<T> schema = getSchema(cls);
            return ProtostuffIOUtil.toByteArray(obj, schema, buffer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            buffer.clear();
        }
    }

    public <T> T deserialize(byte[] data, Class<T> cls) {
        try {
            T message = (T) cls.getConstructors()[0].newInstance();
            Schema<T> schema = getSchema(cls);
            ProtostuffIOUtil.mergeFrom(data, message, schema);
            return message;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setInterfaceId(1);
        rpcRequest.setInterfaceName("com.wjx.demo.HelloService");
        rpcRequest.setInvokeMethodName("hello");
        Object[] arg = new Object[2];
        arg[0] = "string";
        arg[1] = 110;
        rpcRequest.setArgs(arg);
        RpcResponse rpcResponse = new RpcResponse();
        rpcResponse.setResult(rpcRequest);

        ProtoStuffSerializer stuffSerializer = new ProtoStuffSerializer();
        byte[] serialize = stuffSerializer.serialize(rpcResponse);
        Object deserialize = stuffSerializer.deserialize(serialize, rpcResponse.getClass());
        System.out.println(deserialize);
//        byte[] serialize = stuffSerializer.serialize(rpcRequest);
//        RpcRequest request = stuffSerializer.deserialize(serialize, rpcRequest.getClass());
//        Object[] args1 = request.getArgs();
//        System.out.println(args1[0].getClass());
//        System.out.println(args1[1].getClass());
//        System.out.println(request);
    }



}
