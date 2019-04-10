package com.wjx.calcite.netty;

import com.wjx.calcite.protocol.Frame;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.charset.Charset;
import java.util.List;

public class FrameDecodeHandler extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int readableBytes = byteBuf.readableBytes();
        byte[] header = new byte[18];
        byteBuf.readBytes(header, 0, 18);
        byte[] entity = new byte[readableBytes - 18];
        byteBuf.readBytes(entity, 18, readableBytes - 18);
        Frame frame = new Frame();
        frame.setHeader(Unpooled.copiedBuffer(header));
        frame.setEntity(entity);
        list.add(frame);
    }
}
