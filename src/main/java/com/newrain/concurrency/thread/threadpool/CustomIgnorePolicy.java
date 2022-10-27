package com.newrain.concurrency.thread.threadpool;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author newRain
 * @description 自定义线程池拒绝策略
 */
public class CustomIgnorePolicy implements RejectedExecutionHandler {


    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        System.out.println(executor.getTaskCount());
    }


}
