package com.newrain.concurrency.cas.atomic;

import com.newrain.concurrency.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * AtomicLong 使用方式同AtomicInteger一样
 *
 * @author newRain
 * @description AtomicInteger 使用示例
 */
@Slf4j
@ThreadSafe
public class AtomicIntergerExample {
    //总请求数
    private static int clientTotal = 5000;
    //并发数
    private static int threadTotal = 1000;

    private static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    add();
                    semaphore.release();
                } catch (Exception e) {
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
    }

    private static void add() {
        count.incrementAndGet();
    }
}
