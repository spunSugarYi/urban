package com.yipan.urban.connection.handler;

import java.util.concurrent.ConcurrentHashMap;

public class ResponseHandler {
    private static ConcurrentHashMap<Long, Runnable> mapping = new ConcurrentHashMap<>();

    public static void addCallBack(long requestId, Runnable cb) {
        mapping.putIfAbsent(requestId, cb);
    }

    public static void runCallBack(long requestId) {
        Runnable runnable = mapping.get(requestId);
        runnable.run();
        removeCall(requestId);
    }

    private static void removeCall(long requestId) {
        mapping.remove(requestId);
    }
}
