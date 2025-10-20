package com.newrain.concurrency.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

@Slf4j
public class LockSupportDemo {

    public static void main(String[] args) throws InterruptedException {
//        demo();
        unparkDemo();
//        interruptDemo();
    }


    public static void demo() {
        Thread mainThread = Thread.currentThread();

        // 创建一个线程从1数到1000
        Thread counterThread = new Thread(() -> {
            int count = 0;
            do {
                count++;
                log.info("i=" + count);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (count == 100) {
                    // 当数到500时，唤醒主线程
                    LockSupport.unpark(mainThread);
                }
            } while (count != 150);

        });

        counterThread.start();

        // 主线程调用park
        LockSupport.park();
        log.info("Main thread was unparked.");
    }


    public static void unparkDemo() throws InterruptedException {
        Thread counterThread = new Thread(() -> {
            int count = 0;
            for (int i = 0; i < 100; i++) {
                count++;
                log.info("i=" + count);
                if (count == 20){
                    LockSupport.park();
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        counterThread.setName("线程1");
        counterThread.start();
        Thread.sleep(5000);
        LockSupport.unpark(counterThread);
//        Thread.sleep(5000);
        log.info("main thread begin unpark!");

    }

    public static void interruptDemo() throws InterruptedException {
        Thread thread = new Thread(() -> {
//            System.out.println("child thread begin park! ");
            log.info("child thread begin park! ");
            // 调用park方法，挂起自己，只有被中断才会退出循环￼
            while (!Thread.currentThread().isInterrupted()) {
                LockSupport.park();
            }
            log.info("child thread unpark!");

        });
        thread.setName("线程1");
        thread.start();
        Thread.sleep(5000);
        log.info("main thread begin interrupt!");
        thread.interrupt();
        Thread.sleep(100000);

    }
}