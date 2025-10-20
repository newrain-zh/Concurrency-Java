package com.newrain.concurrency.utils.countdownlatch;

import java.util.concurrent.CountDownLatch;

// 工人线程：执行施工任务，完成后调用countDown()
class Worker implements Runnable {
    private final String         workerName; // 工人名称
    private final CountDownLatch latch; // 共享的CountDownLatch

    public Worker(String workerName, CountDownLatch latch) {
        this.workerName = workerName;
        this.latch      = latch;
    }

    @Override
    public void run() {
        try {
            System.out.println(workerName + "开始施工...");
            // 模拟施工耗时（1-3秒随机）
            Thread.sleep((long) (Math.random() * 2000 + 5000));
//            Thread.sleep(10000);
            System.out.println(workerName + "施工完成！");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // 恢复中断状态
        } finally {
            // 无论任务是否正常完成，都必须调用countDown()（避免计数无法归零）
            latch.countDown();
            System.out.println(workerName + "已汇报完成，剩余待完成人数" + latch.getCount());
        }
    }
}

// 主类：监理等待所有工人完成后检查
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        // 1. 初始化CountDownLatch，计数为3（3个工人）
        CountDownLatch latch = new CountDownLatch(3);

        // 2. 创建3个工人线程并启动
        new Thread(new Worker("工人A", latch), "线程1").start();
        new Thread(new Worker("工人B", latch), "线程2").start();
        Thread thread1 = new Thread(new Worker("工人C", latch), "线程3");
        thread1.start();

        // 3. 监理（主线程）等待所有工人完成（计数变为0）
/*        Thread thread = new Thread(() -> {
            try {
                System.out.println("监理线程1-中断：等待所有工人完成施工...");
                latch.await(); // 阻塞直到计数为0
                // 4. 所有工人完成后，执行检查工作
                System.out.println("监理线程1-中断：所有工人已完成施工，开始检查工程质量！");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "监理线程1-中断");
        thread.start();*/
        Thread thread = new Thread(() -> {
            try {
                System.out.println("监理：等待所有工人完成施工...");
                latch.await(); // 阻塞直到计数为0
                // 4. 所有工人完成后，执行检查工作
                System.out.println("监理：所有工人已完成施工，开始检查工程质量！");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "监理线程");
        thread.start();

        System.out.println("主线程结束");
        Thread.sleep(2000);
        thread1.interrupt();

    }
}