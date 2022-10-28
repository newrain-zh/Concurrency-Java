package com.newrain.concurrency.thread.interrupt;

import lombok.extern.slf4j.Slf4j;

/**
 * 捕捉InterruptedException、Exception 终止线程运行示例
 *
 * @author newRain
 * @description 线程中断示例
 */
@Slf4j
public class ThreadInterruptExample2 implements Runnable {
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
                log.debug("this thread is running...");
            } catch (Exception e) {
                log.error("InterruptedException");
                break;
            }
        }
        log.debug("this thread exiting under request....");
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadInterruptExample2 threadInterruptExample3 = new ThreadInterruptExample2();
        Thread thread = new Thread(threadInterruptExample3);
        log.debug("start thread .... ");
        thread.start();
        log.debug("interrupt thread ....");
        Thread.sleep(3000);
        thread.interrupt();
        //thread.isInterrupted() = false
        log.debug("线程是否中断：{}:{}", thread.getName(), thread.isInterrupted());
        log.debug("application over...");
    }
}
