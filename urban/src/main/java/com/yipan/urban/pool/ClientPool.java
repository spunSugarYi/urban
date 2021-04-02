package com.yipan.urban.pool;

import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Arrays;

public class ClientPool {
    /**
     * 维护客户端向服务端发起的连接
     */
    private NioSocketChannel[] clients;
    /**
     * 每一个连接对应的锁
     */
    private Object[] lock;

    public ClientPool(int size) {
        //初始化连接数组的大小
        clients = new NioSocketChannel[size];
        lock = new Object[size];
        //为每个锁对象赋值
        Arrays.fill(lock, new Object());
    }

    public NioSocketChannel getClients(int index) {
        return clients[index];
    }

    public Object getLock(int index) {
        return lock[index];
    }

}
