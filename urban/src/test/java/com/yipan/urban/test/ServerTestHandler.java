package com.yipan.urban.test;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class ServerTestHandler extends SimpleChannelInboundHandler<ByteBuf> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf msg) throws Exception {
        int i = msg.readInt();
        String s = msg.toString(CharsetUtil.UTF_8);
        System.out.println(String.format("id:%s,name:%s", i, s));
    }
}
