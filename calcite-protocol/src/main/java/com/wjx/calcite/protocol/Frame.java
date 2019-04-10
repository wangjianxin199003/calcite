package com.wjx.calcite.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class Frame {
    public static final byte[] separator = new byte[]{18, 17, 13, 10, 9};
    private ByteBuf header = Unpooled.buffer(18);
    private byte[] entity;

    public Frame setVersion(int version){
        header.setByte(0, version);
        return this;
    }

    public int getVersion(){
        return ((int) (header.getByte(0)));
    }

    public Frame setTotalLength(int totalLength) {
        header.setInt(1, totalLength);
        return this;
    }


    public Frame setSessionId(int sessionId) {
        header.setInt(5, sessionId);
        return this;
    }

    public int getSessionId(){
        return header.getInt(5);
    }

    public Frame setProtocolType(int protocolType) {
        header.setShort(9, protocolType);
        return this;
    }

    public int getProtocolType(){
        return (((int) (header.getShort(9))));
    }

    public Frame setCompressMethod(int compressMethod) {
        header.setByte(11, compressMethod);
        return this;
    }

    public Frame setSerializeType(int serializeType) {
        header.setByte(12, serializeType);
        return this;
    }

    public Frame setPlateForm(int plateForm) {
        header.setByte(13, plateForm);
        return this;
    }

    public Frame setCallerKey(String callerKey) {
        byte[] bytes = callerKey.getBytes();
        header.setBytes(14, bytes, 0, 32);
        return this;
    }

    public Frame setEntity(byte[] entity) {
        this.entity = entity;
        return this;
    }

    public byte[] getHeader(){
        return this.header.array();
    }

    public void setHeader(ByteBuf header) {
        this.header = header;
    }

    public byte[] getEntity() {
        return this.entity;
    }






}
