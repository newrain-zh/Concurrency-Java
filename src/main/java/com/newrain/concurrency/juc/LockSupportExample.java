package com.newrain.concurrency.juc;

import java.util.concurrent.locks.LockSupport;

/**
 * @author newRain
 * @description LockSupport 使用示例
 */
public class LockSupportExample {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println("1");
            try {
                Thread.sleep(5000);
                LockSupport.park();
                System.out.println("2");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        Thread.sleep(3000);
        LockSupport.unpark(thread);
    }
}
