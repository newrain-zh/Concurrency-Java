package com.newrain.concurrency.thread.create;

import lombok.extern.slf4j.Slf4j;

/**
 * @author newRain
 * @description 使用Thead类创建线程
 */
@Slf4j
public class CreateThreadByClass extends Thread {

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            log.debug("run {}", i);
        }
    }

    public static void main(String[] args) {
        log.debug("main thread start ....");
        CreateThreadByClass threadByClass = new CreateThreadByClass();
        threadByClass.start();
        log.debug("main thread end ....");
    }
}
