package com.newrain.concurrency.thread.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author newRain
 * @description 自定义线程池拒绝策略
 */
@Slf4j
public class CustomIgnorePolicy implements RejectedExecutionHandler {


    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        log.debug("count:{}", executor.getTaskCount());
    }


}
