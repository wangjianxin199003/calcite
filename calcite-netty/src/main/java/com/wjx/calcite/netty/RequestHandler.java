package com.wjx.calcite.netty;

import com.wjx.calcite.protocol.Frame;
import com.wjx.calcite.protocol.FrameTypeConstants;
import com.wjx.calcite.protocol.RpcRequest;
import com.wjx.calcite.protocol.RpcResponse;
import com.wjx.calcite.serializer.ProtoStuffSerializer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;

public class RequestHandler extends ChannelInboundHandlerAdapter {
    ProtoStuffSerializer stuffSerializer = new ProtoStuffSerializer();

    private ApplicationContext context;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Frame frame = (Frame) msg;
        int protocolType = frame.getProtocolType();
        if (protocolType == FrameTypeConstants.REQUEST) {
            RpcRequest request = stuffSerializer.deserialize(frame.getEntity(), RpcRequest.class);
            Object bean = context.getBean(Class.forName(request.getInterfaceName()));
            Object[] args = request.getArgs();
            Class[] argType = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                argType[i] = args[i].getClass();
            }
            Method method = bean.getClass().getDeclaredMethod(request.getInvokeMethodName(), argType);
            Object result = method.invoke(bean, args);
            RpcResponse rpcResponse = new RpcResponse();
            rpcResponse.setResult(result);
            Frame responseFrame = new Frame();
            responseFrame.setVersion(1);
            responseFrame.setProtocolType(FrameTypeConstants.RESPONSE);
            responseFrame.setSessionId(frame.getSessionId());
            byte[] responseEntiy = stuffSerializer.serialize(rpcResponse);
            responseFrame.setEntity(responseEntiy);
            responseFrame.setTotalLength(responseEntiy.length + 18);
            ctx.writeAndFlush(frame);
        }
    }
}
