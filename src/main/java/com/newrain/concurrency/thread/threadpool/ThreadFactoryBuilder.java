package com.newrain.concurrency.thread.threadpool;

import lombok.Data;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程工厂
 *
 * @author zhiqing.zhang
 * @description ThreadFactoryBuilder 使用示例
 */
@Data
public class ThreadFactoryBuilder implements ThreadFactory {

    private AtomicInteger count;
    private String        name;

    public ThreadFactoryBuilder(String name) {
        count     = new AtomicInteger(0);
        this.name = name;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread();
        thread.setName(name);
        count.getAndAdd(1);
        return thread;
    }
}