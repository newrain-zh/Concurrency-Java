package com.newrain.concurrency.cas.atomic;

import com.newrain.concurrency.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author newRain
 * @description AtomicLong 使用示例
 */
@Slf4j
@ThreadSafe
public class AtomicLongExample {

    private static int clientTotal = 5000;
    private static int threadTotal = 200;
    private static AtomicLong count = new AtomicLong(0);

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
                    log.error("exception:", e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        log.info("count:{}", count.get());
        executorService.shutdown();
    }

    private static void add() {
        count.incrementAndGet();
    }
}
