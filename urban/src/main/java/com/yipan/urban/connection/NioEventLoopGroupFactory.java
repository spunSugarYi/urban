package com.yipan.urban.connection;

import io.netty.channel.nio.NioEventLoopGroup;

public class NioEventLoopGroupFactory {
    private static NioEventLoopGroup clientWorker;

    private static NioEventLoopGroupFactory factory;

    private NioEventLoopGroupFactory() {

    }

    public synchronized static NioEventLoopGroupFactory getInstance(int loopSize) {
        if (null != factory) {
            return factory;
        }
        factory = new NioEventLoopGroupFactory();
        clientWorker = new NioEventLoopGroup(loopSize);
        return factory;
    }

    public static NioEventLoopGroup getClientWorker() {
        return clientWorker;
    }
}
