package com.yipan.urban.connection;

import com.yipan.urban.connection.handler.ClientSimpleHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * 创建客户端连接,用于连接服务器
 */
public class CreateConnection {
    /**
     * 线程组大小,默认为1
     */
    private int nioEventLoopGroupSize = 1;


    private Bootstrap bootstrap;

    public CreateConnection() {
        this.bootstrap = new Bootstrap();
        NioEventLoopGroupFactory.getInstance(nioEventLoopGroupSize);
    }

    public CreateConnection(int nioEventLoopGroupSize) {
        this.bootstrap = new Bootstrap();
        NioEventLoopGroupFactory.getInstance(nioEventLoopGroupSize);
    }

    /**
     * 创建连接
     *
     * @param address 连接地址
     * @return 连接
     */
    public NioSocketChannel create(InetSocketAddress address) {
        ChannelFuture connect = bootstrap.group(NioEventLoopGroupFactory.getClientWorker())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        ChannelPipeline pipeline = nioSocketChannel.pipeline();
                        pipeline.addLast(new ClientSimpleHandler());
                    }
                }).connect(address);
        try {
            Channel channel = connect.sync().channel();
            return (NioSocketChannel) channel;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
