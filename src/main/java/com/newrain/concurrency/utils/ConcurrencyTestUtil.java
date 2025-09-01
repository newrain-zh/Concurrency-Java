package com.newrain.concurrency.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Slf4j
public class ConcurrencyTestUtil {


    //总线程数
    private static int clientTotal = 1200;
    //并发数
    private static int threadTotal = 200;


    public static void start() throws InterruptedException {
        ExecutorService      executorService = Executors.newCachedThreadPool();
        final Semaphore      semaphore       = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch  = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    //todo 用于测试的代码
                    semaphore.release();
                } catch (Exception e) {
                    log.error("exception:", e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();

    }
}