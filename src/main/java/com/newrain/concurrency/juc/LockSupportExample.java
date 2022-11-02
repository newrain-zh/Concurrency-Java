package com.newrain.concurrency.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * @author newRain
 * @description LockSupport 使用示例
 */
@Slf4j
public class LockSupportExample {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            log.debug("1");
            try {
                Thread.sleep(5000);
                LockSupport.park();
                log.debug("2");
                System.out.println("2");
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.error("error:", e);
            }
        });
        thread.start();
        Thread.sleep(3000);
        LockSupport.unpark(thread);
    }
}
