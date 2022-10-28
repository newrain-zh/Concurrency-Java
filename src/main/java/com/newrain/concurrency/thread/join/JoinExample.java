package com.newrain.concurrency.thread.join;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author newRain
 * @description join方法可以让线程顺序执行
 */
@Slf4j
public class JoinExample implements Runnable {

    private String name;

    public JoinExample(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        log.debug("{} start", name);
        System.out.printf("%s begins: %s\n", name, new Date());
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            log.error("e", e);
        }
        log.debug("{} has finished", name);
    }

    public static void main(String[] args) {
        Thread thread1 = new Thread(new JoinExample("One"));
        Thread thread2 = new Thread(new JoinExample("Two"));
        try {
            thread1.start();
            thread1.join();
            thread2.start();
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            log.error("error", e);
        }

        log.debug("Main thread is finished");
    }

}
