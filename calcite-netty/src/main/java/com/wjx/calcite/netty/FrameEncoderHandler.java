package com.wjx.calcite.netty;

import com.wjx.calcite.protocol.Frame;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class FrameEncoderHandler extends MessageToByteEncoder<Frame> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Frame frame, ByteBuf byteBuf) throws Exception {
        byteBuf.writeBytes(frame.getHeader());
        byteBuf.writeBytes(frame.getEntity());
        byteBuf.writeBytes(Frame.separator);
    }
}
