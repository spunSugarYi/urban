package com.yipan.urban.factory;

import com.yipan.urban.connection.CreateConnection;
import com.yipan.urban.pool.ClientPool;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用于维护和创建客户端连接
 */
public class ClientFactory {
    /**
     * 客户端线程池大小,默认为1
     */
    private int poolSize = 1;
    /**
     * 获取客户端的随机值
     */
    private Random rand = new Random();
    /**
     * 客户端的连接线程池
     */
    private NioEventLoopGroup client;
    /**
     * 存储地址和对应连接的地址,允许客户端连接多台服务器
     */
    private ConcurrentHashMap<InetSocketAddress, ClientPool> clientMap = new ConcurrentHashMap<>();

    private CreateConnection createConnection;

    public ClientFactory() {
        createConnection = new CreateConnection();
    }

    public ClientFactory(int poolSize) {
        this.poolSize = poolSize;
        createConnection = new CreateConnection();
    }

    public synchronized NioSocketChannel getClient(InetSocketAddress address) {
        /**
         * 通过地址获取对应的连接池
         */
        ClientPool clientPool = clientMap.get(address);
        //如果获取到空的连接池,则需要创建新的连接池,并添加到clientMap中
        if (clientPool == null) {
            clientMap.putIfAbsent(address, new ClientPool(poolSize));
        }
        clientPool = clientMap.get(address);
        int index = rand.nextInt(poolSize);
        //随机获取一个连接
        NioSocketChannel client = clientPool.getClients(index);
        //如果客户端不为空,并且处于活跃状态,就直接返回
        if (null != client && client.isActive()) {
            return client;
        }
        //创建新的客户端,由于在创建的过程中可能会出现并发问题,所有需要上锁
        synchronized (clientPool.getLock(index)) {
            client = createConnection.create(address);
            return client;
        }
    }
}
