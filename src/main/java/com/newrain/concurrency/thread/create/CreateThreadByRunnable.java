package com.newrain.concurrency.thread.create;

import lombok.extern.slf4j.Slf4j;

/**
 * @author newRain
 * @description 使用Runnable创建线程示例
 */
@Slf4j
public class CreateThreadByRunnable implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            log.debug("run {}", i);
        }
    }


    public static void main(String[] args) {
        log.debug("main thread start ....");
        CreateThreadByRunnable createThreadByInterface = new CreateThreadByRunnable();
        Thread thread = new Thread(createThreadByInterface);
        thread.start();
        log.debug("main thread end ....");
    }
}
