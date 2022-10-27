package com.newrain.concurrency.aqs.signal.cyclicBarrier;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @author newRain
 * @description CyclicBarrier使用示例
 */
public class CyclicBarrierExample {


    public static void main(String[] args) {
        // 创建 CyclicBarrier
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(2, new Runnable() {
            @Override
            public void run() {
                System.out.println("人满了，准备发车：" + new Date());
            }
        });

        // 线程调用的任务
        Runnable runnable = () -> {
            // 生成随机数 1-3
            int randomNumber = new Random().nextInt(3) + 1;
            // 进入任务
            System.out.printf("我是：%s 再走：%d 秒就到车站了，现在时间：%s%n", Thread.currentThread().getName(), randomNumber, new Date());
            try {
                // 模拟执行
                TimeUnit.SECONDS.sleep(randomNumber);
                // 调用 CyclicBarrier
                cyclicBarrier.await();
                // 任务执行
                System.out.printf("线程：%s 上车，时间：%s%n", Thread.currentThread().getName(), new Date());
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        };

        // 创建线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        // 执行任务 1
        threadPool.submit(runnable);
        // 执行任务 2
        threadPool.submit(runnable);
        // 执行任务 3
        threadPool.submit(runnable);
        // 执行任务 4
        threadPool.submit(runnable);

        // 等待所有任务执行完终止线程池
        threadPool.shutdown();
    }
}
