package com.newrain.concurrency.thread.interrupt;

import lombok.extern.slf4j.Slf4j;

/**
 * @author newRain
 * @description 中断线程代码运行示例
 */
@Slf4j
public class ThreadInterruptExample extends Thread {

    @Override
    public void run() {
        while (true) {
            log.debug("thread running");
            System.out.println(" thread running");
            if (isInterrupted()) {
                log.debug("thread interrupt ....");
                return;
            }
        }
    }


    public static void main(String[] args) {
        Thread thread = new ThreadInterruptExample();
        thread.start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            log.error("main error:", e);
        }
        thread.interrupt();//发出中断信号
    }
}
