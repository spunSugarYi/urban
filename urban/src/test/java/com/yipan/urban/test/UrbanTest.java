package com.yipan.urban.test;


import com.yipan.urban.content.Content;
import com.yipan.urban.factory.ClientFactory;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

public class UrbanTest {
    public static void main(String[] args) throws InterruptedException {
        Content content = new Content();
        content.setId(123456);
        content.setName("1测试One");
        ByteBuf buffer = Unpooled.buffer();
        buffer.writeInt(content.getId());
        buffer.writeBytes(content.getName().getBytes(CharsetUtil.UTF_8));
        ClientFactory clientFactory = new ClientFactory();
        NioSocketChannel nioSocketChannel = clientFactory.getClient(new InetSocketAddress("localHost", 9090));
        ChannelFuture channelFuture = nioSocketChannel.writeAndFlush(buffer);
        channelFuture.sync();
    }
}
