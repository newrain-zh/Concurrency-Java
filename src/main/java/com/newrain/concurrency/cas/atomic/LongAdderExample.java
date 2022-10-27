package com.newrain.concurrency.cas.atomic;


import com.newrain.concurrency.annoations.ThreadSafe;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author newRain
 * @description LongAdder使用示例
 */
@ThreadSafe
public class LongAdderExample {
    private static int clientTotal = 5000;
    private static int threadTotal = 200;
    private static LongAdder count = new LongAdder();
    static ExecutorService executorService = Executors.newCachedThreadPool();

    public static void main(String[] args) throws InterruptedException {

        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    add();
                    semaphore.release();
                } catch (Exception e) {
                    //log error
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        System.out.printf("count == clientTotal result:%s%n", count.longValue() == clientTotal);
    }

    private static void add() {
        count.increment();
    }
}
