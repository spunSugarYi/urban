package com.yipan.urban.connection.handler;

import com.yipan.urban.content.Content;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientSimpleHandler extends SimpleChannelInboundHandler<Content> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Content content) throws Exception {
        System.out.println(String.format("id:%s,name:%s", content.getId(), content.getName()));
        channelHandlerContext.writeAndFlush(content);
    }
}
