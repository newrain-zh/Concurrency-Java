package com.newrain.concurrency.thread.interrupt;

import lombok.extern.slf4j.Slf4j;

/**
 * 调用interrupt() 线程内部不做处理代码示例
 *
 * @author newRain
 * @description 线程中断示例
 */
@Slf4j
public class ThreadInterruptExample1 implements Runnable {
    @Override
    public void run() {
        boolean stop = false;
        while (!stop) {
            log.debug("this thread is running...");
            long time = System.currentTimeMillis();
            while (System.currentTimeMillis() - time < 1000) {
            }
        }
        log.debug("this thread exiting under request....");
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new ThreadInterruptExample1(), "Interrupt thread");
        log.debug("start thread ....");
        thread.start();
        Thread.sleep(3000);
        log.debug("interrupt thread ....");
        //注意此处调用interrupt方法 开启的线程不会中断
        thread.interrupt();
        log.debug("线程是否中断：{}", thread.isInterrupted());
        Thread.sleep(3000);
        log.debug("application over ....");
    }
}
