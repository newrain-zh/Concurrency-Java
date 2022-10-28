package com.newrain.concurrency.thread.create;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @author newRain
 * @description Callable 创建线程示例
 */
@Slf4j
public class CreateThreadByCallable implements Callable<String> {

    @Override
    public String call() throws Exception {
        for (int i = 0; i < 10; i++) {
            log.debug("thread call:{}", i);
        }
        return "call";
    }

    public static void main(String[] args) {

        CreateThreadByCallable createThreadByCallable = new CreateThreadByCallable();
        FutureTask<String> futureTask = new FutureTask<String>(createThreadByCallable);
        new Thread(futureTask).start();
//    System.out.println("get() start=====>");
//    try {
//      // 调用get方法 主线程阻塞执行。否则异步。
//      // get()得到call()方法返回的结果
//      System.out.println(futureTask.get());
//    } catch (InterruptedException | ExecutionException e) {
//      e.printStackTrace();
//    }
        log.debug("main thread end ....");
    }
}
